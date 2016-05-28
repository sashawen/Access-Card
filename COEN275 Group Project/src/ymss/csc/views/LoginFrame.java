package ymss.csc.views;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class LoginFrame extends JFrame {

	private static final long serialVersionUID = 2654206624481274499L;
	
	private JTextField tfCardNumber;
	private JPasswordField pfPassword;
	private JLabel lbCardNumber;
	private JLabel lbPassword;
	private JButton btnLogin;
	private JButton btnCancel;
	
	public LoginFrame(String appTitle){
		setTitle("Login");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints cs = new GridBagConstraints();
		
		cs.fill = GridBagConstraints.HORIZONTAL;
		
		lbCardNumber = new JLabel("Card No.: ");
		cs.gridx = 0;
		cs.gridy = 0;
		cs.gridwidth = 1;
		panel.add(lbCardNumber,cs);
		
		tfCardNumber = new JTextField(20);
		cs.gridx = 1;
		cs.gridy = 0;
		cs.gridwidth = 2;
		panel.add(tfCardNumber);
		
		lbPassword = new JLabel("Password: ");
        cs.gridx = 0;
        cs.gridy = 1;
        cs.gridwidth = 1;
        panel.add(lbPassword, cs);
        
        pfPassword = new JPasswordField(20);
        cs.gridx = 1;
        cs.gridy = 1;
        cs.gridwidth = 2;
        panel.add(pfPassword, cs);
        panel.setBorder(new LineBorder(Color.GRAY));
        
        btnLogin = new JButton("Login");
        btnCancel = new JButton("Cancel");
        
        JPanel bp = new JPanel();
        bp.add(btnLogin);
        bp.add(btnCancel);
        
        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(bp, BorderLayout.PAGE_END);
 
        pack();
        setResizable(false);
		setLocationRelativeTo(null);
        
	}
		
	public void addLoginListener(ActionListener loginListener){
		btnLogin.addActionListener(loginListener);
	}
	public void removeLoginListener(ActionListener loginListener){
		btnLogin.removeActionListener(loginListener);
	}
	public void addCancelListener(ActionListener l){
		btnCancel.addActionListener(l);
	}
	public void removeCancelListener(ActionListener l){
		btnCancel.removeActionListener(l);
	}
	
	public Integer getCardNumber(){
		try{
			return Integer.parseInt(tfCardNumber.getText().trim());
		}catch(Exception e){
			return 0;
		}
	}
	
	public String getPassword(){
		return new String(pfPassword.getPassword());
	}
	
}
