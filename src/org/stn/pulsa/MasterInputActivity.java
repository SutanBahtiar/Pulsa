package org.stn.pulsa;

import org.stn.pulsa.controller.MasterController;
import org.stn.pulsa.helper.AlertDialogHelper;

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

public class MasterInputActivity extends Fragment implements OnClickListener {

	private EditText kode;
	private EditText saldo;
	private EditText hargaBeli;
	private EditText hargaJual;
	private Button btnOK;
	private Button btnCancel;
	private MasterController controller;
	private AlertDialogHelper alertDialogHelper;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.master_input_activity, container, false);

		kode = (EditText) v.findViewById(R.id.editTextMasterKode);
		saldo = (EditText) v.findViewById(R.id.editTextMasterSaldo);
		hargaBeli = (EditText) v.findViewById(R.id.editTextMasterHargaBeli);
		hargaJual = (EditText) v.findViewById(R.id.editTextMasterHargaJual);
		btnOK = (Button) v.findViewById(R.id.buttonMasterOK);
		btnCancel = (Button) v.findViewById(R.id.buttonMasterCancel);

		btnOK.setOnClickListener(this);
		btnCancel.setOnClickListener(this);

		controller = new MasterController(getActivity());

		return v;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		FragmentManager fragmentManager = getFragmentManager();

		// Creating a fragment transaction
		final FragmentTransaction ft = fragmentManager.beginTransaction();

		switch (v.getId()) {
		case R.id.buttonMasterCancel:
			MasterActivity masterActivity = new MasterActivity();

			// Adding a fragment to the fragment transaction
			ft.replace(R.id.content_frame, masterActivity);

			// Committing the transaction
			ft.commit();

			break;
		case R.id.buttonMasterOK:

			alertDialogHelper = new AlertDialogHelper(getActivity());
			alertDialogHelper.messageSave();
			alertDialogHelper.setPositiveButton("YES", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					controller.open();
					controller.create(kode.getText().toString(), saldo.getText().toString(), hargaBeli.getText().toString(), hargaJual.getText().toString());
					controller.close();

					MasterDaftarActivity masterDaftarActivity = new MasterDaftarActivity();

					// Adding a fragment to the fragment transaction
					ft.replace(R.id.content_frame, masterDaftarActivity);

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
