package com.britesky.payanywhere.api;

import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.misc.TransactionManager;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;
//import com.littlefluffytoys.littlefluffylocationlibrary.d;
//import com.littlefluffytoys.littlefluffylocationlibrary.e;
//import com.nabancard.c.f;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

// Referenced classes of package com.nabancard.api:
//            MerchantAccount, CoreDbHelper, DbMerchantAccount, DbMerchantUserRelation, 
//            Util, DbUserAccount, CoreTestDriveDbHelper, Vault, 
//            NabException, HttpHelper2, FilterApi, Filter, 
//            Rule, PrinterApi, SettingsApi, CustomerApi, 
//            CartApi, SaleApi, CryptoHelper, TransactionApi, 
//            InventoryApi, Validator, ReceiptInfoApi, Printer

public class GeneralApi
{

    private static final long ALARM_FREQUENCY = 60000L;
    private static String EN_DATABASE_NAME = "testdrive.db";
    private static String ES_DATABASE_NAME = "testdrive_es.db";
    public static final String HTTP_REQUEST_ERROR_ACTION = "com.nabancard.ui.HTTP_REQUEST_ERROR";
    private static final int LOCATION_MAXIMUM_AGE = 0x1d4c0;
    public static final String LOGOUT_ACTION = "com.nabancard.ui.LOGOUT";
    private static final String PREFERENCE_KEY_LAST_LOGIN_WAS_TEST_DRIVE = "last_login_was_test_drive";
    private static final String PREFERENCE_KEY_LOGOUT = "logoutPreference";
    private static final String PREFERENCE_KEY_MERCHANT_PK = "merchant_pk";
    private static final String PREFERENCE_KEY_MERCHANT_USER_RELATION_PK = "merchant_user_relation_pk";
    private static final String PREFERENCE_KEY_PASSWORD = "passwordPreference";
    private static final String PREFERENCE_KEY_TEST_DRIVE = "test_drive";
    private static final String PREFERENCE_KEY_USER_NAME = "usernamePreference";
    private static final String PREFERENCE_KEY_USER_PK = "user_pk";
    private static final String PREFERENCE_PIN_SETTINGS_SELECTION = "pin_setting";
    public static final String SYNC_PROGRESS_ACTION = "com.nabancard.ui.SYNC_PROGRESS";
    public static final String SYNC_PROGRESS_COMPLETE = "com.nabancard.ui.SYNC_COMPLETE";
    private static final ArrayList _accounts = new ArrayList();
    private static Context _context;
    private static DbMerchantAccount _currentMerchant;
//    private static DbMerchantUserRelation _currentMerchantUserRelation;
    private static DbUserAccount _currentUserAccount;
    private static CoreDbHelper _databaseHelper;
    private static boolean _hasBeenSynced;
    private static boolean _isDebuggable;
    private static boolean _isGlobalTestDriveLoaded;
    private static boolean _isSyncing;
    private static SharedPreferences _sharedPreferences;
    private static boolean merchantNotImported = false;
    public static int progress = 0;

    public GeneralApi()
    {
    }

//    public static boolean areEqual(MerchantAccount merchantaccount, MerchantAccount merchantaccount1)
//    {
//        return merchantaccount.getNabPk() == merchantaccount1.getNabPk();
//    }

    public static boolean copyAssetToDir(String s, File file)
    {
        try
        {
            InputStream inputstream = _context.getAssets().open(s);
            file.getParentFile().mkdirs();
            if (!file.createNewFile()) { return true; }
            FileOutputStream fileoutputstream = new FileOutputStream(file);
            copyFile(inputstream, fileoutputstream);
            fileoutputstream.flush();
            fileoutputstream.close();
            inputstream.close();
        }
        catch (IOException ioexception)
        {
            ioexception.printStackTrace();
            return false;
        }
        return true;
    }

    private static void copyFile(InputStream inputstream, OutputStream outputstream) throws IOException
    {
        byte abyte0[] = new byte[1024];
        do
        {
            int i = inputstream.read(abyte0);
            if (i != -1)
            {
                outputstream.write(abyte0, 0, i);
            } else
            {
                return;
            }
        } while (true);
    }

//    public static MerchantAccount createOrUpdateMerchantAccount(MerchantAccount merchantaccount)
//    {
//        DbMerchantUserRelation dbmerchantuserrelation1;
//        DbMerchantUserRelation dbmerchantuserrelation3;
//        Dao dao;
//        Dao dao1;
//        DbMerchantAccount dbmerchantaccount;
//        DbMerchantAccount dbmerchantaccount1;
//        DbMerchantUserRelation dbmerchantuserrelation;
//        DbMerchantUserRelation dbmerchantuserrelation2;
//        MerchantAccount merchantaccount1;
//        try
//        {
//            dao = getDbHelper().getMerchantAccountDao();
//            dao1 = _databaseHelper.getMerchantUserRelationDao();
//            dbmerchantaccount = (DbMerchantAccount)dao.queryForFirst(dao.queryBuilder().where().eq("_mid", merchantaccount.getMid()).prepare());
//        }
//        catch (SQLException sqlexception)
//        {
//            sqlexception.printStackTrace();
//            return merchantaccount;
//        }
//        if (dbmerchantaccount != null)
//        {
//            break MISSING_BLOCK_LABEL_115;
//        }
//        dbmerchantaccount1 = new DbMerchantAccount(merchantaccount);
//        dao.create(dbmerchantaccount1);
//        dbmerchantuserrelation = new DbMerchantUserRelation(dbmerchantaccount1, getCurrentUser(), Util.convertUserRole(merchantaccount.getRole()));
//        dao1.create(dbmerchantuserrelation);
//        return new MerchantAccount(dbmerchantaccount1, dbmerchantuserrelation.getUserRole());
//        if (dbmerchantaccount.getNabPk() < 0L)
//        {
//            merchantNotImported = true;
//        }
//        dbmerchantaccount.setDba(merchantaccount.getDba());
//        dbmerchantaccount.setNabPk(merchantaccount.getNabPk());
//        dao.update(dbmerchantaccount);
//        dbmerchantuserrelation1 = (DbMerchantUserRelation)dao1.queryForFirst(dao1.queryBuilder().where().eq("_merchant_reference_id", Long.valueOf(dbmerchantaccount.getPk())).and().eq("_user_reference_id", Long.valueOf(getCurrentUser().getPk())).prepare());
//        if (dbmerchantuserrelation1 != null) goto _L2; else goto _L1
//_L1:
//        dbmerchantuserrelation2 = new DbMerchantUserRelation(dbmerchantaccount, getCurrentUser(), Util.convertUserRole(merchantaccount.getRole()));
//        dao1.create(dbmerchantuserrelation2);
//        dbmerchantuserrelation3 = dbmerchantuserrelation2;
//_L4:
//        merchantaccount1 = new MerchantAccount(dbmerchantaccount, dbmerchantuserrelation3.getUserRole());
//        return merchantaccount1;
//_L2:
//        dbmerchantuserrelation3 = dbmerchantuserrelation1;
//        if (true) goto _L4; else goto _L3
//_L3:
//    }
//
//    static eBrand getAppBrand()
//    {
//        throwExceptionIfContextNull();
//        if (_context.getPackageName().contains("paya"))
//        {
//            return eBrand.PAYANYWHERE;
//        } else
//        {
//            return eBrand.PHONESWIPE;
//        }
//    }

