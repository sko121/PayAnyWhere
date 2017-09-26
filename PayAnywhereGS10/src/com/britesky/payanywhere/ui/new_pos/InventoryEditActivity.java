// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.britesky.payanywhere.ui.new_pos;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.util.Rfc822Tokenizer;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

//import de.ankri.views.Switch;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.britesky.payanywhere.R;
import com.britesky.payanywhere.api.Category;
import com.britesky.payanywhere.api.GeneralApi;
import com.britesky.payanywhere.api.InventoryApi;
import com.britesky.payanywhere.api.InventoryItem;
import com.britesky.payanywhere.api.Money;
import com.britesky.payanywhere.ui.util.FormatData;
import com.britesky.payanywhere.ui.util.ImageUtil;
import com.britesky.payanywhere.ui.util.PhotoType;
//import com.britesky.payanywhere.ui.view.PreventCursorPositionEditText;
import com.nostra13.universalimageloader.core.ImageLoader;

// Referenced classes of package com.nabancard.ui.new_pos:
//            BaseActivity, cx, cy, cz, 
//            dd, InventoryActivity, dg, dc, 
//            cw, em, da, db, 
//            df

public class InventoryEditActivity extends BaseActivity {

	private static final int ai = 0;
	private static final int aj = 1;
	public static InventoryItem mInventoryItem;
	public static boolean r = false;
	private static final String s = "filePath";
	private static final String t = "isDialogShowing";
	private EditText mDesc;
	private EditText mReceiptMsg;
	private EditText U;
	private AutoCompleteTextView V;
	private EditText W;
	private Switch X;
	private Switch Y;
	private File mInventoryImage;
	private File aa;
	private File ab;
	private String ac;
	private InventoryItem ad;
	private android.view.View.OnClickListener ae;
	private TextView af;
	private ProgressDialog ag;
	private boolean ah;
	private int ak;
	private ImageButton u;
	private EditText mMoney;
	private EditText mItemName;

	public InventoryEditActivity() {
		ag = null;
		ah = false;
		ak = 0;
	}

	// static PreventCursorPositionEditText a(InventoryEditActivity
	// inventoryeditactivity)
	// {
	// return inventoryeditactivity.mMoney;
	// }

	// private void a()
	// {
	// ae = new cx(this);
	// }

	// private static void a(Object obj)
	// {
	// e e1 = new e();
	// a aa1[] = new a[1];
	// aa1[0] = com.d.a.v.ofFloat(obj, "rotationY", new float[] {
	// 0.0F, -3F, 0.0F, 3F, 0.0F, -3F, 0.0F, 3F, 0.0F
	// });
	// e1.playTogether(aa1);
	// e1.setDuration(500L).start();
	// }

	// private boolean a(ArrayList arraylist)
	// {
	// ArrayList arraylist1 = ad.getTags();
	// if (arraylist.size() == arraylist1.size()) goto _L2; else goto _L1
	// _L1:
	// return false;
	// _L2:
	// int k = 0;
	// label0:
	// do
	// {
	// label1:
	// {
	// if (k >= arraylist1.size())
	// {
	// break label1;
	// }
	// if
	// (!((Tag)arraylist.get(k)).getName().equals(((Tag)arraylist1.get(k)).getName()))
	// {
	// break label0;
	// }
	// k++;
	// }
	// } while (true);
	// if (true) goto _L1; else goto _L3
	// _L3:
	// return true;
	// }

	static EditText b(InventoryEditActivity inventoryeditactivity) {
		return inventoryeditactivity.mItemName;
	}

	static File c(InventoryEditActivity inventoryeditactivity) {
		return inventoryeditactivity.mInventoryImage;
	}

