package com.britesky.payanywhere.ui.new_pos;

import java.io.File;
import java.io.IOException;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.britesky.payanywhere.R;
import com.britesky.payanywhere.api.GeneralApi;
import com.britesky.payanywhere.api.Money;
import com.britesky.payanywhere.ui.config.SettingsListActivity;
import com.britesky.payanywhere.ui.util.FormatData;
import com.britesky.payanywhere.ui.util.ImageUtil;
import com.britesky.payanywhere.ui.util.PhotoType;
import com.britesky.payanywhere.ui.view.PreventCursorPositionEditText;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class ManualItemActivity extends BaseActivity {

	private File mCacheDir;
	private File mCachePhoto;
	private File mItemPhoto;
	private ImageButton mPhotoButton;
	private TextView mAddPhotoText;
	private PreventCursorPositionEditText mMoney;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manual_item);

		mPhotoButton = (ImageButton) findViewById(R.id.image);
		mAddPhotoText = (TextView) findViewById(R.id.plus_photo);
		mMoney = (PreventCursorPositionEditText) findViewById(R.id.price);

		mCacheDir = GeneralApi.getCacheDir();

		mMoney.addTextChangedListener(textWatcher);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		this.getSupportMenuInflater().inflate(R.menu.actionbar_accept, menu);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_confirm) {
			Money money = new Money(mMoney.getText().toString());

			boolean hasNoError = true;
			if (!money.isGreaterThanZero()) {
				mMoney.setError(getString(R.string.price_is_zero));
				hasNoError = false;
			} else {
				mMoney.setError(null);
			}

			if (!hasNoError) {
				return false;
			}

			Intent intent = new Intent(this, SwipeCardActivity.class);
			String amount = money.toString();
			amount = Util.removeAmountDollar(amount);
			intent.putExtra("amount", amount);
			startActivity(intent);
			ManualItemActivity.this.finish();
		}

		return super.onOptionsItemSelected(item);
	}

	public final void onAddPhotoClick(View view) {
		// ManualItemActivity.c(a).clearFocus();
		InputMethodManager inputmethodmanager = (InputMethodManager) this
				.getSystemService("input_method");
		if (inputmethodmanager.isActive()) {
			inputmethodmanager.hideSoftInputFromWindow(this.getCurrentFocus()
					.getWindowToken(), 2);
		}
		ImageUtil.launchImageChooser(this);
	}

	public Uri overwriteImageFile() {
		ImageUtil.checkFsWritable(mCacheDir);
		mItemPhoto = mCachePhoto;
		mCachePhoto = new File(mCacheDir, (new StringBuilder("item"))
				.append(System.currentTimeMillis()).append(".png").toString());
		File file = new File(mCacheDir, ".nomedia");
		try {
			mCachePhoto.createNewFile();
			if (!file.exists()) {
				file.createNewFile();
			}
		} catch (IOException ioexception) {
			ioexception.printStackTrace();
		}
		if (mCachePhoto != null) {
			return Uri.fromFile(mCachePhoto);
		} else {
			return null;
		}
	}

	public void onActivityResult(int k, int i1, Intent intent) {
		super.onActivityResult(k, i1, intent);
		ImageUtil.launcherClosed();
		if (i1 == 0) {
			if (mCachePhoto != null) {
				mCachePhoto.delete();
				mCachePhoto = mItemPhoto;
			}
		} else {
			if (k == 0) {
				if (i1 == 500) {
					ImageUtil.startGallery(this, Uri.fromFile(mCachePhoto));
					return;
				} else {
					ImageUtil.doCrop(this, Uri.fromFile(mCachePhoto),
							PhotoType.ITEM);
					return;
				}
			}
			if (k == 1) {
				if (i1 == 500) {
					ImageUtil.startCamera(this, Uri.fromFile(mCachePhoto));
					return;
				} else {
					ImageUtil.doCrop(this, Uri.fromFile(mCachePhoto),
							PhotoType.ITEM);
					return;
				}
			}
			if (mCachePhoto != null) {
				if (mItemPhoto != null) {
					mItemPhoto.delete();
					mItemPhoto = null;
				}
				ImageLoader.getInstance().displayImage(
						Uri.fromFile(mCachePhoto).toString(), mPhotoButton);
				mAddPhotoText.setVisibility(8);
				return;
			}
		}
	}

	TextWatcher textWatcher = new TextWatcher() {

		@Override
		public void afterTextChanged(Editable s) {
			String str = FormatData.formatPrice(s.toString());

			if (str != null) {
				mMoney.removeTextChangedListener(this);
				mMoney.setText(str);
				mMoney.setSelection(str.length());
				mMoney.addTextChangedListener(textWatcher);
			}
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {

		}
	};
}
