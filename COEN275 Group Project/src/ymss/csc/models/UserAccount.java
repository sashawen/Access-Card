package ymss.csc.models;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

public class UserAccount extends Observable {

	private int cardNumber;
	private String password; // yeah... not so secure
	private DietaryProfile diet;

	private double remainingBalance;

	List<AccountTransaction> history = new ArrayList<AccountTransaction>();

	public UserAccount() {
		// nothing for now...
		diet = new DietaryProfile();
	}

	public void addTransaction(AccountTransaction t) {
		if (t == null) {
			System.out.println("Yeah... that's null.");
			return;
		}
		history.add(t);
		setChanged();
		notifyObservers();
	}

	public List<AccountTransaction> getHistory() {
		return history;
	}

	private Boolean onSameDay(Date a, Date b) {
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(a);
		cal2.setTime(b);
		boolean sameDay = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
				&& cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
		return sameDay;
	}

	public Integer getCaloriesConsumed(Date date) {
		// Assumes linear order history.
		List<AccountTransaction> trans = this.getHistory();
		Iterator<AccountTransaction> it = trans.iterator();
		Integer totalCals = 0;
		while (it.hasNext()) {
			AccountTransaction t = it.next();
			if (t instanceof Order) {
				Order order = (Order) t;
				if (onSameDay(order.getDate(), date)) {
					totalCals = totalCals + order.getTotalCalories();
				}
			}
		}
		return totalCals;
	}
	
	public Double getTotalExpenses(Date date){
		List<AccountTransaction> trans = this.getHistory();
		Iterator<AccountTransaction> it = trans.iterator();
		Double totalExpense = 0.0;
		Double oldBalance = 0.0;
		while(it.hasNext()){
			AccountTransaction t = it.next();
			if(t instanceof Order){
				Order order = (Order) t;
				if(onSameDay(order.getDate(),date)){
					totalExpense = totalExpense + oldBalance - order.getBalance();
				}
			}
			oldBalance = t.getBalance();
		}
		return totalExpense;
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

	public double getRemainingBalance() {
		return remainingBalance;
	}

	public void setRemainingBalance(Double funds) {
		remainingBalance = funds;

		setChanged();
		notifyObservers();
	}
	
}
