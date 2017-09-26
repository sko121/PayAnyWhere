package com.britesky.payanywhere.ui.config;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.britesky.payanywhere.R;
import com.britesky.payanywhere.R.layout;
import com.britesky.payanywhere.R.menu;
import com.britesky.payanywhere.ui.new_pos.BaseActivity;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class SettingsListActivity extends SherlockFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_list);
    }

}
