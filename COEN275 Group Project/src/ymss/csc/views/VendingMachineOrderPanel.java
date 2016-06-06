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
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ymss.csc.models.AbstractVendor;
import ymss.csc.models.FoodItem;
import ymss.csc.models.Order;
import ymss.csc.models.UserAccount;
import ymss.csc.models.VendingMachine;
import ymss.csc.views.order.ItemMenuPanel;
import ymss.csc.views.order.ItemMenuPanel.SelectionListener;
import ymss.csc.views.order.ItemPanel;
import java.awt.GridLayout;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;

public class VendingMachineOrderPanel extends AbstractVendorOrderPanel {

	private static final long serialVersionUID = -8636303345623272091L;

	private ItemMenuPanel menuPanel;
	private JLabel lblBalance;
	private JLabel lblCaloriesLeft;

	public VendingMachineOrderPanel(AbstractVendor vendor, UserAccount user) {
		super(vendor, user);

		init(vendor, user);
	}

	private void init(AbstractVendor vendor, UserAccount user) {
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

		menuPanel = new ItemMenuPanel(vendor.getMenu());
		menuPanel.addSelectionListener(new SelectionListener(){
			public void itemSelected(FoodItem item){
				itemWasSelected(item);
			}
		});
		JScrollPane scrollPane = new JScrollPane(menuPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		add(scrollPane, BorderLayout.CENTER);		
		scrollPane.setViewportView(menuPanel);
		redraw();
	}

	public void redraw() {
		
		String sBalance = String.format("%.2f", user.getRemainingBalance());
		lblBalance.setText("Remaining Balance: $" + sBalance);

		Integer calMin = user.getDiet().getCalorieMinimum();
		Integer calMax = user.getDiet().getCalorieMaximum();
		Integer calConsumed = user.getCaloriesConsumed(new Date());

		Integer cMinRem = calMin - calConsumed;
		Integer cMaxRem = calMax - calConsumed;

		lblCaloriesLeft.setText("Remaining Calories: " + cMinRem + " - " + cMaxRem);

		repaint();
		revalidate();
	}

	public void itemWasSelected(FoodItem item) {
		if (user.getRemainingBalance() >= item.getPrice()) {
			Order o = new Order();
			o.addItemToOrder(item);
			o.setMemo("Vending Machine");

			finalMessage = String.format("Dispensing item: %s",item.getName());
			confirmPurchase(o);
		} else {
			JOptionPane.showMessageDialog(this, "You do not have enough funds.");
		}

	}


}
