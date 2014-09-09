package org.stn.pulsa.helper;

import android.R;
import android.app.AlertDialog;
import android.content.Context;

public class AlertDialogHelper extends AlertDialog.Builder {

	public AlertDialogHelper(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		setTitle("Confirm..");
	}

	public void messageSave() {
		setMessage("Are you sure you want save this?");
		setIcon(R.drawable.ic_menu_save);
	}

	public void messageDelete() {
		setMessage("Are you sure you want delete this?");
		setIcon(R.drawable.ic_menu_delete);
	}
}
