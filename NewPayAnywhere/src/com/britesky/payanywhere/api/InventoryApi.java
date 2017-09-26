package com.britesky.payanywhere.api;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.misc.TransactionManager;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;
import com.j256.ormlite.stmt.Where;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//Referenced classes of package com.nabancard.api:
//         InventoryItem, GeneralApi, CoreDbHelper, Category, 
//         DbSaleItemCategory, DbSaleItem, Tag, DbSaleItemTag, 
//         Rule, Money, CategoriesHelper, SaleTransactionItem, 
//         Filter, DbMerchantAccount, TagsHelper, DbSaleItemTagRelation, 
//         DbSaleItemCategoryRelation, ImageApi, HttpHelper2, Vault, 
//         DbUserAccount

public class InventoryApi
{

 private static final ArrayList _categories = new ArrayList();
 private static final ArrayList _items = new ArrayList();
 private static final ArrayList _listeners = new ArrayList();
 private static final ArrayList _tags = new ArrayList();
 private static boolean hasBeenInitialized = false;
//
// public InventoryApi()
// {
// }

 public static void addOnInventoryChangedListener(OnInventoryChangedListener oninventorychangedlistener)
 {
     if (!_listeners.contains(oninventorychangedlistener))
     {
         _listeners.add(oninventorychangedlistener);
     }
 }

// private static void addTagsAndCategories(List list, List list1, List list2, boolean flag)
// {
//     Iterator iterator = _items.iterator();
//     do
//     {
//         if (!iterator.hasNext())
//         {
//             break;
//         }
//         InventoryItem inventoryitem = (InventoryItem)iterator.next();
//         if (list1.size() > 0)
//         {
//             if (itemHasATag(inventoryitem, list1))
//             {
//                 if (!flag && !list.contains(inventoryitem))
//                 {
//                     list.add(inventoryitem);
//                 }
//             } else
//             if (flag && list.contains(inventoryitem))
//             {
//                 list.remove(inventoryitem);
//             }
//         }
//         if (list2.size() > 0)
//         {
//             if (itemHasACategory(inventoryitem, list2))
//             {
//                 if (!flag && !list.contains(inventoryitem))
//                 {
//                     list.add(inventoryitem);
//                 }
//             } else
//             if (flag && list.contains(inventoryitem))
//             {
//                 list.remove(inventoryitem);
//             }
//         }
//     } while (true);
// }
//
// public static void bulkSyncCategories(JSONObject jsonobject)
// {
//     final JSONArray globalCategories;
//     globalCategories = jsonobject.getJSONArray("categories");
//     if (globalCategories.length() == 0)
//     {
//         hasBeenInitialized = true;
//         return;
//     }
//     try
//     {
//         Log.d("TIMETIMETIME", (new StringBuilder("globalCategories size = ")).append(globalCategories.length()).toString());
//         hasBeenInitialized = false;
//         CoreDbHelper coredbhelper = GeneralApi.getDbHelper();
//         final Dao categoryDao = coredbhelper.getSaleItemCategoryDao();
//         TransactionManager.callInTransaction(coredbhelper.getConnectionSource(), new _cls4());
//         return;
//     }
//     catch (Exception exception)
//     {
//         exception.printStackTrace();
//     }
//     return;
// }
//
// public static void bulkSyncSalesItems(JSONObject jsonobject, Context context)
// {
//     final JSONArray salesItems;
//     salesItems = jsonobject.getJSONArray("sales_items");
//     if (salesItems.length() == 0)
//     {
//         hasBeenInitialized = true;
//         return;
//     }
//     try
//     {
//         hasBeenInitialized = false;
//         Log.d("TIMETIMETIME", (new StringBuilder("salesItems size = ")).append(salesItems.length()).toString());
//         File file = new File((new StringBuilder()).append(Environment.getExternalStorageDirectory()).append("/nab").toString());
//         if (!file.exists())
//         {
//             file.mkdir();
//             (new File(file, ".nomedia")).createNewFile();
//         }
//         final CoreDbHelper helper = GeneralApi.getDbHelper();
//         TransactionManager.callInTransaction(helper.getConnectionSource(), new _cls5());
//         return;
//     }
//     catch (Exception exception)
//     {
//         exception.printStackTrace();
//     }
//     return;
// }
//
// public static void bulkSyncTags(JSONObject jsonobject)
// {
//     final JSONArray globalTags;
//     globalTags = jsonobject.getJSONArray("tags");
//     if (globalTags.length() == 0)
//     {
//         hasBeenInitialized = true;
//         return;
//     }
//     try
//     {
//         Log.d("TIMETIMETIME", (new StringBuilder("globalTags size = ")).append(globalTags.length()).toString());
//         hasBeenInitialized = false;
//         CoreDbHelper coredbhelper = GeneralApi.getDbHelper();
//         final Dao tagDao = coredbhelper.getSaleItemTagDao();
//         TransactionManager.callInTransaction(coredbhelper.getConnectionSource(), new _cls3());
//         return;
//     }
//     catch (Exception exception)
//     {
//         exception.printStackTrace();
//     }
//     return;
// }
//
// public static void clearOnInventoryChangedListeners()
// {
//     _listeners.clear();
// }
//
// public static boolean commit(Category category)
// {
//     try
//     {
//         Dao dao = GeneralApi.getDbHelper().getSaleItemCategoryDao();
//         dao.update(((DbSaleItemCategory)dao.queryForId(Long.valueOf(category.getPk()))).update(category));
//     }
//     catch (SQLException sqlexception)
//     {
//         sqlexception.printStackTrace();
//         return false;
//     }
//     return true;
// }

 public static boolean commit(InventoryItem inventoryitem)
 {
     DbSaleItem dbsaleitem;
     try
     {
         Dao dao = GeneralApi.getDbHelper().getSaleItemDao();
         dbsaleitem = ((DbSaleItem)dao.queryForId(Long.valueOf(inventoryitem.getPk()))).update(inventoryitem);
         dao.update(dbsaleitem);
     }
     catch (SQLException sqlexception)
     {
         sqlexception.printStackTrace();
         return false;
     }
     if (!reconcileCategory(inventoryitem, dbsaleitem))
     {
         return false;
     }
     if (!reconcileTags(inventoryitem, dbsaleitem))
     {
         return false;
     } else
     {
         onInventoryChanged();
         return true;
     }
 }

// public static boolean commit(Tag tag)
// {
//     try
//     {
//         Dao dao = GeneralApi.getDbHelper().getSaleItemTagDao();
//         dao.update(((DbSaleItemTag)dao.queryForId(Long.valueOf(tag.getPk()))).update(tag));
//     }
//     catch (SQLException sqlexception)
//     {
//         sqlexception.printStackTrace();
//         return false;
//     }
//     return true;
// }
//
// public static void createDbCategory(Category category, boolean flag)
// {
//     DbSaleItemCategory dbsaleitemcategory = new DbSaleItemCategory(category.getName());
//     dbsaleitemcategory.setIsActive(true);
//     try
//     {
//         GeneralApi.getDbHelper().getSaleItemCategoryDao().create(dbsaleitemcategory);
//     }
//     catch (SQLException sqlexception)
//     {
//         sqlexception.printStackTrace();
//     }
//     category.setPk(dbsaleitemcategory.getPk());
//     commit(category);
//     if (flag)
//     {
//         _categories.add(category);
//         onInventoryChanged();
//     }
// }

