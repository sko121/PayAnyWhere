package com.britesky.payanywhere.ui.util;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ArrayAdapter;

import com.britesky.payanywhere.R;

// Referenced classes of package com.nabancard.ui.util:
//            d

public class CameraActivity extends Activity
    implements android.app.ActionBar.OnNavigationListener
{

    public static final int a = 500;
    private static final String b = "selected_navigation_item";
    private PayCameraFragment c;
    private PayCameraFragment d;
    private PayCameraFragment e;
    private boolean f;

    public CameraActivity()
    {
        super();
        boolean flag = true;
        if (Camera.getNumberOfCameras() <= 1)
        {
            flag = false;
        }
        f = flag;
    }

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.activity_camera);
        if (f)
        {
            ActionBar actionbar = getActionBar();
            actionbar.setDisplayShowTitleEnabled(false);
            actionbar.setNavigationMode(1);
            actionbar.setListNavigationCallbacks(ArrayAdapter.createFromResource(actionbar.getThemedContext(), R.array.camera_selector, 0x1090003), this);
            return;
        } else
        {
            e = PayCameraFragment.a(false);
            getFragmentManager().beginTransaction().replace(R.id.container, e).commit();
            return;
        }
    }

    public boolean onKeyDown(int i, KeyEvent keyevent)
    {
        if (i == 27 && e != null)
        {
            e.takePicture();
            return true;
        } else
        {
            return super.onKeyDown(i, keyevent);
        }
    }

    public boolean onNavigationItemSelected(int i, long l)
    {
        if (i == 0)
        {
            if (c == null)
            {
                c = PayCameraFragment.a(false);
            }
            e = c;
        } else
        {
            if (d == null)
            {
                d = PayCameraFragment.a(true);
            }
            e = d;
        }
        getFragmentManager().beginTransaction().replace(R.id.container, e).commit();
        return true;
    }

    public void onRestoreInstanceState(Bundle bundle)
    {
        if (f && bundle.containsKey("selected_navigation_item"))
        {
            getActionBar().setSelectedNavigationItem(bundle.getInt("selected_navigation_item"));
        }
    }

    public void onSaveInstanceState(Bundle bundle)
    {
        if (f)
        {
            bundle.putInt("selected_navigation_item", getActionBar().getSelectedNavigationIndex());
        }
    }
}
