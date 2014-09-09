package org.stn.pulsa.model;

public class Master {

	private long id;
	private String kode;
	private String saldo;
	private String hargaBeli;
	private String hargaJual;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Kode " + kode + " Harga Beli " + hargaBeli + " Harga Jual " + hargaJual;
	}

	public String getSaldo() {
		return saldo;
	}

	public void setSaldo(String saldo) {
		this.saldo = saldo;
	}

}
