// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.britesky.payanywhere.ui.new_pos;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.britesky.payanywhere.R;
import com.britesky.payanywhere.api.CartApi;
import com.britesky.payanywhere.api.ImageApi;
import com.britesky.payanywhere.api.Money;
import com.britesky.payanywhere.api.SaleTransactionItem;
import com.britesky.payanywhere.ui.util.ImageUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

// Referenced classes of package com.nabancard.ui.new_pos:
//            ReviewCartFragment, gz, hn, hb, 
//            hc, hd, hh, he, 
//            hl, hf, hi

final class ReviewCartAdapter extends BaseAdapter
{
    abstract class hk
    {

        protected SaleTransactionItem b;
        final ReviewCartAdapter c;

        private hk(ReviewCartAdapter ha)
        {
            super();
            c = ha;
        }

        hk(ReviewCartAdapter ha, byte byte0)
        {
            this(ha);
        }

        protected void setItem(SaleTransactionItem saletransactionitem)
        {
            b = saletransactionitem;
        }
    }
    
    final class hh extends hk implements android.view.View.OnClickListener
    {
        final ReviewCartAdapter a;

        private hh(ReviewCartAdapter ha) {
            super(ha, (byte) 0);
            a = ha;
        }

        hh(ReviewCartAdapter ha, byte byte0) {
            this(ha);
        }

        public final void onClick(View view) {
            CartApi.increaseItemQuantity(b, true);
        }
    }
    
    final class hl extends hk implements android.view.View.OnClickListener {

        final ReviewCartAdapter a;

        private hl(ReviewCartAdapter ha1) {
            super(ha1, (byte) 0);
            a = ha1;
        }

        hl(ReviewCartAdapter ha1, byte byte0) {
            this(ha1);
        }

        public final void onClick(View view) {
            if (b.getQuantity() == 1) { // && ReviewCartFragment.b(a.a) != hn.e && gt.b(a.a) != hn.d) {
//                if (b.isManualItem()) {
//                    (new android.app.AlertDialog.Builder(ha.e(a)))
//                            .setTitle(v.delete_item)
//                            .setMessage(v.click_yes_to_remove)
//                            .setCancelable(false)
//                            .setPositiveButton(v.yes, new hm(this))
//                            .setNegativeButton(0x1040000, null).show();
//                    return;
//                } else {
                    CartApi.removeFromCart(b);
                    return;
//                }
            } else {
                CartApi.decreaseItemQuantity(b, true);
                return;
            }
        }
    }
    
    final ReviewCartFragment a;
    private LinearLayout b;
    private ArrayList c;
//    private e d;
    private HashMap e;
    private Context f;
    private LayoutInflater g;

    private ReviewCartAdapter(ReviewCartFragment gt1, Context context)
    {
        super();

        a = gt1;
        c = new ArrayList();
        e = new HashMap();
        f = context;
        g = (LayoutInflater)f.getSystemService("layout_inflater");
//        d = new e(25);
        b = (LinearLayout)g.inflate(R.layout.receipt_totals_layout, null);
        ReviewCartFragment.a(a, (TextView)b.findViewById(R.id.quantityValue));
        ReviewCartFragment.b(a, (TextView)b.findViewById(R.id.subtotalValue));
        ReviewCartFragment.c(a, (TextView)b.findViewById(R.id.taxValue));
        ReviewCartFragment.d(a, (TextView)b.findViewById(R.id.discountValue));
        ReviewCartFragment.e(a, (TextView)b.findViewById(R.id.tipValue));
        ReviewCartFragment.f(a, (TextView)b.findViewById(R.id.totalValue));
        ReviewCartFragment.g(a, (TextView)b.findViewById(R.id.totalValueTransact));
//        gz.a[ReviewCartFragment.b(a).ordinal()];
//        JVM INSTR tableswitch 1 3: default 272
//    //                   1 458
//    //                   2 458
//    //                   3 525;
//           goto _L1 _L2 _L2 _L3
//_L1:
        ImageUtil.setBackgroundSafely(b.findViewById(R.id.discountRow), a.getResources().getDrawable(R.drawable.generic_well));
        if (CartApi.getTotalItemCount() > 0) // && SettingsApi.getDiscountEnabled())
        {
//            b.setOnClickListener(new hb(this));
        }
        c.addAll(CartApi.getCartItems());
        ReviewCartFragment.c(a).setVisibility(8);
//_L5:
//        if (ReviewCartFragment.b(a) != hn.d && ReviewCartFragment.b(a) != com.nabancard.ui.new_pos.hn.e && ReviewCartFragment.b(a) != hn.c && SettingsApi.getDiscountEnabled() && CartApi.getTotalItemCount() > 0)
//        {
//            b.setOnClickListener(new hc(this));
//        }
//        if (!SettingsApi.getDiscountEnabled())
//        {
//            b.findViewById(p.discountRow).setVisibility(8);
//        }
        c();
//        c.add(c.size(), b);
        return;
//_L2:
//        ((LinearLayout)b.findViewById(p.quantityRow)).setVisibility(8);
//        c.addAll(CartApi.getCartItems());
//        ReviewCartFragment.c(a).setVisibility(8);
//        b.findViewById(p.discountRow).setVisibility(8);
//        b.setOnClickListener(null);
//        continue; /* Loop/switch isn't completed */
//_L3:
//        c.addAll(SaleApi.getCurrentTransaction().getReceiptLineItems());
//        b.findViewById(p.quantityRow).setVisibility(8);
//        b.findViewById(p.transactTotalRow).setVisibility(0);
//        b.findViewById(p.totalValue).setVisibility(8);
//        b.findViewById(p.discount_gripper).setVisibility(4);
//        if (SettingsApi.getTipEnabled())
//        {
//            ((LinearLayout)b.findViewById(p.tipRow)).setVisibility(0);
//        }
//        if (SaleApi.getCurrentTransaction().getReceiptDiscountAmount().isGreaterThanZero())
//        {
//            b.findViewById(p.discountRow).setVisibility(0);
//        }
//        b.setOnClickListener(null);
//        if (true) goto _L5; else goto _L4
//_L4:
    }

