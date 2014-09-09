package org.stn.pulsa;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class PembelianActivity extends Fragment {

	private ArrayAdapter<String> adapter;
	private String[] data = { "Daftar Pembelian", "Input Pembelian" };

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.master_activity, container, false);

		adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, data);

		ListView listView = (ListView) v.findViewById(android.R.id.list);
		listView.setFastScrollEnabled(true);
		listView.setOverScrollMode(AbsListView.OVER_SCROLL_IF_CONTENT_SCROLLS);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub

				FragmentManager fragmentManager = getFragmentManager();

				// Creating a fragment transaction
				FragmentTransaction ft = fragmentManager.beginTransaction();

				if (position == 0) {
					// Adding a fragment to the fragment transaction
					ft.replace(R.id.content_frame, new PembelianDaftarActivity());

					// Committing the transaction
					ft.commit();

				} else if (position == 1) {
					// Adding a fragment to the fragment transaction
					ft.replace(R.id.content_frame, new PembelianInputActivity());

					// Committing the transaction
					ft.commit();

				}

			}
		});
		listView.setTextFilterEnabled(true);
		listView.setAdapter(adapter);

		return v;
	}
}