    public static File getCacheDir()
    {
        File file = _context.getExternalCacheDir();
        if (file == null)
        {
            file = _context.getCacheDir();
        }
        return file;
    }

//    static Context getContext()
//    {
//        throwExceptionIfContextNull();
//        return _context;
//    }
//
    static DbMerchantAccount getCurrentMerchant()
    {
        throwExceptionIfContextNull();
        if (_currentMerchant == null)
        {
            try
            {
                _currentMerchant = (DbMerchantAccount)getDbHelper().getMerchantAccountDao()
                                    .queryForId(Long.valueOf(getSharedPreferences().getLong("merchant_pk", 1L)));
            }
            catch (SQLException sqlexception)
            {
                sqlexception.printStackTrace();
            }
        }
        return _currentMerchant;
    }

//    static DbMerchantUserRelation getCurrentMerchantUserRelation()
//    {
//        throwExceptionIfContextNull();
//        if (_currentMerchantUserRelation == null)
//        {
//            try
//            {
//                _currentMerchantUserRelation = (DbMerchantUserRelation)getDbHelper().getMerchantUserRelationDao().queryForFirst(getDbHelper().getMerchantUserRelationDao().queryBuilder().where().eq("_user_reference_id", Long.valueOf(_currentUserAccount.getPk())).and().eq("_merchant_reference_id", Long.valueOf(_currentMerchant.getPk())).prepare());
//            }
//            catch (SQLException sqlexception)
//            {
//                sqlexception.printStackTrace();
//            }
//        }
//        return _currentMerchantUserRelation;
//    }
//
    static DbUserAccount getCurrentUser()
    {
//        throwExceptionIfContextNull();
        if (_currentUserAccount == null)
        {
            try
            {
                _currentUserAccount = (DbUserAccount)getDbHelper().getUserAccountDao().queryForId(Long.valueOf(getSharedPreferences().getLong("user_pk", 1L)));
            }
            catch (SQLException sqlexception)
            {
                sqlexception.printStackTrace();
            }
        }
        return _currentUserAccount;
    }

//    public static String getCurrentUserName()
//    {
//        String s;
//        throwExceptionIfContextNull();
//        s = "";
//        if (_currentUserAccount != null) goto _L2; else goto _L1
//_L1:
//        DbUserAccount dbuseraccount;
//        String s1;
//        String s2;
//        try
//        {
//            dbuseraccount = (DbUserAccount)getDbHelper().getUserAccountDao().queryForId(Long.valueOf(getSharedPreferences().getLong("user_pk", -1L)));
//        }
//        catch (SQLException sqlexception)
//        {
//            sqlexception.printStackTrace();
//            return s;
//        }
//        if (dbuseraccount == null) goto _L4; else goto _L3
//_L3:
//        s1 = dbuseraccount.getUserName();
//        s2 = s1;
//_L6:
//        s = s2;
//_L2:
//        return s;
//_L4:
//        s2 = s;
//        if (true) goto _L6; else goto _L5
//_L5:
//    }

    static CoreDbHelper getDbHelper()
    {
//        throwExceptionIfContextNull();
//        if (isTestDrive())
//        {
            _databaseHelper = CoreTestDriveDbHelper.getHelper(_context);
//        } else
//        {
//            _databaseHelper = CoreDbHelper.getHelper(_context);
//        }
        return _databaseHelper;
    }

//    public static DbMerchantAccount getDbMerchantAccount(long l)
//    {
//        DbMerchantAccount dbmerchantaccount;
//        try
//        {
//            Dao dao = getDbHelper().getMerchantAccountDao();
//            dbmerchantaccount = (DbMerchantAccount)dao.queryForFirst(dao.queryBuilder().where().eq("_nab_pk", Long.valueOf(l)).prepare());
//        }
//        catch (SQLException sqlexception)
//        {
//            sqlexception.printStackTrace();
//            return null;
//        }
//        return dbmerchantaccount;
//    }
//
//    public static DbMerchantAccount getDbMerchantAccount(String s)
//    {
//        DbMerchantAccount dbmerchantaccount;
//        try
//        {
//            Dao dao = getDbHelper().getMerchantAccountDao();
//            dbmerchantaccount = (DbMerchantAccount)dao.queryForFirst(dao.queryBuilder().where().eq("_mid", s).prepare());
//        }
//        catch (SQLException sqlexception)
//        {
//            sqlexception.printStackTrace();
//            return null;
//        }
//        return dbmerchantaccount;
//    }
//
//    static byte[] getEncryptionKey()
//    {
//        throwExceptionIfContextNull();
//        return Vault.getEncryptionKey().getBytes();
//    }
//
//    public static boolean getIsFirstTimeUserLogin()
//    {
//        boolean flag = true;
//        if (getSharedPreferences().getLong("user_pk", -1L) > 0L)
//        {
//            flag = false;
//        }
//        return flag;
//    }
//
//    public static d getLocation()
//    {
//        throwExceptionIfContextNull();
//        d d1 = new d(_context);
//        if (d1.c > 90F || d1.c < -90F || d1.d > 180F || d1.d < -180F)
//        {
//            d1 = null;
//        }
//        return d1;
//    }
//
//    public static MerchantAccount getLoggedInMerchant()
//    {
//        throwExceptionIfContextNull();
//        return new MerchantAccount(getCurrentMerchant(), getCurrentMerchantUserRelation().getUserRole());
//    }
//
//    public static String getLoggedInUserName()
//    {
//        throwExceptionIfContextNull();
//        return getCurrentUser().getUserName();
//    }
//
//    public static boolean getLogoutPreference()
//    {
//        return getSharedPreferences().getBoolean("logoutPreference", false);
//    }
//
//    public static ArrayList getMerchantAccounts()
//    {
//        throwExceptionIfContextNull();
//        return _accounts;
//    }
//
//    public static boolean getMerchantImportStatus()
//    {
//        return merchantNotImported;
//    }
//
//    public static int getPinSelection()
//    {
//        if (isGlobalTestDrive())
//        {
//            return -1;
//        } else
//        {
//            return getSharedPreferences().getInt((new StringBuilder("pin_setting")).append(getCurrentUser().getPk()).toString(), -1);
//        }
//    }
//
    static SharedPreferences getSharedPreferences()
    {
//        throwExceptionIfContextNull();
        if (_sharedPreferences == null)
        {
            _sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        }
        return _sharedPreferences;
    }

//    public static String getStoredPassword()
//    {
//        return getSharedPreferences().getString("passwordPreference", null);
//    }
//
//    public static String getStoredUserName()
//    {
//        return getSharedPreferences().getString("usernamePreference", null);
//    }
//
//    public static boolean hasBeenSynced()
//    {
//        return _hasBeenSynced;
//    }

    public static void init(Context context)
    {
        Log.d("NAB", "GeneralApi: Initializing");
        _context = context.getApplicationContext();
        boolean flag;
        if ((2 & context.getApplicationInfo().flags) != 0)
        {
            flag = true;
        } else
        {
            flag = false;
        }
        _isDebuggable = flag;
//        e.showDebugOutput(false);
//        e.initialiseLibrary(_context, 60000L, 0x1d4c0, _context.getApplicationInfo().packageName);
//        throwExceptionIfContextNull();
    }

//    private static void initializeUserAccount(LoginBundle loginbundle, String s, String s1)
//    {
//        throwExceptionIfContextNull();
//        _currentUserAccount = null;
//        Dao dao;
//        DbUserAccount dbuseraccount;
//        DbUserAccount dbuseraccount1;
//        try
//        {
//            dao = getDbHelper().getUserAccountDao();
//            dbuseraccount = (DbUserAccount)dao.queryForFirst(dao.queryBuilder().where().eq("_nab_pk", Long.valueOf(loginbundle._userPk)).prepare());
//            _currentUserAccount = dbuseraccount;
//        }
//        catch (SQLException sqlexception)
//        {
//            sqlexception.printStackTrace();
//            return;
//        }
//        if (dbuseraccount != null)
//        {
//            break MISSING_BLOCK_LABEL_99;
//        }
//        dbuseraccount1 = new DbUserAccount(loginbundle._userPk, s);
//        _currentUserAccount = dbuseraccount1;
//        dbuseraccount1.setLoginKey(s1);
//        dao.create(_currentUserAccount);
//        getSharedPreferences().edit().putLong("user_pk", _currentUserAccount.getPk()).commit();
//        if (_currentUserAccount.getDefaultMerchantAccount() != null)
//        {
//            _currentMerchant = _currentUserAccount.getDefaultMerchantAccount();
//            _currentMerchantUserRelation = getCurrentMerchantUserRelation();
//            getSharedPreferences().edit().putLong("merchant_pk", _currentUserAccount.getDefaultMerchantAccount().getPk()).putLong("merchant_user_relation_pk", _currentMerchantUserRelation.getPk()).commit();
//        }
//        return;
//    }
//
//    public static boolean isAccountEmpty()
//    {
//        return _accounts.isEmpty();
//    }
//
//    static boolean isDebuggable()
//    {
//        return _isDebuggable;
//    }
//
//    public static boolean isGlobalTestDrive()
//    {
//        if (getCurrentUser() == null)
//        {
//            throw new NabException(NabException.eExceptionReason.NO_CURRENT_USER_EXCEPTION);
//        }
//        return -1L == getCurrentUser().getNabPk();
//    }

