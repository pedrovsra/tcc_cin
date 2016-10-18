package com.pedro.auth;

public class OAuth2ClientCredentials {
	/** Value of the "API Key". */
	public static final String API_KEY = "70e57f0988ab46e0ad513a061cf43da8";

	/** Value of the "API Secret". */
	public static final String API_SECRET = "840b82d993bd425a88098875d083571e";

	/** Port in the "Callback URL". */
	public static final int PORT = 8888;

	/** Domain name in the "Callback URL". */
	public static final String DOMAIN = "localhost";

	public static void errorIfNotSpecified() {
		if (API_KEY.startsWith("Enter ") || API_SECRET.startsWith("Enter ")) {
			System.out.println("Enter API Key and API Secret from your Spotify application"
					+ " into API_KEY and API_SECRET in " + OAuth2ClientCredentials.class);
			System.exit(1);
		}
	}
}