	private void loadValues() {
		u = (ImageButton) findViewById(R.id.image);
		af = (TextView) findViewById(R.id.plus_photo);
		mMoney = (EditText) findViewById(R.id.price);
		u.setOnClickListener(ae);
		// af.setOnClickListener(ae);
		// mMoney.addTextChangedListener(new cy(this));
		mItemName = (EditText) findViewById(R.id.itemName);
		// w.setOnEditorActionListener(new al(this, p.description));
		// w.addTextChangedListener(new cz(this));
		mDesc = (EditText) findViewById(R.id.description);
		mReceiptMsg = (EditText) findViewById(R.id.receiptMessage);
		U = (EditText) findViewById(R.id.quantity);
		// U.setOnEditorActionListener(new al(this, p.category));
		V = (AutoCompleteTextView) findViewById(R.id.category);
		// V.setOnEditorActionListener(new al(this, p.tags));
		// V.setAdapter(new dd(this, InventoryApi.getCategories(), (byte)0));
		V.setOverScrollMode(2);
		V.setThreshold(0);
		// X = (Switch)findViewById(R.id.taxable);
		// Y = (Switch)findViewById(R.id.addInventory);
		W = (EditText) findViewById(R.id.tags);
		// W.setTokenizer(new Rfc822Tokenizer());
		// W.setImeOptions(6);
		// W.setChipType(com.nabancard.ui.view.chips.l.b);
		U.setEnabled(false);
		U.setFocusable(false);
		// Y.setEnabled(false);
		// Y.setChecked(true);
		if (mInventoryItem != null && ad == null) {
			ad = new InventoryItem(mInventoryItem);
			String s1 = mInventoryItem.getImagePath();
			if (s1 != null) {
				Iterator iterator;
				// SaleTransactionItem saletransactionitem;
				if (s1.contains("nab://")) {
					mInventoryImage = new File(s1);
				} else if (s1.startsWith("/")) {
					// mInventoryImage = new File((new
					// StringBuilder("file://")).append(s1).toString());
					mInventoryImage = new File(s1);
					ImageLoader.getInstance().displayImage(
							Uri.fromFile(mInventoryImage).toString(), u); // ,
																			// ax.getDisplayOptions().extraForDownloader(az.a).build());
				} else if (s1.contains("assets:")) {
					mInventoryImage = new File(s1); // .replace("assets://",
													// "file://android_asset/"));
					ImageLoader.getInstance().displayImage(s1, u); // ,
																	// ax.getDisplayOptions().extraForDownloader(az.a).build());
				} else {
					mInventoryImage = new File(s1);
				}
				ac = mInventoryImage.getPath();
			}
			// ImageLoader.getInstance().displayImage(Uri.fromFile(mInventoryImage).toString(),
			// u); //, ax.getDisplayOptions().extraForDownloader(az.a).build());
			af.setVisibility(8);
			mItemName.setText(mInventoryItem.getName());
			mDesc.setText(mInventoryItem.getDescription());
			mReceiptMsg.setText(mInventoryItem.getReceiptMessage());
			// iterator = CartApi.getCartItems().iterator();
			// do
			// {
			// if (!iterator.hasNext())
			// {
			// break;
			// }
			// saletransactionitem = (SaleTransactionItem)iterator.next();
			// if (mInventoryItem.getPk() ==
			// saletransactionitem.getInventoryItem().getPk())
			// {
			// U.setEnabled(true);
			// U.setFocusable(true);
			// U.setText(Integer.toString(saletransactionitem.getQuantity()));
			// }
			// } while (true);
			if (!U.isEnabled()) {
				U.setHint(getString(R.string.disabled));
			}
			if (mInventoryItem.getCategory() != null) {
				V.setText(mInventoryItem.getCategory().getName());
			}
			mMoney.setText(mInventoryItem.getPrice().toString());
			// X.setChecked(mInventoryItem.getIsTaxable());
			// mInventoryItem.getTags();
			// Iterator iterator1 = mInventoryItem.getTags().iterator();
			// do
			// {
			// if (!iterator1.hasNext())
			// {
			// break;
			// }
			// Tag tag = (Tag)iterator1.next();
			// if (tag.getName() != null)
			// {
			// W.append((new
			// StringBuilder()).append(tag.getName()).append(",").toString());
			// }
			// } while (true);
		}
		mMoney.addTextChangedListener(textWatcher);
	}

	static boolean d(InventoryEditActivity inventoryeditactivity) {
		return inventoryeditactivity.g();
	}

	// private boolean e()
	// {
	// ArrayList arraylist;
	// List list1;
	// Iterator iterator;
	// List list = Arrays.asList(W.getText().toString().split("\\s*,\\s*"));
	// arraylist = new ArrayList();
	// list1 = InventoryApi.getTags();
	// iterator = list.iterator();
	// _L2:
	// Tag tag;
	// if (!iterator.hasNext())
	// {
	// break; /* Loop/switch isn't completed */
	// }
	// String s2 = ((String)iterator.next()).replaceAll("^<|>$", "");
	// if (s2.trim().isEmpty())
	// {
	// continue; /* Loop/switch isn't completed */
	// }
	// Iterator iterator1 = list1.iterator();
	// do
	// {
	// if (!iterator1.hasNext())
	// {
	// break MISSING_BLOCK_LABEL_435;
	// }
	// tag = (Tag)iterator1.next();
	// } while (!tag.getName().equalsIgnoreCase(s2));
	// _L3:
	// if (tag != null)
	// {
	// arraylist.add(tag);
	// } else
	// {
	// return true;
	// }
	// if (true) goto _L2; else goto _L1
	// _L1:
	// label0:
	// {
	// label1:
	// {
	// if (!r)
	// {
	// break label0;
	// }
	// if (w.getText().toString().equals(ad.getName()) &&
	// mDesc.getText().toString().equals(ad.getDescription()) &&
	// mReceiptMsg.getText().toString().equals(ad.getReceiptMessage()))
	// {
	// Category category = ad.getCategory();
	// String s1 = V.getText().toString();
	// boolean flag;
	// if (category == null || TextUtils.isEmpty(category.getName()))
	// {
	// flag = s1.isEmpty();
	// } else
	// {
	// flag = s1.equals(category.getName());
	// }
	// if (flag && (new
	// Money(mMoney.getText().toString())).isEqualTo(ad.getPrice()) && !g() &&
	// a(arraylist))
	// {
	// break label1;
	// }
	// }
	// return true;
	// }
	// return false;
	// }
	// return !w.getText().toString().isEmpty() ||
	// !mDesc.getText().toString().isEmpty() ||
	// !mReceiptMsg.getText().toString().isEmpty() ||
	// !V.getText().toString().isEmpty() || (new
	// Money(mMoney.getText().toString())).isGreaterThanZero() ||
	// !arraylist.isEmpty() || mInventoryImage != null;
	// tag = null;
	// goto _L3
	// }

