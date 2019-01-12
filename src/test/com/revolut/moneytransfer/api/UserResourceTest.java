package com.revolut.moneytransfer.api;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

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

import com.revolut.moneytransfer.dao.UserDAO;
import com.revolut.moneytransfer.model.Account;
import com.revolut.moneytransfer.model.User;

import io.dropwizard.testing.junit.ResourceTestRule;

@RunWith(MockitoJUnitRunner.class)
public class UserResourceTest {

	private static final UserDAO dao = mock(UserDAO.class);

	private User user;
	private Optional<User> option;

	@ClassRule
	public static final ResourceTestRule resources = ResourceTestRule.builder().addResource(new UserResource(dao))
			.build();

	@Before
	public void setup() {
		user = new User("altayeb", "loay.saada@gmail.com");
		option = Optional.of(user);
	}

	@After
	public void tearDown() {
		reset(dao);
	}

	@Test
	public void testGetUser() {
		when(dao.findByEmailAddress("loay.saada@gmail.com")).thenReturn(option);
		User response = resources.target("/user/loay.saada@gmail.com").request().get(User.class);
		verify(dao).findByEmailAddress("loay.saada@gmail.com");
		assertThat(response).isEqualTo(user);
	}

	@Test
	public void testGetAllUsers() {
		final List<User> users = Collections.singletonList(user);
		when(dao.findAll()).thenReturn(users);
		final List<User> response = resources.target("/user/all").request().get(new GenericType<List<User>>() {

		});
		verify(dao).findAll();
		assertThat(response).containsAll(users);
	}

}
