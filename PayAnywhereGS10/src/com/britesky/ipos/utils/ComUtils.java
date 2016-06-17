package com.britesky.ipos.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.britesky.payanywhere.R;

//import com.britesky.ipos.R;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ComUtils {
	public static String VISA="";
	public static long RECEIPT_NO= System.currentTimeMillis(); ;
	public static String SALES="";
	public static String CURRENCY="EUR";
	public static String EXPIRY="";
	public static String DEVICESTPYE="";
	public static String EMAILACCOUNT="";
	public static String APP="";
	// 邮箱验证
	public static boolean validateEmailAddress(String email) {
		Pattern pattern = Pattern
				.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
		Matcher matcher = pattern.matcher(email);
		System.out.println(matcher.matches());
		return matcher.matches();
	}
	// 自定义Toast
	public static void toast(Context mContext, String msg) {
		Toast toast = Toast.makeText(mContext.getApplicationContext(),
									 msg, 
									 Toast.LENGTH_SHORT);

		LinearLayout toastView = (LinearLayout) toast.getView();

		ImageView imageCodeProject = new ImageView(
				mContext.getApplicationContext());

		imageCodeProject.setImageResource(R.drawable.yes);

		toastView.addView(imageCodeProject, 0);
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.show();
	}
	// 自定义Toast
	public static void toastInfo(Context mContext, String msg) {
		Toast toast = Toast.makeText(mContext.getApplicationContext(),

		msg, Toast.LENGTH_SHORT);

		LinearLayout toastView = (LinearLayout) toast.getView();

		ImageView imageCodeProject = new ImageView(
				mContext.getApplicationContext());

		imageCodeProject.setImageResource(R.drawable.icon_info);

		toastView.addView(imageCodeProject, 0);
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.show();
	}



	// Activity跳转
	public static void startIntent(Context mcontext, Class<?> startActivity) {
		Intent intent = null; 
		intent = new Intent(mcontext, startActivity);
		mcontext.startActivity(intent);
	}

	public static String replace(String cardNumber){
		String newWord = null;
		String olderWord = null;
		try {
			for (int i = 0; i < 10; i++) {
				if (null != newWord) {
					olderWord = String.valueOf(newWord.charAt(i));
					newWord = newWord.replaceFirst(olderWord, "*");
				} else {
					olderWord = String.valueOf(cardNumber.charAt(i));
					newWord = cardNumber.replaceFirst(olderWord, "*");
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			newWord=cardNumber;
		}
	
		return newWord;
	}

}
