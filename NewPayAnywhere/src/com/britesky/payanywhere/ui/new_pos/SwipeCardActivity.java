package com.britesky.payanywhere.ui.new_pos;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.util.ByteArrayBuffer;

import com.britesky.payanywhere.R;
import com.britesky.payanywhere.api.Money;
import com.dspread.xpos.QPOSService;
import com.dspread.xpos.QPOSService.CommunicationMode;
import com.dspread.xpos.QPOSService.DoTradeResult;
import com.dspread.xpos.QPOSService.Display;
import com.dspread.xpos.QPOSService.EmvOption;
import com.dspread.xpos.QPOSService.Error;
import com.dspread.xpos.QPOSService.TransactionResult;
import com.dspread.xpos.QPOSService.TransactionType;
import com.dspread.xpos.QPOSService.QPOSServiceListener;
import com.dspread.xpos.QPOSService.UpdateInformationResult;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.app.Activity;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.AssetManager;
import android.graphics.drawable.AnimationDrawable;

public class SwipeCardActivity extends Activity {
	private Context mContext;

	private Button doTradeButton;
	private EditText amountEditText;
	private EditText statusEditText;
	private ListView appListView;
	private Dialog dialog;

	private Button btnBT;
	private Button btnDisconnect;

	private QPOSService pos;
	private MyPosListener listener;

	private String amount = "";
	private String cashbackAmount = "";
	private boolean isPinCanceled = false;
	private String blueTootchAddress = "";
	public static final String POS_BLUETOOTH_ADDRESS = "POS_BLUETOOTH_ADDRESS";

	private ListView m_ListView;
	private MyListViewAdapter m_Adapter = null;
	private ImageView imvAnimScan;
	private AnimationDrawable animScan;
	private List<BluetoothDevice> lstDevScanned;
	private BroadcastReceiver recvBTScan = null;
	private Handler hdStopScan;

	private boolean isTest = false;

	private boolean isUart = true;
	private BluetoothAdapter mAdapter;

	private ImageButton mBtnAction;
	private String mSuccessAmount;
	private String mReceiveAmount;
	private Boolean mIsWorking = false;

	private static final int PROGRESS_UP = 1001;
	private Handler updata_handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case PROGRESS_UP:
				statusEditText.setText(msg.obj.toString() + "%");
				break;

