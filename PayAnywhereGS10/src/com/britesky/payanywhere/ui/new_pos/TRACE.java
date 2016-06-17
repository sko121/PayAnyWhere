package com.britesky.payanywhere.ui.new_pos;

import android.util.Log;

public class TRACE {

	private static String AppName = "POS_SDK";

	private static Boolean isTesting = true;

	public static void i(String string) {
		if (isTesting) {
			Log.i(AppName, string);
		}
	}
 
	public static void w(String string) {
		if (isTesting) {
			Log.e(AppName, string);
		}
	}

	public static void e(Exception exception) {
		if (isTesting) {
			Log.e(AppName, exception.toString());
		}
	}

	public static void d(String string) {
		if (isTesting) {
			Log.d(AppName, string);
		}
	}

	public static void a(int num) {
		if (isTesting) {
			Log.d(AppName, Integer.toString(num));
		}
	}
	
}