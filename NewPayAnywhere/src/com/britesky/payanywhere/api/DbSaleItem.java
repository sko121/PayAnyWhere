package com.britesky.payanywhere.api;

import java.math.BigDecimal;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

// Referenced classes of package com.nabancard.api:
//            InventoryItem, Money, GeneralApi, ImageApi, 
//            DbMultimediaItem, DbMerchantAccount

@DatabaseTable(tableName = "sale_items")
final class DbSaleItem extends CoreDbHelper.HasPk
{

    static final String FIELD_ACTIVE = "_is_active";
    static final String FIELD_DATE_ADDED = "_date_added";
    static final String FIELD_DESCRIPTION = "_description";
    static final String FIELD_LIST_PRICE_AMOUNT = "_list_price_amount";
    static final String FIELD_NAME = "_name";
    static final String FIELD_RECEIPT_MESSAGE = "_receipt_message";
    static final String FIELD_TAXABLE = "_is_taxable";
    static final String FOREIGN_FIELD_IMAGE = "_image_id";
    @DatabaseField
    private long _date_added;
    @DatabaseField
    private String _description;
    @DatabaseField(foreign = true, columnName = "_image_id", foreignAutoRefresh = true)
    private DbMultimediaItem _image;
    @DatabaseField
    private boolean _is_active;
    @DatabaseField
    private boolean _is_taxable;
    @DatabaseField
    private BigDecimal _list_price_amount;
    @DatabaseField(foreign = true, columnName = "_merchant_reference_id") 
    private DbMerchantAccount _merchant_reference;
    @DatabaseField
    private long _nab_pk;
    @DatabaseField
    private String _name;
    @DatabaseField
    private String _receipt_message;

    DbSaleItem()
    {
        _is_active = true;
    }

    DbSaleItem(InventoryItem inventoryitem)
    {
        this(inventoryitem.getName(), inventoryitem.getDescription(), inventoryitem.getReceiptMessage(), inventoryitem.getPrice().toBigDecimal(), inventoryitem.getIsTaxable(), inventoryitem.getImagePath(), true);
        _date_added = inventoryitem.getDateAdded();
        _nab_pk = inventoryitem.getNabPk();
    }

    DbSaleItem(String s, String s1, String s2, BigDecimal bigdecimal, boolean flag, String s3, boolean flag1)
    {
        _is_active = true;
        _date_added = System.currentTimeMillis();
        _merchant_reference = GeneralApi.getCurrentMerchant();
        _name = s;
        _description = s1;
        _receipt_message = s2;
        _list_price_amount = bigdecimal;
        _is_taxable = flag;
        _image = ImageApi.getMultimediaItem(s3);
        _is_active = flag1;
    }

    final long getDateAdded()
    {
        return _date_added;
    }

    final String getDescription()
    {
        return _description;
    }

    final DbMultimediaItem getImage()
    {
        return _image;
    }

    final boolean getIsActive()
    {
        return _is_active;
    }

    final boolean getIsTaxable()
    {
        return _is_taxable;
    }

    final long getNabPk()
    {
        return _nab_pk;
    }

    final String getName()
    {
        return _name;
    }

    final BigDecimal getPrice()
    {
        return _list_price_amount;
    }

    final String getReceiptMessage()
    {
        return _receipt_message;
    }

    public final void setDateAdded(long l)
    {
        _date_added = l;
    }

    final void setDescription(String s)
    {
        _description = s;
    }

    final void setImage(DbMultimediaItem dbmultimediaitem)
    {
        _image = dbmultimediaitem;
    }

    final void setIsActive(boolean flag)
    {
        _is_active = flag;
    }

    final void setIsTaxable(boolean flag)
    {
        _is_taxable = flag;
    }

    final void setNabPk(long l)
    {
        _nab_pk = l;
    }

    final void setName(String s)
    {
        _name = s;
    }

    final void setPrice(BigDecimal bigdecimal)
    {
        _list_price_amount = bigdecimal;
    }

    final void setReceiptMessage(String s)
    {
        _receipt_message = s;
    }

    final DbSaleItem update(InventoryItem inventoryitem)
    {
        _nab_pk = inventoryitem._nab_pk;
        _name = inventoryitem.getName();
        _description = inventoryitem.getDescription();
        _receipt_message = inventoryitem.getReceiptMessage();
        _list_price_amount = inventoryitem.getPrice().toBigDecimal();
        _is_taxable = inventoryitem.getIsTaxable();
        _is_active = inventoryitem._isActive;
        _image = ImageApi.getMultimediaItem(inventoryitem.getImagePath());
        return this;
    }
}
