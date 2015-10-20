package com.britesky.payanywhere.ui.new_pos;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.britesky.payanywhere.R;
import com.britesky.payanywhere.api.CartApi;
import com.britesky.payanywhere.api.Money;
import com.britesky.payanywhere.api.SaleTransactionItem;
import com.britesky.payanywhere.ui.pic.DeviceListActivity;
import com.britesky.payanywhere.ui.pic.PicFromPrintUtils;
import com.britesky.payanywhere.ui.pic.PrintUtil;
import com.britesky.payanywhere.ui.util.ImageUtil;
import com.britesky.payanywhere.ui.util.Mail;
import com.britesky.payanywhere.ui.util.SystemUtil;

//import com.britesky.ipos.R;

public class ReceiptActivity extends Activity {
	
	public static  ReceiptActivity instance;   
	
	public static boolean emailSendOK = true;
	public static boolean printSendOK = true;
	
	private Context mContext;

	static public String MaskedPAN = "235755******6698";
	
	private Spinner mSpinner = null;
	private List<String> mpairedDeviceList = new ArrayList<String>();
	private ArrayAdapter<String> mArrayAdapter;

	private BluetoothAdapter mBluetoothAdapter = null; // 创建蓝牙适配器
	private BluetoothDevice mBluetoothDevice = null;
	private BluetoothSocket mBluetoothSocket = null;
	OutputStream mOutputStream = null;
	/*
	 * Hint: If you are connecting to a Bluetooth serial board then try using
	 * the well-known SPP UUID 00001101-0000-1000-8000-00805F9B34FB. However if
	 * you are connecting to an Android peer then please generate your own
	 * unique UUID.
	 */
	private final static int REQUEST_CONNECT_DEVICE = 1; // 宏定义查询设备句柄
	private static final UUID SPP_UUID = UUID
			.fromString("00001101-0000-1000-8000-00805F9B34FB");

	private Builder dialog = null;

	Set<BluetoothDevice> pairedDevices = null;

	private CheckBox mCBEnableEmailing;
	private View mBubble;
	private TextView mEmailNum;
	private AutoCompleteTextView mAutoEmail;
	// private Button mAddEmail;
	private TextView mPrintLabel;
	private TextView mEmailLabel;
	private CheckBox mCBEnablePrint;
	private TextView mDate;
	private TextView mInvoiceNum;
	private EditText mFirstName;
	private EditText mLastName;
	private TextView mTotalAmount;
	private Button mAddEmail;
	private Button mBtnSend;
	private Button mBtnNoSend;

	// private Button mBtnPrint;
	private TextView mPrintStatus;
	private String mprintfData = "";
	private String mLastSelectedPrint;
	private String mAmount;
	private boolean mSelectPrint = false;
	private int mResendMailTime = 0;

	private LinearLayout linear_email,linear_printer;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mContext = this;
		instance = this;
		setContentView(R.layout.receipt_screen);

		linear_email=(LinearLayout) findViewById(R.id.linear_email);
		linear_printer=(LinearLayout) findViewById(R.id.linear_printer);
		
		mSpinner = (Spinner) findViewById(R.id.deviceSpinner);
		mPrintStatus = (TextView) findViewById(R.id.textViewPrintStatus);

