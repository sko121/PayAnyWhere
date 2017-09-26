// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.britesky.payanywhere.api;

import android.util.Log;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

// Referenced classes of package com.nabancard.api:
//            SaleTransactionItem, InventoryItem, Money, InventoryApi, 
//            GeneralTransaction, SettingsApi

public class CartApi
{

    private static final LinkedHashMap _currentItems = new LinkedHashMap();
    private static Money _customPreauthAmount;
    private static Money _customRefundAmount;
    private static BigDecimal _discountPercent;
    private static Money _discountTotal;
//    private static InventoryApi.OnInventoryChangedListener _inventoryListener = new _cls1();
    private static ArrayList _listeners = new ArrayList();
//    private static GeneralTransaction _parentTransaction;
    private static Money _preauthCompleteAmount;
    private static Money _preauthTotal;
    private static Money _refundTotal;
    private static Money _refundableAmount;
    private static Money _subtotal;
    private static Money _taxTotal;
    private static Money _total;
    private static Money _totalBeforeDiscount;
    private static int _totalItemCount;

    public CartApi()
    {
    }

    public static void addOnCartChangedListener(OnCartChangedListener oncartchangedlistener)
    {
        if (!_listeners.contains(oncartchangedlistener))
        {
            _listeners.add(oncartchangedlistener);
        }
    }

    public static SaleTransactionItem addOrUpdateItem(InventoryItem inventoryitem)
    {
        return addOrUpdateItem(inventoryitem, true);
    }

//    public static SaleTransactionItem addOrUpdateItem(InventoryItem inventoryitem, int i)
//    {
//        SaleTransactionItem saletransactionitem;
//        if (inventoryitem instanceof SaleTransactionItem)
//        {
//            saletransactionitem = null;
//        } else
//        {
//            saletransactionitem = updateItem(inventoryitem, i);
//            if (saletransactionitem == null)
//            {
//                SaleTransactionItem saletransactionitem1 = new SaleTransactionItem(inventoryitem, i);
//                synchronized (_currentItems)
//                {
//                    _currentItems.put(Long.valueOf(saletransactionitem1.getPk()), saletransactionitem1);
//                }
//                recalculateCartTotals();
//                return saletransactionitem1;
//            }
//        }
//        return saletransactionitem;
//    }
//
    public static SaleTransactionItem addOrUpdateItem(InventoryItem inventoryitem, boolean flag)
    {
        SaleTransactionItem saletransactionitem;
        
        if ((inventoryitem instanceof SaleTransactionItem)) {

            if (((SaleTransactionItem)inventoryitem).getQuantity() < 0)
            {
                throw new IllegalArgumentException("Quantity must be more than zero");
            }
            if (inventoryitem.getPrice().isLessThanZero())
            {
                throw new IllegalArgumentException("Price must be more than zero");
            }
            if (getItemQuantity(inventoryitem) > 0) {

                saletransactionitem = setItemQuantity(inventoryitem.getPk(), ((SaleTransactionItem)inventoryitem).getQuantity(), flag);

                return saletransactionitem;
            }
        } else {

            if (getItemQuantity(inventoryitem) > 0)
            {
                return increaseItemQuantity(inventoryitem, flag);
            }
        }

        if (inventoryitem instanceof SaleTransactionItem)
        {
            saletransactionitem = (SaleTransactionItem)inventoryitem;
        } else
        {
            saletransactionitem = new SaleTransactionItem(inventoryitem, 1);
        }
        synchronized (_currentItems)
        {
            _currentItems.put(Long.valueOf(saletransactionitem.getPk()), saletransactionitem);
        }
        if (flag)
        {
            recalculateCartTotals();
        }
        return saletransactionitem;
    }

    static boolean cartExists()
    {
        return true;
    }

    public static void clearOnCartChangedListeners()
    {
        _listeners.clear();
    }

    public static SaleTransactionItem decreaseItemQuantity(InventoryItem inventoryitem, boolean flag)
    {
        return modifyItemQuantity(inventoryitem.getPk(), -1, flag);
    }