 private static boolean createDbSaleItem(DbSaleItem dbsaleitem, Category category, ArrayList arraylist)
 {
     try
     {
         GeneralApi.getDbHelper().getSaleItemDao().create(dbsaleitem);
     }
     catch (SQLException sqlexception)
     {
         sqlexception.printStackTrace();
         return false;
     }
     return true;
 }

// public static void createDbTag(Tag tag, boolean flag)
// {
//     DbSaleItemTag dbsaleitemtag = new DbSaleItemTag(tag.getName());
//     try
//     {
//         GeneralApi.getDbHelper().getSaleItemTagDao().create(dbsaleitemtag);
//     }
//     catch (SQLException sqlexception)
//     {
//         sqlexception.printStackTrace();
//     }
//     tag.setPk(dbsaleitemtag.getPk());
//     commit(tag);
//     if (flag)
//     {
//         _tags.add(tag);
//         onInventoryChanged();
//     }
// }
//
// public static boolean deleteDbCategory(Category category)
// {
//     _categories.remove(category);
//     for (Iterator iterator = category.getItems().iterator(); iterator.hasNext(); ((InventoryItem)iterator.next()).setCategory(null)) { }
//     try
//     {
//         Dao dao = GeneralApi.getDbHelper().getSaleItemCategoryDao();
//         DbSaleItemCategory dbsaleitemcategory = (DbSaleItemCategory)dao.queryForId(Long.valueOf(category.getPk()));
//         dbsaleitemcategory.setIsActive(false);
//         dao.update(dbsaleitemcategory);
//         DeleteBuilder deletebuilder = GeneralApi.getDbHelper().getSaleItemCategoryRelationDao().deleteBuilder();
//         deletebuilder.where().eq("_category_id", Long.valueOf(category.getPk()));
//         deletebuilder.delete();
//     }
//     catch (SQLException sqlexception)
//     {
//         sqlexception.printStackTrace();
//         return false;
//     }
//     onInventoryChanged();
//     return true;
// }
//
// public static boolean deleteDbTag(Tag tag)
// {
//     _tags.remove(tag);
//     for (Iterator iterator = tag.getItems().iterator(); iterator.hasNext(); ((InventoryItem)iterator.next()).removeTag(tag)) { }
//     try
//     {
//         Dao dao = GeneralApi.getDbHelper().getSaleItemTagDao();
//         DbSaleItemTag dbsaleitemtag = (DbSaleItemTag)dao.queryForId(Long.valueOf(tag.getPk()));
//         dbsaleitemtag.setIsActive(false);
//         dao.update(dbsaleitemtag);
//         DeleteBuilder deletebuilder = GeneralApi.getDbHelper().getSaleItemTagRelationDao().deleteBuilder();
//         deletebuilder.where().eq("_tag_id", Long.valueOf(tag.getPk()));
//         deletebuilder.delete();
//     }
//     catch (SQLException sqlexception)
//     {
//         sqlexception.printStackTrace();
//         return false;
//     }
//     onInventoryChanged();
//     return true;
// }
//
// public static boolean deleteItem(InventoryItem inventoryitem)
// {
//     _items.remove(inventoryitem);
//     if (inventoryitem.getCategory() != null)
//     {
//         inventoryitem.getCategory().removeItem(inventoryitem);
//     }
//     Iterator iterator = inventoryitem.getTags().iterator();
//     do
//     {
//         if (!iterator.hasNext())
//         {
//             break;
//         }
//         Tag tag = (Tag)iterator.next();
//         tag.removeItem(inventoryitem);
//         if (tag.getItems().isEmpty())
//         {
//             deleteDbTag(tag);
//         }
//     } while (true);
//     try
//     {
//         Dao dao = GeneralApi.getDbHelper().getSaleItemDao();
//         DbSaleItem dbsaleitem = (DbSaleItem)dao.queryForId(Long.valueOf(inventoryitem.getPk()));
//         dbsaleitem.setIsActive(false);
//         dao.update(dbsaleitem);
//     }
//     catch (SQLException sqlexception)
//     {
//         sqlexception.printStackTrace();
//         return false;
//     }
//     onInventoryChanged();
//     return true;
// }
//
// private static void filterPriceAmount(List list, Rule rule, boolean flag)
// {
//     Rule.eOperator eoperator = rule.getOperator();
//     if (eoperator == Rule.eOperator.LESS_THAN)
//     {
//         Iterator iterator5 = _items.iterator();
//         do
//         {
//             if (!iterator5.hasNext())
//             {
//                 break;
//             }
//             InventoryItem inventoryitem5 = (InventoryItem)iterator5.next();
//             if (inventoryitem5.getPrice() != null && inventoryitem5.getPrice().isLessThan(new Money(rule.getValue())))
//             {
//                 if (!flag && !list.contains(inventoryitem5))
//                 {
//                     list.add(inventoryitem5);
//                 }
//             } else
//             if (flag)
//             {
//                 list.remove(inventoryitem5);
//             }
//         } while (true);
//     } else
//     if (eoperator == Rule.eOperator.LESS_THAN_OR_EQUAL)
//     {
//         Iterator iterator4 = _items.iterator();
//         do
//         {
//             if (!iterator4.hasNext())
//             {
//                 break;
//             }
//             InventoryItem inventoryitem4 = (InventoryItem)iterator4.next();
//             if (inventoryitem4.getPrice() != null && (inventoryitem4.getPrice().isLessThan(new Money(rule.getValue())) || inventoryitem4.getPrice().isEqualTo(new Money(rule.getValue()))))
//             {
//                 if (!flag && !list.contains(inventoryitem4))
//                 {
//                     list.add(inventoryitem4);
//                 }
//             } else
//             if (flag)
//             {
//                 list.remove(inventoryitem4);
//             }
//         } while (true);
//     } else
//     if (eoperator == Rule.eOperator.GREATER_THAN)
//     {
//         Iterator iterator3 = _items.iterator();
//         do
//         {
//             if (!iterator3.hasNext())
//             {
//                 break;
//             }
//             InventoryItem inventoryitem3 = (InventoryItem)iterator3.next();
//             if (inventoryitem3.getPrice() != null && inventoryitem3.getPrice().isGreaterThan(new Money(rule.getValue())))
//             {
//                 if (!flag && !list.contains(inventoryitem3))
//                 {
//                     list.add(inventoryitem3);
//                 }
//             } else
//             if (flag)
//             {
//                 list.remove(inventoryitem3);
//             }
//         } while (true);
//     } else
//     if (eoperator == Rule.eOperator.GREATER_THAN_OR_EQUAL)
//     {
//         Iterator iterator2 = _items.iterator();
//         do
//         {
//             if (!iterator2.hasNext())
//             {
//                 break;
//             }
//             InventoryItem inventoryitem2 = (InventoryItem)iterator2.next();
//             if (inventoryitem2.getPrice() != null && (inventoryitem2.getPrice().isGreaterThan(new Money(rule.getValue())) || inventoryitem2.getPrice().isEqualTo(new Money(rule.getValue()))))
//             {
//                 if (!flag && !list.contains(inventoryitem2))
//                 {
//                     list.add(inventoryitem2);
//                 }
//             } else
//             if (flag)
//             {
//                 list.remove(inventoryitem2);
//             }
//         } while (true);
//     } else
//     if (eoperator == Rule.eOperator.EQUAL)
//     {
//         Iterator iterator1 = _items.iterator();
//         do
//         {
//             if (!iterator1.hasNext())
//             {
//                 break;
//             }
//             InventoryItem inventoryitem1 = (InventoryItem)iterator1.next();
//             if (inventoryitem1.getPrice() != null && inventoryitem1.getPrice().isEqualTo(new Money(rule.getValue())))
//             {
//                 if (!flag && !list.contains(inventoryitem1))
//                 {
//                     list.add(inventoryitem1);
//                 }
//             } else
//             if (flag)
//             {
//                 list.remove(inventoryitem1);
//             }
//         } while (true);
//     } else
//     if (eoperator == Rule.eOperator.DOES_NOT_EQUAL)
//     {
//         Iterator iterator = _items.iterator();
//         do
//         {
//             if (!iterator.hasNext())
//             {
//                 break;
//             }
//             InventoryItem inventoryitem = (InventoryItem)iterator.next();
//             if (inventoryitem.getPrice() != null && !inventoryitem.getPrice().isEqualTo(new Money(rule.getValue())))
//             {
//                 if (!flag && !list.contains(inventoryitem))
//                 {
//                     list.add(inventoryitem);
//                 }
//             } else
//             if (flag)
//             {
//                 list.remove(inventoryitem);
//             }
//         } while (true);
//     }
// }
//
// public static List getAllCategoriesFromDb()
// {
//     return CategoriesHelper.getAllCategories();
// }

 public static List getCategories()
 {
     return Collections.unmodifiableList(_categories);
 }

// public static Cursor getCategoriesCursor()
// {
//     Cursor cursor;
//     try
//     {
//         cursor = CategoriesHelper.getCursorOrderedByName();
//     }
//     catch (SQLException sqlexception)
//     {
//         sqlexception.printStackTrace();
//         return null;
//     }
//     return cursor;
// }
//
// static String getCategoriesString(SaleTransactionItem saletransactionitem)
// {
//     InventoryItem inventoryitem = getItem(saletransactionitem.getPk());
//     if (inventoryitem != null)
//     {
//         if (inventoryitem.getCategory() != null)
//         {
//             return inventoryitem.getCategory().getName();
//         } else
//         {
//             return "";
//         }
//     } else
//     {
//         return "";
//     }
// }
//
// public static Category getCategory(long l)
// {
//     for (Iterator iterator = _categories.iterator(); iterator.hasNext();)
//     {
//         Category category = (Category)iterator.next();
//         if (category.getNabPk() == l)
//         {
//             return category;
//         }
//     }
//
//     return null;
// }
//
// public static Category getCategory(String s)
// {
//     for (Iterator iterator = _categories.iterator(); iterator.hasNext();)
//     {
//         Category category = (Category)iterator.next();
//         if (category.getName().equalsIgnoreCase(s))
//         {
//             return category;
//         }
//     }
//
//     return null;
// }
//
// public static Category getCategoryById(long l)
// {
//     return CategoriesHelper.getCategoryById(l);
// }
//
 static DbSaleItem getItem(SaleTransactionItem saletransactionitem)
 {
     DbSaleItem dbsaleitem;
     try
     {
         dbsaleitem = (DbSaleItem)GeneralApi.getDbHelper().getSaleItemDao().queryForId(Long.valueOf(saletransactionitem.getPk()));
     }
     catch (SQLException sqlexception)
     {
         sqlexception.printStackTrace();
         return null;
     }
     return dbsaleitem;
 }

 static InventoryItem getItem(long l)
 {
     for (Iterator iterator = _items.iterator(); iterator.hasNext();)
     {
         InventoryItem inventoryitem = (InventoryItem)iterator.next();
         if (inventoryitem.getPk() == l)
         {
             return inventoryitem;
         }
     }

     return null;
 }

