package com.britesky.payanywhere.api;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.britesky.payanywhere.R;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.CloseableWrappedIterable;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

//Referenced classes of package com.nabancard.api:
//         DbCashRefundTransaction, DbCashSaleTransaction, DbCreditCard, DbCreditCardRefundTransaction, 
//         DbCreditCardSaleTransaction, DbCustomer, DbCustomerEmailRelation, DbEmail, 
//         DbFavorite, DbFilter, DbMerchantAccount, DbMerchantUserRelation, 
//         DbMultimediaItem, DbPreauthTransaction, DbPrinter, DbReceiptInfo, 
//         DbRule, DbSaleItem, DbSaleItemCategory, DbSaleItemCategoryRelation, 
//         DbSaleItemTag, DbSaleItemTagRelation, DbSaleTransactionReceipt, DbSaleTransactionReceiptLineItem, 
//         DbSettings, DbTemporaryCart, DbTemporaryCartItem, DbUserAccount, 
//         DbVoidTransaction, GeneralApi

public class CoreDbHelper extends OrmLiteSqliteOpenHelper
{

    private static final String DATABASE_NAME = "core.db";
    protected static final int DATABASE_VERSION = 1;
    static final String FIELD_IS_ACTIVE = "_is_active";
    static final String FIELD_NAB_PK = "_nab_pk";
    public static final String FIELD_NAME = "_name";
    static final String FOREIGN_FIELD_ITEM = "_item_id";
    static final String FOREIGN_FIELD_MERCHANT_ACCOUNT = "_merchant_reference_id";
    static final String FOREIGN_FIELD_USER_ACCOUNT = "_user_reference_id";
    protected static CoreDbHelper _helper = null;
    protected static final AtomicInteger _usageCounter = new AtomicInteger(0);
    private static final Class databaseClasses[] = {
        DbSaleItem.class,
        DbSaleItemTag.class,
        DbSaleItemCategory.class,
        DbSaleItemCategoryRelation.class,
        DbSaleItemTagRelation.class,
        DbMerchantAccount.class,
        DbUserAccount.class,
        DbMultimediaItem.class,
    };
    private Dao _cashRefundTransactionDao;
    private Dao _cashSaleTransactionDao;
    private Dao _creditCardDao;
    private Dao _creditCardRefundTransactionDao;
    private Dao _creditCardSaleTransactionDao;
    private Dao _customerDao;
    private Dao _customerEmailRelationDao;
    private Dao _emailDao;
    private Dao _favoriteDao;
    private Dao _filterDao;
    private Dao _merchantAccountDao;
    private Dao _merchantUserRelationDao;
    private Dao _multimediaItemDao;
    private Dao _preauthTransactionDao;
    private Dao _printerDao;
    private Dao _receiptInfoDao;
    private Dao _ruleDao;
    private Dao _saleItemCategoryDao;
    private Dao _saleItemCategoryRelationDao;
    private Dao _saleItemDao;
    private Dao _saleItemTagDao;
    private Dao _saleItemTagRelationDao;
    private Dao _saleTransactionReceiptDao;
    private Dao _saleTransactionReceiptLineItemDao;
    private Dao _settingsDao;
    private Dao _temporaryCartDao;
    private Dao _temporaryCartItemDao;
    private Dao _userAccountDao;
    private Dao _voidTransactionDao;

    private CoreDbHelper(Context context)
    {
        super(context, "core.db", null, 1, R.raw.ormlite_config);
        _cashRefundTransactionDao = null;
        _cashSaleTransactionDao = null;
        _creditCardDao = null;
        _creditCardRefundTransactionDao = null;
        _creditCardSaleTransactionDao = null;
        _customerDao = null;
        _customerEmailRelationDao = null;
        _emailDao = null;
        _favoriteDao = null;
        _filterDao = null;
        _merchantAccountDao = null;
        _merchantUserRelationDao = null;
        _multimediaItemDao = null;
        _preauthTransactionDao = null;
        _printerDao = null;
        _receiptInfoDao = null;
        _ruleDao = null;
        _saleItemDao = null;
        _saleItemCategoryDao = null;
        _saleItemCategoryRelationDao = null;
        _saleItemTagDao = null;
        _saleItemTagRelationDao = null;
        _saleTransactionReceiptDao = null;
        _saleTransactionReceiptLineItemDao = null;
        _settingsDao = null;
        _temporaryCartDao = null;
        _temporaryCartItemDao = null;
        _userAccountDao = null;
        _voidTransactionDao = null;
    }