    public static List<SaleTransactionItem> getCartItems()
    {
        ArrayList<SaleTransactionItem> arraylist = new ArrayList<SaleTransactionItem>();
        Long long1;
        for (Iterator iterator = _currentItems.keySet().iterator(); iterator.hasNext(); arraylist.add((SaleTransactionItem)_currentItems.get(long1)))
        {
            long1 = (Long)iterator.next();
        }

        return Collections.unmodifiableList(arraylist);
    }

    public static Money getCustomPreAuthAmount()
    {
        return _customPreauthAmount;
    }

    public static Money getDiscountAmount()
    {
        return _discountTotal;
    }

    public static BigDecimal getDiscountPercentage()
    {
        return _discountPercent;
    }

    private static int getItemQuantity(InventoryItem inventoryitem)
    {
        if (_currentItems.containsKey(Long.valueOf(inventoryitem.getPk())))
        {
            return ((SaleTransactionItem)_currentItems.get(Long.valueOf(inventoryitem.getPk()))).getQuantity();
        } else
        {
            return -1;
        }
    }

//    static GeneralTransaction getParentTransaction()
//    {
//        return _parentTransaction;
//    }

    public static Money getPreAuthCompletableAmount()
    {
        return _preauthCompleteAmount;
    }

//    public static Money getPreauthTotal()
//    {
//        if (_preauthTotal == null && _preauthCompleteAmount != null && !recalculateCartTotals())
//        {
//            return null;
//        } else
//        {
//            return _preauthTotal;
//        }
//    }
//
//    public static Money getRefundTotal()
//    {
//        if (_refundTotal == null && _refundableAmount != null && !recalculateCartTotals())
//        {
//            return null;
//        } else
//        {
//            return _refundTotal;
//        }
//    }
//
//    public static Money getRefundableAmount()
//    {
//        return _refundableAmount;
//    }
//
    public static Money getSubtotal()
    {
        if (_subtotal == null && !recalculateCartTotals())
        {
            return null;
        } else
        {
            return _subtotal;
        }
    }
//
//    public static Money getTax()
//    {
//        if (_taxTotal == null && !recalculateCartTotals())
//        {
//            return null;
//        } else
//        {
//            return _taxTotal;
//        }
//    }
//
    public static Money getTotal()
    {
        if (_total == null && !recalculateCartTotals())
        {
            return null;
        } else
        {
            return _total;
        }
    }

    public static int getTotalItemCount()
    {
        return _totalItemCount;
    }

    public static SaleTransactionItem increaseItemQuantity(InventoryItem inventoryitem, boolean flag)
    {
        return modifyItemQuantity(inventoryitem.getPk(), 1, flag);
    }

