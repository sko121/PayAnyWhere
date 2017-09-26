// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.britesky.payanywhere.api;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import java.sql.SQLException;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

// Referenced classes of package com.nabancard.api:
//            CoreDbHelper

class CoreTestDriveDbHelper extends CoreDbHelper
{

    private static final String DATABASE_NAME = "testdrive.db";
    private static final String SPANISH_DATABASE_NAME = "testdrive_es.db";

    private CoreTestDriveDbHelper(Context context)
    {
//        String s;
//        if (context.getResources().getConfiguration().locale.getLanguage().equals("es"))
//        {
//            s = "testdrive_es.db";
//        } else
//        {
//            s = "testdrive.db";
//        }
        super(context, "testdrive.db", 1);
    }

    static CoreDbHelper getHelper(Context context)
    {
//        com/nabancard/api/CoreTestDriveDbHelper;
//        JVM INSTR monitorenter ;
        CoreDbHelper coredbhelper;
        if (_helper == null)
        {
            _helper = new CoreTestDriveDbHelper(context);
        }
        _usageCounter.incrementAndGet();
        coredbhelper = _helper;
//        com/nabancard/api/CoreTestDriveDbHelper;
//        JVM INSTR monitorexit ;
//        return coredbhelper;
//        Exception exception;
//        exception;
//        throw exception;
        return coredbhelper;
    }

//    void clearTestDrive()
//    {
//        if (_helper == null)
//        {
//            return;
//        }
//        try
//        {
//            clearCashRefundTransactions();
//            clearCashSaleTransactions();
//            clearCreditCardRefundTransactions();
//            clearCreditCardSaleTransactions();
//            clearCustomerEmailRelations();
//            clearCustomers();
//            clearEmails();
//            clearFavorites();
//            clearFilters();
//            clearPreauthTransactions();
//            clearPrinters();
//            clearReceiptInfo();
//            clearSaleItemCategoryRelations();
//            clearSaleItemTagRelations();
//            clearSaleItemCategories();
//            clearSaleItemTags();
//            clearSaleItems();
//            clearSaleTransactionReceipts();
//            clearSetting();
//            clearTemporaryCarts();
//            clearVoidTransactions();
//            return;
//        }
//        catch (SQLException sqlexception)
//        {
//            sqlexception.printStackTrace();
//        }
//    }
}
