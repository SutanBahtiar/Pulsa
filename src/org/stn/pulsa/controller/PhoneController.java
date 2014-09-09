package org.stn.pulsa.controller;

import java.util.ArrayList;
import java.util.List;

import org.stn.pulsa.model.Phone;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

public class PhoneController {

	private String TAG = this.getClass().getSimpleName();
	private ContentResolver contentResolver;

	// get data
	public List<Phone> getDataPhone(Context context) {
		List<Phone> list = new ArrayList<Phone>();

		contentResolver = context.getContentResolver();
		Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

		// pindah ke data paling pertama
		cursor.moveToFirst();

		// masukan cursor ke list
		while (!cursor.isAfterLast()) {
			Phone phone = cursorToPhone(cursor);
			if (!phone.getNama().equals("=="))
				list.add(phone);
			cursor.moveToNext();
		}

		// close cursor
		cursor.close();

		Log.i(TAG, "GET DATA PHONE");

		return list;
	}

	// Merubah cursor menjadi objek
	private Phone cursorToPhone(Cursor cursor) {
		Phone phone = new Phone();

		int isPhone = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

		if (isPhone == 1) {
			phone.setNama(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));

			Cursor tlpCursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
					new String[] { cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID)) }, null);

			while (tlpCursor.moveToNext()) {
				phone.setTelepon(tlpCursor.getString(tlpCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
			}

			tlpCursor.close();

		} else {
			phone.setNama("==");
			phone.setTelepon("000");
		}

		return phone;
	}
}
