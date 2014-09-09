package org.stn.pulsa;

import org.stn.pulsa.controller.MasterController;
import org.stn.pulsa.helper.AlertDialogHelper;
import org.stn.pulsa.model.Master;

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
import android.widget.EditText;

public class MasterUpdateActivity extends Fragment implements OnClickListener {

	// private EditText id;
	private EditText kode;
	private EditText saldo;
	private EditText hargaBeli;
	private EditText hargaJual;
	private Button btnUpdate;
	private Button btnDelete;
	private MasterController controller;
	private long longId;
	private String strKode;
	private String strBeli;
	private String strJual;
	private String strSaldo;
	private AlertDialogHelper alertDialogHelper;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.master_update_activity, container, false);

		// id = (EditText) findViewById(R.id.editTextMasterId);
		kode = (EditText) v.findViewById(R.id.editTextMasterKode);
		hargaBeli = (EditText) v.findViewById(R.id.editTextMasterHargaBeli);
		hargaJual = (EditText) v.findViewById(R.id.editTextMasterHargaJual);
		saldo = (EditText) v.findViewById(R.id.editTextMasterSaldo);
		btnUpdate = (Button) v.findViewById(R.id.buttonMasterUpdate);
		btnDelete = (Button) v.findViewById(R.id.buttonMasterDelete);

		btnUpdate.setOnClickListener(this);
		btnDelete.setOnClickListener(this);
		controller = new MasterController(getActivity());

		// ambil data dari extras intent
		Bundle bundle = getArguments();
		longId = bundle.getLong("id");
		strKode = bundle.getString("kode");
		strBeli = bundle.getString("hargaBeli");
		strJual = bundle.getString("hargaJual");
		strSaldo = bundle.getString("saldo");

		// isi data form
		// id.setText(String.valueOf(longId));
		kode.setText(strKode);
		hargaBeli.setText(strBeli);
		hargaJual.setText(strJual);
		saldo.setText(strSaldo);

		return v;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		FragmentManager fragmentManager = getFragmentManager();

		// Creating a fragment transaction
		final FragmentTransaction ft = fragmentManager.beginTransaction();

		switch (v.getId()) {
		case R.id.buttonMasterUpdate:

			alertDialogHelper = new AlertDialogHelper(getActivity());
			alertDialogHelper.messageSave();
			alertDialogHelper.setPositiveButton("YES", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// isi objek
					Master master = new Master();
					master.setId(longId);
					master.setKode(kode.getText().toString());
					master.setHargaBeli(hargaBeli.getText().toString());
					master.setHargaJual(hargaJual.getText().toString());
					master.setSaldo(saldo.getText().toString());

					controller.open();
					controller.update(master);
					controller.close();

					// Adding a fragment to the fragment transaction
					ft.replace(R.id.content_frame, new MasterDaftarActivity());

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
		case R.id.buttonMasterDelete:

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
					ft.replace(R.id.content_frame, new MasterDaftarActivity());

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
		}
	}

}
