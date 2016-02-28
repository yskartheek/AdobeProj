package com.adobe.google.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.client.RestTemplate;

import com.adobe.google.constants.Constants;
import com.adobe.google.domain.GeocodeResponse;
import com.google.gson.Gson;

/**
 * GeoCodingService has the logic to Access the Google Geo Code API and get the required address Details .
 * @author KARTHEEK YS
 *
 */
public class GeoCodingService {

	/**
	 * This Methods Interacts with GOOGLE GEO CODE API and gets the right Address , Longitude and Latitude 
	 * @param theAddressToBeFormatted
	 * @return GeocodeResponse
	 */
	public static GeocodeResponse getFormattedAddress(String theAddressToBeFormatted) {
		Map<String, String> vars = new HashMap<String, String>();
		//Sends the Address to be formatted
		vars.put(Constants.GEO_CODE_ADDRESS_KEY, theAddressToBeFormatted);
		//Firing the request to Google Geo Code .
		RestTemplate restTemplate = new RestTemplate();
		String json = restTemplate.getForObject(Constants.GEOCODING_URL, String.class, vars);
		//Using GSON to get the Response .
		return new Gson().fromJson(json, GeocodeResponse.class);
	}
}
