package com.britesky.payanywhere.ui.new_pos;

import com.britesky.payanywhere.R;
import com.britesky.payanywhere.ui.util.PayMenuItem;

import android.os.Bundle;

public class TransactionDetailActivity extends BaseActivity {

    private PayMenuItem mRelatedTransationsMenuItem;
    private PayMenuItem mShareMenuItem;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_detail);
    }
    
    @Override
    public void onPostCreate(Bundle bundle) {
        super.onPostCreate(bundle);
        mRelatedTransationsMenuItem = addSlidingMenuItem(R.string.related_transactions_cap, R.drawable.ic_menu_getpaid);

        mShareMenuItem = addSlidingMenuItem(R.string.share_cap, R.drawable.ic_menu_share);
//        a();
        addDivider();
        setUpSlidingMenuBaseItems(false, true);
    }

}
