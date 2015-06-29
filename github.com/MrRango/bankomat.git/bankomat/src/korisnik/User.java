package korisnik;

public class User {
	
	private String username, password;
	private boolean isAdmin;
	private double amount;
	
	public User(){
		
	}
	public User(String newUsername, String newPassword, boolean newIsAdmin, double newAmount){
		username = newUsername;
		password = newPassword;
		isAdmin = newIsAdmin;
		amount = newAmount;
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
	public boolean isAdmin() {
		return isAdmin;
	}
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}

}
