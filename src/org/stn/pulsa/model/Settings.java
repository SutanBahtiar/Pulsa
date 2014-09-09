package org.stn.pulsa.model;

public class Settings {

	private long id;
	private String tanggal;
	private String saldo;
	private String kas;
	private String piutang;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getKas() {
		return kas;
	}

	public void setKas(String kas) {
		this.kas = kas;
	}

	public String getPiutang() {
		return piutang;
	}

	public void setPiutang(String piutang) {
		this.piutang = piutang;
	}

	public String getSaldo() {
		return saldo;
	}

	public void setSaldo(String saldo) {
		this.saldo = saldo;
	}

	public String getTanggal() {
		return tanggal;
	}

	public void setTanggal(String tanggal) {
		this.tanggal = tanggal;
	}

}
