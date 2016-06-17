package com.britesky.payanywhere.ui.view;

import android.content.Context;
import android.util.AttributeSet;

import com.britesky.payanywhere.R;
import de.ankri.views.Switch;

public class SwitchOrange extends Switch
{

    public SwitchOrange(Context context)
    {
        this(context, null);
    }

    public SwitchOrange(Context context, AttributeSet attributeset)
    {
        this(context, attributeset, R.attr.switchStyle);
    }

    public SwitchOrange(Context context, AttributeSet attributeset, int i)
    {
        super(context, attributeset, i);
        setBackgroundResource(R.drawable.switch_background);
        setTrackResource(R.drawable.switch_track);
        setThumbResource(R.drawable.switch_thumb);
    }
}
