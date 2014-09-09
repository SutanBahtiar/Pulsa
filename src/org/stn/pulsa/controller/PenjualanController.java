package org.stn.pulsa.controller;

import java.util.ArrayList;
import java.util.Date;

import org.stn.pulsa.helper.DBHelper;
import org.stn.pulsa.model.Penjualan;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class PenjualanController {

	private SQLiteDatabase database;
	private DBHelper dbHelper;
	private String tag = this.getClass().getSimpleName();

	public PenjualanController(Context context) {
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
	public Penjualan create(String tanggal, String nama, String telepon, String kode, String saldo, String hargaJual, String hargaBeli, String margin, int status) {

		ContentValues values = new ContentValues();
		values.put(DBHelper.COLUMN_TANGGAL, tanggal);
		values.put(DBHelper.COLUMN_NAMA, nama);
		values.put(DBHelper.COLUMN_TElEPON, telepon);
		values.put(DBHelper.COLUMN_KODE, kode);
		values.put(DBHelper.COLUMN_SALDO, saldo);
		values.put(DBHelper.COLUMN_JUAL, hargaJual);
		values.put(DBHelper.COLUMN_BELI, hargaBeli);
		values.put(DBHelper.COLUMN_MARGIN, margin);
		values.put(DBHelper.COLUMN_STATUS, status);

		// insert ke database dan mendapatkan id
		long insertId = database.insert(DBHelper.TABLE_PENJUALAN, null, values);

		Log.i(tag, "INSERT ::: " + DBHelper.TABLE_PENJUALAN + " ::: " + insertId);

		// query tabel dengan id yang diinsert
		Cursor cursor = database.query(DBHelper.TABLE_PENJUALAN, DBHelper.columnsPenjualan, DBHelper.COLUMN_ID + " = " + insertId, null, null, null, null);

		// pindah ke data paling pertama
		cursor.moveToFirst();

		// merubah cursor menjadi objek
		Penjualan penjualan = cursorToObjek(cursor);

		// close cursor
		cursor.close();

		return penjualan;
	}

	// Get All Data
	public ArrayList<Penjualan> getAll() {
		ArrayList<Penjualan> list = new ArrayList<Penjualan>();

		// query all data
		Cursor cursor = database.query(DBHelper.TABLE_PENJUALAN, DBHelper.columnsPenjualan, null, null, null, null, DBHelper.COLUMN_TANGGAL);

		Log.i(tag, "GET ALL ::: " + DBHelper.TABLE_PENJUALAN + " ::: " + cursor.getColumnCount());

		// pindah ke data paling pertama
		cursor.moveToFirst();

		// masukan cursor ke list
		while (!cursor.isAfterLast()) {
			Penjualan penjualan = cursorToObjek(cursor);
			list.add(penjualan);
			cursor.moveToNext();
		}

		// close cursor
		cursor.close();

		return list;
	}

	/**
	 * 
	 * @param status
	 *            0 = lunas, 1 = hutang
	 * @return
	 */
	public ArrayList<Penjualan> getAllKasOrPiutang(int status) {
		ArrayList<Penjualan> list = new ArrayList<Penjualan>();

		// query all data
		Cursor cursor = database.query(DBHelper.TABLE_PENJUALAN, DBHelper.columnsPenjualan, DBHelper.COLUMN_STATUS + "=" + status, null, null, null, DBHelper.COLUMN_TANGGAL);

		Log.i(tag, "GET ALL = " + DBHelper.TABLE_PENJUALAN + " ROW = " + cursor.getCount());

		// pindah ke data paling pertama
		cursor.moveToFirst();

		// masukan cursor ke list
		while (!cursor.isAfterLast()) {
			Penjualan penjualan = cursorToObjek(cursor);
			list.add(penjualan);
			cursor.moveToNext();
		}

		// close cursor
		cursor.close();

		return list;
	}

	// Get Data
	public Penjualan getData(long id) {
		Penjualan penjualan = new Penjualan();

		// query where id
		Cursor cursor = database.query(DBHelper.TABLE_PENJUALAN, DBHelper.columnsPenjualan, "_id =" + id, null, null, null, null);

		// pindah ke data paling pertama
		cursor.moveToFirst();

		// merubah cursor menjadi objek
		penjualan = cursorToObjek(cursor);

		return penjualan;
	}

	// Update Data
	public void update(Penjualan penjualan) {
		// ambil id
		String idPenjualan = "_id=" + penjualan.getId();

		ContentValues values = new ContentValues();
		values.put(DBHelper.COLUMN_TANGGAL, penjualan.getTanggal());
		values.put(DBHelper.COLUMN_NAMA, penjualan.getNama());
		values.put(DBHelper.COLUMN_TElEPON, penjualan.getTelepon());
		values.put(DBHelper.COLUMN_KODE, penjualan.getKode());
		values.put(DBHelper.COLUMN_SALDO, penjualan.getSaldo());
		values.put(DBHelper.COLUMN_JUAL, penjualan.getHargaJual());
		values.put(DBHelper.COLUMN_BELI, penjualan.getHargaBeli());
		values.put(DBHelper.COLUMN_MARGIN, penjualan.getMargin());
		values.put(DBHelper.COLUMN_STATUS, penjualan.getStatus());

		// query update
		database.update(DBHelper.TABLE_PENJUALAN, values, idPenjualan, null);
	}

	// Delete Barang
	public void delete(long id) {
		// ambil id
		String idPenjualan = "_id=" + id;

		// query delete
		database.delete(DBHelper.TABLE_PENJUALAN, idPenjualan, null);
	}

	// Merubah cursor menjadi objek
	private Penjualan cursorToObjek(Cursor cursor) {
		Penjualan penjualan = new Penjualan();
		penjualan.setId(cursor.getLong(0));
		penjualan.setTanggal(cursor.getString(1));
		penjualan.setNama(cursor.getString(2));
		penjualan.setTelepon(cursor.getString(3));
		penjualan.setKode(cursor.getString(4));
		penjualan.setSaldo(cursor.getString(5));
		penjualan.setHargaJual(cursor.getString(6));
		penjualan.setHargaBeli(cursor.getString(7));
		penjualan.setMargin(cursor.getString(8));
		penjualan.setStatus(cursor.getInt(9));
		return penjualan;
	}

	/**
	 * Mendapatkan nilai kas dan piutang
	 * 
	 * @param date
	 * @param status
	 *            0 lunas 1 hutang
	 * @return
	 */
	public Double getKasOrPiutang(Date date, int status) {

		Double d = null;

		String sql = "SELECT SUM(" + DBHelper.COLUMN_JUAL + ") FROM " + DBHelper.TABLE_PENJUALAN + " WHERE TANGGAL <= '" + date + "'  AND STATUS = '" + status + "' ";
		Log.i(tag, "SQL " + DBHelper.TABLE_PEMBELIAN + " " + sql);

		// query all data
		Cursor cursor = database.rawQuery(sql, null);

		// pindah ke data paling pertama
		if (cursor.moveToFirst()) {
			d = cursor.getDouble(0);
			Log.i(tag, "SUM KAS " + DBHelper.TABLE_PENJUALAN + " === " + d);
		}

		// close cursor
		cursor.close();

		return d;
	}

	/**
	 * Mendapatkan nilai margin kas dan margin piutang
	 * 
	 * @param date
	 * @param status
	 *            0 lunas 1 hutang
	 * @return
	 */
	public Double getMarginKasOrMarginPiutang(Date date, int status) {

		Double d = null;

		String sql = "SELECT SUM(" + DBHelper.COLUMN_MARGIN + ") FROM " + DBHelper.TABLE_PENJUALAN + " WHERE TANGGAL <= '" + date + "'  AND STATUS = '" + status + "' ";
		Log.i(tag, "SQL " + DBHelper.TABLE_PEMBELIAN + " " + sql);

		// query all data
		Cursor cursor = database.rawQuery(sql, null);

		// pindah ke data paling pertama
		if (cursor.moveToFirst()) {
			d = cursor.getDouble(0);
			Log.i(tag, "SUM MARGIN " + DBHelper.TABLE_PENJUALAN + " === " + d);
		}

		// close cursor
		cursor.close();

		return d;
	}

	/**
	 * Mendapatkan nilai saldo
	 * 
	 * @param date
	 * @return
	 */
	public Double getSaldo(Date date) {

		Double d = null;

		String sql = "SELECT SUM(" + DBHelper.COLUMN_BELI + ") FROM " + DBHelper.TABLE_PENJUALAN + " WHERE TANGGAL <= '" + date + "' ";
		Log.i(tag, "SQL " + DBHelper.TABLE_PEMBELIAN + " " + sql);

		// query all data
		Cursor cursor = database.rawQuery(sql, null);

		// pindah ke data paling pertama
		if (cursor.moveToFirst()) {
			d = cursor.getDouble(0);
			Log.i(tag, "SUM SALDO " + DBHelper.TABLE_PENJUALAN + " === " + d);
		}

		// close cursor
		cursor.close();

		return d;
	}

}
