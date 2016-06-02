package ymss.csc.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ymss.csc.models.UserAccount;
import ymss.csc.views.FinanceFrame;
import ymss.csc.views.FundDepositFrame;

public class FinanceController {
	private FinanceFrame financeFrame;
	private FundDepositFrame fundDepositFrame;
	private UserAccount user;
	
	public void launch(UserAccount user){
		this.user = user;
		launchFinanceFrame();
	}
	
	public void launchFinanceFrame() {
		if (financeFrame == null)
			financeFrame = new FinanceFrame();

		financeFrame.setBalance(user.getRemainingBalance());
		
		financeFrame.addDepositListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				launchFundDepositFrame();
			}
		});

		financeFrame.setVisible(true);
	}

	public void launchFundDepositFrame() {
		if (fundDepositFrame == null){
			fundDepositFrame = new FundDepositFrame();
		
			fundDepositFrame.addDepositListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					Double amt = fundDepositFrame.getDeposit();
					if(finListener != null){
						finListener.fundsDeposited(amt);
					}
					fundDepositFrame.dispose();
				}
			});
		}
		
		fundDepositFrame.setVisible(true);
	}
	
	public interface FinanceListener{
		public void fundsDeposited(Double d);
	}
	
	private FinanceListener finListener;
	
	public void setFinanceListener(FinanceListener l){
		finListener = l;
	}
}
