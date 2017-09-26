package com.britesky.payanywhere.ui.new_pos;

import java.util.ArrayList;

import com.britesky.payanywhere.R;
import com.britesky.payanywhere.api.Category;
import com.britesky.payanywhere.api.Filter;
import com.britesky.payanywhere.api.InventoryApi;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class InventoryPagerAdapter extends FragmentStatePagerAdapter {
    
    private Context mContext;
    private final ArrayList mCategories = new ArrayList();
    private ArrayList mTitles;
    private Filter mFilter;
    private int mMenuMode;
    
    public InventoryPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        
        mMenuMode = 0;
        mContext = context;
        mTitles = new ArrayList();
        refreshTitles();
    }

    @Override
    public Fragment getItem(int index) {
        InventoryFragment fragment = new InventoryFragment();
//        fragment.setViewMode();
//        fragment.setFilter();
        
        fragment.setModes(mMenuMode);
        if (index == 0) {
            fragment.setCategory(null);
        }
        else {
            fragment.setCategory((Category)mCategories.get(index));
        }
        
        fragment.refresh();
        return fragment;
    }

    @Override
    public int getCount() {
        return mTitles.size();
    }
    
    @Override
    public int getItemPosition(Object obj) {
        return POSITION_NONE;
    }
    
    public final CharSequence getPageTitle(int i) {
        return (CharSequence)mTitles.get(i);
    }
    
    public final void refreshTitles() {
        mCategories.clear();
        mCategories.addAll(InventoryApi.getCategories());
        
        mTitles = new ArrayList();
        addTitles();
    }
    
    private final void addTitles() {
        for (int i = 0; i < mCategories.size(); i++) {
            if (i == 0) {
                mTitles.add(0, mContext.getString(R.string.title_inventory));
            }
            else {
                mTitles.add(((Category)mCategories.get(i)).getName());
            }
        }
    }
    
    public final void setMenuMode(int i1) {
        mMenuMode = i1;
    }

}
