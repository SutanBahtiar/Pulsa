package org.stn.pulsa.adapter;

import java.util.List;
import java.util.Locale;

import org.stn.pulsa.R;
import org.stn.pulsa.helper.DateFormatHelper;
import org.stn.pulsa.helper.DoubleFormatHelper;
import org.stn.pulsa.model.Penjualan;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PenjualanAdapter extends BaseAdapter<Penjualan> {

	public PenjualanAdapter(Context context, int textViewResourceId, List<Penjualan> objects) {
		super(context, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View v, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;

		if (v == null) {
			v = getInflater().inflate(R.layout.row_penjualan, null);
			holder = new ViewHolder();

			holder.tanggal = (TextView) v.findViewById(R.id.penjualanTanggal);
			holder.nama = (TextView) v.findViewById(R.id.penjualanNama);
			holder.kode = (TextView) v.findViewById(R.id.penjualanKode);
			holder.hargaJual = (TextView) v.findViewById(R.id.penjualanHargaJual);

			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}

		holder.tanggal.setText(DateFormatHelper.toFormat(getList().get(position).getTanggal()));
		holder.nama.setText(getList().get(position).getNama());
		holder.kode.setText(getList().get(position).getKode());
		holder.hargaJual.setText(DoubleFormatHelper.format(getList().get(position).getHargaJual()));

		return v;
	}

	public void filter(String string) {
		string = string.toLowerCase(Locale.getDefault());
		getList().clear();
		if (string.length() == 0) {
			getList().addAll(getArrayList());
		} else {
			for (Penjualan m : getArrayList()) {
				if (m.getTanggal().toLowerCase(Locale.getDefault()).contains(string) || m.getNama().toLowerCase(Locale.getDefault()).contains(string)
						|| m.getKode().toLowerCase(Locale.getDefault()).contains(string) || m.getHargaJual().toLowerCase(Locale.getDefault()).contains(string)) {
					getList().add(m);
				}
			}
		}
		notifyDataSetChanged();
	}

	public static class ViewHolder {
		public TextView tanggal;
		public TextView nama;
		public TextView kode;
		public TextView hargaJual;
	}

}
