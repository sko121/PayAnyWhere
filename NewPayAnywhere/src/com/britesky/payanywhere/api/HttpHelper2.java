//package com.britesky.payanywhere.api;
//
//import android.content.Context;
//import android.content.Intent;
//import android.util.Base64;
//import android.util.Log;
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.UnsupportedEncodingException;
//import java.security.InvalidKeyException;
//import java.security.NoSuchAlgorithmException;
//import java.util.Date;
//import java.util.UUID;
//import javax.crypto.BadPaddingException;
//import javax.crypto.Cipher;
//import javax.crypto.IllegalBlockSizeException;
//import javax.crypto.Mac;
//import javax.crypto.NoSuchPaddingException;
//import javax.crypto.spec.SecretKeySpec;
//import org.apache.http.Header;
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpDelete;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.client.methods.HttpPut;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.impl.cookie.DateUtils;
//import org.apache.http.params.BasicHttpParams;
//import org.apache.http.params.HttpConnectionParams;
//import org.apache.http.util.EntityUtils;
//import org.json.JSONException;
//import org.json.JSONObject;
//
////Referenced classes of package com.nabancard.api:
////         CryptoHelper, GeneralApi, Util, HttpPatch
//
//class HttpHelper2 {
//
//    private final int TIME_OUT_MS = 60000;
//
//    HttpHelper2() {
//    }
//
//    private static String aesDecryptResponse(String s, String s1) {
//        SecretKeySpec secretkeyspec = new SecretKeySpec(s1.getBytes(), "AES");
//        byte abyte0[];
//        Cipher cipher;
//        try {
//            cipher = Cipher.getInstance("AES/ECB/PKCS7Padding");
//            cipher.init(2, secretkeyspec);
//            abyte0 = cipher.doFinal(Base64.decode(s, 0));
//            if (abyte0 == null) {
//                return null;
//            } else {
//                return new String(abyte0);
//            }
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (NoSuchPaddingException e) {
//            e.printStackTrace();
//        } catch (InvalidKeyException e) {
//            e.printStackTrace();
//        } catch (IllegalBlockSizeException e) {
//            e.printStackTrace();
//        } catch (BadPaddingException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    private static String aesEncryptRequest(String s, String s1)
//    {
//        try {
//            SecretKeySpec secretkeyspec = new SecretKeySpec(s1.getBytes(), "AES");
//            byte abyte0[];
//            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding");
//            cipher.init(1, secretkeyspec);
//            abyte0 = cipher.doFinal(s.getBytes("UTF-8"));
//
//            if (abyte0 == null)
//            {
//                return null;
//            } else
//            {
//                return Base64.encodeToString(abyte0, 2);
//            }
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (NoSuchPaddingException e) {
//            e.printStackTrace();
//        } catch (InvalidKeyException e) {
//            e.printStackTrace();
//        } catch (IllegalBlockSizeException e) {
//            e.printStackTrace();
//        } catch (BadPaddingException e) {
//            e.printStackTrace();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    private static String hmacMd5(byte abyte0[], String s) {
//        SecretKeySpec secretkeyspec = new SecretKeySpec(s.getBytes(), "ASCII");
//        Mac mac;
//        try {
//            mac = Mac.getInstance("HMACMD5");
//            mac.init(secretkeyspec);
//        } catch (NoSuchAlgorithmException nosuchalgorithmexception) {
//            nosuchalgorithmexception.printStackTrace();
//            return null;
//        } catch (InvalidKeyException invalidkeyexception) {
//            invalidkeyexception.printStackTrace();
//            return null;
//        }
//        return CryptoHelper.bytesToHex(mac.doFinal(abyte0));
//    }
//
//    public String convertStreamToString(InputStream inputstream)
//    {
//        StringBuilder stringbuilder = new StringBuilder();
//        BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(inputstream));
//        _L1:
//            String s = bufferedreader.readLine();
//        if (s == null)
//        {
//            break MISSING_BLOCK_LABEL_52;
//        }
//        stringbuilder.append(s);
//        goto _L1
//        IOException ioexception;
//        ioexception;
//        ioexception.printStackTrace();
//        return stringbuilder.toString();
//    }
//
//    public String send(String s, Verb verb, String s1, String s2, String s3, boolean flag)
//    {
//        int i;
//        String s4;
//        String s5;
//        if (GeneralApi.isDebuggable())
//        {
//            Util.logJson((new StringBuilder("URL: ")).append(s).toString());
//            Util.logJson((new StringBuilder("Request: ")).append(s1).toString());
//        }
//        i = 0;
//        s4 = DateUtils.formatDate(new Date());
//        s5 = UUID.randomUUID().toString().replace("-", "");
//        if (s1 == null)
//        {
//            s1 = "";
//        }
//        String s7 = CryptoHelper.bytesToHex(CryptoHelper.md5(s1.getBytes("UTF-8")));
//        if (s3 == null) goto _L2; else goto _L1
//        _L1:
//            String s8 = aesEncryptRequest(s1, s3);
//        _L54:
//            _cls1..SwitchMap.com.nabancard.api.HttpHelper2.Verb[verb.ordinal()];
//        JVM INSTR tableswitch 1 5: default 1651
//        //                   1 622
//        //                   2 666
//        //                   3 710
//        //                   4 754
//        //                   5 798;
//        goto _L3 _L4 _L5 _L6 _L7 _L8
//        _L21:
//            String s9;
//        Log.d("HTTP LOGIN ACCOUNTS signatureData", s9);
//        JSONException jsonexception;
//        String s6;
//        JSONException jsonexception1;
//        IOException ioexception;
//        IOException ioexception1;
//        String s10;
//        String s11;
//        String s12;
//        String s13;
//        String s14;
//        String s15;
//        DefaultHttpClient defaulthttpclient;
//        HttpResponse httpresponse;
//        StringEntity stringentity;
//        HttpDelete httpdelete;
//        HttpResponse httpresponse1;
//        HttpResponse httpresponse2;
//        String s16;
//        String s18;
//        HttpPut httpput;
//        HttpPost httppost;
//        HttpPatch httppatch;
//        HttpGet httpget;
//        if (s3 == null)
//        {
//            s10 = "NO KEY";
//        } else
//        {
//            s10 = s3;
//        }
//        Log.d("HTTP LOGIN ACCOUNTS key", s10);
//        if (s3 == null) goto _L10; else goto _L9
//        _L9:
//            s11 = hmacMd5(s9.getBytes("UTF-8"), s3);
//        _L53:
//            s12 = Base64.encodeToString(s7.getBytes("UTF-8"), 2);
//        s13 = Base64.encodeToString(s2.getBytes("UTF-8"), 2);
//        Log.d("HTTP LOGIN ACCOUNTS raw nonce", s5);
//        s14 = Base64.encodeToString(s5.getBytes("UTF-8"), 2);
//        if (s11 == null) goto _L12; else goto _L11
//        _L11:
//            s15 = Base64.encodeToString(s11.getBytes("UTF-8"), 2);
//        _L52:
//            Log.d("HTTP LOGIN ACCOUNTS contentMd5Header", s12);
//        Log.d("HTTP LOGIN ACCOUNTS usernameHeader", s13);
//        Log.d("HTTP LOGIN ACCOUNTS nonceHeader", s14);
//        Log.d("HTTP LOGIN ACCOUNTS signatureHeader", s15);
//        BasicHttpParams basichttpparams = new BasicHttpParams();
//        HttpConnectionParams.setConnectionTimeout(basichttpparams, 60000);
//        HttpConnectionParams.setSoTimeout(basichttpparams, 60000);
//        defaulthttpclient = new DefaultHttpClient(basichttpparams);
//        httpresponse = null;
//        stringentity = null;
//        if (s8 == null)
//        {
//            break MISSING_BLOCK_LABEL_349;
//        }
//        stringentity = new StringEntity(s8);
//        _cls1..SwitchMap.com.nabancard.api.HttpHelper2.Verb[verb.ordinal()];
//        JVM INSTR tableswitch 1 5: default 1658
//        //                   1 842
//        //                   2 978
//        //                   3 1121
//        //                   4 1264
//        //                   5 1407;
//        goto _L13 _L14 _L15 _L16 _L17 _L18
//        _L51:
//            s6 = "";
//        if (httpresponse2.getEntity() != null && httpresponse2.getEntity().getContentLength() < 1000L)
//        {
//            s6 = EntityUtils.toString(httpresponse2.getEntity());
//        }
//        s16 = "";
//        if (httpresponse2 == null) goto _L20; else goto _L19
//        _L19:
//            s16 = httpresponse2.getHeaders("Content-Type")[0].getValue();
//        _L20:
//            if (!s16.equals("application/encrypted-json"))
//            {
//                break MISSING_BLOCK_LABEL_489;
//            }
//        s18 = aesDecryptResponse(s6, s3);
//        s6 = s18;
//        if (s6.startsWith("{"))
//        {
//            JSONObject jsonobject = new JSONObject(s6);
//            if (jsonobject.has("error_type"))
//            {
//                String s17 = jsonobject.getString("error_type");
//                if (s17.equals("UsernameNotFound") || s17.equals("ChecksumFailed") || s17.equals("SignatureMismatch") || s17.equals("NonceInUse"))
//                {
//                    GeneralApi.getContext().sendBroadcast(new Intent("com.nabancard.ui.LOGOUT"));
//                }
//            }
//        }
//        _L48:
//            if (GeneralApi.isDebuggable())
//            {
//                Util.logJson((new StringBuilder("Response: ")).append(s6).toString());
//            }
//        return s6;
//        _L4:
//            s9 = (new StringBuilder()).append(s2).append(s4).append(s5).append("GET").append(s).append(s1).toString();
//        goto _L21
//        _L5:
//            s9 = (new StringBuilder()).append(s2).append(s4).append(s5).append("PATCH").append(s).append(s1).toString();
//        goto _L21
//        _L6:
//            s9 = (new StringBuilder()).append(s2).append(s4).append(s5).append("POST").append(s).append(s1).toString();
//        goto _L21
//        _L7:
//            s9 = (new StringBuilder()).append(s2).append(s4).append(s5).append("PUT").append(s).append(s1).toString();
//        goto _L21
//        _L8:
//            s9 = (new StringBuilder()).append(s2).append(s4).append(s5).append("DELETE").append(s).append(s1).toString();
//        goto _L21
//        _L14:
//            httpget = new HttpGet(s);
//        httpget.setHeader("Content-MD5", s12);
//        httpget.setHeader("Date", s4);
//        httpget.setHeader("X-NabWss-Username", s13);
//        httpget.setHeader("X-NabWss-Nonce", s14);
//        httpget.setHeader("X-NabWss-Signature", s15);
//        if (!flag) goto _L23; else goto _L22
//        _L22:
//            httpget.setHeader("X-TestDriveMode", "1");
//        httpget.setHeader("X-StubbedResponseCode", "APR");
//        _L23:
//            httpget.setHeader("Content-Type", "application/json");
//        _L27:
//            if (httpresponse == null) goto _L25; else goto _L24
//            _L24:
//                if (httpresponse.getEntity() != null)
//                {
//                    break; /* Loop/switch isn't completed */
//                }
//        _L25:
//            if (i >= 5)
//            {
//                break; /* Loop/switch isn't completed */
//            }
//                httpresponse = defaulthttpclient.execute(httpget);
//                i++;
//                if (true) goto _L27; else goto _L26
//                _L15:
//                    httppatch = new HttpPatch(s);
//                httppatch.setEntity(stringentity);
//                httppatch.setHeader("Content-MD5", s12);
//                httppatch.setHeader("Date", s4);
//                httppatch.setHeader("X-NabWss-Username", s13);
//                httppatch.setHeader("X-NabWss-Nonce", s14);
//                httppatch.setHeader("X-NabWss-Signature", s15);
//                if (!flag) goto _L29; else goto _L28
//                _L28:
//                    httppatch.setHeader("X-TestDriveMode", "1");
//                httppatch.setHeader("X-StubbedResponseCode", "APR");
//                _L29:
//                    httppatch.setHeader("Content-Type", "application/json");
//                _L32:
//                    if (httpresponse == null) goto _L31; else goto _L30
//                    _L30:
//                        if (httpresponse.getEntity() != null)
//                        {
//                            break; /* Loop/switch isn't completed */
//                        }
//                _L31:
//                    if (i >= 5)
//                    {
//                        break; /* Loop/switch isn't completed */
//                    }
//                        httpresponse = defaulthttpclient.execute(httppatch);
//                        i++;
//                        if (true) goto _L32; else goto _L26
//                        _L16:
//                            httppost = new HttpPost(s);
//                        httppost.setEntity(stringentity);
//                        httppost.setHeader("Content-MD5", s12);
//                        httppost.setHeader("Date", s4);
//                        httppost.setHeader("X-NabWss-Username", s13);
//                        httppost.setHeader("X-NabWss-Nonce", s14);
//                        httppost.setHeader("X-NabWss-Signature", s15);
//                        if (!flag) goto _L34; else goto _L33
//                        _L33:
//                            httppost.setHeader("X-TestDriveMode", "1");
//                        httppost.setHeader("X-StubbedResponseCode", "APR");
//                        _L34:
//                            httppost.setHeader("Content-Type", "application/json");
//                        _L37:
//                            if (httpresponse == null) goto _L36; else goto _L35
//                            _L35:
//                                if (httpresponse.getEntity() != null)
//                                {
//                                    break; /* Loop/switch isn't completed */
//                                }
//                        _L36:
//                            if (i >= 5)
//                            {
//                                break; /* Loop/switch isn't completed */
//                            }
//                                httpresponse = defaulthttpclient.execute(httppost);
//                                i++;
//                                if (true) goto _L37; else goto _L26
//                                _L17:
//                                    httpput = new HttpPut(s);
//                                httpput.setEntity(stringentity);
//                                httpput.setHeader("Content-MD5", s12);
//                                httpput.setHeader("Date", s4);
//                                httpput.setHeader("X-NabWss-Username", s13);
//                                httpput.setHeader("X-NabWss-Nonce", s14);
//                                httpput.setHeader("X-NabWss-Signature", s15);
//                                if (!flag) goto _L39; else goto _L38
//                                _L38:
//                                    httpput.setHeader("X-TestDriveMode", "1");
//                                httpput.setHeader("X-StubbedResponseCode", "APR");
//                                _L39:
//                                    httpput.setHeader("Content-Type", "application/json");
//                                _L42:
//                                    if (httpresponse == null) goto _L41; else goto _L40
//                                    _L40:
//                                        if (httpresponse.getEntity() != null)
//                                        {
//                                            break; /* Loop/switch isn't completed */
//                                        }
//                                _L41:
//                                    if (i >= 5)
//                                    {
//                                        break; /* Loop/switch isn't completed */
//                                    }
//                                        httpresponse = defaulthttpclient.execute(httpput);
//                                        i++;
//                                        if (true) goto _L42; else goto _L26
//                                        _L18:
//                                            httpdelete = new HttpDelete(s);
//                                        httpdelete.setHeader("Content-MD5", s12);
//                                        httpdelete.setHeader("Date", s4);
//                                        httpdelete.setHeader("X-NabWss-Username", s13);
//                                        httpdelete.setHeader("X-NabWss-Nonce", s14);
//                                        httpdelete.setHeader("X-NabWss-Signature", s15);
//                                        if (!flag) goto _L44; else goto _L43
//                                        _L43:
//                                            httpdelete.setHeader("X-TestDriveMode", "1");
//                                        httpdelete.setHeader("X-StubbedResponseCode", "APR");
//                                        _L44:
//                                            httpdelete.setHeader("Content-Type", "application/json");
//                                        _L47:
//                                            if (httpresponse == null) goto _L46; else goto _L45
//                                            _L45:
//                                                if (httpresponse.getEntity() != null)
//                                                {
//                                                    break; /* Loop/switch isn't completed */
//                                                }
//                                        _L46:
//                                            if (i >= 5)
//                                            {
//                                                break; /* Loop/switch isn't completed */
//                                            }
//                                                httpresponse1 = defaulthttpclient.execute(httpdelete);
//                                                httpresponse = httpresponse1;
//                                                i++;
//                                                if (true) goto _L47; else goto _L26
//                                                ioexception;
//                                                s6 = "";
//                                                ioexception1 = ioexception;
//                                                _L50:
//                                                    ioexception1.printStackTrace();
//                                                GeneralApi.getContext().sendBroadcast(new Intent("com.nabancard.ui.HTTP_REQUEST_ERROR"));
//                                                goto _L48
//                                                jsonexception;
//                                                s6 = "";
//                                                jsonexception1 = jsonexception;
//                                                _L49:
//                                                    jsonexception1.printStackTrace();
//                                                GeneralApi.getContext().sendBroadcast(new Intent("com.nabancard.ui.HTTP_REQUEST_ERROR"));
//                                                goto _L48
//                                                jsonexception1;
//                                                goto _L49
//                                                ioexception1;
//                                                goto _L50
//                                                _L26:
//                                                    httpresponse2 = httpresponse;
//                                                goto _L51
//                                                _L12:
//                                                    s15 = "";
//                                                goto _L52
//                                                _L10:
//                                                    s11 = null;
//                                                goto _L53
//                                                _L2:
//                                                    s8 = null;
//                                                goto _L54
//                                                _L3:
//                                                    s9 = "";
//                                                goto _L21
//                                                _L13:
//                                                    httpresponse2 = null;
//                                                goto _L51
//    }
//
//    private class _cls1 {
//
//        static final int $SwitchMap$com$nabancard$api$HttpHelper2$Verb[];
//
//        static {
//            $SwitchMap$com$nabancard$api$HttpHelper2$Verb = new int[Verb
//                                                                    .values().length];
//            try {
//                $SwitchMap$com$nabancard$api$HttpHelper2$Verb[Verb.Get
//                                                              .ordinal()] = 1;
//            } catch (NoSuchFieldError nosuchfielderror) {
//            }
//            try {
//                $SwitchMap$com$nabancard$api$HttpHelper2$Verb[Verb.Patch
//                                                              .ordinal()] = 2;
//            } catch (NoSuchFieldError nosuchfielderror1) {
//            }
//            try {
//                $SwitchMap$com$nabancard$api$HttpHelper2$Verb[Verb.Post
//                                                              .ordinal()] = 3;
//            } catch (NoSuchFieldError nosuchfielderror2) {
//            }
//            try {
//                $SwitchMap$com$nabancard$api$HttpHelper2$Verb[Verb.Put
//                                                              .ordinal()] = 4;
//            } catch (NoSuchFieldError nosuchfielderror3) {
//            }
//            try {
//                $SwitchMap$com$nabancard$api$HttpHelper2$Verb[Verb.Delete
//                                                              .ordinal()] = 5;
//            } catch (NoSuchFieldError nosuchfielderror4) {
//                return;
//            }
//        }
//    }
//
//    private class Verb extends Enum {
//
//        private static final Verb $VALUES[];
//        public static final Verb Delete;
//        public static final Verb Get;
//        public static final Verb Patch;
//        public static final Verb Post;
//        public static final Verb Put;
//
//        public static Verb valueOf(String s) {
//            return (Verb) Enum.valueOf(
//                    com / nabancard / api / HttpHelper2$Verb, s);
//        }
//
//        public static Verb[] values() {
//            return (Verb[]) $VALUES.clone();
//        }
//
//        static {
//            Get = new Verb("Get", 0);
//            Patch = new Verb("Patch", 1);
//            Post = new Verb("Post", 2);
//            Put = new Verb("Put", 3);
//            Delete = new Verb("Delete", 4);
//            Verb averb[] = new Verb[5];
//            averb[0] = Get;
//            averb[1] = Patch;
//            averb[2] = Post;
//            averb[3] = Put;
//            averb[4] = Delete;
//            $VALUES = averb;
//        }
//
//        private Verb(String s, int i) {
//            super(s, i);
//        }
//    }
//
//}
