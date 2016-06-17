// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.britesky.payanywhere.ui.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.Toast;
import java.io.File;

import com.commonsware.cwac.camera.CameraFragment;
import com.commonsware.cwac.camera.PictureTransaction;
import com.commonsware.cwac.camera.SimpleCameraHost;

public final class PayCameraHost extends SimpleCameraHost
{

    final PayCameraFragment a;

    public PayCameraHost(PayCameraFragment d1, Context context)
    {
        super(context);
        a = d1;
    }

    public final android.hardware.Camera.Parameters adjustPreviewParameters(android.hardware.Camera.Parameters parameters)
    {
        if (parameters.getMaxZoom() == 0)
        {
            PayCameraFragment.a(a).setEnabled(false);
        } else
        {
            PayCameraFragment.a(a).setMax(parameters.getMaxZoom());
            PayCameraFragment.a(a).setOnSeekBarChangeListener(a);
        }
        return super.adjustPreviewParameters(parameters);
    }

    public final void autoFocusAvailable()
    {
        PayCameraFragment.b(a).setEnabled(true);
    }

    public final void autoFocusUnavailable()
    {
        PayCameraFragment.b(a).setEnabled(false);
    }

    protected final File getPhotoPath()
    {
        return new File(((Uri)a.getActivity().getIntent().getParcelableExtra("output")).getPath());
    }

    public final float maxPictureCleanupHeapUsage()
    {
        return 0.0F;
    }

//    public final void onCameraFail(c c)
//    {
//        super.onCameraFail(c);
//        Toast.makeText(a.getActivity(), v.camera_fail, 1).show();
//    }

    public final void saveImage(PictureTransaction ab, byte abyte0[])
    {
        super.saveImage(ab, abyte0);
        a.getActivity().setResult(-1);
        a.getActivity().finish();
    }

    public final boolean useFrontFacingCamera()
    {
        return a.getArguments().getBoolean("com.commonsware.cwac.camera.demo.USE_FFC");
    }

    public final boolean useSingleShotMode()
    {
        return true;
    }
}