    ReviewCartAdapter(ReviewCartFragment gt1, Context context, byte byte0)
    {
        this(gt1, context);
    }
//
//    private void a()
//    {
//        b = (LinearLayout)g.inflate(r.receipt_totals_layout, null);
//        ReviewCartFragment.a(a, (TextView)b.findViewById(p.quantityValue));
//        ReviewCartFragment.b(a, (TextView)b.findViewById(p.subtotalValue));
//        ReviewCartFragment.c(a, (TextView)b.findViewById(p.taxValue));
//        ReviewCartFragment.d(a, (TextView)b.findViewById(p.discountValue));
//        com.nabancard.ui.new_pos.ReviewCartFragment.e(a, (TextView)b.findViewById(p.tipValue));
//        com.nabancard.ui.new_pos.ReviewCartFragment.f(a, (TextView)b.findViewById(p.totalValue));
//        com.nabancard.ui.new_pos.ReviewCartFragment.g(a, (TextView)b.findViewById(p.totalValueTransact));
//        gz.a[ReviewCartFragment.b(a).ordinal()];
//        JVM INSTR tableswitch 1 3: default 204
//    //                   1 390
//    //                   2 390
//    //                   3 457;
//           goto _L1 _L2 _L2 _L3
//_L1:
//        am.setBackgroundSafely(b.findViewById(p.discountRow), a.getResources().getDrawable(o.generic_well));
//        if (CartApi.getTotalItemCount() > 0 && SettingsApi.getDiscountEnabled())
//        {
//            b.setOnClickListener(new hb(this));
//        }
//        c.addAll(CartApi.getCartItems());
//        ReviewCartFragment.c(a).setVisibility(8);
//_L5:
//        if (ReviewCartFragment.b(a) != hn.d && ReviewCartFragment.b(a) != com.nabancard.ui.new_pos.hn.e && ReviewCartFragment.b(a) != hn.c && SettingsApi.getDiscountEnabled() && CartApi.getTotalItemCount() > 0)
//        {
//            b.setOnClickListener(new hc(this));
//        }
//        if (!SettingsApi.getDiscountEnabled())
//        {
//            b.findViewById(p.discountRow).setVisibility(8);
//        }
//        c();
//        c.add(c.size(), b);
//        return;
//_L2:
//        ((LinearLayout)b.findViewById(p.quantityRow)).setVisibility(8);
//        c.addAll(CartApi.getCartItems());
//        ReviewCartFragment.c(a).setVisibility(8);
//        b.findViewById(p.discountRow).setVisibility(8);
//        b.setOnClickListener(null);
//        continue; /* Loop/switch isn't completed */
//_L3:
//        c.addAll(SaleApi.getCurrentTransaction().getReceiptLineItems());
//        b.findViewById(p.quantityRow).setVisibility(8);
//        b.findViewById(p.transactTotalRow).setVisibility(0);
//        b.findViewById(p.totalValue).setVisibility(8);
//        b.findViewById(p.discount_gripper).setVisibility(4);
//        if (SettingsApi.getTipEnabled())
//        {
//            ((LinearLayout)b.findViewById(p.tipRow)).setVisibility(0);
//        }
//        if (SaleApi.getCurrentTransaction().getReceiptDiscountAmount().isGreaterThanZero())
//        {
//            b.findViewById(p.discountRow).setVisibility(0);
//        }
//        b.setOnClickListener(null);
//        if (true) goto _L5; else goto _L4
//_L4:
//    }
//
//    private void a(ImageButton imagebutton, ImageButton imagebutton1, SaleTransactionItem saletransactionitem)
//    {
//        switch (gz.a[ReviewCartFragment.b(a).ordinal()])
//        {
//        default:
//            return;
//
//        case 1: // '\001'
//        case 2: // '\002'
//            if (saletransactionitem.getQuantity() < ((Integer)e.get(saletransactionitem)).intValue())
//            {
//                imagebutton.setClickable(true);
//                imagebutton.setVisibility(0);
//            } else
//            {
//                imagebutton.setClickable(false);
//                imagebutton.setVisibility(4);
//            }
//            if (saletransactionitem.getQuantity() > 0)
//            {
//                imagebutton1.setClickable(true);
//                imagebutton1.setVisibility(0);
//                return;
//            } else
//            {
//                imagebutton1.setClickable(false);
//                imagebutton1.setVisibility(4);
//                return;
//            }
//
//        case 3: // '\003'
//            imagebutton.setClickable(false);
//            imagebutton.setVisibility(8);
//            imagebutton1.setClickable(false);
//            imagebutton1.setVisibility(8);
//            return;
//
//        case 4: // '\004'
//        case 5: // '\005'
//            imagebutton.setClickable(true);
//            break;
//        }
//        if (saletransactionitem.getQuantity() > 0)
//        {
//            imagebutton1.setClickable(true);
//            imagebutton1.setVisibility(0);
//            return;
//        } else
//        {
//            imagebutton1.setClickable(false);
//            imagebutton1.setVisibility(4);
//            return;
//        }
//    }
//
    static void a(ReviewCartAdapter ha1)
    {
        if (ha1.a.getSherlockActivity() != null)
        {
//            ha1.a.getSherlockActivity().runOnUiThread(new hd(ha1));
            ReviewCartAdapter.b(ha1).clear();
            ReviewCartAdapter.b(ha1).addAll(CartApi.getCartItems());
            ReviewCartAdapter.b(ha1).add(ReviewCartAdapter.c(ha1));
            ReviewCartAdapter.d(ha1);
            ha1.notifyDataSetChanged();
        }
    }

