package org.stn.pulsa;

import java.util.ArrayList;
import java.util.Locale;

import org.stn.pulsa.adapter.PembelianAdapter;
import org.stn.pulsa.controller.PembelianController;
import org.stn.pulsa.model.Pembelian;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ListView;

public class PembelianDaftarActivity extends Fragment implements OnItemLongClickListener {

	private PembelianController controller;
	private ArrayList<Pembelian> list;
	private PembelianAdapter adapter;
	private EditText editSearch;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.pembelian_daftar_activity, container, false);

		editSearch = (EditText) v.findViewById(R.id.editSearch);
		ListView listView = (ListView) v.findViewById(android.R.id.list);

		controller = new PembelianController(getActivity());
		controller.open();
		list = controller.getAll();
		controller.close();

		listView.setFastScrollEnabled(true);
		listView.setOverScrollMode(AbsListView.OVER_SCROLL_IF_CONTENT_SCROLLS);
		listView.setOnItemLongClickListener(this);
		listView.setTextFilterEnabled(true);

		adapter = new PembelianAdapter(getActivity(), 0, list);
		listView.setAdapter(adapter);

		editSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				adapter.filter(s.toString().toLowerCase(Locale.getDefault()));
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

		return v;
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		FragmentManager fragmentManager = getFragmentManager();

		// Creating a fragment transaction
		FragmentTransaction ft = fragmentManager.beginTransaction();

		Pembelian pembelian = adapter.getItem(position);

		Bundle bundle = new Bundle();
		bundle.putLong("id", pembelian.getId());
		bundle.putString("tanggal", pembelian.getTanggal());
		bundle.putString("kas", pembelian.getKas());
		bundle.putString("saldo", pembelian.getSaldo());

		PembelianUpdateActivity pembelianUpdateActivity = new PembelianUpdateActivity();
		pembelianUpdateActivity.setArguments(bundle);

		// Adding a fragment to the fragment transaction
		ft.replace(R.id.content_frame, pembelianUpdateActivity);

		// Committing the transaction
		ft.commit();

		return true;
	}

}
