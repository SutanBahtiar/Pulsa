package org.stn.pulsa.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.stn.pulsa.R;
import org.stn.pulsa.helper.DateFormatHelper;
import org.stn.pulsa.helper.DoubleFormatHelper;
import org.stn.pulsa.model.Pembelian;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class PembelianAdapter extends ArrayAdapter<Pembelian> {

	private static LayoutInflater inflater = null;
	private ArrayList<Pembelian> arrayList;
	private List<Pembelian> listPembelian;

	public PembelianAdapter(Context context, int textViewResourceId, List<Pembelian> objects) {
		super(context, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
		this.listPembelian = objects;
		this.arrayList = new ArrayList<Pembelian>();
		this.arrayList.addAll(listPembelian);
		inflater = LayoutInflater.from(context);
	}

	@Override
	public View getView(int position, View v, ViewGroup parent) {
		// TODO Auto-generated method stub

		ViewHolder holder;

		if (v == null) {
			v = inflater.inflate(R.layout.row_pembelian, null);
			holder = new ViewHolder();

			holder.tanggal = (TextView) v.findViewById(R.id.pembelianTanggal);
			holder.saldo = (TextView) v.findViewById(R.id.pembelianSaldo);

			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}

		holder.tanggal.setText(DateFormatHelper.toFormat(listPembelian.get(position).getTanggal()));
		holder.saldo.setText(DoubleFormatHelper.format(listPembelian.get(position).getSaldo()));

		return v;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listPembelian.size();
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public Pembelian getItem(int position) {
		// TODO Auto-generated method stub
		return listPembelian.get(position);
	}

	public void filter(String string) {
		string = string.toLowerCase(Locale.getDefault());
		listPembelian.clear();
		if (string.length() == 0) {
			listPembelian.addAll(arrayList);
		} else {
			for (Pembelian p : arrayList) {
				if (p.getTanggal().toLowerCase(Locale.getDefault()).contains(string) || p.getSaldo().toLowerCase(Locale.getDefault()).contains(string)) {
					listPembelian.add(p);
				}
			}
		}
		notifyDataSetChanged();
	}

	public static class ViewHolder {
		public TextView tanggal;
		public TextView saldo;
	}

}
