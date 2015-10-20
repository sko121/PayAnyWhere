package com.britesky.payanywhere.api;

import android.net.Uri;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

//Referenced classes of package com.nabancard.api:
//         Money, DbSaleItem, Category, DbMultimediaItem, 
//         ImageApi, Tag

public class InventoryItem {

    protected ArrayList _categories;
    private long _dateAdded;
    private String _description;
    private DbMultimediaItem _image;
    protected boolean _isActive;
    protected long _nab_pk;
    private String _name;
    private long _pk;
    protected Money _price;
    private String _receiptMessage;
    protected ArrayList _saleTransactionItems;
    protected ArrayList _tags;
    private boolean _taxable;

    InventoryItem(long l, long l1, String s, String s1, String s2, long l2,
            Money money, boolean flag, boolean flag1,
            DbMultimediaItem dbmultimediaitem, Category category,
            ArrayList arraylist) {
        _categories = new ArrayList();
        _tags = new ArrayList();
        _saleTransactionItems = new ArrayList();
        if (money.isLessThanZero()) {
            throw new IllegalArgumentException("Price cannot be less than zero");
        }
        _pk = l;
        _nab_pk = l1;
        _name = s;
        _description = s1;
        _receiptMessage = s2;
        _dateAdded = l2;
        _price = money;
        _taxable = flag;
        _isActive = flag1;
        _image = dbmultimediaitem;
        setCategory(category);
        if (arraylist == null) {
            arraylist = new ArrayList();
        }
        _tags = arraylist;
    }

    InventoryItem(DbSaleItem dbsaleitem) {
        this(dbsaleitem.getPk(), dbsaleitem.getNabPk(), dbsaleitem.getName(),
                dbsaleitem.getDescription(), dbsaleitem.getReceiptMessage(),
                dbsaleitem.getDateAdded(), new Money(dbsaleitem.getPrice()),
                dbsaleitem.getIsTaxable(), dbsaleitem.getIsActive(), dbsaleitem
                        .getImage(), null, null);
    }

    public InventoryItem(InventoryItem inventoryitem) {
//        long l = inventoryitem._pk;
//        long l1 = inventoryitem._nab_pk;
//        String s = inventoryitem._name;
//        String s1 = inventoryitem._description;
//        String s2 = inventoryitem._receiptMessage;
//        long l2 = inventoryitem._dateAdded;
//        Money money = new Money(inventoryitem._price.toBigDecimal());
//        boolean flag = inventoryitem._taxable;
//        boolean flag1 = inventoryitem._isActive;
//        DbMultimediaItem dbmultimediaitem = inventoryitem._image;
//        Category category = inventoryitem.getCategory();
//        ArrayList arraylist = new ArrayList(inventoryitem._tags);
        this(inventoryitem._pk, inventoryitem._nab_pk, inventoryitem._name,
                inventoryitem._description, inventoryitem._receiptMessage,
                inventoryitem._dateAdded, new Money(inventoryitem._price.toBigDecimal()),
                inventoryitem._taxable, inventoryitem._isActive, inventoryitem._image,
                inventoryitem.getCategory(), new ArrayList(inventoryitem._tags));
    }

    public boolean addTag(Tag tag) {
        if (tag == null) {
            return false;
        } else {
            return _tags.add(tag);
        }
    }

    public Category getCategory() {
        if (_categories.isEmpty()) {
            return null;
        } else {
            return (Category) _categories.get(0);
        }
    }

    public long getDateAdded() {
        return _dateAdded;
    }

    public String getDescription() {
        return _description;
    }

    public String getImagePath() {
        if (_image == null) {
            return null;
        } else {
            return _image.getFilePath();
        }
    }

    public Uri getImageUri() {
        if (getImagePath() == null) {
            return null;
        } else {
            return Uri.fromFile(new File(getImagePath()));
        }
    }

    public boolean getIsTaxable() {
        return _taxable;
    }

    public long getNabPk() {
        return _nab_pk;
    }

    public String getName() {
        return _name;
    }

    public long getPk() {
        return _pk;
    }

    public Money getPrice() {
        return _price;
    }

    public String getReceiptMessage() {
        return _receiptMessage;
    }

//    public List getSaleTransactionItems() {
//        return Collections.unmodifiableList(_saleTransactionItems);
//    }

    public ArrayList getTags() {
        return _tags;
    }

//    public boolean isEqualTo(InventoryItem inventoryitem) {
//        return _pk == inventoryitem._pk
//                && _nab_pk == inventoryitem._nab_pk
//                && _name.equals(inventoryitem._name)
//                && _description.equals(inventoryitem._description)
//                && _receiptMessage.equals(inventoryitem._receiptMessage)
//                && _dateAdded == inventoryitem._dateAdded
//                && _price.isEqualTo(inventoryitem._price)
//                && _taxable == inventoryitem._taxable
//                && _isActive == inventoryitem._isActive
//                && _image.equals(inventoryitem._image)
//                && ((Category) _categories.get(0)).getName().equals(
//                        inventoryitem.getCategory().getName())
//                && Arrays.deepEquals(_tags.toArray(),
//                        inventoryitem._tags.toArray());
//    }
//
//    public boolean removeTag(Tag tag) {
//        return _tags.remove(tag);
//    }

    public boolean setCategory(Category category) {
        _categories.clear();
        if (category == null) {
            return true;
        } else {
            return _categories.add(category);
        }
    }

    public void setDateAdded(long l) {
        _dateAdded = l;
    }

    public void setDescription(String s) {
        _description = s;
    }

    public void setImageNabPk(long l) {
        _nab_pk = l;
    }

    public void setImagePath(String s) {
        _image = ImageApi.getMultimediaItem(s);
    }

    public void setIsTaxable(boolean flag) {
        _taxable = flag;
    }

    public void setName(String s) {
        _name = s;
    }

    public void setPrice(Money money) {
        _price = money;
    }

    public void setReceiptMessage(String s) {
        _receiptMessage = s;
    }

    void setTags(ArrayList arraylist) {
        if (arraylist == null) {
            return;
        } else {
            _tags = arraylist;
            return;
        }
    }
}