			default:
				break;
			}
		};
	};

	private void doScanBTPos() {
		// if (lstDevScanned == null) {
		// lstDevScanned = new ArrayList<BluetoothDevice>();
		// }
		// //
		// lstDevScanned.clear();
		//
		close();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//
		refreshAdapter();
		//
		// if (mAdapter == null) {
		// mAdapter = BluetoothAdapter.getDefaultAdapter();
		// }
		// if (!mAdapter.isEnabled()) {
		// // 弹出对话框提示用户是后打?
		// Intent enabler = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		// startActivity(enabler);
		// return;
		// // 不做提示，强行打�? // mAdapter.enable();
		// }
		//
		// if (recvBTScan != null)
		// unregisterReceiver(recvBTScan);
		// recvBTScan = new BroadcastReceiver() {
		//
		// @Override
		// public void onReceive(Context context, Intent intent) {
		// // TODO Auto-generated method stub
		// String action = intent.getAction();
		// if (BluetoothDevice.ACTION_FOUND.equals(action)) {
		// BluetoothDevice device = intent
		// .getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
		// boolean isFound = false;
		// for (BluetoothDevice dev : lstDevScanned) {
		// if (dev.getAddress().equals(device.getAddress())) {
		// isFound = true;
		// break;
		// }
		// }
		// //
		// if (!isFound)
		// lstDevScanned.add(device);
		// refreshAdapter();
		// } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED
		// .equals(action)) {
		// onStopScanBTPos();
		// }
		// }
		//
		// };
		// //
		// IntentFilter localIntentFilter = new IntentFilter(
		// BluetoothDevice.ACTION_FOUND);
		// localIntentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		// registerReceiver(recvBTScan, localIntentFilter);
		//
		m_ListView.setVisibility(View.VISIBLE);
		// animScan.start();
		// imvAnimScan.setVisibility(View.VISIBLE);
		// //
		// hdStopScan.sendEmptyMessageDelayed(10240, 1000 * 20);
		// //
		// mAdapter.startDiscovery();
	}

	private void StopScanBTPos() {
		if (mAdapter != null) {
			mAdapter.cancelDiscovery();
			mAdapter = null;
		}

	}

	private void onStopScanBTPos() {
		animScan.stop();
		imvAnimScan.setVisibility(View.GONE);
		//
		unregisterReceiver(recvBTScan);
		refreshAdapter();
		recvBTScan = null;
	}

	private void onBTPosSelected(Activity activity, View itemView, int index) {
		StopScanBTPos();
		//
		start_time = new Date().getTime();
		if (index == 0) {
			/* 这里是点中音频列表项的处�? */
			open(CommunicationMode.AUDIO);
			posType = POS_TYPE.AUDIO;
			pos.openAudio();
			
		} else if (index == 1 && isUart) {
			/* 这里是点中串口列表项的处�? */
			open(CommunicationMode.UART);
			posType = POS_TYPE.UART;
			pos.openUart();
		} else {
			/* 其余是点中蓝牙列表项的处�? */

			// open(CommunicationMode.BLUETOOTH_VER2);
			open(CommunicationMode.BLUETOOTH_2Mode);
			posType = POS_TYPE.BLUETOOTH;
			Map<String, ?> dev = (Map<String, ?>) m_Adapter.getItem(index);
			blueTootchAddress = (String) dev.get("ADDRESS");
			sendMsg(1001);
		}
	}

	protected List<Map<String, ?>> generateAdapterData() {
		// TODO Auto-generated method stub
		List<Map<String, ?>> data = new ArrayList<Map<String, ?>>();
		//
		Map<String, Object> itmAudio = new HashMap<String, Object>();
		itmAudio.put("ICON", Integer.valueOf(R.drawable.ic_headphones_on));
		itmAudio.put("TITLE", getResources().getString(R.string.audio));
		itmAudio.put("ADDRESS", getResources().getString(R.string.audio));

		data.add(itmAudio);

		if (isUart) {
			//
			Map<String, Object> itmSerialPort = new HashMap<String, Object>();
			itmSerialPort.put("ICON", Integer.valueOf(R.drawable.serialport));
			itmSerialPort.put("TITLE",
					getResources().getString(R.string.serialport));
			itmSerialPort.put("ADDRESS",
					getResources().getString(R.string.serialport));

			data.add(itmSerialPort);
			//
		}

		// for (BluetoothDevice dev : lstDevScanned) {
		// Map<String, Object> itm = new HashMap<String, Object>();
		// itm.put("ICON",
		// dev.getBondState() == BluetoothDevice.BOND_BONDED ? Integer
		// .valueOf(R.drawable.bluetooth_blue) : Integer
		// .valueOf(R.drawable.bluetooth_blue_unbond));
		// itm.put("TITLE", dev.getName() + "(" + dev.getAddress() + ")");
		// itm.put("ADDRESS", dev.getAddress());
		// //
		// data.add(itm);
		// }
		//
		return data;
	}

	private void refreshAdapter() {
		if (m_Adapter != null) {
			m_Adapter.clearData();
			m_Adapter = null;
		}
		//
		List<Map<String, ?>> data = generateAdapterData();
		m_Adapter = new MyListViewAdapter(this, data);
		//
		m_ListView.setAdapter(m_Adapter);
	}

	private class MyListViewAdapter extends BaseAdapter {
		private List<Map<String, ?>> m_DataMap;
		private LayoutInflater m_Inflater;

		public void clearData() {
			m_DataMap.clear();
			m_DataMap = null;
		}

		public MyListViewAdapter(Context context, List<Map<String, ?>> map) {
			this.m_DataMap = map;
			this.m_Inflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return m_DataMap.size();
		}

		@Override
		public Object getItem(int position) {
			return m_DataMap.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			if (convertView == null) {
				convertView = m_Inflater.inflate(R.layout.bt_qpos_item, null);
			}
			ImageView m_Icon = (ImageView) convertView
					.findViewById(R.id.item_iv_icon);
			TextView m_TitleName = (TextView) convertView
					.findViewById(R.id.item_tv_lable);
			//
			Map<String, ?> itemdata = (Map<String, ?>) m_DataMap.get(position);
			int idIcon = (Integer) itemdata.get("ICON");
			String sTitleName = (String) itemdata.get("TITLE");
			//
			m_Icon.setBackgroundResource(idIcon);
			m_TitleName.setText(sTitleName);
			//
			return convertView;
		}

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		if (!isUart) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}
		mContext = this;
		mIsWorking = true;

		setContentView(R.layout.activity_swipe);
		mReceiveAmount = getIntent().getStringExtra("amount");
		// System.out.println("-------------amount ="+mSendAmount+"\n");

		m_ListView = (ListView) findViewById(R.id.lv_indicator_BTPOS);
		m_ListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				onBTPosSelected(SwipeCardActivity.this, view, position);
				m_ListView.setVisibility(View.GONE);
				animScan.stop();
				imvAnimScan.setVisibility(View.GONE);
			}

		});

		imvAnimScan = (ImageView) findViewById(R.id.img_anim_scanbt);
		animScan = (AnimationDrawable) getResources().getDrawable(
				R.anim.progressanmi);
		imvAnimScan.setBackgroundDrawable(animScan);

		hdStopScan = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 10024) {
					StopScanBTPos();
				}
			}
		};

		doTradeButton = (Button) findViewById(R.id.doTradeButton);
		amountEditText = (EditText) findViewById(R.id.amountEditText);
		statusEditText = (EditText) findViewById(R.id.statusEditText);
		btnBT = (Button) findViewById(R.id.btnBT);
		btnDisconnect = (Button) findViewById(R.id.disconnect);
		MyOnClickListener myOnClickListener = new MyOnClickListener();
		doTradeButton.setOnClickListener(myOnClickListener);
		btnBT.setOnClickListener(myOnClickListener);
		btnDisconnect.setOnClickListener(myOnClickListener);

		mBtnAction = (ImageButton) findViewById(R.id.action);
		mBtnAction.setOnClickListener(myOnClickListener);
		
		//add test
		//mBtnAction.setVisibility(View.VISIBLE);
		
		// initBluetooth();
	}

	// private POS_TYPE posType = POS_TYPE.BLUETOOTH;
	private POS_TYPE posType = POS_TYPE.AUDIO;

	private static enum POS_TYPE {
		BLUETOOTH, AUDIO, UART
	}

	private void open(CommunicationMode mode) {
		listener = new MyPosListener();
		pos = QPOSService.getInstance(mode);
		if (pos == null) {
			statusEditText.setText("CommunicationMode unknow");
			return;
		}
		pos.setContext(getApplicationContext());
		Handler handler = new Handler(Looper.myLooper());
		pos.initListener(handler, listener);
	}

	private void close() {
		if (pos == null) {
			return;
		}
		if (posType == POS_TYPE.AUDIO) {
			TRACE.d("********************* close POS_TYPE.AUDIO");
			pos.closeAudio();
		} else if (posType == POS_TYPE.BLUETOOTH) {
			TRACE.d("********************* close POS_TYPE.BLUETOOTH");
			pos.disconnectBT();
		} else if (posType == POS_TYPE.UART) {
			TRACE.d("********************* close POS_TYPE.UART");
			pos.resetQPOS();
			pos.closeUart();
		}
	}

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// MenuInflater inflater = getMenuInflater();
	// inflater.inflate(R.menu.activity_main, menu);
	// return true;
	// }
	//
	// @Override
	// public boolean onOptionsItemSelected(MenuItem item) {
	// if (pos == null) {
	// return true;
	// }
	//
	// if (item.getItemId() == R.id.menu_get_deivce_info) {
	// statusEditText.setText(R.string.getting_info);
	// pos.getQposInfo();
	// } else if (item.getItemId() == R.id.menu_get_pos_id) {
	// statusEditText.setText(R.string.getting_pos_id);
	// pos.getQposId();
	// } else if (item.getItemId() == R.id.menu_get_pin) {
	// statusEditText.setText(R.string.input_pin);
	// pos.getPin("201402121655");
	// }
	// // else if (item.getItemId() == R.id.menu_update) {
	// // // Toast.makeText(getApplicationContext(), "updata",
	// // // Toast.LENGTH_LONG)
	// // // .show();
	// // UpdateThread updateThread = new UpdateThread();
	// // updateThread.start();
	// //
	// // }
	// // else if(item.getItemId() == R.id.menu_audio) {
	// // finish();
	// // Intent intent = new Intent(this, AudioActivity.class);
	// // startActivity(intent);
	// // }else if(item.getItemId() == R.id.menu_icc) {
	// //
	// // finish();
	// // Intent intent = new Intent(this, IccActivity.class);
	// // startActivity(intent);
	// // }
	// return true;
	// }
	class UpdateThread extends Thread {
		public void run() {
			byte[] data = readLine("A21_s1_stm32.asc");
			// data = readLine("A16_geu_B30_stm32.asc");
			// .asc
			// pos.updatePosFirmware(data, blueTootchAddress);
			while (true) {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				int progress = pos.getUpdateProgress();
				if (progress < 100) {
					Message msg = updata_handler.obtainMessage();
					msg.what = PROGRESS_UP;
					msg.obj = progress;
					msg.sendToTarget();
					continue;
				}
				Message msg = updata_handler.obtainMessage();
				msg.what = PROGRESS_UP;
				msg.obj = "升级完成";
				msg.sendToTarget();
				break;
			}
		};
	};

	@Override
	public void onPause() {
		super.onPause();
		TRACE.d("onPause");
	}

	@Override
	public void onResume() {
		super.onResume();
		TRACE.d("onResume");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mIsWorking = false;
		dismissDialog();
		
		TRACE.d("*********************swipe card onDestroy");
		if (mAdapter != null) {
			mAdapter.cancelDiscovery();
		}
		close();
		if (pos != null) {
			pos.onDestroy();
			TRACE.d("*********************swipe card pos.onDestroy");
		}
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.exit(0);
		// finish();
	}

	public void dismissDialog() {
		if (dialog != null) {
			dialog.dismiss();
			dialog = null;
		}
	}

	private byte[] readLine(String Filename) {

		String str = "";
		ByteArrayBuffer buffer = new ByteArrayBuffer(0);
		try {
			android.content.ContextWrapper contextWrapper = new ContextWrapper(
					this);
			AssetManager assetManager = contextWrapper.getAssets();
			InputStream inputStream = assetManager.open(Filename);
			// BufferedReader br = new BufferedReader(new
			// InputStreamReader(inputStream));
			// str = br.readLine();
			int b = inputStream.read();
			while (b != -1) {
				buffer.append((byte) b);
				b = inputStream.read();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return buffer.toByteArray();
	}

	class MyPosListener implements QPOSServiceListener {

		@Override
		public void onRequestWaitingUser() {
			TRACE.d("****QPOSServiceListener--->onRequestWaitingUser");
			
			if (mIsWorking == false) {
				return;
			}
			dismissDialog();
			statusEditText.setText(getString(R.string.waiting_for_card));
		}

		@Override
		public void onDoTradeResult(DoTradeResult result,
				Hashtable<String, String> decodeData) {
			TRACE.d("****QPOSServiceListener--->onDoTradeResult");
			if (mIsWorking == false) {
				return;
			}
			dismissDialog();
			
			if (result == DoTradeResult.NONE) {
				statusEditText.setText(getString(R.string.no_card_detected));
			} else if (result == DoTradeResult.ICC) {
				statusEditText.setText(getString(R.string.icc_card_inserted));
				TRACE.d("EMV ICC Start");
				pos.doEmvApp(EmvOption.START);
			} else if (result == DoTradeResult.NOT_ICC) {
				statusEditText.setText(getString(R.string.card_inserted));
			} else if (result == DoTradeResult.BAD_SWIPE) {
				statusEditText.setText(getString(R.string.bad_swipe));
			} else if (result == DoTradeResult.MCR) {
				TRACE.d("decodeData: " + decodeData);
				String content = getString(R.string.card_swiped);
				String formatID = decodeData.get("formatID");
				if (formatID.equals("31") || formatID.equals("40")
						|| formatID.equals("37") || formatID.equals("17")
						|| formatID.equals("11") || formatID.equals("10")) {
					String maskedPAN = decodeData.get("maskedPAN");
					
					ReceiptActivity.MaskedPAN = maskedPAN+"";
					
					String expiryDate = decodeData.get("expiryDate");
					String cardHolderName = decodeData.get("cardholderName");
					String serviceCode = decodeData.get("serviceCode");
					String trackblock = decodeData.get("trackblock");
					String psamId = decodeData.get("psamId");
					String posId = decodeData.get("posId");
					String pinblock = decodeData.get("pinblock");
					String macblock = decodeData.get("macblock");
					String activateCode = decodeData.get("activateCode");
					String trackRandomNumber = decodeData
							.get("trackRandomNumber");

					content += getString(R.string.format_id) + " " + formatID
							+ "\n";
					content += getString(R.string.masked_pan) + " " + maskedPAN
							+ "\n";
					content += getString(R.string.expiry_date) + " "
							+ expiryDate + "\n";
					content += getString(R.string.cardholder_name) + " "
							+ cardHolderName + "\n";

					content += getString(R.string.service_code) + " "
							+ serviceCode + "\n";
					content += "trackblock: " + trackblock + "\n";
					content += "psamId: " + psamId + "\n";
					content += "posId: " + posId + "\n";
					content += getString(R.string.pinBlock) + " " + pinblock
							+ "\n";
					content += "macblock: " + macblock + "\n";
					content += "activateCode: " + activateCode + "\n";
					content += "trackRandomNumber: " + trackRandomNumber + "\n";
				} else {

					String maskedPAN = decodeData.get("maskedPAN");
					ReceiptActivity.MaskedPAN = maskedPAN+"";
					
					String expiryDate = decodeData.get("expiryDate");
					String cardHolderName = decodeData.get("cardholderName");
					String ksn = decodeData.get("ksn");
					String serviceCode = decodeData.get("serviceCode");
					String track1Length = decodeData.get("track1Length");
					String track2Length = decodeData.get("track2Length");
					String track3Length = decodeData.get("track3Length");
					String encTracks = decodeData.get("encTracks");
					String encTrack1 = decodeData.get("encTrack1");
					String encTrack2 = decodeData.get("encTrack2");
					String encTrack3 = decodeData.get("encTrack3");
					String partialTrack = decodeData.get("partialTrack");
					// TODO
					String pinKsn = decodeData.get("pinKsn");
					String trackksn = decodeData.get("trackksn");
					String pinBlock = decodeData.get("pinBlock");
					String encPAN = decodeData.get("encPAN");
					String trackRandomNumber = decodeData
							.get("trackRandomNumber");
					String pinRandomNumber = decodeData.get("pinRandomNumber");

					content += getString(R.string.format_id) + " " + formatID
							+ "\n";
					content += getString(R.string.masked_pan) + " " + maskedPAN
							+ "\n";
					content += getString(R.string.expiry_date) + " "
							+ expiryDate + "\n";
					content += getString(R.string.cardholder_name) + " "
							+ cardHolderName + "\n";
					content += getString(R.string.ksn) + " " + ksn + "\n";
					content += getString(R.string.pinKsn) + " " + pinKsn + "\n";
					content += getString(R.string.trackksn) + " " + trackksn
							+ "\n";
					content += getString(R.string.service_code) + " "
							+ serviceCode + "\n";
					content += getString(R.string.track_1_length) + " "
							+ track1Length + "\n";
					content += getString(R.string.track_2_length) + " "
							+ track2Length + "\n";
					content += getString(R.string.track_3_length) + " "
							+ track3Length + "\n";
					content += getString(R.string.encrypted_tracks) + " "
							+ encTracks + "\n";
					content += getString(R.string.encrypted_track_1) + " "
							+ encTrack1 + "\n";
					content += getString(R.string.encrypted_track_2) + " "
							+ encTrack2 + "\n";
					content += getString(R.string.encrypted_track_3) + " "
							+ encTrack3 + "\n";
					content += getString(R.string.partial_track) + " "
							+ partialTrack + "\n";
					content += getString(R.string.pinBlock) + " " + pinBlock
							+ "\n";
					content += "encPAN: " + encPAN + "\n";
					content += "trackRandomNumber: " + trackRandomNumber + "\n";
					content += "pinRandomNumber:" + " " + pinRandomNumber
							+ "\n";

				}

				TRACE.d("swipe card:" + content);
				statusEditText.setText(content);
				mBtnAction.setVisibility(View.VISIBLE);
			} else if (result == DoTradeResult.NO_RESPONSE) {
				statusEditText.setText(getString(R.string.card_no_response));
			}
		}

		@Override
		public void onQposInfoResult(Hashtable<String, String> posInfoData) {
			TRACE.d("****QPOSServiceListener--->onQposInfoResult");
			if (mIsWorking == false) {
				return;
			}
			String isSupportedTrack1 = posInfoData.get("isSupportedTrack1") == null ? ""
					: posInfoData.get("isSupportedTrack1");
			String isSupportedTrack2 = posInfoData.get("isSupportedTrack2") == null ? ""
					: posInfoData.get("isSupportedTrack2");
			String isSupportedTrack3 = posInfoData.get("isSupportedTrack3") == null ? ""
					: posInfoData.get("isSupportedTrack3");
			String bootloaderVersion = posInfoData.get("bootloaderVersion") == null ? ""
					: posInfoData.get("bootloaderVersion");
			String firmwareVersion = posInfoData.get("firmwareVersion") == null ? ""
					: posInfoData.get("firmwareVersion");
			String isUsbConnected = posInfoData.get("isUsbConnected") == null ? ""
					: posInfoData.get("isUsbConnected");
			String isCharging = posInfoData.get("isCharging") == null ? ""
					: posInfoData.get("isCharging");
			String batteryLevel = posInfoData.get("batteryLevel") == null ? ""
					: posInfoData.get("batteryLevel");
			String hardwareVersion = posInfoData.get("hardwareVersion") == null ? ""
					: posInfoData.get("hardwareVersion");

			String content = "";
			content += getString(R.string.bootloader_version)
					+ bootloaderVersion + "\n";
			content += getString(R.string.firmware_version) + firmwareVersion
					+ "\n";
			content += getString(R.string.usb) + isUsbConnected + "\n";
			content += getString(R.string.charge) + isCharging + "\n";
			content += getString(R.string.battery_level) + batteryLevel + "\n";
			content += getString(R.string.hardware_version) + hardwareVersion
					+ "\n";
			content += getString(R.string.track_1_supported)
					+ isSupportedTrack1 + "\n";
			content += getString(R.string.track_2_supported)
					+ isSupportedTrack2 + "\n";
			content += getString(R.string.track_3_supported)
					+ isSupportedTrack3 + "\n";

			statusEditText.setText(content);
		}

		@Override
		public void onRequestTransactionResult(
				TransactionResult transactionResult) {
			TRACE.d("****QPOSServiceListener--->onRequestTransactionResult");
			if (mIsWorking == false) {
				return;
			}
			// clearDisplay();
			dismissDialog();

			// statusEditText.setText("");
			dialog = new Dialog(SwipeCardActivity.this);
			dialog.setContentView(R.layout.alert_dialog);
			dialog.setTitle(R.string.transaction_result);
			TextView messageTextView = (TextView) dialog
					.findViewById(R.id.messageTextView);

			if (transactionResult == TransactionResult.APPROVED) {
				TRACE.d("TransactionResult.APPROVED");
				String message = getString(R.string.transaction_approved)
						+ "\n" + getString(R.string.amount) + ": $" + amount
						+ "\n";
				if (!cashbackAmount.equals("")) {
					message += getString(R.string.cashback_amount) + ": $"
							+ cashbackAmount;
				}
				messageTextView.setText(message);
			} else if (transactionResult == TransactionResult.TERMINATED) {
				clearDisplay();
				messageTextView
						.setText(getString(R.string.transaction_terminated));
			} else if (transactionResult == TransactionResult.DECLINED) {
				messageTextView
						.setText(getString(R.string.transaction_declined));
			} else if (transactionResult == TransactionResult.CANCEL) {
				clearDisplay();
				messageTextView.setText(getString(R.string.transaction_cancel));
			} else if (transactionResult == TransactionResult.CAPK_FAIL) {
				messageTextView
						.setText(getString(R.string.transaction_capk_fail));
			} else if (transactionResult == TransactionResult.NOT_ICC) {
				messageTextView
						.setText(getString(R.string.transaction_not_icc));
			} else if (transactionResult == TransactionResult.SELECT_APP_FAIL) {
				messageTextView
						.setText(getString(R.string.transaction_app_fail));
			} else if (transactionResult == TransactionResult.DEVICE_ERROR) {
				messageTextView
						.setText(getString(R.string.transaction_device_error));
			} else if (transactionResult == TransactionResult.CARD_NOT_SUPPORTED) {
				messageTextView.setText(getString(R.string.card_not_supported));
			} else if (transactionResult == TransactionResult.MISSING_MANDATORY_DATA) {
				messageTextView
						.setText(getString(R.string.missing_mandatory_data));
			} else if (transactionResult == TransactionResult.CARD_BLOCKED_OR_NO_EMV_APPS) {
				messageTextView
						.setText(getString(R.string.card_blocked_or_no_evm_apps));
			} else if (transactionResult == TransactionResult.INVALID_ICC_DATA) {
				messageTextView.setText(getString(R.string.invalid_icc_data));
			}

			dialog.findViewById(R.id.confirmButton).setOnClickListener(
					new OnClickListener() {

						@Override
						public void onClick(View v) {
							dismissDialog();
						}
					});
			dialog.setOnCancelListener(new OnCancelListener()
			{
				
				@Override
				public void onCancel(DialogInterface dialog)
				{
					// TODO Auto-generated method stub
					dismissDialog();
				}
			});
			
			dialog.show();

			amount = "";
			cashbackAmount = "";
			amountEditText.setText("");
		}

		@Override
		public void onRequestBatchData(String tlv) {
			TRACE.d("****QPOSServiceListener--->onRequestBatchData");
			if (mIsWorking == false) {
				return;
			}
			TRACE.d("ICC交易结束");
			// dismissDialog();
			String content = getString(R.string.batch_data);
			TRACE.d("tlv:" + tlv);
			content += tlv;
			statusEditText.setText(content);
			mBtnAction.setVisibility(View.VISIBLE);
		}

		@Override
		public void onRequestTransactionLog(String tlv) {
			TRACE.d("****QPOSServiceListener--->onRequestTransactionLog");
			if (mIsWorking == false) {
				return;
			}
			dismissDialog();
			String content = getString(R.string.transaction_log);
			content += tlv;
			statusEditText.setText(content);
		}

		@Override
		public void onQposIdResult(Hashtable<String, String> posIdTable) {
			TRACE.d("****QPOSServiceListener--->onQposIdResult");
			
			if (mIsWorking == false) {
				return;
			}
			String posId = posIdTable.get("posId") == null ? "" : posIdTable
					.get("posId");
			String csn = posIdTable.get("csn") == null ? "" : posIdTable
					.get("csn");

			String content = "";
			content += getString(R.string.posId) + posId + "\n";
			content += "csn: " + csn + "\n";
			statusEditText.setText(content);
			if (isTest) {
				sendMsg(1003);
			}

		}

		@Override
		public void onRequestSelectEmvApp(ArrayList<String> appList) {
			TRACE.d("****QPOSServiceListener--->onRequestSelectEmvApp");
			
			if (mIsWorking == false) {
				return;
			}
			TRACE.d("请�?择App -- S");
			dismissDialog();

			dialog = new Dialog(SwipeCardActivity.this);
			dialog.setContentView(R.layout.emv_app_dialog);
			dialog.setTitle(R.string.please_select_app);

			String[] appNameList = new String[appList.size()];
			for (int i = 0; i < appNameList.length; ++i) {
				TRACE.d("i=" + i + "," + appList.get(i));
				appNameList[i] = appList.get(i);
			}

			appListView = (ListView) dialog.findViewById(R.id.appList);
			appListView.setAdapter(new ArrayAdapter<String>(
					SwipeCardActivity.this,
					android.R.layout.simple_list_item_1, appNameList));
			appListView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					
					pos.selectEmvApp(position);
					TRACE.d("请�?择App -- 结束 position = " + position);
					dismissDialog();
				}

			});
			dialog.findViewById(R.id.cancelButton).setOnClickListener(
					new OnClickListener() {

						@Override
						public void onClick(View v) {
							pos.cancelSelectEmvApp();
							dismissDialog();
						}
					});
			dialog.setOnCancelListener(new OnCancelListener()
			{
				
				@Override
				public void onCancel(DialogInterface dialog)
				{
					pos.cancelSelectEmvApp();
					dismissDialog();
				}
			});
			
			dialog.show();
		}

		@Override
		public void onRequestSetAmount() {
			TRACE.d("****QPOSServiceListener--->onRequestSetAmount");
			if (mIsWorking == false) {
				return;
			}
			TRACE.d("输入金额 -- S");
			dismissDialog();
			dialog = new Dialog(SwipeCardActivity.this);
			dialog.setContentView(R.layout.amount_dialog);
			dialog.setTitle(getString(R.string.set_amount));
			((EditText) (dialog.findViewById(R.id.amountEditText)))
					.setText(mReceiveAmount);

			String[] transactionTypes = new String[] { "GOODS", "SERVICES",
					"CASHBACK", "INQUIRY", "TRANSFER", "PAYMENT" };
			((Spinner) dialog.findViewById(R.id.transactionTypeSpinner))
					.setAdapter(new ArrayAdapter<String>(
							SwipeCardActivity.this,
							android.R.layout.simple_spinner_item,
							transactionTypes));

			dialog.findViewById(R.id.setButton).setOnClickListener(
					new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// String amount = ((EditText) (dialog
							// .findViewById(R.id.amountEditText)))
							// .getText().toString();
							EditText amountET = (EditText) (dialog
									.findViewById(R.id.amountEditText));
							Money money = new Money(amountET.getText()
									.toString());

							boolean hasNoError = true;
							if (!money.isGreaterThanZero()) {
								amountET.setError(getString(R.string.price_is_zero));
								hasNoError = false;
							} else {
								amountET.setError(null);
							}
							if (!hasNoError) {
								return;
							}

							String cashbackAmount = ((EditText) (dialog
									.findViewById(R.id.cashbackAmountEditText)))
									.getText().toString();
							String transactionTypeString = (String) ((Spinner) dialog
									.findViewById(R.id.transactionTypeSpinner))
									.getSelectedItem();

							TransactionType transactionType = TransactionType.GOODS;
							if (transactionTypeString.equals("GOODS")) {
								transactionType = TransactionType.GOODS;
							} else if (transactionTypeString.equals("SERVICES")) {
								transactionType = TransactionType.SERVICES;
							} else if (transactionTypeString.equals("CASHBACK")) {
								transactionType = TransactionType.CASHBACK;
							} else if (transactionTypeString.equals("INQUIRY")) {
								transactionType = TransactionType.INQUIRY;
							} else if (transactionTypeString.equals("TRANSFER")) {
								transactionType = TransactionType.TRANSFER;
							} else if (transactionTypeString.equals("PAYMENT")) {
								transactionType = TransactionType.PAYMENT;
							}
							// pos.setAmountIcon("$");
							// pos.setAmountIcon("RMB");

							// amountEditText.setText("$" + amount(amount));
							// mSuccessAmount = amountEditText.getText()
							// .toString();
							// pos.setAmount(amount, cashbackAmount, "384",
							// transactionType);
							// SwipeCardActivity.this.amount = amount(amount);

							String amount = money.toString();
							amountEditText.setText(amount);
							mSuccessAmount = amountEditText.getText()
									.toString();
							pos.setAmount(Util.removeAmountSymbol(amount),
									cashbackAmount, "384", transactionType);
							SwipeCardActivity.this.amount = Util
									.removeAmountDollar(amount);

							SwipeCardActivity.this.cashbackAmount = cashbackAmount;
							TRACE.d("输入金额  -- 结束");
							dismissDialog();
						}

					});

			dialog.findViewById(R.id.cancelButton).setOnClickListener(
					new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							pos.cancelSetAmount();
							dialog.dismiss();
						}

					});
			dialog.setOnCancelListener(new OnCancelListener()
			{
				
				@Override
				public void onCancel(DialogInterface dialog)
				{
					pos.cancelSetAmount();
					dismissDialog();
				}
			});
			dialog.show();

			// amountEditText.setText("$" + mSendAmount);
			// mSuccessAmount = amountEditText.getText().toString();
			// pos.setAmount(mSendAmount, cashbackAmount, "384",
			// TransactionType.GOODS);
			// SwipeCardActivity.this.amount = amount(mSendAmount);
			// SwipeCardActivity.this.cashbackAmount = "";

		}

		@Override
		public void onRequestIsServerConnected() {
			TRACE.d("****QPOSServiceListener--->onRequestIsServerConnected");
			
			if (mIsWorking == false) {
				return;
			}
			TRACE.d("在线过程请求");
			dismissDialog();
			dialog = new Dialog(SwipeCardActivity.this);
			dialog.setContentView(R.layout.alert_dialog);
			dialog.setTitle(R.string.online_process_requested);

			((TextView) dialog.findViewById(R.id.messageTextView))
					.setText(R.string.replied_connected);

			dialog.findViewById(R.id.confirmButton).setOnClickListener(
					new OnClickListener() {

						@Override
						public void onClick(View v) {
							pos.isServerConnected(true);
							dismissDialog();
						}
					});
			dialog.setOnCancelListener(new OnCancelListener()
			{
				
				@Override
				public void onCancel(DialogInterface dialog)
				{
					pos.isServerConnected(false);
					dismissDialog();
				}
			});
			dialog.show();
		}

		@Override
		public void onRequestOnlineProcess(String tlv) {
			TRACE.d("****QPOSServiceListener--->onRequestOnlineProcess");
			
			if (mIsWorking == false) {
				return;
			}
			TRACE.d("向服务器请求数据");
			dismissDialog();
			dialog = new Dialog(SwipeCardActivity.this);
			dialog.setContentView(R.layout.alert_dialog);
			dialog.setTitle(R.string.request_data_to_server);
			TRACE.d("tlv:" + tlv);
			// TODO final String str = Client.send(tlv);
			// TRACE.d("str:"+str);
			Hashtable<String, String> decodeData = pos.anlysEmvIccData(tlv);
			TRACE.i("onlineProcess: " + decodeData);
			if (isPinCanceled) {
				((TextView) dialog.findViewById(R.id.messageTextView))
						.setText(R.string.replied_failed);
			} else {
				((TextView) dialog.findViewById(R.id.messageTextView))
						.setText(R.string.replied_success);
			}

			dialog.findViewById(R.id.confirmButton).setOnClickListener(
					new OnClickListener() {

						@Override
						public void onClick(View v) {
							if (isPinCanceled) {
								pos.sendOnlineProcessResult(null);
							} else {
								pos.sendOnlineProcessResult("8A023030");
								// emvSwipeController.sendOnlineProcessResult(str);
							}
							dismissDialog();
						}
					});
			dialog.setOnCancelListener(new OnCancelListener()
			{
				
				@Override
				public void onCancel(DialogInterface dialog)
				{
					pos.sendOnlineProcessResult(null);
					dismissDialog();
				}
			});
			dialog.show();
		}

		@Override
		public void onRequestTime() {
			TRACE.d("****QPOSServiceListener--->onRequestTime");
			
			if (mIsWorking == false) {
				return;
			}
			TRACE.d("要求终端时间。已回覆");
			dismissDialog();
			String terminalTime = new SimpleDateFormat("yyyyMMddHHmmss")
					.format(Calendar.getInstance().getTime());
			pos.sendTime(terminalTime);
			statusEditText.setText(getString(R.string.request_terminal_time)
					+ " " + terminalTime);
		}

		@Override
		public void onRequestDisplay(Display displayMsg) {
			TRACE.d("****QPOSServiceListener--->onRequestDisplay");
			
			if (mIsWorking == false) {
				return;
			}
			dismissDialog();

			String msg = "";
			if (displayMsg == Display.CLEAR_DISPLAY_MSG) {
				msg = "";
			} else if (displayMsg == Display.PLEASE_WAIT) {
				msg = getString(R.string.wait);
			} else if (displayMsg == Display.REMOVE_CARD) {
				msg = getString(R.string.remove_card);
			} else if (displayMsg == Display.TRY_ANOTHER_INTERFACE) {
				msg = getString(R.string.try_another_interface);
			} else if (displayMsg == Display.PROCESSING) {
				msg = getString(R.string.processing);
			} else if (displayMsg == Display.INPUT_PIN_ING) {
				msg = "please input pin on pos";
			} else if (displayMsg == Display.MAG_TO_ICC_TRADE) {
				msg = "please insert chip card on pos";
			}
			statusEditText.setText(msg);
		}

		@Override
		public void onRequestFinalConfirm() {
			TRACE.d("****QPOSServiceListener--->onRequestFinalConfirm");
			
			if (mIsWorking == false) {
				return;
			}
			TRACE.d("确认金额-- S");
			dismissDialog();
			if (!isPinCanceled) {
				dialog = new Dialog(SwipeCardActivity.this);
				dialog.setContentView(R.layout.confirm_dialog);
				dialog.setTitle(getString(R.string.confirm_amount));

				String message = getString(R.string.amount) + ": $" + amount;
				if (!cashbackAmount.equals("")) {
					message += "\n" + getString(R.string.cashback_amount)
							+ ": $" + cashbackAmount;
				}

				((TextView) dialog.findViewById(R.id.messageTextView))
						.setText(message);

				dialog.findViewById(R.id.confirmButton).setOnClickListener(
						new OnClickListener() {
							@Override
							public void onClick(View v) {
								pos.finalConfirm(true);
								dialog.dismiss();
								TRACE.d("确认金额-- 结束");
							}
						});

				dialog.findViewById(R.id.cancelButton).setOnClickListener(
						new OnClickListener() {
							@Override
							public void onClick(View v) {
								pos.finalConfirm(false);
								dialog.dismiss();
							}
						});
				dialog.setOnCancelListener(new OnCancelListener()
				{
					
					@Override
					public void onCancel(DialogInterface dialog)
					{
						pos.finalConfirm(false);
						dismissDialog();
					}
				});
				dialog.show();
			} else {
				pos.finalConfirm(false);
			}
		}

		@Override
		public void onRequestNoQposDetected() {
			TRACE.d("****QPOSServiceListener--->onRequestNoQposDetected");
			
			if (mIsWorking == false) {
				return;
			}
			dismissDialog();
			statusEditText.setText(getString(R.string.no_device_detected));
		}

		@Override
		public void onRequestQposConnected() {
			TRACE.d("****QPOSServiceListener--->onRequestQposConnected");
			
			if (mIsWorking == false) {
				return;
			}
			dismissDialog();
			long use_time = new Date().getTime() - start_time;
			statusEditText.setText(getString(R.string.device_plugged) + "--用时"
					+ Util.formatLongToTimeStr(use_time));
			doTradeButton.setEnabled(true);
			btnDisconnect.setEnabled(true);
		}

		@Override
		public void onRequestQposDisconnected() {
			TRACE.d("****QPOSServiceListener--->onRequestQposDisconnected");
			
			if (mIsWorking == false) {
				return;
			}
			dismissDialog();
			statusEditText.setText(getString(R.string.device_unplugged));
			btnDisconnect.setEnabled(false);
			doTradeButton.setEnabled(false);
		}

		@Override
		public void onError(Error errorState) {
			TRACE.d("****QPOSServiceListener--->onError");
			
			if (mIsWorking == false) {
				return;
			}
			dismissDialog();
			amountEditText.setText("");
			if (errorState == Error.CMD_NOT_AVAILABLE) {
				statusEditText
						.setText(getString(R.string.command_not_available));
			} else if (errorState == Error.TIMEOUT) {
				statusEditText.setText(getString(R.string.device_no_response));
			} else if (errorState == Error.DEVICE_RESET) {
				statusEditText.setText(getString(R.string.device_reset));
			} else if (errorState == Error.UNKNOWN) {
				statusEditText.setText(getString(R.string.unknown_error));
			} else if (errorState == Error.DEVICE_BUSY) {
				statusEditText.setText(getString(R.string.device_busy));
			} else if (errorState == Error.INPUT_OUT_OF_RANGE) {
				statusEditText.setText(getString(R.string.out_of_range));
			} else if (errorState == Error.INPUT_INVALID_FORMAT) {
				statusEditText.setText(getString(R.string.invalid_format));
			} else if (errorState == Error.INPUT_ZERO_VALUES) {
				statusEditText.setText(getString(R.string.zero_values));
			} else if (errorState == Error.INPUT_INVALID) {
				statusEditText.setText(getString(R.string.input_invalid));
			} else if (errorState == Error.CASHBACK_NOT_SUPPORTED) {
				statusEditText
						.setText(getString(R.string.cashback_not_supported));
			} else if (errorState == Error.CRC_ERROR) {
				statusEditText.setText(getString(R.string.crc_error));
			} else if (errorState == Error.COMM_ERROR) {
				statusEditText.setText(getString(R.string.comm_error));
			} else if (errorState == Error.MAC_ERROR) {
				statusEditText.setText(getString(R.string.mac_error));
			} else if (errorState == Error.CMD_TIMEOUT) {
				statusEditText.setText(getString(R.string.cmd_timeout));
			}
		}

		@Override
		public void onReturnReversalData(String tlv) {
			TRACE.d("****QPOSServiceListener--->onReturnReversalData");
			
			if (mIsWorking == false) {
				return;
			}
			String content = getString(R.string.reversal_data);
			content += tlv;
			TRACE.i("listener: onReturnReversalData: " + tlv);
			statusEditText.setText(content);

		}

		@Override
		public void onReturnGetPinResult(Hashtable<String, String> result) {
			TRACE.d("****QPOSServiceListener--->onReturnGetPinResult");
			
			if (mIsWorking == false) {
				return;
			}
			String pinBlock = result.get("pinBlock");
			String pinKsn = result.get("pinKsn");
			String content = "get pin result\n";

			content += getString(R.string.pinKsn) + " " + pinKsn + "\n";
			content += getString(R.string.pinBlock) + " " + pinBlock + "\n";
			statusEditText.setText(content);
			TRACE.i(content);

		}

		@Override
		public void onReturnApduResult(boolean arg0, String arg1, int arg2) {
			// TODO Auto-generated method stub
			TRACE.d("****QPOSServiceListener--->onReturnApduResult");
		}

		@Override
		public void onReturnPowerOffIccResult(boolean arg0) {
			// TODO Auto-generated method stub
			TRACE.d("****QPOSServiceListener--->onReturnPowerOffIccResult");
		}

		@Override
		public void onReturnPowerOnIccResult(boolean arg0, String arg1,
				String arg2, int arg3) {
			// TODO Auto-generated method stub
			TRACE.d("****QPOSServiceListener--->onReturnPowerOnIccResult");
		}

		@Override
		public void onReturnSetSleepTimeResult(boolean isSuccess) {
			
			TRACE.d("****QPOSServiceListener--->onReturnSetSleepTimeResult");
			
			if (mIsWorking == false) {
				return;
			}
			String content = "";
			if (isSuccess) {
				content = "set the sleep time success.";
			} else {
				content = "set the sleep time failed.";
			}
			statusEditText.setText(content);

		}

		@Override
		public void onGetCardNoResult(String cardNo) {
			TRACE.d("****QPOSServiceListener--->onGetCardNoResult");
			
			if (mIsWorking == false) {
				return;
			}
			statusEditText.setText("cardNo: " + cardNo);
			ReceiptActivity.MaskedPAN = cardNo+"";
			mBtnAction.setVisibility(View.VISIBLE);
		}

		@Override
		public void onRequestCalculateMac(String calMac) {
			TRACE.d("****QPOSServiceListener--->onRequestCalculateMac");
			
			if (mIsWorking == false) {
				return;
			}
			statusEditText.setText("calMac: " + calMac);

		}

		@Override
		public void onRequestSignatureResult(byte[] arg0) {
			TRACE.d("****QPOSServiceListener--->onRequestSignatureResult");
		}

		@Override
		public void onRequestUpdateWorkKeyResult(UpdateInformationResult result) {
			TRACE.d("****QPOSServiceListener--->onRequestUpdateWorkKeyResult");
			
			if (mIsWorking == false) {
				return;
			}
			if (result == UpdateInformationResult.UPDATE_SUCCESS) {
				statusEditText.setText("update work key success");
			} else if (result == UpdateInformationResult.UPDATE_FAIL) {
				statusEditText.setText("update work key fail");
			} else if (result == UpdateInformationResult.UPDATE_PACKET_VEFIRY_ERROR) {
				statusEditText.setText("update work key packet vefiry error");
			} else if (result == UpdateInformationResult.UPDATE_PACKET_LEN_ERROR) {
				statusEditText.setText("update work key packet len error");
			}

		}

		@Override
		public void onReturnCustomConfigResult(boolean isSuccess, String result) {
			TRACE.d("****QPOSServiceListener--->onReturnCustomConfigResult");
			if (mIsWorking == false) {
				return;
			}
			String reString = "Failed";
			if (isSuccess) {
				reString = "Success";
			}
			statusEditText.setText("result: " + reString + "\ndata: " + result);

		}

		@Override
		public void onRequestSetPin() {
			TRACE.d("****QPOSServiceListener--->onRequestSetPin");
			
			if (mIsWorking == false) {
				return;
			}
			dismissDialog();

			dialog = new Dialog(SwipeCardActivity.this);
			dialog.setContentView(R.layout.pin_dialog);
			dialog.setTitle(getString(R.string.enter_pin));

			dialog.findViewById(R.id.confirmButton).setOnClickListener(
					new OnClickListener() {

						@Override
						public void onClick(View v) {
							String pin = ((EditText) dialog
									.findViewById(R.id.pinEditText)).getText()
									.toString();
							if (pin.length() >= 4 && pin.length() <= 12) {
								pos.sendPin(pin);
								dismissDialog();
							}
						}
					});

			dialog.findViewById(R.id.bypassButton).setOnClickListener(
					new OnClickListener() {

						@Override
						public void onClick(View v) {
							pos.emptyPin();
							dismissDialog();
						}
					});

			dialog.findViewById(R.id.cancelButton).setOnClickListener(
					new OnClickListener() {

						@Override
						public void onClick(View v) {
							isPinCanceled = true;
							pos.cancelPin();
							dismissDialog();
						}
					});
			dialog.setOnCancelListener(new OnCancelListener()
			{
				
				@Override
				public void onCancel(DialogInterface dialog)
				{
					isPinCanceled=true;
					pos.cancelPin();
					dismissDialog();
				}
			});
			dialog.show();

		}

		@Override
		public void onReturnSetMasterKeyResult(boolean isSuccess) {
			TRACE.d("****QPOSServiceListener--->onReturnSetMasterKeyResult");
			
			if (mIsWorking == false) {
				return;
			}
			statusEditText.setText("result: " + isSuccess);

		}

		@Override
		public void onReturnBatchSendAPDUResult(LinkedHashMap<Integer, String> batchAPDUResult) {
			
			TRACE.d("****QPOSServiceListener--->onReturnBatchSendAPDUResult");
			
			
			if (mIsWorking == false) {
				return;
			}
			StringBuilder sb = new StringBuilder();
			sb.append("APDU Responses: \n");
			for (HashMap.Entry<Integer, String> entry : batchAPDUResult
					.entrySet()) {
				sb.append("[" + entry.getKey() + "]: " + entry.getValue()
						+ "\n");
			}
			statusEditText.setText("\n" + sb.toString());

		}

		@Override
		public void onBluetoothBondFailed() {
			TRACE.d("****QPOSServiceListener--->onBluetoothBondFailed");
			
			if (mIsWorking == false) {
				return;
			}
			statusEditText.setText("bond failed");

		}

		@Override
		public void onBluetoothBondTimeout() {
			TRACE.d("****QPOSServiceListener--->onBluetoothBondTimeout");
			
			if (mIsWorking == false) {
				return;
			}
			statusEditText.setText("bond timeout");

		}

		@Override
		public void onBluetoothBonded() {
			TRACE.d("****QPOSServiceListener--->onBluetoothBonded");
			
			if (mIsWorking == false) {
				return;
			}
			statusEditText.setText("bond success");

		}

		@Override
		public void onBluetoothBonding() {
			TRACE.d("****QPOSServiceListener--->onBluetoothBonding");
			
			if (mIsWorking == false) {
				return;
			}
			statusEditText.setText("bonding .....");

		}

		@Override
		public void onReturniccCashBack(Hashtable<String, String> result) {
			TRACE.d("****QPOSServiceListener--->onReturniccCashBack");
			
			if (mIsWorking == false) {
				return;
			}
			String s = "serviceCode: " + result.get("serviceCode");
			s += "\n";
			s += "trackblock: " + result.get("trackblock");

			statusEditText.setText(s);

		}

		@Override
		public void onLcdShowCustomDisplay(boolean arg0) {
			// TODO Auto-generated method stub
			TRACE.d("****QPOSServiceListener--->onLcdShowCustomDisplay");
		}

		@Override
		public void onUpdatePosFirmwareResult(UpdateInformationResult arg0) {
			
			TRACE.d("****QPOSServiceListener--->onUpdatePosFirmwareResult");
			
			
			if (mIsWorking == false) {
				return;
			}
			if (arg0 == UpdateInformationResult.UPDATE_SUCCESS) {
				statusEditText.setText("升级完成");
			} else if (arg0 == UpdateInformationResult.UPDATE_LOWPOWER) {
				statusEditText.setText("电量");
			} else {
				statusEditText.setText("升级失败");
			}

		}

		@Override
		public void onGetPosComm(int arg0, String arg1, String arg2)
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPinKey_TDES_Result(String arg0)
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onReturnDownloadRsaPublicKey(HashMap<String, String> arg0)
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onUpdateMasterKeyResult(boolean arg0, Hashtable<String, String> arg1)
		{
			// TODO Auto-generated method stub
			
		}

	}

	private void clearDisplay() {
		statusEditText.setText("");
	}

	private String amount(String tradeAmount) {
		String rs = "";
		int a = 0;
		if (tradeAmount == null || "".equals(tradeAmount)) {
			return rs;
		}
		try {
			Integer.parseInt(tradeAmount);
		} catch (NumberFormatException e) {
			return rs;
		}
		TRACE.d("---------------:" + tradeAmount);
		if (tradeAmount.startsWith("0")) {
			return rs;
		}
		a = tradeAmount.length();
		if (tradeAmount.length() == 1) {
			rs = "0.0" + tradeAmount;
		} else if (tradeAmount.length() == 2) {
			rs = "0." + tradeAmount;
		} else if (tradeAmount.length() > 2) {
			rs = tradeAmount.substring(0, a - 2) + "."
					+ tradeAmount.substring(a - 2, a);
		}
		return rs;
	}

	private String terminalTime = "";
	private long startTime = 0;

	private void si_one() {
		LinkedHashMap<Integer, String[]> example = new LinkedHashMap<Integer, String[]>();
		String terminalTime = new SimpleDateFormat("yyyyMMddHHmmss")
				.format(Calendar.getInstance().getTime());

		example.put(1, new String[] { "FE", terminalTime,
				"00A404000FA0000003334355502D4D4F42494C45" });
		example.put(2, new String[] { "FE", terminalTime, "80E0000000" });
		example.put(
				3,
				new String[] {
						"FE",
						terminalTime,
						"00D68100404AC0680CDECDF183C0F8435ED4A34F15FE9DF64F7E289A05C0F8435ED4A34F15C0F8435ED4A34F15C0F8435ED4A34F15C0F8435ED4A34F15C0F8435ED4A34F15" });
		example.put(4, new String[] { "FE", terminalTime,
				"00D682001075681C57D50DC2940100FFFFFFFFFFFF" });// 保存csn
		example.put(5, new String[] { "FE", terminalTime, "00D683000101" });
		example.put(6, new String[] { "FE", terminalTime, "0084000008" });// 取随机数

		pos.VIPOSBatchSendAPDU(example);
	}

	private void si_two() {
		LinkedHashMap<Integer, String[]> example = new LinkedHashMap<Integer, String[]>();
		String terminalTime = new SimpleDateFormat("yyyyMMddHHmmss")
				.format(Calendar.getInstance().getTime());

		example.put(
				1,
				new String[] { "FE", terminalTime,
						"84F402201C841603538D516996FF92D085F82A1EC2C8D95EC3422ADCE075524904" });
		example.put(
				2,
				new String[] { "FE", terminalTime,
						"84F401131CC2E575D9FEBF0BC8E9B1848350C1A83ED707B462E19A38F7EF908312" });
		example.put(
				3,
				new String[] { "FE", terminalTime,
						"84F401141C6D17E17E576E577FF272F21B08DAACAB6BD70DED617328AFC3DC78B9" });
		example.put(
				4,
				new String[] { "FE", terminalTime,
						"84F401151CA12290798946A652373D849E996A2456FEAE5375A36398DCF582F340" });
		example.put(
				5,
				new String[] { "FE", terminalTime,
						"84F401161C1D8F56B75CC74FADF8453A42E31C1B420FDB9660C5EF19D6051E4865" });
		example.put(
				6,
				new String[] { "FE", terminalTime,
						"84F401171CAE6DD1EFB70D1818F272F21B08DAACAB6BD70DED617328AFCF6FF0E8" });
		example.put(7, new String[] { "FE", terminalTime, "8026000000" });

		pos.VIPOSBatchSendAPDU(example);
	}

	private void apduExample() {
		LinkedHashMap<Integer, String[]> example = new LinkedHashMap<Integer, String[]>();
		String terminalTime = new SimpleDateFormat("yyyyMMddHHmmss")
				.format(Calendar.getInstance().getTime());

		terminalTime = "20140517162926";
		example.put(1, new String[] { "13", terminalTime,
				"30303030303030303031303000000000" });
		example.put(2, new String[] { "13", terminalTime,
				"363231373939383830303030303030303631330000000000" });
		example.put(3, new String[] { "14", terminalTime, "06123456FFFFFFFF" });
		example.put(
				4,
				new String[] {
						"15",
						terminalTime,
						"323031343036303331373036333720373644414137333846383136383335373031303080000000008000000000000000" });
		// example.put(5, new String[] {"15",terminalTime,
		// "80FA070078000000000000000032303134303531373136323932362036324431413635374241334333333846443543414443353942363931333932412033374539364236433242454444444331383532303346373136443931413938464632303030303046203031303436444538423633383432303446383643413335"});
		// example.put(6, new String[] {"15",terminalTime,
		// "80FA010078303732364632463442323742373938394235443339304346333531344632333938363444303438343536374645393539363035334335354541353146323943333946333343464346333439463933363130363030302037353638314335374435304443323934303130308000000000008000000000000000"});

		pos.VIPOSBatchSendAPDU(example);
	}

	class MyOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			statusEditText.setText("");
			if (selectBTFlag) {
				// statusEditText.setText(R.string.wait);
				return;
			}
			if (v == doTradeButton) {
				if (pos == null) {
					statusEditText.setText(R.string.scan_bt_pos_error);
					return;
				}

				if (posType == POS_TYPE.BLUETOOTH) {
					if (blueTootchAddress == null
							|| "".equals(blueTootchAddress)) {
						statusEditText.setText(R.string.scan_bt_pos_error);
						return;
					}
				}

				isPinCanceled = false;
				amountEditText.setText("");
				statusEditText.setText(R.string.starting);

				pos.doTrade(60);
				
				String userData = "11223344556677889900aabbccddeeff";
				// pos.saveUserData(0, userData);

				// TODO
				String emvAppCfg = "0000000000000000000000000000000000000000000000000000f4f0f0faaffe8000010f00000000753000000000c350000000009c400000000003e8b6000000000003e8012260d8c8ff80f0300100000000000000000000000000000000100000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000fc50a820000400000000f850a8f8001432000013880000000000f4f0f0faaffe8000010f00000000753000000000c350000000009c400000000003e8b6000000000003e8012260d8c8ff80f03001a0000003330101000000000000000000070020050012345678901234424354455354203132333435363738616263640000000000000000000000015600015600015638333230314943434e4c2d475037333003039f37040000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000009f0802000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000010e0000fc78fcf8f00010000000fc78fcf8f01432000013880000000000f4f0f0faaffe8000010f00000000753000000000c350000000009c400000000003e8b6000000000003e8012260d8c8ff80f03001a0000003330101060000000000000000080020050012345678901234424354455354203132333435363738616263640000000000000000000000015600015600015638333230314943434e4c2d475037333003039f37040000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000009f0802000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000010e0000fc78fcf8f00010000000fc78fcf8f01432000013880000000000f4f0f0faaffe8000010f00000000753000000000c350000000009c400000000003e8b6000000000003e8012260d8c8ff80f03001a0000003330101030000000000000000080020050012345678901234424354455354203132333435363738616263640000000000000000000000015600015600015638333230314943434e4c2d475037333003039f37040000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000009f0802000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000010e0000fc78fcf8f00010000000fc78fcf8f01432000013880000000000f4f0f0faaffe8000010f00000000753000000000c350000000009c400000000003e8b6000000000003e8012260d8c8ff80f03001a0000003330101020000000000000000080020050012345678901234424354455354203132333435363738616263640000000000000000000000015600015600015638333230314943434e4c2d475037333003039f37040000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000009f0802000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000010e0000fc78fcf8f00010000000fc78fcf8f01432000013880000000000f4f0f0faaffe8000010f00000000753000000000c350000000009c400000000003e8b6000000000003e8012260d8c8ff80f03001a0000003330101010000000000000000080020050012345678901234424354455354203132333435363738616263640000000000000000000000015600015600015638333230314943434e4c2d475037333003039f37040000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000009f0802000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000010e0000";
				// pos.readEmvAppConfig();

				String emvCapkCfg = "a3767abd1b6aa69d7f3fbf28c092de9ed1e658ba5f0909af7a1ccd907373b7210fdeb16287ba8e78e1529f443976fd27f991ec67d95e5f4e96b127cab2396a94d6e45cda44ca4c4867570d6b07542f8d4bf9ff97975db9891515e66f525d2b3cbeb6d662bfb6c3f338e93b02142bfc44173a3764c56aadd202075b26dc2f9f7d7ae74bd7d00fd05ee430032663d27a5700000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000009000000303bb335a8549a03b87ab089d006f60852e4b806020211231a00000033302010100000000b0627dee87864f9c18c13b9a1f025448bf13c58380c91f4ceba9f9bcb214ff8414e9b59d6aba10f941c7331768f47b2127907d857fa39aaf8ce02045dd01619d689ee731c551159be7eb2d51a372ff56b556e5cb2fde36e23073a44ca215d6c26ca68847b388e39520e0026e62294b557d6470440ca0aefc9438c923aec9b2098d6d3a1af5e8b1de36f4b53040109d89b77cafaf70c26c601abdf59eec0fdc8a99089140cd2e817e335175b03b7aa33d000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000b000000387f0cd7c0e86f38f89a66f8c47071a8b88586f2620211231a00000033303010100000000bc853e6b5365e89e7ee9317c94b02d0abb0dbd91c05a224a2554aa29ed9fcb9d86eb9ccbb322a57811f86188aac7351c72bd9ef196c5a01acef7a4eb0d2ad63d9e6ac2e7836547cb1595c68bcbafd0f6728760f3a7ca7b97301b7e0220184efc4f653008d93ce098c0d93b45201096d1adff4cf1f9fc02af759da27cd6dfd6d789b099f16f378b6100334e63f3d35f3251a5ec78693731f5233519cdb380f5ab8c0f02728e91d469abd0eae0d93b1cc66ce127b29c7d77441a49d09fca5d6d9762fc74c31bb506c8bae3c79ad6c2578775b95956b5370d1d0519e37906b384736233251e8f09ad79dfbe2c6abfadac8e4d8624318c27daf1f8000003f527081cf371dd7e1fd4fa414a665036e0f5e6e520211231a00000033304010100000000b61645edfd5498fb246444037a0fa18c0f101ebd8efa54573ce6e6a7fbf63ed21d66340852b0211cf5eef6a1cd989f66af21a8eb19dbd8dbc3706d135363a0d683d046304f5a836bc1bc632821afe7a2f75da3c50ac74c545a754562204137169663cfcc0b06e67e2109eba41bc67ff20cc8ac80d7b6ee1a95465b3b2657533ea56d92d539e5064360ea4850fed2d1bf000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000090000003ee23b616c95c02652ad18860e48787c079e8e85a20301231a00000033308010100000000eb374dfc5a96b71d2863875eda2eafb96b1b439d3ece0b1826a2672eeefa7990286776f8bd989a15141a75c384dfc14fef9243aab32707659be9e4797a247c2f0b6d99372f384af62fe23bc54bcdc57a9acd1d5585c303f201ef4e8b806afb809db1a3db1cd112ac884f164a67b99c7d6e5a8a6df1d3cae6d7ed3d5be725b2de4ade23fa679bf4eb15a93d8a6e29c7ffa1a70de2e54f593d908a3bf9ebbd760bbfdc8db8b54497e6c5be0e4a4dac29e5000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000b0000003a075306eab0045baf72cdd33b3b678779de1f52720301231a00000033309010100000000b2ab1b6e9ac55a75adfd5bbc34490e53c4c3381f34e60e7fac21cc2b26dd34462b64a6fae2495ed1dd383b8138bea100ff9b7a111817e7b9869a9742b19e5c9dac56f8b8827f11b05a08eccf9e8d5e85b0f7cfa644eff3e9b796688f38e006deb21e101c01028903a06023ac5aab8635f8e307a53ac742bdce6a283f585f48ef00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000080000003c88be6b2417c4f941c9371ea35a377158767e4e320301231a0000003330a010100000000cf9fdf46b356378e9af311b0f981b21a1f22f250fb11f55c958709e3c7241918293483289eae688a094c02c344e2999f315a72841f489e24b1ba0056cfab3b479d0e826452375dcdbb67e97ec2aa66f4601d774feaef775accc621bfeb65fb0053fc5f392aa5e1d4c41a4de9ffdfdf1327c4bb874f1f63a599ee3902fe95e729fd78d4234dc7e6cf1ababaa3f6db29b7f05d1d901d2e76a606a8cbffffecbd918fa2d278bdb43b0434f5d45134be1c2781d157d501ff43e5f1c470967cd57ce53b64d82974c8275937c5d8502a1252a8a5d6088a259b694f98648d9af2cb0efd9d943c69f896d49fa39702162acb5af29b90bade005bc157f8000003bd331f9996a490b33c13441066a09ad3feb5f66c20301231a0000003330b010100000000cf9fdf46b356378e9af311b0f981b21a1f22f250fb11f55c958709e3c7241918293483289eae688a094c02c344e2999f315a72841f489e24b1ba0056cfab3b479d0e826452375dcdbb67e97ec2aa66f4601d774feaef775accc621bfeb65fb0053fc5f392aa5e1d4c41a4de9ffdfdf1327c4bb874f1f63a599ee3902fe95e729fd78d4234dc7e6cf1ababaa3f6db29b7f05d1d901d2e76a606a8cbffffecbd918fa2d278bdb43b0434f5d45134be1c2781d157d501ff43e5f1c470967cd57ce53b64d82974c8275937c5d8502a1252a8a5d6088a259b694f98648d9af2cb0efd9d943c69f896d49fa39702162acb5af29b90bade005bc157f8000003c9dbfa54a4ac5c7c947d4c8b5b08d90d0319541520301231a0000003330c010100000000";
				// pos.readEmvCapkConfig();
				// pos.updateEmvConfig(emvAppCfg,emvCapkCfg);

				terminalTime = new SimpleDateFormat("yyyyMMddHHmmss")
						.format(Calendar.getInstance().getTime());
				terminalTime = "20140517162926";
				// pos.doTrade(terminalTime, 60);
				
				String at = "5001000027a0123a814bfa90e2f9503787c68a28990395492d60b37fd5f8c719de093b1aabeb72350f42866c282a489ed5661c4d835fbf4e6b05698803bd596871afd2fea3ca01b0b3167acb90f20c8649d193696fa0e6b20bdc0ef7f79a637777f3eaa5e8531ff74085168f8f1f0946af072b0ecd099ba0908ccf3c4a8265f0ac67b70022bb227fd55397438f90e61768d1e83a450c39876bc42033eb7f883d4091c2fbcd88d08a0ae64fb07876ef98573f8101fabd3f7395292ff8b568dc2feeaa5e1f361c8b5c4d615926bd40191126fbf9513f619cdf5eae4f55b86b6861794e4e28381c5fb96cf933e8dedfb8c46f926c945a14e1669842fa138865970073799909d99b18daffc54d45e3803449e5f9f197930bc0f249801ff00bc25d257589c9b188d4cd638b2a3b1d21e0c9dcb690687f1b67860b75599ec0c0eb1f956bbf3fcecea6d6d372f346de08537f0c73165d8effffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff";
				// pos.udpateWorkKey(at);

				String str = "40EF23F8DA76D3DC3D1C1610D62AE6D9";
				str = "FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF";
				str = "11111111111111111111111111111111";
				// pos.setDesKey(str);
				// pos.doTrade_QF(0x0a, "345", "456");
				// pos.getCardNo();

				// pos.iccCashBack(terminalTime, "123");

				// pos.getIccCardNo(terminalTime);
				// pos.inquireECQAmount(terminalTime);

				// pos.doTrade(60);
				// pos.doCheckCard();
				String workKey = "B2BFE2651B174FCAEFD93008753C399B";
				String workKeyCheck = "6693C018";
				// pos.udpateWorkKey(workKey, workKeyCheck);

				// String pink = "9CCDD7EA7404FA3302940BF1A3E5E4B3";
				// String pinkcheckvalue = "7A58FDC500000000";
				// String trk = "433333EC043D5C6EAE3E4BA0925F2BE4";
				// String trkcheckvalue = "052336FE00000000";
				// pos.udpateWorkKey(pink, pinkcheckvalue, trk, trkcheckvalue,
				// "", "");

				// test update works
				String pik = "edcd448ed7ab138fe7815de037cc86b3";
				String pikCheck = "a106795415ca0522";

				pik = "edcd448ed7ab138fe7815de037cc8611";
				pikCheck = "a106795415ca0533";

				String trk = "9a2ece1d33f14b836d81ed326f258367";
				String trkCheck = "397271125ab5da08";

				String mak = "505675e0282dcdc2131f701a7630ef7a";
				String makCheck = "7d5b1fbd99ce34a8";
				// pos.udpateWorkKey(pik, pikCheck, trk, trkCheck, mak,
				// makCheck);
				
				// apduExample();

				str = "你好";
				//
				try {
					byte[] paras = str.getBytes("GBK");
					// pos.lcdShowCustomDisplay(LcdModeAlign.LCD_MODE_ALIGNCENTER,
					// QPOSUtil.byteArray2Hex(paras));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					TRACE.d("gbk error");
				}
			} else if (v == btnBT) {
				close();
				doScanBTPos();
			} else if (v == btnDisconnect) {
				if (mAdapter != null) {
					mAdapter.cancelDiscovery();
				}
				close();
			} else if (v == mBtnAction) {
				Intent intent = new Intent();
				intent.setClass(mContext, SignatureActivity.class);
				intent.putExtra("amount", mSuccessAmount);
				startActivity(intent);
				finish();
				// startActivityForResult(intent, VariablesComm.RESULT_OK);
			}
		}
	}

	public void onSelectBluetoothName(final ArrayList<String> btList) {
		dismissDialog();

		dialog = new Dialog(SwipeCardActivity.this);
		dialog.setContentView(R.layout.search_bt_name);
		dialog.setTitle(R.string.please_select_bt_name);

		String[] appNameList = new String[btList.size()];
		for (int i = 0; i < appNameList.length; ++i) {
			TRACE.d("i=" + i + "," + btList.get(i));
			appNameList[i] = btList.get(i).split(",")[0];
		}

		ListView btListView = (ListView) dialog.findViewById(R.id.btList);
		btListView.setAdapter(new ArrayAdapter<String>(SwipeCardActivity.this,
				android.R.layout.simple_list_item_1, appNameList));
		btListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				blueTootchAddress = btList.get(position).split(",")[1];
				dismissDialog();
				TRACE.d("blueTootchAddress:" + blueTootchAddress);
				sendMsg(1001);

			}

		});
		dialog.show();
	}

	private void sendMsg(int what) {
		Message msg = new Message();
		msg.what = what;
		mHandler.sendMessage(msg);
	}

	private boolean selectBTFlag = false;
	private long start_time = 0l;
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1001:
				btnBT.setEnabled(false);
				doTradeButton.setEnabled(false);
				selectBTFlag = true;
				statusEditText.setText(R.string.connecting_bt_pos);
				sendMsg(1002);
				break;
			case 1002:
				pos.connectBluetoothDevice(20, blueTootchAddress);
				btnBT.setEnabled(true);
				// doTradeButton.setEnabled(true);
				selectBTFlag = false;
				break;
			case 1003:
				pos.doTrade();
				break;
			default:
				break;
			}
		}
	};
}