    public static void init()
    {
        Log.d("NAB", "CartApi: Initializing");
        _currentItems.clear();
        _totalItemCount = 0;
//        _parentTransaction = null;
        _subtotal = null;
        _discountTotal = null;
        _discountPercent = null;
        _refundableAmount = null;
        _preauthCompleteAmount = null;
        _customPreauthAmount = null;
        _preauthTotal = null;
        _customRefundAmount = null;
        _refundTotal = null;
        _taxTotal = null;
        _total = null;
//        InventoryApi.addOnInventoryChangedListener(_inventoryListener);
        onCartChanged();
    }
//
//    public static boolean loadCartForPreauth(GeneralTransaction generaltransaction)
//    {
//        if (GeneralTransaction.eTransactionType.PREAUTH != generaltransaction.getTransactionType())
//        {
//            return false;
//        }
//        resetCart();
//        init();
//        for (Iterator iterator = generaltransaction.getReceiptLineItems().iterator(); iterator.hasNext(); addOrUpdateItem((SaleTransactionItem)iterator.next(), false)) { }
//        if (generaltransaction.getReceiptDiscountAmount().isGreaterThanZero())
//        {
//            setDiscountAmount(generaltransaction.getReceiptDiscountAmount());
//        }
//        _parentTransaction = generaltransaction;
//        _preauthCompleteAmount = generaltransaction.getPreauthAmount();
//        return recalculateCartTotals();
//    }
//
//    public static boolean loadCartForRefund(GeneralTransaction generaltransaction)
//    {
//        switch (_cls2..SwitchMap.com.nabancard.api.GeneralTransaction.eTransactionType[generaltransaction.getTransactionType().ordinal()])
//        {
//        default:
//            return false;
//
//        case 1: // '\001'
//        case 2: // '\002'
//            resetCart();
//            break;
//        }
//        init();
//        _totalBeforeDiscount = generaltransaction.getReceiptSubtotalAmount();
//        for (Iterator iterator = generaltransaction.getReceiptLineItems().iterator(); iterator.hasNext(); addOrUpdateItem(new SaleTransactionItem((SaleTransactionItem)iterator.next()), false)) { }
//        if (generaltransaction.getReceiptDiscountAmount().isGreaterThanZero())
//        {
//            setDiscountAmount(generaltransaction.getReceiptDiscountAmount());
//        }
//        _parentTransaction = generaltransaction;
//        _refundableAmount = generaltransaction.getRefundableAmount();
//        return recalculateCartTotals();
//    }

    private static SaleTransactionItem modifyItemQuantity(long l, int i, boolean flag)
    {
        boolean flag1 = _currentItems.containsKey(Long.valueOf(l));
        SaleTransactionItem saletransactionitem = null;
        if (flag1)
        {
            saletransactionitem = (SaleTransactionItem)_currentItems.get(Long.valueOf(l));
            saletransactionitem.setQuantity(i + ((SaleTransactionItem)_currentItems.get(Long.valueOf(l))).getQuantity());
            if (flag)
            {
                recalculateCartTotals();
            }
        }
        return saletransactionitem;
    }

    private static void onCartChanged()
    {
        if (_listeners != null)
        {
            for (Iterator iterator = _listeners.iterator(); iterator.hasNext(); ((OnCartChangedListener)iterator.next()).onCartChanged()) { }
        }
    }

    private static boolean recalculateCartTotals()
    {
        LinkedHashMap linkedhashmap = _currentItems;
//        linkedhashmap;
//        JVM INSTR monitorenter ;
        Iterator iterator;
        _totalItemCount = 0;
        _subtotal = new Money();
        _taxTotal = new Money();
        iterator = _currentItems.keySet().iterator();
//_L2:
        Money money6;
        do
        {
            SaleTransactionItem saletransactionitem;
            do
            {
                if (!iterator.hasNext())
                {
                    break; // MISSING_BLOCK_LABEL_168;
                }
                Long long1 = (Long)iterator.next();
                saletransactionitem = (SaleTransactionItem)_currentItems.get(long1);
                _totalItemCount += saletransactionitem.getQuantity();
                if (_subtotal != null && saletransactionitem.getTotal() != null)
                {
                    _subtotal.add(saletransactionitem.getTotal());
                }
            } while (true); //!saletransactionitem.getIsTaxable());// || SettingsApi.getTaxRate() == null);
//            money6 = Money.multiplyRoundUp(saletransactionitem.getTotal(), SettingsApi.getTaxRate());
        } while (false); // (_taxTotal == null || money6 == null);
//        _taxTotal.add(money6);
//        if (true) goto _L2; else goto _L1
//_L1:
//        Exception exception;
//        exception;
//        throw exception;
//        if (!SettingsApi.getDiscountEnabled())
//        {
//            break MISSING_BLOCK_LABEL_396;
//        }
//        if (_discountPercent == null) goto _L4; else goto _L3
//_L3:
//        _discountTotal = Money.multiplyRoundDown(_subtotal, _discountPercent.divide(new BigDecimal(100)));
//_L6:
        
      _discountTotal = new Money();
      _discountPercent = BigDecimal.ZERO;

        if (_subtotal != null && _taxTotal != null)
        {
            Money money5 = _subtotal;
            Money amoney3[] = new Money[1];
            amoney3[0] = _taxTotal;
            _totalBeforeDiscount = Money.add(money5, amoney3);
        }
        Money money = _totalBeforeDiscount;
        Money amoney[] = new Money[1];
        amoney[0] = _discountTotal;
        _total = Money.subtract(money, amoney);
//        if (_refundableAmount != null)
//        {
//            Money money3 = _subtotal;
//            Money amoney2[] = new Money[1];
//            amoney2[0] = _customRefundAmount;
//            Money money4 = Money.add(money3, amoney2);
//            _refundTotal = money4;
//            money4.add(_taxTotal);
//        }
//        if (_preauthCompleteAmount != null)
//        {
//            Money money1 = _subtotal;
//            Money amoney1[] = new Money[1];
//            amoney1[0] = _customPreauthAmount;
//            Money money2 = Money.add(money1, amoney1);
//            _preauthTotal = money2;
//            money2.add(_taxTotal);
//        }
        onCartChanged();
//        linkedhashmap;
//        JVM INSTR monitorexit ;
//        return true;
//_L4:
//        if (_discountTotal != null) goto _L6; else goto _L5
//_L5:
//        _discountTotal = new Money();
//        _discountPercent = BigDecimal.ZERO;
//          goto _L6
//        _discountTotal = new Money();
//        _discountPercent = BigDecimal.ZERO;
//          goto _L6
        
        return true;
    }

