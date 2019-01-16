package com.revolut.moneytransfer;

import org.flywaydb.core.Flyway;
import org.jdbi.v3.core.Jdbi;

import com.revolut.moneytransfer.api.AccountResource;
import com.revolut.moneytransfer.api.TransactionResource;
import com.revolut.moneytransfer.api.UserResource;
import com.revolut.moneytransfer.dao.AccountDAO;
import com.revolut.moneytransfer.dao.UserDAO;
import com.revolut.moneytransfer.mapper.ServiceExceptionMapper;
import com.revolut.moneytransfer.service.SimpleTransferService;
import com.revolut.moneytransfer.service.TransferService;
import com.revolut.moneytransfer.service.TransferServiceValidator;

import io.dropwizard.Application;
import io.dropwizard.configuration.ResourceConfigurationSourceProvider;
import io.dropwizard.jdbi3.JdbiFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class MainApplication extends Application<MainApplicationConfiguration> {

	public static void main(String[] args) throws Exception {
		new MainApplication().run(args);
	}

	@Override
	public String getName() {
		return "money-transfer";
	}

	@Override
	public void initialize(Bootstrap<MainApplicationConfiguration> bootstrap) {
		bootstrap.setConfigurationSourceProvider(new ResourceConfigurationSourceProvider());
	}

	@Override
	public void run(MainApplicationConfiguration configuration, Environment environment) {
		final JdbiFactory factory = new JdbiFactory();
		final Jdbi jdbi = factory.build(environment, configuration.getDataSourceFactory(), "h2");

		Flyway flyway = Flyway.configure().dataSource(configuration.getDataSourceFactory().getUrl(),
				configuration.getDataSourceFactory().getUser(), configuration.getDataSourceFactory().getPassword()).load();
		
		
		flyway.migrate();

		final UserDAO userDAO = jdbi.onDemand(UserDAO.class);
		final AccountDAO accountDAO = jdbi.onDemand(AccountDAO.class);
		final TransferServiceValidator transferServiceValidator = new TransferServiceValidator();
		final TransferService transferService = new SimpleTransferService(accountDAO, transferServiceValidator);
		environment.jersey().register(new ServiceExceptionMapper());
		environment.jersey().register(new UserResource(userDAO));
		environment.jersey().register(new AccountResource(accountDAO));
		environment.jersey().register(new TransactionResource(transferService));
		

	}
}