    static ArrayList b(ReviewCartAdapter ha1)
    {
        return ha1.c;
    }

//    private void b()
//    {
//        if (a.getSherlockActivity() != null)
//        {
//            a.getSherlockActivity().runOnUiThread(new hd(this));
//        }
//    }
//
    static LinearLayout c(ReviewCartAdapter ha1)
    {
        return ha1.b;
    }

    private void c()
    {
//        switch (gz.a[ReviewCartFragment.b(a).ordinal()])
//        {
//        default:
            if (CartApi.getTotalItemCount() > 0)
            {
                b.findViewById(R.id.quantityRow).setVisibility(0);
//                if (SettingsApi.getDiscountEnabled())
//                {
//                    b.findViewById(p.discountRow).setVisibility(0);
//                }
                ReviewCartFragment.k(a).setText(Integer.toString(CartApi.getTotalItemCount()));
            } else
            {
                b.findViewById(R.id.quantityRow).setVisibility(8);
            }
            if (CartApi.getSubtotal().toString().length() > 12)
            {
                ReviewCartFragment.d(a).setTextAppearance(a.getActivity(), 0x1010042);
            }
            ReviewCartFragment.d(a).setText(CartApi.getSubtotal().toString());
//            ReviewCartFragment.e(a).setText(CartApi.getTax().toString());
            ReviewCartFragment.f(a).setText(CartApi.getDiscountAmount().toString());
            ReviewCartFragment.g(a).setText(CartApi.getTotal().toString());
            return;

//        case 2: // '\002'
//            ReviewCartFragment.d(a).setText(CartApi.getSubtotal().toString());
//            ReviewCartFragment.e(a).setText(CartApi.getTax().toString());
//            ReviewCartFragment.f(a).setText(CartApi.getRefundableAmount().toString());
//            ReviewCartFragment.g(a).setText(CartApi.getRefundTotal().toString());
//            return;
//
//        case 1: // '\001'
//            ReviewCartFragment.d(a).setText(CartApi.getSubtotal().toString());
//            ReviewCartFragment.e(a).setText(CartApi.getTax().toString());
//            ReviewCartFragment.f(a).setText(CartApi.getPreAuthCompletableAmount().toString());
//            ReviewCartFragment.g(a).setText(CartApi.getPreauthTotal().toString());
//            return;
//
//        case 3: // '\003'
//            break;
//        }
//        if (SaleApi.getCurrentTransaction().getReceiptSubtotalAmount().isGreaterThanZero())
//        {
//            ReviewCartFragment.d(a).setText(SaleApi.getCurrentTransaction().getReceiptSubtotalAmount().toString());
//            com.nabancard.ui.new_pos.ReviewCartFragment.e(a).setText(SaleApi.getCurrentTransaction().getReceiptTaxAmount().toString());
//            com.nabancard.ui.new_pos.ReviewCartFragment.f(a).setText(SaleApi.getCurrentTransaction().getReceiptDiscountAmount().toString());
//            ReviewCartFragment.i(a).setText(ReviewCartFragment.h(a).toString());
//            ReviewCartFragment.j(a).setText(SaleApi.getCurrentTransaction().getReceiptTotalAmount().toString());
//            return;
//        } else
//        {
//            ReviewCartFragment.d(a).setText(SaleApi.getCurrentTransaction().getPaidAmount().toString());
//            com.nabancard.ui.new_pos.ReviewCartFragment.e(a).setText(SaleApi.getCurrentTransaction().getReceiptTaxAmount().toString());
//            com.nabancard.ui.new_pos.ReviewCartFragment.f(a).setText(SaleApi.getCurrentTransaction().getReceiptDiscountAmount().toString());
//            ReviewCartFragment.i(a).setText(SaleApi.getCurrentTransaction().getReceiptTipAmount().toString());
//            ReviewCartFragment.j(a).setText(SaleApi.getCurrentTransaction().getTotalAmount().toFormattedString());
//            return;
//        }
    }
//
//    private View d()
//    {
//        return g.inflate(r.review_cart_item, null);
//    }

