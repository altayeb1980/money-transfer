package com.revolut.moneytransfer;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

import com.revolut.moneytransfer.dao.DAOFactory;
import com.revolut.moneytransfer.service.AccountService;
import com.revolut.moneytransfer.service.ServiceExceptionMapper;
import com.revolut.moneytransfer.service.TransactionService;
import com.revolut.moneytransfer.service.UserService;

public class Application {

	public static void main(String[] args) throws Exception {

		DAOFactory h2DaoFactory = DAOFactory.getDAOFactory(DAOFactory.H2);
		h2DaoFactory.populateTestData();

		// Host service on jetty
		startService();
	}

	private static void startService() throws Exception {
		Server server = new Server(9090);
		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.setContextPath("/");
		server.setHandler(context);
		ServletHolder servletHolder = context.addServlet(ServletContainer.class, "/*");
		servletHolder.setInitParameter("jersey.config.server.provider.classnames",
				UserService.class.getCanonicalName() + "," + AccountService.class.getCanonicalName() + ","
						+ TransactionService.class.getCanonicalName() + ","
						+ ServiceExceptionMapper.class.getCanonicalName());
		try {
			server.start();
			server.join();
		} finally {
			server.destroy();
		}
	}
}