	private boolean f() {
		Category category = ad.getCategory();
		String s1 = V.getText().toString();
		if (category == null || TextUtils.isEmpty(category.getName())) {
			return s1.isEmpty();
		} else {
			return s1.equals(category.getName());
		}
	}

	private boolean g() {
		String s1;
		if (mInventoryImage != null) {
			s1 = mInventoryImage.getPath();
		} else {
			s1 = null;
		}
		return s1 != ac;
	}

	private void h() {
		ag = new ProgressDialog(this);
		ag.setProgressStyle(0);
		ag.setMessage(getString(R.string.uploading_changes));
		ag.setCancelable(false);
		ag.show();
	}

	private void i() {
		if (ag == null) {
			// break MISSING_BLOCK_LABEL_14;
		}
		ag.dismiss();
		// return;
		// IllegalArgumentException illegalargumentexception;
		// illegalargumentexception;
		// illegalargumentexception.printStackTrace();
		return;
	}

	public void handleResponse(Object obj) {
		Vector vector = (Vector) obj;
		boolean flag = ((Boolean) vector.get(0)).booleanValue();
		// NabException nabexception = (NabException)vector.get(1);
		// if (nabexception != null)
		// {
		// x.showUserExceptionDialog(this, nabexception.getMessage());
		// }
		if (flag) {
			String s1 = mInventoryItem.getImagePath();
			if (s1 != null && s1.startsWith("file:/")) {
				(new File(s1.replaceFirst("^file:/+", "/"))).delete();
			}
			// InventoryApi.deleteItem(mInventoryItem);
			// InventoryActivity.refreshOnFocus();
			finish();
		}
	}

	public void negativeResults() {
		// (new dg(this)).show(getSupportFragmentManager(), "uploadingError");
	}

	public void noResults() {
	}

	public void onActivityResult(int k, int i1, Intent intent) {
		super.onActivityResult(k, i1, intent);
		ImageUtil.launcherClosed();
		if (i1 == 0) {
			if (mInventoryImage != null) {
				mInventoryImage.delete();
				mInventoryImage = ab;
			}
		} else {
			if (k == 0) {
				if (i1 == 500) {
					ImageUtil.startGallery(this, Uri.fromFile(mInventoryImage));
					return;
				} else {
					ImageUtil.doCrop(this, Uri.fromFile(mInventoryImage),
							PhotoType.ITEM);
					return;
				}
			}
			if (k == 1) {
				if (i1 == 500) {
					ImageUtil.startCamera(this, Uri.fromFile(mInventoryImage));
					return;
				} else {
					ImageUtil.doCrop(this, Uri.fromFile(mInventoryImage),
							PhotoType.ITEM);
					return;
				}
			}
			if (mInventoryImage != null) {
				if (ab != null) {
					ab.delete();
					ab = null;
				}
				ImageLoader.getInstance().displayImage(
						Uri.fromFile(mInventoryImage).toString(), u);
				af.setVisibility(8);
				return;
			}
		}
	}