    static void d(ReviewCartAdapter ha1)
    {
        ha1.c();
    }

//    static Context e(ReviewCartAdapter ha1)
//    {
//        return ha1.f;
//    }
//
    public final int getCount()
    {
        return c.size();
    }

    public final Object getItem(int i)
    {
        return c.get(i);
    }

    public final long getItemId(int i)
    {
        return (long)i;
    }

    public final View getView(int i, View view, ViewGroup viewgroup)
    {
        View view2;
        SaleTransactionItem saletransactionitem;
        ImageButton imagebutton;
        ImageButton imagebutton1;
        TextView textview;
        ImageView imageview;
        TextView textview1;
//        hi hi1;
        if (!(c.get(i) instanceof SaleTransactionItem))
        {
            b.setTag(null);
            return b;
        }
        View view1 = null; // (View)d.get(Integer.valueOf(i));
        if (view1 == null)
        {
            if (view == null || view.getTag() == null) {
                view1 = g.inflate(R.layout.review_cart_item, null);
//            d.put(Integer.valueOf(i), view1);
            } else {
                view1 = view;
            }
        }
        view2 = view1;
        saletransactionitem = (SaleTransactionItem)c.get(i);
        imagebutton = (ImageButton)view2.findViewById(R.id.quantityAddReview);
        hh hh1 = new hh(this, (byte)0);
        hh1.setItem(saletransactionitem);
        imagebutton.setOnClickListener(hh1); //(new he(this, saletransactionitem));
        imagebutton1 = (ImageButton)view2.findViewById(R.id.quantitySubtractReview);
        hl hl1 = new hl(this, (byte)0);
        hl1.setItem(saletransactionitem);
        imagebutton1.setOnClickListener(hl1); //(new hf(this, saletransactionitem));
        textview = (TextView)view2.findViewById(R.id.reviewname);
        imageview = (ImageView)view2.findViewById(R.id.reviewimage);
        textview1 = (TextView)view2.findViewById(R.id.taxedPrice);
//        hi1 = new hi(this, (byte)0);
//        hh1.setItem(saletransactionitem);
//        hl1.setItem(saletransactionitem);
//        hi1.setItem(saletransactionitem);
//        gz.a[ReviewCartFragment.b(a).ordinal()];
//        JVM INSTR tableswitch 1 5: default 280
    //                   1 453
    //                   2 453
    //                   3 541
    //                   4 570
    //                   5 570;
//           goto _L1 _L2 _L2 _L3 _L4 _L4
//_L1:
//        break; /* Loop/switch isn't completed */
//_L4:
//        break MISSING_BLOCK_LABEL_570;
//_L5:
        textview.setText(saletransactionitem.getName());
//        com.e.a.b.d d1 = ax.getDisplayOptionsRounded().extraForDownloader(az.a).build();
        String s = (String)imageview.getTag();
        int j;
        TextView textview2;
        TextView textview3;
        if (s == null || !s.equals(saletransactionitem.getImagePath()))
        {
            if (saletransactionitem.getImagePath() != null)
            {
                s = saletransactionitem.getImagePath();
                if (s.startsWith("/")) {
                    ImageLoader.getInstance().displayImage(Uri.fromFile(new File(s)).toString(), imageview);
                }
                else {
                    ImageLoader.getInstance().displayImage(saletransactionitem.getImagePath(), imageview);
                }
            } else
            {
                ImageLoader.getInstance().displayImage(null, imageview);
            }
        }
        imageview.setTag(saletransactionitem.getImagePath());
        if (saletransactionitem.getIsTaxable())
        {
            j = 0;
        } else
        {
            j = 4;
        }
        textview1.setVisibility(j);
//        view2.setOnLongClickListener(hi1);
        textview2 = (TextView)view2.findViewById(R.id.reviewpriceFixed);
        textview3 = (TextView)view2.findViewById(R.id.reviewquantity);
        textview2.setText(Money.multiply(saletransactionitem.getPrice(), saletransactionitem.getQuantity()).toString());
        textview3.setText(Integer.toString(saletransactionitem.getQuantity()));
        
        view2.setTag("transaction");
        return view2;
//_L2:
//        if (saletransactionitem.getQuantity() < ((Integer)e.get(saletransactionitem)).intValue())
//        {
//            imagebutton.setClickable(true);
//            imagebutton.setVisibility(0);
//        } else
//        {
//            imagebutton.setClickable(false);
//            imagebutton.setVisibility(4);
//        }
//        if (saletransactionitem.getQuantity() > 0)
//        {
//            imagebutton1.setClickable(true);
//            imagebutton1.setVisibility(0);
//        } else
//        {
//            imagebutton1.setClickable(false);
//            imagebutton1.setVisibility(4);
//        }
////          goto _L5
////_L3:
//        imagebutton.setClickable(false);
//        imagebutton.setVisibility(8);
//        imagebutton1.setClickable(false);
//        imagebutton1.setVisibility(8);
////          goto _L5
//        imagebutton.setClickable(true);
//        if (saletransactionitem.getQuantity() > 0)
//        {
//            imagebutton1.setClickable(true);
//            imagebutton1.setVisibility(0);
//        } else
//        {
//            imagebutton1.setClickable(false);
//            imagebutton1.setVisibility(4);
//        }
//          goto _L5
        
//        return view2;
    }
//
//    public final void populateItemLimits()
//    {
//        Iterator iterator = c.iterator();
//        do
//        {
//            if (!iterator.hasNext())
//            {
//                break;
//            }
//            Object obj = iterator.next();
//            if (obj instanceof SaleTransactionItem)
//            {
//                e.put((SaleTransactionItem)obj, Integer.valueOf(((SaleTransactionItem)obj).getQuantity()));
//            }
//        } while (true);
//    }
//
//    public final void updateTipTotal()
//    {
//        if (SaleApi.getCurrentTransaction().getReceiptSubtotalAmount().isGreaterThanZero())
//        {
//            ReviewCartFragment.i(a).setText(ReviewCartFragment.h(a).toString());
//            ReviewCartFragment.c(a).setText(SaleApi.getCurrentTransaction().getReceiptTotalAmount().toString());
//            ReviewCartFragment.j(a).setText(SaleApi.getCurrentTransaction().getReceiptTotalAmount().toString());
//            return;
//        } else
//        {
//            Money money = new Money(SaleApi.getCurrentTransaction().getPaidAmount().addByValue(SaleApi.getCurrentTransaction().getReceiptTipAmount()));
//            ReviewCartFragment.i(a).setText(SaleApi.getCurrentTransaction().getReceiptTipAmount().toString());
//            ReviewCartFragment.c(a).setText(SaleApi.getCurrentTransaction().getPaidAmount().addByValue(SaleApi.getCurrentTransaction().getReceiptTipAmount()).toString());
//            ReviewCartFragment.j(a).setText(money.toFormattedString());
//            return;
//        }
//    }
}
