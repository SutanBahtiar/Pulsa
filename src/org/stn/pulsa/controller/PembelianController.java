package org.stn.pulsa.controller;

import java.util.ArrayList;
import java.util.Date;

import org.stn.pulsa.helper.DBHelper;
import org.stn.pulsa.model.Pembelian;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class PembelianController {

	private SQLiteDatabase database;
	private DBHelper dbHelper;
	private String tag = this.getClass().getSimpleName();

	public PembelianController(Context context) {
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
	public Pembelian create(String tanggal, String saldo, String kas) {

		ContentValues values = new ContentValues();
		values.put(DBHelper.COLUMN_TANGGAL, tanggal);
		values.put(DBHelper.COLUMN_SALDO, saldo);
		values.put(DBHelper.COLUMN_KAS, kas);

		// insert ke database dan mendapatkan id
		long insertId = database.insert(DBHelper.TABLE_PEMBELIAN, null, values);

		Log.i(tag, "INSERT ::: " + DBHelper.TABLE_PEMBELIAN + " ::: " + insertId);

		// query tabel dengan id yang diinsert
		Cursor cursor = database.query(DBHelper.TABLE_PEMBELIAN, DBHelper.columnsPembelian, DBHelper.COLUMN_ID + " = " + insertId, null, null, null, null);

		// pindah ke data paling pertama
		cursor.moveToFirst();

		// merubah cursor menjadi objek
		Pembelian pembelian = cursorToObjek(cursor);

		// close cursor
		cursor.close();

		return pembelian;
	}

	// Get All Data
	public ArrayList<Pembelian> getAll() {
		ArrayList<Pembelian> list = new ArrayList<Pembelian>();

		// query all data
		Cursor cursor = database.query(DBHelper.TABLE_PEMBELIAN, DBHelper.columnsPembelian, null, null, null, null, DBHelper.COLUMN_TANGGAL);

		Log.i(tag, "GET ALL ::: " + DBHelper.TABLE_PEMBELIAN + " ::: " + cursor.getColumnCount());

		// pindah ke data paling pertama
		cursor.moveToFirst();

		// masukan cursor ke list
		while (!cursor.isAfterLast()) {
			Pembelian pembelian = cursorToObjek(cursor);
			list.add(pembelian);
			cursor.moveToNext();
		}

		// close cursor
		cursor.close();

		return list;
	}

	// Get Data
	public Pembelian getData(long id) {
		Pembelian pembelian = new Pembelian();

		// query where id
		Cursor cursor = database.query(DBHelper.TABLE_PEMBELIAN, DBHelper.columnsPembelian, "_id =" + id, null, null, null, null);

		// pindah ke data paling pertama
		cursor.moveToFirst();

		// merubah cursor menjadi objek
		pembelian = cursorToObjek(cursor);

		return pembelian;
	}

	// Update Data
	public void update(Pembelian pembelian) {
		// ambil id
		String idPembelian = "_id=" + pembelian.getId();

		ContentValues values = new ContentValues();
		values.put(DBHelper.COLUMN_TANGGAL, pembelian.getTanggal());
		values.put(DBHelper.COLUMN_KAS, pembelian.getKas());
		values.put(DBHelper.COLUMN_SALDO, pembelian.getSaldo());

		// query update
		database.update(DBHelper.TABLE_PEMBELIAN, values, idPembelian, null);
	}

	// Delete Barang
	public void delete(long id) {
		// ambil id
		String idPembelian = "_id=" + id;

		// query delete
		database.delete(DBHelper.TABLE_PEMBELIAN, idPembelian, null);
	}

	// Merubah cursor menjadi objek
	private Pembelian cursorToObjek(Cursor cursor) {
		Pembelian pembelian = new Pembelian();
		pembelian.setId(cursor.getLong(0));
		pembelian.setTanggal(cursor.getString(1));
		pembelian.setKas(cursor.getString(2));
		pembelian.setSaldo(cursor.getString(3));
		return pembelian;
	}

	// Mendapatkan nilai saldo
	public Double getSaldo(Date date) {

		Double d = null;

		String sql = "SELECT SUM(" + DBHelper.COLUMN_SALDO + ") FROM " + DBHelper.TABLE_PEMBELIAN + " WHERE TANGGAL <= '" + date + "'";
		Log.i(tag, "SQL " + DBHelper.TABLE_PEMBELIAN + " " + sql);

		// query all data
		Cursor cursor = database.rawQuery(sql, null);

		// pindah ke data paling pertama
		if (cursor.moveToFirst()) {
			d = cursor.getDouble(0);
			Log.i(tag, "SUM SALDO " + DBHelper.TABLE_PEMBELIAN + " === " + d);
		}

		// close cursor
		cursor.close();

		return d;
	}

	// Mendapatkan nilai kas
	public Double getKas(Date date) {

		Double d = null;

		String sql = "SELECT SUM(" + DBHelper.COLUMN_KAS + ") FROM " + DBHelper.TABLE_PEMBELIAN + " WHERE TANGGAL <= '" + date + "'";
		Log.i(tag, "SQL " + sql);

		// query all data
		Cursor cursor = database.rawQuery(sql, null);

		// pindah ke data paling pertama
		if (cursor.moveToFirst()) {
			d = cursor.getDouble(0);
			Log.i(tag, "SUM KAS " + DBHelper.TABLE_PEMBELIAN + " === " + d);
		}

		// close cursor
		cursor.close();

		return d;
	}

}
