// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.britesky.payanywhere.api;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


// Referenced classes of package com.nabancard.api:
//            GeneralApi, Category, DbMerchantAccount

@DatabaseTable(tableName = "sale_item_categories")
final class DbSaleItemCategory extends CoreDbHelper.HasPk
{

    public static final String FIELD_NAME = "_name";
    @DatabaseField
    private boolean _is_active;
    @DatabaseField(foreign = true, columnName = "_merchant_reference_id")
    private DbMerchantAccount _merchant_reference;
    @DatabaseField
    private long _nab_pk;
    @DatabaseField
    private String _name;

    DbSaleItemCategory()
    {
        _is_active = true;
    }

    DbSaleItemCategory(String s)
    {
        _is_active = true;
        _name = s;
        _merchant_reference = GeneralApi.getCurrentMerchant();
    }

    final long getNabPk()
    {
        return _nab_pk;
    }

    final String getName()
    {
        return _name;
    }

    final void setIsActive(boolean flag)
    {
        _is_active = flag;
    }

    final void setNabPk(Long long1)
    {
        _nab_pk = long1.longValue();
    }

    final void setName(String s)
    {
        _name = s;
    }

    final DbSaleItemCategory update(Category category)
    {
        _name = category.getName();
        _is_active = category.isActive();
        _nab_pk = category.getNabPk();
        return this;
    }
}