    public static boolean isGlobalTestDriveLoaded()
    {
        return false;
//        return _isGlobalTestDriveLoaded;
    }

//    public static boolean isLastLoginTestDrive()
//    {
//        return getSharedPreferences().getBoolean("last_login_was_test_drive", true);
//    }
//
//    public static boolean isPandoraRunning()
//    {
//        List list = ((ActivityManager)_context.getSystemService("activity")).getRunningAppProcesses();
//        int i = 0;
//        do
//        {
//label0:
//            {
//                int j = list.size();
//                boolean flag = false;
//                if (i < j)
//                {
//                    if (!((android.app.ActivityManager.RunningAppProcessInfo)list.get(i)).processName.equals("com.pandora.android"))
//                    {
//                        break label0;
//                    }
//                    flag = true;
//                }
//                return flag;
//            }
//            i++;
//        } while (true);
//    }
//
//    public static boolean isSyncing()
//    {
//        return _isSyncing;
//    }
//
    public static boolean isTestDrive()
    {
        return true;
//        return getSharedPreferences().getBoolean("test_drive", true);
    }

//    public static LoginBundle logInLastUserAccount()
//    {
//        HttpHelper2 httphelper2;
//        eLoginResult eloginresult;
//        LoginBundle loginbundle;
//        DbUserAccount dbuseraccount;
//        throwExceptionIfContextNull();
//        httphelper2 = new HttpHelper2();
//        getSharedPreferences().edit().putBoolean("test_drive", false).commit();
//        eloginresult = eLoginResult.LOGIN_UNSUCCESSFUL;
//        loginbundle = new LoginBundle();
//        dbuseraccount = getCurrentUser();
//        if (dbuseraccount == null) goto _L2; else goto _L1
//_L1:
//        JSONObject jsonobject = new JSONObject(httphelper2.send((new StringBuilder()).append(Vault.getStoreFrontUri()).append(Vault.getLoginUrl()).toString(), HttpHelper2.Verb.Get, "", dbuseraccount.getUserName(), dbuseraccount.getLoginKey(), isTestDrive()));
//        if (!jsonobject.has("user_id") || jsonobject.getLong("user_id") <= 0L) goto _L4; else goto _L3
//_L3:
//        String s;
//        loginbundle._userPk = jsonobject.getLong("user_id");
//        initializeUserAccount(loginbundle, dbuseraccount.getUserName(), null);
//        s = httphelper2.send((new StringBuilder()).append(Vault.getStoreFrontUri()).append(Vault.getMerchantAccountsUrl().replace("{id}", (new StringBuilder()).append(getCurrentUser().getNabPk()).toString())).toString(), HttpHelper2.Verb.Get, "", getCurrentUser().getUserName(), getCurrentUser().getLoginKey(), isTestDrive());
//        _accounts.clear();
//        if (s.contains("error_message")) goto _L6; else goto _L5
//_L5:
//        JSONArray jsonarray = new JSONArray(s);
//        int i = 0;
//_L8:
//        if (i >= jsonarray.length())
//        {
//            break; /* Loop/switch isn't completed */
//        }
//        MerchantAccount merchantaccount = createOrUpdateMerchantAccount(new MerchantAccount(jsonarray.getJSONObject(i).getLong("mea_id"), jsonarray.getJSONObject(i).getString("mid"), jsonarray.getJSONObject(i).getString("dba_name"), Util.convertUserRole(jsonarray.getJSONObject(i).getString("relationship"))));
//        _accounts.add(merchantaccount);
//        i++;
//        if (true) goto _L8; else goto _L7
//_L7:
//        eLoginResult eloginresult1;
//        try
//        {
//            loginbundle._merchantAccounts = _accounts;
//            eloginresult1 = eLoginResult.LOGIN_SUCCESSFUL;
//        }
//        catch (JSONException jsonexception)
//        {
//            jsonexception.printStackTrace();
//            eloginresult1 = eLoginResult.LOGIN_UNSUCCESSFUL;
//        }
//        catch (Exception exception)
//        {
//            exception.printStackTrace();
//            eloginresult1 = eLoginResult.LOGIN_UNSUCCESSFUL;
//        }
//_L10:
//        loginbundle._loginResult = eloginresult1;
//        return loginbundle;
//_L6:
//        eloginresult1 = eLoginResult.NO_MERCHANT_ACCOUNTS;
//        continue; /* Loop/switch isn't completed */
//_L4:
//        if (jsonobject.has("error_type") && "RequestExpired".equalsIgnoreCase(jsonobject.getString("error_type")))
//        {
//            eloginresult1 = eLoginResult.REQUEST_EXPIRED;
//            continue; /* Loop/switch isn't completed */
//        }
//        eloginresult1 = eLoginResult.LOGIN_UNSUCCESSFUL;
//        continue; /* Loop/switch isn't completed */
//_L2:
//        eloginresult1 = eloginresult;
//        if (true) goto _L10; else goto _L9
//_L9:
//    }
//
//    public static void logInMerchantAccount(MerchantAccount merchantaccount)
//    {
//        throwExceptionIfContextNull();
//        _currentMerchant = null;
//        _currentMerchantUserRelation = null;
//        if (merchantaccount != null) goto _L2; else goto _L1
//_L1:
//        return;
//_L2:
//        DbMerchantAccount dbmerchantaccount;
//        if (_currentMerchant != null || _currentMerchantUserRelation != null)
//        {
//            break MISSING_BLOCK_LABEL_519;
//        }
//        dbmerchantaccount = getDbMerchantAccount(merchantaccount.getNabPk());
//        _currentMerchant = dbmerchantaccount;
//        if (dbmerchantaccount != null) goto _L4; else goto _L3
//_L3:
//        MerchantAccount merchantaccount3 = createOrUpdateMerchantAccount(merchantaccount);
//        MerchantAccount merchantaccount2 = merchantaccount3;
//        _currentMerchant = getDbMerchantAccount(merchantaccount2.getNabPk());
//_L9:
//        Dao dao;
//        DbMerchantUserRelation dbmerchantuserrelation;
//        dao = getDbHelper().getMerchantUserRelationDao();
//        dbmerchantuserrelation = (DbMerchantUserRelation)dao.queryForFirst(dao.queryBuilder().where().eq("_merchant_reference_id", Long.valueOf(_currentMerchant.getPk())).and().eq("_user_reference_id", Long.valueOf(getCurrentUser().getPk())).prepare());
//        _currentMerchantUserRelation = dbmerchantuserrelation;
//        if (dbmerchantuserrelation != null)
//        {
//            break MISSING_BLOCK_LABEL_179;
//        }
//        _currentMerchantUserRelation = new DbMerchantUserRelation(_currentMerchant, getCurrentUser(), Util.convertUserRole(merchantaccount2.getRole()));
//        dao.create(_currentMerchantUserRelation);
//_L10:
//        DbFilter dbfilter = FilterApi.getDbFilter(_context.getResources().getString(f.filter_trans_cash), Filter.eFilterType.TRANSACTION);
//        if (dbfilter != null)
//        {
//            break MISSING_BLOCK_LABEL_267;
//        }
//        Filter filter1 = FilterApi.newFilter(Filter.eFilterType.TRANSACTION);
//        filter1.setMatchAll(true);
//        filter1.setName(_context.getResources().getString(f.filter_trans_cash));
//        filter1.getRules().add(new Rule(Rule.eAttribute.DATE, Rule.eOperator.ON_OR_AFTER, 0L, Rule.eTransactionType.CASH));
//        FilterApi.commit(filter1);
//_L5:
//        DbFilter dbfilter1 = FilterApi.getDbFilter(_context.getResources().getString(f.filter_trans_credit), Filter.eFilterType.TRANSACTION);
//        if (dbfilter1 != null)
//        {
//            break MISSING_BLOCK_LABEL_355;
//        }
//        Filter filter = FilterApi.newFilter(Filter.eFilterType.TRANSACTION);
//        filter.setMatchAll(true);
//        filter.setName(_context.getResources().getString(f.filter_trans_credit));
//        filter.getRules().add(new Rule(Rule.eAttribute.DATE, Rule.eOperator.ON_OR_AFTER, 0L, Rule.eTransactionType.CREDIT_CARD));
//        FilterApi.commit(filter);
//_L7:
//        FilterApi.saveTempFilter(null);
//        getSharedPreferences().edit().putLong("merchant_pk", _currentMerchant.getPk()).putLong("user_pk", getCurrentUser().getPk()).putLong("merchant_user_relation_pk", _currentMerchantUserRelation.getPk()).commit();
//        MerchantAccount merchantaccount1 = merchantaccount2;
//_L6:
//        if (merchantaccount1.getNabPk() != 1L)
//        {
//            PrinterApi.loadPrinters(_context);
//            SettingsApi.setTaxPercent(new BigDecimal(6));
//            CustomerApi.init();
//            FilterApi.init();
//            CartApi.init();
//            SaleApi.init();
//        }
//        if (SettingsApi.getLoginSetting() == SettingsApi.eSettingKey.LOGIN_USE_LAST)
//        {
//            SettingsApi.setDefaultMerchantAccount(merchantaccount1);
//            return;
//        }
//          goto _L1
//        NabException nabexception1;
//        nabexception1;
//        nabexception1.printStackTrace();
//          goto _L5
//        SQLException sqlexception2;
//        sqlexception2;
//        SQLException sqlexception1;
//        sqlexception1 = sqlexception2;
//        merchantaccount1 = merchantaccount2;
//_L8:
//        sqlexception1.printStackTrace();
//          goto _L6
//        NabException nabexception;
//        nabexception;
//        nabexception.printStackTrace();
//          goto _L7
//        SQLException sqlexception;
//        sqlexception;
//        sqlexception1 = sqlexception;
//        merchantaccount1 = merchantaccount;
//          goto _L8
//_L4:
//        merchantaccount2 = merchantaccount;
//          goto _L9
//        merchantaccount2 = merchantaccount;
//          goto _L10
//    }
//
//    public static LoginBundle logInUserAccount(String s, String s1)
//    {
//        HttpHelper2 httphelper2;
//        LoginBundle loginbundle;
//        throwExceptionIfContextNull();
//        httphelper2 = new HttpHelper2();
//        getSharedPreferences().edit().putBoolean("test_drive", false).commit();
//        eLoginResult.LOGIN_UNSUCCESSFUL;
//        loginbundle = new LoginBundle();
//        String s2;
//        s2 = httphelper2.send((new StringBuilder()).append(Vault.getStoreFrontUri()).append(Vault.getSaltUrl().replace("{username}", s)).toString(), HttpHelper2.Verb.Get, "", s, null, isTestDrive());
//        if (!s2.isEmpty())
//        {
//            break MISSING_BLOCK_LABEL_112;
//        }
//        loginbundle._loginResult = eLoginResult.NO_INTERNET_OR_CREDENTIALS;
//        return loginbundle;
//        JSONObject jsonobject;
//        jsonobject = new JSONObject(s2);
//        if (!jsonobject.has("error_type") || !jsonobject.getString("error_type").equals("createNotFoundException"))
//        {
//            break MISSING_BLOCK_LABEL_163;
//        }
//        loginbundle._loginResult = eLoginResult.LOGIN_UNSUCCESSFUL;
//        return loginbundle;
//        String s4;
//        byte abyte0[];
//        String s3 = jsonobject.getString("salt");
//        s4 = (new StringBuilder()).append(s1).append("{").append(s3).append("}").toString();
//        abyte0 = CryptoHelper.sha512(s4.getBytes());
//        int i = 1;
//_L2:
//        if (i >= 5000)
//        {
//            break; /* Loop/switch isn't completed */
//        }
//        String s5 = new String(abyte0, "ISO-8859-1");
//        abyte0 = CryptoHelper.sha512((new StringBuilder()).append(s5).append(s4).toString().getBytes("ISO-8859-1"));
//        i++;
//        if (true) goto _L2; else goto _L1
//_L1:
//        JSONObject jsonobject1;
//        JSONArray jsonarray;
//        String s6 = CryptoHelper.bytesToHex(CryptoHelper.md5(Base64.encodeToString(abyte0, 2).getBytes("ISO-8859-1")));
//        jsonobject1 = new JSONObject(httphelper2.send((new StringBuilder()).append(Vault.getStoreFrontUri()).append(Vault.getLoginUrl()).toString(), HttpHelper2.Verb.Get, "", s, s6, isTestDrive()));
//        if (!jsonobject1.has("user_id") || jsonobject1.getLong("user_id") <= 0L)
//        {
//            break MISSING_BLOCK_LABEL_643;
//        }
//        loginbundle._userPk = jsonobject1.getLong("user_id");
//        initializeUserAccount(loginbundle, s, s6);
//        String s7 = httphelper2.send((new StringBuilder()).append(Vault.getStoreFrontUri()).append(Vault.getMerchantAccountsUrl().replace("{id}", (new StringBuilder()).append(getCurrentUser().getNabPk()).toString())).toString(), HttpHelper2.Verb.Get, "", getCurrentUser().getUserName(), getCurrentUser().getLoginKey(), isTestDrive());
//        _accounts.clear();
//        if (s7.contains("error_message"))
//        {
//            break MISSING_BLOCK_LABEL_635;
//        }
//        jsonarray = new JSONArray(s7);
//        int j = 0;
//_L4:
//        if (j >= jsonarray.length())
//        {
//            break; /* Loop/switch isn't completed */
//        }
//        MerchantAccount merchantaccount = createOrUpdateMerchantAccount(new MerchantAccount(jsonarray.getJSONObject(j).getLong("mea_id"), jsonarray.getJSONObject(j).getString("mid"), jsonarray.getJSONObject(j).getString("dba_name"), Util.convertUserRole(jsonarray.getJSONObject(j).getString("relationship"))));
//        _accounts.add(merchantaccount);
//        j++;
//        if (true) goto _L4; else goto _L3
//_L3:
//        eLoginResult eloginresult;
//        try
//        {
//            getSharedPreferences().edit().putBoolean("last_login_was_test_drive", false).commit();
//            loginbundle._merchantAccounts = _accounts;
//            eloginresult = eLoginResult.LOGIN_SUCCESSFUL;
//        }
//        catch (JSONException jsonexception)
//        {
//            jsonexception.printStackTrace();
//            eloginresult = eLoginResult.LOGIN_UNSUCCESSFUL;
//        }
//        catch (UnsupportedEncodingException unsupportedencodingexception)
//        {
//            unsupportedencodingexception.printStackTrace();
//            eloginresult = eLoginResult.LOGIN_UNSUCCESSFUL;
//        }
//        loginbundle._loginResult = eloginresult;
//        return loginbundle;
//        eloginresult = eLoginResult.NO_MERCHANT_ACCOUNTS;
//        break MISSING_BLOCK_LABEL_624;
//label0:
//        {
//            if (!jsonobject1.has("error_type") || !"RequestExpired".equalsIgnoreCase(jsonobject1.getString("error_type")))
//            {
//                break label0;
//            }
//            eloginresult = eLoginResult.REQUEST_EXPIRED;
//        }
//        break MISSING_BLOCK_LABEL_624;
//        eloginresult = eLoginResult.LOGIN_UNSUCCESSFUL;
//        break MISSING_BLOCK_LABEL_624;
//    }
//
//    public static void logOut()
//    {
//        TransactionApi.reset();
//        CustomerApi.reset();
//        InventoryApi.reset();
//        PrinterApi.reset();
//        setTestDrive(false);
//        _currentMerchant = null;
//        _currentUserAccount = null;
//        _currentMerchantUserRelation = null;
//        _accounts.clear();
//        _databaseHelper = null;
//        _hasBeenSynced = false;
//        getSharedPreferences().edit().putLong("merchant_pk", -1L).putLong("user_pk", -1L).putLong("merchant_user_relation_pk", -1L).commit();
//        storeUserCredentials(null, null);
//        setLogoutPreference(true);
//    }
//
//    public static boolean performSync()
//    {
//        throwExceptionIfContextNull();
//        _isSyncing = true;
//        progress = 0;
//        CoreDbHelper coredbhelper = getDbHelper();
//        if (coredbhelper.getConnectionSource().isOpen())
//        {
//            TransactionManager.callInTransaction(coredbhelper.getConnectionSource(), new _cls1());
//        }
//        _isSyncing = false;
//        return true;
//        SQLException sqlexception;
//        sqlexception;
//        sqlexception.printStackTrace();
//        _isSyncing = false;
//        return false;
//        Exception exception;
//        exception;
//        _isSyncing = false;
//        throw exception;
//    }
//
//    private static boolean performSync(Calendar calendar, Calendar calendar1, HttpHelper2 httphelper2, boolean flag)
//    {
//        throwExceptionIfContextNull();
//        SimpleDateFormat simpledateformat;
//        String s;
//        String s1;
//        JSONObject jsonobject;
//        String s2;
//        try
//        {
//            simpledateformat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
//            s = Vault.getSyncDeltaUrl().replace("{id}", Long.toString(getCurrentMerchant().getNabPk())).replace("{from}", simpledateformat.format(calendar.getTime())).replace("{include-non-transactions}", Boolean.toString(flag));
//        }
//        catch (JSONException jsonexception)
//        {
//            jsonexception.printStackTrace();
//            return false;
//        }
//        if (calendar1 != null) goto _L2; else goto _L1
//_L1:
//        s1 = s.replace("/{to}", "");
//        calendar1 = Calendar.getInstance(TimeZone.getTimeZone("EST"));
//_L3:
//        jsonobject = new JSONObject(httphelper2.send((new StringBuilder()).append(Vault.getStoreFrontUri()).append(s1).toString(), HttpHelper2.Verb.Get, null, getCurrentUser().getUserName(), getCurrentUser().getLoginKey(), isTestDrive()));
//        syncSettings(jsonobject);
//        syncMerchantSettings(jsonobject);
//        if (!flag)
//        {
//            break MISSING_BLOCK_LABEL_177;
//        }
//        CustomerApi.bulkSyncCustomers(jsonobject);
//        InventoryApi.bulkSyncTags(jsonobject);
//        InventoryApi.bulkSyncCategories(jsonobject);
//        InventoryApi.bulkSyncSalesItems(jsonobject, _context);
//        if (Validator.isSamsungTab10())
//        {
//            TransactionApi.reInit();
//        }
//        TransactionApi.bulkSyncReceipts(jsonobject);
//        TransactionApi.bulkSyncCashSales(jsonobject);
//        TransactionApi.bulkSyncCashRefunds(jsonobject);
//        TransactionApi.bulkSyncPreAuths(jsonobject);
//        TransactionApi.bulkSyncCreditCardSales(jsonobject);
//        TransactionApi.bulkSyncCreditCardRefunds(jsonobject);
//        TransactionApi.bulkSyncVoids(jsonobject);
//        getCurrentMerchant().setLastSync(calendar1.getTime());
//        return true;
//_L2:
//        s2 = s.replace("{to}", simpledateformat.format(calendar1.getTime()));
//        s1 = s2;
//          goto _L3
//    }
//
//    public static boolean performSyncSettings()
//    {
//        throwExceptionIfContextNull();
//        _isSyncing = true;
//        JSONObject jsonobject = new JSONObject((new HttpHelper2()).send((new StringBuilder()).append(Vault.getStoreFrontUri()).append(Vault.getSettingsUrl().replace("{id}", Long.toString(getCurrentMerchant().getNabPk()))).toString(), HttpHelper2.Verb.Get, null, getCurrentUser().getUserName(), getCurrentUser().getLoginKey(), isTestDrive()));
//        syncSettings(jsonobject);
//        syncMerchantSettings(jsonobject);
//        _isSyncing = false;
//        return true;
//        JSONException jsonexception;
//        jsonexception;
//        jsonexception.printStackTrace();
//        _isSyncing = false;
//        return false;
//        Exception exception;
//        exception;
//        _isSyncing = false;
//        throw exception;
//    }
//
//    public static eLoginResult reInitAccountList()
//    {
//        String s = (new HttpHelper2()).send((new StringBuilder()).append(Vault.getStoreFrontUri()).append(Vault.getMerchantAccountsUrl().replace("{id}", (new StringBuilder()).append(getCurrentUser().getNabPk()).toString())).toString(), HttpHelper2.Verb.Get, "", getCurrentUser().getUserName(), getCurrentUser().getLoginKey(), isTestDrive());
//        _accounts.clear();
//        if (s.contains("error_message"))
//        {
//            break MISSING_BLOCK_LABEL_210;
//        }
//        JSONArray jsonarray;
//        int i;
//        long l;
//        String s1;
//        String s2;
//        String s3;
//        try
//        {
//            jsonarray = new JSONArray(s);
//        }
//        catch (JSONException jsonexception)
//        {
//            jsonexception.printStackTrace();
//            return eLoginResult.LOGIN_UNSUCCESSFUL;
//        }
//        i = 0;
//        if (i >= jsonarray.length())
//        {
//            break; /* Loop/switch isn't completed */
//        }
//        l = jsonarray.getJSONObject(i).getLong("mea_id");
//        s1 = jsonarray.getJSONObject(i).getString("mid");
//        s2 = jsonarray.getJSONObject(i).getString("dba_name");
//        s3 = jsonarray.getJSONObject(i).getString("relationship");
//        _accounts.add(new MerchantAccount(l, s1, s2, Util.convertUserRole(s3)));
//        i++;
//        if (true) goto _L2; else goto _L1
//_L2:
//        break MISSING_BLOCK_LABEL_106;
//_L1:
//        return eLoginResult.LOGIN_SUCCESSFUL;
//        return eLoginResult.NO_MERCHANT_ACCOUNTS;
//    }