    public static boolean removeFromCart(SaleTransactionItem saletransactionitem)
    {
        synchronized (_currentItems)
        {
            _currentItems.remove(Long.valueOf(saletransactionitem.getPk()));
        }
        return recalculateCartTotals();
    }

    public static void removeOnCartChangedListener(OnCartChangedListener oncartchangedlistener)
    {
        _listeners.remove(oncartchangedlistener);
    }

    public static boolean resetCart()
    {
        _subtotal = null;
        _discountTotal = null;
        _discountPercent = null;
        _refundableAmount = null;
        _preauthCompleteAmount = null;
        _customRefundAmount = null;
        _refundTotal = null;
        _customPreauthAmount = null;
        _preauthTotal = null;
        _taxTotal = null;
        _total = null;
        _totalItemCount = 0;
//        _parentTransaction = null;
        _currentItems.clear();
//        _listeners.clear();
        return true;
    }

//    public static boolean setCustomPreAuthAmount(Money money)
//    {
//        while (_preauthCompleteAmount == null || money == null || money.isGreaterThan(_preauthCompleteAmount) || money.isLessThanZero()) 
//        {
//            return false;
//        }
//        _customPreauthAmount = money;
//        return recalculateCartTotals();
//    }
//
//    public static boolean setDiscountAmount(Money money)
//    {
//        if (money == null)
//        {
//            return true;
//        }
//        if (money.isGreaterThan(_totalBeforeDiscount) || money.isLessThanZero())
//        {
//            return false;
//        } else
//        {
//            _discountTotal = money;
//            _discountPercent = null;
//            return recalculateCartTotals();
//        }
//    }

//    public static boolean setDiscountPercentage(BigDecimal bigdecimal)
//    {
//        if (bigdecimal == null)
//        {
//            return true;
//        }
//        if (1 == bigdecimal.compareTo(BigDecimal.valueOf(100L)) || -1 == bigdecimal.compareTo(BigDecimal.ZERO))
//        {
//            return false;
//        } else
//        {
//            _discountPercent = bigdecimal;
//            _discountTotal = null;
//            return recalculateCartTotals();
//        }
//    }
//
    private static SaleTransactionItem setItemQuantity(long l, int i, boolean flag)
    {
        boolean flag1 = _currentItems.containsKey(Long.valueOf(l));
        SaleTransactionItem saletransactionitem = null;
        if (flag1)
        {
            saletransactionitem = (SaleTransactionItem)_currentItems.get(Long.valueOf(l));
            saletransactionitem.setQuantity(i);
            if (flag)
            {
                recalculateCartTotals();
            }
        }
        return saletransactionitem;
    }

//    public static void setQuantitiesToZero()
//    {
//        Long long1;
//        for (Iterator iterator = _currentItems.keySet().iterator(); iterator.hasNext(); ((SaleTransactionItem)_currentItems.get(long1)).setQuantity(0))
//        {
//            long1 = (Long)iterator.next();
//        }
//
//        recalculateCartTotals();
//    }
//
//    public static boolean setRefundAmount(Money money)
//    {
//        while (_refundableAmount == null || money == null || money.isGreaterThan(_refundableAmount) || money.isLessThanZero()) 
//        {
//            return false;
//        }
//        _customRefundAmount = money;
//        return recalculateCartTotals();
//    }
//
//    private static SaleTransactionItem updateItem(InventoryItem inventoryitem, int i)
//    {
//        LinkedHashMap linkedhashmap = _currentItems;
//        linkedhashmap;
//        JVM INSTR monitorenter ;
//        SaleTransactionItem saletransactionitem;
//        if (!_currentItems.containsKey(Long.valueOf(inventoryitem.getPk())))
//        {
//            break MISSING_BLOCK_LABEL_162;
//        }
//        saletransactionitem = (SaleTransactionItem)_currentItems.get(Long.valueOf(inventoryitem.getPk()));
//        saletransactionitem.setQuantity(i);
//        saletransactionitem.setName(inventoryitem.getName());
//        saletransactionitem.setDescription(inventoryitem.getDescription());
//        saletransactionitem.setReceiptMessage(inventoryitem.getReceiptMessage());
//        saletransactionitem.setPrice(inventoryitem.getPrice());
//        saletransactionitem.setIsTaxable(inventoryitem.getIsTaxable());
//        saletransactionitem.setImagePath(inventoryitem.getImagePath());
//        saletransactionitem.setCategory(inventoryitem.getCategory());
//        saletransactionitem.setTags(inventoryitem.getTags());
//        if (i != 0)
//        {
//            break MISSING_BLOCK_LABEL_138;
//        }
//        removeFromCart(saletransactionitem);
//_L1:
//        recalculateCartTotals();
//        linkedhashmap;
//        JVM INSTR monitorexit ;
//        return saletransactionitem;
//        _currentItems.put(Long.valueOf(inventoryitem.getPk()), saletransactionitem);
//          goto _L1
//        Exception exception;
//        exception;
//        throw exception;
//        linkedhashmap;
//        JVM INSTR monitorexit ;
//        return null;
//    }
//
//
//
//
//    private class _cls2
//    {
//
//        static final int $SwitchMap$com$nabancard$api$GeneralTransaction$eTransactionType[];
//
//        static 
//        {
//            $SwitchMap$com$nabancard$api$GeneralTransaction$eTransactionType = new int[GeneralTransaction.eTransactionType.values().length];
//            try
//            {
//                $SwitchMap$com$nabancard$api$GeneralTransaction$eTransactionType[GeneralTransaction.eTransactionType.CASH_SALE.ordinal()] = 1;
//            }
//            catch (NoSuchFieldError nosuchfielderror) { }
//            try
//            {
//                $SwitchMap$com$nabancard$api$GeneralTransaction$eTransactionType[GeneralTransaction.eTransactionType.CREDIT_CARD_SALE.ordinal()] = 2;
//            }
//            catch (NoSuchFieldError nosuchfielderror1)
//            {
//                return;
//            }
//        }
//    }
//
//
    public interface OnCartChangedListener
    {

        public abstract void onCartChanged();
    }

//
//    private class _cls1
//        implements InventoryApi.OnInventoryChangedListener
//    {
//
//        public final void onInventoryChanged()
//        {
//            if (CartApi._currentItems != null)
//            {
//                CartApi.onCartChanged();
//            }
//        }
//
//        _cls1()
//        {
//        }
//    }

}
