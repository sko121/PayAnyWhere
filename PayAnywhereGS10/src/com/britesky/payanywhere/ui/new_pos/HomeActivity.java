package com.britesky.payanywhere.ui.new_pos;

import com.britesky.payanywhere.R;
import com.britesky.payanywhere.api.GeneralApi;
import com.britesky.payanywhere.ui.config.SettingsListActivity;
import com.britesky.payanywhere.ui.new_pos.InventoryActivity;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.content.Intent;

public class HomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        
        if (GeneralApi.isTestDrive())
        {
            if (!GeneralApi.isGlobalTestDriveLoaded())
            {
//                Y = X;
//                (new j(this)).execute(new Void[0]);
                GeneralApi.setUpGlobalTestDrive();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    public void onInventoryClick(View view) {
        Intent intent = new Intent(this, InventoryActivity.class);
        startActivity(intent);
    }
    
    public void onSettingClick(View view) {
        Intent intent = new Intent(this, SettingsListActivity.class);
        startActivity(intent);
    }
    
    public void onSellClick(View view) {
        Intent intent = new Intent(this, SellActivity.class);
        startActivity(intent);
    }
    
    public void onReportsClick(View view) {
        Intent intent = new Intent(this, TransactionDetailActivity.class);
        startActivity(intent);
    }
}
