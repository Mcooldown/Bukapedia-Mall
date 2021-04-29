import java.util.Vector;

public class User {
	
//	Didalam class user ada username,password, dan detail transaction history
	private String username;
	private String password;
	private Vector<Beli> History = new Vector<>();

//	RETURN VECTOR UNTUK DIAKSES MAIN
	public Vector<Beli> getHistory() {
		return History;
	}
	
//	MERGE VECTOR YANG SUDAH ADA
	public void setHistory(Vector<Beli> BuyList) {
		History.addAll(BuyList);
	}

	public User(Vector<Beli> History,String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	

	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
