package com.britesky.payanywhere.ui.new_pos;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.britesky.payanywhere.R;
import com.britesky.payanywhere.ui.view.PaintView;

import java.math.BigDecimal;

public class SignatureActivity extends Activity implements View.OnClickListener {
	private Context mContext;
	private static final float T = 0.2575758F;
	private static final int s = 101;

	private ProgressDialog U;

	public String q;
	public Boolean r = Boolean.valueOf(false);
	// private Money t;
	private boolean u;

	private TextView mSubtotal;
	private TextView mDiscount;
	private TextView mTax;
	private TextView mTotal;
	private PaintView mPaintView;
	private Button BtnClear;
	private ImageButton BtnConfirm;
	private String mAmount;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		setContentView(R.layout.activity_signature);

		mSubtotal = ((TextView) findViewById(R.id.signature_subtotal));
		mDiscount = ((TextView) findViewById(R.id.signature_discount));
		mTax = ((TextView) findViewById(R.id.signature_tax));
		mTotal = ((TextView) findViewById(R.id.signature_total));
		mPaintView = ((PaintView) findViewById(R.id.signature_paint));
		BtnClear = (Button) findViewById(R.id.signature_clear);
		BtnConfirm = (ImageButton) findViewById(R.id.signature_confirm);
		BtnClear.setOnClickListener(this);
		BtnConfirm.setOnClickListener(this);

		mAmount = getIntent().getStringExtra("amount");
		mTotal.setText(mAmount);

		// add hand writing
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenWidthDip = dm.widthPixels;
		int screenHeightDip = dm.heightPixels;
		mPaintView.setBitmapSize(screenWidthDip, screenHeightDip);
		//
		// this.mSubtotal.setText(localGeneralTransaction1.getReceiptTaxAmount()
		// .toString());
		// this.mTotal.setText(localGeneralTransaction1.getReceiptTotalAmount()
		// .toString());
	}

	public void killDialog() {
		if (this.U != null)
			;
		try {
			this.U.dismiss();
			return;
		} catch (IllegalArgumentException localIllegalArgumentException) {
			while (true)
				localIllegalArgumentException.printStackTrace();
		}
	}

	protected void onActivityResult(int paramInt1, int paramInt2,
			Intent paramIntent) {

	}

	public void onBackPressed() {
	}

	public void onClick(View paramView) {
		int i = paramView.getId();
		if (i == R.id.signature_clear) {
			mPaintView.clear();
		} else if (i == R.id.signature_confirm) {
			//save signature
			mPaintView.saveMyBitmap("handwriting");
			
			Intent intent = new Intent(mContext, ReceiptActivity.class);
			intent.putExtra("amount", mAmount);
			startActivity(intent);
			SignatureActivity.this.finish();
		}

	}

	protected void onPause() {
		// killDialog();
		super.onPause();
	}

	// public boolean onTouch(View paramView, MotionEvent paramMotionEvent) {
	// return false;
	// }

	public void showDialog() {
		this.U = new ProgressDialog(this);
		this.U.setProgressStyle(0);
		this.U.setMessage("processing");
		this.U.setCancelable(false);
		this.U.show();
	}

	// public void taskComplete() {
	// killDialog();
	// finish();
	// findViewById(R.id.signature_confirm).setEnabled(true);
	// }
	//
	// public void taskStart() {
	// showDialog();
	// }
	//
	// final class iz
	// implements ViewTreeObserver.OnGlobalLayoutListener
	// {
	// iz(SignatureActivity paramSignatureActivity)
	// {
	// }
	//
	// public final void onGlobalLayout()
	// {
	// SignatureActivity.a(this.a).getLayoutParams().height = ((int)(0.2575758F
	// * SignatureActivity.a(this.a).getWidth()));
	// SignatureActivity.a(this.a).getViewTreeObserver().removeGlobalOnLayoutListener(this);
	// }
	// }
}
