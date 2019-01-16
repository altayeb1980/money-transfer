package com.revolut.moneytransfer.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import com.revolut.moneytransfer.dao.AccountDAO;
import com.revolut.moneytransfer.model.Account;
import com.revolut.moneytransfer.model.UserTransaction;
import com.revolut.moneytransfer.service.SimpleTransferService;
import com.revolut.moneytransfer.service.TransferServiceValidator;

import io.dropwizard.testing.junit.ResourceTestRule;

@RunWith(MockitoJUnitRunner.class)
public class TransactionResourceTest {

	
	private static final AccountDAO dao = mock(AccountDAO.class);
	private static final TransferServiceValidator transferServiceValidator = mock(TransferServiceValidator.class);
		
	
	@ClassRule
	public static final ResourceTestRule resources = ResourceTestRule.builder().addResource(new TransactionResource( new SimpleTransferService(dao, transferServiceValidator))).build();
	
	
	private UserTransaction userTransaction;
	private Account debitorAccount;
	private Account creditorAccount;
	private Optional<Account> debitorOption;
	private Optional<Account> creditorOption;
	
	
	@Before
    public void setup() {
		userTransaction = new 	UserTransaction("USD", new BigDecimal("10.000"),1L,2L);
		debitorAccount = new Account(1L,"altayeb",new BigDecimal(100.0000),"USD");
		creditorAccount = new Account(2L,"pawel",new BigDecimal(100.0000),"USD");
		debitorOption = Optional.of(debitorAccount);
		creditorOption = Optional.of(creditorAccount);
	}
	
	
	@Test
	public void testTransferFund() {
		when(dao.findById(1L)).thenReturn(debitorOption);
		when(dao.findById(2L)).thenReturn(creditorOption);

		Account expectedNewDebitorAccount = new Account(1L,"altayeb",new BigDecimal("90.000"),"USD");
		
		Entity<?> entity = Entity.entity(userTransaction, MediaType.APPLICATION_JSON_TYPE);
		
		final Response response = resources.target("/transaction").request().post(entity);
		verify(dao).updateAccountBalance(expectedNewDebitorAccount);	
		assertThat(response.getStatus()).isEqualTo(Response.status(Response.Status.OK).build().getStatus());
	}
	
	
}
