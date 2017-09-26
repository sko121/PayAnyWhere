package com.britesky.payanywhere.ui.new_pos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.britesky.payanywhere.R;
import com.britesky.payanywhere.api.Category;
import com.britesky.payanywhere.ui.util.GridViewAdapter;
import com.britesky.payanywhere.api.CartApi;

public class InventoryFragment extends SherlockFragment 
    implements CartApi.OnCartChangedListener {

    private GridViewAdapter mGridViewAdapter;
    private Category mCategory;
    private int mModes;

    public InventoryFragment() {
        super();

        mModes = 0;
    }

    @Override
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setHasOptionsMenu(true);
        mGridViewAdapter = new GridViewAdapter(getSherlockActivity(), mModes, null, mCategory);
        CartApi.addOnCartChangedListener(this);
    }
    
    @Override
    public final void onDestroy() {
        super.onDestroy();
        CartApi.removeOnCartChangedListener(this);
    }

    @Override
    public final void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (mModes == 1) {
            inflater.inflate(R.menu.actionbar_sell, menu);
        } else {
            inflater.inflate(R.menu.actionbar_inventory, menu);
        }
    }

    @Override
    public final boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    public final View onCreateView(LayoutInflater layoutinflater,
            ViewGroup viewgroup, Bundle bundle) {
        View view = layoutinflater.inflate(R.layout.inventory_fragment,
                viewgroup, false);

        GridView gridView = (GridView) view.findViewById(R.id.gridview);
        gridView.setNumColumns(1);
        gridView.setAdapter(mGridViewAdapter);

        return view;
    }

    public final void setCategory(Category category) {
        mCategory = category;
        if (mGridViewAdapter != null) {
            mGridViewAdapter.setCategory(category);
            mGridViewAdapter.refresh();
        }
    }

    public final void setModes(int i1) {
        mModes = i1;
    }

    public final void refresh() {
        if (mGridViewAdapter != null) {
            mGridViewAdapter.refresh();
        }
    }

    @Override
    public void onCartChanged() {
        refresh();
    }

}
