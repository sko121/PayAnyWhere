package com.britesky.payanywhere.ui.welcomescreen;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.britesky.payanywhere.R;
import com.britesky.payanywhere.api.GeneralApi;
import com.britesky.payanywhere.ui.new_pos.HomeActivity;

import android.os.Bundle;
import android.view.View;
import android.content.Intent;

public class WelcomeActivity extends SherlockFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_welcome);
        
        GeneralApi.init(this);
    }
    
    public void onClick(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        this.startActivity(intent);
    }
}
