package com.adobe.configuration;

import java.io.IOException;
import java.net.URISyntaxException;

import com.adobe.google.authorization.CredentialsHelper;
import com.adobe.google.service.SpreadSheetService;
import com.google.api.client.auth.oauth2.Credential;
import com.google.gdata.util.ServiceException;

/**
 * This is a Main Class for Spring Boot Application, Currently I am running this
 * as an standlone app using the MAIN This class can also be used to load a
 * spring boot app with the required container .
 * 
 * @author KARTHEEK YS
 *
 */
public class AdobeInterviewSolutionApplication {

	public static void main(String[] args) {
		try {

			Credential appCredencial = null;
			// Call the Credential Helper to get App Credentials
			appCredencial = CredentialsHelper.getCredentials();

			// Call the UploadSpreadSheet Method to copy the data from given
			// file and create my own copy of the same file in given google
			// cloud details (Currently using my cloud can be changed in
			// constants).
			SpreadSheetService.uploadSpreadSheet(appCredencial);

			// Call the updateSpreadSheet method to update the Address , Latitude , Longitude for
			// all the ROWS by getting the data from Google GEO CODE API
			SpreadSheetService.updateSpreadSheet(appCredencial);

		} catch (IOException theIOException) {
			System.out.println("Exception : IOException Occured While Processing the Request ");
			theIOException.printStackTrace();
		} catch (ServiceException theServiceException) {
			System.out.println("Exception : ServiceException Occured While Processing the Request ");
			theServiceException.printStackTrace();
		} catch (URISyntaxException theURIException) {
			System.out.println("Exception : URISyntaxException Occured While Processing the Request ");
			theURIException.printStackTrace();
		}
	}
}
