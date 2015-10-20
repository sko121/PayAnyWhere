package com.britesky.payanywhere.api;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


// Referenced classes of package com.nabancard.api:
//            GeneralApi, DbSaleItemCategory, DbSaleItem, DbMerchantAccount

@DatabaseTable(tableName = "sale_item_category_relations")
final class DbSaleItemCategoryRelation extends CoreDbHelper.HasPk
{

    public static final String FIELD_CATEGORY = "_category_id";
    public static final String FIELD_ITEM = "_item_id";
    @DatabaseField(foreign = true)
    private DbSaleItemCategory _category;
    @DatabaseField(foreign = true)
    private DbSaleItem _item;
    @DatabaseField(foreign = true, columnName = "_merchant_reference_id")
    private DbMerchantAccount _merchant_reference;
    @DatabaseField
    private long _nab_pk;

    DbSaleItemCategoryRelation()
    {
    }

    DbSaleItemCategoryRelation(DbSaleItem dbsaleitem, DbSaleItemCategory dbsaleitemcategory)
    {
        _item = dbsaleitem;
        _category = dbsaleitemcategory;
        _merchant_reference = GeneralApi.getCurrentMerchant();
    }

    final DbSaleItemCategory getCategory()
    {
        return _category;
    }

    final DbSaleItem getItem()
    {
        return _item;
    }

    final long getNabPk()
    {
        return _nab_pk;
    }

    final void setCategory(DbSaleItemCategory dbsaleitemcategory)
    {
        _category = dbsaleitemcategory;
    }

    final void setItem(DbSaleItem dbsaleitem)
    {
        _item = dbsaleitem;
    }
}
