import java.awt.EventQueue;
import java.awt.Image;
import java.awt.image.*;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class CSC {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JPanel panel_1;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CSC window = new CSC();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CSC() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 732, 480);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnLogout = new JMenu("Account");
		mnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//JOptionPane.showMessageDialog(null, "Logged out");
				System.exit(JFrame.EXIT_ON_CLOSE);
			}
		});
		menuBar.add(mnLogout);
		
		JMenuItem mntmFund = new JMenuItem("Add fund");
		mnLogout.add(mntmFund);
		
		JMenuItem mntmExpenseProfile = new JMenuItem("View expense");
		mnLogout.add(mntmExpenseProfile);
		
		JMenuItem mntmFoodPreferences = new JMenuItem("Food Preferences");
		menuBar.add(mntmFoodPreferences);
		frame.getContentPane().setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(6, 6, 449, 408);
		frame.getContentPane().add(tabbedPane);
		
		JPanel panel_1 = new JPanel();
		//Image img = new ImageIcon(this.getClass().getResource("/cafePic.png")).getImage();

		tabbedPane.addTab("Cafe", null, panel_1, null);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Vending Machine", null, panel, null);
		
		JLabel lblNewLabel = new JLabel("Available Fund:");
		lblNewLabel.setBounds(477, 66, 100, 43);
		frame.getContentPane().add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(579, 66, 134, 43);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(477, 134, 235, 174);
		frame.getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblSelectedLocation = new JLabel("Selected Location:");
		lblSelectedLocation.setBounds(6, 6, 142, 29);
		panel_2.add(lblSelectedLocation);
		
		JLabel lblCafeNo = new JLabel("Cafe No.");
		lblCafeNo.setHorizontalAlignment(SwingConstants.CENTER);
		lblCafeNo.setBounds(39, 47, 69, 40);
		panel_2.add(lblCafeNo);
		
		textField_1 = new JTextField();
		textField_1.setBounds(139, 47, 38, 40);
		panel_2.add(textField_1);
		textField_1.setColumns(10);
		
		JButton btnNewButton = new JButton("Go!");
		btnNewButton.setBounds(182, 48, 48, 40);
		panel_2.add(btnNewButton);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(6, 88, 224, 12);
		panel_2.add(separator_1);
		
		JLabel lblNewLabel_1 = new JLabel("Vending Machine No.");
		lblNewLabel_1.setBounds(6, 112, 133, 40);
		panel_2.add(lblNewLabel_1);
		
		textField_2 = new JTextField();
		textField_2.setBounds(139, 112, 38, 40);
		panel_2.add(textField_2);
		textField_2.setColumns(10);
		
		JButton button = new JButton("Go!");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		button.setBounds(182, 113, 48, 40);
		panel_2.add(button);
		
		JButton btnNewButton_1 = new JButton("Log out");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_1.setBounds(549, 350, 117, 51);
		frame.getContentPane().add(btnNewButton_1);
	
		
	}
}
