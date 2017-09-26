package com.britesky.payanywhere.ui.new_pos;

import com.actionbarsherlock.view.MenuItem;
import com.britesky.payanywhere.R;
import com.britesky.payanywhere.R.layout;
import com.britesky.payanywhere.R.menu;
import com.britesky.payanywhere.api.CartApi;
import com.britesky.payanywhere.ui.util.PayMenuItem;
import com.viewpagerindicator.TitlePageIndicator;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;

public class SellActivity extends BaseActivity {

    private PayMenuItem mSaleMenuItem;
    private PayMenuItem mDiscountMenuItem;
    private PayMenuItem mNewPreauthMenuItem;
    private PayMenuItem mCompletePreauthMenuItem;
    private PayMenuItem mTransactionMenuItem;
    private PayMenuItem mRefundableMenuItem;
    private PayMenuItem mVoidableMenuItem;
    private PayMenuItem mManualItemMenuItem;
    private PayMenuItem mForceTransactionMenuItem;

    private ViewPager mViewPager;
    private InventoryPagerAdapter mPagerAdapter;
    private TitlePageIndicator mTitlePageIndicator;

    private LinearLayout U;
    private LinearLayout V;
    private MenuItem at;
    
    private ReviewCartFragment mReviewCartFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);

        V = (LinearLayout) findViewById(R.id.sell_viewpager_wrapper);
        U = (LinearLayout) findViewById(R.id.sell_reviewcart_wrapper);
        U.setVisibility(View.INVISIBLE);
        
        CartApi.clearOnCartChangedListeners();

        mViewPager = (ViewPager) findViewById(R.id.sell_pager);
        mViewPager.setOffscreenPageLimit(1);
        mViewPager.setOverScrollMode(2);
        mViewPager.setCurrentItem(0);

        mPagerAdapter = new InventoryPagerAdapter(getSupportFragmentManager(), this);
        mPagerAdapter.setMenuMode(1);
        // mPagerAdapter.setViewMode(ai);
        mViewPager.setAdapter(mPagerAdapter);

        mTitlePageIndicator = (TitlePageIndicator) findViewById(R.id.sell_indicator);
        mTitlePageIndicator.setViewPager(mViewPager);
        mTitlePageIndicator.setFooterIndicatorStyle(TitlePageIndicator.IndicatorStyle.Triangle);
        
        mReviewCartFragment = (ReviewCartFragment)this.getSupportFragmentManager().findFragmentByTag("review cart");
        if (mReviewCartFragment == null) {
            mReviewCartFragment = new ReviewCartFragment();
            this.getSupportFragmentManager().beginTransaction()
            .add(R.id.sell_reviewcart_wrapper, mReviewCartFragment, "review cart").commit();
        }
    }

    @Override
    public void onPostCreate(Bundle bundle) {
        super.onPostCreate(bundle);
        mSaleMenuItem = addSlidingMenuItem(R.string.new_sale,
                R.drawable.ic_menu_newsale);
        String s1 = getString(R.string.discounts);
        int l = R.drawable.ic_menu_discount;
        int i1 = 0;
        // if (SettingsApi.getDiscountEnabled())
        // {
        // i1 = 0;
        // } else
        // {
        // i1 = 1;
        // }
        mDiscountMenuItem = addSpecialSlidingMenuItem(s1, l, i1);
        mNewPreauthMenuItem = addSlidingMenuItem(R.string.new_preauth,
                R.drawable.ic_menu_preauth);
        mCompletePreauthMenuItem = addSlidingMenuItem(
                R.string.complete_preauth, R.drawable.ic_menu_preauth);
        mTransactionMenuItem = addSlidingMenuItem(R.string.transaction_cap,
                R.drawable.ic_menu_transactions);
        mRefundableMenuItem = addSlidingMenuItem(R.string.refundable_cap,
                R.drawable.ic_menu_refund);
        mVoidableMenuItem = addSlidingMenuItem(R.string.voidable_cap,
                R.drawable.ic_menu_void);
        mManualItemMenuItem = addSlidingMenuItem(R.string.manual_item_cap,
                R.drawable.ic_menu_manualitem);
        mForceTransactionMenuItem = addSlidingMenuItem(
                R.string.force_transaction_cap, R.drawable.ic_menu_forcetrans);
        addDivider();
        setUpSlidingMenuBaseItems(true, true);
        addDivider();
        // setUpSlidingMenuFilterItems(com.nabancard.api.Filter.eFilterType.ITEM,
        // true, false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_reviewcart) {
            at = item;
            if (V.getVisibility() == 0) {
                item.setIcon(R.drawable.ico_inventory_white);
            } else {
                item.setIcon(R.drawable.ico_reviewcart_white);
            }

//            if (CartApi.getTotalItemCount() > 0) {
                flipViews(V, U);
//            }
        }

        return super.onOptionsItemSelected(item);
    }
    
    public void showPagerFragment()
    {
        if (V.getVisibility() == 4)
        {
            flipViews(V, U);
            if (at != null)
            {
                at.setIcon(R.drawable.ico_reviewcart_white);
            }
        }
    }
}
