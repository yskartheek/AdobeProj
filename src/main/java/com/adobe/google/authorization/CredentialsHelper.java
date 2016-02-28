package com.adobe.google.authorization;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.adobe.google.constants.Constants;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;

/**
 * This class is used to get the Application Credentials to Access the GOOGLE API'S
 * @author KARTHEEK YS
 *
 */
public class CredentialsHelper {

	/**
	 * Gets the Credentials for Application Used to access Google API'S
	 * @return Credential
	 * @throws IOException
	 */
	public static Credential getCredentials() throws IOException {
		HttpTransport httpTransport = new NetHttpTransport();
		JacksonFactory jsonFactory = new JacksonFactory();

		//Getting the Authorization URL used to authorize the request and get the authorizationCode(AuthorizationCode Changes for every request).
		String AppAuthorizationUrl = new GoogleAuthorizationCodeRequestUrl(Constants.CLIENT_ID, Constants.REDIRECT_URI,
				Constants.SCOPES).build();
		System.out.println("Go to the following link in your browser:");
		System.out.println(AppAuthorizationUrl);

		// Read the authorization code from the input.
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("What is the authorization code?");
		String code = in.readLine();
		
		//Exchange the Code Entered with Google API'S to get an AuthToken ( This gets refreshed for every request )
		GoogleTokenResponse response = new GoogleAuthorizationCodeTokenRequest(httpTransport, jsonFactory,
				Constants.CLIENT_ID, Constants.CLIENT_SECRET, code, Constants.REDIRECT_URI).execute();
		
		//Use the Refresh Token To Get the credentials Used for This Thread of Application .
		return new GoogleCredential.Builder().setClientSecrets(Constants.CLIENT_ID, Constants.CLIENT_SECRET)
				.setJsonFactory(jsonFactory).setTransport(httpTransport).build().setAccessToken(response.getAccessToken())
				.setRefreshToken(response.getRefreshToken());
	}

}
