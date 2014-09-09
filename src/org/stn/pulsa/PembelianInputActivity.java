package org.stn.pulsa;

import java.util.Calendar;
import java.util.Date;

import org.stn.pulsa.controller.HomeController;
import org.stn.pulsa.controller.PembelianController;
import org.stn.pulsa.helper.AlertDialogHelper;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

public class PembelianInputActivity extends Fragment implements OnClickListener {

	private EditText tanggal;
	private EditText saldo;
	private EditText kas;
	private Button btnOK;
	private Button btnCancel;
	private DatePickerDialog datePickerDialog;
	private PembelianController controller;
	private HomeController homeController;
	private String tglFormat;
	private String strKas;
	private Double dKas;
	private View v;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		v = inflater.inflate(R.layout.pembelian_input_activity, container, false);

		tanggal = (EditText) v.findViewById(R.id.editTextPembelianTanggal);
		saldo = (EditText) v.findViewById(R.id.editTextPembelianHarga);
		kas = (EditText) v.findViewById(R.id.editTextPembelianKas);
		btnOK = (Button) v.findViewById(R.id.buttonPembelianOK);
		btnCancel = (Button) v.findViewById(R.id.buttonPembelianCancel);

		tanggal.setOnClickListener(this);
		btnOK.setOnClickListener(this);
		btnCancel.setOnClickListener(this);

		controller = new PembelianController(getActivity());
		homeController = new HomeController(getActivity());

		homeController.open();

		dKas = homeController.getKas(new Date());
		strKas = String.valueOf(dKas).replace(".0", "");
		kas.setText(strKas);

		return v;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		FragmentManager fragmentManager = getFragmentManager();

		// Creating a fragment transaction
		final FragmentTransaction ft = fragmentManager.beginTransaction();

		switch (v.getId()) {
		case R.id.buttonPembelianOK:

			Double dKas = Double.parseDouble(kas.getText().toString());
			Double dSaldo = Double.parseDouble(saldo.getText().toString());

			if (dSaldo.compareTo(dKas) != 1) {

				AlertDialogHelper alertDialogHelper = new AlertDialogHelper(getActivity());
				alertDialogHelper.messageSave();
				alertDialogHelper.setPositiveButton("YES", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						controller.open();
						controller.create(tglFormat, saldo.getText().toString(), saldo.getText().toString());
						controller.close();

						strKas = "";

						ft.replace(R.id.content_frame, new PembelianDaftarActivity());

						// Committing the transaction
						ft.commit();
					}
				});

				alertDialogHelper.setNegativeButton("NO", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.cancel();
					}
				});

				alertDialogHelper.show();

			} else {
				Toast.makeText(getActivity(), "Pembelian Saldo Tidak Boleh Melebihi Uang Kas", Toast.LENGTH_LONG).show();
			}

			break;
		case R.id.editTextPembelianTanggal:
			Calendar c = Calendar.getInstance();
			int mYear = c.get(Calendar.YEAR);
			int mMonth = c.get(Calendar.MONTH);
			int mDay = c.get(Calendar.DAY_OF_MONTH);

			tanggal.setText("");
			tglFormat = "";

			datePickerDialog = new DatePickerDialog(getActivity(), onDateSetListener, mYear, mMonth, mDay);
			datePickerDialog.updateDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
			datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
			datePickerDialog.show();
			break;
		case R.id.buttonPembelianCancel:
			ft.replace(R.id.content_frame, new PembelianActivity());

			// Committing the transaction
			ft.commit();
			break;
		}
	}

	private DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
			// TODO Auto-generated method stub
			view.updateDate(year, monthOfYear, dayOfMonth);
			int mOfYear = monthOfYear + 1;
			// format date ("YYYY-MM-DD");
			tglFormat = year + "-" + mOfYear + "-" + dayOfMonth;
			// format date ("DD/MM/YYYY");
			tanggal.setText(dayOfMonth + "/" + mOfYear + "/" + year);
		}
	};

}
