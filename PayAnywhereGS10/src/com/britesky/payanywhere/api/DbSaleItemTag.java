// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.britesky.payanywhere.api;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


// Referenced classes of package com.nabancard.api:
//            GeneralApi, Tag, DbUserAccount

@DatabaseTable(tableName = "sale_item_tags")
final class DbSaleItemTag extends CoreDbHelper.HasPk
{

    public static final String FIELD_ACTIVE = "_is_active";
    public static final String FIELD_NAME = "_name";
    @DatabaseField
    private boolean _is_active;
    @DatabaseField
    private long _nab_pk;
    @DatabaseField
    private String _name;
    @DatabaseField(foreign = true, columnName = "_user_reference_id")
    private DbUserAccount _user_reference;

    DbSaleItemTag()
    {
        _is_active = true;
    }

    DbSaleItemTag(String s)
    {
        _is_active = true;
        _name = s;
        _user_reference = GeneralApi.getCurrentUser();
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

    final DbSaleItemTag update(Tag tag)
    {
        _name = tag.getName();
        _is_active = tag.isActive();
        _nab_pk = tag.getNabPk();
        return this;
    }
}
