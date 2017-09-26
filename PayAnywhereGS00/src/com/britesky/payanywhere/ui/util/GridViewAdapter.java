package com.britesky.payanywhere.ui.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.britesky.payanywhere.R;
import com.britesky.payanywhere.api.CartApi;
import com.britesky.payanywhere.api.Category;
import com.britesky.payanywhere.api.Filter;
import com.britesky.payanywhere.api.InventoryApi;
import com.britesky.payanywhere.api.InventoryItem;
import com.britesky.payanywhere.api.SaleTransactionItem;
import com.britesky.payanywhere.ui.new_pos.InventoryEditActivity;
import com.britesky.payanywhere.ui.new_pos.ManualItemActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public final class GridViewAdapter extends BaseAdapter 
    implements android.view.View.OnTouchListener {
    
    private final LayoutInflater mLayoutInflater;

    private final ArrayList inventoryItems = new ArrayList();
    private List stockInventoryItems;
    private Category mCategory;
    private Activity mActivity;
    
    private boolean mSelling;
    private int mModes;
    
    public GridViewAdapter(Activity activity, int i1, Filter filter, Category category) {
        mSelling = true;
        mModes = i1;
        
        mActivity = activity;
        mLayoutInflater = (LayoutInflater)activity.getSystemService("layout_inflater");
        mCategory = category;
        refresh();
    }
    
    public void setSelling(boolean isSelling) {
        mSelling = isSelling;
    }

    @Override
    public int getCount() {
        return inventoryItems.size();
    }

    @Override
    public Object getItem(int position) {
        return inventoryItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return (long)position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        InventoryItem inventoryitem = (InventoryItem)inventoryItems.get(position);
        
        if (mModes == 1) {
            if (mSelling && position == 0 && mCategory == null) {
                if (view == null || view.getTag() != null) {
                    view = mLayoutInflater.inflate(R.layout.inventory_express_item, parent, false);
                    view.setTag(null);
                }
                ImageView imageview = (ImageView)view.findViewById(R.id.blankclick);
                imageview.setOnTouchListener(this);
                imageview.setTag("ExpressItem");
                return view;
            }

            if (mSelling && position == 1 && mCategory == null) {
                if (view == null || view.getTag() != null) { 
                    view = mLayoutInflater.inflate(R.layout.inventory_manual_item, parent, false);
                    view.setTag(null);
                }
                ImageView imageview = (ImageView)view.findViewById(R.id.blankclick);
                imageview.setOnTouchListener(this);
                imageview.setTag("ExpressItem");
                return view;
            }            
        }

        if (view == null || view.getTag() == null) {
            view = mLayoutInflater.inflate(R.layout.inventory_item, parent, false);
        }
        if (view.findViewById(R.id.reviewname) != null) {
            ((TextView)view.findViewById(R.id.reviewname)).setText(inventoryitem.getName());
        }
        if (view.findViewById(R.id.reviewprice) != null) {
            ((TextView)view.findViewById(R.id.reviewprice)).setText(inventoryitem.getPrice().toString());
        }
        ImageView imageview = (ImageView)view.findViewById(R.id.reviewimage);
        String imageTag = (String)imageview.getTag();
        if (imageTag == null || !imageTag.equals(inventoryitem.getImagePath()))
        {
            String s = inventoryitem.getImagePath();
            if (s != null && s.startsWith("/")) {
                ImageLoader.getInstance().displayImage(Uri.fromFile(new File(s)).toString(), imageview);
            }
            else {
                ImageLoader.getInstance().displayImage(inventoryitem.getImagePath(), imageview);
            }
        }
        imageview.setTag(inventoryitem.getImagePath());
        if (view.findViewById(R.id.review_category) != null) {
            if (inventoryitem.getCategory() != null) {
                ((TextView)view.findViewById(R.id.review_category)).setText(inventoryitem.getCategory().getName());
            }
            else {
                ((TextView)view.findViewById(R.id.review_category)).setText("");
            }
        }
//        if (inventoryitem.getIsTaxable()) {
//            ((ImageView)view.findViewById(R.id.reviewtaxable)).setVisibility(0);
//        } else
//        {
//            ((ImageView)view.findViewById(R.id.reviewtaxable)).setVisibility(4);
//        }
        
        List list = CartApi.getCartItems();
        String s1 = "";
        if (!list.isEmpty())
        {
            Iterator iterator = list.iterator();
            while (iterator.hasNext()) 
            {
                SaleTransactionItem saletransactionitem = (SaleTransactionItem)iterator.next();
                String s4;
                if (saletransactionitem.getInventoryItem() != null && saletransactionitem.getInventoryItem().getPk() == inventoryitem.getPk())
                {
                    s4 = Integer.toString(saletransactionitem.getQuantity());
                } else
                {
                    s4 = s1;
                }
                s1 = s4;
            }
        }
        
        TextView textview = (TextView)view.findViewById(R.id.quantity);
        textview.setText(s1);
        if (mModes != 1)
        {
            textview.setVisibility(View.INVISIBLE);
        }
        view.setOnTouchListener(this);
        view.setTag(inventoryitem);
        
        return view;
    }
    
    public final void refresh() {
        if (mCategory != null) {
            stockInventoryItems = mCategory.getItems();
        }
        else {
            stockInventoryItems = InventoryApi.getItems();
        }
        inventoryItems.clear();
        inventoryItems.addAll(stockInventoryItems);
        sort();
        
        if (mCategory == null && mModes == 1) {
            inventoryItems.add(0, null);
            inventoryItems.add(0, null);
        }
        
        this.notifyDataSetChanged();
    }
    
    public final void sort() {
        Collections.sort(inventoryItems, new PayComparator());
    }

    public final void setCategory(Category category) {
        mCategory = category;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getActionMasked() == MotionEvent.ACTION_UP) {
            if (v.getTag().equals("ExpressItem")) {
                Intent intent;
                intent = new Intent(mActivity, ManualItemActivity.class)
                        .putExtra("collapsed_mode", true)
                        .putExtra("preauthMode", false).setFlags(0x20000000);
                mActivity.startActivity(intent);
            } else if (v.getTag().getClass() == InventoryItem.class) {
                if (mModes == 0) {
                    InventoryEditActivity.mInventoryItem = (InventoryItem) v.getTag();
                    Intent intent = new Intent(mActivity,InventoryEditActivity.class);
                    mActivity.startActivity(intent);
                }
                else {
                    String s;
                    TextView textview = (TextView) v.findViewById(R.id.quantity);
                    if (textview.getText().toString().isEmpty())
                    {
                        s = "0";
                    } else
                    {
                        s = textview.getText().toString();
                    }
                    int quantity = Integer.parseInt(s);
                    textview.setText(Integer.toString(1 + quantity));
                    
                    CartApi.addOrUpdateItem((InventoryItem) v.getTag());
                }
            }
        }
        return true;
    }
}
