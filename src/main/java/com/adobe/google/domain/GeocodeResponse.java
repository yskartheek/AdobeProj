package com.adobe.google.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * This Class is Used to accept the GEO CODE Response from Google Geo Code API 
 * @author KARTHEEK YS
 *
 */
public class GeocodeResponse {

	private String status;
	private List<Geocode> results = new ArrayList<Geocode>();

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setResults(List<Geocode> results) {
		this.results = results;
	}

	public List<Geocode> getResults() {
		return results;
	}
}
