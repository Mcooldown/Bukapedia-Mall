import java.util.Random;

public class Barang extends Beli{

	Random rand= new Random(); //Untuk random Harga sesuai diminta soal
	
//	Constructor ke class beli
	public Barang(int harga, String nama, int banyak) {
		super(harga, nama, banyak);
	}
	
//	Menghitung harga barang secara total
	@Override
	public void countHarga(int banyak) {
		super.harga = (rand.nextInt(100001))*banyak;
	}
	

}