 public static List getItems(/* Filter filter */)
 {
     QueryBuilder querybuilder;
     Where where;
     QueryBuilder querybuilder1;
     Where where1;
     QueryBuilder querybuilder2;
     Where where2;
     int i;
     int j;
     int k;
//     Rule rule1;
//     Rule.eOperator eoperator;
//     long l1;
//     long l2;
//     if (filter == null || filter.getRules() == null || filter.getRules().isEmpty())
//     {
         return Collections.unmodifiableList(_items);
//     }
 }
//     Iterator iterator;
//     try
//     {
//         querybuilder = GeneralApi.getDbHelper().getSaleItemDao().queryBuilder();
//         where = querybuilder.where();
//         querybuilder1 = GeneralApi.getDbHelper().getSaleItemTagDao().queryBuilder();
//         where1 = querybuilder1.where();
//         querybuilder2 = GeneralApi.getDbHelper().getSaleItemCategoryDao().queryBuilder();
//         where2 = querybuilder2.where();
//         iterator = filter.getRules().iterator();
//     }
//     catch (SQLException sqlexception)
//     {
//         sqlexception.printStackTrace();
//         return null;
//     }
//     i = 0;
//     j = 0;
//     k = 0;
//_L71:
//     if (!iterator.hasNext()) goto _L2; else goto _L1
//_L1:
//     rule1 = (Rule)iterator.next();
//     eoperator = rule1.getOperator();
//     _cls6..SwitchMap.com.nabancard.api.Rule.eAttribute[rule1.getAttribute().ordinal()];
//     JVM INSTR tableswitch 1 8: default 2130
// //                   1 192
// //                   2 312
// //                   3 540
// //                   4 768
// //                   5 793
// //                   6 1020
// //                   7 1254
// //                   8 1486;
//        goto _L3 _L4 _L5 _L6 _L7 _L8 _L9 _L10 _L11
//_L4:
//     j++;
//     l1 = Long.parseLong(rule1.getValue());
//     l2 = (0x5265c00L + l1) - 1L;
//     _cls6..SwitchMap.com.nabancard.api.Rule.eOperator[eoperator.ordinal()];
//     JVM INSTR tableswitch 1 3: default 2141
// //                   1 252
// //                   2 280
// //                   3 296;
//        goto _L12 _L13 _L14 _L15
//_L12:
//     if (false)
//     {
//     }
//     continue; /* Loop/switch isn't completed */
//_L13:
//     where.between("_date_added", Long.valueOf(l1), Long.valueOf(l2));
//     continue; /* Loop/switch isn't completed */
//_L14:
//     where.ge("_date_added", Long.valueOf(l1));
//     continue; /* Loop/switch isn't completed */
//_L15:
//     where.le("_date_added", Long.valueOf(l2));
//     continue; /* Loop/switch isn't completed */
//_L5:
//     j++;
//     String s4 = rule1.getValue().replaceAll("'", "''");
//     _cls6..SwitchMap.com.nabancard.api.Rule.eOperator[eoperator.ordinal()];
//     JVM INSTR tableswitch 4 9: default 2144
// //                   4 380
// //                   5 412
// //                   6 441
// //                   7 476
// //                   8 514
// //                   9 527;
//        goto _L16 _L17 _L18 _L19 _L20 _L21 _L22
//_L16:
//     if (false)
//     {
//     }
//     continue; /* Loop/switch isn't completed */
//_L17:
//     where.like("_name", (new StringBuilder()).append(s4).append("%").toString());
//     continue; /* Loop/switch isn't completed */
//_L18:
//     where.like("_name", (new StringBuilder("%")).append(s4).toString());
//     continue; /* Loop/switch isn't completed */
//_L19:
//     where.like("_name", (new StringBuilder("%")).append(s4).append("%").toString());
//     continue; /* Loop/switch isn't completed */
//_L20:
//     where.not().like("_name", (new StringBuilder("%")).append(s4).append("%").toString());
//     continue; /* Loop/switch isn't completed */
//_L21:
//     where.eq("_name", s4);
//     continue; /* Loop/switch isn't completed */
//_L22:
//     where.ne("_name", s4);
//     continue; /* Loop/switch isn't completed */
//_L6:
//     j++;
//     String s3 = rule1.getValue().replaceAll("'", "''");
//     _cls6..SwitchMap.com.nabancard.api.Rule.eOperator[eoperator.ordinal()];
//     JVM INSTR tableswitch 4 9: default 2147
// //                   4 608
// //                   5 640
// //                   6 669
// //                   7 704
// //                   8 742
// //                   9 755;
//        goto _L23 _L24 _L25 _L26 _L27 _L28 _L29
//_L23:
//     if (false)
//     {
//     }
//     continue; /* Loop/switch isn't completed */
//_L24:
//     where.like("_description", (new StringBuilder()).append(s3).append("%").toString());
//     continue; /* Loop/switch isn't completed */
//_L25:
//     where.like("_description", (new StringBuilder("%")).append(s3).toString());
//     continue; /* Loop/switch isn't completed */
//_L26:
//     where.like("_description", (new StringBuilder("%")).append(s3).append("%").toString());
//     continue; /* Loop/switch isn't completed */
//_L27:
//     where.not().like("_description", (new StringBuilder("%")).append(s3).append("%").toString());
//     continue; /* Loop/switch isn't completed */
//_L28:
//     where.eq("_description", s3);
//     continue; /* Loop/switch isn't completed */
//_L29:
//     where.ne("_description", s3);
//     continue; /* Loop/switch isn't completed */
//_L7:
//     j++;
//     where.eq("_is_taxable", Boolean.valueOf(Boolean.parseBoolean(rule1.getValue())));
//     continue; /* Loop/switch isn't completed */
//_L8:
//     j++;
//     String s2 = rule1.getValue().replaceAll("'", "''");
//     _cls6..SwitchMap.com.nabancard.api.Rule.eOperator[eoperator.ordinal()];
//     JVM INSTR tableswitch 4 9: default 2150
// //                   4 860
// //                   5 892
// //                   6 921
// //                   7 956
// //                   8 994
// //                   9 1007;
//        goto _L30 _L31 _L32 _L33 _L34 _L35 _L36
//_L30:
//     if (false)
//     {
//     }
//     continue; /* Loop/switch isn't completed */
//_L31:
//     where.like("_receipt_message", (new StringBuilder()).append(s2).append("%").toString());
//     continue; /* Loop/switch isn't completed */
//_L32:
//     where.like("_receipt_message", (new StringBuilder("%")).append(s2).toString());
//     continue; /* Loop/switch isn't completed */
//_L33:
//     where.like("_receipt_message", (new StringBuilder("%")).append(s2).append("%").toString());
//     continue; /* Loop/switch isn't completed */
//_L34:
//     where.not().like("_receipt_message", (new StringBuilder("%")).append(s2).append("%").toString());
//     continue; /* Loop/switch isn't completed */
//_L35:
//     where.eq("_receipt_message", s2);
//     continue; /* Loop/switch isn't completed */
//_L36:
//     where.ne("_receipt_message", s2);
//     continue; /* Loop/switch isn't completed */
//_L9:
//     k++;
//     String s1 = rule1.getValue().replaceAll("'", "''");
//     _cls6..SwitchMap.com.nabancard.api.Rule.eOperator[eoperator.ordinal()];
//     JVM INSTR tableswitch 4 9: default 2153
// //                   4 1088
// //                   5 1121
// //                   6 1151
// //                   7 1187
// //                   8 1226
// //                   9 1240;
//        goto _L37 _L38 _L39 _L40 _L41 _L42 _L43
//_L37:
//     if (false)
//     {
//     }
//     continue; /* Loop/switch isn't completed */
//_L38:
//     where2.like("_name", (new StringBuilder()).append(s1).append("%").toString());
//     continue; /* Loop/switch isn't completed */
//_L39:
//     where2.like("_name", (new StringBuilder("%")).append(s1).toString());
//     continue; /* Loop/switch isn't completed */
//_L40:
//     where2.like("_name", (new StringBuilder("%")).append(s1).append("%").toString());
//     continue; /* Loop/switch isn't completed */
//_L41:
//     where2.not().like("_name", (new StringBuilder("%")).append(s1).append("%").toString());
//     continue; /* Loop/switch isn't completed */
//_L42:
//     where2.eq("_name", s1);
//     continue; /* Loop/switch isn't completed */
//_L43:
//     where2.ne("_name", s1);
//     continue; /* Loop/switch isn't completed */
//_L10:
//     i++;
//     String s = rule1.getValue().replaceAll("'", "''");
//     _cls6..SwitchMap.com.nabancard.api.Rule.eOperator[eoperator.ordinal()];
//     JVM INSTR tableswitch 4 9: default 2156
// //                   4 1320
// //                   5 1353
// //                   6 1383
// //                   7 1419
// //                   8 1458
// //                   9 1472;
//        goto _L44 _L45 _L46 _L47 _L48 _L49 _L50
//_L44:
//     if (false)
//     {
//     }
//     continue; /* Loop/switch isn't completed */
//_L45:
//     where1.like("_name", (new StringBuilder()).append(s).append("%").toString());
//     continue; /* Loop/switch isn't completed */
//_L46:
//     where1.like("_name", (new StringBuilder("%")).append(s).toString());
//     continue; /* Loop/switch isn't completed */
//_L47:
//     where1.like("_name", (new StringBuilder("%")).append(s).append("%").toString());
//     continue; /* Loop/switch isn't completed */
//_L48:
//     where1.not().like("_name", (new StringBuilder("%")).append(s).append("%").toString());
//     continue; /* Loop/switch isn't completed */
//_L49:
//     where1.eq("_name", s);
//     continue; /* Loop/switch isn't completed */
//_L50:
//     where1.ne("_name", s);
//     continue; /* Loop/switch isn't completed */
//_L11:
//     int i1 = j + 1;
//     _cls6..SwitchMap.com.nabancard.api.Rule.eOperator[eoperator.ordinal()];
//     JVM INSTR tableswitch 10 11: default 2159
// //                   10 1524
// //                   11 1539;
//        goto _L51 _L52 _L53
//_L51:
//     break MISSING_BLOCK_LABEL_2134;
//_L52:
//     where.isNotNull("_image_id");
//     j = i1;
//     continue; /* Loop/switch isn't completed */
//_L53:
//     where.isNull("_image_id");
//     break MISSING_BLOCK_LABEL_2134;
//_L2:
//     long l;
//     ArrayList arraylist;
//     ArrayList arraylist1;
//     l = GeneralApi.getCurrentMerchant().getPk();
//     arraylist = new ArrayList();
//     arraylist1 = new ArrayList();
//     if (!filter.getMatchAll()) goto _L55; else goto _L54
//_L54:
//     if (j <= 0) goto _L57; else goto _L56
//_L56:
//     where.and(j);
//     where.and().eq("_merchant_reference_id", Long.valueOf(l));
//_L57:
//     if (i <= 0) goto _L59; else goto _L58
//_L58:
//     where1.and(i);
//     for (Iterator iterator1 = querybuilder1.query().iterator(); iterator1.hasNext(); arraylist.add(getTag(((DbSaleItemTag)iterator1.next()).getName()))) { }
//_L59:
//     if (k <= 0)
//     {
//         break MISSING_BLOCK_LABEL_1887;
//     }
//     where2.and(k);
//     for (Iterator iterator2 = querybuilder2.query().iterator(); iterator2.hasNext(); arraylist1.add(getCategory(((DbSaleItemCategory)iterator2.next()).getName()))) { }
//     break MISSING_BLOCK_LABEL_1887;
//_L55:
//     if (j <= 0) goto _L61; else goto _L60
//_L60:
//     where.or(j);
//     where.and().eq("_merchant_reference_id", Long.valueOf(l));
//_L61:
//     if (i <= 0) goto _L63; else goto _L62
//_L62:
//     where1.or(i);
//     for (Iterator iterator5 = querybuilder1.query().iterator(); iterator5.hasNext(); arraylist.add(getTag(((DbSaleItemTag)iterator5.next()).getPk()))) { }
//_L63:
//     if (k <= 0)
//     {
//         break MISSING_BLOCK_LABEL_1887;
//     }
//     where2.or(k);
//     for (Iterator iterator6 = querybuilder2.query().iterator(); iterator6.hasNext(); arraylist1.add(getCategory(((DbSaleItemCategory)iterator6.next()).getPk()))) { }
//     ArrayList arraylist2 = new ArrayList();
//     if (j <= 0)
//     {
//         break MISSING_BLOCK_LABEL_1949;
//     }
//     for (Iterator iterator3 = querybuilder.query().iterator(); iterator3.hasNext(); arraylist2.add(getItem(((DbSaleItem)iterator3.next()).getPk()))) { }
//     boolean flag = filter.getMatchAll();
//     if (j != 0 || !flag) goto _L65; else goto _L64
//_L64:
//     arraylist2.addAll(_items);
//_L65:
//     Iterator iterator4;
//     while (arraylist2.remove(null)) ;
//     while (arraylist1.remove(null)) ;
//     while (arraylist.remove(null)) ;
//     if (arraylist.size() > 0 || arraylist1.size() > 0)
//     {
//         addTagsAndCategories(arraylist2, arraylist, arraylist1, filter.getMatchAll());
//     }
//     iterator4 = filter.getRules().iterator();
//_L69:
//     if (!iterator4.hasNext()) goto _L67; else goto _L66
//_L66:
//     Rule rule = (Rule)iterator4.next();
//     Rule.eAttribute eattribute = rule.getAttribute();
//     switch (_cls6..SwitchMap.com.nabancard.api.Rule.eAttribute[eattribute.ordinal()])
//     {
//     case 9: // '\t'
//         filterPriceAmount(arraylist2, rule, flag);
//         break;
//     }
//     if (true) goto _L69; else goto _L68
//_L68:
//_L67:
//     List list = Collections.unmodifiableList(arraylist2);
//     return list;
//_L3:
//     i1 = j;
//     j = i1;
//     if (true) goto _L71; else goto _L70
//_L70:
// }
//
// public static Tag getTag(long l)
// {
//     for (Iterator iterator = _tags.iterator(); iterator.hasNext();)
//     {
//         Tag tag = (Tag)iterator.next();
//         if (tag.getNabPk() == l)
//         {
//             return tag;
//         }
//     }
//
//     return null;
// }
//
// public static Tag getTag(String s)
// {
//     for (Iterator iterator = _tags.iterator(); iterator.hasNext();)
//     {
//         Tag tag = (Tag)iterator.next();
//         if (tag.getName().equalsIgnoreCase(s))
//         {
//             return tag;
//         }
//     }
//
//     return null;
// }
//
// public static Tag getTagById(long l)
// {
//     return TagsHelper.getTagById(l);
// }
//
// public static List getTags()
// {
//     return Collections.unmodifiableList(_tags);
// }
//
// public static Cursor getTagsCursor()
// {
//     Cursor cursor;
//     try
//     {
//         cursor = TagsHelper.getCursorOrderedByName();
//     }
//     catch (SQLException sqlexception)
//     {
//         sqlexception.printStackTrace();
//         return null;
//     }
//     return cursor;
// }
//
// static String getTagsString(SaleTransactionItem saletransactionitem)
// {
//     StringBuilder stringbuilder = new StringBuilder();
//     InventoryItem inventoryitem = getItem(saletransactionitem.getPk());
//     if (inventoryitem != null)
//     {
//         ArrayList arraylist = inventoryitem.getTags();
//         if (arraylist != null && !arraylist.isEmpty())
//         {
//             Iterator iterator = arraylist.iterator();
//             do
//             {
//                 if (!iterator.hasNext())
//                 {
//                     break;
//                 }
//                 Tag tag = (Tag)iterator.next();
//                 if (tag != null && tag.getName() != null)
//                 {
//                     stringbuilder.append(tag.getName()).append(';');
//                 }
//             } while (true);
//             if (stringbuilder.length() > 0)
//             {
//                 stringbuilder.deleteCharAt(-1 + stringbuilder.length());
//             }
//         }
//     }
//     return stringbuilder.toString();
// }

