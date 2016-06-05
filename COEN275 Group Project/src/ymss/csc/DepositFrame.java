package ymss.csc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.geom.Area;
import java.util.Observable;
import java.util.Observer;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JComboBox;

public class DepositFrame extends JFrame implements Observer{

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DepositFrame frame = new DepositFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public DepositFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 677, 384);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblMyAccount = new JLabel("My Account");
		lblMyAccount.setBounds(6, 6, 84, 30);
		contentPane.add(lblMyAccount);
		
		textField = new JTextField();
		textField.setBounds(220, 81, 134, 28);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnDeposit = new JButton("Fill Account");
		btnDeposit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnDeposit.setBounds(98, 82, 110, 29);
		contentPane.add(btnDeposit);
		
		JLabel lblCurrentBalance = new JLabel("Current Balance");
		lblCurrentBalance.setBounds(98, 42, 110, 16);
		contentPane.add(lblCurrentBalance);
		
		textField_1 = new JTextField();
		textField_1.setBounds(220, 36, 134, 28);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JButton btnViewExpense = new JButton("View Expense");
		btnViewExpense.setBounds(98, 175, 110, 29);
		contentPane.add(btnViewExpense);
		
		JLabel lblDate = new JLabel("Date:");
		lblDate.setBounds(557, 42, 61, 16);
		contentPane.add(lblDate);
		
		JPanel panel = new JPanel();
		panel.setBounds(220, 161, 434, 195);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblExpensePeriod = new JLabel("Expense Period");
		lblExpensePeriod.setBounds(108, 129, 107, 27);
		contentPane.add(lblExpensePeriod);
		
		JComboBox expensePeriod = new JComboBox();
		expensePeriod = new JComboBox(new String[] { "Please select ...", "7 days", "30 days", "60 days"});
		
		
		expensePeriod.addItemListener(new ItemListener() {
		      public void itemStateChanged(ItemEvent ie) {
		        repaint();
		      }
		    });
		//controls.add(expensePeriod);
		
		expensePeriod.setBounds(220, 130, 134, 27);
		contentPane.add(expensePeriod);
		
		JLabel lblBackHome = new JLabel("Back Home");
		lblBackHome.setBounds(557, 13, 78, 16);
		contentPane.add(lblBackHome);
	}
	
	
	public void paintComponent(Graphics g) {
	  	super.paintComponents(g);
	    Graphics2D g2 = (Graphics2D)g;
	    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	        RenderingHints.VALUE_ANTIALIAS_ON);
	    
	    // Retrieve the selection option from the combo box.
	    //String option = (String)expensePeriod.getSelectedItem();
	    //f (option.equals("7 days")) {
	      // Just draw the outlines and return.
	       //g2.draw(polyline7);
	      
	     // return;
	    //}
	    // Create Areas from the shapes.
	    //Area areaOne = new Area(ShapeOne);
	    //Area areaTwo = new Area(mShapeTwo);
	    // Combine the Areas according to the selected option.
	    //if (option.equals("7 days")) areaOne.add(g2.draw(polyline7);
	   
	    // Fill the resulting Area.
	    g2.setPaint(Color.orange);
	    //g2.fill(areaOne);
	    
	   
	  }

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
	}



