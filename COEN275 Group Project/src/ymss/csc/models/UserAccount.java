package ymss.csc.models;

public class UserAccount {

	private int cardNumber;
	private String password; // yeah... not so secure
	private DietaryProfile diet;
	
	private double remainingBalance;
	
	List<Order> orderHistory = new ArrayList<Order>();
	
	public UserAccount(){
		// nothing for now...
	}

	public DietaryProfile getDiet() {
		return diet;
	}

	public void setDiet(DietaryProfile diet) {
		this.diet = diet;
	}

	public int getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(int cardNumber) {
		this.cardNumber = cardNumber;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public double getRemainingBalance(){
		return remainingBalance;
	}
}
