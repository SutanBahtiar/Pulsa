package org.stn.pulsa.controller;

import java.util.ArrayList;
import java.util.Date;

import org.stn.pulsa.helper.DBHelper;
import org.stn.pulsa.model.Settings;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class SettingsController {

	private SQLiteDatabase database;
	private DBHelper dbHelper;
	private String tag = this.getClass().getSimpleName();

	public SettingsController(Context context) {
		// TODO Auto-generated constructor stub
		dbHelper = new DBHelper(context);
	}

	public void open() {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	// Insert
	public Settings create(String tanggal, String saldo, String kas, String piutang) {

		ContentValues values = new ContentValues();
		values.put(DBHelper.COLUMN_TANGGAL, tanggal);
		values.put(DBHelper.COLUMN_SALDO, saldo);
		values.put(DBHelper.COLUMN_KAS, kas);
		values.put(DBHelper.COLUMN_PIUTANG, piutang);

		// insert ke database dan mendapatkan id
		long insertId = database.insert(DBHelper.TABLE_SETTINGS, null, values);

		Log.i(tag, "INSERT ::: " + DBHelper.TABLE_SETTINGS + " ::: " + insertId);

		// query tabel dengan id yang diinsert
		Cursor cursor = database.query(DBHelper.TABLE_SETTINGS, DBHelper.columnsSettings, DBHelper.COLUMN_ID + " = " + insertId, null, null, null, null);

		// pindah ke data paling pertama
		cursor.moveToFirst();

		// merubah cursor menjadi objek
		Settings settings = cursorToObjek(cursor);

		// close cursor
		cursor.close();

		return settings;
	}

	// Get All Data
	public ArrayList<Settings> getAll() {
		ArrayList<Settings> list = new ArrayList<Settings>();

		// query all data
		Cursor cursor = database.query(DBHelper.TABLE_SETTINGS, DBHelper.columnsSettings, null, null, null, null, null);

		Log.i(tag, "GET ALL ::: " + DBHelper.TABLE_SETTINGS + " ::: " + cursor.getCount());

		// pindah ke data paling pertama
		cursor.moveToFirst();

		// masukan cursor ke list
		while (!cursor.isAfterLast()) {
			Settings settings = cursorToObjek(cursor);

			Log.i(tag, settings.getTanggal());

			list.add(settings);

			Log.i(tag, "LIST GET TANGGAL == " + list.get(0).getTanggal());
			cursor.moveToNext();
		}

		// close cursor
		cursor.close();

		return list;
	}

	// Get Data
	public Settings getData(long id) {
		Settings settings = new Settings();

		// query where id
		Cursor cursor = database.query(DBHelper.TABLE_SETTINGS, DBHelper.columnsSettings, "_id =" + id, null, null, null, null);

		// pindah ke data paling pertama
		cursor.moveToFirst();

		// merubah cursor menjadi objek
		settings = cursorToObjek(cursor);

		cursor.close();

		return settings;
	}

	// Update Data
	public void update(Settings settings) {
		// ambil id
		String idSettings = "_id=" + settings.getId();

		ContentValues values = new ContentValues();
		values.put(DBHelper.COLUMN_TANGGAL, settings.getTanggal());
		values.put(DBHelper.COLUMN_SALDO, settings.getSaldo());
		values.put(DBHelper.COLUMN_KAS, settings.getKas());
		values.put(DBHelper.COLUMN_PIUTANG, settings.getPiutang());

		// query update
		database.update(DBHelper.TABLE_SETTINGS, values, idSettings, null);

	}

	// Delete Barang
	public void delete(long id) {
		// ambil id
		String idSettings = "_id=" + id;

		// query delete
		database.delete(DBHelper.TABLE_SETTINGS, idSettings, null);
	}

	// Merubah cursor menjadi objek
	private Settings cursorToObjek(Cursor cursor) {
		Settings settings = new Settings();
		settings.setId(cursor.getLong(0));
		settings.setTanggal(cursor.getString(1));
		settings.setSaldo(cursor.getString(2));
		settings.setKas(cursor.getString(3));
		settings.setPiutang(cursor.getString(4));
		return settings;
	}

	// Mendapatkan jumlah baris data
	public int getCount() {

		// query all data
		Cursor cursor = database.query(DBHelper.TABLE_SETTINGS, DBHelper.columnsSettings, null, null, null, null, null);

		// dapatkan jumlah row
		int i = cursor.getCount();

		Log.i(tag, "GET COUNT ==== " + i);

		// close cursor
		cursor.close();

		return i;
	}

	// Mendapatkan nilai saldo
	public Double getSaldo(Date date) {

		Double d = null;

		String sql = "SELECT SUM(" + DBHelper.COLUMN_SALDO + ") FROM " + DBHelper.TABLE_SETTINGS + " WHERE TANGGAL <= '" + date + "'";
		Log.i(tag, "SQL " + DBHelper.TABLE_SETTINGS + " " + sql);

		// query all data
		Cursor cursor = database.rawQuery(sql, null);

		// pindah ke data paling pertama
		if (cursor.moveToFirst()) {
			d = cursor.getDouble(0);
			Log.i(tag, "SUM SALDO " + DBHelper.TABLE_SETTINGS + " === " + d);
		}

		// close cursor
		cursor.close();

		return d;
	}

	// Mendapatkan nilai kas
	public Double getKas(Date date) {

		Double d = null;

		String sql = "SELECT SUM(" + DBHelper.COLUMN_KAS + ") FROM " + DBHelper.TABLE_SETTINGS + " WHERE TANGGAL <= '" + date + "'";
		Log.i(tag, "SQL " + sql);

		// query all data
		Cursor cursor = database.rawQuery(sql, null);

		// pindah ke data paling pertama
		if (cursor.moveToFirst()) {
			d = cursor.getDouble(0);
			Log.i(tag, "SUM KAS " + DBHelper.TABLE_SETTINGS + " === " + d);
		}

		// close cursor
		cursor.close();

		return d;
	}

	// Mendapatkan nilai kas
	public Double getPiutang(Date date) {

		Double d = null;

		String sql = "SELECT SUM(" + DBHelper.COLUMN_PIUTANG + ") FROM " + DBHelper.TABLE_SETTINGS + " WHERE TANGGAL <= '" + date + "'";
		Log.i(tag, "SQL " + sql);

		// query all data
		Cursor cursor = database.rawQuery(sql, null);

		// pindah ke data paling pertama
		if (cursor.moveToFirst()) {
			d = cursor.getDouble(0);
			Log.i(tag, "SUM PIUTANG " + DBHelper.TABLE_SETTINGS + " === " + d);
		}

		// close cursor
		cursor.close();

		return d;
	}

}
