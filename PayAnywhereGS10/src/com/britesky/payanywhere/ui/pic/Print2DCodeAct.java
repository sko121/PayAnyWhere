package com.britesky.payanywhere.ui.pic;

import java.io.IOException;
import java.net.URL;
import com.britesky.payanywhere.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;



public class Print2DCodeAct extends Activity implements OnClickListener {
        private static final String TAG = "Print2DCodeAct";
        TextView back, print, motifiscan;
        TextView oId, money, price;
        ImageView printImg;
        ProgressDialog dialog;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                requestWindowFeature(Window.FEATURE_NO_TITLE);
                //setContentView(R.layout.activity_print2dcode);
                initView();
        }

        String extra;
        private void initView() {
                Intent intent = getIntent();
                extra = intent.getStringExtra("money");
                
//                back = (TextView) this.findViewById(R.id.back);
//                oId = (TextView) this.findViewById(R.id.order_id);
//                back.setOnClickListener(this);
//                motifiscan = (TextView) this.findViewById(R.id.motifiscan);
//                print = (TextView) this.findViewById(R.id.print_image);
//                printImg = (ImageView) this.findViewById(R.id.print_two_image);
//                money = (TextView) this.findViewById(R.id.money);
//                print.setOnClickListener(this);
//                motifiscan.setOnClickListener(this);
                
                money.setText(Html.fromHtml("￥<big>" + extra + "</big>"));
                
                //显示图片
//                Bitmap bitmapOrg = BitmapFactory.decodeResource(getResources(), R.drawable.alipay);
//                Bitmap bitmap = compressPic(bitmapOrg);
//                image_alipy.setImageBitmap(bitmap);
                
                //请求数据
                //getData();
                 

//                hideDialog();
           
        }

//        private void showDialog() {
//                if (dialog == null) {
//                        dialog = new ProgressDialog(this);
//                        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//                        dialog.setCancelable(true);
//                        dialog.setMessage("正在加载中，请稍候...");
//                }
//                dialog.show();
//        }
//        private void hideDialog() {
//                runOnUiThread(new Runnable() {
//                        
//                        @Override
//                        public void run() {
//                                if (dialog != null) {
//                                        dialog.dismiss();
//                                }
//                        }
//                });
//        }
        
        
        
        @Override
        protected void onResume() {
                super.onResume();
                //Constants.ACTIVITY_INSTANCE = Print2DCodeAct.this;
        }

        //List<ScanCodeRes> list = new ArrayList<ScanCodeRes>();
        Bitmap compressPic = null;
        String big_pic_url;
        String pic_url;
        String small_pic_url;
//        private void getData() {
//                float f = Float.parseFloat(extra) * 100;
//                int inte = (int) f;
//                long parseLong = Long.parseLong(String.valueOf(inte));
//                
//                String sign = Constants.md5("100512354" + "150039203" + Constants.KEY);
//                String url = "http://api.coupon.dev.wosai.cn/Upay/alipayQrCodeOffline";
//                AsyncHttpClient client = new AsyncHttpClient();
//                RequestParams params = new RequestParams();
//                
//                params.put("store_own_order_id", "1234");
//                params.put("subject", "喔噻体验商品");
//                params.put("total_fee", parseLong+"");
//                params.put("wosai_store_id", "100512354");
//                params.put("wosai_app_id", "150039203");
//                params.put("sign", sign);
//                params.put("notify_url", "http://www.woyouwaimai.com/get");   //推送支付成功的通知
//                Log.i(TAG, url + params);
                
