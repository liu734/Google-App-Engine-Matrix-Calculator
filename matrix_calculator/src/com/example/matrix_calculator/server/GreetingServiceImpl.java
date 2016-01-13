package com.example.matrix_calculator.server;

import java.util.Scanner;
import com.example.matrix_calculator.client.GreetingService;
import com.example.matrix_calculator.shared.FieldVerifier;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.appengine.api.datastore.*;
import com.google.appengine.api.datastore.Query.*;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
		GreetingService {

	public String greetServer(String input) throws IllegalArgumentException {
		// Verify that the input is valid.
		if (!FieldVerifier.isValidName(input)) {
			// If the input is not valid, throw an IllegalArgumentException back
			// to
			// the client.
			throw new IllegalArgumentException(
					"Name must be at least 4 characters long");
		}

		String serverInfo = getServletContext().getServerInfo();
		String userAgent = getThreadLocalRequest().getHeader("User-Agent");

		// Escape data from the client to avoid cross-site script
		// vulnerabilities.
		input = escapeHtml(input);
		userAgent = escapeHtml(userAgent);

		Scanner scan = new Scanner(input);

		if (scan.hasNext()) {

			// System.out.println(scan.next());

			String command = scan.next();

			if (command.equals("login")) {
				DatastoreService datastore = DatastoreServiceFactory
						.getDatastoreService();
				Entity loginuser = new Entity("User");

				Filter nameFilter = null;
				Filter passwordFilter = null;

				if (scan.hasNext()) {

					nameFilter = new FilterPredicate("userName",
							FilterOperator.EQUAL, scan.next());

				}

				if (scan.hasNext()) {
					passwordFilter = new FilterPredicate("password",
							FilterOperator.EQUAL, scan.next());

				}
				Filter heightRangeFilter = CompositeFilterOperator.and(
						nameFilter, passwordFilter);
				Query q = new Query("User").setFilter(heightRangeFilter);

				PreparedQuery pq = datastore.prepare(q);

				FetchOptions fetchOptions = FetchOptions.Builder.withOffset(0);

				if (pq.countEntities(fetchOptions) == 0) {

					return "<br><br>No this user, man !!! ";

				}

				for (Entity result : pq.asIterable()) {
					String firstName = (String) result.getProperty("userName");
					String email = (String) result.getProperty("email");
					String lastName = (String) result.getProperty("password");

					return "Welcome back!!" + firstName + lastName + email;
				}

			} else if (command.equals("register")) {

				DatastoreService datastore = DatastoreServiceFactory
						.getDatastoreService();
				Entity registeruser = new Entity("User");
				String temppassword = "";
				String tempuser = "";

				if (scan.hasNext()) {
					tempuser = scan.next();
					registeruser.setProperty("userName", tempuser);

				} else
					return "kidding me? what did you send?";

				if (scan.hasNext()) {
					temppassword = scan.next();

				} else
					return "no password?";

				if (scan.hasNext()) {
					if (temppassword.equals(scan.next())) {

						registeruser.setProperty("password", temppassword);

					} else
						return "pasword is wrong wrong wrong!!!!";

					// loginuser.setProperty("gender", "male");
				}

				if (scan.hasNext()) {

					registeruser.setProperty("eamil", scan.next());

				}

				if (scan.hasNext()) {

					registeruser.setProperty("phone", scan.next());
				}

				Filter nameFilter = new FilterPredicate("userName",
						FilterOperator.EQUAL, tempuser);
				// passwordFilter = new FilterPredicate("password",
				// FilterOperator.EQUAL, scan.next());

				// Filter heightRangeFilter = CompositeFilterOperator.and(
				// nameFilter, passwordFilter);
				Query q = new Query("User").setFilter(nameFilter);

				PreparedQuery pq = datastore.prepare(q);

				FetchOptions fetchOptions = FetchOptions.Builder.withOffset(0);

				if (pq.countEntities(fetchOptions) > 0) {

					return "The user name is used, please use a new one";

				}

				datastore.put(registeruser);

				return "Register successfully" + tempuser;

			}

		}

		//

		// Entity loginuser = new Entity("User");

		// loginuser.setProperty("userName", user);
		// loginuser.setProperty("password", 1122);
		// datastore.put(loginuser);
		//

		return "Hello, " + input + "!<br><br>I am running " + serverInfo
				+ ".<br><br>It looks like you are using:<br>" + userAgent;
	}

	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html
	 *            the html string to escape
	 * @return the escaped string
	 */

	// public void login(){

	// }
	// public void register(){

	// }

	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;");
	}
}
