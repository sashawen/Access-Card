package ymss.csc.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class CredentialFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4121023135199984623L;
	
	protected JTextField tfCredential;
	protected JPasswordField pfPassword;
	protected JLabel lbCredential;
	protected JLabel lbPassword;
	protected JButton btnAuthenticate;
	protected JButton btnCancel;
	
	public void initialize(){
		setTitle("Authenticate");
	}

	public CredentialFrame() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints cs = new GridBagConstraints();

		cs.fill = GridBagConstraints.HORIZONTAL;

		lbCredential = new JLabel("Credential : ");
		cs.gridx = 0;
		cs.gridy = 0;
		cs.gridwidth = 1;
		panel.add(lbCredential, cs);

		tfCredential = new JTextField(20);
		cs.gridx = 1;
		cs.gridy = 0;
		cs.gridwidth = 2;
		panel.add(tfCredential);

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

		btnAuthenticate = new JButton("Submit");
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				dispose();
			}
		});

		JPanel bp = new JPanel();
		bp.add(btnAuthenticate);
		bp.add(btnCancel);

		getContentPane().add(panel, BorderLayout.CENTER);
		getContentPane().add(bp, BorderLayout.PAGE_END);

		pack();
		setResizable(false);
		setLocationRelativeTo(null);

		initialize();
	}

	public void addAuthListener(ActionListener loginListener) {
		btnAuthenticate.addActionListener(loginListener);
	}

	public Integer getCardNumber() {
		try {
			return Integer.parseInt(tfCredential.getText().trim());
		} catch (Exception e) {
			return 0;
		}
	}

	public String getPassword() {
		return new String(pfPassword.getPassword());
	}
}
