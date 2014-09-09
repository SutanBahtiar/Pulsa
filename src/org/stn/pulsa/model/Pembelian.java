package org.stn.pulsa.model;

public class Pembelian {

	private long id;
	private String tanggal;
	private String saldo;
	private String kas;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTanggal() {
		return tanggal;
	}

	public void setTanggal(String tanggal) {
		this.tanggal = tanggal;
	}

	public String getSaldo() {
		return saldo;
	}

	public void setSaldo(String saldo) {
		this.saldo = saldo;
	}

	public String getKas() {
		return kas;
	}

	public void setKas(String kas) {
		this.kas = kas;
	}

}
