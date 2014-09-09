package org.stn.pulsa.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

	// database name
	private static final String db_name = "pulsa.db";
	private static final int db_version = 1;

	public static final String COLUMN_ID = "_id";

	// table master
	public static final String TABLE_MASTER = "tb_master";
	public static final String COLUMN_KODE = "kode";
	public static final String COLUMN_SALDO = "saldo";
	public static final String COLUMN_BELI = "harga_beli";
	public static final String COLUMN_JUAL = "harga_jual";

	private static final String table_master_create = "create table " + TABLE_MASTER + "(" + COLUMN_ID + " integer primary key autoincrement, " + COLUMN_KODE + " varchar(200) not null, "
			+ COLUMN_SALDO + " number(20) not null, " + COLUMN_BELI + " number(20) not null, " + COLUMN_JUAL + " number(20) not null);";

	public static final String[] columnsMaster = { COLUMN_ID, COLUMN_KODE, COLUMN_SALDO, COLUMN_BELI, COLUMN_JUAL };

	// table pembelian
	public static final String COLUMN_TANGGAL = "tanggal";
	public static final String COLUMN_KAS = "kas";
	public static final String TABLE_PEMBELIAN = "tb_pembelian";

	private static final String table_pembelian_create = "create table " + TABLE_PEMBELIAN + "(" + COLUMN_ID + " integer primary key autoincrement, " + COLUMN_TANGGAL + " date, " + COLUMN_KAS
			+ " number(20) not null, " + COLUMN_SALDO + " number(20) not null);";

	public static final String[] columnsPembelian = { COLUMN_ID, COLUMN_TANGGAL, COLUMN_KAS, COLUMN_SALDO };

	// table penjualan
	public static final String COLUMN_NAMA = "nama";
	public static final String COLUMN_MARGIN = "margin";
	public static final String COLUMN_STATUS = "status";
	public static final String COLUMN_TElEPON = "telepon";
	public static final String TABLE_PENJUALAN = "tb_penjualan";

	private static final String table_penjualan_create = "create table " + TABLE_PENJUALAN + "(" + COLUMN_ID + " integer primary key autoincrement, " + COLUMN_TANGGAL + " date, " + COLUMN_NAMA
			+ " varchar(200) not null, " + COLUMN_TElEPON + " number(20) not null, " + COLUMN_KODE + " varchar(200) not null, " + COLUMN_SALDO + " number(20) not null, " + COLUMN_JUAL
			+ " number(20) not null, " + COLUMN_BELI + " number(20) not null," + COLUMN_MARGIN + " number(20) not null," + COLUMN_STATUS + " number(1) not null);";

	public static final String[] columnsPenjualan = { COLUMN_ID, COLUMN_TANGGAL, COLUMN_NAMA, COLUMN_TElEPON, COLUMN_KODE, COLUMN_SALDO, COLUMN_JUAL, COLUMN_BELI, COLUMN_MARGIN, COLUMN_STATUS };

	// table settings
	public static final String COLUMN_PIUTANG = "piutang";
	public static final String TABLE_SETTINGS = "tb_settings";

	private static final String table_settings_create = "create table " + TABLE_SETTINGS + "(" + COLUMN_ID + " integer primary key autoincrement, " + COLUMN_TANGGAL + " date, " + COLUMN_SALDO
			+ " number(20) not null, " + COLUMN_KAS + " number(20) not null, " + COLUMN_PIUTANG + " number(20) not null);";

	public static final String[] columnsSettings = { COLUMN_ID, COLUMN_TANGGAL, COLUMN_SALDO, COLUMN_KAS, COLUMN_PIUTANG };

	public DBHelper(Context context) {
		super(context, db_name, null, db_version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(table_master_create);
		Log.i(this.getClass().getSimpleName(), "CREATE TABLE ::: " + table_master_create);

		db.execSQL(table_pembelian_create);
		Log.i(this.getClass().getSimpleName(), "CREATE TABLE ::: " + table_pembelian_create);

		db.execSQL(table_penjualan_create);
		Log.i(this.getClass().getSimpleName(), "CREATE TABLE ::: " + table_penjualan_create);

		db.execSQL(table_settings_create);
		Log.i(this.getClass().getSimpleName(), "CREATE TABLE ::: " + table_settings_create);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		Log.w(DBHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_MASTER);
		onCreate(db);
	}

}
