package ymss.csc.views;

import javax.swing.JFrame;

public class FundDepositFrame extends JFrame {
private static final long serialVersionUID = 7093746272356081378L;
	
	static final String title = "Deposit Funds";

	public FundDepositFrame() {
		// Window initialization
		setTitle(title);
		setSize(800, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
}
