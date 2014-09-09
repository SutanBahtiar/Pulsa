package org.stn.pulsa;

import java.util.Calendar;
import java.util.Date;

import org.stn.pulsa.controller.SettingsController;
import org.stn.pulsa.helper.AlertDialogHelper;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

public class KasInputActivity extends Activity implements OnClickListener {

	private EditText tanggal;
	private EditText kas;
	private Button btnOK;
	private Button btnCancel;
	private DatePickerDialog datePickerDialog;
	private SettingsController controller;
	private String tglFormat;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kas_input_activity);

		tanggal = (EditText) findViewById(R.id.editTextSettingsTanggal);
		kas = (EditText) findViewById(R.id.editTextSettingsKas);
		btnOK = (Button) findViewById(R.id.buttonSettingsOK);
		btnCancel = (Button) findViewById(R.id.buttonSettingsCancel);

		btnOK.setOnClickListener(this);
		btnCancel.setOnClickListener(this);
		tanggal.setOnClickListener(this);

		controller = new SettingsController(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.buttonSettingsOK:

			AlertDialogHelper alertDialogHelper = new AlertDialogHelper(this);
			alertDialogHelper.setPositiveButton("YES", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					controller.open();
					controller.create(tglFormat, "0", kas.getText().toString(), "0");
					controller.close();

					Intent i = new Intent(KasInputActivity.this, HomeActivity.class);
					KasInputActivity.this.finish();
					startActivity(i);
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
			Intent i2 = new Intent(this, HomeActivity.class);
			startActivity(i2);
			this.finish();
			break;
		case R.id.editTextSettingsTanggal:
			Calendar c = Calendar.getInstance();
			int mYear = c.get(Calendar.YEAR);
			int mMonth = c.get(Calendar.MONTH);
			int mDay = c.get(Calendar.DAY_OF_MONTH);

			tanggal.setText("");
			tglFormat = "";

			datePickerDialog = new DatePickerDialog(this, onDateSetListener, mYear, mMonth, mDay);
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
