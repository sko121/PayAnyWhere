// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.britesky.payanywhere.ui.util;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.britesky.payanywhere.R;
import com.commonsware.cwac.camera.CameraFragment;

public final class PayCameraFragment extends CameraFragment
    implements android.widget.SeekBar.OnSeekBarChangeListener
{

    private static final String a = "com.commonsware.cwac.camera.demo.USE_FFC";
    private MenuItem b;
    private MenuItem c;
    private SeekBar d;

    public PayCameraFragment()
    {
    }

    static SeekBar a(PayCameraFragment d1)
    {
        return d1.d;
    }

    static PayCameraFragment a(boolean flag)
    {
        Bundle bundle = new Bundle();
        bundle.putBoolean("com.commonsware.cwac.camera.demo.USE_FFC", flag);
        PayCameraFragment d1 = new PayCameraFragment();
        d1.setArguments(bundle);
        return d1;
    }

    static MenuItem b(PayCameraFragment d1)
    {
        return d1.b;
    }

    private static PayCameraFragment b(boolean flag)
    {
        Bundle bundle = new Bundle();
        bundle.putBoolean("com.commonsware.cwac.camera.demo.USE_FFC", flag);
        PayCameraFragment d1 = new PayCameraFragment();
        d1.setArguments(bundle);
        return d1;
    }

    public final void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setHasOptionsMenu(true);
        setHost(new PayCameraHost(this, getActivity()));
    }

    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuinflater)
    {
        menuinflater.inflate(R.menu.actionbar_camera, menu);
        c = menu.findItem(R.id.camera);
        b = menu.findItem(R.id.autofocus);
    }

    public final View onCreateView(LayoutInflater layoutinflater, ViewGroup viewgroup, Bundle bundle)
    {
        View view = super.onCreateView(layoutinflater, viewgroup, bundle);
        View view1 = layoutinflater.inflate(R.layout.fragment_camera, viewgroup, false);
        ((ViewGroup)view1.findViewById(R.id.camera)).addView(view);
        d = (SeekBar)view1.findViewById(R.id.zoom);
        return view1;
    }

    public final boolean onOptionsItemSelected(MenuItem menuitem)
    {
        if (menuitem.getItemId() == R.id.camera)
        {
            c.setEnabled(false);
            takePicture();
            return true;
        }
        if (menuitem.getItemId() == R.id.gallery)
        {
            getActivity().setResult(500);
            getActivity().finish();
            return true;
        }
        if (menuitem.getItemId() == R.id.autofocus)
        {
            autoFocus();
            return true;
        } else
        {
            return super.onOptionsItemSelected(menuitem);
        }
    }

    public final void onProgressChanged(SeekBar seekbar, int i, boolean flag)
    {
        if (flag)
        {
            d.setEnabled(false);
//            zoomTo(d.getProgress()).onComplete(new e(this)).go();
        }
    }

    public final void onStartTrackingTouch(SeekBar seekbar)
    {
    }

    public final void onStopTrackingTouch(SeekBar seekbar)
    {
    }
}
