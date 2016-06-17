// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.britesky.payanywhere.api;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


// Referenced classes of package com.nabancard.api:
//            DbMerchantAccount

@DatabaseTable(tableName = "user_accounts")
final class DbUserAccount extends CoreDbHelper.HasPk
{
    @DatabaseField(foreign = true, columnName = "_default_id")
    private DbMerchantAccount _default;
    @DatabaseField
    private boolean _is_pattern;
    @DatabaseField
    private String _login_key;
    @DatabaseField
    private eMerchantAccountLoginSetting _merchant_account_login_setting;
    @DatabaseField
    private long _nab_pk;
    @DatabaseField
    private String _security;
    @DatabaseField
    private String _user_name;

    DbUserAccount()
    {
    }

    DbUserAccount(long l, String s)
    {
        _nab_pk = l;
        _user_name = s;
    }

//    final DbMerchantAccount getDefaultMerchantAccount()
//    {
//        return _default;
//    }
//
//    final String getLoginKey()
//    {
//        return _login_key;
//    }
//
//    final eMerchantAccountLoginSetting getMerchantAccountLoginSetting()
//    {
//        return _merchant_account_login_setting;
//    }
//
//    final long getNabPk()
//    {
//        return _nab_pk;
//    }
//
//    final String getPattern()
//    {
//        if (_is_pattern)
//        {
//            return _security;
//        } else
//        {
//            return null;
//        }
//    }
//
//    final String getPin()
//    {
//        if (_is_pattern)
//        {
//            return null;
//        } else
//        {
//            return _security;
//        }
//    }
//
//    final String getUserName()
//    {
//        return _user_name;
//    }
//
//    final void setDefaultMerchantAccount(DbMerchantAccount dbmerchantaccount)
//    {
//        _default = dbmerchantaccount;
//        _merchant_account_login_setting = eMerchantAccountLoginSetting.DEFAULT_ACCOUNT;
//    }
//
//    final void setLoginKey(String s)
//    {
//        _login_key = s;
//    }
//
//    final void setMerchantAccountLoginSetting(eMerchantAccountLoginSetting emerchantaccountloginsetting)
//    {
//        _merchant_account_login_setting = emerchantaccountloginsetting;
//        if (eMerchantAccountLoginSetting.ASK_EVERY_TIME == _merchant_account_login_setting)
//        {
//            _default = null;
//        }
//    }
//
//    final void setPattern(String s)
//    {
//        _security = s;
//        _is_pattern = true;
//    }
//
//    final void setPin(String s)
//    {
//        _security = s;
//        _is_pattern = false;
//    }
//
//    final void setUseLastMerchantAccount(DbMerchantAccount dbmerchantaccount)
//    {
//        _default = dbmerchantaccount;
//        _merchant_account_login_setting = eMerchantAccountLoginSetting.USE_LAST;
//    }
//
      private enum eMerchantAccountLoginSetting {
          ASK_EVERY_TIME, USE_LAST, DEFAULT_ACCOUNT
      }
    
//    private class eMerchantAccountLoginSetting extends Enum
//    {
//
//        private static final eMerchantAccountLoginSetting $VALUES[];
//        public static final eMerchantAccountLoginSetting ASK_EVERY_TIME;
//        public static final eMerchantAccountLoginSetting DEFAULT_ACCOUNT;
//        public static final eMerchantAccountLoginSetting USE_LAST;
//
//        public static eMerchantAccountLoginSetting valueOf(String s)
//        {
//            return (eMerchantAccountLoginSetting)Enum.valueOf(com/nabancard/api/DbUserAccount$eMerchantAccountLoginSetting, s);
//        }
//
//        public static eMerchantAccountLoginSetting[] values()
//        {
//            return (eMerchantAccountLoginSetting[])$VALUES.clone();
//        }
//
//        static 
//        {
//            ASK_EVERY_TIME = new eMerchantAccountLoginSetting("ASK_EVERY_TIME", 0);
//            USE_LAST = new eMerchantAccountLoginSetting("USE_LAST", 1);
//            DEFAULT_ACCOUNT = new eMerchantAccountLoginSetting("DEFAULT_ACCOUNT", 2);
//            eMerchantAccountLoginSetting aemerchantaccountloginsetting[] = new eMerchantAccountLoginSetting[3];
//            aemerchantaccountloginsetting[0] = ASK_EVERY_TIME;
//            aemerchantaccountloginsetting[1] = USE_LAST;
//            aemerchantaccountloginsetting[2] = DEFAULT_ACCOUNT;
//            $VALUES = aemerchantaccountloginsetting;
//        }
//
//        private eMerchantAccountLoginSetting(String s, int i)
//        {
//            super(s, i);
//        }
//    }
//
}
