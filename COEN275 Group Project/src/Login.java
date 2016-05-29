import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Window;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField tf_username;
	private JTextField tf_password;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login login = new Login();
					login.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 677, 384);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel logo = new JLabel("Welcome to ScoYi Online Order");
		logo.setFont(new Font("Hiragino Kaku Gothic StdN", Font.BOLD | Font.ITALIC, 17));
		logo.setHorizontalAlignment(SwingConstants.CENTER);
		logo.setBounds(94, 17, 472, 54);
		contentPane.add(logo);
		
		tf_username = new JTextField();
		tf_username.setBounds(466, 118, 159, 38);
		contentPane.add(tf_username);
		tf_username.setColumns(10);
		
		JLabel lbl_username = new JLabel("UserName:");
		lbl_username.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_username.setBounds(385, 123, 80, 28);
		contentPane.add(lbl_username);
		
		JButton btn_login = new JButton("Login");
		btn_login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				MainMenu mainMenu = new MainMenu();
				mainMenu.setVisible(true);
			}
		});
		btn_login.setBounds(395, 236, 96, 29);
		contentPane.add(btn_login);
		
		JButton btn_cancel = new JButton("Cancel");
		btn_cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tf_username.setText("");
				tf_password.setText("");
			}
		});
		btn_cancel.setBounds(529, 236, 96, 29);
		contentPane.add(btn_cancel);
		
		JLabel logoPic = new JLabel("New label");
		logoPic.setBounds(59, 85, 272, 240);
		contentPane.add(logoPic);
		Image img = new ImageIcon(this.getClass().getResource("login.jpg")).getImage();		
		logoPic.setIcon(new ImageIcon(img));
		
		JLabel lbl_password = new JLabel("Password:");
		lbl_password.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_password.setBounds(385, 175, 80, 28);
		contentPane.add(lbl_password);
		
		tf_password = new JPasswordField();
		tf_password.setColumns(10);
		tf_password.setBounds(466, 170, 159, 38);
		contentPane.add(tf_password);
	}

}
