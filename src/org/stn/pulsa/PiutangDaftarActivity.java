package org.stn.pulsa;

import java.util.ArrayList;

import org.stn.pulsa.adapter.PenjualanAdapter;
import org.stn.pulsa.controller.PenjualanController;
import org.stn.pulsa.model.Penjualan;

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

public class PiutangDaftarActivity extends Fragment implements OnItemLongClickListener {

	private PenjualanController controller;
	private ArrayList<Penjualan> list;
	private PenjualanAdapter adapter;
	private EditText editSearch;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.piutang_daftar_activity, container, false);

		editSearch = (EditText) v.findViewById(R.id.editSearch);

		controller = new PenjualanController(getActivity());
		controller.open();
		list = controller.getAllKasOrPiutang(1);
		controller.close();

		ListView listView = (ListView) v.findViewById(android.R.id.list);
		listView.setFastScrollEnabled(true);
		listView.setOverScrollMode(AbsListView.OVER_SCROLL_IF_CONTENT_SCROLLS);
		listView.setOnItemLongClickListener(this);
		listView.setTextFilterEnabled(true);

		// View header = inflater.inflate(R.layout.row_penjualan, null);
		// listView.addHeaderView(header);

		adapter = new PenjualanAdapter(getActivity(), 0, list);
		listView.setAdapter(adapter);

		editSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				PiutangDaftarActivity.this.adapter.getFilter().filter(s);
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
		Penjualan item = adapter.getItem(position);

		controller.open();
		Penjualan penjualan = controller.getData(item.getId());
		controller.close();

		Bundle bundle = new Bundle();
		bundle.putLong("id", penjualan.getId());
		bundle.putString("tanggal", penjualan.getTanggal());
		bundle.putString("nama", penjualan.getNama());
		bundle.putString("telepon", penjualan.getTelepon());
		bundle.putString("kode", penjualan.getKode());
		bundle.putString("saldo", penjualan.getSaldo());
		bundle.putString("hargaBeli", penjualan.getHargaBeli());
		bundle.putString("hargaJual", penjualan.getHargaJual());
		bundle.putString("margin", penjualan.getMargin());
		bundle.putInt("status", penjualan.getStatus());

		PenjualanUpdateActivity penjualanUpdateActivity = new PenjualanUpdateActivity();
		penjualanUpdateActivity.setArguments(bundle);

		FragmentManager fragmentManager = getFragmentManager();

		// Creating a fragment transaction
		FragmentTransaction ft = fragmentManager.beginTransaction();

		// Adding a fragment to the fragment transaction
		ft.replace(R.id.content_frame, penjualanUpdateActivity);

		// Committing the transaction
		ft.commit();

		return true;

	}
}
