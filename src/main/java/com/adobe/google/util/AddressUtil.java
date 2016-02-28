package com.adobe.google.util;

import com.google.gdata.data.spreadsheet.ListEntry;

/**
 * Address UTIL which will return the given address to be formatted .
 * @author KARTHEEK YS
 *
 */
public class AddressUtil {
	
	/**
	 * Gets the Rown and gives the given address to be formatted 
	 * @param aGivenRow
	 * @return String
	 */
	public static String getAddressToFormat(ListEntry aGivenRow) {
		return aGivenRow.getCustomElements().getValue("Address") + " " + aGivenRow.getCustomElements().getValue("City") + " "
				+ aGivenRow.getCustomElements().getValue("Country") + " " + aGivenRow.getCustomElements().getValue("postalcode")
				+ " " + aGivenRow.getCustomElements().getValue("stateprovince") + " "
				+ aGivenRow.getCustomElements().getValue("country_2");
	}
}
