package ymss.csc.models;

import java.util.Date;

public class Deposit implements AccountTransaction {

	private static final String memo = "Account Deposit";
	private static final String type = "DEPOSIT";
	
	private Double amt;
	private Double balance;
	private Date date;
	
	public Deposit(Double amt, Double balance, Date date){
		this.amt = amt;
		this.balance = balance;
		this.date = date;
	}
	
	@Override
	public String getMemo() {
		return memo;
	}

	@Override
	public Double getAccountChange() {
		return amt;
	}

	@Override
	public Double getBalance() {
		return balance;
	}

	@Override
	public Date getDate() {
		return date;
	}
	
	public static String getType(){
		return type;
	}
	
}
