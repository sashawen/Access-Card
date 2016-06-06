package ymss.csc.views;

import javax.swing.JFrame;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import ymss.csc.models.Deposit;
import ymss.csc.models.UserAccount;

import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.awt.Component;
import java.awt.GridLayout;

public class FundDepositFrame extends JFrame {
private static final long serialVersionUID = 7093746272356081378L;
	
	static final String title = "Deposit Funds";
	private JTextField tfDeposit;
	private JButton btnDeposit;
	
	private UserAccount user;
	
	public FundDepositFrame(UserAccount user) {
		this.user = user;
		
		// Window initialization
		setTitle(title);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		
		JPanel pnlMain = new JPanel();
		getContentPane().add(pnlMain);
		pnlMain.setBorder(new EmptyBorder(10, 25, 10, 25));
		pnlMain.setLayout(new BoxLayout(pnlMain, BoxLayout.Y_AXIS));
		
		initForm(pnlMain);		
		initButtons(pnlMain);
		
		//this.setSize(366, 306);
		pack();
	}
	
	private void initForm(JPanel parent){
		if(parent == null) return;
		
		JPanel pnlForm = new JPanel();
		pnlForm.setBorder(new EmptyBorder(10, 0, 10, 0));
		pnlForm.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		pnlForm.setLayout(new BoxLayout(pnlForm, BoxLayout.X_AXIS));
		
		JLabel lblDeposit = new JLabel("Deposit Amount ($): ");
		pnlForm.add(lblDeposit);
		
		tfDeposit = new JTextField();
		pnlForm.add(tfDeposit);
		tfDeposit.setText("");
		tfDeposit.setColumns(10);
		
		parent.add(pnlForm);
	}
	
	private void initButtons(JPanel parent){
		if(parent == null) return;
		
		JPanel pnlButtons = new JPanel();
		pnlButtons.setBorder(null);
		pnlButtons.setLayout(new GridLayout(0, 2, 30, 0));
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				dispose();
			}
		});
		pnlButtons.add(btnCancel);
		
		btnDeposit = new JButton("Deposit");
		btnDeposit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Double newBalance = user.getRemainingBalance() + getDeposit();
				user.setRemainingBalance(newBalance);
				
				Deposit d = new Deposit(getDeposit(), newBalance, new Date());
				user.addTransaction(d);
				dispose();
			}
		});
		pnlButtons.add(btnDeposit);
		
		parent.add(pnlButtons);
	}
	
	public Double getDeposit(){
		String sDeposit = tfDeposit.getText().trim();
		try{
			return Double.parseDouble(sDeposit);
		}catch(Exception e){
			return 0.0;
		}
	}
}
