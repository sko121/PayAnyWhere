package com.britesky.payanywhere.ui.config;

import java.util.ArrayList;
import java.util.List;

import com.britesky.payanywhere.R;
import com.britesky.payanywhere.ui.util.ImageUtil;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SettingsListFragment extends ListFragment {
    
    public final class br
    {

        private int a;
        private int b;
        private boolean c;

        private br(int i)
        {
            a = i;
            c = true;
        }

        br(int i, byte byte0)
        {
            this(i);
        }

        private br(int i, int j)
        {
            a = i;
            b = j;
        }

        br(int i, int j, byte byte0)
        {
            this(i, j);
        }

        private int a()
        {
            return a;
        }

        private void a(int i)
        {
            a = i;
        }

        void a(br br1, int i)
        {
            br1.a = i;
        }

        boolean a(br br1)
        {
            return br1.c;
        }

        int b(br br1)
        {
            return br1.a;
        }

        int c(br br1)
        {
            return br1.a;
        }

        int d(br br1)
        {
            return br1.b;
        }

        public final int getId()
        {
            return SettingsListFragment.mSettings.indexOf(this);
        }
    }
    
    public final class SettingsListAdapter extends BaseAdapter {
        
        private final SettingsListFragment mFragment;
        private LayoutInflater b;
        
        public SettingsListAdapter(SettingsListFragment fragment) {
            mFragment = fragment;
            b = (LayoutInflater)fragment.getActivity().getSystemService("layout_inflater");
        }

        @Override
        public int getCount() {
            return this.mFragment.mSettings.size();
        }

        @Override
        public Object getItem(int index) {
            return this.mFragment.mSettings.get(index);
        }

        @Override
        public long getItemId(int index) {
            return (long)index;
        }

        @Override
        public View getView(int index, View view, ViewGroup viewgroup) {
            br br1 = (br)getItem(index);
            TextView textview;
            if (br1.a(br1)) {
                textview = (TextView)b.inflate(R.layout.settings_list_footer, viewgroup, false);
                SettingsListFragment settingslistfragment = this.mFragment;
                int j = br1.b(br1);
                Object aobj[] = new Object[1];
                aobj[0] = "Test"; //GeneralApi.getLoggedInUserName();
                textview.setText(settingslistfragment.getString(j, aobj));
            }
            else {
                textview = (TextView)b.inflate(R.layout.settings_list_item, viewgroup, false);
                textview.setText(br1.c(br1));
                textview.setCompoundDrawablesWithIntrinsicBounds(null, null, ImageUtil.applyBrandColor(this.mFragment.getActivity(), this.mFragment.getResources().getDrawable(br1.d(br1))), null);
            }

            return textview;
        }
    }
    
    public static final List mSettings = new ArrayList();
    
    public final br l = new br(R.string.general_settings_cap, R.drawable.ic_menu_settings_general, (byte)0);
    public final br m = new br(R.string.printing_caps, R.drawable.ic_menu_settings_print, (byte)0);
    public final br ai = new br(R.string.receiptinfo_caps, R.drawable.ic_menu_settings_receipt, (byte)0);
    public final br aj = new br(R.string.merchant_accounts_caps, R.drawable.ic_menu_settings_accounts, (byte)0);
    public final br ak = new br(R.string.reset_pin, R.drawable.ic_menu_settings_pin, (byte)0);
    public final br al = new br(R.string.pin_settings_caps, R.drawable.ic_menu_settings_pin, (byte)0);
    public final br am = new br(R.string.sync_data, R.drawable.ic_menu_refresh, (byte)0);
    public final br an = new br(R.string.help_caps, R.drawable.ic_menu_settings_help, (byte)0);
    public final br ao = new br(R.string.sign_out_caps, R.drawable.ic_menu_settings_signout, (byte)0);
    public final br ap = new br(R.string.signed_in_as, (byte)0);
    
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        
        mSettings.clear();
        mSettings.add(l);
        mSettings.add(m);
        mSettings.add(ai);
        mSettings.add(aj);
        mSettings.add(ak);
        mSettings.add(al);
        mSettings.add(am);
        mSettings.add(an);
        mSettings.add(ao);
        mSettings.add(ap);
        
        setListAdapter(new SettingsListAdapter(this));
    }

    @Override
    public void onViewCreated(View view, Bundle bundle)
    {
        super.onViewCreated(view, bundle);
        
        getListView().setBackgroundResource(R.drawable.gradient_menu);
    }
}
