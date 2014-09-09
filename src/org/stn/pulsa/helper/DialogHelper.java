package org.stn.pulsa.helper;

import java.util.Locale;

import org.stn.pulsa.R;
import org.stn.pulsa.adapter.BaseAdapter;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

public class DialogHelper extends Dialog {

	private TextView editSearch;
	private ListView listView;
	private BaseAdapter<?> baseAdapter;

	public DialogHelper(Context context, int layoutResID) {
		super(context);
		// TODO Auto-generated constructor stub
		setContentView(layoutResID);

		editSearch = (TextView) findViewById(R.id.editSearch);
		listView = (ListView) findViewById(android.R.id.list);

	}

	public void setAdapter(BaseAdapter<?> arrayAdapter) {
		baseAdapter = arrayAdapter;
		setList();
		setSearch();
	}

	public BaseAdapter<?> getAdapter() {
		return baseAdapter;
	}

	private void setList() {
		listView.setAdapter(baseAdapter);
		listView.setOverScrollMode(AbsListView.OVER_SCROLL_IF_CONTENT_SCROLLS);
	}

	public ListView getList() {
		return listView;
	}

	private void setSearch() {
		editSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				baseAdapter.filter(s.toString().toLowerCase(Locale.getDefault()));
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});
	}

	public TextView getSearch() {
		return editSearch;
	}

}
