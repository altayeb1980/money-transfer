package com.revolut.moneytransfer.service;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.StampedLock;

import javax.validation.constraints.NotNull;

import com.revolut.moneytransfer.dao.AccountDAO;
import com.revolut.moneytransfer.exception.AccountNotFoundException;
import com.revolut.moneytransfer.exception.OptimisticLockException;
import com.revolut.moneytransfer.exception.TransferFailureException;
import com.revolut.moneytransfer.model.Account;
import com.revolut.moneytransfer.model.UserTransaction;
import com.revolut.moneytransfer.utils.MoneyUtil;

public class SimpleTransferService implements TransferService {

	private static final int THREAD_POOL_SIZE = 3;
	private AccountDAO accountDAO;
	private ConcurrentHashMap<String, StampedLock> sendLocks;
	private ConcurrentHashMap<String, StampedLock> receiveLocks;
	private ExecutorService senders;
	private ExecutorService receivers;

	public SimpleTransferService(AccountDAO accountDAO) {
		this.accountDAO = accountDAO;
		this.senders = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
		this.receivers = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
		this.sendLocks = new ConcurrentHashMap<>(THREAD_POOL_SIZE * 2);
		this.receiveLocks = new ConcurrentHashMap<>(THREAD_POOL_SIZE * 2);
	}

	private Account findAccount(@NotNull long accountId) {
		return accountDAO.findById(accountId).orElseThrow(() -> new AccountNotFoundException(accountId));
	}

	private long updateAccount(@NotNull Account account, @NotNull StampedLock lock, long stamp) {
		stamp = lock.tryConvertToWriteLock(stamp);
		if (stamp != 0) {
			try {
				accountDAO.updateAccountBalance(account);
			} finally {
				stamp = lock.tryConvertToOptimisticRead(stamp);
			}
		} else {
			throw new OptimisticLockException(account);
		}
		return stamp;
	}

	private CompletableFuture<Account> send(final UserTransaction userTransaction) {
		return CompletableFuture.supplyAsync(() -> {
			Account debit = null;
			StampedLock lock = sendLocks.computeIfAbsent(String.valueOf(userTransaction.getFromAccountId()),
					it -> new StampedLock());
			long stamp = lock.tryOptimisticRead();
			try {
				Account sender = findAccount(userTransaction.getFromAccountId());

				// check transaction currency
				if (!sender.getCurrencyCode().equals(userTransaction.getCurrencyCode())) {
					throw new TransferFailureException(
							"Fail to transfer Fund, transaction ccy are different from sender");
				}

				BigDecimal fromAccountLeftOver = sender.getBalance().subtract(userTransaction.getAmount());

				if (fromAccountLeftOver.compareTo(MoneyUtil.zeroAmount) < 0) {
					throw new TransferFailureException("Not enough Fund from source Account ");
				}

				debit = new Account(userTransaction.getFromAccountId(), sender.getUserName(), fromAccountLeftOver,
						sender.getCurrencyCode());
				stamp = updateAccount(debit, lock, stamp);
			} catch (Exception e) {
				System.err.println("Transfer failed: " + e);
				throw e;
			} finally {
				if (lock.tryConvertToWriteLock(stamp) != 0) {
					sendLocks.remove(String.valueOf(userTransaction.getFromAccountId()));
				}
			}
			receivers.submit(() -> receive(userTransaction));
			return debit;
		}, senders);

	}

	private CompletableFuture<Account> receive(final UserTransaction userTransaction) {
		return CompletableFuture.supplyAsync(() -> {
			Account credit = null;
			StampedLock lock = receiveLocks.computeIfAbsent(String.valueOf(userTransaction.getToAccountId()),
					it -> new StampedLock());
			long stamp = lock.tryOptimisticRead();
			try {
				Account receiver = findAccount(userTransaction.getToAccountId());

				// check transaction currency
				if (!receiver.getCurrencyCode().equals(userTransaction.getCurrencyCode())) {
					throw new TransferFailureException(
							"Fail to transfer Fund, transaction ccy are different from receiver");
				}

				credit = new Account(userTransaction.getToAccountId(), receiver.getUserName(),
						receiver.getBalance().add(userTransaction.getAmount()), receiver.getCurrencyCode());
				stamp = updateAccount(credit, lock, stamp);
			} catch (Exception e) {
				System.out.println("Transfer failed: {}. Reason: " + e);
				throw e;
			} finally {
				if (lock.tryConvertToWriteLock(stamp) != 0) {
					receiveLocks.remove(String.valueOf(userTransaction.getToAccountId()));
				}
			}
			return credit;
		}, receivers);
	}

	@Override
	public CompletableFuture<Account> transferFund(UserTransaction userTransaction) {
		return send(userTransaction);
	}
}
