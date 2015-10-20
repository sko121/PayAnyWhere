package com.britesky.payanywhere.api;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
//import com.j256.ormlite.dao.Dao;
//import com.j256.ormlite.stmt.QueryBuilder;
//import com.j256.ormlite.stmt.Where;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.sql.SQLException;
import org.json.JSONArray;
import org.json.JSONObject;

import com.j256.ormlite.dao.Dao;

//Referenced classes of package com.nabancard.api:
//         HttpHelper2, GeneralApi, DbUserAccount, Vault, 
//         DbMerchantAccount, CoreDbHelper, DbMultimediaItem

public class ImageApi {

    public ImageApi() {
    }

//    public static boolean checkFileSystemWritable(File file) {
//        if (!file.isDirectory() && !file.mkdirs()) {
//            return false;
//        } else {
//            return file.canWrite();
//        }
//    }
//
//    private static InputStream getImage(String s)
// {
//     ByteArrayInputStream bytearrayinputstream;
//     JSONArray jsonarray = new JSONArray((new HttpHelper2()).send(s, HttpHelper2.Verb.Get, null, GeneralApi.getCurrentUser().getUserName(), GeneralApi.getCurrentUser().getLoginKey(), GeneralApi.isTestDrive()));
//     if (jsonarray.length() <= 0)
//     {
//         break MISSING_BLOCK_LABEL_74;
//     }
//     bytearrayinputstream = new ByteArrayInputStream(Base64.decode(jsonarray.getJSONObject(0).getString("image_data"), 2));
//     return bytearrayinputstream;
//     Exception exception;
//     exception;
//     exception.printStackTrace();
//     return null;
// }
//
//    public static InputStream getLineItemImage(long l) {
//        return getImage((new StringBuilder())
//                .append(Vault.getStoreFrontUri())
//                .append(Vault
//                        .getLineItemImageUrl()
//                        .replace(
//                                "{id}",
//                                Long.toString(GeneralApi.getCurrentMerchant()
//                                        .getNabPk()))
//                        .replace("{line_item_id}", Long.toString(l)))
//                .toString());
//    }
//
    static DbMultimediaItem getMultimediaItem(String s) {
        if (s == null) { return null; }
        
        DbMultimediaItem dbmultimediaitem = null;
        Dao dao;
        try {
            dao = GeneralApi.getDbHelper().getMultimediaItemDao();
            dbmultimediaitem = (DbMultimediaItem) dao.queryForFirst(dao
                    .queryBuilder().where().eq("_file_absolute_path", s)
                    .prepare());
        } catch (SQLException sqlexception) {
            sqlexception.printStackTrace();
            return null;
        }
        
        if (dbmultimediaitem == null) {
            dbmultimediaitem = new DbMultimediaItem(s);
            try {
                dao.create(dbmultimediaitem);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return dbmultimediaitem;
    }
//
//    public static InputStream getReceiptLogoImage() {
//        return getImage((new StringBuilder())
//                .append(Vault.getStoreFrontUri())
//                .append(Vault
//                        .getMerchantLogoImageUrl()
//                        .replace(
//                                "{id}",
//                                Long.toString(GeneralApi.getCurrentMerchant()
//                                        .getNabPk())).replace("{width}", "200")
//                        .replace("{height}", "200")).toString());
//    }
//
//    public static InputStream getSaleItemImage(long l) {
//        return getImage((new StringBuilder())
//                .append(Vault.getStoreFrontUri())
//                .append(Vault
//                        .getSaleItemImageUrl()
//                        .replace(
//                                "{id}",
//                                Long.toString(GeneralApi.getCurrentMerchant()
//                                        .getNabPk()))
//                        .replace("{sale_item_id}", Long.toString(l))
//                        .replace("{width}", "200").replace("{height}", "200"))
//                .toString());
//    }
//
//    public static InputStream getSignatureImage(long l) {
//        return getImage((new StringBuilder())
//                .append(Vault.getStoreFrontUri())
//                .append(Vault
//                        .getSignatureImageUrl()
//                        .replace(
//                                "{id}",
//                                Long.toString(GeneralApi.getCurrentMerchant()
//                                        .getNabPk()))
//                        .replace("{signature_id}", Long.toString(l)))
//                .toString());
//    }
//
//    static Bitmap scaleBitmapFromFile(String s) {
//        android.graphics.BitmapFactory.Options options = new android.graphics.BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
//        BitmapFactory.decodeFile(s, options);
//        int i = options.outHeight;
//        if (i == -1) {
//            return null;
//        } else {
//            int j = Math.round(i / 140);
//            options.inJustDecodeBounds = false;
//            options.inSampleSize = j;
//            return BitmapFactory.decodeFile(s, options);
//        }
//    }
}
