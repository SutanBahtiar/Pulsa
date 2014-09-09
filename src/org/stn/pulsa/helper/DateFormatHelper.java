package org.stn.pulsa.helper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatHelper {

	public static String getDateTime(Date tanggal) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(tanggal);
	}

	/**
	 * 
	 * @param String
	 *            YYYY-MM-DD
	 * @return DD/MM/YYYY
	 */
	public static String toFormat(String date) {
		String[] tglFormat = date.split("-");
		return new String(tglFormat[2] + "/" + tglFormat[1] + "/" + tglFormat[0]);
	}
}
