package org.stn.pulsa.adapter;

import java.util.List;
import java.util.Locale;

import org.stn.pulsa.R;
import org.stn.pulsa.helper.DoubleFormatHelper;
import org.stn.pulsa.model.Master;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MasterAdapter extends BaseAdapter<Master> {

	public MasterAdapter(Context context, int textViewResourceId, List<Master> objects) {
		super(context, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View v, ViewGroup parent) {
		// TODO Auto-generated method stub

		ViewHolder holder;

		if (v == null) {
			v = getInflater().inflate(R.layout.row_master, null);
			holder = new ViewHolder();

			holder.kode = (TextView) v.findViewById(R.id.kode);
			holder.saldo = (TextView) v.findViewById(R.id.saldo);
			holder.hargaBeli = (TextView) v.findViewById(R.id.hargaBeli);
			holder.hargaJual = (TextView) v.findViewById(R.id.hargaJual);

			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}

		holder.kode.setText(getList().get(position).getKode());
		holder.saldo.setText(DoubleFormatHelper.format(getList().get(position).getSaldo()));
		holder.hargaBeli.setText(DoubleFormatHelper.format(getList().get(position).getHargaBeli()));
		holder.hargaJual.setText(DoubleFormatHelper.format(getList().get(position).getHargaJual()));

		return v;
	}

	@Override
	public void filter(String string) {
		string = string.toLowerCase(Locale.getDefault());
		getList().clear();
		if (string.length() == 0) {
			getList().addAll(getArrayList());
		} else {
			for (Master m : getArrayList()) {
				if (m.getKode().toLowerCase(Locale.getDefault()).contains(string) || m.getSaldo().toLowerCase(Locale.getDefault()).contains(string)
						|| m.getHargaBeli().toLowerCase(Locale.getDefault()).contains(string) || m.getHargaJual().toLowerCase(Locale.getDefault()).contains(string)) {
					getList().add(m);
				}
			}
		}
		notifyDataSetChanged();
	}

	public static class ViewHolder {
		public TextView kode;
		public TextView saldo;
		public TextView hargaBeli;
		public TextView hargaJual;
	}

}
