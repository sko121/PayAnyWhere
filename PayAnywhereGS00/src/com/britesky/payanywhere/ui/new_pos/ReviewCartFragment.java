// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.britesky.payanywhere.ui.new_pos;

import com.actionbarsherlock.app.SherlockFragment;
import com.britesky.payanywhere.R;
import com.britesky.payanywhere.api.Money;
import com.britesky.payanywhere.api.CartApi;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

// Referenced classes of package com.nabancard.ui.new_pos:
//            hn, ReviewCartAdapter, SellActivity, SellCheckoutActivity, 
//            gv, gw, gz, gu, 
//            gy, gx

public final class ReviewCartFragment extends SherlockFragment
    implements CartApi.OnCartChangedListener
{
    
    final class gv implements android.view.View.OnClickListener {

        final ReviewCartFragment a;

        gv(ReviewCartFragment gt1) {
            super();
            a = gt1;
        }

        public final void onClick(View view) {
            if (CartApi.getTotalItemCount() > 0) {
//                a.taskStart();
                CartApi.resetCart();
                CartApi.init();
                return;
            } else {
//                Toast.makeText(a.getActivity(), v.cart_cancel_toast, 0).show();
                return;
            }
        }
    }
    
    final class gw implements View.OnClickListener {

        final ReviewCartFragment a;

        gw(ReviewCartFragment gt1) {
            super();
            a = gt1;
        }

        public final void onClick(View view) {
//            if (ReviewCartFragment.a(a)) {
//                ((SellCheckoutActivity) a.getActivity()).confirmPress();
//                return;
//            }
            if (CartApi.getTotalItemCount() > 0) {
//                if (((BaseActivity) a.getActivity()).isTablet() || ((BaseActivity) a.getActivity()).isTab7()) {
//                    Intent intent = new Intent(a.getActivity(), com / nabancard / ui / new_pos / SellCheckoutActivity);
//                    a.getActivity();
//                    if (SellActivity.q) {
//                        intent.putExtra("checkout_mode", io.b);
//                    }
//                    a.startActivity(intent);
//                    return;
//                }
                Intent intent1 = new Intent(a.getActivity(), SwipeCardActivity.class);
                String amount = f.getText().toString();
                amount = Util.removeAmountDollar(amount);          
                intent1.putExtra("amount", amount);
                a.getActivity();
//                if (SellActivity.q) {
//                    intent1.putExtra("checkout_mode", io.b);
//                }
                a.startActivity(intent1);
                
                return;
            } else {
//                Toast.makeText(a.getActivity(), v.add_items_to_cart, 1).show();
                return;
            }
        }
    }

    
    final class gu implements ViewTreeObserver.OnGlobalLayoutListener {

        final RelativeLayout a;
        final ReviewCartFragment b;

        gu(ReviewCartFragment gt, RelativeLayout relativelayout) {
            super();
            b = gt;
            a = relativelayout;
        }

        public final void onGlobalLayout() {
            if (a.getWidth() == 0) {
                return;
            } else {
                ImageView imageview = (ImageView) a.findViewById(R.id.printer);
                Bitmap bitmap = ((BitmapDrawable) imageview.getDrawable())
                        .getBitmap();
                float f = (float) bitmap.getWidth()
                        / (float) bitmap.getHeight();
                android.widget.RelativeLayout.LayoutParams layoutparams = (android.widget.RelativeLayout.LayoutParams) imageview
                        .getLayoutParams();
                layoutparams.height = (int) ((float) a.getWidth() / f);
                ImageView imageview1 = (ImageView) a
                        .findViewById(R.id.printer_back);
                Bitmap bitmap1 = ((BitmapDrawable) imageview1.getDrawable())
                        .getBitmap();
                float f1 = (float) bitmap1.getWidth()
                        / (float) bitmap1.getHeight();
                android.widget.RelativeLayout.LayoutParams layoutparams1 = (android.widget.RelativeLayout.LayoutParams) imageview1
                        .getLayoutParams();
                layoutparams1.height = (int) ((float) a.getWidth() / f1);
                layoutparams1.bottomMargin = (int) (0.78F * (float) layoutparams.height);
                ((android.widget.RelativeLayout.LayoutParams) ((LinearLayout) a
                        .findViewById(R.id.reviewcartlist_container))
                        .getLayoutParams()).bottomMargin = (int) (-0.05F * (float) layoutparams.height);
                a.requestLayout();
                a.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                return;
            }
        }
    }

    public static boolean a = false;
    public static boolean b = false;
    private boolean ai;
//    private hn c;
    private TextView d;
    private TextView e;
    private TextView f;
    private TextView g;
    private TextView h;
    private TextView i;
    private TextView j;
    private TextView k;
    private ReviewCartAdapter l;
    private Money m;
//
    public ReviewCartFragment()
    {
//        c = hn.b;
        m = new Money();
        ai = false;
    }

    static TextView a(ReviewCartFragment gt1, TextView textview)
    {
        gt1.d = textview;
        return textview;
    }

    static boolean a(ReviewCartFragment gt1)
    {
        return gt1.ai;
    }

    static TextView b(ReviewCartFragment gt1, TextView textview)
    {
        gt1.e = textview;
        return textview;
    }

//    static hn b(ReviewCartFragment gt1)
//    {
//        return gt1.c;
//    }

    static TextView c(ReviewCartFragment gt1)
    {
        return gt1.j;
    }

    static TextView c(ReviewCartFragment gt1, TextView textview)
    {
        gt1.g = textview;
        return textview;
    }

    static TextView d(ReviewCartFragment gt1)
    {
        return gt1.e;
    }

    static TextView d(ReviewCartFragment gt1, TextView textview)
    {
        gt1.h = textview;
        return textview;
    }

    static TextView e(ReviewCartFragment gt1)
    {
        return gt1.g;
    }

    static TextView e(ReviewCartFragment gt1, TextView textview)
    {
        gt1.i = textview;
        return textview;
    }

    static TextView f(ReviewCartFragment gt1)
    {
        return gt1.h;
    }

    static TextView f(ReviewCartFragment gt1, TextView textview)
    {
        gt1.j = textview;
        return textview;
    }

    static TextView g(ReviewCartFragment gt1, TextView textview)
    {
        gt1.k = textview;
        return textview;
    }

    static TextView g(ReviewCartFragment gt1)
    {
        return gt1.f;
    }

    static Money h(ReviewCartFragment gt1)
    {
        return gt1.m;
    }

    static TextView i(ReviewCartFragment gt1)
    {
        return gt1.i;
    }

    static TextView j(ReviewCartFragment gt1)
    {
        return gt1.k;
    }

    static TextView k(ReviewCartFragment gt1)
    {
        return gt1.d;
    }

//    final void a(Money money)
//    {
//        if (money == null)
//        {
//            money = new Money();
//        }
//        m = money;
//        if (l != null)
//        {
//            l.updateTipTotal();
//        }
//    }
//
//    final void a(hn hn1)
//    {
//        c = hn1;
//    }
//
//    final Money k()
//    {
//        return new Money(f.getText().toString());
//    }
//
    public final void onCartChanged()
    {
        if (CartApi.getTotalItemCount() != 0 || !(getActivity() instanceof SellActivity)) { // goto _L2; else goto _L1
            if (CartApi.getTotalItemCount() == 0) // && (getActivity() instanceof SellCheckoutActivity))
            {
                startActivity((new Intent(getActivity(), SellActivity.class)).setFlags(0x4008000));
            }
        }
        else {
            ((SellActivity)getActivity()).showPagerFragment();
        }
        
        ReviewCartAdapter.a(l);
        return;
//
//_L1:
//        ((SellActivity)getActivity()).showPagerFragment();
//_L4:
//        ReviewCartAdapter.a(l);
//        return;
//_L2:
//        if (CartApi.getTotalItemCount() == 0 && (getActivity() instanceof SellCheckoutActivity))
//        {
//            startActivity((new Intent(getActivity(), com/nabancard/ui/new_pos/SellActivity)).setFlags(0x4008000));
//        }
//        if (true) goto _L4; else goto _L3
//_L3:
    }

    public final void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setHasOptionsMenu(true);
        setRetainInstance(true);
        onViewStateRestored(bundle);
        if (bundle != null)
        {
//            b = bundle.getBoolean("cancelTransaction");
        }
    }

    public final View onCreateView(LayoutInflater layoutinflater, ViewGroup viewgroup, Bundle bundle)
    {
        if (viewgroup == null)
        {
            return null;
        }
        RelativeLayout relativelayout = (RelativeLayout)layoutinflater.inflate(R.layout.fragment_review_cart, viewgroup, false);
        ImageButton imagebutton;
        ListView listview;
//        if (c == hn.c || c == hn.e || c == hn.d)
//        {
//            relativelayout.findViewById(R.id.printer).setVisibility(8);
//            relativelayout.findViewById(R.id.printer_back).setVisibility(8);
//            relativelayout.findViewById(R.id.printer_contents).setVisibility(8);
//            android.widget.RelativeLayout.LayoutParams layoutparams = (android.widget.RelativeLayout.LayoutParams)((LinearLayout)relativelayout.findViewById(R.id.reviewcartlist_container)).getLayoutParams();
//            layoutparams.height = -1;
//            layoutparams.addRule(12);
//            ((android.widget.LinearLayout.LayoutParams)((ListView)relativelayout.findViewById(R.id.reviewcartlist)).getLayoutParams()).height = -1;
//        } else
        {
            relativelayout.getViewTreeObserver().addOnGlobalLayoutListener(new gu(this, relativelayout));
        }
        imagebutton = (ImageButton)relativelayout.findViewById(R.id.checkoutButton);
        imagebutton.setOnClickListener(new gw(this));
        ((ImageButton)relativelayout.findViewById(R.id.cancelButton)).setOnClickListener(new gv(this));
        listview = (ListView)relativelayout.findViewById(R.id.reviewcartlist);
        f = (TextView)relativelayout.findViewById(R.id.checkoutTotal);
//        f.setMaxTextSize(90F);
//        f.setMinTextSize(20F);
        l = new ReviewCartAdapter(this, getActivity(), (byte)0);
        listview.setAdapter(l);
        listview.setOverScrollMode(2);
        listview.setDivider(null);
        listview.setDividerHeight(0);
//        if (hn.c == c)
//        {
//            f.setVisibility(8);
//        } else
        {
            CartApi.addOnCartChangedListener(this);
        }
//        switch (gz.a[c.ordinal()])
//        {
//        default:
//            return relativelayout;
//
//        case 1: // '\001'
//            l.populateItemLimits();
//            return relativelayout;
//
//        case 2: // '\002'
//            l.populateItemLimits();
//            break;
//        }
//        CartApi.setQuantitiesToZero();
        return relativelayout;
    }

    public final void onDestroy()
    {
        CartApi.removeOnCartChangedListener(this);
        super.onDestroy();
    }

//    public final void onSaveInstanceState(Bundle bundle)
//    {
//        super.onSaveInstanceState(bundle);
//        bundle.putBoolean("cancelTransaction", b);
//    }
//
//    public final void setIsSellCheckOut()
//    {
//        ai = true;
//    }
//
//    public final void taskComplete()
//    {
//    }
//
//    public final void taskStart()
//    {
//        TextView textview = new TextView(getActivity());
//        textview.setText(v.app_name);
//        textview.setGravity(17);
//        textview.setTextSize(20F);
//        textview.setPadding(15, 15, 15, 15);
//        textview.setTextColor(0xff444444);
//        (new android.app.AlertDialog.Builder(getActivity())).setCustomTitle(textview).setCancelable(false).setMessage(v.cancel_transaction).setPositiveButton(v.yes, new gy(this)).setNegativeButton(v.no, new gx(this)).show();
//        b = true;
//    }

}
