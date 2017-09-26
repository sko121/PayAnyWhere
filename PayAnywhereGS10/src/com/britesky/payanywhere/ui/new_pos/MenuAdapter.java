package com.britesky.payanywhere.ui.new_pos;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.britesky.payanywhere.R;
import com.britesky.payanywhere.ui.util.PayMenuItem;

import java.util.ArrayList;
import java.util.Locale;

public class MenuAdapter extends BaseAdapter
{

    private final LayoutInflater a;
    private final ArrayList b = new ArrayList();
    private Context c;

    public MenuAdapter(Context context) {
        c = context;
        a = (LayoutInflater)context.getSystemService("layout_inflater");
    }

    public final void addDivider() {
        b.add(".");
        notifyDataSetChanged();
    }

    public final void addItem(Object obj) {
        b.add(obj);
        notifyDataSetChanged();
    }

    public final void clear() {
        b.clear();
    }

    public final int getCount() {
        return b.size();
    }

    public final Object getItem(int i) {
        return b.get(i);
    }

    public final long getItemId(int i) {
        return (long)i;
    }

    public final View getView(int i, View view, ViewGroup viewgroup) {
        Object obj = b.get(i);
        
        if (!(obj instanceof PayMenuItem)) {
            return a.inflate(R.layout.divider_layout, null);
        }

        View view1;
        TextView textview;
        ImageView imageview;
        PayMenuItem at1;
        view1 = a.inflate(R.layout.sliding_menu_list_item, null);
        textview = (TextView)view1.findViewById(R.id.menu_text);
        imageview = (ImageView)view1.findViewById(R.id.menu_image);
        at1 = (PayMenuItem)obj;
        
        textview.setText(at1.d);
        imageview.setImageResource(at1.e);
        view1.setTag(obj);
        return view1;
        
//        JVM INSTR tableswitch 0 2: default 96
// //                   0 159
// //                   1 180
// //                   2 201;
//        goto _L3 _L4 _L5 _L6
//_L3:
//        textview.setText(at1.d);
//        if (Locale.getDefault().getDisplayLanguage().equals("es")) {
//            textview.setTextSize(1.684282E+07F);
//        }
//        imageview.setColorFilter(bj.getBrandColor(c));
//        imageview.setImageResource(at1.e);
//        view1.setTag(obj);
//        return view1;
//_L4:
//         textview.setTextColor(c.getResources().getColor(m.white));
//        continue; /* Loop/switch isn't completed */
//_L5:
//        textview.setTextColor(c.getResources().getColor(m.gray));
//        continue; /* Loop/switch isn't completed */
//_L6:
//         textview.setTextColor(bj.getBrandColor(c));
//        if (true) goto _L3; else goto _L2
//_L2:
//        return a.inflate(r.divider_layout, null);
    }

    public final void removeItem(Object obj) {
        b.remove(obj);
        notifyDataSetChanged();
    }

    public final void replaceItem(Object obj, Object obj1) {
        b.add(b.indexOf(obj), obj1);
        b.remove(obj);
        notifyDataSetChanged();
    }

    public final void setItemList(ArrayList arraylist) {
        b.clear();
        b.addAll(arraylist);
        notifyDataSetChanged();
    }

}
