package com.britesky.payanywhere.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//Referenced classes of package com.nabancard.api:
//         DbSaleItemCategory, InventoryItem

public class Category {

    private boolean _is_active;
    private final ArrayList _items;
    private long _nab_pk;
    private String _name;
    private long _pk;

    Category(long l, long l1, String s) {
        _items = new ArrayList();
        _pk = l;
        _nab_pk = l1;
        _name = s;
    }

    Category(DbSaleItemCategory dbsaleitemcategory) {
        this(dbsaleitemcategory.getPk(), dbsaleitemcategory.getNabPk(),
                dbsaleitemcategory.getName());
    }

    Category(String s) {
        this(-1L, -1L, s);
    }

    public boolean addItem(InventoryItem inventoryitem) {
        if (itemExists(inventoryitem)) {
            return true;
        } else {
            return _items.add(inventoryitem);
        }
    }

    public List getItems() {
        return Collections.unmodifiableList(_items);
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

    public boolean isActive() {
        return _is_active;
    }

    boolean itemExists(InventoryItem inventoryitem) {
        return _items.contains(inventoryitem);
    }

    public boolean removeItem(InventoryItem inventoryitem) {
        return _items.remove(inventoryitem);
    }

    public void setIsActive(boolean flag) {
        _is_active = flag;
    }

    public void setNabPk(long l) {
        _nab_pk = l;
    }

    public void setName(String s) {
        _name = s;
    }

    public void setPk(long l) {
        _pk = l;
    }
}
