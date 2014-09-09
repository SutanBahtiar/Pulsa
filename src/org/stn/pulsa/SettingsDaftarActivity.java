package org.stn.pulsa;

import java.util.ArrayList;

import org.stn.pulsa.adapter.SettingsAdapter;
import org.stn.pulsa.controller.SettingsController;
import org.stn.pulsa.model.Settings;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

public class SettingsDaftarActivity extends Fragment implements OnItemLongClickListener {

	private SettingsController controller;
	private ArrayList<Settings> list;
	private SettingsAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.settings_daftar_activity, container, false);

		ListView listView = (ListView) v.findViewById(android.R.id.list);

		controller = new SettingsController(getActivity());
		controller.open();
		list = controller.getAll();
		controller.close();

		listView.setFastScrollEnabled(true);
		listView.setOverScrollMode(AbsListView.OVER_SCROLL_IF_CONTENT_SCROLLS);
		listView.setOnItemLongClickListener(this);
		listView.setTextFilterEnabled(true);

		adapter = new SettingsAdapter(getActivity(), 0, list);
		listView.setAdapter(adapter);

		return v;
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		return false;
	}

}
