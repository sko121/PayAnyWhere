package com.britesky.payanywhere.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.text.Editable;
import android.util.AttributeSet;
import android.widget.EditText;

public class PreventCursorPositionEditText extends EditText
{

    public PreventCursorPositionEditText(Context context)
    {
        super(context);
        setLongClickable(false);
    }

    public PreventCursorPositionEditText(Context context, AttributeSet attributeset)
    {
        super(context, attributeset);
        setLongClickable(false);
    }

    public PreventCursorPositionEditText(Context context, AttributeSet attributeset, int i)
    {
        super(context, attributeset, i);
        setLongClickable(false);
    }

    protected void onDraw(Canvas canvas)
    {
        setSelection(getText().length());
        super.onDraw(canvas);
    }

    public void setSelection(int i)
    {
        super.setSelection(i);
    }

    public void setSelection(int i, int j)
    {
        super.setSelection(i, j);
    }
}
