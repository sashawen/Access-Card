package ymss.csc.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class UserAccount extends Observable{

	private int cardNumber;
	private String password; // yeah... not so secure
	private DietaryProfile diet;
	
	private double remainingBalance;
	
	List<AccountTransaction> history = new ArrayList<AccountTransaction>();
	
	public UserAccount(){
		// nothing for now...
		diet = new DietaryProfile();
	}
	
	public void addTransaction(AccountTransaction t){
		history.add(t);
	}
	
	public List<AccountTransaction> getHistory(){
		return history;
	}

	public DietaryProfile getDiet() {
		return diet;
	}

	public void setDiet(DietaryProfile diet) {
		this.diet = diet;

		setChanged();
		notifyObservers();
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
	
	public void setRemainingBalance(Double funds){
		remainingBalance = funds;
		
		setChanged();
		notifyObservers();
	}
}
