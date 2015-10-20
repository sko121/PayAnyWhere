//package com.britesky.payanywhere.api;
//
//import android.text.TextUtils;
//import android.util.Base64;
//import java.security.InvalidKeyException;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//import javax.crypto.BadPaddingException;
//import javax.crypto.Cipher;
//import javax.crypto.IllegalBlockSizeException;
//import javax.crypto.NoSuchPaddingException;
//import javax.crypto.spec.SecretKeySpec;
//
////Referenced classes of package com.nabancard.api:
////         GeneralApi
//
//class CryptoHelper
//{
//
// CryptoHelper()
// {
// }
//
// public static String aesDecrypt(String s, byte abyte0[])
// {
//     SecretKeySpec secretkeyspec;
//     if (TextUtils.isEmpty(s))
//     {
//         return s;
//     }
//     if (abyte0 == null)
//     {
//         abyte0 = md5(GeneralApi.getEncryptionKey());
//     }
//     secretkeyspec = new SecretKeySpec(abyte0, "AES");
//     byte abyte1[];
//     Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding");
//     cipher.init(2, secretkeyspec);
//     abyte1 = cipher.doFinal(Base64.decode(s, 2));
//     Exception exception;
//     BadPaddingException badpaddingexception;
//     IllegalBlockSizeException illegalblocksizeexception;
//     InvalidKeyException invalidkeyexception;
//     NoSuchPaddingException nosuchpaddingexception;
//     NoSuchAlgorithmException nosuchalgorithmexception;
//     if (abyte1 == null)
//     {
//         return null;
//     } else
//     {
//         return new String(abyte1);
//     }
//     nosuchalgorithmexception;
//     nosuchalgorithmexception.printStackTrace();
//     return null;
//     nosuchpaddingexception;
//     nosuchpaddingexception.printStackTrace();
//     return null;
//     invalidkeyexception;
//     invalidkeyexception.printStackTrace();
//     return null;
//     illegalblocksizeexception;
//     illegalblocksizeexception.printStackTrace();
//     return null;
//     badpaddingexception;
//     badpaddingexception.printStackTrace();
//     return null;
//     exception;
//     return null;
// }
//
// public static String aesEncrypt(String s, byte abyte0[])
// {
//     SecretKeySpec secretkeyspec;
//     if (TextUtils.isEmpty(s))
//     {
//         return s;
//     }
//     if (abyte0 == null)
//     {
//         abyte0 = md5(GeneralApi.getEncryptionKey());
//     }
//     secretkeyspec = new SecretKeySpec(abyte0, "AES");
//     byte abyte1[];
//     Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding");
//     cipher.init(1, secretkeyspec);
//     abyte1 = cipher.doFinal(s.getBytes());
//     Exception exception;
//     BadPaddingException badpaddingexception;
//     IllegalBlockSizeException illegalblocksizeexception;
//     InvalidKeyException invalidkeyexception;
//     NoSuchPaddingException nosuchpaddingexception;
//     NoSuchAlgorithmException nosuchalgorithmexception;
//     if (abyte1 == null)
//     {
//         return null;
//     } else
//     {
//         return Base64.encodeToString(abyte1, 2);
//     }
//     nosuchalgorithmexception;
//     nosuchalgorithmexception.printStackTrace();
//     return null;
//     nosuchpaddingexception;
//     nosuchpaddingexception.printStackTrace();
//     return null;
//     invalidkeyexception;
//     invalidkeyexception.printStackTrace();
//     return null;
//     illegalblocksizeexception;
//     illegalblocksizeexception.printStackTrace();
//     return null;
//     badpaddingexception;
//     badpaddingexception.printStackTrace();
//     return null;
//     exception;
//     return null;
// }
//
// public static String bytesToHex(byte abyte0[])
// {
//     char ac[] = {
//         '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
//         'a', 'b', 'c', 'd', 'e', 'f'
//     };
//     char ac1[] = new char[2 * abyte0.length];
//     for (int i = 0; i < abyte0.length; i++)
//     {
//         int j = 0xff & abyte0[i];
//         ac1[i * 2] = ac[j >>> 4];
//         ac1[1 + i * 2] = ac[j & 0xf];
//     }
//
//     return new String(ac1);
// }
//
// public static byte[] hash(byte abyte0[], String s)
// {
//     MessageDigest messagedigest;
//     try
//     {
//         messagedigest = MessageDigest.getInstance(s);
//     }
//     catch (NoSuchAlgorithmException nosuchalgorithmexception)
//     {
//         nosuchalgorithmexception.printStackTrace();
//         return null;
//     }
//     messagedigest.update(abyte0, 0, abyte0.length);
//     return messagedigest.digest();
// }
//
// public static byte[] md5(byte abyte0[])
// {
//     return hash(abyte0, "MD5");
// }
//
// public static byte[] sha512(byte abyte0[])
// {
//     return hash(abyte0, "SHA-512");
// }
//}
//
