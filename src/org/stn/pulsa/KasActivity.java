package org.stn.pulsa;

import java.util.Date;

import org.stn.pulsa.controller.HomeController;
import org.stn.pulsa.helper.DoubleFormatHelper;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class KasActivity extends Fragment {

	private TextView kas;
	private TextView margin;
	private TextView pokok;
	private HomeController controller;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.kas_activity, container, false);

		kas = (TextView) v.findViewById(R.id.txtKasKas);
		margin = (TextView) v.findViewById(R.id.txtKasMargin);
		pokok = (TextView) v.findViewById(R.id.txtKasPokok);

		controller = new HomeController(getActivity());
		controller.open();

		Double dKas = controller.getKas(new Date());
		Double dMargin = controller.getMarginKas(new Date());
		Double dPokok = dKas - dMargin;

		controller.close();

		kas.setText(DoubleFormatHelper.format(dKas));
		margin.setText(DoubleFormatHelper.format(dMargin));
		pokok.setText(DoubleFormatHelper.format(dPokok));

		return v;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.kasInput:
//			Intent i = new Intent(this, KasInputActivity.class);
//			startActivity(i);
//			break;
		case R.id.menuHome:
//			Intent i2 = new Intent(this, HomeActivity.class);
//			startActivity(i2);
//			break;
		}
		return true;
	}
}
