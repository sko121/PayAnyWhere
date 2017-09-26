package com.britesky.ipos.utils;

import java.text.SimpleDateFormat;

import android.graphics.Bitmap;

public class VariablesComm {
	// 日期控件data
	// data
	public static String data[] = new String[] { "2012", "2013", "2014",
			"2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022",
			"2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030",
			"2031", "2032" };
	public static String hh[] = new String[] { "01", "02", "03", "04", "05",
			"06", "07", "08", "09", "10", "11", "12" };
	// data
	public static String data1[] = new String[] { "12", "13", "14", "15", "16",
			"17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27",
			"28", "29", "30", "31", "32" };
	// 支付
	public static String currency[] = new String[] { "SGD", "USD", "EUR" };

	public static String PAY_RESULT = "failed";
	public static String TRANSACTION_RESULT = "failed";

	public static String HANDSET_NUMBER_TID = "7009100001";
	public static int selectwhell = 1;
	public static int select = 1;

	public static int clickTag = 1;

	// getTime
	public static String getCurrentTime() {
		SimpleDateFormat sDateFormat = new SimpleDateFormat("HH:mm MM/dd/yyyy");
		String data = sDateFormat.format(new java.util.Date());
		return data;
	}

	public static final int RESULT_OK = 17;
	public static Bitmap BITMAP_TAG = null;

	public static int TOUCH_MOVE_TAG = 1;
}