 public static void init()
 {
     Log.d("NAB", "InventoryApi: Attempting init");
     Log.d("TIMETIMETIME", (new StringBuilder("Inventory hasBeenInitialized = ")).append(hasBeenInitialized).toString());
     if (!hasBeenInitialized)
     {
         reInit();
     }
 }

// private static boolean itemHasACategory(InventoryItem inventoryitem, List list)
// {
//     return list.contains(inventoryitem.getCategory());
// }
//
// private static boolean itemHasATag(InventoryItem inventoryitem, List list)
// {
//     for (Iterator iterator = inventoryitem.getTags().iterator(); iterator.hasNext();)
//     {
//         if (list.contains((Tag)iterator.next()))
//         {
//             return true;
//         }
//     }
//
//     return false;
// }
//
// public static Category newCategory(String s)
// {
//     return newCategory(s, true);
// }
//
// public static Category newCategory(String s, boolean flag)
// {
//     Category category = new Category(s);
//     (new CreateNewCategoryTask(category, true)).execute(new Void[0]);
//     return category;
// }
//
// public static Category newCategoryInstance(String s)
// {
//     return new Category(s);
// }

 public static InventoryItem newInventoryItem(String s, String s1, String s2, Money money, boolean flag, String s3, Category category, ArrayList arraylist)
 {
     DbSaleItem dbsaleitem = new DbSaleItem(s, s1, s2, money.toBigDecimal(), flag, s3, true);
     if (!createDbSaleItem(dbsaleitem, category, arraylist))
     {
         return null;
     } else
     {
         InventoryItem inventoryitem = new InventoryItem(dbsaleitem);
         inventoryitem.setCategory(category);
         reconcileCategory(inventoryitem, dbsaleitem);
         inventoryitem.setTags(arraylist);
         reconcileTags(inventoryitem, dbsaleitem);
         _items.add(inventoryitem);
         onInventoryChanged();
         return inventoryitem;
     }
 }

// public static SaleTransactionItem newManualItem(String s, String s1, String s2, Money money, boolean flag, String s3, Category category, ArrayList arraylist, 
//         boolean flag1, int i)
// {
//     if (i <= 0)
//     {
//         throw new IllegalArgumentException("Quantity must be more than zero");
//     }
//     DbSaleItem dbsaleitem = new DbSaleItem(s, s1, s2, money.toBigDecimal(), flag, s3, flag1);
//     if (!createDbSaleItem(dbsaleitem, category, arraylist))
//     {
//         return null;
//     }
//     if (flag1)
//     {
//         InventoryItem inventoryitem = new InventoryItem(dbsaleitem);
//         inventoryitem.setCategory(category);
//         inventoryitem.setTags(arraylist);
//         _items.add(inventoryitem);
//         onInventoryChanged();
//     }
//     ArrayList arraylist1 = new ArrayList();
//     if (category != null)
//     {
//         arraylist1.add(category);
//     }
//     return new SaleTransactionItem(dbsaleitem, i, arraylist1, arraylist);
// }
//
// public static Tag newTag(String s)
// {
//     return newTag(s, true);
// }
//
// public static Tag newTag(String s, boolean flag)
// {
//     Tag tag = new Tag(s);
//     (new CreateNewTagTask(tag, true)).execute(new Void[0]);
//     return tag;
// }
//
// public static Tag newTagInstance(String s)
// {
//     return new Tag(s);
// }
//
 private static void onInventoryChanged()
 {
     if (_listeners != null)
     {
         for (int i = 0; i < _listeners.size(); i++)
         {
             ((OnInventoryChangedListener)_listeners.get(i)).onInventoryChanged();
         }

     }
 }

 public static void populateCategories()
 {
     _categories.clear();
     final CoreDbHelper helper = GeneralApi.getDbHelper();
     try
     {
         TransactionManager.callInTransaction(helper.getConnectionSource(), new Callable() {
             @Override
             public final Void call() throws SQLException
             {
                 DbSaleItemCategory dbsaleitemcategory;
                 for (Iterator iterator = helper.getSaleItemCategoryDao()
                                             .queryBuilder()
                                             .orderByRaw("_name COLLATE NOCASE")
                                             .where().eq("_merchant_reference_id", GeneralApi.getCurrentMerchant())
                                             .and().eq("_is_active", Boolean.valueOf(true))
                                             .query().iterator();
                         iterator.hasNext();
                         InventoryApi._categories.add(new Category(dbsaleitemcategory)))
                 {
                     dbsaleitemcategory = (DbSaleItemCategory)iterator.next();
                 }

                 for (Iterator iterator1 = helper.getSaleItemCategoryRelationDao().queryForAll().iterator();
                         iterator1.hasNext();)
                 {
                     DbSaleItemCategoryRelation dbsaleitemcategoryrelation = (DbSaleItemCategoryRelation)iterator1.next();
                     Iterator iterator2 = InventoryApi._categories.iterator();
                     while (iterator2.hasNext()) 
                     {
                         Category category = (Category)iterator2.next();
                         if (dbsaleitemcategoryrelation.getCategory().getPk() == category.getPk())
                         {
                             Iterator iterator3 = InventoryApi._items.iterator();
                             while (iterator3.hasNext()) 
                             {
                                 InventoryItem inventoryitem = (InventoryItem)iterator3.next();
                                 if (dbsaleitemcategoryrelation.getItem().getPk() == inventoryitem.getPk())
                                 {
                                     category.addItem(inventoryitem);
                                     inventoryitem.setCategory(category);
                                 }
                             }
                         }
                     }
                 }
                 return null;
             }
         });
     }
     catch (SQLException sqlexception)
     {
         sqlexception.printStackTrace();
     }
     onInventoryChanged();
     Log.d("TIMETIMETIME", "-----END PopulateInventory-----");
 }

 private static void populateInventory()
 {
     Log.d("TIMETIMETIME", "-----START PopulateInventory-----");
     _items.clear();
     _tags.clear();
     final CoreDbHelper helper = GeneralApi.getDbHelper();
     try
     {
         TransactionManager.callInTransaction(helper.getConnectionSource(), new Callable() {
             @Override
             public final Void call() throws SQLException
             {
                 Dao dao = helper.getSaleItemDao();
                 Dao dao1 = helper.getSaleItemTagDao();
                 DbSaleItem dbsaleitem;
                 for (Iterator iterator = dao.queryBuilder()
                                             .orderByRaw("_name COLLATE NOCASE")
                                             .where().eq("_merchant_reference_id", GeneralApi.getCurrentMerchant())
                                                 .and().eq("_is_active", Boolean.valueOf(true))
                                             .query().iterator();
                         iterator.hasNext();
                         InventoryApi._items.add(new InventoryItem(dbsaleitem)))
                 {
                     dbsaleitem = (DbSaleItem)iterator.next();
                 }

                 InventoryApi.populateCategories();
                 DbSaleItemTag dbsaleitemtag;
                 for (Iterator iterator1 = dao1.queryBuilder()
                                               .orderByRaw("_name COLLATE NOCASE")
                                               .where().eq("_user_reference_id", GeneralApi.getCurrentUser())
                                                   .and().eq("_is_active", Boolean.valueOf(true))
                                                   .query().iterator(); 
                         iterator1.hasNext(); 
                         InventoryApi._tags.add(new Tag(dbsaleitemtag)))
                 {
                     dbsaleitemtag = (DbSaleItemTag)iterator1.next();
                 }

                 return null;
             }
         });
         for (Iterator iterator = helper.getSaleItemTagRelationDao().queryForAll().iterator(); iterator.hasNext();)
         {
             DbSaleItemTagRelation dbsaleitemtagrelation = (DbSaleItemTagRelation)iterator.next();
             Iterator iterator1 = _tags.iterator();
             while (iterator1.hasNext()) 
             {
                 Tag tag = (Tag)iterator1.next();
                 if (dbsaleitemtagrelation.getTag() != null && dbsaleitemtagrelation.getTag().getPk() == tag.getPk())
                 {
                     Iterator iterator2 = _items.iterator();
                     while (iterator2.hasNext()) 
                     {
                         InventoryItem inventoryitem = (InventoryItem)iterator2.next();
                         if (dbsaleitemtagrelation.getItem().getPk() == inventoryitem.getPk())
                         {
                             tag.addItem(inventoryitem);
                             inventoryitem.addTag(tag);
                         }
                     }
                 }
             }
         }

     }
     catch (SQLException sqlexception)
     {
         sqlexception.printStackTrace();
     }
     onInventoryChanged();
     Log.d("TIMETIMETIME", "-----END PopulateInventory-----");
 }

