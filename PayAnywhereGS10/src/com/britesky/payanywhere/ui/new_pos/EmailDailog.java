package com.britesky.payanywhere.ui.new_pos;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;






import android.widget.TextView;

//import com.nabancard.api.CustomerApi;
//import com.nabancard.ui.p;
//import com.nabancard.ui.r;
import java.util.ArrayList;
import java.util.List;

import com.britesky.payanywhere.R;

public final class EmailDailog extends DialogFragment {
	private Button aA;
	private AutoCompleteTextView ai;
	private AutoCompleteTextView aj;
	private AutoCompleteTextView ak;
	private AutoCompleteTextView al;
//	private AutoCompleteTextView am;
//	private AutoCompleteTextView an;
//	private AutoCompleteTextView ao;
//	private AutoCompleteTextView ap;
	private ImageView aq;
	private ImageView ar;
	private ImageView as;
	private ImageView at;
//	private ImageView au;
//	private ImageView av;
//	private ImageView aw;
//	private ImageView ax;
	private ArrayList<String> ay;
	private Button az;

	private void a(View paramView) {
		this.ai = ((AutoCompleteTextView) paramView
				.findViewById(R.id.emailAutoComplete1));
		this.aj = ((AutoCompleteTextView) paramView
				.findViewById(R.id.emailAutoComplete2));
		this.ak = ((AutoCompleteTextView) paramView
				.findViewById(R.id.emailAutoComplete3));
		this.al = ((AutoCompleteTextView) paramView
				.findViewById(R.id.emailAutoComplete4));
		
		this.aq = ((ImageView) paramView.findViewById(R.id.emailClear1));
		this.ar = ((ImageView) paramView.findViewById(R.id.emailClear2));
		this.as = ((ImageView) paramView.findViewById(R.id.emailClear3));
		this.at = ((ImageView) paramView.findViewById(R.id.emailClear4));

		this.az = ((Button) paramView.findViewById(R.id.btnSave));
		this.aA = ((Button) paramView.findViewById(R.id.btnCancel));
//		this.ai.setAdapter(new gb(this, CustomerApi.getAllEmails()));
//		this.aj.setAdapter(new gb(this, CustomerApi.getAllEmails()));
//		this.ak.setAdapter(new gb(this, CustomerApi.getAllEmails()));
//		this.al.setAdapter(new gb(this, CustomerApi.getAllEmails()));
//		this.am.setAdapter(new gb(this, CustomerApi.getAllEmails()));
//		this.an.setAdapter(new gb(this, CustomerApi.getAllEmails()));
//		this.ao.setAdapter(new gb(this, CustomerApi.getAllEmails()));
//		this.ap.setAdapter(new gb(this, CustomerApi.getAllEmails()));
		this.ai.setOverScrollMode(2);
		this.ai.setThreshold(0);
		this.ai.setInputType(33);
		this.aj.setOverScrollMode(2);
		this.aj.setThreshold(0);
		this.aj.setInputType(33);
		this.ak.setOverScrollMode(2);
		this.ak.setThreshold(0);
		this.ak.setInputType(33);
		this.al.setOverScrollMode(2);
		this.al.setThreshold(0);
		this.al.setInputType(33);
//		this.am.setOverScrollMode(2);
//		this.am.setThreshold(0);
//		this.am.setInputType(33);
//		this.an.setOverScrollMode(2);
//		this.an.setThreshold(0);
//		this.an.setInputType(33);
//		this.ao.setOverScrollMode(2);
//		this.ao.setThreshold(0);
//		this.ao.setInputType(33);
//		this.ap.setOverScrollMode(2);
//		this.ap.setThreshold(0);
//		this.ap.setInputType(33);
		if (this.ay.size() > 1)
			this.ai.setText((CharSequence) this.ay.get(1));
		if (this.ay.size() > 2)
			this.aj.setText((CharSequence) this.ay.get(2));
		if (this.ay.size() > 3)
			this.ak.setText((CharSequence) this.ay.get(3));
		if (this.ay.size() > 4)
			this.al.setText((CharSequence) this.ay.get(4));
		
	}

//	private boolean a(AutoCompleteTextView paramAutoCompleteTextView) {
//		return ((ReceiptActivity) getActivity())
//				.validateEmail(paramAutoCompleteTextView);
//	}
//
//	private void b(AutoCompleteTextView paramAutoCompleteTextView) {
//		paramAutoCompleteTextView.addTextChangedListener(new fp(this,
//				paramAutoCompleteTextView));
//	}
//
//	private void c(AutoCompleteTextView paramAutoCompleteTextView) {
//		paramAutoCompleteTextView.setOnItemClickListener(new ft(this,
//				paramAutoCompleteTextView));
//	}
//
//	private boolean k() {
//		if ((!a(this.ai)) || (!a(this.aj)) || (!a(this.ak)) || (!a(this.al))
//				|| (!a(this.am)) || (!a(this.an)) || (!a(this.ao))
//				|| (!a(this.ap)))
//			;
//		for (boolean bool = false;; bool = true)
//			return bool;
//	}
//
//	private void l() {
//		if (this.ay.size() > 1)
//			this.ai.setText((CharSequence) this.ay.get(1));
//		if (this.ay.size() > 2)
//			this.aj.setText((CharSequence) this.ay.get(2));
//		if (this.ay.size() > 3)
//			this.ak.setText((CharSequence) this.ay.get(3));
//		if (this.ay.size() > 4)
//			this.al.setText((CharSequence) this.ay.get(4));
//		if (this.ay.size() > 5)
//			this.am.setText((CharSequence) this.ay.get(5));
//		if (this.ay.size() > 6)
//			this.an.setText((CharSequence) this.ay.get(6));
//		if (this.ay.size() > 7)
//			this.ao.setText((CharSequence) this.ay.get(7));
//		if (this.ay.size() > 8)
//			this.ap.setText((CharSequence) this.ay.get(8));
//	}
//
//	private void m() {
//		String str = (String) this.ay.get(0);
//		this.ay = new ArrayList();
//		this.ay.add(str);
//		if (!this.ai.getText().toString().isEmpty())
//			this.ay.add(this.ai.getText().toString());
//		if (!this.aj.getText().toString().isEmpty())
//			this.ay.add(this.aj.getText().toString());
//		if (!this.ak.getText().toString().isEmpty())
//			this.ay.add(this.ak.getText().toString());
//		if (!this.al.getText().toString().isEmpty())
//			this.ay.add(this.al.getText().toString());
//		if (!this.am.getText().toString().isEmpty())
//			this.ay.add(this.am.getText().toString());
//		if (!this.an.getText().toString().isEmpty())
//			this.ay.add(this.an.getText().toString());
//		if (!this.ao.getText().toString().isEmpty())
//			this.ay.add(this.ao.getText().toString());
//		if (!this.ap.getText().toString().isEmpty())
//			this.ay.add(this.ap.getText().toString());
//	}
//
//	private void n() {
//		b(this.ai);
//		b(this.aj);
//		b(this.ak);
//		b(this.al);
//		b(this.am);
//		b(this.an);
//		b(this.ao);
//		b(this.ap);
//		c(this.ai);
//		c(this.aj);
//		c(this.ak);
//		c(this.al);
//		c(this.am);
//		c(this.an);
//		c(this.ao);
//		c(this.ap);
//		this.aq.setOnClickListener(new fu(this));
//		this.ar.setOnClickListener(new fv(this));
//		this.as.setOnClickListener(new fw(this));
//		this.at.setOnClickListener(new fx(this));
//		this.au.setOnClickListener(new fy(this));
//		this.av.setOnClickListener(new fz(this));
//		this.aw.setOnClickListener(new ga(this));
//		this.ax.setOnClickListener(new fq(this));
//		this.az.setOnClickListener(new fr(this));
//		this.aA.setOnClickListener(new fs(this));
//	}
//
//	public final Dialog onCreateDialog(Bundle paramBundle) {
//		setRetainInstance(true);
//		View localView = getActivity().getLayoutInflater().inflate(
//				R.layout.dialog_fragment_receipt_email, null);
//		this.ai = ((AutoCompleteTextView) localView
//				.findViewById(p.emailAutoComplete1));
//		this.aj = ((AutoCompleteTextView) localView
//				.findViewById(p.emailAutoComplete2));
//		this.ak = ((AutoCompleteTextView) localView
//				.findViewById(p.emailAutoComplete3));
//		this.al = ((AutoCompleteTextView) localView
//				.findViewById(p.emailAutoComplete4));
//		this.am = ((AutoCompleteTextView) localView
//				.findViewById(p.emailAutoComplete5));
//		this.an = ((AutoCompleteTextView) localView
//				.findViewById(p.emailAutoComplete6));
//		this.ao = ((AutoCompleteTextView) localView
//				.findViewById(p.emailAutoComplete7));
//		this.ap = ((AutoCompleteTextView) localView
//				.findViewById(p.emailAutoComplete8));
//		this.aq = ((ImageView) localView.findViewById(p.emailClear1));
//		this.ar = ((ImageView) localView.findViewById(p.emailClear2));
//		this.as = ((ImageView) localView.findViewById(p.emailClear3));
//		this.at = ((ImageView) localView.findViewById(p.emailClear4));
//		this.au = ((ImageView) localView.findViewById(p.emailClear5));
//		this.av = ((ImageView) localView.findViewById(p.emailClear6));
//		this.aw = ((ImageView) localView.findViewById(p.emailClear7));
//		this.ax = ((ImageView) localView.findViewById(p.emailClear8));
//		this.az = ((Button) localView.findViewById(p.btnSave));
//		this.aA = ((Button) localView.findViewById(p.btnCancel));
//		this.ai.setAdapter(new gb(this, CustomerApi.getAllEmails()));
//		this.aj.setAdapter(new gb(this, CustomerApi.getAllEmails()));
//		this.ak.setAdapter(new gb(this, CustomerApi.getAllEmails()));
//		this.al.setAdapter(new gb(this, CustomerApi.getAllEmails()));
//		this.am.setAdapter(new gb(this, CustomerApi.getAllEmails()));
//		this.an.setAdapter(new gb(this, CustomerApi.getAllEmails()));
//		this.ao.setAdapter(new gb(this, CustomerApi.getAllEmails()));
//		this.ap.setAdapter(new gb(this, CustomerApi.getAllEmails()));
//		this.ai.setOverScrollMode(2);
//		this.ai.setThreshold(0);
//		this.ai.setInputType(33);
//		this.aj.setOverScrollMode(2);
//		this.aj.setThreshold(0);
//		this.aj.setInputType(33);
//		this.ak.setOverScrollMode(2);
//		this.ak.setThreshold(0);
//		this.ak.setInputType(33);
//		this.al.setOverScrollMode(2);
//		this.al.setThreshold(0);
//		this.al.setInputType(33);
//		this.am.setOverScrollMode(2);
//		this.am.setThreshold(0);
//		this.am.setInputType(33);
//		this.an.setOverScrollMode(2);
//		this.an.setThreshold(0);
//		this.an.setInputType(33);
//		this.ao.setOverScrollMode(2);
//		this.ao.setThreshold(0);
//		this.ao.setInputType(33);
//		this.ap.setOverScrollMode(2);
//		this.ap.setThreshold(0);
//		this.ap.setInputType(33);
//		if (this.ay.size() > 1)
//			this.ai.setText((CharSequence) this.ay.get(1));
//		if (this.ay.size() > 2)
//			this.aj.setText((CharSequence) this.ay.get(2));
//		if (this.ay.size() > 3)
//			this.ak.setText((CharSequence) this.ay.get(3));
//		if (this.ay.size() > 4)
//			this.al.setText((CharSequence) this.ay.get(4));
//		if (this.ay.size() > 5)
//			this.am.setText((CharSequence) this.ay.get(5));
//		if (this.ay.size() > 6)
//			this.an.setText((CharSequence) this.ay.get(6));
//		if (this.ay.size() > 7)
//			this.ao.setText((CharSequence) this.ay.get(7));
//		if (this.ay.size() > 8)
//			this.ap.setText((CharSequence) this.ay.get(8));
//		b(this.ai);
//		b(this.aj);
//		b(this.ak);
//		b(this.al);
//		b(this.am);
//		b(this.an);
//		b(this.ao);
//		b(this.ap);
//		c(this.ai);
//		c(this.aj);
//		c(this.ak);
//		c(this.al);
//		c(this.am);
//		c(this.an);
//		c(this.ao);
//		c(this.ap);
//		this.aq.setOnClickListener(new fu(this));
//		this.ar.setOnClickListener(new fv(this));
//		this.as.setOnClickListener(new fw(this));
//		this.at.setOnClickListener(new fx(this));
//		this.au.setOnClickListener(new fy(this));
//		this.av.setOnClickListener(new fz(this));
//		this.aw.setOnClickListener(new ga(this));
//		this.ax.setOnClickListener(new fq(this));
//		this.az.setOnClickListener(new fr(this));
//		this.aA.setOnClickListener(new fs(this));
//		AlertDialog.Builder localBuilder = new AlertDialog.Builder(
//				getActivity());
//		localBuilder.setTitle("Add More Emails");
//		localBuilder.setView(localView);
//		return localBuilder.create();
//	}
//
//	public final void setEmails(ArrayList<String> paramArrayList) {
//		this.ay = paramArrayList;
//	}
//	
//	
//	
//	public final class gb extends ArrayAdapter<String>
//	  implements Filterable
//	{
//	  private List<String> b;
//	  private ArrayList<String> c;
//	  private ArrayList<String> d;
//	  private int e;
//
//	  public gb(List<String> paramList)
//	  {
//	    super(paramList.getActivity(), r.transactions_emails, localList);
//	    this.b = localList;
//	    this.e = r.transactions_emails;
//	    this.c = new ArrayList();
//	    this.d = new ArrayList(this.b);
//	  }
//
//	  public final int getCount()
//	  {
//	    return this.c.size();
//	  }
//
//	  public final Filter getFilter()
//	  {
//	    return new gc(this);
//	  }
//
//	  public final String getItem(int paramInt)
//	  {
//	    return (String)this.c.get(paramInt);
//	  }
//
//	  public final View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
//	  {
//	    if (paramView == null)
//	      paramView = LayoutInflater.from(getContext()).inflate(this.e, null);
//	    String str = (String)this.c.get(paramInt);
//	    if (str != null)
//	      ((TextView)paramView.findViewById(p.emailName)).setText(str);
//	    return paramView;
//	  }
//	  
//	  final class gc extends Filter
//	  {
//	    gc(gb paramgb)
//	    {
//	    }
//
//	    public final String convertResultToString(Object paramObject)
//	    {
//	      return paramObject.toString();
//	    }
//
//	    protected final Filter.FilterResults performFiltering(CharSequence paramCharSequence)
//	    {
//	      gb.a(this.a).clear();
//	      Filter.FilterResults localFilterResults = new Filter.FilterResults();
//	      if (paramCharSequence != null)
//	      {
//	        Iterator localIterator = gb.b(this.a).iterator();
//	        while (localIterator.hasNext())
//	        {
//	          String str = (String)localIterator.next();
//	          if (str.toString().toLowerCase().startsWith(paramCharSequence.toString().toLowerCase()))
//	            gb.a(this.a).add(str);
//	        }
//	        localFilterResults.values = gb.a(this.a);
//	        localFilterResults.count = gb.a(this.a).size();
//	      }
//	      return localFilterResults;
//	    }
//
//	    protected final void publishResults(CharSequence paramCharSequence, Filter.FilterResults paramFilterResults)
//	    {
//	      if ((paramFilterResults != null) && (paramFilterResults.count > 0))
//	        this.a.notifyDataSetChanged();
//	      while (true)
//	      {
//	        return;
//	        this.a.notifyDataSetInvalidated();
//	      }
//	    }
}