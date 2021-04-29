import java.util.Scanner;
import java.util.Vector;
import java.util.Comparator;
import java.util.Random;
import java.util.Collections;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {

	Random rand = new Random();
	Scanner scan = new Scanner(System.in);
	Vector<User> userList = new Vector<>(); //List User Account
	Vector<Beli> BuyList = new Vector<>();//List Pembelian Pulsa

	//	Untuk clear screen
	void clear(){
		for (int i = 0; i < 20; i++) {
			System.out.println("");
		}
	}

	private void transactionHistory(int userNum){

		clear();
		int j=1;
		System.out.println("Transaction History");

		//		Looping History transaction
		for (int i = 0; i < userList.get(userNum).getHistory().size(); i++) {

			//			Kondisi dimana data pertama dan juga kondisi dimana data index i kodenya beda dengan data index i-1,
			//			otomatis berbeda transaksi
			if(i==0 || userList.get(userNum).getHistory().get(i).kode.equals(userList.get(userNum).getHistory().get(i-1).kode)==false){
				//				print Headernya
				System.out.println("----------------------------------------------------");
				if(userList.get(userNum).getHistory().get(i) instanceof Barang){
					System.out.println("Tipe Transaksi: Barang");
				}else System.out.println("Tipe Transaksi: Pulsa");
				System.out.println("Kode Transaksi : " +userList.get(userNum).getHistory().get(i).kode);
				System.out.println("Tanggal Transaksi: "+userList.get(userNum).getHistory().get(i).date);
				System.out.println("Item:");
				j=1;//Variabel j untuk penanda urutan item dalam transaksi yang berbeda kode
			}

			//			Print seluruh item dalam 1 transaksi
			if(userList.get(userNum).getHistory().get(i) instanceof Pulsa){
				System.out.println(j +". No HP: "+ userList.get(userNum).getHistory().get(i).nama
						+ ", Saldo: " + userList.get(userNum).getHistory().get(i).banyak
						+ ", Harga: "+ userList.get(userNum).getHistory().get(i).harga);
			}else{
				System.out.println(j +". Nama Barang: "+ userList.get(userNum).getHistory().get(i).nama
						+ ", Quantity: " + userList.get(userNum).getHistory().get(i).banyak
						+ ", Harga Satuan: "+ (userList.get(userNum).getHistory().get(i).harga/userList.get(userNum).getHistory().get(i).banyak)
						+ ", Harga Total: "+ userList.get(userNum).getHistory().get(i).harga);
			}
			j++;
		}
		System.out.println("----------------------------------------------------");
	}

	private void checkout(int userNum){
		clear();
		//		Cari tanggal hari ini
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");  
		LocalDateTime now = LocalDateTime.now();  

		String kodeTotal = ""; //Inisialisasi string kode transaksi 

		//		Looping mengisi kode transaksi
		for(int i=0; i< 5 ; i++){
			int f = 65;
			int k = rand.nextInt(25);
			f = f+k;
			char b = (char) f;
			kodeTotal = kodeTotal+b;
		}

		//		Print tanggal dan kode transaksi
		//		System.out.println(kodeTotal);
		//		System.out.println(dtf.format(now));

		//		Looping mengisi vector BuyList satu persatu dengan kode transaksi dan tanggal yang sama
		for (int i = 0; i < BuyList.size(); i++) {
			BuyList.get(i).date = dtf.format(now);
			BuyList.get(i).kode = kodeTotal;
		}

		//		Konfirmasi Pembelian
		System.out.println("Checkout");
		System.out.println("Tanggal Transaksi :"+ dtf.format(now));
		System.out.println("Kode Transaksi: "+kodeTotal);
		System.out.println("-------------------------------------------------");

		for (int i = 0; i < BuyList.size(); i++) {
			if(BuyList.get(i) instanceof Barang){
				System.out.println((i+1)+". Nama Barang:" +BuyList.get(i).nama +", Quantity: "+ BuyList.get(i).banyak +", HargaSatuan: "+ (BuyList.get(i).harga/BuyList.get(i).banyak) +", Harga Total: "+ BuyList.get(i).harga);					
			}else{
				System.out.println((i+1)+". No HP:" +BuyList.get(i).nama +", Saldo: "+ BuyList.get(i).banyak +", Harga: "+ BuyList.get(i).harga);
			}
		}
		System.out.println("-------------------------------------------------");

		//		Masukin vector BuyList ke vector Transaction History masing masing user
		userList.get(userNum).setHistory(BuyList);

		//		Clear isi vector BuyList
		BuyList.clear();

		//		Sebelum balik ke menu
		System.out.print("Transaksi disimpan, Press enter to continue...");
	}

	private void ViewKeranjang(int choose){

		//		Sort by harga barang ascending jika choose =2
		if(choose ==2){
			Collections.sort(BuyList, new Comparator<Beli>() {
				public int compare(Beli step1, Beli step2){
					return Integer.valueOf(step1.harga).compareTo(step2.harga);
				}
			});

			//		Sort by nama/no HP ascending jika choose =3
		}else if(choose ==3){
			Collections.sort(BuyList, new Comparator<Beli>() {
				public int compare(Beli step1, Beli step2){
					return String.valueOf(step1.nama).compareTo(step2.nama);
				}
			});
		}

		//		View Menu
		int menu =-1; //variabel menu untuk pilih remove atau balik ke menu
		do{
			//			View List Keranjang 
			clear();
			System.out.println("List Keranjang");
			System.out.println("-------------------------------------------------");

			for (int i = 0; i < BuyList.size(); i++) {
				if(BuyList.get(i) instanceof Barang){
					System.out.println((i+1)+". Nama Barang:" +BuyList.get(i).nama +", Quantity: "+ BuyList.get(i).banyak +", HargaSatuan: "+ (BuyList.get(i).harga/BuyList.get(i).banyak) +", Harga Total: "+ BuyList.get(i).harga);					
				}else{
					System.out.println((i+1)+". No HP:" +BuyList.get(i).nama +", Saldo: "+ BuyList.get(i).banyak +", Harga: "+ BuyList.get(i).harga);
				}
			}

			//			Menu untuk Delete & Back to menu
			System.out.println("------------------------------------------------");
			System.out.println("1. Remove Item by number");
			System.out.println("2. Remove All Item");
			System.out.println("3. Back to menu");
			System.out.print("Choose >>");

			//			Terima input variabel menu
			try {
				menu = scan.nextInt();							
			} catch (Exception e) {
				menu =-1;
			}
			scan.nextLine();

			switch (menu) {
			//			Remove Item
			case 1:
				int select=-1;//variabel nampung index yang mau diremove

				//				Input index yang mau didelete
				do{
					System.out.print("Select item to be removed [1-"+ BuyList.size()+"]:");
					try {
						select = scan.nextInt();
					} catch (Exception e) {
						select = -1;
					}
					scan.nextLine();

				}while(select<1 || select > BuyList.size());

				//				Remove item sesuai variable select-1
				BuyList.remove(select-1);

				//				Message sebelum ke home
				System.out.print("Item succesfully removed, Press enter to continue...");
				scan.nextLine();
				break;
			case 2:
				BuyList.clear();
				System.out.print("All Item succesfully removed, Press enter to continue...");
				scan.nextLine();
				break;
			}

		}while( menu <1 || menu >3 );

	}

	private void menuPulsa(int userNum){

		int menu = -1; //variabel untuk akses menu di MenuPulsa
		String noHP = null; //inisialisasi nomor HP yang mau diisi saldo
		int saldo = 0; //inisialisasi saldo HP yang belum diisi

		do {
			//			Menu Beli Pulsa
			clear();
			System.out.println("Menu Beli Pulsa");
			System.out.println("--------------------------------------");
			System.out.println("1.Add item");
			System.out.println("2.Lihat Keranjang (sort by harga)");
			System.out.println("3.Lihat Keranjang (sort by nama item)");
			System.out.println("4.Checkout");
			System.out.print("Choose >>");

			//			Terima input variabel menu
			try {				
				menu= scan.nextInt();
			} catch (Exception e) {
				menu =-1;
				// TODO: handle exception
			}
			scan.nextLine();

			switch (menu) {
			case 1:
				//				Add Item
				clear();
				boolean check; //variabel yang menandakan apakah noHP nya numeric semua

				//				Input nomor HP
				do{
					do{
						System.out.print("Input Nomor HP [10-13 characters]:");
						noHP= scan.nextLine();
						noHP= noHP.trim();
					}while(noHP.length()<10 || noHP.length()>13);

					//					Kalo checknya true = angka bukan numeric semua, kalo false artinya sudah numeric semua/benar
					check =false;
					for (int i = 0; i < noHP.length(); i++) {
						if(noHP.charAt(i)<48 || noHP.charAt(i)>57){
							check = true;
							break;
						}
					}

				}while(check ==true);//Loop selama checknya true

				//				Input saldo
				do{
					System.out.print("Input saldo: ");
					try {
						saldo = scan.nextInt();
					} catch (Exception e) {
						saldo=0;
					}
					scan.nextLine();

				}while(saldo <=0);

				//				Add noHP dan saldo ke class penampung (tanpa harga total)
				Beli temp = new Pulsa(0, noHP, saldo);

				//				Hitung harga total sekalian disimpan di class penampung
				temp.countHarga(saldo);

				//				Tambahkan class penampung ke vector PulsaList
				BuyList.add(temp);

				//				Message terakhir sebelum ke menu
				System.out.print("Item added, Press enter to continue...");
				scan.nextLine();
				break;

			case 2:
				//SORT BERDASARKAN HARGA TERENDAH
				clear();

				//				Kalau ada data di vektor Pulsa
				if(BuyList.isEmpty()==false){	
					ViewKeranjang(2);
				}
				//				Kalau tidak ada data
				else{
					System.out.print("Item empty, Press enter to continue...");
					scan.nextLine();
				}
				break;

			case 3:
				//SORT BERDASARKAN PHONE NUMBER
				clear();

				//				Kalau ada data di vektor pulsa
				if(BuyList.isEmpty()==false){	
					ViewKeranjang(3);
				}
				//				Kalau tidak ada data
				else{
					System.out.print("Item empty, Press enter to continue");
					scan.nextLine();
				}
				break;

			case 4:
				//				Jalankan fungsi Checkout jika ada barang yang dibeli, jika tidak langsung keluar
				if(BuyList.isEmpty()==false){
					checkout(userNum);
				}
				else{
					System.out.print("Item empty, Press enter to continue...");
				}
				scan.nextLine();
				break;
			}

		}while (menu!=4);
	}

	private void menuBarang(int userNum){
		int menu = -1; //variabel untuk akses menu di MenuPulsa
		String nama = null; //inisialisasi nama barang
		int qty =0; //inisialisasi var qty

		do {
			//			Menu Beli Barang
			clear();
			System.out.println("Menu Beli Barang");
			System.out.println("--------------------------------------");
			System.out.println("1.Add item");
			System.out.println("2.Lihat Keranjang (sort by harga)");
			System.out.println("3.Lihat Keranjang (sort by nama item)");
			System.out.println("4.Checkout");
			System.out.print("Choose >>");

			//			Terima input variabel menu
			try {				
				menu= scan.nextInt();
			} catch (Exception e) {
				menu =-1;
			}
			scan.nextLine();

			switch (menu) {
			case 1:
				//				Add Item
				clear();

				//				Input nama barang
				do{
					System.out.print("Input Nama Barang:");
					nama= scan.nextLine();
					nama= nama.trim();

				}while(nama.length()==0);

				//				Input saldo
				do{
					System.out.print("Input quantity: ");
					try {
						qty = scan.nextInt();
					} catch (Exception e) {
						qty=0;
					}
					scan.nextLine();

				}while(qty<=0);

				//				Add noHP dan saldo ke class penampung (tanpa harga total)
				Beli temp = new Barang(0, nama, qty);

				//				Hitung harga total sekalian disimpan di class penampung
				temp.countHarga(qty);

				//				Tambahkan class penampung ke vector PulsaList
				BuyList.add(temp);

				//				Message terakhir sebelum ke menu
				System.out.print("Item added, Press enter to continue...");
				scan.nextLine();
				break;

			case 2:
				//SORT BERDASARKAN HARGA TERENDAH
				clear();

				//				Kalau ada data di vektor Pulsa
				if(BuyList.isEmpty()==false){	
					ViewKeranjang(2);
				}
				//				Kalau tidak ada data
				else{
					System.out.print("Item empty, Press enter to continue...");
					scan.nextLine();
				}
				break;

			case 3:
				//SORT BERDASARKAN PHONE NUMBER
				clear();

				//				Kalau ada data di vektor pulsa
				if(BuyList.isEmpty()==false){	
					ViewKeranjang(3);
				}
				//				Kalau tidak ada data
				else{
					System.out.print("Item empty, Press enter to continue");
					scan.nextLine();
				}
				break;

			case 4:
				//				Jalankan fungsi Checkout jika ada barang yang dibeli, jika tidak langsung keluar
				if(BuyList.isEmpty()==false){
					checkout(userNum);
				}
				else{
					System.out.print("Item empty, Press enter to continue...");
				}
				scan.nextLine();
				break;
			}

		}while (menu!=4);
	}

	private void userMenu(int userNum){

		int menu =-1; //variabel penampung untuk mengarahkan ke menu nomor brapa
		//		Menu User Awal
		do{
			clear();
			System.out.println("Welcome, "+ userList.get(userNum).getUsername()); //Tampilan nama user di awal menu
			System.out.println("------------------------");
			System.out.println("Menu Bukapediamall");
			System.out.println("1.Beli Pulsa");
			System.out.println("2.Beli Barang");
			System.out.println("3.Transaction History");
			System.out.println("4.Logout");
			System.out.print("Choose >>");

			//			Handle input variabel menu
			try {
				menu = scan.nextInt();
			} catch (Exception e) {
				menu = -1;
			}
			scan.nextLine();

			switch (menu) {
			case 1:
				//				Menampilkan menu beli pulsa
				menuPulsa(userNum);
				break;

			case 2:
				//				Menampilkan menu beli Barang
				menuBarang(userNum);
				break;
			case 3:
				//				Menampilkan transaction history dari user
				if(userList.get(userNum).getHistory().isEmpty()== false){
					transactionHistory(userNum);
				}
				else{
					System.out.print("No Transaction History. ");
				}
				System.out.print("Press enter to continue...");
				scan.nextLine();
				break;
			}

		}while(menu!=4);
	}

	private void userList(){

		System.out.println("User List");
		System.out.println("-----------------------------------------------");
		for (int i = 1; i < userList.size(); i++) {
			System.out.println("User Number: "+(i) + ", User Name: " + userList.get(i).getUsername());
		}
		System.out.println("-----------------------------------------------");

	}

	private void adminMenu(){

		int menu=-1;
		do{
			clear();
			System.out.println("Admin Menu | Bukapediamall");
			System.out.println("------------------------------");
			System.out.println("1.View User List");
			System.out.println("2.View User Transaction History");
			System.out.println("3.Delete User");
			System.out.println("4.Logout");
			System.out.print("Choose >> ");

			try {
				menu = scan.nextInt();
			} catch (Exception e) {
				menu=-1;
			}
			scan.nextLine();

			switch (menu) {
			case 1:
				clear();
				if(userList.size()>1){
					userList();
				}else{
					System.out.print("No User Account. ");
				}
				System.out.print("Press enter to continue...");
				scan.nextLine();
				break;

			case 2:
				clear();
				if(userList.size()>1){
					userList();

					int choose =-1;
					do{
						System.out.print("Select user to see transaction history [1-" + (userList.size()-1) + "]:");
						try {
							choose=scan.nextInt();
						} catch (Exception e) {
							choose =-1;
						}
						scan.nextLine();

					}while(choose<1 || choose> userList.size()-1 );

					transactionHistory(choose);
				}else{
					System.out.print("No User Account. ");
				}
				System.out.print("Press enter to continue");
				scan.nextLine();
				break;

			case 3:
				clear();
				if(userList.size()>1){
					userList();

					int choose =-1;
					do{
						System.out.print("Select user to delete [1-" + (userList.size()-1) + "][0 to back]:");
						try {
							choose=scan.nextInt();
						} catch (Exception e) {
							choose =-1;
						}
						scan.nextLine();

					}while(choose<0 || choose> userList.size()-1 );

					if(choose >0){
						userList.remove(choose);
						System.out.print("User has been removed.");
						System.out.print("Press enter to continue");
						scan.nextLine();
					}

				}else{
					System.out.print("No User Account. ");
					System.out.print("Press enter to continue");
					scan.nextLine();
				}
				break;
			}

		}while(menu!=4);
	}

	public Main() {

		int menu = -1; //variabel penampung menu ke brapa
		String username= null; //inisialisasi string username
		String password = null; //inisialisasi string password
		boolean check= false; // tanda username sudah dipakai ketika regist / salah username & password ketika login
		userList.add(new User(null,"admin_user","admin_user")); //Add account admin ke vector user , vektor history transaksi di nullkan

		//		Menu Awal sebelum login
		System.out.println("Scroll down");
		do {
			clear();
			System.out.println("Welcome to Bukapedia Mall");
			System.out.println("1.Login");
			System.out.println("2.Register");
			System.out.println("3.Exit");
			System.out.print("Choose >> ");

			//			Handle input variabel menu
			try {
				menu = scan.nextInt();				
			} catch (Exception e) {
				menu =-1;
			}
			scan.nextLine();

			switch (menu) {
			//			Login Menu
			case 1:
				clear();

				//				Kalau ada data user ->bisa login
				if(userList.isEmpty()== false){
					System.out.print("Enter your username:");
					username = scan.nextLine();
					System.out.print("Enter your password:");
					password = scan.nextLine();

					check=false;//inisialisasi check

					//					Validasi apakah username dan passwordnya sama
					for (int i = 0; i < userList.size(); i++) {
						if(userList.get(i).getUsername().equals(username)){
							if(userList.get(i).getPassword().equals(password)){
								//								Berhasil Login , check ditandai true + jalanin menu User
								check=true;
								if(userList.get(i).getUsername().equals("admin_user"))adminMenu();
								else{
									userMenu(i);									
								}
								break;
							}
						}
					}

					//					Kalo checknya false -> gagal login karena salah username/password
					if(check==false){
						System.out.print("Invalid username or password!!. Press enter to continue...");
						scan.nextLine();
					}

				}

				//				Kalau tidak ada data user -> tidak ada account ->tidak ada yg bisa login
				else{
					System.out.print("Empty user, register your account first. Press enter to continue..");
					scan.nextLine();
				}
				break;

			case 2:
				//				Menu Register
				clear();
				do{					
					do{
						System.out.print("Enter your username [must unique][5-20 characters]:");
						username = scan.nextLine();
						username = username.trim();
					}while(username.length()<5 || username.length()>20);

					//					Validasi apakah usernamenya sudah dipakai atau belum
					check = false;
					for (int i = 0; i < userList.size(); i++) {
						if(userList.get(i).getUsername().equals(username)){
							System.out.println("Username taken.");
							check =true;
						}
					}

				}while(check==true);

				do{
					System.out.print("Enter your password [8-20 characters]:");
					password = scan.nextLine();					
				}while(password.length()<8 || password.length()>20);

				userList.add(new User(null,username,password)); //Add account baru ke vector user , vektor history transaksi di nullkan dulu

				//				Sudah selesai register
				System.out.print("Successfull Register account! Press enter to continue...");
				scan.nextLine();
				break;
			}
		} while (menu!=3);
	}

	public static void main(String[] args) {
		new Main();
	}

}
