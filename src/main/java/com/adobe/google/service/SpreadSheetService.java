package com.adobe.google.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import com.adobe.google.constants.Constants;
import com.adobe.google.domain.GeocodeResponse;
import com.adobe.google.util.AddressUtil;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.ListFeed;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetFeed;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;

/**
 * This class has the business logic to get the spreadsheet from give sheet and
 * copy it to a give GOOGLE CLOUD and to update the address details in the same
 * 
 * @author KARTHEEK YS
 *
 */
public class SpreadSheetService {

	// String to get the Download Path of the updated SpreadSheet.
	public static String aNewFileDownloadPath;

	/**
	 * This Method is used to get the excel from given URL and copy the same to
	 * given CLOUD .
	 * 
	 * @param appAccessCredentials
	 * @throws IOException
	 * @throws ServiceException
	 * @throws URISyntaxException
	 */
	public static void uploadSpreadSheet(Credential appAccessCredentials)
			throws IOException, ServiceException, URISyntaxException {
		HttpTransport httpTransport = new NetHttpTransport();
		JacksonFactory jsonFactory = new JacksonFactory();
		// Create a DRIVE Service to copy the File .
		Drive driveService = new Drive.Builder(httpTransport, jsonFactory, appAccessCredentials).build();
		// Creating a File with Configured Name
		File body = new File();
		body.setName(Constants.FILE_NAME);
		body.setMimeType(Constants.MIME_TYPE);
		// Getting the Given FILE ID
		String fileId = Constants.GIVEN_FILE_ID;
		// Copy the File from given fileId and excute the same to create a file
		// in your GOOGLE CLOUD
		body = driveService.files().copy(fileId, body).execute();
		aNewFileDownloadPath = "https://docs.google.com/spreadsheets/d/" + body.getId() + "/edit#gid=0";
	}

	/**
	 * This Method Gets the spreadSheet , Interacts with Geo Code to get the
	 * correct Address and update the same in given google cloud .
	 * 
	 * @param appAccessCredentials
	 * @throws AuthenticationException
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws ServiceException
	 */
	public static void updateSpreadSheet(Credential appAccessCredentials)
			throws AuthenticationException, MalformedURLException, IOException, ServiceException {

		// Setting the Authentication from the credentials obtained .
		SpreadsheetService aRequestedService = new SpreadsheetService(Constants.APPLICATION_NAME);
		aRequestedService.setOAuth2Credentials(appAccessCredentials);

		// Define the SPREADSHEET_FEED_URL to request. This should never change.
		URL SPREADSHEET_FEED_URL = new URL(Constants.SPREADSHEET_FEED_URL);

		// Make a request to the API and get all spreadsheets.
		SpreadsheetFeed theSpreadSheetFeed = aRequestedService.getFeed(SPREADSHEET_FEED_URL, SpreadsheetFeed.class);
		List<SpreadsheetEntry> theExistingSpreadSheets = theSpreadSheetFeed.getEntries();

		if (theExistingSpreadSheets.size() == 0) {
			System.out.println("Hey There were no Spread Sheets!!");
		}

		// Getting the recently created spreadsheet , Using get(0) to get the
		// latest file as the name of file is configurable and can change .
		SpreadsheetEntry aRequestedSpreadSheet = theExistingSpreadSheets.get(0);
		System.out.println(aRequestedSpreadSheet.getTitle().getPlainText());

		WorksheetFeed theWorksheetFeed = aRequestedService.getFeed(aRequestedSpreadSheet.getWorksheetFeedUrl(),
				WorksheetFeed.class);
		List<WorksheetEntry> theExistingWorksheets = theWorksheetFeed.getEntries();
		WorksheetEntry theRequiredworksheet = theExistingWorksheets.get(0);

		// Fetch all the Rows in the worksheet
		URL listFeedUrl = theRequiredworksheet.getListFeedUrl();
		ListFeed listFeed = aRequestedService.getFeed(listFeedUrl, ListFeed.class);

		// Iterating through all the rows to get the formatted address ,
		// Longitude and Latitude
		for (ListEntry theCurrentRow : listFeed.getEntries()) {
			// Gets the Address to Format from UTIl .
			String theAddressToBeFormatted = AddressUtil.getAddressToFormat(theCurrentRow);
			// Get the Formatted Address from GEO CODE API
			GeocodeResponse theFormattedAddress = GeoCodingService.getFormattedAddress(theAddressToBeFormatted);
			// SET the new address details to each row and update the same .
			if (theFormattedAddress.getResults().size() != 0) {
				theCurrentRow.getCustomElements().setValueLocal(Constants.GOOGLE_VERIFIED_ADDRESS,
						theFormattedAddress.getResults().get(0).getFormatted_address());
				theCurrentRow.getCustomElements().setValueLocal(Constants.LATITUDE,
						String.valueOf(theFormattedAddress.getResults().get(0).getGeometry().getLocation().getLat()));
				theCurrentRow.getCustomElements().setValueLocal(Constants.LONGITUDE,
						String.valueOf(theFormattedAddress.getResults().get(0).getGeometry().getLocation().getLng()));
			} else {
				theCurrentRow.getCustomElements().setValueLocal(Constants.GOOGLE_VERIFIED_ADDRESS,
						Constants.FORMAT_ERROR_MSG);
				theCurrentRow.getCustomElements().setValueLocal(Constants.LATITUDE, Constants.FORMAT_ERROR_MSG);
				theCurrentRow.getCustomElements().setValueLocal(Constants.LONGITUDE, Constants.FORMAT_ERROR_MSG);
			}
			// Calling the SpreadSheet API to update the row
			theCurrentRow.update();
		}
		// Printing the File Path to view on Console .
		System.out.println("VIEW THE UPDATED FILE USING :" + aNewFileDownloadPath);
	}
}