    protected CoreDbHelper(Context context, String s, int i)
    {
        super(context, s, null, i);
        _cashRefundTransactionDao = null;
        _cashSaleTransactionDao = null;
        _creditCardDao = null;
        _creditCardRefundTransactionDao = null;
        _creditCardSaleTransactionDao = null;
        _customerDao = null;
        _customerEmailRelationDao = null;
        _emailDao = null;
        _favoriteDao = null;
        _filterDao = null;
        _merchantAccountDao = null;
        _merchantUserRelationDao = null;
        _multimediaItemDao = null;
        _preauthTransactionDao = null;
        _printerDao = null;
        _receiptInfoDao = null;
        _ruleDao = null;
        _saleItemDao = null;
        _saleItemCategoryDao = null;
        _saleItemCategoryRelationDao = null;
        _saleItemTagDao = null;
        _saleItemTagRelationDao = null;
        _saleTransactionReceiptDao = null;
        _saleTransactionReceiptLineItemDao = null;
        _settingsDao = null;
        _temporaryCartDao = null;
        _temporaryCartItemDao = null;
        _userAccountDao = null;
        _voidTransactionDao = null;
    }

//    private static void deleteHelperMerchant(Dao dao)
//    {
//        DeleteBuilder deletebuilder = dao.deleteBuilder();
//        deletebuilder.where().eq("_merchant_reference_id", Long.valueOf(GeneralApi.getCurrentMerchant().getPk()));
//        deletebuilder.delete();
//    }
//
//    private static void deleteHelperMerchantAndUser(Dao dao)
//    {
//        DeleteBuilder deletebuilder = dao.deleteBuilder();
//        deletebuilder.where().eq("_merchant_reference_id", Long.valueOf(GeneralApi.getCurrentMerchant().getPk())).and().eq("_user_reference_id", Long.valueOf(GeneralApi.getCurrentUser().getPk()));
//        deletebuilder.delete();
//    }
//
//    private static void deleteHelperUser(Dao dao)
//    {
//        DeleteBuilder deletebuilder = dao.deleteBuilder();
//        deletebuilder.where().eq("_user_reference_id", Long.valueOf(GeneralApi.getCurrentUser().getPk()));
//        deletebuilder.delete();
//    }
//
//    private static ArrayList extractPksFromForeignCollection(ForeignCollection foreigncollection)
//    {
//        ArrayList arraylist = new ArrayList();
//        for (Iterator iterator = foreigncollection.iterator(); iterator.hasNext(); arraylist.add(Long.valueOf(((HasPk)iterator.next()).getPk()))) { }
//        return arraylist;
//    }
//
//    static CoreDbHelper getHelper(Context context)
//    {
//        com/nabancard/api/CoreDbHelper;
//        JVM INSTR monitorenter ;
//        CoreDbHelper coredbhelper1;
//        if (_helper == null)
//        {
//            CoreDbHelper coredbhelper = new CoreDbHelper(context);
//            _helper = coredbhelper;
//            coredbhelper.getWritableDatabase().rawQuery("PRAGMA journal_mode = WAL;", null);
//        }
//        _usageCounter.incrementAndGet();
//        coredbhelper1 = _helper;
//        com/nabancard/api/CoreDbHelper;
//        JVM INSTR monitorexit ;
//        return coredbhelper1;
//        Exception exception;
//        exception;
//        throw exception;
//    }
//
//    private static CloseableWrappedIterable getIterableMerchant(Dao dao)
//    {
//        return dao.getWrappedIterable(dao.queryBuilder().where().eq("_merchant_reference_id", Long.valueOf(GeneralApi.getCurrentMerchant().getPk())).prepare());
//    }
//
//    private static CloseableWrappedIterable getIterableMerchantActive(Dao dao)
//    {
//        return dao.getWrappedIterable(dao.queryBuilder().where().eq("_merchant_reference_id", Long.valueOf(GeneralApi.getCurrentMerchant().getPk())).and().eq("_is_active", Boolean.valueOf(true)).prepare());
//    }
//
//    private static CloseableWrappedIterable getIterableMerchantAndUser(Dao dao)
//    {
//        return dao.getWrappedIterable(dao.queryBuilder().where().eq("_merchant_reference_id", Long.valueOf(GeneralApi.getCurrentMerchant().getPk())).and().eq("_user_reference_id", Long.valueOf(GeneralApi.getCurrentUser().getPk())).prepare());
//    }
//
//    private static CloseableWrappedIterable getIterableUser(Dao dao)
//    {
//        return dao.getWrappedIterable(dao.queryBuilder().where().eq("_user_reference_id", Long.valueOf(GeneralApi.getCurrentUser().getPk())).prepare());
//    }
//
//    private static Object getSingleRowMerchantAndUser(Dao dao)
//    {
//        return dao.queryForFirst(dao.queryBuilder().where().eq("_merchant_reference_id", Long.valueOf(GeneralApi.getCurrentMerchant().getPk())).and().eq("_user_reference_id", Long.valueOf(GeneralApi.getCurrentUser().getPk())).prepare());
//    }
//
//    static void resetHelper()
//    {
//        com/nabancard/api/CoreDbHelper;
//        JVM INSTR monitorenter ;
//        _helper = null;
//        com/nabancard/api/CoreDbHelper;
//        JVM INSTR monitorexit ;
//        return;
//        Exception exception;
//        exception;
//        throw exception;
//    }
//
//    public void clear()
//    {
//        ConnectionSource connectionsource = getConnectionSource();
//        Class aclass[];
//        int i;
//        aclass = databaseClasses;
//        i = aclass.length;
//        int j = 0;
//        _L2:
//            if (j >= i)
//            {
//                break; /* Loop/switch isn't completed */
//            }
//        TableUtils.clearTable(connectionsource, aclass[j]);
//        j++;
//        if (true) goto _L2; else goto _L1
//        SQLException sqlexception;
//        sqlexception;
//        sqlexception.printStackTrace();
//        _L1:
//    }
//
//    void clearCashRefundTransactions()
//    {
//        deleteHelperMerchantAndUser(getCashRefundTransactionDao());
//    }
//
//    void clearCashSaleTransactions()
//    {
//        deleteHelperMerchantAndUser(getCashSaleTransactionDao());
//    }
//
//    void clearCreditCardRefundTransactions()
//    {
//        deleteHelperMerchantAndUser(getCreditCardRefundTransactionDao());
//    }
//
//    void clearCreditCardSaleTransactions()
//    {
//        deleteHelperMerchantAndUser(getCreditCardSaleTransactionDao());
//    }
//
//    void clearCustomerEmailRelations()
//    {
//        deleteHelperMerchant(getCustomerEmailRelationDao());
//    }
//
//    void clearCustomers()
//    {
//        deleteHelperMerchant(getCustomerDao());
//    }
//
//    void clearEmails()
//    {
//        deleteHelperMerchant(getEmailDao());
//    }
//
//    void clearFavorites()
//    {
//        deleteHelperUser(getFavoriteDao());
//    }
//
//    void clearFilters()
//    {
//        CloseableWrappedIterable closeablewrappediterable = getIterableMerchantAndUser(getFilterDao());
//        for (Iterator iterator = closeablewrappediterable.iterator(); iterator.hasNext(); clearRules((DbFilter)iterator.next())) { }
//        break MISSING_BLOCK_LABEL_52;
//        Exception exception;
//        exception;
//        SQLException sqlexception1;
//        try
//        {
//            closeablewrappediterable.close();
//        }
//        catch (SQLException sqlexception)
//        {
//            sqlexception.printStackTrace();
//        }
//        throw exception;
//        try
//        {
//            closeablewrappediterable.close();
//        }
//        // Misplaced declaration of an exception variable
//        catch (SQLException sqlexception1)
//        {
//            sqlexception1.printStackTrace();
//        }
//        deleteHelperMerchantAndUser(getFilterDao());
//        return;
//    }
//
//    void clearMerchantUserRelations()
//    {
//        deleteHelperUser(getMerchantUserRelationDao());
//    }
//
//    void clearOrphanedSaleTransactionReceiptLineItems()
//    {
//        DeleteBuilder deletebuilder = getSaleTransactionReceiptLineItemDao().deleteBuilder();
//        deletebuilder.where().eq("_receipt_reference_id", Integer.valueOf(0));
//        deletebuilder.delete();
//    }
//
//    void clearPreauthTransactions()
//    {
//        deleteHelperMerchantAndUser(getPreauthTransactionDao());
//    }
//
//    void clearPrinters()
//    {
//        deleteHelperMerchantAndUser(getPrinterDao());
//    }
//
//    void clearReceiptInfo()
//    {
//        deleteHelperMerchantAndUser(getReceiptInfoDao());
//    }
//
//    void clearRules(DbFilter dbfilter)
//    {
//        getRuleDao().deleteIds(extractPksFromForeignCollection(dbfilter.getRules()));
//    }
//
//    void clearSaleItemCategories()
//    {
//        deleteHelperMerchant(getSaleItemCategoryDao());
//    }
//
//    void clearSaleItemCategoryRelations()
//    {
//        deleteHelperMerchant(getSaleItemCategoryRelationDao());
//    }
//
//    void clearSaleItemTagRelations()
//    {
//        deleteHelperMerchantAndUser(getSaleItemTagRelationDao());
//    }
//
//    void clearSaleItemTags()
//    {
//        deleteHelperUser(getSaleItemTagDao());
//    }
//
//    void clearSaleItems()
//    {
//        deleteHelperMerchant(getSaleItemDao());
//    }
//
//    void clearSaleTransactionReceipts()
//    {
//        CloseableWrappedIterable closeablewrappediterable = getIterableMerchant(getSaleTransactionReceiptDao());
//        DbSaleTransactionReceipt dbsaletransactionreceipt;
//        for (Iterator iterator = closeablewrappediterable.iterator(); iterator.hasNext(); getSaleTransactionReceiptLineItemDao().deleteIds(extractPksFromForeignCollection(dbsaletransactionreceipt.getReceiptLineItems())))
//        {
//            dbsaletransactionreceipt = (DbSaleTransactionReceipt)iterator.next();
//        }
//
//        break MISSING_BLOCK_LABEL_68;
//        Exception exception;
//        exception;
//        SQLException sqlexception1;
//        try
//        {
//            closeablewrappediterable.close();
//        }
//        catch (SQLException sqlexception)
//        {
//            sqlexception.printStackTrace();
//        }
//        throw exception;
//        try
//        {
//            closeablewrappediterable.close();
//        }
//        // Misplaced declaration of an exception variable
//        catch (SQLException sqlexception1)
//        {
//            sqlexception1.printStackTrace();
//        }
//        deleteHelperMerchant(getSaleTransactionReceiptDao());
//        return;
//    }
//
//    void clearSetting()
//    {
//        deleteHelperMerchantAndUser(getSettingsDao());
//    }
//
//    void clearTemporaryCartItems(DbTemporaryCart dbtemporarycart)
//    {
//        getTemporaryCartItemDao().deleteIds(extractPksFromForeignCollection(dbtemporarycart.getCartItems()));
//    }
//
//    void clearTemporaryCarts()
//    {
//        CloseableWrappedIterable closeablewrappediterable = getIterableMerchantAndUser(getTemporaryCartDao());
//        for (Iterator iterator = closeablewrappediterable.iterator(); iterator.hasNext(); clearTemporaryCartItems((DbTemporaryCart)iterator.next())) { }
//        break MISSING_BLOCK_LABEL_52;
//        Exception exception;
//        exception;
//        SQLException sqlexception1;
//        try
//        {
//            closeablewrappediterable.close();
//        }
//        catch (SQLException sqlexception)
//        {
//            sqlexception.printStackTrace();
//        }
//        throw exception;
//        try
//        {
//            closeablewrappediterable.close();
//        }
//        // Misplaced declaration of an exception variable
//        catch (SQLException sqlexception1)
//        {
//            sqlexception1.printStackTrace();
//        }
//        deleteHelperMerchantAndUser(getTemporaryCartDao());
//        return;
//    }
//
//    void clearVoidTransactions()
//    {
//        deleteHelperMerchantAndUser(getVoidTransactionDao());
//    }
//
//    public void close()
//    {
//        if (_usageCounter.decrementAndGet() == 0)
//        {
//            super.close();
//            _cashRefundTransactionDao = null;
//            _cashSaleTransactionDao = null;
//            _creditCardDao = null;
//            _creditCardRefundTransactionDao = null;
//            _creditCardSaleTransactionDao = null;
//            _customerDao = null;
//            _customerEmailRelationDao = null;
//            _emailDao = null;
//            _favoriteDao = null;
//            _filterDao = null;
//            _merchantAccountDao = null;
//            _merchantUserRelationDao = null;
//            _multimediaItemDao = null;
//            _preauthTransactionDao = null;
//            _printerDao = null;
//            _receiptInfoDao = null;
//            _ruleDao = null;
//            _saleItemDao = null;
//            _saleItemCategoryDao = null;
//            _saleItemCategoryRelationDao = null;
//            _saleItemTagDao = null;
//            _saleItemTagRelationDao = null;
//            _saleTransactionReceiptDao = null;
//            _saleTransactionReceiptLineItemDao = null;
//            _settingsDao = null;
//            _temporaryCartDao = null;
//            _temporaryCartItemDao = null;
//            _userAccountDao = null;
//            _voidTransactionDao = null;
//            _helper = null;
//        }
//    }
//
//    CloseableWrappedIterable getActiveSaleItemCategoryIterable()
//    {
//        return getIterableMerchantActive(getSaleItemCategoryDao());
//    }
//
//    CloseableWrappedIterable getActiveSaleItemIterable()
//    {
//        return getIterableMerchantActive(getSaleItemDao());
//    }
//
//    Dao getCashRefundTransactionDao()
//    {
//        if (_cashRefundTransactionDao == null)
//        {
//            _cashRefundTransactionDao = getDao(com/nabancard/api/DbCashRefundTransaction);
//        }
//        return _cashRefundTransactionDao;
//    }
//
//    CloseableWrappedIterable getCashRefundTransactionIterable()
//    {
//        return getIterableMerchantAndUser(getCashRefundTransactionDao());
//    }
//
//    Dao getCashSaleTransactionDao()
//    {
//        if (_cashSaleTransactionDao == null)
//        {
//            _cashSaleTransactionDao = getDao(com/nabancard/api/DbCashSaleTransaction);
//        }
//        return _cashSaleTransactionDao;
//    }
//
//    CloseableWrappedIterable getCashSaleTransactionIterable()
//    {
//        return getIterableMerchantAndUser(getCashSaleTransactionDao());
//    }
//
//    Dao getCreditCardDao()
//    {
//        if (_creditCardDao == null)
//        {
//            _creditCardDao = getDao(com/nabancard/api/DbCreditCard);
//        }
//        return _creditCardDao;
//    }
//
//    Dao getCreditCardRefundTransactionDao()
//    {
//        if (_creditCardRefundTransactionDao == null)
//        {
//            _creditCardRefundTransactionDao = getDao(com/nabancard/api/DbCreditCardRefundTransaction);
//        }
//        return _creditCardRefundTransactionDao;
//    }
//
//    CloseableWrappedIterable getCreditCardRefundTransactionIterable()
//    {
//        return getIterableMerchantAndUser(getCreditCardRefundTransactionDao());
//    }
//
//    Dao getCreditCardSaleTransactionDao()
//    {
//        if (_creditCardSaleTransactionDao == null)
//        {
//            _creditCardSaleTransactionDao = getDao(com/nabancard/api/DbCreditCardSaleTransaction);
//        }
//        return _creditCardSaleTransactionDao;
//    }
//
//    CloseableWrappedIterable getCreditCardSaleTransactionIterable()
//    {
//        return getIterableMerchantAndUser(getCreditCardSaleTransactionDao());
//    }
//
//    Dao getCustomerDao()
//    {
//        if (_customerDao == null)
//        {
//            _customerDao = getDao(com/nabancard/api/DbCustomer);
//        }
//        return _customerDao;
//    }
//
//    Dao getCustomerEmailRelationDao()
//    {
//        if (_customerEmailRelationDao == null)
//        {
//            _customerEmailRelationDao = getDao(com/nabancard/api/DbCustomerEmailRelation);
//        }
//        return _customerEmailRelationDao;
//    }
//
//    CloseableWrappedIterable getCustomerEmailRelationIterable()
//    {
//        return getIterableMerchant(getCustomerEmailRelationDao());
//    }
//
//    CloseableWrappedIterable getCustomerIterable()
//    {
//        return getIterableMerchant(getCustomerDao());
//    }
//
//    Dao getEmailDao()
//    {
//        if (_emailDao == null)
//        {
//            _emailDao = getDao(com/nabancard/api/DbEmail);
//        }
//        return _emailDao;
//    }
//
//    CloseableWrappedIterable getEmailIterator()
//    {
//        return getIterableMerchant(getEmailDao());
//    }
//
//    Dao getFavoriteDao()
//    {
//        if (_favoriteDao == null)
//        {
//            _favoriteDao = getDao(com/nabancard/api/DbFavorite);
//        }
//        return _favoriteDao;
//    }
//
//    CloseableWrappedIterable getFavoriteIterable()
//    {
//        return getIterableUser(getFavoriteDao());
//    }
//
//    Dao getFilterDao()
//    {
//        if (_filterDao == null)
//        {
//            _filterDao = getDao(com/nabancard/api/DbFilter);
//        }
//        return _filterDao;
//    }
//
//    CloseableWrappedIterable getFilterIterable()
//    {
//        return getIterableMerchantAndUser(getFilterDao());
//    }

