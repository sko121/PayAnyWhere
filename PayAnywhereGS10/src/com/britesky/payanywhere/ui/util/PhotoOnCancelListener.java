package com.britesky.payanywhere.ui.util;

import com.britesky.payanywhere.ui.new_pos.ManualItemActivity;

import android.app.Activity;
import android.content.DialogInterface;

// Referenced classes of package com.nabancard.ui.util:
//            am

final class PhotoOnCancelListener
    implements android.content.DialogInterface.OnCancelListener
{

    final Activity a;

    PhotoOnCancelListener(Activity activity)
    {
        super();
        a = activity;
    }

    public final void onCancel(DialogInterface dialoginterface)
    {
        ImageUtil.launcherClosed();
        if (a.getClass() == ManualItemActivity.class)
        {
//            ((ManualItemActivity)a).resetImageFile();
        }
    }
}
