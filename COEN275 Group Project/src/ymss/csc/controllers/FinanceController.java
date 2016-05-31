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

		financeFrame.addDepositListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				launchFundDepositFrame();
			}
		});

		financeFrame.setVisible(true);
	}

	public void launchFundDepositFrame() {
		if (fundDepositFrame == null)
			fundDepositFrame = new FundDepositFrame();
		fundDepositFrame.setVisible(true);
	}
}
