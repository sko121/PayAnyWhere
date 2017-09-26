// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.britesky.payanywhere.ui.util;

import java.math.BigDecimal;
import java.util.Comparator;

import com.britesky.payanywhere.api.InventoryItem;

// Referenced classes of package com.nabancard.ui.util:
//            s, u, i

final class PayComparator
    implements Comparator
{

//    final u a;
//    final i b;
//
//    PayComparator(i j, u u1)
//    {
//        b = j;
//        a = u1;
//    }

    public final int compare(InventoryItem inventoryitem, InventoryItem inventoryitem1)
    {
        if (inventoryitem == null || inventoryitem1 == null)
        {
            return 0;
        }
//        switch (s.a[a.ordinal()])
//        {
//        default:
//            return 0;
//
//        case 1: // '\001'
            return inventoryitem.getName().compareToIgnoreCase(inventoryitem1.getName());
//
//        case 2: // '\002'
//            return inventoryitem1.getName().compareToIgnoreCase(inventoryitem.getName());
//
//        case 3: // '\003'
//            return inventoryitem.getPrice().toBigDecimal().compareTo(inventoryitem1.getPrice().toBigDecimal());
//
//        case 4: // '\004'
//            return inventoryitem1.getPrice().toBigDecimal().compareTo(inventoryitem.getPrice().toBigDecimal());
//
//        case 5: // '\005'
//            i _tmp = b;
//            return i.a(inventoryitem.getCategory(), inventoryitem1.getCategory());
//
//        case 6: // '\006'
//            i _tmp1 = b;
//            break;
//        }
//        return i.a(inventoryitem1.getCategory(), inventoryitem.getCategory());
    }

    public final int compare(Object obj, Object obj1)
    {
        return compare((InventoryItem)obj, (InventoryItem)obj1);
    }
}
