package org.stn.pulsa.controller;

import java.util.Date;

import org.stn.pulsa.helper.DBHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class HomeController {

	@SuppressWarnings("unused")
	private SQLiteDatabase database;
	private DBHelper dbHelper;
	// private String tag = this.getClass().getSimpleName();

	private SettingsController settingsController;
	private PembelianController pembelianController;
	private PenjualanController penjualanController;

	public HomeController(Context context) {
		// TODO Auto-generated constructor stub
		dbHelper = new DBHelper(context);

		settingsController = new SettingsController(context);
		pembelianController = new PembelianController(context);
		penjualanController = new PenjualanController(context);
	}

	public void open() {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	// Mendapatkan nilai saldo
	public Double getSaldo(Date date) {
		// nilai saldo tb settings
		settingsController.open();
		Double saldoSettings = settingsController.getSaldo(date);
		settingsController.close();

		// nilai saldo tb pembelian
		pembelianController.open();
		Double saldoPembelian = pembelianController.getSaldo(date);
		pembelianController.close();

		// nilai saldo tb penjualan
		penjualanController.open();
		Double saldoPenjualan = penjualanController.getSaldo(date);
		penjualanController.close();

		// total saldo
		Double d = saldoSettings + saldoPembelian - saldoPenjualan;

		return d;
	}

	// Mendapatkan nilai kas
	public Double getKas(Date date) {
		// nilai kas tb settings
		settingsController.open();
		Double kasSettings = settingsController.getKas(date);
		settingsController.close();

		// nilai kas tb pembelian
		pembelianController.open();
		Double kasPembelian = pembelianController.getKas(date);
		pembelianController.close();

		// nilai kas tb penjualan
		penjualanController.open();
		Double kasPenjualan = penjualanController.getKasOrPiutang(date, 0);
		penjualanController.close();

		// total kas
		Double d = kasSettings + kasPenjualan - kasPembelian;

		return d;
	}

	// Mendapatkan nilai piutang
	public Double getPiutang(Date date) {
		// nilai piutang tb settings
		settingsController.open();
		Double piutangSettings = settingsController.getPiutang(date);
		settingsController.close();

		// nilai piutang tb penjualan
		penjualanController.open();
		Double piutangPenjualan = penjualanController.getKasOrPiutang(date, 1);
		penjualanController.close();

		// total kas
		Double d = piutangSettings + piutangPenjualan;

		return d;
	}

	// Mendapatkan nilai margin
	public Double getMarginKas(Date date) {
		// nilai margin
		penjualanController.open();
		Double margin = penjualanController.getMarginKasOrMarginPiutang(date, 0);
		penjualanController.close();

		return margin;
	}

}
