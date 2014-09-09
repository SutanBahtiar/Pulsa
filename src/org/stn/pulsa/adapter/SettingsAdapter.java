package org.stn.pulsa.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.stn.pulsa.R;
import org.stn.pulsa.helper.DateFormatHelper;
import org.stn.pulsa.helper.DoubleFormatHelper;
import org.stn.pulsa.model.Settings;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SettingsAdapter extends ArrayAdapter<Settings> {

	private static LayoutInflater inflater = null;
	private ArrayList<Settings> arrayList;
	private List<Settings> listSettings;

	public SettingsAdapter(Context context, int textViewResourceId, List<Settings> objects) {
		super(context, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
		this.listSettings = objects;
		this.arrayList = new ArrayList<Settings>();
		this.arrayList.addAll(listSettings);
		inflater = LayoutInflater.from(context);
	}

	@Override
	public View getView(int position, View v, ViewGroup parent) {
		// TODO Auto-generated method stub

		ViewHolder holder;

		if (v == null) {
			v = inflater.inflate(R.layout.row_settings, null);
			holder = new ViewHolder();

			holder.tanggal = (TextView) v.findViewById(R.id.settingsTanggal);
			holder.saldo = (TextView) v.findViewById(R.id.settingsSaldo);
			holder.kas = (TextView) v.findViewById(R.id.settingsKas);
			holder.piutang = (TextView) v.findViewById(R.id.settingsPiutang);

			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}

		holder.tanggal.setText(DateFormatHelper.toFormat(listSettings.get(position).getTanggal()));
		holder.saldo.setText(DoubleFormatHelper.format(listSettings.get(position).getSaldo()));
		holder.kas.setText(DoubleFormatHelper.format(listSettings.get(position).getKas()));
		holder.piutang.setText(DoubleFormatHelper.format(listSettings.get(position).getPiutang()));

		return v;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listSettings.size();
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public Settings getItem(int position) {
		// TODO Auto-generated method stub
		return listSettings.get(position);
	}

	public void filter(String string) {
		string = string.toLowerCase(Locale.getDefault());
		listSettings.clear();
		if (string.length() == 0) {
			listSettings.addAll(arrayList);
		} else {
			for (Settings m : arrayList) {
				if (m.getTanggal().toLowerCase(Locale.getDefault()).contains(string) || m.getSaldo().toLowerCase(Locale.getDefault()).contains(string)
						|| m.getKas().toLowerCase(Locale.getDefault()).contains(string) || m.getPiutang().toLowerCase(Locale.getDefault()).contains(string)) {
					listSettings.add(m);
				}
			}
		}
		notifyDataSetChanged();
	}

	public static class ViewHolder {
		public TextView tanggal;
		public TextView saldo;
		public TextView kas;
		public TextView piutang;
	}

}