 public static void reInit()
 {
     populateInventory();
     hasBeenInitialized = true;
 }

 private static boolean reconcileCategory(InventoryItem inventoryitem, DbSaleItem dbsaleitem)
 {
     if (inventoryitem.getCategory() == null)
     {
         Iterator iterator1 = _categories.iterator();
         do
         {
             if (!iterator1.hasNext())
             {
                 break;
             }
             Category category1 = (Category)iterator1.next();
             if (category1 != null && inventoryitem != null)
             {
                 category1.removeItem(inventoryitem);
             }
         } while (true);
     } else
     {
         Iterator iterator = _categories.iterator();
         do
         {
             if (!iterator.hasNext())
             {
                 break;
             }
             Category category = (Category)iterator.next();
             if (category != null)
             {
                 if (category.getPk() == inventoryitem.getCategory().getPk())
                 {
                     category.addItem(inventoryitem);
                 } else
                 {
                     category.removeItem(inventoryitem);
                 }
             }
         } while (true);
     }
     if (dbsaleitem == null)
     {
         return true;
     }
     try
     {
         Dao dao = GeneralApi.getDbHelper().getSaleItemCategoryRelationDao();
         DeleteBuilder deletebuilder = dao.deleteBuilder();
         deletebuilder.where().eq("_item_id", Long.valueOf(inventoryitem.getPk()));
         deletebuilder.delete();
         if (inventoryitem.getCategory() != null)
         {
             dao.create(new DbSaleItemCategoryRelation(dbsaleitem, (DbSaleItemCategory)GeneralApi.getDbHelper().getSaleItemCategoryDao().queryForEq("_pk", Long.valueOf(inventoryitem.getCategory().getPk())).get(0)));
         }
     }
     catch (SQLException sqlexception)
     {
         sqlexception.printStackTrace();
         return false;
     }
     return true;
 }

 private static boolean reconcileTags(InventoryItem inventoryitem, DbSaleItem dbsaleitem)
 {
     ArrayList arraylist = new ArrayList();
     int i = -1 + _tags.size();
     while (i >= 0) 
     {
         Tag tag1 = (Tag)_tags.get(i);
         if (tag1.getItems().contains(inventoryitem) && !inventoryitem.getTags().contains(tag1))
         {
             tag1.removeItem(inventoryitem);
             if (tag1.getItems().isEmpty())
             {
                 arraylist.add(Long.valueOf(tag1.getPk()));
             }
         } else
         if (inventoryitem.getTags().contains(tag1) && !tag1.getItems().contains(inventoryitem))
         {
             tag1.addItem(inventoryitem);
         }
         i--;
     }
     if (dbsaleitem == null)
     {
         return true;
     }
     Dao dao;
     Iterator iterator;
     dao = GeneralApi.getDbHelper().getSaleItemTagRelationDao();
     DeleteBuilder deletebuilder = dao.deleteBuilder();
     try {
        deletebuilder.where().eq("_item_id", Long.valueOf(inventoryitem.getPk()));
        deletebuilder.delete();
     } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
     }
     iterator = inventoryitem.getTags().iterator();

     while (iterator.hasNext())
     {
         Tag tag = (Tag)iterator.next();
         try
         {
             List list = GeneralApi.getDbHelper().getSaleItemTagDao().queryForEq("_pk", Long.valueOf(tag.getPk()));
             if (list.size() > 0)
             {
                 dao.create(new DbSaleItemTagRelation(dbsaleitem, (DbSaleItemTag)list.get(0)));
             }
         }
         catch (SQLException sqlexception)
         {
             sqlexception.printStackTrace();
             return false;
         }
     }

     return true;
 }

