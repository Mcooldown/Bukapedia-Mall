public abstract class Beli {
	
	protected int harga;// Total harga pulsa / harga barang
	protected String nama;//No HP/ Nama barang
	protected int banyak;//Jumlah saldo/ jumlah barang
	protected String date;//tanggal beli
	protected String kode;//kode transaksi
	
	public Beli(int harga, String nama, int banyak) {
		super();
		this.harga = harga;
		this.nama = nama;
		this.banyak = banyak;
	}

	public abstract void countHarga(int banyak);

}