		mCBEnableEmailing = (CheckBox) findViewById(R.id.checkBoxEnableEmailing);
		mBubble = (View) findViewById(R.id.emailNumberBubbleContainer);
		mEmailNum = (TextView) findViewById(R.id.textViewEmailNumber);
		mAutoEmail = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextViewEmail);
		mAddEmail = (Button) findViewById(R.id.buttonAddAdditionalEmails);
		mPrintLabel = (TextView) findViewById(R.id.textViewPrintLable);
		mEmailLabel = (TextView) findViewById(R.id.textViewEmailLable);
		mCBEnablePrint = (CheckBox) findViewById(R.id.checkBoxStarPrinters);
		mDate = (TextView) findViewById(R.id.textViewDate);
		mInvoiceNum = (TextView) findViewById(R.id.textViewInvoice);
		mFirstName = (EditText) findViewById(R.id.editTextFirstName);
		mLastName = (EditText) findViewById(R.id.editTextLastName);
		mTotalAmount = (TextView) findViewById(R.id.textViewTotalAmount);
		mBtnSend = (Button) findViewById(R.id.buttonSendReceipt);
		mBtnNoSend = (Button) findViewById(R.id.buttonNoReceipt);

		Typeface typeface = Typeface.createFromAsset(getAssets(),
				"Boton-MediumItalic.ttf");
		mBtnSend.setTypeface(typeface);
		mBtnNoSend.setTypeface(typeface);
		ImageUtil.applyBrandColor(this, mBtnNoSend.getBackground());

		mDate.setText(getCurrentDate());
		mInvoiceNum.setText("1234");
		mAmount = getIntent().getStringExtra("amount");
		mTotalAmount.setText(mAmount);

		mprintfData = "\n----------------------------------------------\n"
					+ "RECEIPT NO.                       523458706\n" 
					+ "DATE                       "+ getCurrentTime() + "\n" 
					+ "APPROVAL_CODE                     234798234\n"
					+ "APPROVAL LOCATION                     China\n";
		//磁卡卡号
		if(MaskedPAN.length()==16)
		{
			mprintfData +=  "PAN:                       "+MaskedPAN+"\n";
	    //IC卡号
		}else{
			mprintfData +=  "PAN:                   "+MaskedPAN+"\n";
		}
		
					
		mprintfData += "SALES                                 abner\n"
					+ String.format(
					  "TOTAL                                   %03d\n",
					  CartApi.getTotalItemCount());
		
		mprintfData += getCartInf();
		
		System.out.println(" ++++++recepit data = \n" + mprintfData);
		init();

	}
	
	public String getCartInf()
	{
		List<SaleTransactionItem> CartItems = CartApi.getCartItems();
		
		StringBuilder sb=new StringBuilder();
		if(CartItems != null)
		{

			sb.append(String.format("\n%s       %s    %s    %s\n",
								    "Bar Code",
								    "Quantity",
								    "Price",
								    "Amount"));
			
			sb.append("----------------------------------------------\n");
			sb.append("GOODS:\n");
			
			for(int i=0;i<CartItems.size();i++)
			{
				SaleTransactionItem item = CartItems.get(i); 
				String name = item.getName();
				int number = item.getQuantity();
				Money price = item.getPrice();
				Money totalPrice = price.multiply(price,number);
				long serial = item.getPk();
				sb.append(i+1+":"+name+"\n");
				sb.append(String.format("%08d       %03d         %s    %s",
										serial,
										number,
										price.toString(),
										totalPrice));
				
				sb.append("\n");
			}
		}
		
	    
		//25    //"DiscountAmount:      "
		sb.append("\n----------------------------------------------\n");
		sb.append(String.format("%s\t%s","Total Item Count:    ",CartApi.getTotalItemCount()+"\n"));
		sb.append(String.format("%s\t%s","Subtotal:            ",CartApi.getSubtotal().toString()+"\n"));
		sb.append(String.format("%s\t%s","Total:               ",CartApi.getTotal().toString()+"\n"));
		sb.append(String.format("%s\t%s","Discount Amount:     ",CartApi.getDiscountAmount().toString()+"\n"));
	    
		sb.append("----------------------------------------------\n");
	    return sb.toString();
	}
	
	

	@Override
	public void onPause() {
		// killDialog();
		super.onPause();
//		TRACE.d("onPause  -----close OutputStream()");
//		
//		if (mOutputStream != null) {
//			try {
//				mOutputStream.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		if (mBluetoothSocket != null) {
//			try {
//				mBluetoothSocket.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
	}
	

	@Override
	public void onDestroy() {
		super.onDestroy();
		CartApi.resetCart();
		CartApi.init();
		
		TRACE.d("onDestroy  -----close OutputStream()");
		
		if (mOutputStream != null) {
			try {
				mOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (mBluetoothSocket != null) {
			try {
				mBluetoothSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

	//
	// public void onBackPressed() {
	// }

	private void init() {

		mCBEnableEmailing.setOnCheckedChangeListener(mCheckboxListener);
		mCBEnablePrint.setOnCheckedChangeListener(mCheckboxListener);

		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

		mAddEmail.setOnClickListener(mClickListener);
		mBtnNoSend.setOnClickListener(mClickListener);
		mBtnSend.setOnClickListener(mClickListener);
		mAutoEmail.addTextChangedListener(mAutoEmailListener);
		
		mEmailLabel.setOnClickListener(mClickListener);
		mPrintLabel.setOnClickListener(mClickListener);
		
		setEmailColor(false);
		setPrintColor(false);

		mpairedDeviceList.add(this.getString(R.string.pls_choice_device));
		mArrayAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_dropdown_item,
				mpairedDeviceList);
		mSpinner.setAdapter(mArrayAdapter);
		mSpinner.setOnTouchListener(new Spinner.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction() != MotionEvent.ACTION_UP) {
					return false;
				}
				
				try {
					if (mBluetoothAdapter == null) {
						

						// mTipTextView.setText(getString(R.string.not_bluetooth_adapter));
						Toast.makeText(mContext,
								R.string.not_bluetooth_adapter,
								Toast.LENGTH_LONG).show();

					} else if (mBluetoothAdapter.isEnabled()) {
						
						
						
							TRACE.d("start.......");
							connectBluetooth();
						
						
						
//							String getName = mBluetoothAdapter.getName();
//							pairedDevices = mBluetoothAdapter.getBondedDevices();
//							while (mpairedDeviceList.size() > 1) {
//								mpairedDeviceList.remove(1);
//							}
//							if (pairedDevices.size() == 0) {
//								Toast.makeText(mContext, "No paired device.",
//										Toast.LENGTH_LONG).show();
//							}
//							for (BluetoothDevice device : pairedDevices) {
//								// Add the name and address to an array adapter to
//								// show in a ListView
//								getName = device.getName() + "#"
//										+ device.getAddress();
//								mpairedDeviceList.add(getName);
//							}

					} else {
						Toast.makeText(mContext,
								"BluetoothAdapter not open...",
								Toast.LENGTH_LONG).show();
					}
				} catch (Exception e) {
					e.printStackTrace();
					Toast.makeText(mContext, e.toString(), Toast.LENGTH_LONG)
							.show();
				}
				return false;
			}
		});

		mSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				//sendMsg(1001);
				
				System.out.println("---onItemSelected\n");
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				System.out.println("---onNothingSelected\n");
			}

		});

	}

	private TextWatcher mAutoEmailListener = new TextWatcher() {

		public final void afterTextChanged(Editable paramEditable) {
		}

		public final void beforeTextChanged(CharSequence paramCharSequence,
				int paramInt1, int paramInt2, int paramInt3) {
		}

		public final void onTextChanged(CharSequence paramCharSequence,
				int paramInt1, int paramInt2, int paramInt3) {
			if (validateEmail(mAutoEmail)) {
				mCBEnableEmailing.setChecked(true);
				mBtnSend.setEnabled(true);
				mBtnSend.setText(R.string.send_receipt);
				setEmailColor(true);

				// mAddEmail.setEnabled(true);
			} else {
				mCBEnableEmailing.setChecked(false);
				setEmailColor(false);
				if (mCBEnablePrint.isChecked() == false) {
					mBtnSend.setEnabled(false);
				} else {
					mBtnSend.setText(R.string.print_receipt);
				}

				// mAddEmail.setEnabled(false);
			}
		}
	};

	private CheckBox.OnCheckedChangeListener mCheckboxListener = new CheckBox.OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			switch (buttonView.getId()) {
			case R.id.checkBoxEnableEmailing:
				
				
				
				if (isChecked) {
					mBtnSend.setEnabled(true);
					mBtnSend.setText(R.string.send_receipt);
					setEmailColor(true);
				} else {
					if (mCBEnablePrint.isChecked() == false) {
						mBtnSend.setEnabled(false);
					} else {
						mBtnSend.setText(R.string.print_receipt);
					}
					setEmailColor(false);
				}
				break;

			case R.id.checkBoxStarPrinters:
				if (isChecked) {
					mBtnSend.setEnabled(true);
					if (mCBEnableEmailing.isChecked() == false) {
						mBtnSend.setText(R.string.print_receipt);
					}
					setPrintColor(true);
				} else {
					if (mCBEnableEmailing.isChecked() == false) {
						mBtnSend.setEnabled(false);
					} else {
						mBtnSend.setText(R.string.send_receipt);
					}
					setPrintColor(false);

				}
				break;

			default:
				break;
			}
		}

	};

	public boolean validateEmail(AutoCompleteTextView view) {
		boolean bool = true;
		if ((view.getText().toString() == null)
				|| (view.getText().toString().isEmpty())) {
			// view.setError(null);
			bool = false;
		} else if ((view.getText().toString() != null)
				&& (!Patterns.EMAIL_ADDRESS.matcher(view.getText()).matches())) {
			// view.setError(getString(R.string.invalid_email));
			bool = false;
		}
		return bool;
	}
	public SendMailThread thread;
	private View.OnClickListener mClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {

			switch (v.getId()) {

			case R.id.buttonSendReceipt: {
				if (mCBEnableEmailing.isChecked() == true) {// send email
					// sendMail();
					if(validateEmail(mAutoEmail))
					{
						thread = new SendMailThread();
						emailSendOK = false;
						thread.doStart();
					}
					else
					{
						Toast.makeText(getApplicationContext(),"email invalid", Toast.LENGTH_SHORT).show();
					}
				} 
				if (mCBEnablePrint.isChecked() == true) {// print receipt
					printSendOK = false;
//					if (mSelectPrint == false) {
//						Toast.makeText(mContext,
//								"Please select a valid Printer first.",
//								Toast.LENGTH_LONG).show();
//						return;
//					}

					TRACE.d("getOutputStream()........");
					
					try {
						
					  /*
					   *   BitmapDrawable drawable =  (BitmapDrawable) getResources().getDrawable(R.drawable.test2);
						Bitmap bitmap = drawable.getBitmap();
						Bitmap compressPic = PicFromPrintUtils.compressPic(bitmap);
		          
						mOutputStream = mBluetoothSocket.getOutputStream();
						
						mOutputStream.write("test!".getBytes("GBK"));
						mOutputStream.write(new byte[] { 0x0a});
						mOutputStream.flush();
						
						final byte[] bytes = PicFromPrintUtils.draw2PxPoint(compressPic);
		                try {
		                	PrintUtil.printBytes(bytes,mOutputStream);
		                	//PrintUtil.printBytes(PrintUtil.CutPaper(),mOutputStream);
		                    //PrintUtil.printAlipayTitle(bytes, "11.58",mOutputStream);
			            } catch (Exception e) {
			                    e.printStackTrace();
			            }
//		                PrintUtil.printLogo(getApplicationContext(),mOutputStream);
	                
		                mOutputStream.write(new byte[] { 0x0a});
						mOutputStream.flush();
					*/
						
						
						mOutputStream = mBluetoothSocket.getOutputStream();
						
						mOutputStream.write(mprintfData.getBytes("GBK"));
						mOutputStream.flush();

						
						mOutputStream.write(new byte[] { 0x0a, 0x0a, 0x1d,
								0x56, 0x31 });
						mOutputStream.flush();
						Toast.makeText(mContext, "Data sent successfully...",
								Toast.LENGTH_LONG).show();
					} catch (Exception e) {
						e.printStackTrace();
						Toast.makeText(mContext,
								"Print fail. " + e.getMessage(),
								Toast.LENGTH_LONG).show();
					}
					printSendOK = true;
					
					Message msg = Message.obtain();
					msg.what = 1006;
					mHandler.sendMessage(msg);
					
				}
				
				
			}
				break;

			case R.id.buttonNoReceipt: {
//				CartApi.resetCart();
//				CartApi.init();
				ReceiptActivity.this.finish();
			}
				break;
			case R.id.textViewEmailLable:
			{
				if(mCBEnableEmailing.isChecked())
				{
					mCBEnableEmailing.setChecked(false);
				}
				else
				{
					mCBEnableEmailing.setChecked(true);
				}
			}
				break;
			case R.id.textViewPrintLable:
			{
				if(mCBEnablePrint.isChecked())
				{
					mCBEnablePrint.setChecked(false);
				}
				else
				{
					mCBEnablePrint.setChecked(true);
				}
				
			}
				break;
			default:
				break;
			}

		}
	};

	private void setPrintColor(boolean b) {
		if (b) {
			mPrintLabel.setTextColor(SystemUtil
					.getBrandColor(getApplicationContext()));
			ImageUtil.applyBrandColor(getApplicationContext(),
					mPrintLabel.getCompoundDrawables()[1]);
		} else {
			mPrintLabel.setTextColor(-3355444);
			mPrintLabel.getCompoundDrawables()[1].setColorFilter(-3355444,
					android.graphics.PorterDuff.Mode.MULTIPLY);
		}
	}

	private void setEmailColor(boolean b) {
		if (b) {
			mEmailLabel.setTextColor(SystemUtil
					.getBrandColor(getApplicationContext()));
			ImageUtil.applyBrandColor(getApplicationContext(),
					mEmailLabel.getCompoundDrawables()[1]);
		} else {
			mEmailLabel.setTextColor(-3355444);
			mEmailLabel.getCompoundDrawables()[1].setColorFilter(-3355444,
					android.graphics.PorterDuff.Mode.MULTIPLY);
		}
	}

	private void sendMail() {
		String to = mAutoEmail.getText().toString();
		if (validateEmail(mAutoEmail) == false) {
			sendMsg(1002, "Please input valid email address first.");
			return;
		}

		Mail m = new Mail("smartpos@sina.com", "thtfit2015");

		String[] toArr = { to };
		m.setTo(toArr);
		m.setFrom("smartpos@sina.com");
		m.setSubject(this.getString(R.string.email_subject));
		m.setBody("Dear " + mLastName.getText().toString() + "."
				+ mFirstName.getText().toString() + ":\n"
				+ "This is your receipt.\n" + mprintfData
				+ "Best Regards,\n POS");

		try {
			m.addAttachment(mContext.getFilesDir() + "/handwriting.png");
			if (m.send()) {
				sendMsg(1002, getString(R.string.email_success));
			} else {
				sendMsg(1005, getString(R.string.email_failure));
			}
			
		} catch (Exception e) {
			sendMsg(1005, getString(R.string.email_failure));
			e.printStackTrace();

		}

	}

	private void freshPrintStatus() {
		String temString = (String) mSpinner.getSelectedItem();
		if (mSpinner.getSelectedItemId() != 0) {

			temString = temString.substring(temString.length() - 17);

			// close last one
			if (!mLastSelectedPrint.equals(temString) && mOutputStream != null) {
				try {
					mOutputStream.close();
					mBluetoothSocket.close();

				} catch (Exception e) {
					sendMsg(1002, e.toString());
				}
				sendMsg(1003, getString(R.string.disconnect));
				// sendMsg(1004, false);
				mSelectPrint = false;
				mOutputStream = null;
				mBluetoothSocket = null;
			}

			try {
				sendMsg(1003, getString(R.string.connecting));
				mBluetoothDevice = mBluetoothAdapter.getRemoteDevice(temString);
				mBluetoothSocket = mBluetoothDevice
						.createRfcommSocketToServiceRecord(SPP_UUID);
				mBluetoothSocket.connect();
				sendMsg(1003, getString(R.string.connected));
				// sendMsg(1004, true);
				mSelectPrint = true;

			} catch (Exception e) {
				sendMsg(1003, getString(R.string.disconnect));
				// sendMsg(1004, false);
				mSelectPrint = false;
				sendMsg(1002, e.toString()
						+ "\nPlease select other bluetooth device!");
			}

		} else {
			// sendMsg(1004, false);
			mSelectPrint = false;
			sendMsg(1002, "Pls select a bluetooth device...");
		}

		mLastSelectedPrint = temString;
	}

	private void sendMsg(int what) {
		Message msg = new Message();
		msg.what = what;
		mHandler.sendMessage(msg);
	}

	private void sendMsg(int what, String str) {
		Message msg = Message.obtain();
		msg.what = what;
		msg.obj = str;
		mHandler.sendMessage(msg);
	}

	private void sendMsg(int what, boolean b) {
		Message msg = new Message();
		msg.what = what;
		msg.obj = b;
		mHandler.sendMessage(msg);
	}

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1001:
				RefeshStatusThread thread = new RefeshStatusThread();
				thread.doStart();
				break;

			case 1002:
				Toast.makeText(mContext, (String) msg.obj, Toast.LENGTH_LONG)
						.show();
				break;

			case 1003:
				mPrintStatus.setText((String) msg.obj);
				break;

			case 1004:
				mBtnSend.setEnabled((Boolean) msg.obj);
				break;

			case 1005:
				if (mResendMailTime < 2) {
					mResendMailTime++;
					SendMailThread thread1 = new SendMailThread();
					thread1.doStart();
				} else {if(mBluetoothAdapter!=null)
				{
					mBluetoothAdapter.disable();
				}
					Toast.makeText(mContext, (String) msg.obj, Toast.LENGTH_LONG)
					.show();
				}
			case 1006:
				if(emailSendOK && printSendOK)
				{
					finish();
				}
				break;
			default:
				break;
			}
		}
	};

	private class RefeshStatusThread extends Thread {
		private AlertDialog dlg;

		public void doStart() {
			if (mContext==null) {
				return;
			}
			dlg = new AlertDialog.Builder(mContext).create();
			dlg.setMessage("processing");
			dlg.show();

			this.start();
		}

		@Override
		public void run() {
			super.run();

			freshPrintStatus();
			dlg.dismiss();
		}
	}

	private class SendMailThread extends Thread {
		private AlertDialog dlg;

		public void doStart() {
			if (mContext==null) {
				return;
			}
			dlg = new AlertDialog.Builder(mContext).create();
			dlg.setMessage("processing");

			dlg.show();

			this.start();
		}

		@Override
		public void run() {
			super.run();
			sendMail();
			// freshPrintStatus();
			dlg.dismiss();
			
			emailSendOK = true;
			
			Message msg = Message.obtain();
			msg.what = 1006;
			mHandler.sendMessage(msg);
		}
	}

	public static String getCurrentTime() {
		SimpleDateFormat sDateFormat = new SimpleDateFormat("HH:mm MM/dd/yyyy");
		String data = sDateFormat.format(new java.util.Date());
		return data;
	}

	public static String getCurrentDate() {
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String data = sDateFormat.format(new java.util.Date());
		return data;
	}
	
	
	
	
	// 连接按键响应函数
	public void connectBluetooth()
	{
		Intent serverIntent = new Intent(this, DeviceListActivity.class); // 跳转程序设置
		startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE); // 设置返回宏定义
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		switch (requestCode)
		{
			case REQUEST_CONNECT_DEVICE: // 连接结果，由DeviceListActivity设置返回
				// 响应返回结果
				if (resultCode == Activity.RESULT_OK)
				{   
					// 连接成功，由DeviceListActivity设置返回
					// MAC地址，由DeviceListActivity设置返回
					String address = data.getExtras().getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
					// 得到蓝牙设备句柄
					mBluetoothDevice = mBluetoothAdapter.getRemoteDevice(address);

					// 用服务号得到socket
					try
					{
						mBluetoothSocket = mBluetoothDevice.createRfcommSocketToServiceRecord(SPP_UUID);
					}
					catch (IOException e)
					{
						Toast.makeText(this, "connect fail！", Toast.LENGTH_SHORT).show();
					}

					// 连接socket
					// Button btn = (Button) findViewById(R.id.Button03);

					try
					{
						mBluetoothSocket.connect();
						Toast.makeText(this, "connect" + mBluetoothDevice.getName() + "success！", Toast.LENGTH_SHORT).show();
						//btConnect.setText("disconnect");
					}
					catch (IOException e)
					{
						try
						{
							Toast.makeText(this, "connect fail！", Toast.LENGTH_SHORT).show();
							mBluetoothSocket.close();
							mBluetoothSocket = null;
						}
						catch (IOException ee)
						{
							Toast.makeText(this, "connect fail！", Toast.LENGTH_SHORT).show();
						}

						return;
					}

//					// 打开接收线程
//					try
//					{
//						is = bluetoothSocket.getInputStream(); // 得到蓝牙数据输入流
//						LOG.D(is.toString());
//					}
//					catch (IOException e)
//					{
//						Toast.makeText(this, "接收数据失败！", Toast.LENGTH_SHORT).show();
//						return;
//					}
//					if (bThread == false)
//					{
//						ReadThread.start();
//						bThread = true;
//					}
//					else
//					{
//						bRun = true;
//					}
					
					
					
				}
				break;
			default:
				break;
		}
	}
	
	

}
