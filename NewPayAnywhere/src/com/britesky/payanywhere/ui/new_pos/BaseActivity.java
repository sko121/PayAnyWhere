package com.britesky.payanywhere.ui.new_pos;

import com.britesky.payanywhere.R;
import com.britesky.payanywhere.api.GeneralApi;
import com.britesky.payanywhere.ui.util.PayMenuItem;
import com.britesky.payanywhere.ui.util.SystemUtil;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.app.SherlockListFragment;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class BaseActivity extends SherlockFragmentActivity 
    implements android.widget.AdapterView.OnItemClickListener {
    
    public final class MenuListFragment extends SherlockListFragment
    {
        public MenuListFragment()
        {
        }

        public final void onActivityCreated(Bundle bundle)
        {
            super.onActivityCreated(bundle);
            getListView().setVerticalScrollBarEnabled(false);
        }
    }
    
    protected SlidingMenu mSlidingMenu;
    private MenuListFragment mMenuListFragment;
    private MenuAdapter mMenuAdapter;
    private PayMenuItem mSaleMenuItem;
    private PayMenuItem mHomeMenuItem;
    private ImageView mDrawerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        GeneralApi.init(getApplicationContext());
        
        ActionBar actionbar;
        actionbar = getSupportActionBar();
        actionbar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.red)));
        actionbar.setTitle(R.string.testdrive);
        actionbar.setDisplayShowTitleEnabled(false);
        actionbar.setDisplayShowTitleEnabled(true);
    }
    
    @Override
    public void onPostCreate(Bundle bundle) {
        super.onPostCreate(bundle);
        
        this.mMenuListFragment.getListView().setOnItemClickListener(this);
    }

    @Override
    public void setContentView(int layout) {
        super.setContentView(R.layout.activity_base);
        ((LinearLayout)findViewById(R.id.base_content_wrapper))
            .addView(getLayoutInflater().inflate(layout, null), new android.view.ViewGroup.LayoutParams(-1, -1));
        mDrawerButton = (ImageView)findViewById(R.id.drawer_button);
        mDrawerButton.setColorFilter(SystemUtil.getBrandColor(this));

        mSlidingMenu = new SlidingMenu(this);
        mSlidingMenu.setMenu(R.layout.menu_list);
        mSlidingMenu.setMode(0);
        mSlidingMenu.setTouchModeAbove(0);
        mSlidingMenu.setBehindOffset(400);
        mSlidingMenu.setBehindScrollScale(0.5F);
        mSlidingMenu.setBehindWidth(400);
        mSlidingMenu.setFadeDegree(0.8F);
        mSlidingMenu.attachToActivity(this, 1);
//        mSlidingMenu.setOnOpenedListener(new j(this));
//        mSlidingMenu.setOnClosedListener(new k(this));
//        setSlideMode(ab);
        
        mMenuListFragment = new MenuListFragment();
        setFragment(R.id.menu, mMenuListFragment);

        mMenuAdapter = new MenuAdapter(this);
        mMenuListFragment.setListAdapter(mMenuAdapter);
    }
    
    public void toggleSlidingMenu(View view) {
        mSlidingMenu.toggle();
    }
    
    public void setFragment(int i1, Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(i1, fragment).commit();
    }
    
    public void setActionBar(int resid, boolean flag) {
        ActionBar actionbar;
        actionbar = getSupportActionBar();

        actionbar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.red)));
        actionbar.setTitle(R.string.testdrive);
    }
    
    public PayMenuItem addSlidingMenuItem(int i1, int j1) {
        PayMenuItem at1 = new PayMenuItem(getString(i1), j1, 0);
        addSlidingMenuItem(at1);
        return at1;
    }
    
    public void addSlidingMenuItem(PayMenuItem at1) {
        mMenuAdapter.addItem(at1);
    }
    
    public PayMenuItem addSpecialSlidingMenuItem(String s1, int i1, int j1) {
        PayMenuItem at1 = new PayMenuItem(s1, i1, j1);
        addSlidingMenuItem(at1);
        return at1;
    }
    
    public void addDivider() {
        mMenuAdapter.addDivider();
    }
    
    public void setUpSlidingMenuBaseItems(boolean flag, boolean flag1) {
        if (flag) {
            mSaleMenuItem = addSlidingMenuItem(R.string.express_sale_cap, R.drawable.ic_menu_getpaid);
        }
        if (flag1) {
            mHomeMenuItem = addSlidingMenuItem(R.string.home, R.drawable.ic_menu_home);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterview, View view, int arg2, long arg3) {
        PayMenuItem menuItem = (PayMenuItem)view.getTag();
        if (menuItem == this.mHomeMenuItem) {
            this.dirtyHome();
        }
        else if (menuItem == this.mSaleMenuItem) {
            Intent intent = new Intent(this, ManualItemActivity.class);
            startActivity(intent);
        }
    }
    
    public void dirtyHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    
//    public boolean isAnimationRunning()
//    {
//        return t.isRunning() || u.isRunning();
//    }
    
    public void flipViews(View view, View view1)
    {
        if (view.getVisibility() == View.VISIBLE) {
            view.setVisibility(View.INVISIBLE);
            view1.setVisibility(View.VISIBLE);
        }
        else {
            view1.setVisibility(View.INVISIBLE);
            view.setVisibility(View.VISIBLE);            
        }
//        if (!isAnimationRunning())
//        {
//            View view2;
//            View view3;
//            byte byte0;
//            if (view.getVisibility() == 0)
//            {
//                byte0 = 4;
//                view2 = view1;
//                view3 = view;
//            } else
//            {
//                view2 = view;
//                view3 = view1;
//                byte0 = 0;
//            }
//            t = com.d.a.v.ofFloat(view3, "rotationY", new float[] {
//                0.0F, 90F
//            });
//            t.setDuration(500L);
//            t.setInterpolator(W);
//            u = com.d.a.v.ofFloat(view2, "rotationY", new float[] {
//                -90F, 0.0F
//            });
//            u.setDuration(500L);
//            u.setInterpolator(X);
//            t.addListener(new com.nabancard.ui.new_pos.m(this, view, byte0, view1));
//            t.start();
//        }
    }
}