// public static void removeOnInventoryChangedListener(OnInventoryChangedListener oninventorychangedlistener)
// {
//     if (!_listeners.contains(oninventorychangedlistener))
//     {
//         _listeners.remove(oninventorychangedlistener);
//     }
// }
//
// public static void reset()
// {
//     _listeners.clear();
//     _items.clear();
//     _categories.clear();
//     _tags.clear();
//     hasBeenInitialized = false;
// }
//
// static boolean syncInventoryItem(String s, String s1, String s2, Money money, boolean flag, String s3, Category category, ArrayList arraylist, 
//         boolean flag1, long l)
// {
//     DbSaleItem dbsaleitem;
//     try
//     {
//         Dao dao = GeneralApi.getDbHelper().getSaleItemDao();
//         dbsaleitem = (DbSaleItem)dao.queryForFirst(dao.queryBuilder().where().eq("_nab_pk", Long.valueOf(l)).prepare());
//     }
//     catch (Exception exception)
//     {
//         exception.printStackTrace();
//         return false;
//     }
//     if (dbsaleitem != null) goto _L2; else goto _L1
//_L1:
//     dbsaleitem = new DbSaleItem(s, s1, s2, money.toBigDecimal(), flag, s3, flag1);
//     dbsaleitem.setNabPk(l);
//_L4:
//     if (!createDbSaleItem(dbsaleitem, category, arraylist))
//     {
//         return false;
//     }
//     break; /* Loop/switch isn't completed */
//_L2:
//     dbsaleitem.setName(s);
//     dbsaleitem.setDescription(s1);
//     dbsaleitem.setReceiptMessage(s2);
//     dbsaleitem.setPrice(money.toBigDecimal());
//     dbsaleitem.setIsTaxable(flag);
//     dbsaleitem.setImage(ImageApi.getMultimediaItem(s3));
//     dbsaleitem.setIsActive(flag1);
//     if (true) goto _L4; else goto _L3
//_L3:
//     if (!flag1)
//     {
//         break MISSING_BLOCK_LABEL_289;
//     }
//     InventoryItem inventoryitem = new InventoryItem(dbsaleitem);
//     inventoryitem.setCategory(category);
//     reconcileCategory(inventoryitem, null);
//     inventoryitem.setTags(arraylist);
//     reconcileTags(inventoryitem, null);
//     _items.add(inventoryitem);
//     if (!_categories.contains(category))
//     {
//         _categories.add(category);
//     }
//     if (arraylist == null)
//     {
//         break MISSING_BLOCK_LABEL_286;
//     }
//     Iterator iterator = arraylist.iterator();
//     do
//     {
//         if (!iterator.hasNext())
//         {
//             break;
//         }
//         Tag tag = (Tag)iterator.next();
//         if (!_tags.contains(tag))
//         {
//             _tags.add(tag);
//         }
//     } while (true);
//     onInventoryChanged();
//     return true;
// }
//
// public static eInventoryResponse uploadCategory(Category category)
// {
//     if (category == null)
//     {
//         break MISSING_BLOCK_LABEL_11;
//     }
//     if (category.getName() != null)
//     {
//         break MISSING_BLOCK_LABEL_15;
//     }
//     return eInventoryResponse.Error;
//     HttpHelper2 httphelper2;
//     JSONObject jsonobject;
//     httphelper2 = new HttpHelper2();
//     jsonobject = new JSONObject();
//     jsonobject.put("name", category.getName());
//     if (category == null) goto _L2; else goto _L1
//_L1:
//     if (category.getNabPk() <= 0L) goto _L2; else goto _L3
//_L3:
//     String s = httphelper2.send((new StringBuilder()).append(Vault.getStoreFrontUri()).append(Vault.getUpdateCategoryUrl().replace("{id}", Long.toString(GeneralApi.getCurrentMerchant().getNabPk())).replace("{categoryId}", Long.toString(category.getNabPk()))).toString(), HttpHelper2.Verb.Put, jsonobject.toString(), GeneralApi.getCurrentUser().getUserName(), GeneralApi.getCurrentUser().getLoginKey(), GeneralApi.isTestDrive());
//_L5:
//     JSONObject jsonobject1 = new JSONObject(s);
//     if (jsonobject1.has("id"))
//     {
//         long l = jsonobject1.getLong("id");
//         if (category.getNabPk() <= 0L)
//         {
//             category.setNabPk(l);
//         }
//         category.setIsActive(true);
//         return eInventoryResponse.OK;
//     }
//     break; /* Loop/switch isn't completed */
//_L2:
//     s = httphelper2.send((new StringBuilder()).append(Vault.getStoreFrontUri()).append(Vault.getCreateCategoryUrl().replace("{id}", Long.toString(GeneralApi.getCurrentMerchant().getNabPk()))).toString(), HttpHelper2.Verb.Post, jsonobject.toString(), GeneralApi.getCurrentUser().getUserName(), GeneralApi.getCurrentUser().getLoginKey(), GeneralApi.isTestDrive());
//     if (true) goto _L5; else goto _L4
//_L4:
//     eInventoryResponse einventoryresponse;
//     try
//     {
//         einventoryresponse = eInventoryResponse.Error;
//     }
//     catch (JSONException jsonexception)
//     {
//         jsonexception.printStackTrace();
//         return eInventoryResponse.Error;
//     }
//     return einventoryresponse;
// }
//
// public static eInventoryResponse uploadDeleteCategory(Category category)
// {
//     eInventoryResponse einventoryresponse = eInventoryResponse.OK;
//     try
//     {
//         (new HttpHelper2()).send((new StringBuilder()).append(Vault.getStoreFrontUri()).append(Vault.getUpdateCategoryUrl().replace("{id}", Long.toString(GeneralApi.getCurrentMerchant().getNabPk())).replace("{categoryId}", Long.toString(category.getNabPk()))).toString(), HttpHelper2.Verb.Delete, null, GeneralApi.getCurrentUser().getUserName(), GeneralApi.getCurrentUser().getLoginKey(), GeneralApi.isTestDrive());
//     }
//     catch (Exception exception)
//     {
//         exception.printStackTrace();
//         return eInventoryResponse.createNotFoundException;
//     }
//     return einventoryresponse;
// }
//
// public static eInventoryResponse uploadDeleteTag(Tag tag)
// {
//     eInventoryResponse einventoryresponse = eInventoryResponse.OK;
//     try
//     {
//         (new HttpHelper2()).send((new StringBuilder()).append(Vault.getStoreFrontUri()).append(Vault.getUpdateTagUrl().replace("{id}", Long.toString(tag.getNabPk()))).toString(), HttpHelper2.Verb.Delete, null, GeneralApi.getCurrentUser().getUserName(), GeneralApi.getCurrentUser().getLoginKey(), GeneralApi.isTestDrive());
//     }
//     catch (Exception exception)
//     {
//         exception.printStackTrace();
//         return eInventoryResponse.createNotFoundException;
//     }
//     return einventoryresponse;
// }
//
// public static eInventoryResponse uploadInventoryItem(InventoryItem inventoryitem)
// {
//     return uploadInventoryItem(inventoryitem, inventoryitem._isActive, false);
// }
//
// public static eInventoryResponse uploadInventoryItem(InventoryItem inventoryitem, boolean flag, boolean flag1)
// {
//     eInventoryResponse einventoryresponse;
//     HttpHelper2 httphelper2;
//     JSONObject jsonobject;
//     JSONArray jsonarray1;
//     ArrayList arraylist;
//     einventoryresponse = eInventoryResponse.Error;
//     if (flag1)
//     {
//         flag = false;
//     }
//     Category category;
//     JSONArray jsonarray;
//     JSONObject jsonobject2;
//     Iterator iterator;
//     Tag tag;
//     JSONObject jsonobject4;
//     try
//     {
//         httphelper2 = new HttpHelper2();
//         jsonobject = new JSONObject();
//         JSONObject jsonobject1 = new JSONObject();
//         if (inventoryitem._nab_pk > 0L)
//         {
//             jsonobject1.put("id", inventoryitem._nab_pk);
//         }
//         jsonobject1.put("is_active", flag);
//         jsonobject1.put("is_taxable", inventoryitem.getIsTaxable());
//         if (!TextUtils.isEmpty(inventoryitem.getDescription()))
//         {
//             jsonobject1.put("description", inventoryitem.getDescription());
//         }
//         if (!TextUtils.isEmpty(inventoryitem.getName()))
//         {
//             jsonobject1.put("name", inventoryitem.getName());
//         }
//         jsonobject1.put("price", inventoryitem.getPrice().toPlainString());
//         if (!TextUtils.isEmpty(inventoryitem.getReceiptMessage()))
//         {
//             jsonobject1.put("receipt_message", inventoryitem.getReceiptMessage());
//         }
//         jsonobject1.put("is_one_time_use", flag1);
//         jsonobject.put("sales_item", jsonobject1);
//         category = inventoryitem.getCategory();
//     }
//     catch (Exception exception)
//     {
//         exception.printStackTrace();
//         return eInventoryResponse.Error;
//     }
//     if (category == null)
//     {
//         break MISSING_BLOCK_LABEL_285;
//     }
//     if (category.getNabPk() <= 0L)
//     {
//         if (uploadCategory(category) != eInventoryResponse.OK)
//         {
//             break MISSING_BLOCK_LABEL_421;
//         }
//         createDbCategory(category, true);
//         inventoryitem.setCategory(category);
//     }
//     jsonarray = new JSONArray();
//     jsonobject2 = new JSONObject();
//     jsonobject2.put("id", inventoryitem.getCategory().getNabPk());
//     jsonarray.put(jsonobject2);
//     jsonobject.put("categories", jsonarray);
//     if (inventoryitem.getTags() == null)
//     {
//         break MISSING_BLOCK_LABEL_473;
//     }
//     jsonarray1 = new JSONArray();
//     arraylist = new ArrayList();
//     iterator = inventoryitem.getTags().iterator();
//     do
//     {
//         if (!iterator.hasNext())
//         {
//             break MISSING_BLOCK_LABEL_449;
//         }
//         tag = (Tag)iterator.next();
//         if (tag.getNabPk() <= 0L)
//         {
//             if (uploadTag(tag) != eInventoryResponse.OK)
//             {
//                 break;
//             }
//             createDbTag(tag, true);
//         }
//         arraylist.add(tag);
//         jsonobject4 = new JSONObject();
//         jsonobject4.put("id", tag.getNabPk());
//         jsonarray1.put(jsonobject4);
//     } while (true);
//     break MISSING_BLOCK_LABEL_435;
//     Log.e("Uploading", "category error");
//     return eInventoryResponse.Error;
//     Log.e("Uploading", "tag error");
//     return eInventoryResponse.Error;
//     if (jsonarray1.length() > 0)
//     {
//         jsonobject.put("tags", jsonarray1);
//     }
//     inventoryitem.setTags(arraylist);
//     if (inventoryitem.getImagePath() == null) goto _L2; else goto _L1
//_L1:
//     if (!inventoryitem.getImagePath().contains("nab://")) goto _L4; else goto _L3
//_L3:
//     String s2 = (new StringBuilder()).append(GeneralApi.getCacheDir().getPath()).append(File.separator).append(inventoryitem.getImagePath().hashCode()).toString();
//_L16:
//     Bitmap bitmap = ImageApi.scaleBitmapFromFile(s2);
//     if (bitmap == null) goto _L2; else goto _L5
//_L5:
//     ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
//     bitmap.compress(android.graphics.Bitmap.CompressFormat.PNG, 100, bytearrayoutputstream);
//     jsonobject.put("image_data", Base64.encodeToString(bytearrayoutputstream.toByteArray(), 2));
//_L2:
//     if (inventoryitem._nab_pk <= 0L) goto _L7; else goto _L6
//_L6:
//     String s = httphelper2.send((new StringBuilder()).append(Vault.getStoreFrontUri()).append(Vault.getUpdateItemUrl().replace("{id}", Long.toString(GeneralApi.getCurrentMerchant().getNabPk())).replace("{salesItemId}", Long.toString(inventoryitem._nab_pk))).toString(), HttpHelper2.Verb.Put, jsonobject.toString(), GeneralApi.getCurrentUser().getUserName(), GeneralApi.getCurrentUser().getLoginKey(), GeneralApi.isTestDrive());
//_L13:
//     if (!s.startsWith("{")) goto _L9; else goto _L8
//_L8:
//     JSONObject jsonobject3 = new JSONObject(s);
//     if (!jsonobject3.has("error_type")) goto _L11; else goto _L10
//_L10:
//     String s1;
//     s1 = jsonobject3.getString("error_type");
//     if (s1.equals("createNotFoundException"))
//     {
//         return eInventoryResponse.createNotFoundException;
//     }
//       goto _L12
//_L4:
//     s2 = inventoryitem.getImagePath().replace("file://", "");
//     continue; /* Loop/switch isn't completed */
//_L7:
//     s = httphelper2.send((new StringBuilder()).append(Vault.getStoreFrontUri()).append(Vault.getCreateItemUrl().replace("{id}", Long.toString(GeneralApi.getCurrentMerchant().getNabPk()))).toString(), HttpHelper2.Verb.Post, jsonobject.toString(), GeneralApi.getCurrentUser().getUserName(), GeneralApi.getCurrentUser().getLoginKey(), GeneralApi.isTestDrive());
//       goto _L13
//_L12:
//     if (s1.equals("Validation"))
//     {
//         return eInventoryResponse.Validation;
//     }
//       goto _L14
//_L11:
//     if (jsonobject3.has("id"))
//     {
//         inventoryitem._nab_pk = jsonobject3.getLong("id");
//     }
//     if (jsonobject3.has("image_data"))
//     {
//         inventoryitem.setImageNabPk(jsonobject3.getJSONObject("image_data").getLong("id"));
//         inventoryitem.setImagePath((new StringBuilder("nab://")).append(inventoryitem.getNabPk()).toString());
//         File file = new File((new StringBuilder()).append(GeneralApi.getCacheDir().getPath()).append(File.separator).append(inventoryitem.getImagePath().hashCode()).toString());
//         if (file.exists())
//         {
//             file.delete();
//         }
//     }
//     if (flag1)
//     {
//         break MISSING_BLOCK_LABEL_975;
//     }
//     commit(inventoryitem);
//     onInventoryChanged();
//     return eInventoryResponse.OK;
//_L9:
//     if (s.equals("[]") || s.equals("null"))
//     {
//         einventoryresponse = eInventoryResponse.OK;
//     }
//     return einventoryresponse;
//_L14:
//     return einventoryresponse;
//     if (true) goto _L16; else goto _L15
//_L15:
// }
//
// public static eInventoryResponse uploadTag(Tag tag)
// {
//     if (tag == null)
//     {
//         break MISSING_BLOCK_LABEL_11;
//     }
//     if (tag.getName() != null)
//     {
//         break MISSING_BLOCK_LABEL_15;
//     }
//     return eInventoryResponse.Error;
//     HttpHelper2 httphelper2;
//     JSONObject jsonobject;
//     httphelper2 = new HttpHelper2();
//     jsonobject = new JSONObject();
//     jsonobject.put("name", tag.getName());
//     if (tag == null) goto _L2; else goto _L1
//_L1:
//     if (tag.getNabPk() <= 0L) goto _L2; else goto _L3
//_L3:
//     String s = httphelper2.send((new StringBuilder()).append(Vault.getStoreFrontUri()).append(Vault.getUpdateTagUrl().replace("{id}", Long.toString(tag.getNabPk()))).toString(), HttpHelper2.Verb.Put, jsonobject.toString(), GeneralApi.getCurrentUser().getUserName(), GeneralApi.getCurrentUser().getLoginKey(), GeneralApi.isTestDrive());
//_L5:
//     JSONObject jsonobject1 = new JSONObject(s);
//     if (jsonobject1.has("id"))
//     {
//         long l = jsonobject1.getLong("id");
//         if (tag.getNabPk() <= 0L)
//         {
//             tag.setNabPk(l);
//         }
//         tag.setIsActive(true);
//         return eInventoryResponse.OK;
//     }
//     break; /* Loop/switch isn't completed */
//_L2:
//     s = httphelper2.send((new StringBuilder()).append(Vault.getStoreFrontUri()).append(Vault.getCreateTagUrl()).toString(), HttpHelper2.Verb.Post, jsonobject.toString(), GeneralApi.getCurrentUser().getUserName(), GeneralApi.getCurrentUser().getLoginKey(), GeneralApi.isTestDrive());
//     if (true) goto _L5; else goto _L4
//_L4:
//     eInventoryResponse einventoryresponse;
//     try
//     {
//         einventoryresponse = eInventoryResponse.Error;
//     }
//     catch (JSONException jsonexception)
//     {
//         jsonexception.printStackTrace();
//         return eInventoryResponse.Error;
//     }
//     return einventoryresponse;
// }
//
//
//
//
//
// private class _cls4
//     implements Callable
// {
//
//     final Dao val$categoryDao;
//     final JSONArray val$globalCategories;
//
////     public final Object call()
////     {
////         return call();
////     }
//
//     public final Void call()
//     {
//         SelectArg selectarg = new SelectArg();
//         com.j256.ormlite.stmt.PreparedQuery preparedquery = categoryDao.queryBuilder().where().eq("_nab_pk", selectarg).prepare();
//         for (int i = 0; i < globalCategories.length(); i++)
//         {
//             JSONObject jsonobject = globalCategories.getJSONObject(i);
//             long l = jsonobject.getLong("id");
//             String s = jsonobject.getString("name");
//             boolean flag = jsonobject.getBoolean("is_active");
//             selectarg.setValue(Long.valueOf(l));
//             DbSaleItemCategory dbsaleitemcategory = (DbSaleItemCategory)categoryDao.queryForFirst(preparedquery);
//             if (dbsaleitemcategory == null)
//             {
//                 dbsaleitemcategory = new DbSaleItemCategory(s);
//                 dbsaleitemcategory.setNabPk(Long.valueOf(l));
//             }
//             dbsaleitemcategory.setIsActive(flag);
//             dbsaleitemcategory.setName(s);
//             categoryDao.createOrUpdate(dbsaleitemcategory);
//         }
//
//         return null;
//     }
//
//     _cls4()
//     {
//         categoryDao = dao;
//         globalCategories = jsonarray;
//         super();
//     }
// }
//
//
// private class _cls5
//     implements Callable
// {
//
//     final CoreDbHelper val$helper;
//     final JSONArray val$salesItems;
//
//     public final volatile Object call()
//     {
//         return call();
//     }
//
//     public final Void call()
//     {
//         Dao dao = helper.getSaleItemDao();
//         SelectArg selectarg = new SelectArg();
//         com.j256.ormlite.stmt.PreparedQuery preparedquery = dao.queryBuilder().where().eq("_nab_pk", selectarg).prepare();
//         Dao dao1 = helper.getMultimediaItemDao();
//         Dao dao2 = helper.getSaleItemCategoryDao();
//         SelectArg selectarg1 = new SelectArg();
//         com.j256.ormlite.stmt.PreparedQuery preparedquery1 = dao2.queryBuilder().where().eq("_nab_pk", selectarg1).prepare();
//         Dao dao3 = helper.getSaleItemCategoryRelationDao();
//         DeleteBuilder deletebuilder = dao3.deleteBuilder();
//         SelectArg selectarg2 = new SelectArg();
//         deletebuilder.where().eq("_item_id", selectarg2);
//         Dao dao4 = helper.getSaleItemTagDao();
//         QueryBuilder querybuilder = dao4.queryBuilder();
//         Dao dao5 = helper.getSaleItemTagRelationDao();
//         DeleteBuilder deletebuilder1 = dao5.deleteBuilder();
//         SelectArg selectarg3 = new SelectArg();
//         deletebuilder1.where().eq("_item_id", selectarg3);
//         int i = 0;
//         while (i < salesItems.length()) 
//         {
//             JSONObject jsonobject = salesItems.getJSONObject(i);
//             long l = jsonobject.getLong("id");
//             boolean flag = jsonobject.getBoolean("is_active");
//             boolean flag1 = jsonobject.getBoolean("is_taxable");
//             String s = jsonobject.getString("name");
//             String s1;
//             String s2;
//             long l1;
//             Money money;
//             String s3;
//             JSONArray jsonarray;
//             JSONArray jsonarray1;
//             DbSaleItem dbsaleitem;
//             DbSaleItem dbsaleitem2;
//             boolean flag2;
//             ArrayList arraylist;
//             if (jsonobject.has("description"))
//             {
//                 s1 = jsonobject.getString("description");
//             } else
//             {
//                 s1 = "";
//             }
//             if (jsonobject.has("created_date"))
//             {
//                 s2 = jsonobject.getString("created_date");
//             } else
//             {
//                 s2 = "";
//             }
//             l1 = (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")).parse(s2).getTime();
//             money = new Money(jsonobject.getString("price"));
//             if (jsonobject.has("receipt_message"))
//             {
//                 s3 = jsonobject.getString("receipt_message");
//             } else
//             {
//                 s3 = "";
//             }
//             jsonarray = jsonobject.getJSONArray("categories");
//             jsonarray1 = jsonobject.getJSONArray("tags");
//             selectarg.setValue(Long.valueOf(l));
//             dbsaleitem = (DbSaleItem)dao.queryForFirst(preparedquery);
//             if (dbsaleitem == null)
//             {
//                 DbSaleItem dbsaleitem1 = new DbSaleItem(s, s1, s3, money.toBigDecimal(), flag1, null, flag);
//                 dbsaleitem1.setNabPk(l);
//                 dbsaleitem2 = dbsaleitem1;
//                 flag2 = true;
//             } else
//             {
//                 dbsaleitem2 = dbsaleitem;
//                 flag2 = false;
//             }
//             dbsaleitem2.setDescription(s1);
//             dbsaleitem2.setIsActive(flag);
//             dbsaleitem2.setIsTaxable(flag1);
//             dbsaleitem2.setName(s);
//             dbsaleitem2.setPrice(money.toBigDecimal());
//             dbsaleitem2.setReceiptMessage(s3);
//             if (l1 > 0L)
//             {
//                 dbsaleitem2.setDateAdded(l1);
//             }
//             if (jsonobject.has("image_id"))
//             {
//                 String s4 = (new StringBuilder("nab://")).append(l).toString();
//                 File file = new File((new StringBuilder()).append(GeneralApi.getCacheDir().getPath()).append(File.separator).append(s4.hashCode()).toString());
//                 if (file.exists())
//                 {
//                     file.delete();
//                 }
//                 DbMultimediaItem dbmultimediaitem = new DbMultimediaItem(s4);
//                 dbmultimediaitem.setNabPk(jsonobject.getLong("image_id"));
//                 dao1.createOrUpdate(dbmultimediaitem);
//                 dbsaleitem2.setImage(dbmultimediaitem);
//             }
//             if (flag2)
//             {
//                 dao.create(dbsaleitem2);
//             } else
//             {
//                 dao.update(dbsaleitem2);
//                 selectarg2.setValue(Long.valueOf(dbsaleitem2.getPk()));
//                 dao3.delete(deletebuilder.prepare());
//                 selectarg3.setValue(Long.valueOf(dbsaleitem2.getPk()));
//                 dao5.delete(deletebuilder1.prepare());
//             }
//             if (jsonarray.length() > 0)
//             {
//                 selectarg1.setValue(Long.valueOf(jsonarray.getJSONObject(0).getLong("id")));
//                 DbSaleItemCategory dbsaleitemcategory = (DbSaleItemCategory)dao2.queryForFirst(preparedquery1);
//                 int j;
//                 if (dbsaleitemcategory != null)
//                 {
//                     dao3.create(new DbSaleItemCategoryRelation(dbsaleitem2, dbsaleitemcategory));
//                 } else
//                 {
//                     Log.w("SQL Eception", (new StringBuilder("dbSaleItemCategory null:")).append(dbsaleitem2.getName()).append(" ").append(dbsaleitem2.getPrice()).append(" ").append(dbsaleitem2.getNabPk()).append(" ").toString());
//                 }
//             }
//             if (jsonarray1.length() <= 0)
//             {
//                 continue;
//             }
//             arraylist = new ArrayList();
//             for (j = 0; j < jsonarray1.length(); j++)
//             {
//                 arraylist.add(Long.valueOf(jsonarray1.getJSONObject(j).getLong("id")));
//             }
//
//             for (Iterator iterator = dao4.query(querybuilder.where().in("_nab_pk", arraylist).prepare()).iterator(); iterator.hasNext(); dao5.create(new DbSaleItemTagRelation(dbsaleitem2, (DbSaleItemTag)iterator.next()))) { }
//             i++;
//         }
//         return null;
//     }
//
//     _cls5()
//     {
//         helper = coredbhelper;
//         salesItems = jsonarray;
//         super();
//     }
// }
//
//
// private class _cls3
//     implements Callable
// {
//
//     final JSONArray val$globalTags;
//     final Dao val$tagDao;
//
//     public final volatile Object call()
//     {
//         return call();
//     }
//
//     public final Void call()
//     {
//         SelectArg selectarg = new SelectArg();
//         com.j256.ormlite.stmt.PreparedQuery preparedquery = tagDao.queryBuilder().where().eq("_nab_pk", selectarg).prepare();
//         for (int i = 0; i < globalTags.length(); i++)
//         {
//             JSONObject jsonobject = globalTags.getJSONObject(i);
//             long l = jsonobject.getLong("id");
//             String s = jsonobject.getString("name");
//             boolean flag = jsonobject.getBoolean("is_active");
//             selectarg.setValue(Long.valueOf(l));
//             DbSaleItemTag dbsaleitemtag = (DbSaleItemTag)tagDao.queryForFirst(preparedquery);
//             if (dbsaleitemtag == null)
//             {
//                 dbsaleitemtag = new DbSaleItemTag(s);
//                 dbsaleitemtag.setNabPk(Long.valueOf(l));
//             }
//             dbsaleitemtag.setIsActive(flag);
//             dbsaleitemtag.setName(s);
//             tagDao.createOrUpdate(dbsaleitemtag);
//         }
//
//         return null;
//     }
//
//     _cls3()
//     {
//         tagDao = dao;
//         globalTags = jsonarray;
//         super();
//     }
// }
//
//
// private class _cls6
// {
//
//     static final int $SwitchMap$com$nabancard$api$Rule$eAttribute[];
//     static final int $SwitchMap$com$nabancard$api$Rule$eOperator[];
//
//     static 
//     {
//         $SwitchMap$com$nabancard$api$Rule$eAttribute = new int[Rule.eAttribute.values().length];
//         try
//         {
//             $SwitchMap$com$nabancard$api$Rule$eAttribute[Rule.eAttribute.DATE_ADDED.ordinal()] = 1;
//         }
//         catch (NoSuchFieldError nosuchfielderror) { }
//         try
//         {
//             $SwitchMap$com$nabancard$api$Rule$eAttribute[Rule.eAttribute.NAME.ordinal()] = 2;
//         }
//         catch (NoSuchFieldError nosuchfielderror1) { }
//         try
//         {
//             $SwitchMap$com$nabancard$api$Rule$eAttribute[Rule.eAttribute.DESCRIPTION.ordinal()] = 3;
//         }
//         catch (NoSuchFieldError nosuchfielderror2) { }
//         try
//         {
//             $SwitchMap$com$nabancard$api$Rule$eAttribute[Rule.eAttribute.IS_TAXABLE.ordinal()] = 4;
//         }
//         catch (NoSuchFieldError nosuchfielderror3) { }
//         try
//         {
//             $SwitchMap$com$nabancard$api$Rule$eAttribute[Rule.eAttribute.RECEIPT_MESSAGE.ordinal()] = 5;
//         }
//         catch (NoSuchFieldError nosuchfielderror4) { }
//         try
//         {
//             $SwitchMap$com$nabancard$api$Rule$eAttribute[Rule.eAttribute.CATEGORY_NAME.ordinal()] = 6;
//         }
//         catch (NoSuchFieldError nosuchfielderror5) { }
//         try
//         {
//             $SwitchMap$com$nabancard$api$Rule$eAttribute[Rule.eAttribute.TAG_NAME.ordinal()] = 7;
//         }
//         catch (NoSuchFieldError nosuchfielderror6) { }
//         try
//         {
//             $SwitchMap$com$nabancard$api$Rule$eAttribute[Rule.eAttribute.IMAGE.ordinal()] = 8;
//         }
//         catch (NoSuchFieldError nosuchfielderror7) { }
//         try
//         {
//             $SwitchMap$com$nabancard$api$Rule$eAttribute[Rule.eAttribute.PRICE_AMOUNT.ordinal()] = 9;
//         }
//         catch (NoSuchFieldError nosuchfielderror8) { }
//         $SwitchMap$com$nabancard$api$Rule$eOperator = new int[Rule.eOperator.values().length];
//         try
//         {
//             $SwitchMap$com$nabancard$api$Rule$eOperator[Rule.eOperator.ON_DATE.ordinal()] = 1;
//         }
//         catch (NoSuchFieldError nosuchfielderror9) { }
//         try
//         {
//             $SwitchMap$com$nabancard$api$Rule$eOperator[Rule.eOperator.ON_OR_AFTER.ordinal()] = 2;
//         }
//         catch (NoSuchFieldError nosuchfielderror10) { }
//         try
//         {
//             $SwitchMap$com$nabancard$api$Rule$eOperator[Rule.eOperator.ON_OR_BEFORE.ordinal()] = 3;
//         }
//         catch (NoSuchFieldError nosuchfielderror11) { }
//         try
//         {
//             $SwitchMap$com$nabancard$api$Rule$eOperator[Rule.eOperator.BEGINS_WITH.ordinal()] = 4;
//         }
//         catch (NoSuchFieldError nosuchfielderror12) { }
//         try
//         {
//             $SwitchMap$com$nabancard$api$Rule$eOperator[Rule.eOperator.ENDS_WITH.ordinal()] = 5;
//         }
//         catch (NoSuchFieldError nosuchfielderror13) { }
//         try
//         {
//             $SwitchMap$com$nabancard$api$Rule$eOperator[Rule.eOperator.CONTAINS.ordinal()] = 6;
//         }
//         catch (NoSuchFieldError nosuchfielderror14) { }
//         try
//         {
//             $SwitchMap$com$nabancard$api$Rule$eOperator[Rule.eOperator.DOES_NOT_CONTAIN.ordinal()] = 7;
//         }
//         catch (NoSuchFieldError nosuchfielderror15) { }
//         try
//         {
//             $SwitchMap$com$nabancard$api$Rule$eOperator[Rule.eOperator.MATCHES.ordinal()] = 8;
//         }
//         catch (NoSuchFieldError nosuchfielderror16) { }
//         try
//         {
//             $SwitchMap$com$nabancard$api$Rule$eOperator[Rule.eOperator.DOES_NOT_MATCH.ordinal()] = 9;
//         }
//         catch (NoSuchFieldError nosuchfielderror17) { }
//         try
//         {
//             $SwitchMap$com$nabancard$api$Rule$eOperator[Rule.eOperator.IMAGE_EXISTS.ordinal()] = 10;
//         }
//         catch (NoSuchFieldError nosuchfielderror18) { }
//         try
//         {
//             $SwitchMap$com$nabancard$api$Rule$eOperator[Rule.eOperator.IMAGE_DOES_NOT_EXIST.ordinal()] = 11;
//         }
//         catch (NoSuchFieldError nosuchfielderror19)
//         {
//             return;
//         }
//     }
// }
//
//
// private class CreateNewCategoryTask extends AsyncTask
// {
//
//     private Boolean _addToCache;
//     private Category _category;
//
//     protected volatile Object doInBackground(Object aobj[])
//     {
//         return doInBackground((Void[])aobj);
//     }
//
//     protected transient Void doInBackground(Void avoid[])
//     {
//         if (InventoryApi.uploadCategory(_category) == eInventoryResponse.OK)
//         {
//             InventoryApi.createDbCategory(_category, _addToCache.booleanValue());
//         }
//         return null;
//     }
//
//     protected void onPreExecute()
//     {
//         super.onPreExecute();
//     }
//
//     public CreateNewCategoryTask(Category category, boolean flag)
//     {
//         _category = category;
//         _addToCache = Boolean.valueOf(flag);
//     }
// }
//
//
// private class CreateNewTagTask extends AsyncTask
// {
//
//     private Boolean _addToCache;
//     private Tag _tag;
//
//     protected transient eInventoryResponse doInBackground(Void avoid[])
//     {
//         return InventoryApi.uploadTag(_tag);
//     }
//
//     protected volatile Object doInBackground(Object aobj[])
//     {
//         return doInBackground((Void[])aobj);
//     }
//
//     protected void onPostExecute(eInventoryResponse einventoryresponse)
//     {
//         if (einventoryresponse == eInventoryResponse.OK)
//         {
//             InventoryApi.createDbTag(_tag, _addToCache.booleanValue());
//             return;
//         } else
//         {
//             Log.e("upload create tag error: ", einventoryresponse.toString());
//             return;
//         }
//     }
//
//     protected volatile void onPostExecute(Object obj)
//     {
//         onPostExecute((eInventoryResponse)obj);
//     }
//
//     protected void onPreExecute()
//     {
//         super.onPreExecute();
//     }
//
//     public CreateNewTagTask(Tag tag, boolean flag)
//     {
//         _tag = tag;
//         _addToCache = Boolean.valueOf(flag);
//     }
// }
//

 public abstract class OnInventoryChangedListener
 {

     public abstract void onInventoryChanged();
 }


