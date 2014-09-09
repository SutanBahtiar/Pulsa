package org.stn.pulsa.controller;

import java.util.ArrayList;

import org.stn.pulsa.helper.DBHelper;
import org.stn.pulsa.model.Master;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class MasterController {

	private SQLiteDatabase database;
	private DBHelper dbHelper;
	private String tag = this.getClass().getSimpleName();

	public MasterController(Context context) {
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
	public Master create(String kode, String saldo, String beli, String jual) {

		ContentValues values = new ContentValues();
		values.put(DBHelper.COLUMN_KODE, kode);
		values.put(DBHelper.COLUMN_SALDO, saldo);
		values.put(DBHelper.COLUMN_BELI, beli);
		values.put(DBHelper.COLUMN_JUAL, jual);

		// insert ke database dan mendapatkan id
		long insertId = database.insert(DBHelper.TABLE_MASTER, null, values);

		Log.i(tag, "INSERT ::: " + DBHelper.TABLE_MASTER + " ::: " + insertId);

		// query tabel dengan id yang diinsert
		Cursor cursor = database.query(DBHelper.TABLE_MASTER, DBHelper.columnsMaster, DBHelper.COLUMN_ID + " = " + insertId, null, null, null, null);

		// pindah ke data paling pertama
		cursor.moveToFirst();

		// merubah cursor menjadi objek
		Master master = cursorToMaster(cursor);

		// close cursor
		cursor.close();

		return master;
	}

	// Get All Data
	public ArrayList<Master> getAll() {
		ArrayList<Master> list = new ArrayList<Master>();

		// query all data
		Cursor cursor = database.query(DBHelper.TABLE_MASTER, DBHelper.columnsMaster, null, null, null, null, DBHelper.COLUMN_KODE);

		Log.i(tag, "GET ALL ::: " + DBHelper.TABLE_MASTER + " ::: " + cursor.getCount());

		// pindah ke data paling pertama
		cursor.moveToFirst();

		// masukan cursor ke list
		while (!cursor.isAfterLast()) {
			Master master = cursorToMaster(cursor);
			list.add(master);
			cursor.moveToNext();
		}

		// close cursor
		cursor.close();

		return list;
	}

	// Get Data
	public Master getData(long id) {
		Master master = new Master();

		// query where id
		Cursor cursor = database.query(DBHelper.TABLE_MASTER, DBHelper.columnsMaster, "_id =" + id, null, null, null, null);

		Log.i(tag, "GET DATA ::: " + DBHelper.TABLE_MASTER + " ::: ID ::: " + id);

		// pindah ke data paling pertama
		cursor.moveToFirst();

		// merubah cursor menjadi objek
		master = cursorToMaster(cursor);

		return master;
	}

	// Update Data
	public void update(Master master) {
		// ambil id
		String idMaster = "_id =" + master.getId();

		ContentValues values = new ContentValues();
		values.put(DBHelper.COLUMN_KODE, master.getKode());
		values.put(DBHelper.COLUMN_SALDO, master.getSaldo());
		values.put(DBHelper.COLUMN_BELI, master.getHargaBeli());
		values.put(DBHelper.COLUMN_JUAL, master.getHargaJual());

		Log.i(tag, "UPDATE ::: KODE " + master.getKode() + " SALDO " + master.getSaldo() + " BELI " + master.getHargaBeli() + " JUAL " + master.getHargaJual());
		Log.i(tag, "UPDATE ::: " + DBHelper.TABLE_MASTER + " ::: ID ::: " + idMaster);

		// query update
		database.update(DBHelper.TABLE_MASTER, values, idMaster, null);
	}

	// Delete Barang
	public void delete(long id) {
		// ambil id
		String idMaster = "_id=" + id;

		// query delete
		database.delete(DBHelper.TABLE_MASTER, idMaster, null);
	}

	// Merubah cursor menjadi objek
	private Master cursorToMaster(Cursor cursor) {
		Master master = new Master();
		master.setId(cursor.getLong(0));
		master.setKode(cursor.getString(1));
		master.setSaldo(cursor.getString(2));
		master.setHargaBeli(cursor.getString(3));
		master.setHargaJual(cursor.getString(4));
		return master;
	}

}
