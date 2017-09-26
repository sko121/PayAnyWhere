package com.britesky.payanywhere.ui.new_pos;

public class Util {

	/**
	 * 将毫秒�?转换成时分秒（时长）
	 * 
	 * @param l
	 * @return
	 */
	public static String formatLongToTimeStr(Long l) {
		String str = "";
		long hour = 0;
		long minute = 0;
		float second = 0;
		second = (float) l / (float) 1000;
		if (second > 60) {
			minute = (long) (second / 60);
			second = second % 60;
			if (minute > 60) {
				hour = minute / 60;
				minute = minute % 60;
				str = hour + "h" + minute + "m" + second + "s";
			} else {
				str = minute + "m" + second + "s";
			}
		} else {
			str = second + "s";
		}
		return str;

	}

	public static String removeAmountSymbol(String amount) {
		amount = amount.replace("$", "");
		amount = amount.replace(".", "");
		amount = amount.replace(",", "");
		while (amount.startsWith("0")) {
			amount = amount.substring(1);
		}
		return amount;
	}

	public static String removeAmountDollar(String amount) {
		amount = amount.replace("$", "");
		amount = amount.replace(",", "");

		return amount;
	}
}
