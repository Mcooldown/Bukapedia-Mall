
public class Pulsa extends Beli{

//	Constructor ke class Beli
	public Pulsa(int harga, String nama, int banyak) {		
		super(harga, nama, banyak);
	}
	
//	Menghitung harga pulsa
	@Override
	public void countHarga(int banyak) {
		super.harga = banyak+(banyak*10/100);
	}
	
	
}
