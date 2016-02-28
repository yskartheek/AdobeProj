package com.adobe.google.constants;

import java.util.Arrays;
import java.util.List;

/**
 * This Class has all the constants used in the Application .
 * @author KARTHEEK YS
 *
 */
public class Constants {
	
	//Change the Below four Parameters with your cloud details If you want to run the Application on your google cloud .
	public static final String APPLICATION_NAME = "AdobeInterview";
	public static final String CLIENT_ID = "788925749861-rrdq0ujl3bnelbo4pgvpc2mhq2l2i1ds.apps.googleusercontent.com";
	public static final String CLIENT_SECRET = "sPaIJYt2GnV610pUA3v8eisG";
	public static final String REDIRECT_URI = "http://localhost:8080";
	
	//Generic Parameters used for Google API'S
	public static final List<String> SCOPES = Arrays.asList("https://spreadsheets.google.com/feeds","http://docs.google.com/feeds","https://www.googleapis.com/auth/drive");
	public static final String GEOCODING_URL = "http://maps.googleapis.com/maps/api/geocode/json?address={address}&sensor=false";
	public static final String SPREADSHEET_FEED_URL = "https://spreadsheets.google.com/feeds/spreadsheets/private/full";
	public static final String GOOGLE_VERIFIED_ADDRESS = "googleverifiedaddress";
	public static final String LONGITUDE = "latitude";
	public static final String LATITUDE = "longitude";
	public static final String FORMAT_ERROR_MSG = "Unable To Format Address";
	public static final String GEO_CODE_ADDRESS_KEY = "address";
	public static final String FILE_NAME = "Address-MyCopy";
	public static final String MIME_TYPE = "application/vnd.ms-excel";
	public static final String GIVEN_FILE_ID = "1yZg4D6RLkRZkotKBy840QoBjt5qEmS7uJbvy-GQbDGc";




}
