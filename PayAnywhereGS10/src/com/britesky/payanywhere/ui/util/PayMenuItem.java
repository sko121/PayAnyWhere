package com.britesky.payanywhere.ui.util;

import android.graphics.drawable.Drawable;

public final class PayMenuItem {

    public static final int a = 0;
    public static final int b = 1;
    public static final int c = 2;
    public String d;
    public int e;
    public int f;
    public Drawable g;

    public PayMenuItem(String s, int i, int j)
    {
        d = null;
        e = 0;
        f = 0;
        d = s;
        e = i;
        f = j;
    }

    public PayMenuItem(String s, Drawable drawable, int i)
    {
        d = null;
        e = 0;
        f = 0;
        d = s;
        g = drawable;
        f = i;
    }
}
