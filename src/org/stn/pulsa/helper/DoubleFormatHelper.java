package org.stn.pulsa.helper;

import java.text.DecimalFormat;

public class DoubleFormatHelper {

	/**
	 * 
	 * @param nilai
	 *            1000
	 * @return 1k
	 */
	public static String formatK(String nilai) {
		Double d = Double.parseDouble(nilai) / 1000;
		return String.valueOf(d) + "k";
	}

	/**
	 * 
	 * @param nilai
	 *            1000
	 * @return 1k
	 */
	public static String formatK(Double nilai) {
		Double d = nilai / 1000;
		return String.valueOf(d) + "k";
	}

	/**
	 * 
	 * @param nilai
	 *            1000
	 * @return 1,000.00
	 */
	public static String format(String nilai) {
		DecimalFormat format = new DecimalFormat("###,###,###");
		return format.format(Long.parseLong(nilai));
	}

	/**
	 * 
	 * @param nilai
	 *            1000
	 * @return 1,000
	 */
	public static String format(Double nilai) {
		DecimalFormat format = new DecimalFormat("###,###,###");
		return format.format(nilai).replace(".0", "");
	}

}