    Dao getMerchantAccountDao()
    {
        if (_merchantAccountDao == null)
        {
            try {
                _merchantAccountDao = getDao(DbMerchantAccount.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return _merchantAccountDao;
    }

//    Dao getMerchantUserRelationDao()
//    {
//        if (_merchantUserRelationDao == null)
//        {
//            _merchantUserRelationDao = getDao(com/nabancard/api/DbMerchantUserRelation);
//        }
//        return _merchantUserRelationDao;
//    }
//
//    CloseableWrappedIterable getMerchantUserRelationIterable()
//    {
//        return getIterableUser(getMerchantUserRelationDao());
//    }

    Dao getMultimediaItemDao()
    {
        if (_multimediaItemDao == null)
        {
            try {
                _multimediaItemDao = getDao(DbMultimediaItem.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return _multimediaItemDao;
    }

//    Dao getPreauthTransactionDao()
//    {
//        if (_preauthTransactionDao == null)
//        {
//            _preauthTransactionDao = getDao(com/nabancard/api/DbPreauthTransaction);
//        }
//        return _preauthTransactionDao;
//    }
//
//    CloseableWrappedIterable getPreauthTransactionIterable()
//    {
//        return getIterableMerchantAndUser(getPreauthTransactionDao());
//    }
//
//    Dao getPrinterDao()
//    {
//        if (_printerDao == null)
//        {
//            _printerDao = getDao(com/nabancard/api/DbPrinter);
//        }
//        return _printerDao;
//    }
//
//    CloseableWrappedIterable getPrinterIterable()
//    {
//        return getIterableMerchantAndUser(getPrinterDao());
//    }
//
//    DbReceiptInfo getReceiptInfo()
//    {
//        return (DbReceiptInfo)getSingleRowMerchantAndUser(getReceiptInfoDao());
//    }
//
//    Dao getReceiptInfoDao()
//    {
//        if (_receiptInfoDao == null)
//        {
//            _receiptInfoDao = getDao(com/nabancard/api/DbReceiptInfo);
//        }
//        return _receiptInfoDao;
//    }
//
//    Dao getRuleDao()
//    {
//        if (_ruleDao == null)
//        {
//            _ruleDao = getDao(com/nabancard/api/DbRule);
//        }
//        return _ruleDao;
//    }

    Dao getSaleItemCategoryDao()
    {
        if (_saleItemCategoryDao == null)
        {
            try {
                _saleItemCategoryDao = getDao(DbSaleItemCategory.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return _saleItemCategoryDao;
    }
//
//    CloseableWrappedIterable getSaleItemCategoryIterable()
//    {
//        return getIterableMerchant(getSaleItemCategoryDao());
//    }
//
    Dao getSaleItemCategoryRelationDao()
    {
        if (_saleItemCategoryRelationDao == null)
        {
            try {
                _saleItemCategoryRelationDao = getDao(DbSaleItemCategoryRelation.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return _saleItemCategoryRelationDao;
    }
//
//    CloseableWrappedIterable getSaleItemCategoryRelationIterable()
//    {
//        return getIterableMerchant(getSaleItemCategoryRelationDao());
//    }

    Dao getSaleItemDao()
    {
        if (_saleItemDao == null)
        {
            try {
                _saleItemDao = getDao(DbSaleItem.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return _saleItemDao;
    }

//    CloseableWrappedIterable getSaleItemIterable()
//    {
//        return getIterableMerchant(getSaleItemDao());
//    }

    Dao getSaleItemTagDao()
    {
        if (_saleItemTagDao == null)
        {
            try {
                _saleItemTagDao = getDao(DbSaleItemTag.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return _saleItemTagDao;
    }
//
//    CloseableWrappedIterable getSaleItemTagIterable()
//    {
//        return getIterableUser(getSaleItemTagDao());
//    }

    Dao getSaleItemTagRelationDao()
    {
        if (_saleItemTagRelationDao == null)
        {
            try {
                _saleItemTagRelationDao = getDao(DbSaleItemTagRelation.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return _saleItemTagRelationDao;
    }

//    CloseableWrappedIterable getSaleItemTagRelationIterable()
//    {
//        return getIterableMerchantAndUser(getSaleItemTagRelationDao());
//    }
//
//    Dao getSaleTransactionReceiptDao()
//    {
//        if (_saleTransactionReceiptDao == null)
//        {
//            _saleTransactionReceiptDao = getDao(com/nabancard/api/DbSaleTransactionReceipt);
//        }
//        return _saleTransactionReceiptDao;
//    }
//
//    CloseableWrappedIterable getSaleTransactionReceiptIterator()
//    {
//        return getIterableMerchant(getSaleTransactionReceiptDao());
//    }
//
//    Dao getSaleTransactionReceiptLineItemDao()
//    {
//        if (_saleTransactionReceiptLineItemDao == null)
//        {
//            _saleTransactionReceiptLineItemDao = getDao(com/nabancard/api/DbSaleTransactionReceiptLineItem);
//        }
//        return _saleTransactionReceiptLineItemDao;
//    }
//
//    DbSettings getSetting()
//    {
//        return (DbSettings)getSingleRowMerchantAndUser(getSettingsDao());
//    }
//
//    Dao getSettingsDao()
//    {
//        if (_settingsDao == null)
//        {
//            _settingsDao = getDao(com/nabancard/api/DbSettings);
//        }
//        return _settingsDao;
//    }
//
//    Dao getTemporaryCartDao()
//    {
//        if (_temporaryCartDao == null)
//        {
//            _temporaryCartDao = getDao(com/nabancard/api/DbTemporaryCart);
//        }
//        return _temporaryCartDao;
//    }
//
//    Dao getTemporaryCartItemDao()
//    {
//        if (_temporaryCartItemDao == null)
//        {
//            _temporaryCartItemDao = getDao(com/nabancard/api/DbTemporaryCartItem);
//        }
//        return _temporaryCartItemDao;
//    }
//
//    CloseableWrappedIterable getTemporaryCartIterable()
//    {
//        return getIterableMerchantAndUser(getTemporaryCartDao());
//    }

    Dao getUserAccountDao()
    {
        if (_userAccountDao == null)
        {
            try {
                _userAccountDao = getDao(DbUserAccount.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return _userAccountDao;
    }

//    Dao getVoidTransactionDao()
//    {
//        if (_voidTransactionDao == null)
//        {
//            _voidTransactionDao = getDao(com/nabancard/api/DbVoidTransaction);
//        }
//        return _voidTransactionDao;
//    }
//
//    CloseableWrappedIterable getVoidTransactionIterable()
//    {
//        return getIterableMerchantAndUser(getVoidTransactionDao());
//    }

    public void onCreate(SQLiteDatabase sqlitedatabase, ConnectionSource connectionsource)
    {
//        Class aclass[];
//        int i;
//        int j;
//        try
//        {
//            aclass = databaseClasses;
//            i = aclass.length;
//        }
//        catch (SQLException sqlexception)
//        {
//            throw new RuntimeException(sqlexception);
//        }
//        j = 0;
//        if (j >= i)
//        {
//            break; /* Loop/switch isn't completed */
//        }
//        TableUtils.createTable(connectionsource, aclass[j]);
//        j++;
//        if (true) goto _L2; else goto _L1
//        _L2:
//            break MISSING_BLOCK_LABEL_13;
//        _L1:
        
//        Class aclass[];
//        aclass = databaseClasses;
//        for (int i = 0; i < aclass.length; i++) {
//            try {
//                TableUtils.createTable(connectionsource, aclass[i]);
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
    }

    public void onUpgrade(SQLiteDatabase sqlitedatabase, ConnectionSource connectionsource, int i, int j)
    {
    }


    public static class HasPk
    {

        static final String FIELD_PK = "_pk";
        @DatabaseField(generatedId = true)
        private long _pk;

        final long getPk()
        {
            return _pk;
        }

        HasPk()
        {
        }
    }

}