//                client.get(url, params, new JsonHttpResponseHandler() {
//                        @Override
//                        public void onStart() {
//                                super.onStart();
//                                showDialog();
//                        }
//                        @Override
//                        public void onSuccess(JSONObject response) {
//                                super.onSuccess(response);
//                                try {
//                                        String code = response.getString("code");
//                                        String msg = response.getString("msg");
//                                        Log.i(TAG, "code:" + code);
//                                        Log.i(TAG, "msg:" + msg);
//                                        if ("10000".equals(code)) {
//                                                JSONObject data = response.getJSONObject("data");
//                                                String order_sn = data.getString("order_sn");
//                                                String wosai_store_id = data.getString("wosai_store_id");
//                                                int status = data.getInt("status");
//                                                String ctime = data.getString("ctime");
//                                                JSONObject order_pay_detail = data.getJSONObject("order_pay_detail");
//                                                String order_detail = data.getString("order_detail");
//                                                String pay_way = data.getString("pay_way");
//                                                long total_fee = data.getLong("total_fee");
//
//                                                String is_success = order_pay_detail.getString("is_success");
//                                                JSONObject responses = order_pay_detail.getJSONObject("response");
//                                                String sign = order_pay_detail.getString("sign");
//                                                String sign_type = order_pay_detail.getString("sign_type");
//
//                                                JSONObject alipay = responses.getJSONObject("alipay");
//                                                big_pic_url = alipay.getString("big_pic_url");
//                                                String out_trade_no = alipay.getString("out_trade_no");
//                                                pic_url = alipay.getString("pic_url");
//                                                String qr_code = alipay.getString("qr_code");
//                                                String result_code = alipay.getString("result_code");
//                                                small_pic_url = alipay.getString("small_pic_url");
//                                                String voucher_type = alipay.getString("voucher_type");
//                                                Log.i(TAG, "big_pic_url:" + big_pic_url);
//                                                Log.i(TAG, "pic_url:" + pic_url);
//                                                Log.i(TAG, "small_pic_url:" + small_pic_url);
//                                                
//                                                ScanCodeRes res = new ScanCodeRes();
//                                                res.setOrder_sn(order_sn);
//                                                res.setWosai_store_id(wosai_store_id);
//                                                res.setStatus(status);
//                                                res.setCtime(ctime);
//                                                res.setIs_success(is_success);
//                                                res.setOrder_detail(order_detail);
//                                                res.setTotal_fee(total_fee);
//                                                res.setPay_way(pay_way);
//                                                list.add(res);
//                                                
//                                                Constants.memoryCache.put(Constants.SCAN_CODE_RESULT, list);
//                                                
//                                        }
//                                } catch (JSONException e) {
//                                        e.printStackTrace();
//                                        hideDialog();
//                                }
//                        }
//
//                        @Override
//                        public void onFinish() {
//                                super.onFinish();
//                                ThreadPoolManager.getInstance().executeTask(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                                try {
//                                                        Bitmap bitmap = BitmapFactory.decodeStream(new URL(pic_url).openStream());
//                                                        compressPic = PicFromPrintUtils.compressPic(bitmap);
//                                                }catch (IOException e) {
//                                                        e.printStackTrace();
//                                                }
//                                                runOnUiThread(new Runnable() {
//                                                        
//                                                        @Override
//                                                        public void run() {
//                                                                printImg.setImageBitmap(compressPic);
//                                                                print2Code(compressPic);
//                                                                hideDialog();
//                                                        }
//                                                });
//                                                
//                                        }
//                                });
//                        }
//
//                });
//        }
        
        //打印二维码
        private void print2Code(Bitmap bitmap){
//                final byte[] bs = PicFromPrintUtils.draw2PxPoint(bitmap);
//                try {
//                    PrintUtil.printAlipayTitle(bs, extra);
//            } catch (Exception e) {
//                    e.printStackTrace();
//            }
//                ThreadPoolManager.getInstance().executeTask(new Runnable() {
//                        @Override
//                        public void run() {
//                                
//                        }
//                });
        }
        
        @Override
        public void onClick(View v) {
//                switch (v.getId()) 
//                {
//                case R.id.print_image:
//                        if ( compressPic != null ){
//                                print2Code(compressPic);
//                        }
//                        break;
//
//                case R.id.back:
//                        finish();
//                        break;
//
//                case R.id.motifiscan:
//                        Intent intent = new Intent(this, HomeAct.class);
//                        intent.putExtra("money", extra);
//                        startActivity(intent);
//                        break;
//                }
        }
        
        public Drawable loadImageFromNetwork(String urladdr) {
                Drawable drawable = null;
                try {
                        drawable = Drawable.createFromStream(new URL(urladdr).openStream(),"image.jpg");
                } catch (IOException e) {
                        Log.d("test", e.getMessage());
                }
                if (drawable == null) {
                        Log.d("test", "null drawable");
                } else {
                        Log.d("test", "not null drawable");
                }
                return drawable;
        }
        
        // 计算图片的缩放值
        public static int calculateInSampleSize(BitmapFactory.Options options,
                        int reqWidth, int reqHeight) {
                final int height = options.outHeight;
                final int width = options.outWidth;
                int inSampleSize = 1;
                if (height > reqHeight || width > reqWidth) {
                        final int heightRatio = Math.round((float) height
                                        / (float) reqHeight);
                        final int widthRatio = Math.round((float) width / (float) reqWidth);
                        inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
                }
                return inSampleSize;
        }

}