// private class _cls2
//     implements Callable
// {
//
//     final CoreDbHelper helper;
//
////     public final volatile Object call()
////     {
////         return call();
////     }
//
//     public final Void call() throws SQLException
//     {
//         DbSaleItemCategory dbsaleitemcategory;
//         for (Iterator iterator = helper.getSaleItemCategoryDao().queryBuilder().orderByRaw("_name COLLATE NOCASE").where().eq("_merchant_reference_id", GeneralApi.getCurrentMerchant()).and().eq("_is_active", Boolean.valueOf(true)).query().iterator(); iterator.hasNext(); InventoryApi._categories.add(new Category(dbsaleitemcategory)))
//         {
//             dbsaleitemcategory = (DbSaleItemCategory)iterator.next();
//         }
//
//         for (Iterator iterator1 = helper.getSaleItemCategoryRelationDao().queryForAll().iterator(); iterator1.hasNext();)
//         {
//             DbSaleItemCategoryRelation dbsaleitemcategoryrelation = (DbSaleItemCategoryRelation)iterator1.next();
//             Iterator iterator2 = InventoryApi._categories.iterator();
//             while (iterator2.hasNext()) 
//             {
//                 Category category = (Category)iterator2.next();
//                 if (dbsaleitemcategoryrelation.getCategory().getPk() == category.getPk())
//                 {
//                     Iterator iterator3 = InventoryApi._items.iterator();
//                     while (iterator3.hasNext()) 
//                     {
//                         InventoryItem inventoryitem = (InventoryItem)iterator3.next();
//                         if (dbsaleitemcategoryrelation.getItem().getPk() == inventoryitem.getPk())
//                         {
//                             category.addItem(inventoryitem);
//                             inventoryitem.setCategory(category);
//                         }
//                     }
//                 }
//             }
//         }
//
//         return null;
//     }
//
//     _cls2()
//     {
//         super();
//         helper = GeneralApi.getDbHelper();
//     }
// }