	// public void onBackPressed()
	// {
	// am.launcherClosed();
	// if (e())
	// {
	// android.app.AlertDialog.Builder builder = new
	// android.app.AlertDialog.Builder(this);
	// builder.setTitle(com.nabancard.ui.v.unsaved_data).setMessage(com.nabancard.ui.v.are_you_sure).setCancelable(false).setPositiveButton(com.nabancard.ui.v.yes,
	// new dc(this)).setNegativeButton(com.nabancard.ui.v.no, null);
	// builder.create().show();
	// return;
	// } else
	// {
	// super.onBackPressed();
	// return;
	// }
	// }

	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		// if (GeneralApi.getLoggedInMerchant().getRole() ==
		// com.nabancard.api.MerchantAccount.eUserRole.ROLE_SUB)
		// {
		// (new
		// android.app.AlertDialog.Builder(this)).setTitle(com.nabancard.ui.v.access_restricted).setCancelable(false).setPositiveButton(com.nabancard.ui.v.ok,
		// new cw(this)).show();
		// }
		// if (!bj.isTablet(this))
		// {
		// setRequestedOrientation(1);
		// }
		aa = GeneralApi.getCacheDir();
		setContentView(R.layout.activity_inventoryedit);
		// setActionBar(com.nabancard.ui.v.add_item, false);
		// ae = new cx(this);
		loadValues();
		if (bundle != null) {
			ah = bundle.getBoolean("isDialogShowing");
			if (bundle.containsKey("filePath")) {
				mInventoryImage = new File(bundle.getString("filePath"));
				if (mInventoryImage != null) {
					af.setVisibility(8);
					ImageLoader.getInstance().displayImage(
							Uri.fromFile(mInventoryImage).toString(), u);
				}
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		this.getSupportMenuInflater().inflate(R.menu.actionbar_accept, menu);

		return super.onCreateOptionsMenu(menu);
	}

	protected void onDestroy() {
		mInventoryItem = null;
		super.onDestroy();
	}

	public void onItemClick(AdapterView adapterview, View view, int k, long l1) {
		if (view.getTag() == null) {
			return;
		}
		// if ((at)view.getTag() == F)
		// {
		// setIsDirty(e());
		// }
		super.onItemClick(adapterview, view, k, l1);
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

			if (mItemName.getText() == null
					|| mItemName.getText().toString().isEmpty()) {
				mItemName.setError(getString(R.string.name_is_empty));
				hasNoError = false;
			} else {
				mItemName.setError(null);
			}

			if (!hasNoError) {
				return false;
			}

			if (mInventoryItem != null) {
				mInventoryItem.setName(mItemName.getText().toString());
				mInventoryItem.setDescription(mDesc.getText().toString());
				mInventoryItem.setReceiptMessage(mReceiptMsg.getText()
						.toString());
				mInventoryItem.setPrice(new Money(mMoney.getText().toString()));
				// mInventoryItem.setIsTaxable(X.isChecked());
			} else {
				String imagePath = "";
				if (mInventoryImage != null) {
					imagePath = mInventoryImage.getPath().toString();
				}
				mInventoryItem = InventoryApi.newInventoryItem(mItemName
						.getText().toString(), mDesc.getText().toString(),
						mReceiptMsg.getText().toString(), money, false,
						imagePath, null, null);
			}
			InventoryApi.commit(mInventoryItem);

			InventoryActivity.refreshOnFocus();
			this.finish();
		}

		return super.onOptionsItemSelected(item);
	}

	// protected void onPause()
	// {
	// i();
	// super.onPause();
	// }

	public void onPostCreate(Bundle bundle) {
		super.onPostCreate(bundle);
		setUpSlidingMenuBaseItems(true, true);
	}

	// protected void onResume()
	// {
	// super.onResume();
	// if (ah)
	// {
	// h();
	// if (ak == 0)
	// {
	// com.nabancard.ui.a.i.setCallBackListener(this);
	// } else
	// if (ak == 1)
	// {
	// df.setCallBackListener(this);
	// return;
	// }
	// }
	// }

	public void onSaveInstanceState(Bundle bundle) {
		if (mInventoryImage != null && mInventoryImage.getPath() != null) {
			bundle.putString("filePath", mInventoryImage.getPath());
		}
		bundle.putBoolean("isDialogShowing", ah);
		super.onSaveInstanceState(bundle);
	}

	public Uri overwriteImageFile() {
		ImageUtil.checkFsWritable(aa);
		ab = mInventoryImage;
		mInventoryImage = new File(aa, (new StringBuilder("item"))
				.append(System.currentTimeMillis()).append(".png").toString());
		File file = new File(aa, ".nomedia");
		try {
			mInventoryImage.createNewFile();
			if (!file.exists()) {
				file.createNewFile();
			}
		} catch (IOException ioexception) {
			ioexception.printStackTrace();
		}
		if (mInventoryImage != null) {
			return Uri.fromFile(mInventoryImage);
		} else {
			return null;
		}
	}

	public void positiveResults() {
		finish();
	}

	public void taskComplete() {
		i();
		ah = false;
	}

	public void taskStart() {
		ah = true;
		h();
	}

	public final void onClickPlusPhoto(View view) {
		// InventoryEditActivity.a(a).clearFocus();
		InputMethodManager inputmethodmanager = (InputMethodManager) this
				.getSystemService("input_method");
		if (inputmethodmanager.isActive()) {
			inputmethodmanager.hideSoftInputFromWindow(this.getCurrentFocus()
					.getWindowToken(), 1);
		}
		ImageUtil.launchImageChooser(this);
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
