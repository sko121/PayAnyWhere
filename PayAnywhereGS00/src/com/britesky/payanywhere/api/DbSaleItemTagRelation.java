// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.britesky.payanywhere.api;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


// Referenced classes of package com.nabancard.api:
//            GeneralApi, DbSaleItem, DbMerchantAccount, DbSaleItemTag, 
//            DbUserAccount

@DatabaseTable(tableName = "sale_item_tag_relations")
final class DbSaleItemTagRelation extends CoreDbHelper.HasPk
{

    public static final String FIELD_ITEM = "_item_id";
    public static final String FIELD_TAG = "_tag_id";
    @DatabaseField(foreign = true)
    private DbSaleItem _item;
    @DatabaseField(foreign = true, columnName = "_merchant_reference_id")
    private DbMerchantAccount _merchant_reference;
    @DatabaseField
    private long _nab_pk;
    @DatabaseField(foreign = true)
    private DbSaleItemTag _tag;
    @DatabaseField(foreign = true)
    private DbUserAccount _user_reference;

    DbSaleItemTagRelation()
    {
    }

    DbSaleItemTagRelation(DbSaleItem dbsaleitem, DbSaleItemTag dbsaleitemtag)
    {
        _item = dbsaleitem;
        _tag = dbsaleitemtag;
        _merchant_reference = GeneralApi.getCurrentMerchant();
        _user_reference = GeneralApi.getCurrentUser();
    }

    final DbSaleItem getItem()
    {
        return _item;
    }

    final long getNabPk()
    {
        return _nab_pk;
    }

    final DbSaleItemTag getTag()
    {
        return _tag;
    }
}