// private class _cls1
//     implements Callable
// {
//
//     final CoreDbHelper helper;
//
////     public final volatile Object call()
////     {
////         return call();
////     }
//
//     public final Void call() throws SQLException
//     {
//         Dao dao = helper.getSaleItemDao();
//         Dao dao1 = helper.getSaleItemTagDao();
//         DbSaleItem dbsaleitem;
//         for (Iterator iterator = dao.queryBuilder().orderByRaw("_name COLLATE NOCASE").where().eq("_merchant_reference_id", GeneralApi.getCurrentMerchant()).and().eq("_is_active", Boolean.valueOf(true)).query().iterator(); iterator.hasNext(); InventoryApi._items.add(new InventoryItem(dbsaleitem)))
//         {
//             dbsaleitem = (DbSaleItem)iterator.next();
//         }
//
//         InventoryApi.populateCategories();
//         DbSaleItemTag dbsaleitemtag;
//         for (Iterator iterator1 = dao1.queryBuilder().orderByRaw("_name COLLATE NOCASE").where().eq("_user_reference_id", GeneralApi.getCurrentUser()).and().eq("_is_active", Boolean.valueOf(true)).query().iterator(); iterator1.hasNext(); InventoryApi._tags.add(new Tag(dbsaleitemtag)))
//         {
//             dbsaleitemtag = (DbSaleItemTag)iterator1.next();
//         }
//
//         return null;
//     }
//
//     _cls1()
//     {
//         super();
//         helper = GeneralApi.getDbHelper();
//     }
// }

//
// private class eInventoryResponse extends Enum
// {
//
//     private static final eInventoryResponse $VALUES[];
//     public static final eInventoryResponse Error;
//     public static final eInventoryResponse OK;
//     public static final eInventoryResponse Validation;
//     public static final eInventoryResponse createNotFoundException;
//
//     public static eInventoryResponse valueOf(String s)
//     {
//         return (eInventoryResponse)Enum.valueOf(com/nabancard/api/InventoryApi$eInventoryResponse, s);
//     }
//
//     public static eInventoryResponse[] values()
//     {
//         return (eInventoryResponse[])$VALUES.clone();
//     }
//
//     static 
//     {
//         OK = new eInventoryResponse("OK", 0);
//         createNotFoundException = new eInventoryResponse("createNotFoundException", 1);
//         Validation = new eInventoryResponse("Validation", 2);
//         Error = new eInventoryResponse("Error", 3);
//         eInventoryResponse aeinventoryresponse[] = new eInventoryResponse[4];
//         aeinventoryresponse[0] = OK;
//         aeinventoryresponse[1] = createNotFoundException;
//         aeinventoryresponse[2] = Validation;
//         aeinventoryresponse[3] = Error;
//         $VALUES = aeinventoryresponse;
//     }
//
//     private eInventoryResponse(String s, int i)
//     {
//         super(s, i);
//     }
// }

}
