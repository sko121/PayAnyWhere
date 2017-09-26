// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.britesky.payanywhere.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Referenced classes of package com.nabancard.api:
//            DbSaleItemTag, InventoryItem

public class Tag
{

    private boolean _is_active;
    private final ArrayList _items;
    private long _nab_pk;
    private String _name;
    private long _pk;

    Tag(long l, long l1, String s)
    {
        _items = new ArrayList();
        _pk = l;
        _nab_pk = l1;
        _name = s;
    }

    Tag(DbSaleItemTag dbsaleitemtag)
    {
        this(dbsaleitemtag.getPk(), dbsaleitemtag.getNabPk(), dbsaleitemtag.getName());
    }

    Tag(String s)
    {
        this(-1L, -1L, s);
    }

    boolean addItem(InventoryItem inventoryitem)
    {
        return _items.add(inventoryitem);
    }

    public List getItems()
    {
        return Collections.unmodifiableList(_items);
    }

    public long getNabPk()
    {
        return _nab_pk;
    }

    public String getName()
    {
        return _name;
    }

    public long getPk()
    {
        return _pk;
    }

    boolean isActive()
    {
        return _is_active;
    }

    boolean removeItem(InventoryItem inventoryitem)
    {
        return _items.remove(inventoryitem);
    }

    void setIsActive(boolean flag)
    {
        _is_active = flag;
    }

    void setNabPk(long l)
    {
        _nab_pk = l;
    }

    public void setName(String s)
    {
        _name = s;
    }

    void setPk(long l)
    {
        _pk = l;
    }
}
