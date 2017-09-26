package com.britesky.payanywhere.ui.new_pos;

import com.actionbarsherlock.view.MenuItem;
import com.britesky.payanywhere.R;
import com.britesky.payanywhere.api.InventoryApi;
import com.britesky.payanywhere.ui.new_pos.BaseActivity;
import com.britesky.payanywhere.ui.util.PayMenuItem;
import com.viewpagerindicator.TitlePageIndicator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

public class InventoryActivity extends BaseActivity {

    private ViewPager mViewPager;
    private TitlePageIndicator mTitlePageIndicator;
    private InventoryPagerAdapter mPagerAdapter;
    private PayMenuItem mManageAttributesMenuItem;
    private static boolean mToRefresh = false;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        
        mViewPager = (ViewPager)findViewById(R.id.inventory_pager);
        mViewPager.setOffscreenPageLimit(1);
        mViewPager.setCurrentItem(0);
        mViewPager.setOverScrollMode(ViewPager.OVER_SCROLL_NEVER);
        
        mPagerAdapter = new InventoryPagerAdapter(getSupportFragmentManager(), this);
        mPagerAdapter.setMenuMode(0);
        mViewPager.setAdapter(mPagerAdapter);
        
        mTitlePageIndicator = (TitlePageIndicator)findViewById(R.id.inventory_indicator);
        mTitlePageIndicator.setViewPager(mViewPager);
        mTitlePageIndicator.setFooterIndicatorStyle(TitlePageIndicator.IndicatorStyle.Triangle);
        
        setActionBar(-1, true);
        
        InventoryApi.init();
    }
    
    @Override
    public void onPostCreate(Bundle bundle) {
        super.onPostCreate(bundle);
        setUpSideItems();
    }
    
    @Override
    protected void onResume()
    {
        super.onResume();
        if (mToRefresh)
        {
//            B = true;
//            setUpSlidingMenuFilterItems(com.nabancard.api.Filter.eFilterType.ITEM, true, true);
            mPagerAdapter.refreshTitles();
            mPagerAdapter.notifyDataSetChanged();
            mToRefresh = false;
        }
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            startActivityForResult(new Intent(this, InventoryEditActivity.class), 10);
        }
        return super.onOptionsItemSelected(item);
    }

    public void setUpSideItems() {
        mManageAttributesMenuItem = addSlidingMenuItem(R.string.manage_attributes_cap, R.drawable.ic_menu_attributes);
        addDivider();
        setUpSlidingMenuBaseItems(true, true);
        addDivider();
//        setUpSlidingMenuFilterItems(com.nabancard.api.Filter.eFilterType.ITEM, true, true);
    }
    
    public static void refreshOnFocus()
    {
        mToRefresh = true;
    }
    
}
