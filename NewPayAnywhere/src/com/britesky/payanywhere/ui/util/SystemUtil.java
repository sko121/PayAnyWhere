package com.britesky.payanywhere.ui.util;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import com.britesky.payanywhere.R;
import com.britesky.payanywhere.ui.AppName;

public final class SystemUtil
{

    public SystemUtil()
    {
    }

    private static String a(String s)
    {
        if (s == null || s.length() == 0)
        {
            s = "";
        } else
        {
            char c = s.charAt(0);
            if (!Character.isUpperCase(c))
            {
                return (new StringBuilder()).append(Character.toUpperCase(c)).append(s.substring(1)).toString();
            }
        }
        return s;
    }

    public static String getAppCurrentLanguage(Context context)
    {
        return context.getResources().getConfiguration().locale.getLanguage();
    }

    public static AppName getBrand(Context context)
    {
        if ("ban.card.payanywhere".equals(context.getPackageName()))
        {
            return AppName.PAYANYWHERE;
        }
        if ("ban.card.phoneswipe".equals(context.getPackageName()))
        {
            return AppName.PHONESWIPE;
        } else
        {
            return null;
        }
    }

    public static int getBrandColor(Context context)
    {
        if (getBrand(context) == AppName.PAYANYWHERE)
        {
            return context.getResources().getColor(R.color.payanywhere);
        } else
        {
            return context.getResources().getColor(R.color.phoneswipe);
        }
    }

    public static String getDeviceCurrentLocaleCountry(Context context)
    {
        return context.getResources().getConfiguration().locale.getCountry();
    }

    public static String getDeviceName()
    {
        String s = Build.MANUFACTURER;
        String s1 = Build.MODEL;
        if (s1.startsWith(s))
        {
            return a(s1);
        } else
        {
            return (new StringBuilder()).append(a(s)).append(" ").append(s1).toString();
        }
    }

    public static boolean hasImageCaptureBug()
    {
        ArrayList arraylist = new ArrayList();
        arraylist.add("google/mantaray/manta");
        arraylist.add("google/razor/flo");
        return arraylist.contains((new StringBuilder()).append(Build.BRAND).append("/").append(Build.PRODUCT).append("/").append(Build.DEVICE).toString());
    }

    public static boolean isAirplaneModeOn(Context context)
    {
//        if (android.os.Build.VERSION.SDK_INT >= 17) goto _L2; else goto _L1;
//_L1:
//        if (android.provider.Settings.System.getInt(context.getContentResolver(), "airplane_mode_on", 0) == 0) goto _L4; else goto _L3;
//_L3:
//        return true;
//_L4:
//        return false;
//_L2:
//        if (android.provider.Settings.Global.getInt(context.getContentResolver(), "airplane_mode_on", 0) == 0)
//        {
//            return false;
//        }
//        if (true) goto _L3; else goto _L5;
//_L5:
        return false;
    }

    public static boolean isConnectedToInternet(Context context)
    {
        ConnectivityManager connectivitymanager = (ConnectivityManager)context.getSystemService("connectivity");
        if (connectivitymanager != null)
        {
            NetworkInfo anetworkinfo[] = connectivitymanager.getAllNetworkInfo();
            if (anetworkinfo != null)
            {
                int i = anetworkinfo.length;
                for (int j = 0; j < i; j++)
                {
                    NetworkInfo networkinfo = anetworkinfo[j];
                    if ((networkinfo.getType() == 0 || networkinfo.getType() == 1) && networkinfo.getState() == android.net.NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }
                }

            }
        }
        return false;
    }

    public static boolean isConnectivitySufficient(Context context)
    {
label0:
        {
            ConnectivityManager connectivitymanager = (ConnectivityManager)context.getSystemService("connectivity");
            if (connectivitymanager == null)
            {
                break label0;
            }
            NetworkInfo networkinfo = connectivitymanager.getActiveNetworkInfo();
            if (networkinfo == null)
            {
                break label0;
            }
            if (networkinfo.getType() == 1)
            {
                WifiManager wifimanager = (WifiManager)context.getSystemService("wifi");
                if (wifimanager.getWifiState() != 3)
                {
                    break label0;
                }
                Iterator iterator = wifimanager.getScanResults().iterator();
                int i;
                do
                {
                    ScanResult scanresult;
                    do
                    {
                        if (!iterator.hasNext())
                        {
                            break label0;
                        }
                        scanresult = (ScanResult)iterator.next();
                    } while (!scanresult.BSSID.equals(wifimanager.getConnectionInfo().getBSSID()));
                    i = (100 * WifiManager.calculateSignalLevel(wifimanager.getConnectionInfo().getRssi(), scanresult.level)) / scanresult.level;
                } while (i < 100 && i < 75 && i < 50);
                return true;
            }
            if (networkinfo.getType() == 0 && networkinfo.getState() == android.net.NetworkInfo.State.CONNECTED)
            {
                return true;
            }
        }
        return false;
    }

    public static boolean isJeffrey(Context context)
    {
        return getDeviceName().equals("Samsung SPH-L710") || getDeviceName().equals("Samsung SCH-I535") || getDeviceName().equals("Samsung SCH-R530") || getDeviceName().equals("Samsung SGH-T999") || getDeviceName().equals("Samsung SGH-I747");
    }

    public static boolean isLocationEnabled(Context context)
    {
        LocationManager locationmanager = (LocationManager)context.getSystemService("location");
        return locationmanager.isProviderEnabled("gps") || locationmanager.isProviderEnabled("network");
    }

    public static boolean isPortrait(Context context)
    {
        return context.getResources().getConfiguration().orientation == 1;
    }

    public static boolean isS2(Context context)
    {
        return getDeviceName().equals("Samsung SPH-D710") || getDeviceName().equals("Samsung SCH-R760X") || getDeviceName().equals("Samsung SCH-R760") || getDeviceName().equals("Samsung SGH-I727") || getDeviceName().equals("Samsung SGH-T989");
    }

    public static boolean isScreenSize(int i, Context context)
    {
        return i == (0xf & context.getResources().getConfiguration().screenLayout);
    }

    public static boolean isTablet(Context context)
    {
        int i = 0xf & context.getResources().getConfiguration().screenLayout;
        return 4 == i || 3 == i;
    }
}
