// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.britesky.payanywhere.api;

import java.util.ArrayList;

// Referenced classes of package com.nabancard.api:
//            InventoryItem, DbSaleItem, InventoryApi, Money, 
//            DbSaleTransactionReceiptLineItem, DbTemporaryCartItem

public class SaleTransactionItem extends InventoryItem
{

    private Money _discountAmount;
    private Money _discountRate;
    private Money _extendedTotal;
    private InventoryItem _originalItem;
    private int _quantity;
    private Money _taxAmount;
    private Money _taxRate;

    SaleTransactionItem(DbSaleItem dbsaleitem, int i, ArrayList arraylist, ArrayList arraylist1)
    {
        super(dbsaleitem);
        _originalItem = InventoryApi.getItem(dbsaleitem.getPk());
        _categories = arraylist;
        _tags = arraylist1;
        _discountAmount = new Money();
        setQuantity(i);
    }

//    SaleTransactionItem(DbSaleTransactionReceiptLineItem dbsaletransactionreceiptlineitem)
//    {
//        super(dbsaletransactionreceiptlineitem.getSaleItem().getPk(), dbsaletransactionreceiptlineitem.getNabPk(), dbsaletransactionreceiptlineitem.getName(), dbsaletransactionreceiptlineitem.getDescription(), dbsaletransactionreceiptlineitem.getReceiptMessage(), dbsaletransactionreceiptlineitem.getSaleItem().getDateAdded(), new Money(dbsaletransactionreceiptlineitem.getUnitPrice()), dbsaletransactionreceiptlineitem.getIsTaxable(), false, dbsaletransactionreceiptlineitem.getImage(), null, null);
//        _originalItem = InventoryApi.getItem(dbsaletransactionreceiptlineitem.getSaleItem().getPk());
//        _quantity = dbsaletransactionreceiptlineitem.getQuantity();
//        _discountAmount = new Money(dbsaletransactionreceiptlineitem.getDiscountAmount());
//        _discountRate = new Money(dbsaletransactionreceiptlineitem.getDiscountRate());
//        _taxAmount = new Money(dbsaletransactionreceiptlineitem.getTaxAmount());
//        _taxRate = new Money(dbsaletransactionreceiptlineitem.getTaxRate());
//        _extendedTotal = new Money(dbsaletransactionreceiptlineitem.getExtendedPrice());
//    }
//
//    SaleTransactionItem(DbTemporaryCartItem dbtemporarycartitem)
//    {
//        super(dbtemporarycartitem.getSaleItem());
//        _originalItem = InventoryApi.getItem(dbtemporarycartitem.getSaleItem().getPk());
//        _quantity = dbtemporarycartitem.getQuantity();
//        _discountAmount = new Money(dbtemporarycartitem.getDiscountAmount());
//        _extendedTotal = new Money(dbtemporarycartitem.getExtendedPrice());
//    }

    SaleTransactionItem(InventoryItem inventoryitem, int i)
    {
        super(inventoryitem);
        _originalItem = inventoryitem;
        _discountAmount = new Money();
        setQuantity(i);
    }

    SaleTransactionItem(SaleTransactionItem saletransactionitem)
    {
        super(saletransactionitem);
        _originalItem = saletransactionitem._originalItem;
        _quantity = saletransactionitem._quantity;
        _discountAmount = saletransactionitem._discountAmount;
        _discountRate = saletransactionitem._discountRate;
        _taxAmount = saletransactionitem._taxAmount;
        _taxRate = saletransactionitem._taxRate;
        _extendedTotal = saletransactionitem._extendedTotal;
    }

    public Money getDiscountAmount()
    {
        return _discountAmount;
    }

    public Money getDiscountRate()
    {
        return _discountRate;
    }

    public InventoryItem getInventoryItem()
    {
        return _originalItem;
    }

    public int getQuantity()
    {
        return _quantity;
    }

    public Money getTaxAmount()
    {
        return _taxAmount;
    }

    public Money getTaxRate()
    {
        return _taxRate;
    }

    public Money getTotal()
    {
        return _extendedTotal;
    }

    public boolean isManualItem()
    {
        return !_isActive;
    }

    public void setAddToInventory()
    {
        _isActive = true;
    }

    public void setPrice(Money money)
    {
        super.setPrice(money);
        _extendedTotal = Money.multiply(_price, _quantity);
    }

    void setQuantity(int i)
    {
        _quantity = i;
        _extendedTotal = Money.multiply(_price, _quantity);
    }

    public void setTaxAmount(Money money)
    {
        _taxAmount = money;
    }

    public void setTaxRate(Money money)
    {
        _taxRate = money;
    }
}
