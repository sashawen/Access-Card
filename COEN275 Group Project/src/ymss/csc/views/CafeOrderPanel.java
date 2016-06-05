package ymss.csc.views;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

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

	public CafeOrderPanel(AbstractVendor vendor, UserAccount user) {
		super(vendor, user);
		order = new Order();
		order.addObserver(this);

		this.setLayout(new BorderLayout());

		JPanel pnlBalance = new JPanel();
		lblBalance = new JLabel("Remaining Balance:");
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
