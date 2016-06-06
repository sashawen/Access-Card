package ymss.csc.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ymss.csc.models.AbstractVendor;
import ymss.csc.models.FoodItem;
import ymss.csc.models.Order;
import ymss.csc.models.UserAccount;
import ymss.csc.models.VendingMachine;
import ymss.csc.views.order.ItemPanel;
import java.awt.GridLayout;
import javax.swing.SwingConstants;

public class VendingMachineOrderPanel extends AbstractVendorOrderPanel {

	private static final long serialVersionUID = -8636303345623272091L;

	private JPanel menuPanel;
	private JLabel lblBalance;
	private JLabel lblCaloriesLeft;
	
	public VendingMachineOrderPanel(){
		VendingMachine vendor = new VendingMachine();
		vendor.addItemToMenu(FoodItem.getItem(0));
		vendor.addItemToMenu(FoodItem.getItem(1));
		vendor.addItemToMenu(FoodItem.getItem(2));
		vendor.addItemToMenu(FoodItem.getItem(3));
		
		UserAccount user = new UserAccount();
		init(vendor,user);
	}
	
	public VendingMachineOrderPanel(AbstractVendor vendor, UserAccount user) {
		super(vendor, user);
	
		init(vendor,user);
	}
	
	private void init(AbstractVendor vendor, UserAccount user){
		setLayout(new BorderLayout());

		JPanel pnlBalance = new JPanel();
		pnlBalance.setLayout(new GridLayout(0, 2, 0, 0));
		pnlBalance.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
		
		lblBalance = new JLabel("Remaining Balance:");
		lblBalance.setHorizontalAlignment(SwingConstants.CENTER);
		pnlBalance.add(lblBalance);
		add(pnlBalance, BorderLayout.NORTH);
		
		lblCaloriesLeft = new JLabel("Remaining Calories: 0");
		lblCaloriesLeft.setHorizontalAlignment(SwingConstants.CENTER);
		pnlBalance.add(lblCaloriesLeft);

		menuPanel = new JPanel();
		menuPanel.setLayout(new GridBagLayout());

		add(menuPanel, BorderLayout.CENTER);
		
		redraw();
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

		Integer calMin = user.getDiet().getCalorieMinimum();
		Integer calMax = user.getDiet().getCalorieMaximum();
		Integer calConsumed = user.getCaloriesConsumed(new Date());
		
		Integer cMinRem = calMin - calConsumed;
		Integer cMaxRem = calMax - calConsumed;
		
		lblCaloriesLeft.setText("Remaining Calories: "+cMinRem + " - " + cMaxRem);	

		repaint();
		revalidate();
	}

	public void itemSelected(FoodItem item) {
		if (user.getRemainingBalance() >= item.getPrice()) {
			Order o = new Order();
			o.addItemToOrder(item);
			o.setMemo("Vending Machine");
			confirmPurchase(o);
		} else {
			JOptionPane.showMessageDialog(this, "You do not have enough funds.");
		}

	}

	private void addItem(FoodItem item) {
		ItemPanel itemPanel = new ItemPanel(item);

		itemPanel.addSelectListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FoodItem item = itemPanel.getItem();
				itemSelected(item);
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
