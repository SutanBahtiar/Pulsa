package org.stn.pulsa;

import java.util.Calendar;

import org.stn.pulsa.controller.PembelianController;
import org.stn.pulsa.helper.AlertDialogHelper;
import org.stn.pulsa.helper.DateFormatHelper;
import org.stn.pulsa.model.Pembelian;

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

public class PembelianUpdateActivity extends Fragment implements OnClickListener {

	private EditText tanggal;
	private EditText saldo;
	private EditText kas;
	private Button btnUpdate;
	private Button btnDelete;
	private DatePickerDialog datePickerDialog;
	private PembelianController controller;
	private String tglFormat;
	private long longId;
	private String strTanggal;
	private String strKas;
	private String strSaldo;
	private AlertDialogHelper alertDialogHelper;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.pembelian_update_activity, container, false);

		tanggal = (EditText) v.findViewById(R.id.editTextPembelianTanggal);
		saldo = (EditText) v.findViewById(R.id.editTextPembelianHarga);
		kas = (EditText) v.findViewById(R.id.editTextPembelianKas);
		btnUpdate = (Button) v.findViewById(R.id.buttonPembelianUpdate);
		btnDelete = (Button) v.findViewById(R.id.buttonPembelianDelete);

		tanggal.setOnClickListener(this);
		btnUpdate.setOnClickListener(this);
		btnDelete.setOnClickListener(this);

		kas.setKeyListener(null);

		controller = new PembelianController(getActivity());

		// ambil data dari extras intent
		Bundle bundle = getArguments();
		longId = bundle.getLong("id");
		strTanggal = bundle.getString("tanggal");
		strKas = bundle.getString("kas");
		strSaldo = bundle.getString("saldo");

		// isi data form
		tanggal.setText(DateFormatHelper.toFormat(strTanggal));
		kas.setText(strKas);
		saldo.setText(strSaldo);

		tglFormat = strTanggal.replace("/", "-");

		return v;
	}

	@Override
	public void onClick(View v) {
		FragmentManager fragmentManager = getFragmentManager();

		// Creating a fragment transaction
		final FragmentTransaction ft = fragmentManager.beginTransaction();

		switch (v.getId()) {
		case R.id.buttonPembelianUpdate:
			Double dKas = Double.parseDouble(strKas);
			Double dSaldo = Double.parseDouble(saldo.getText().toString());

			if (dSaldo.compareTo(dKas) != 1) {

				alertDialogHelper = new AlertDialogHelper(getActivity());
				alertDialogHelper.messageSave();
				alertDialogHelper.setPositiveButton("YES", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						// isi objek
						Pembelian pembelian = new Pembelian();
						pembelian.setId(longId);
						pembelian.setTanggal(tglFormat);
						pembelian.setKas(kas.getText().toString());
						pembelian.setSaldo(saldo.getText().toString());

						controller.open();
						controller.update(pembelian);
						controller.close();

						// Adding a fragment to the fragment transaction
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
		case R.id.buttonPembelianDelete:

			alertDialogHelper = new AlertDialogHelper(getActivity());
			alertDialogHelper.messageDelete();
			alertDialogHelper.setPositiveButton("YES", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					controller.open();
					controller.delete(longId);
					controller.close();

					// Adding a fragment to the fragment transaction
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
			datePickerDialog.show();
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
