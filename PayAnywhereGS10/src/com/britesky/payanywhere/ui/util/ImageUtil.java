package com.britesky.payanywhere.ui.util;

import java.io.File;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.view.View;

import com.britesky.payanywhere.R;
import com.britesky.payanywhere.ui.util.SystemUtil;

import eu.janmuller.android.simplecropimage.CropImage;

public class ImageUtil {

    public static Drawable applyBrandColor(Context context, Drawable drawable) {
        drawable
            .mutate()
            .setColorFilter(SystemUtil.getBrandColor(context), android.graphics.PorterDuff.Mode.MULTIPLY);
        return drawable;
    }
    
    public static void launchImageChooser(Activity activity) {
        (new android.app.AlertDialog.Builder(activity))
            .setItems(R.array.img_src_entries, new PhotoOnClickListener(activity))
            .setOnCancelListener(new PhotoOnCancelListener(activity))
            .show();
    }
    
    public static void launcherClosed() {
    
    }
    
    public static boolean checkFsWritable(File file)
    {
        if (!file.isDirectory() && !file.mkdirs())
        {
            return false;
        } else
        {
            return file.canWrite();
        }
    }
    
    private static Intent a(Context context, Uri uri)
    {
        return (new Intent(context, CropImage.class)).putExtra("image-path", uri.getPath()).putExtra("scale", true);
    }

    private static Intent a(Intent intent, PhotoType photoType)
    {
        if (photoType == PhotoType.ITEM) {
            return intent.putExtra("aspectX", 1)
                    .putExtra("aspectY", 1)
                    .putExtra("outputX", 256)
                    .putExtra("outputY", 256);
        }
        else if (photoType == PhotoType.LOGO) {
            return intent.putExtra("aspectX", 2)
                    .putExtra("aspectY", 1)
                    .putExtra("outputX", 400)
                    .putExtra("outputY", 200);
        }
        
        return null;
    }
    
    public static void doCrop(Object obj, Uri uri, PhotoType photoType)
    {
        if (obj instanceof Activity)
        {
            Activity activity = (Activity)obj;
            activity.startActivityForResult(a(a(activity, uri), photoType), 2);
        } else
        if (obj instanceof Fragment)
        {
            Fragment fragment = (Fragment)obj;
            fragment.startActivityForResult(a(a(fragment.getActivity(), uri), photoType), 2);
            return;
        }
    }
    
    @SuppressLint("NewApi")
    public static void setBackgroundSafely(View view, Drawable drawable)
    {
        if (android.os.Build.VERSION.SDK_INT < 16)
        {
            view.setBackgroundDrawable(drawable);
            return;
        } else
        {
            view.setBackground(drawable);
            return;
        }
    }
    
    public static void startCamera(Object obj, Uri uri)
    {
        Intent intent = new Intent();
        intent.putExtra("output", uri);
        if (obj instanceof Activity)
        {
            Activity activity = (Activity)obj;
            intent.setClass(activity, CameraActivity.class);
            activity.startActivityForResult(intent, 0);
        } else
        if (obj instanceof Fragment)
        {
            Fragment fragment = (Fragment)obj;
            intent.setClass(fragment.getActivity(), CameraActivity.class);
            fragment.startActivityForResult(intent, 0);
            return;
        }
    }

    public static void startGallery(Object obj, Uri uri)
    {
        Intent intent = new Intent();
        intent.putExtra("IMAGE_OUTPUT_LOCATION", uri);
        if (obj instanceof Activity)
        {
            Activity activity = (Activity)obj;
//            intent.setClass(activity, com/nabancard/imagechooser/activity/BucketHomeFragmentActivity);
//            activity.startActivityForResult(intent, 1);
        } else
        if (obj instanceof Fragment)
        {
            Fragment fragment = (Fragment)obj;
//            intent.setClass(fragment.getActivity(), com/nabancard/imagechooser/activity/BucketHomeFragmentActivity);
//            fragment.startActivityForResult(intent, 1);
            return;
        }
    }
}
