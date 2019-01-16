package com.revolut.moneytransfer.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.core.GenericType;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import com.revolut.moneytransfer.dao.AccountDAO;
import com.revolut.moneytransfer.model.Account;

import io.dropwizard.testing.junit.ResourceTestRule;


@RunWith(MockitoJUnitRunner.class)
public class AccountResourceTest {

	private static final AccountDAO dao = mock(AccountDAO.class);
	
	
	private Account account;
	
	private Optional<Account> option;
	
	@ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new AccountResource(dao))
            .build();
	
	
	@Before
    public void setup() {
		account = new Account(100L,"altayeb",new BigDecimal(100.000),"USD");
		option = Optional.of(account);
    }
	
	@After
    public void tearDown(){
		reset(dao);
    }
	
	

	@Test
    public void testGetAccount() {
		when(dao.findById(100L)).thenReturn(option);
		Account response = resources.target("/account/100").request().get(Account.class);
		
		verify(dao).findById(100L);
		assertThat(response).isEqualTo(account);
		
    }
		
	
	
	
	@Test
    public void testGetAllAccounts() {
		final List<Account> accounts = Collections.singletonList(account);
		when(dao.getAll()).thenReturn(accounts);
		final List<Account> response = resources.target("/account/all").request().get(new GenericType<List<Account>>() {
			
		});
		verify(dao).getAll();
		assertThat(response).containsAll(accounts);
    }
	
	
}
