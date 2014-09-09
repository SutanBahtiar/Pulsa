package org.stn.pulsa.adapter;

import java.util.List;
import java.util.Locale;

import org.stn.pulsa.R;
import org.stn.pulsa.model.Phone;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PhoneAdapter extends BaseAdapter<Phone> {

	public PhoneAdapter(Context context, int textViewResourceId, List<Phone> objects) {
		super(context, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View v, ViewGroup parent) {
		// TODO Auto-generated method stub

		ViewHolder holder;

		if (v == null) {
			v = getInflater().inflate(R.layout.row_phone, null);
			holder = new ViewHolder();

			holder.nama = (TextView) v.findViewById(R.id.phoneNama);
			holder.telepon = (TextView) v.findViewById(R.id.phoneTelepon);

			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}

		holder.nama.setText(getList().get(position).getNama());
		holder.telepon.setText(getList().get(position).getTelepon());

		return v;
	}

	@Override
	public void filter(String string) {
		string = string.toLowerCase(Locale.getDefault());
		getList().clear();
		if (string.length() == 0) {
			getList().addAll(getArrayList());
		} else {
			for (Phone m : getArrayList()) {
				if (m.getNama().toLowerCase(Locale.getDefault()).contains(string) || m.getTelepon().toLowerCase(Locale.getDefault()).contains(string)) {
					getList().add(m);
				}
			}
		}
		notifyDataSetChanged();
	}

	public static class ViewHolder {
		public TextView nama;
		public TextView telepon;
	}

}
