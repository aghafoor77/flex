package com.ri.se.aggregation.acctmanegement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.crypto.tink.subtle.Base64;
import com.ri.se.aggregation.Representation;
import com.ri.se.aggregation.RestClient;
import com.ri.se.commonentities.AssignRole;
import com.ri.se.commonentities.DTOUsers;

public class AccountHandling {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new AccountHandling().createAccount();
	}

	public static void createAccount() {
		/// tapi/idms/v1/account
		String ip = "http://localhost";

		int port = 9000;
		String resource = "/tapi/idms/v1/account";

		Map<String, String> user = new HashMap<String, String>();
		user.put("email", "employee@gmail.com");
		user.put("password", "abdul1234");
		createAccount(ip, port, resource, user);

		user = new HashMap<String, String>();
		user.put("email", "farmowner@gmail.com");
		user.put("password", "abdul1234");
		createAccount(ip, port, resource, user);

		user = new HashMap<String, String>();
		user.put("email", "transporter@gmail.com");
		user.put("password", "abdul1234");
		createAccount(ip, port, resource, user);

		user = new HashMap<String, String>();
		user.put("email", "slaughterhouse@gmail.com");
		user.put("password", "abdul1234");
		createAccount(ip, port, resource, user);

		resource = "/tapi/idms/v1/auth";
		DTOUsers userDTO = authenticate(ip, port, resource, "basic " + Base64.encode("admin@lsc.se:1234".getBytes()));

		// List user
		resource = "/tapi/idms/v1/account/listusers";

		List<Map<String, String>> users = listUser(ip, port, "basic " + Base64.encode("admin@lsc.se:1234".getBytes()),
				resource);
		for (Map<String, String> aUser : users) {

			AssignRole assignRole = new AssignRole();

			if (aUser.get("email").equals("transporter@gmail.com")) {
				assignRole.setRoles("TRANSPORTER");
			}
			if (aUser.get("email").equals("employee@gmail.com")) {
				assignRole.setRoles("EMPLOYEE");
			}
			if (aUser.get("email").equals("farmowner@gmail.com")) {
				assignRole.setRoles("OWNER");
			}
			if (aUser.get("email").equals("slaughterhouse@gmail.com")) {
				assignRole.setRoles("SALUGHTERHOUSEOWNER");
			}

			assignRole.setVeid(aUser.get("veid"));
			assignRole.setEmail(aUser.get("email"));
			assignRole.setOrganization("RISE AB");
			resource = "/tapi/idms/v1/account/assignrole";
			assignRole(ip, port, resource, assignRole, "basic " + Base64.encode("admin@lsc.se:1234".getBytes()));

		}

		// Assign Role
		//
		//
		//
		//

		/*
		 * AssignRole assignRole = new AssignRole(); assignRole.setVeid(
		 * "4433372a5164534c782d382d456b56756c795449744a2a4f783164705257");
		 * assignRole.setEmail("aghafoor77@gmail.com");
		 * assignRole.setEmail("aghafoor77@gmail.com"); resource =
		 * "/tapi/idms/v1/account/assignrole"; assignRole(ip, port, resource,
		 * assignRole);
		 */
		resource = "/tapi/idms/v1/auth";
		userDTO = authenticate(ip, port, resource,
				"basic " + Base64.encode("farmowner@gmail.com:abdul1234".getBytes()));
		if (Objects.isNull(userDTO)) {

		} else {
			System.out.println(userDTO.getVeid());
		}
	}

	public static String createAccount(String ip, int port, String resource, Object data) {
		try {
			System.out.println(data);
			RestClient rc = RestClient.builder().baseUrl(ip + ":" + port).build();
			Representation rep = rc.post(resource, data, null);
			System.out.println(rep.getCode());
			System.out.println(rep.getBody().toString());
			Scanner scanner = new Scanner(System.in);

			// Message to the user
			System.out.println("Press Enter to continue...");

			// Wait for the user to press Enter
			// scanner.nextLine();
			return rep.getBody().toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Scanner scanner = new Scanner(System.in);

		// Message to the user
		System.out.println("Press Enter to continue...");

		// Wait for the user to press Enter
		// scanner.nextLine();
		return null;
	}

	public static List<Map<String, String>> listUser(String ip, int port, String header, String resource) {
		try {
			RestClient rc = RestClient.builder().baseUrl(ip + ":" + port).build();
			HashMap<String, String> headers = new HashMap<String, String>();
			headers.put("Authorization", header);
			Representation rep = rc.get(resource, headers);
			System.out.println(rep.getCode());
			System.out.println(rep.getBody().toString());

			ObjectMapper om = new ObjectMapper();
			List<Map<String, String>> users = new ArrayList<Map<String, String>>();
			users = om.readValue(rep.getBody().toString().getBytes(), users.getClass());
			return users;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public static DTOUsers authenticate(String ip, int port, String resource, String data) {
		try {
			System.out.println(data);
			RestClient rc = RestClient.builder().baseUrl(ip + ":" + port).build();
			HashMap<String, String> headers = new HashMap<String, String>();
			headers.put("Authorization", data);
			Representation rep = rc.post(resource, data, headers);
			System.out.println(rep.getCode());
			System.out.println(rep.getBody().toString());
			Scanner scanner = new Scanner(System.in);

			// Message to the user
			System.out.println("Press Enter to continue...");

			// Wait for the user to press Enter
			// scanner.nextLine();
			return new ObjectMapper().readValue(rep.getBody().toString(), DTOUsers.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Scanner scanner = new Scanner(System.in);

		// Message to the user
		System.out.println("Press Enter to continue...");

		// Wait for the user to press Enter
		// scanner.nextLine();
		return null;
	}

	public static String assignRole(String ip, int port, String resource, Object data, String header) {
		try {
			System.out.println(data);
			RestClient rc = RestClient.builder().baseUrl(ip + ":" + port).build();
			HashMap<String, String> headers = new HashMap<String, String>();
			headers.put("Authorization", header);
			Representation rep = rc.put(resource, data, headers);
			System.out.println(rep.getCode());
			System.out.println(rep.getBody().toString());
			Scanner scanner = new Scanner(System.in);

			// Message to the user
			System.out.println("Press Enter to continue...");

			// Wait for the user to press Enter
			// scanner.nextLine();
			return rep.getBody().toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Scanner scanner = new Scanner(System.in);

		// Message to the user
		System.out.println("Press Enter to continue...");

		// Wait for the user to press Enter
		// scanner.nextLine();
		return null;
	}

}
