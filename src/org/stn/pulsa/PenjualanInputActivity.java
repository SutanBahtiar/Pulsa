package org.stn.pulsa;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.stn.pulsa.adapter.MasterAdapter;
import org.stn.pulsa.adapter.PhoneAdapter;
import org.stn.pulsa.controller.MasterController;
import org.stn.pulsa.controller.PenjualanController;
import org.stn.pulsa.controller.PhoneController;
import org.stn.pulsa.helper.AlertDialogHelper;
import org.stn.pulsa.helper.DialogHelper;
import org.stn.pulsa.model.Master;
import org.stn.pulsa.model.Phone;

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;

public class PenjualanInputActivity extends Fragment implements OnClickListener {

	private EditText tanggal;
	private EditText nama;
	private EditText telepon;
	private EditText kode;
	private EditText saldo;
	private EditText hargaJual;
	private EditText hargaBeli;
	private Switch status;
	private Button btnOK;
	private Button btnCancel;
	private DatePickerDialog datePickerDialog;
	private PenjualanController controller;
	private String tglFormat;
	private int nilaiStatus;

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.penjualan_input_activity, container, false);

		status = (Switch) v.findViewById(R.id.penjualanStatus);
		tanggal = (EditText) v.findViewById(R.id.editTextPenjualanTanggal);
		nama = (EditText) v.findViewById(R.id.editTextPenjualanNama);
		telepon = (EditText) v.findViewById(R.id.editTextPenjualanTelepon);
		kode = (EditText) v.findViewById(R.id.editTextPenjualanKode);
		saldo = (EditText) v.findViewById(R.id.editTextPenjualanSaldo);
		hargaBeli = (EditText) v.findViewById(R.id.editTextPenjualanHargaBeli);
		hargaJual = (EditText) v.findViewById(R.id.editTextPenjualanHargaJual);

		btnOK = (Button) v.findViewById(R.id.buttonPenjualanOK);
		btnCancel = (Button) v.findViewById(R.id.buttonPenjualanCancel);

		tanggal.setOnClickListener(this);
		nama.setOnClickListener(this);
		kode.setOnClickListener(this);
		btnOK.setOnClickListener(this);
		btnCancel.setOnClickListener(this);

		saldo.setKeyListener(null);
		hargaBeli.setKeyListener(null);
		hargaJual.setKeyListener(null);

		nilaiStatus = 0;
		status.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				nilaiStatus = isChecked ? 1 : 0;
			}
		});

		controller = new PenjualanController(getActivity());

		return v;
	}

	@Override
	public void onClick(View v) {
		FragmentManager fragmentManager = getFragmentManager();

		// Creating a fragment transaction
		final FragmentTransaction ft = fragmentManager.beginTransaction();

		switch (v.getId()) {
		case R.id.editTextPenjualanNama:
			showDialogPhone();
			break;
		case R.id.buttonPenjualanCancel:
			ft.replace(R.id.content_frame, new PenjualanActivity());

			// Committing the transaction
			ft.commit();
			break;
		case R.id.buttonPenjualanOK:

			AlertDialogHelper alertDialogHelper = new AlertDialogHelper(getActivity());
			alertDialogHelper.messageSave();
			alertDialogHelper.setPositiveButton("YES", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					String nSaldo = saldo.getText().toString().replace(",", "");
					String beli = hargaBeli.getText().toString().replace(",", "");
					String jual = hargaJual.getText().toString().replace(",", "");
					String margin = Integer.toString(Integer.parseInt(jual) - Integer.parseInt(beli));

					controller.open();
					controller.create(tglFormat, nama.getText().toString(), telepon.getText().toString(), kode.getText().toString(), nSaldo, jual, beli, margin, nilaiStatus);
					controller.close();

					ft.replace(R.id.content_frame, new PenjualanDaftarActivity());

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
		case R.id.editTextPenjualanTanggal:
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
		case R.id.editTextPenjualanKode:
			showDialogKode();
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

	private void showDialogPhone() {
		final DialogHelper dialog = new DialogHelper(getActivity(), R.layout.phone);
		final PhoneAdapter phoneAdapter = new PhoneAdapter(getActivity(), 0, new PhoneController().getDataPhone(getActivity()));
		dialog.setAdapter(phoneAdapter);

		ListView ls = dialog.getList();
		ls.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				Phone phone = phoneAdapter.getItem(position);

				nama.setText(phone.getNama());
				telepon.setText(phone.getTelepon());

				dialog.dismiss();
			}
		});

		dialog.show();
	}

	private void showDialogKode() {
		MasterController masterController = new MasterController(getActivity());
		masterController.open();
		ArrayList<Master> listMasters = masterController.getAll();
		masterController.close();

		final MasterAdapter masterAdapter = new MasterAdapter(getActivity(), 0, listMasters);
		final DialogHelper dialog = new DialogHelper(getActivity(), R.layout.pembelian_daftar_activity);
		dialog.setAdapter(masterAdapter);

		ListView ls = dialog.getList();
		ls.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				Master master = masterAdapter.getItem(position);

				kode.setText(master.getKode());
				hargaBeli.setText(master.getHargaBeli());
				hargaJual.setText(master.getHargaJual());
				saldo.setText(master.getSaldo());

				dialog.dismiss();
			}
		});

		dialog.show();
	}

}
