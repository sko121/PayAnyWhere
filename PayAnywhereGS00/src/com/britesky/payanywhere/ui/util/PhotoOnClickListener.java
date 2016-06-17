package com.britesky.payanywhere.ui.util;

import android.app.Activity;
import android.content.DialogInterface;
import com.britesky.payanywhere.ui.new_pos.InventoryEditActivity;
//import com.britesky.payanywhere.ui.new_pos.InventoryEditActivity;
import com.britesky.payanywhere.ui.new_pos.ManualItemActivity;

// Referenced classes of package com.nabancard.ui.util:
//            am

final class PhotoOnClickListener
    implements android.content.DialogInterface.OnClickListener {

    final Activity a;

    PhotoOnClickListener(Activity activity) {
        super();
        a = activity;
    }

    public final void onClick(DialogInterface dialoginterface, int i) {
        android.net.Uri uri;
        if (a.getClass() == ManualItemActivity.class) {
            uri = ((ManualItemActivity)a).overwriteImageFile();
        }
        else {
            Class class1 = a.getClass();
            uri = null;
            if (class1 == InventoryEditActivity.class) {
                uri = ((InventoryEditActivity)a).overwriteImageFile();
            }
        }
        switch (i)
        {
        default:
            return;

        case 0: // '\0'
            ImageUtil.startCamera(a, uri);
            return;

        case 1: // '\001'
            ImageUtil.startGallery(a, uri);
            break;
        }
    }
}
