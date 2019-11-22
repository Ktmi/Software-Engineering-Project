package com.bluejay.server.logic;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.mvc.freemarker.FreemarkerMvcFeature;

import com.bluejay.server.db.DatabaseFacade;

/**
 * Configuration for the {@link org.glassfish.jersey.servlet.ServletContainer}.
 * Injected into the servlet container during initialization of the WebApp.
 */
@ApplicationPath("/rest")
public class ControlConfig extends ResourceConfig {

	public ControlConfig() {
		// Set package to expose components of
		packages("com.bluejay.server.logic");
		// Adds in Templating for pages
		property(FreemarkerMvcFeature.TEMPLATE_BASE_PATH, "templates/freemarker");
		register(FreemarkerMvcFeature.class);
		try {
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			final DatabaseFacade db = (DatabaseFacade) envCtx.lookup("bean/bluejay-db-facade");
			db.setDataSource((DataSource) envCtx.lookup("jdbc/bluejay-db"));
			register(new AbstractBinder() {
				@Override
				protected void configure() {
					bind(db).to(DatabaseFacade.class);
				}
			});

		} catch (NamingException e) {
			e.printStackTrace();
		}

	}
}