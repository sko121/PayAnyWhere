package com.britesky.payanywhere.api;

import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

// Referenced classes of package com.nabancard.api:
//            MerchantAccount

@DatabaseTable(tableName = "merchant_accounts")
final class DbMerchantAccount extends CoreDbHelper.HasPk
{

    static final String FIELD_MID = "_mid";
    @DatabaseField
    private String _dba;
    @DatabaseField
    private Date _last_sync;
    @DatabaseField
    private String _mid;
    @DatabaseField
    private long _nab_pk;

    DbMerchantAccount()
    {
    }

    DbMerchantAccount(long l, String s, String s1)
    {
        _nab_pk = l;
        _mid = s;
        _dba = s1;
    }

    DbMerchantAccount(DbMerchantAccount merchantaccount)
    {
        this(merchantaccount.getNabPk(), merchantaccount.getMid(), merchantaccount.getDba());
    }

    final String getDba()
    {
        return _dba;
    }

    final Date getLastSync()
    {
        return _last_sync;
    }

    final String getMid()
    {
        return _mid;
    }

    final long getNabPk()
    {
        return _nab_pk;
    }

    final void setDba(String s)
    {
        _dba = s;
    }

    final void setLastSync(Date date)
    {
        _last_sync = date;
    }

    final void setNabPk(long l)
    {
        _nab_pk = l;
    }
}
