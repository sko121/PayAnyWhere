package com.britesky.payanywhere.ui.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class FormatData {

	public static final SimpleDateFormat a = new SimpleDateFormat("MM/dd/yyyy");
	private static final DecimalFormat b = new DecimalFormat("0.00");

	public static String decimalNumber(String str) {
//		if (!str.matches("[0-9]*\\.?[0-9]*"))
//			throw new NumberFormatException();
//		if (str.matches(".*\\.0*$")) {
//			str = str.replaceAll("\\.0*", "");
//			if (str.matches("^0+.*"))
//				str = str.replaceAll("^0+", "");
//			if (!str.isEmpty())
//				break label86;
//			str = "0";
//		}
//		while (true) {
//			return str;
//			if (!str.matches(".*\\..*0+$"))
//				break;
//			str = str.replaceAll("0+$", "");
//			break;
//			label86: if (str.startsWith("."))
//				str = "0" + str;
//		}
		return null;
	}

	public static String formatPercentage(String paramString) {
		return formatPercentage(paramString, "%");
	}

	public static String formatPercentage(String paramString1,
			String paramString2) {
//		int i = -1;
//		if (paramString1 != null)
//			;
//		try {
//			int j = Integer.parseInt(paramString1.replace(paramString2, ""));
//			i = j;
//			if (i > 99) {
//				str = "99" + paramString2;
//				return str;
//			}
//		} catch (NumberFormatException localNumberFormatException) {
//			while (true) {
//				String str = null;
//				continue;
//				if (i <= 0)
//					str = "0" + paramString2;
//				else
//					str = Integer.toString(i) + paramString2;
//			}
//		}
		return null;
	}

	public static String formatPrice(String input) {
		StringBuilder sb;
		String str;
		if (!input.matches("^\\$(\\d{1,3}(\\,\\d{3})*|(\\d+))(\\.\\d{2})?$")) {
			sb = new StringBuilder(input.replaceAll("[^\\d]", ""));
			while ((sb.length() > 3) && (sb.charAt(0) == '0'))
				sb.deleteCharAt(0);
			while (sb.length() < 3)
				sb.insert(0, '0');
			sb.insert(-2 + sb.length(), '.');
			sb.insert(0, '$');
			str = sb.toString();
		} else {
			str = null;
		}
		return str;
	}

	public static String formatTaxPercentage(String paramString) {
		return formatTaxPercentage(paramString, "%");
	}

	public static String formatTaxPercentage(String paramString1,
			String paramString2) {
//		int i = -1;
//		if (paramString1 != null)
//			;
//		try {
//			int j = Integer.parseInt(paramString1.replace(paramString2, ""));
//			i = j;
//			if (i > 99999) {
//				str = "99.999" + paramString2;
//				return str;
//			}
//		} catch (NumberFormatException localNumberFormatException) {
//			while (true) {
//				String str = null;
//				continue;
//				if (i <= 0) {
//					str = "0" + paramString2;
//				} else {
//					StringBuilder localStringBuilder = new StringBuilder(
//							paramString1.replaceAll("[^\\d]", ""));
//					while ((localStringBuilder.length() > 3)
//							&& (localStringBuilder.charAt(0) == '0'))
//						localStringBuilder.deleteCharAt(0);
//					while (localStringBuilder.length() < 3)
//						localStringBuilder.insert(0, '0');
//					localStringBuilder.insert(-3 + localStringBuilder.length(),
//							'.');
//					localStringBuilder.insert(localStringBuilder.length(), '%');
//					str = localStringBuilder.toString();
//				}
//			}
//		}
		return null;
	}

	public static String price(int paramInt) {
		return b.format(paramInt / 100.0F);
	}
}