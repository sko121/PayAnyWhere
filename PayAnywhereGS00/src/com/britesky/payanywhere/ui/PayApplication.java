package com.britesky.payanywhere.ui;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.app.Application;
import android.content.Context;

public class PayApplication extends Application {
    
    private static PayApplication mPayApplication;

    public void onCreate() {
        super.onCreate();
        mPayApplication = this;
        
        initImageLoader(getApplicationContext());
    }
    
    public static Context getContext() {
        return mPayApplication.getApplicationContext();
    }
    
    public static void initImageLoader(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getContext())
                                                .build();
        ImageLoader.getInstance().init(config);
    }
}
