package ymss.csc.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import ymss.csc.application.Constants;
import ymss.csc.models.AbstractVendor;
import ymss.csc.models.Cafe;
import ymss.csc.models.FoodItem;
import ymss.csc.models.Order;
import ymss.csc.models.UserAccount;
import ymss.csc.views.order.ItemMenuPanel;
import ymss.csc.views.order.ItemMenuPanel.SelectionListener;
import ymss.csc.views.order.ItemPanel;
import ymss.csc.views.order.OrderPanel;
import java.awt.Insets;

public class CafeOrderPanel extends AbstractVendorOrderPanel {

	// private JPanel menuPanel;
	private JLabel lblBalance;
	private JLabel lblCaloriesLeft;

	private Order order;
	private OrderPanel pnlOrder;

	// add
	private JScrollPane menu;
	//

	public CafeOrderPanel() {
		Cafe cafe = new Cafe();
		cafe.addItemToCart(FoodItem.getItem(0));
		cafe.addItemToCart(FoodItem.getItem(1));
		cafe.addItemToCart(FoodItem.getItem(2));
		UserAccount user = new UserAccount();
		init(cafe, user);
	}

	public CafeOrderPanel(AbstractVendor vendor, UserAccount user) {
		super(vendor, user);
		init(vendor, user);
	}

	private void init(AbstractVendor vendor, UserAccount user) {
		order = new Order();
		order.addObserver(this);
		setLayout(new BorderLayout(0, 0));

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

		JPanel tempPanel = new JPanel();
		add(tempPanel);

		ItemMenuPanel menuPanel = new ItemMenuPanel(vendor.getMenu());
		menuPanel.addSelectionListener(new SelectionListener() {
			public void itemSelected(FoodItem item) {
				order.addItemToOrder(item);
			}
		});
		tempPanel.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane(menuPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setViewportView(menuPanel);
		tempPanel.add(scrollPane, BorderLayout.CENTER);
		tempPanel.setBackground(Color.pink);

		pnlOrder = new OrderPanel(this.order);
		tempPanel.addComponentListener(new ComponentListener() {

			@Override
			public void componentHidden(ComponentEvent arg0) {
				Integer w = (int) (tempPanel.getWidth() * 0.3);
				pnlOrder.setPreferredSize(new Dimension(w, tempPanel.getHeight()));

			}

			@Override
			public void componentMoved(ComponentEvent arg0) {
				Integer w = (int) (tempPanel.getWidth() * 0.3);
				pnlOrder.setPreferredSize(new Dimension(w, tempPanel.getHeight()));

			}

			@Override
			public void componentResized(ComponentEvent arg0) {
				Integer w = (int) (tempPanel.getWidth() * 0.3);
				pnlOrder.setPreferredSize(new Dimension(w, tempPanel.getHeight()));

			}

			@Override
			public void componentShown(ComponentEvent arg0) {
				Integer w = (int) (tempPanel.getWidth() * 0.3);
				pnlOrder.setPreferredSize(new Dimension(w, tempPanel.getHeight()));

			}

		});
		pnlOrder.addPurchaseListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				purchaseOrder();

			}
		});
		tempPanel.add(pnlOrder, BorderLayout.EAST);

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

		String sBalance = String.format("%.2f", user.getRemainingBalance());
		lblBalance.setText("Remaining Balance: $" + sBalance);

		Integer calMin = user.getDiet().getCalorieMinimum();
		Integer calMax = user.getDiet().getCalorieMaximum();
		Integer calConsumed = user.getCaloriesConsumed(new Date());

		Integer cMinRem = calMin - calConsumed;
		Integer cMaxRem = calMax - calConsumed;

		lblCaloriesLeft.setText("Remaining Calories: " + cMinRem + " - " + cMaxRem);

		if (order.isPurchased()) {
			order.deleteObserver(this);
			order = new Order();
			order.addObserver(this);
			pnlOrder.setOrder(order);
		}

		repaint();
		revalidate();
	}

}