    public static void resetTestDrive()
    {
        String s = EN_DATABASE_NAME;

        String s1 = _context.getPackageName();
        String s2 = null;
        try {
            s2 = _context.getPackageManager().getPackageInfo(s1, 0).applicationInfo.dataDir;
        } catch (NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        File file = new File(new File((new StringBuilder()).append(s2).append(File.separator).append("databases").toString()), s);
        copyAssetToDir((new StringBuilder("testdrive")).append(File.separator).append(s).toString(), file);
        
//        SettingsApi.setTaxPercent(new BigDecimal(6));
        InventoryApi.reInit();
//        FilterApi.init();
//        CustomerApi.init();
        CartApi.init();
//        SaleApi.init();
//        TransactionApi.reInit();

        _isGlobalTestDriveLoaded = true;
        
//        if (!_context.getResources().getConfiguration().locale.getLanguage().equals("es")) goto _L2; else goto _L1
//_L1:
//        String s = ES_DATABASE_NAME;
//_L3:
//        CoreDbHelper coredbhelper;
//        d d1;
//        String s1 = _context.getPackageName();
//        String s2 = _context.getPackageManager().getPackageInfo(s1, 0).applicationInfo.dataDir;
//        File file = new File(new File((new StringBuilder()).append(s2).append(File.separator).append("databases").toString()), s);
//        copyAssetToDir((new StringBuilder("testdrive")).append(File.separator).append(s).toString(), file);
//        coredbhelper = getDbHelper();
//        d1 = getLocation();
//        if (d1 == null)
//        {
//            break MISSING_BLOCK_LABEL_491;
//        }
//        float f1;
//        float f2;
//        f1 = d1.c;
//        f2 = d1.d;
//_L6:
//        UpdateBuilder updatebuilder = coredbhelper.getCashSaleTransactionDao().updateBuilder();
//        updatebuilder.updateColumnValue("_location_latitude", Float.valueOf(f1));
//        updatebuilder.updateColumnValue("_location_longitude", Float.valueOf(f2));
//        updatebuilder.update();
//        UpdateBuilder updatebuilder1 = coredbhelper.getCashRefundTransactionDao().updateBuilder();
//        updatebuilder1.updateColumnValue("_location_latitude", Float.valueOf(f1));
//        updatebuilder1.updateColumnValue("_location_longitude", Float.valueOf(f2));
//        updatebuilder1.update();
//        UpdateBuilder updatebuilder2 = coredbhelper.getCreditCardSaleTransactionDao().updateBuilder();
//        updatebuilder2.updateColumnValue("_location_latitude", Float.valueOf(f1));
//        updatebuilder2.updateColumnValue("_location_longitude", Float.valueOf(f2));
//        updatebuilder2.update();
//        UpdateBuilder updatebuilder3 = coredbhelper.getCreditCardRefundTransactionDao().updateBuilder();
//        updatebuilder3.updateColumnValue("_location_latitude", Float.valueOf(f1));
//        updatebuilder3.updateColumnValue("_location_longitude", Float.valueOf(f2));
//        updatebuilder3.update();
//        UpdateBuilder updatebuilder4 = coredbhelper.getPreauthTransactionDao().updateBuilder();
//        updatebuilder4.updateColumnValue("_location_latitude", Float.valueOf(f1));
//        updatebuilder4.updateColumnValue("_location_longitude", Float.valueOf(f2));
//        updatebuilder4.update();
//        UpdateBuilder updatebuilder5 = coredbhelper.getVoidTransactionDao().updateBuilder();
//        updatebuilder5.updateColumnValue("_location_latitude", Float.valueOf(f1));
//        updatebuilder5.updateColumnValue("_location_longitude", Float.valueOf(f2));
//        updatebuilder5.update();
//_L4:
//        SettingsApi.setTaxPercent(new BigDecimal(6));
//        InventoryApi.reInit();
//        FilterApi.init();
//        CustomerApi.init();
//        CartApi.init();
//        SaleApi.init();
//        TransactionApi.reInit();
//_L5:
//        _isGlobalTestDriveLoaded = true;
//        return;
//_L2:
//        s = EN_DATABASE_NAME;
//          goto _L3
//        SQLException sqlexception;
//        sqlexception;
//        sqlexception.printStackTrace();
//          goto _L4
//        android.content.pm.PackageManager.NameNotFoundException namenotfoundexception;
//        namenotfoundexception;
//        namenotfoundexception.printStackTrace();
//          goto _L5
//        f2 = 0.0F;
//        f1 = 0.0F;
//          goto _L6
    }
//
//    public static void setLogoutPreference(boolean flag)
//    {
//        getSharedPreferences().edit().putBoolean("logoutPreference", flag).commit();
//    }
//
//    public static void setPinSelection(int i)
//    {
//        getSharedPreferences().edit().putInt((new StringBuilder("pin_setting")).append(getCurrentUser().getPk()).toString(), i).commit();
//    }
//
//    public static void setSyncPerformed(boolean flag)
//    {
//        _hasBeenSynced = flag;
//    }
//
//    public static void setTestDrive(boolean flag)
//    {
//        getSharedPreferences().edit().putBoolean("test_drive", flag).commit();
//        CoreDbHelper.resetHelper();
//        if (!flag)
//        {
//            _isGlobalTestDriveLoaded = false;
//            return;
//        } else
//        {
//            getSharedPreferences().edit().putBoolean("last_login_was_test_drive", true).commit();
//            return;
//        }
//    }
//
    public static void setUpGlobalTestDrive()
    {
        throwExceptionIfContextNull();
//        LoginBundle loginbundle = new LoginBundle();
//        loginbundle._userPk = -1L;
//        initializeUserAccount(loginbundle, "mobile_test_drive_user", "a500c25345ce88bf3b376d773b87a6af");
//        logInMerchantAccount(new MerchantAccount(1L, "", "", MerchantAccount.eUserRole.ROLE_OWNER));
        resetTestDrive();
    }
//
//    public static void storeUserCredentials(String s, String s1)
//    {
//        getSharedPreferences().edit().putString("usernamePreference", CryptoHelper.aesEncrypt(s, null)).commit();
//        getSharedPreferences().edit().putString("passwordPreference", CryptoHelper.aesEncrypt(s1, null)).commit();
//    }
//
//    protected static void syncMerchantSettings(JSONObject jsonobject)
//    {
//        if (!jsonobject.has("merchant_settings")) goto _L2; else goto _L1
//_L1:
//        JSONObject jsonobject1 = jsonobject.getJSONObject("merchant_settings");
//_L7:
//        if (jsonobject1 == null)
//        {
//            break MISSING_BLOCK_LABEL_509;
//        }
//        JSONArray jsonarray;
//        ArrayList arraylist;
//        boolean flag;
//        String s;
//        File file;
//        int i;
//        try
//        {
//            if (jsonobject1.has("street_address"))
//            {
//                JSONObject jsonobject2 = jsonobject1.getJSONObject("street_address");
//                if (jsonobject2.has("address1"))
//                {
//                    ReceiptInfoApi.setReceiptAddress1(jsonobject2.getString("address1"));
//                }
//                if (jsonobject2.has("address2"))
//                {
//                    ReceiptInfoApi.setReceiptAddress2(jsonobject2.getString("address2"));
//                }
//                if (jsonobject2.has("city"))
//                {
//                    ReceiptInfoApi.setReceiptCity(jsonobject2.getString("city"));
//                }
//                if (jsonobject2.has("state"))
//                {
//                    ReceiptInfoApi.setReceiptState(jsonobject2.getString("state"));
//                }
//                if (jsonobject2.has("zip_code"))
//                {
//                    ReceiptInfoApi.setReceiptZip(jsonobject2.getString("zip_code"));
//                }
//            }
//            if (jsonobject1.has("receipt_message"))
//            {
//                ReceiptInfoApi.setReceiptMessage(jsonobject1.getString("receipt_message"));
//            }
//            if (jsonobject1.has("email_address"))
//            {
//                ReceiptInfoApi.setReceiptEmail(jsonobject1.getString("email_address"));
//            }
//            if (jsonobject1.has("phone_number"))
//            {
//                ReceiptInfoApi.setReceiptPhone(jsonobject1.getString("phone_number"));
//            }
//            if (jsonobject1.has("fax_number"))
//            {
//                ReceiptInfoApi.setReceiptFax(jsonobject1.getString("fax_number"));
//            }
//            if (jsonobject1.has("is_map_enabled"))
//            {
//                ReceiptInfoApi.setShowMap(jsonobject1.getBoolean("is_map_enabled"));
//            }
//            if (jsonobject1.has("merchant_name"))
//            {
//                ReceiptInfoApi.setReceiptMerchantName(jsonobject1.getString("merchant_name"));
//            }
//            if (jsonobject1.has("receipt_disclaimer"))
//            {
//                ReceiptInfoApi.setDisclaimer(jsonobject1.getString("receipt_disclaimer"));
//            }
//            if (jsonobject1.has("website"))
//            {
//                ReceiptInfoApi.setReceiptWebsite(jsonobject1.getString("website"));
//            }
//            if (jsonobject1.has("should_bcc"))
//            {
//                ReceiptInfoApi.setEmailMe(jsonobject1.getBoolean("should_bcc"));
//            }
//            jsonarray = jsonobject1.getJSONArray("bcc_email_addresses");
//            arraylist = new ArrayList();
//        }
//        catch (JSONException jsonexception)
//        {
//            jsonexception.printStackTrace();
//            return;
//        }
//        if (jsonarray == null) goto _L4; else goto _L3
//_L3:
//        i = 0;
//_L5:
//        if (i >= jsonarray.length())
//        {
//            break; /* Loop/switch isn't completed */
//        }
//        arraylist.add(jsonarray.get(i).toString());
//        i++;
//        if (true) goto _L5; else goto _L4
//_L4:
//        ReceiptInfoApi.setEmailsToBcc(arraylist);
//        flag = jsonobject1.has("logo_image_id");
//        s = null;
//        if (!flag)
//        {
//            break MISSING_BLOCK_LABEL_503;
//        }
//        s = (new StringBuilder("nab://")).append(jsonobject1.getLong("logo_image_id")).toString();
//        file = new File((new StringBuilder()).append(getCacheDir().getPath()).append(File.separator).append(s.hashCode()).toString());
//        if (file.exists())
//        {
//            file.delete();
//        }
//        ReceiptInfoApi.setReceiptMerchantLogo(s);
//        return;
//_L2:
//        jsonobject1 = null;
//        if (true) goto _L7; else goto _L6
//_L6:
//    }
//
//    protected static void syncSettings(JSONObject jsonobject)
//    {
//        if (!jsonobject.has("user_settings")) goto _L2; else goto _L1
//_L1:
//        JSONObject jsonobject1 = jsonobject.getJSONObject("user_settings");
//_L30:
//        if (jsonobject1 == null) goto _L4; else goto _L3
//_L3:
//        if (jsonobject1.has("receipt_message"))
//        {
//            ReceiptInfoApi.setReceiptMessage(jsonobject1.getString("receipt_message"));
//        }
//        if (jsonobject1.has("is_cloud_print_enabled"))
//        {
//            SettingsApi.setPrintCloudEnabled(jsonobject1.getBoolean("is_cloud_print_enabled"));
//        }
//        if (jsonobject1.has("is_auto_generated_invoice_numbers_enabled"))
//        {
//            SettingsApi.setInvoiceEnabled(jsonobject1.getBoolean("is_auto_generated_invoice_numbers_enabled"));
//        }
//        if (jsonobject1.has("is_discount_enabled"))
//        {
//            SettingsApi.setDiscountEnabled(jsonobject1.getBoolean("is_discount_enabled"));
//        }
//        if (jsonobject1.has("is_geotax_enabled"))
//        {
//            SettingsApi.setTaxAutoEnabled(jsonobject1.getBoolean("is_geotax_enabled"));
//        }
//        if (jsonobject1.has("is_signature_required"))
//        {
//            SettingsApi.setSignatureRequiredEnabled(jsonobject1.getBoolean("is_signature_required"));
//        }
//        if (jsonobject1.has("is_star_print_enabled"))
//        {
//            SettingsApi.setPrintStarEnabled(jsonobject1.getBoolean("is_star_print_enabled"));
//        }
//        if (jsonobject1.has("tax_rate"))
//        {
//            SettingsApi.setTaxPercent(new BigDecimal(jsonobject1.getString("tax_rate")));
//        }
//        if (jsonobject1.has("selected_tip_default_value"))
//        {
//            SettingsApi.setTipDefault(1 + jsonobject1.getInt("selected_tip_default_value"));
//        }
//        if (jsonobject1.has("is_tip_enabled"))
//        {
//            SettingsApi.setTipEnabled(jsonobject1.getBoolean("is_tip_enabled"));
//        }
//        if (!jsonobject1.has("tip_default_values")) goto _L6; else goto _L5
//_L5:
//        JSONArray jsonarray1 = jsonobject1.getJSONArray("tip_default_values");
//        jsonarray1.length();
//        JVM INSTR tableswitch 0 3: default 779
//    //                   0 351
//    //                   1 356
//    //                   2 361
//    //                   3 366;
//           goto _L7 _L8 _L9 _L10 _L11
//_L31:
//        int k;
//        if (k >= jsonarray1.length()) goto _L6; else goto _L12
//_L12:
//        JSONObject jsonobject3;
//        BigDecimal bigdecimal;
//        jsonobject3 = jsonarray1.getJSONObject(k);
//        bigdecimal = new BigDecimal(jsonobject3.getString("value"));
//        if (jsonobject3.getInt("type") != 0) goto _L14; else goto _L13
//_L13:
//        SettingsApi.eTipPresetType etippresettype = SettingsApi.eTipPresetType.TYPE_MONEY;
//          goto _L15
//_L8:
//        SettingsApi.setTipPreset1Enabled(false);
//_L9:
//        SettingsApi.setTipPreset2Enabled(false);
//_L10:
//        SettingsApi.setTipPreset3Enabled(false);
//_L11:
//        SettingsApi.setTipPreset4Enabled(false);
//          goto _L7
//        JSONException jsonexception;
//        jsonexception;
//        jsonexception.printStackTrace();
//_L4:
//        return;
//_L14:
//        etippresettype = SettingsApi.eTipPresetType.TYPE_PERCENT;
//          goto _L15
//_L32:
//        SettingsApi.setTipPreset1Enabled(true);
//        SettingsApi.setTipPreset1(bigdecimal);
//        SettingsApi.setTipPreset1Type(etippresettype);
//          goto _L16
//_L33:
//        SettingsApi.setTipPreset2Enabled(true);
//        SettingsApi.setTipPreset2(bigdecimal);
//        SettingsApi.setTipPreset2Type(etippresettype);
//          goto _L16
//_L34:
//        SettingsApi.setTipPreset3Enabled(true);
//        SettingsApi.setTipPreset3(bigdecimal);
//        SettingsApi.setTipPreset3Type(etippresettype);
//          goto _L16
//_L35:
//        SettingsApi.setTipPreset4Enabled(true);
//        SettingsApi.setTipPreset4(bigdecimal);
//        SettingsApi.setTipPreset4Type(etippresettype);
//          goto _L16
//_L6:
//        int i = jsonobject1.getInt("login_mode");
//        if (i != SettingsApi.eSettingKey.LOGIN_ASK_EVERY_TIME.ordinal()) goto _L18; else goto _L17
//_L17:
//        SettingsApi.setLoginSetting(SettingsApi.eSettingKey.LOGIN_ASK_EVERY_TIME);
//_L25:
//        JSONArray jsonarray;
//        if (jsonobject1.has("default_merchant_account_id"))
//        {
//            SettingsApi.setDefaultMerchantAccount(jsonobject1.getInt("default_merchant_account_id"));
//        }
//        jsonarray = jsonobject1.getJSONArray("printers");
//        int j = 0;
//_L36:
//        if (j >= jsonarray.length()) goto _L4; else goto _L19
//_L19:
//        JSONObject jsonobject2 = jsonarray.getJSONObject(j);
//        boolean flag1 = jsonobject2.getBoolean("selected");
//        boolean flag = flag1;
//_L29:
//        String s;
//        String s1;
//        String s2;
//        String s3;
//        String s4;
//        Printer printer;
//        s = jsonobject2.getString("alias");
//        s1 = jsonobject2.getString("mac");
//        s2 = jsonobject2.getString("make");
//        s3 = jsonobject2.getString("model");
//        s4 = jsonobject2.getString("name");
//        PrinterApi.loadPrinters(_context);
//        printer = PrinterApi.getPrinter(s1);
//        if (printer != null) goto _L21; else goto _L20
//_L20:
//        PrinterApi.newPrinter(flag, s, s1, s2, s3, s4, "");
//          goto _L22
//_L18:
//        if (i != SettingsApi.eSettingKey.LOGIN_DEFAULT_MERCHANT_ACCOUNT.ordinal()) goto _L24; else goto _L23
//_L23:
//        SettingsApi.setLoginSetting(SettingsApi.eSettingKey.LOGIN_DEFAULT_MERCHANT_ACCOUNT);
//          goto _L25
//_L24:
//        if (i != SettingsApi.eSettingKey.LOGIN_USE_DEFAULT.ordinal()) goto _L27; else goto _L26
//_L26:
//        SettingsApi.setLoginSetting(SettingsApi.eSettingKey.LOGIN_USE_DEFAULT);
//          goto _L25
//_L27:
//        if (i != SettingsApi.eSettingKey.LOGIN_USE_LAST.ordinal()) goto _L25; else goto _L28
//_L28:
//        SettingsApi.setLoginSetting(SettingsApi.eSettingKey.LOGIN_USE_LAST);
//          goto _L25
//        JSONException jsonexception1;
//        jsonexception1;
//        if (jsonobject2.getInt("selected") == 1)
//        {
//            flag = true;
//        } else
//        {
//            flag = false;
//        }
//          goto _L29
//_L21:
//        printer.setSelected(flag);
//        printer.setAlias(s);
//        printer.setManufacturer(s2);
//        printer.setModel(s3);
//        printer.setProductName(s4);
//        PrinterApi.commit(printer);
//          goto _L22
//_L2:
//        jsonobject1 = null;
//          goto _L30
//_L7:
//        k = 0;
//          goto _L31
//_L15:
//        k;
//        JVM INSTR tableswitch 0 3: default 816
//    //                   0 388
//    //                   1 408
//    //                   2 428
//    //                   3 448;
//           goto _L16 _L32 _L33 _L34 _L35
//_L16:
//        k++;
//          goto _L31
//_L22:
//        j++;
//          goto _L36
//    }

    private static void throwExceptionIfContextNull()
    {
        if (_context == null)
        {
            throw new IllegalStateException("Context reference is null.  Did you forget to call GeneralApi.init()?");
        } else
        {
            return;
        }
    }


//
//
//
///*
//    static boolean access$502(boolean flag)
//    {
//        _hasBeenSynced = flag;
//        return flag;
//    }
//
//*/
//
//    private class eBrand extends Enum
//    {
//
//        private static final eBrand $VALUES[];
//        public static final eBrand PAYANYWHERE;
//        public static final eBrand PHONESWIPE;
//
//        public static eBrand valueOf(String s)
//        {
//            return (eBrand)Enum.valueOf(com/nabancard/api/GeneralApi$eBrand, s);
//        }
//
//        public static eBrand[] values()
//        {
//            return (eBrand[])$VALUES.clone();
//        }
//
//        static 
//        {
//            PAYANYWHERE = new eBrand("PAYANYWHERE", 0);
//            PHONESWIPE = new eBrand("PHONESWIPE", 1);
//            eBrand aebrand[] = new eBrand[2];
//            aebrand[0] = PAYANYWHERE;
//            aebrand[1] = PHONESWIPE;
//            $VALUES = aebrand;
//        }
//
//        private eBrand(String s, int i)
//        {
//            super(s, i);
//        }
//    }
//
//
//    private class LoginBundle
//    {
//
//        private eLoginResult _loginResult;
//        private ArrayList _merchantAccounts;
//        private long _userPk;
//
//        public eLoginResult getLoginResult()
//        {
//            return _loginResult;
//        }
//
//        public ArrayList getMerchantAccounts()
//        {
//            return _merchantAccounts;
//        }
//
//
//
///*
//        static long access$002(LoginBundle loginbundle, long l)
//        {
//            loginbundle._userPk = l;
//            return l;
//        }
//
//*/
//
//
///*
//        static ArrayList access$102(LoginBundle loginbundle, ArrayList arraylist)
//        {
//            loginbundle._merchantAccounts = arraylist;
//            return arraylist;
//        }
//
//*/
//
//
///*
//        static eLoginResult access$202(LoginBundle loginbundle, eLoginResult eloginresult)
//        {
//            loginbundle._loginResult = eloginresult;
//            return eloginresult;
//        }
//
//*/
//
//        public LoginBundle()
//        {
//        }
//    }
//
//
//    private class eLoginResult extends Enum
//    {
//
//        private static final eLoginResult $VALUES[];
//        public static final eLoginResult LOCK_OUT;
//        public static final eLoginResult LOGIN_SUCCESSFUL;
//        public static final eLoginResult LOGIN_UNSUCCESSFUL;
//        public static final eLoginResult NO_INTERNET;
//        public static final eLoginResult NO_INTERNET_OR_CREDENTIALS;
//        public static final eLoginResult NO_MERCHANT_ACCOUNTS;
//        public static final eLoginResult REQUEST_EXPIRED;
//
//        public static eLoginResult valueOf(String s)
//        {
//            return (eLoginResult)Enum.valueOf(com/nabancard/api/GeneralApi$eLoginResult, s);
//        }
//
//        public static eLoginResult[] values()
//        {
//            return (eLoginResult[])$VALUES.clone();
//        }
//
//        static 
//        {
//            LOGIN_SUCCESSFUL = new eLoginResult("LOGIN_SUCCESSFUL", 0);
//            NO_INTERNET = new eLoginResult("NO_INTERNET", 1);
//            LOCK_OUT = new eLoginResult("LOCK_OUT", 2);
//            LOGIN_UNSUCCESSFUL = new eLoginResult("LOGIN_UNSUCCESSFUL", 3);
//            NO_MERCHANT_ACCOUNTS = new eLoginResult("NO_MERCHANT_ACCOUNTS", 4);
//            REQUEST_EXPIRED = new eLoginResult("REQUEST_EXPIRED", 5);
//            NO_INTERNET_OR_CREDENTIALS = new eLoginResult("NO_INTERNET_OR_CREDENTIALS", 6);
//            eLoginResult aeloginresult[] = new eLoginResult[7];
//            aeloginresult[0] = LOGIN_SUCCESSFUL;
//            aeloginresult[1] = NO_INTERNET;
//            aeloginresult[2] = LOCK_OUT;
//            aeloginresult[3] = LOGIN_UNSUCCESSFUL;
//            aeloginresult[4] = NO_MERCHANT_ACCOUNTS;
//            aeloginresult[5] = REQUEST_EXPIRED;
//            aeloginresult[6] = NO_INTERNET_OR_CREDENTIALS;
//            $VALUES = aeloginresult;
//        }
//
//        private eLoginResult(String s, int i)
//        {
//            super(s, i);
//        }
//    }
//
//
//    private class _cls1
//        implements Callable
//    {
//
//        public final volatile Object call()
//        {
//            return call();
//        }
//
//        public final Void call()
//        {
//            java.util.Date date = GeneralApi.getLoggedInMerchant().getLastSync();
//            TimeZone timezone = TimeZone.getTimeZone("EST");
//            Calendar calendar = Calendar.getInstance(timezone);
//            Calendar calendar1;
//            Calendar calendar2;
//            Calendar calendar3;
//            int i;
//            Intent intent;
//            CoreDbHelper coredbhelper;
//            HttpHelper2 httphelper2;
//            Boolean boolean1;
//            if (date == null)
//            {
//                calendar.set(2012, 1, 1);
//            } else
//            {
//                calendar.setTime(date);
//            }
//            calendar1 = Calendar.getInstance(timezone);
//            calendar2 = Calendar.getInstance(timezone);
//            calendar3 = Calendar.getInstance(timezone);
//            calendar2.setTime(calendar.getTime());
//            calendar2.add(12, -5);
//            calendar3.setTime(calendar.getTime());
//            calendar3.add(2, 1);
//            i = 1 + (12 * (calendar1.get(1) - calendar2.get(1)) + (calendar1.get(2) - calendar2.get(2)));
//            intent = new Intent("com.nabancard.ui.SYNC_PROGRESS");
//            intent.putExtra("MAX", i);
//            intent.putExtra("PROGRESS", GeneralApi.progress);
//            GeneralApi._context.sendBroadcast(intent);
//            coredbhelper = GeneralApi.getDbHelper();
//            httphelper2 = new HttpHelper2();
//            if (calendar3.before(calendar1))
//            {
//                boolean1 = Boolean.valueOf(GeneralApi.performSync(calendar2, calendar3, httphelper2, true));
//            } else
//            {
//                boolean1 = Boolean.valueOf(GeneralApi.performSync(calendar2, null, httphelper2, true));
//            }
//            coredbhelper.getMerchantAccountDao().update(GeneralApi.getCurrentMerchant());
//            if (!boolean1.booleanValue())
//            {
//                coredbhelper.close();
//                return null;
//            }
//            int j = 1 + GeneralApi.progress;
//            GeneralApi.progress = j;
//            intent.putExtra("PROGRESS", j);
//            GeneralApi._context.sendBroadcast(intent);
//            calendar2.add(2, 1);
//            calendar3.add(2, 1);
//            for (; calendar2.before(calendar1); calendar3.add(2, 1))
//            {
//                Boolean boolean2;
//                if (calendar3.before(calendar1))
//                {
//                    boolean2 = Boolean.valueOf(GeneralApi.performSync(calendar2, calendar3, httphelper2, false));
//                } else
//                {
//                    boolean2 = Boolean.valueOf(GeneralApi.performSync(calendar2, null, httphelper2, false));
//                }
//                coredbhelper.getMerchantAccountDao().update(GeneralApi.getCurrentMerchant());
//                if (!boolean2.booleanValue())
//                {
//                    coredbhelper.close();
//                    return null;
//                }
//                int k = 1 + GeneralApi.progress;
//                GeneralApi.progress = k;
//                intent.putExtra("PROGRESS", k);
//                GeneralApi._context.sendBroadcast(intent);
//                calendar2.add(2, 1);
//            }
//
//            coredbhelper.getMerchantAccountDao().update(GeneralApi.getCurrentMerchant());
//            coredbhelper.close();
//            GeneralApi._hasBeenSynced = true;
//            TransactionApi.cleanUpSyncMaps();
//            InventoryApi.reset();
//            TransactionApi.reset();
//            return null;
//        }
//
//        _cls1()
//        {
//        }
//    }

}
