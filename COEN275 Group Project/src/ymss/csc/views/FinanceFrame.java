package ymss.csc.views;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class FinanceFrame extends JFrame {

	private static final long serialVersionUID = -126850826291601022L;
	
	static final String title = "My Financial Profile";
	
	private JButton btnDeposit;

	public FinanceFrame() {
		// Window initialization
		setTitle(title);
		setSize(800, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		JPanel tempPanel = new JPanel();
		this.getContentPane().add(tempPanel);

		btnDeposit = new JButton("Deposit Funds");
		tempPanel.add(btnDeposit);
	}
	
	public void addDepositListener(ActionListener l){
		btnDeposit.addActionListener(l);
	}
	
	public void removeDepositListener(ActionListener l){
		btnDeposit.removeActionListener(l);
	}

}
