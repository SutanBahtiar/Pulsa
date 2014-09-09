package org.stn.pulsa.model;

public class Penjualan {

	private long id;
	private String tanggal;
	private String nama;
	private String telepon;
	private String kode;
	private String saldo;
	private String hargaBeli;
	private String hargaJual;
	private String margin;
	private int status;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public String getKode() {
		return kode;
	}

	public void setKode(String kode) {
		this.kode = kode;
	}

	public String getHargaBeli() {
		return hargaBeli;
	}

	public void setHargaBeli(String hargaBeli) {
		this.hargaBeli = hargaBeli;
	}

	public String getHargaJual() {
		return hargaJual;
	}

	public void setHargaJual(String hargaJual) {
		this.hargaJual = hargaJual;
	}

	public String getMargin() {
		return margin;
	}

	public void setMargin(String margin) {
		this.margin = margin;
	}

	public String getTanggal() {
		return tanggal;
	}

	public void setTanggal(String tanggal) {
		this.tanggal = tanggal;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getSaldo() {
		return saldo;
	}

	public void setSaldo(String saldo) {
		this.saldo = saldo;
	}

	public String getTelepon() {
		return telepon;
	}

	public void setTelepon(String telepon) {
		this.telepon = telepon;
	}

}
