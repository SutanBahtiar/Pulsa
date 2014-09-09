package org.stn.pulsa;

import java.util.Calendar;
import java.util.Date;

import org.stn.pulsa.controller.SettingsController;
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

public class SettingsInputActivity extends Fragment implements OnClickListener {

	private EditText tanggal;
	private EditText saldo;
	private EditText kas;
	private EditText piutang;
	private Button btnOK;
	private Button btnCancel;
	private DatePickerDialog datePickerDialog;
	private SettingsController controller;
	private String tglFormat;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.settings_input_activity, container, false);

		tanggal = (EditText) v.findViewById(R.id.editTextSettingsTanggal);
		saldo = (EditText) v.findViewById(R.id.editTextSettingsSaldo);
		kas = (EditText) v.findViewById(R.id.editTextSettingsKas);
		piutang = (EditText) v.findViewById(R.id.editTextSettingsPiutang);
		btnOK = (Button) v.findViewById(R.id.buttonSettingsOK);
		btnCancel = (Button) v.findViewById(R.id.buttonSettingsCancel);

		btnOK.setOnClickListener(this);
		btnCancel.setOnClickListener(this);
		tanggal.setOnClickListener(this);

		controller = new SettingsController(getActivity());

		return v;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		FragmentManager fragmentManager = getFragmentManager();

		// Creating a fragment transaction
		final FragmentTransaction ft = fragmentManager.beginTransaction();

		switch (v.getId()) {
		case R.id.buttonSettingsOK:

			AlertDialogHelper alertDialogHelper = new AlertDialogHelper(getActivity());
			alertDialogHelper.messageSave();
			alertDialogHelper.setPositiveButton("YES", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					controller.open();
					controller.create(tglFormat, saldo.getText().toString(), kas.getText().toString(), piutang.getText().toString());
					controller.close();

					ft.replace(R.id.content_frame, new SettingsDaftarActivity());

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
		case R.id.buttonSettingsCancel:
			ft.replace(R.id.content_frame, new HomeActivity());

			// Committing the transaction
			ft.commit();
			break;
		case R.id.editTextSettingsTanggal:
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
