package org.stn.pulsa;

import java.util.Date;

import org.stn.pulsa.controller.HomeController;
import org.stn.pulsa.helper.DoubleFormatHelper;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class HomeActivity extends Fragment implements OnLongClickListener {

	private TextView saldo;
	private TextView kas;
	private TextView piutang;
	private HomeController controller;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.home_activity, container, false);

		saldo = (TextView) v.findViewById(R.id.textHomeSaldo);
		kas = (TextView) v.findViewById(R.id.textHomeKas);
		piutang = (TextView) v.findViewById(R.id.textHomePiutang);

		controller = new HomeController(getActivity());
		controller.open();

		saldo.setText(DoubleFormatHelper.formatK(controller.getSaldo(new Date())));
		kas.setText(DoubleFormatHelper.formatK(controller.getKas(new Date())));
		piutang.setText(DoubleFormatHelper.formatK(controller.getPiutang(new Date())));

		controller.close();

		kas.setOnLongClickListener(this);
		saldo.setOnLongClickListener(this);
		piutang.setOnLongClickListener(this);

		return v;
	}

	@Override
	public boolean onLongClick(View v) {
		// TODO Auto-generated method stub
		FragmentManager fragmentManager = getFragmentManager();

		// Creating a fragment transaction
		FragmentTransaction ft = fragmentManager.beginTransaction();
		switch (v.getId()) {
		case R.id.textHomeKas:
			KasActivity kasActivity = new KasActivity();

			// Adding a fragment to the fragment transaction
			ft.replace(R.id.content_frame, kasActivity);

			// Committing the transaction
			ft.commit();
			break;
		case R.id.textHomePiutang:
			PiutangDaftarActivity piutangDaftarActivity = new PiutangDaftarActivity();

			// Adding a fragment to the fragment transaction
			ft.replace(R.id.content_frame, piutangDaftarActivity);

			// Committing the transaction
			ft.commit();
			break;
		}
		return false;
	}

}
