package com.adobe.google.domain;

/**
 * Gets the formatted address from GEO CODE API .
 * @author KARTHEEK YS
 *
 */
public class Geocode {

	private String formatted_address;
	private Geometry geometry;

	public void setFormatted_address(String formatted_address) {
		this.formatted_address = formatted_address;
	}

	public String getFormatted_address() {
		return formatted_address;
	}

	public Geometry getGeometry() {
		return geometry;
	}

	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}

}
