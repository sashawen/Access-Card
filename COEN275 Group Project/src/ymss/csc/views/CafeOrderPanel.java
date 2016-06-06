package ymss.csc.views;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

import ymss.csc.application.Constants;
import ymss.csc.models.AbstractVendor;
import ymss.csc.models.Cafe;
import ymss.csc.models.FoodItem;
import ymss.csc.models.Order;
import ymss.csc.models.UserAccount;
import ymss.csc.views.order.ItemPanel;
import ymss.csc.views.order.OrderPanel;

public class CafeOrderPanel extends AbstractVendorOrderPanel {

	private JPanel menuPanel;
	private JLabel lblBalance;

	private Order order;
	private OrderPanel pnlOrder;
	
	//add
	private JScrollPane menu;
	//

	public CafeOrderPanel(AbstractVendor vendor, UserAccount user) {
		super(vendor, user);
		order = new Order();
		order.addObserver(this);

		this.setLayout(new BorderLayout());

		JPanel pnlBalance = new JPanel();
		lblBalance = new JLabel("Remaining Balance:");
		lblBalance.setFont(Constants.FONT_HEADING_3);
		pnlBalance.add(lblBalance);
		add(pnlBalance, BorderLayout.NORTH);

		pnlOrder = new OrderPanel(this.order);
		pnlOrder.addPurchaseListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				purchaseOrder();

			}
		});
		add(pnlOrder, BorderLayout.EAST);

		menuPanel = new JPanel();
		menuPanel.setLayout(new GridBagLayout());

		add(menuPanel, BorderLayout.CENTER);
		//add
		menu = new JScrollPane(menuPanel);
		menu.setBounds(6, 95, 463, 425);
		add(menu, BorderLayout.CENTER);
		menu.setViewportView(menuPanel);
		menu.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		menu.setVisible(true);
		//
		
		//#########XH
//		 JRadioButton form[][] = new JRadioButton[12][5];
//		 String counts[] = { "", "0-1", "2-5", "6-10", "11-100", "101+" };
//		 String categories[] = { "testing,,,,,,,", "Office", "Extended Family",
//		        "Company (US)", "Company (World)", "Team", "Will",
//		        "Birthday Card List", "High School", "Country", "Continent",
//		        "Planet"};
		 //JPanel p = new JPanel();
//		 menuPanel.setSize(300, 100);
//		 menuPanel.setLayout(new GridLayout(13, 6, 10, 0));
//		 for (int row = 0; row < 13; row++) {
//		      ButtonGroup bg = new ButtonGroup();
//		      for (int col = 0; col < 6; col++) {
//		        if (row == 0) {
//		        	menuPanel.add(new JLabel(counts[col]));
//		        } else {
//		          if (col == 0) {
//		        	  menuPanel.add(new JLabel(categories[row - 1]));
//		          } else {
//		            form[row - 1][col - 1] = new JRadioButton();
//		            bg.add(form[row - 1][col - 1]);
//		            menuPanel.add(form[row - 1][col - 1]);
//		          }
//		        }
//		      }
//		    }
//		 
//		 	TextArea test = new TextArea();
//		 	test.setText("test.............................");
//		 	
//		 	for (int i = 0; i < 10; i++){
//		 		menuPanel.add(test);
//		 	}
//		 	
//		    menu = new JScrollPane(menuPanel);
//		    menu.setBounds(6, 95, 463, 425);
//		   add(menu, BorderLayout.CENTER);
//		   menu.setViewportView(menuPanel);
//		   menu.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//			menu.setVisible(true);
		
		
		//#########XH
		redraw();
	}

	public void purchaseOrder() {
		if (user.getRemainingBalance() >= order.getTotalCost()) {
			order.setMemo(vendor.getName());
			confirmPurchase(order);
		} else {
			JOptionPane.showMessageDialog(this, "You do not have enough funds.");
		}
	}

	public void redraw() {
		
		List<FoodItem> menu = vendor.getMenu();
		menuPanel.removeAll();

		Iterator<FoodItem> it = menu.iterator();
		while (it.hasNext()) {
			FoodItem item = it.next();
			addItem(item);
		}

		String sBalance = String.format("%.2f", user.getRemainingBalance());
		lblBalance.setText("Remaining Balance: $" + sBalance);

		if(order.isPurchased()){
			order.deleteObserver(this);
			order = new Order();
			order.addObserver(this);
			pnlOrder.setOrder(order);
		}

		repaint();
		revalidate();
	}

	private void addItem(FoodItem item) {
		ItemPanel itemPanel = new ItemPanel(item);

		itemPanel.addSelectListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				order.addItemToOrder(item);
			}
		});

		GridBagConstraints cs = new GridBagConstraints();
		cs.gridx = 0;
		cs.fill = GridBagConstraints.HORIZONTAL;
		cs.weightx = 1.0;
		cs.weighty = 1.0;
		menuPanel.add(itemPanel, cs);
	}

}
