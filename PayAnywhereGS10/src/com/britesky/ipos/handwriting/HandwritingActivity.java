package com.britesky.ipos.handwriting;

//import com.britesky.ipos.R;
import com.britesky.ipos.utils.ComUtils;
import com.britesky.ipos.utils.VariablesComm;
import com.britesky.payanywhere.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class HandwritingActivity extends Activity {

	FingerDrawView drawView;
	TextView txt_total, agreeToSignPrompt;

	//	LinearLayout mRoot;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_handwriting);
		//		mRoot = (LinearLayout) findViewById(R.id.root);
		txt_total = (TextView) findViewById(R.id.txt_total);
		agreeToSignPrompt = (TextView) findViewById(R.id.agreeToSignPrompt);
		drawView = (FingerDrawView) findViewById(R.id.draw_view);

		txt_total.setText(ComUtils.CURRENCY + ComUtils.SALES);
		agreeToSignPrompt.setText(String.format(
				getString(R.string.AgreeToSignPrompt), ComUtils.VISA));
		Button button;
		button = (Button) findViewById(R.id.btn_clear);
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				handleReset();
			}
		});

		button = (Button) findViewById(R.id.btn_confirm);
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// handleReco();

				Intent i = new Intent();
				VariablesComm.BITMAP_TAG = drawView.bitmap;
				HandwritingActivity.this.setResult(VariablesComm.RESULT_OK, i);
				finish();
			}
		});

		//add hand writing
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenWidthDip = dm.widthPixels;
		int screenHeightDip = dm.heightPixels;
		drawView.setBitmapSize(screenWidthDip, screenHeightDip);

		//no actionbar
//		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//				WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}

	private void handleReset() {
		drawView.resetView();
	}

}
