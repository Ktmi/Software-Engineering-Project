package com.bluejay.server.logic;

import java.security.Key;

import javax.crypto.spec.SecretKeySpec;
import javax.inject.Singleton;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import com.bluejay.server.db.DatabaseFacade;

import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Configuration for the {@link org.glassfish.jersey.servlet.ServletContainer}.
 * Injected into the servlet container during initialization of the WebApp.
 * 
 * @author David Ramirez <drami102@fiu.edu>
 */
@ApplicationPath("/rest")
public class ControlConfig extends ResourceConfig {

	public ControlConfig() {
		// Set package to expose components of
		packages(false, "com.bluejay.server.logic.rest");

		// Filter for Authorization
		register(AuthorizationFilter.class);

		try {
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			final DatabaseFacade db = (DatabaseFacade) envCtx.lookup("bean/bluejay-db-facade");
			db.setDataSource((DataSource) envCtx.lookup("jdbc/bluejay-db"));

			byte[] secretBytes = { 1 };
			final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
			final Key key = new SecretKeySpec(secretBytes, signatureAlgorithm.getJcaName());

			register(new AbstractBinder() {
				@Override
				protected void configure() {
					bind(signatureAlgorithm).to(SignatureAlgorithm.class);
					bind(key).to(Key.class);
					bind(Authentication.class).to(Authentication.class).in(Singleton.class);
					bind(db).to(DatabaseFacade.class);
				}
			});

		} catch (NamingException e) {
			e.printStackTrace();
		}

	}
}