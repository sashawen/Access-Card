package ymss.csc.views.order;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ymss.csc.application.Constants;
import ymss.csc.models.FoodItem;
import ymss.csc.models.Order;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class OrderPanel extends JPanel implements Observer {

	private Order order;

	private JButton btnPurchase;
	private JPanel pnlItems;
	private JLabel lblTotal;
	private JPanel panel;

	public OrderPanel(Order order) {
		setBorder(new EmptyBorder(10, 5, 10, 5));
		this.order = order;
		order.addObserver(this);
		setLayout(new BorderLayout(0, 0));

		JLabel lblHeader = new JLabel("Order");
		lblHeader.setFont(Constants.FONT_HEADING_4);
		lblHeader.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(lblHeader, BorderLayout.NORTH);

		pnlItems = new JPanel();
		pnlItems.setLayout(new GridBagLayout());
		this.add(pnlItems);

		panel = new JPanel();
		add(panel, BorderLayout.SOUTH);
		panel.setLayout(new GridLayout(0, 1, 0, 0));

		String strTotal = String.format("$%.2f", order.getTotalCost());
		lblTotal = new JLabel(strTotal);
		lblTotal.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblTotal);

		btnPurchase = new JButton("Purchase");
		panel.add(btnPurchase);

	}

	public void setOrder(Order order) {

		// stop observing old order
		this.order.deleteObserver(this);

		// observe new order
		this.order = order;
		order.addObserver(this);

		redraw();
	}

	private void redrawItems() {
		pnlItems.removeAll();

		if (order != null) {
			List<FoodItem> items = order.getItems();
			Iterator<FoodItem> it = items.iterator();
			while (it.hasNext()) {
				FoodItem item = it.next();
				addOrderLine(pnlItems, item, order.getQuantity(item));
			}

		}
		GridBagConstraints gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.BOTH;
		gc.anchor = GridBagConstraints.SOUTH;
		gc.gridx = 0;
		gc.gridy = GridBagConstraints.RELATIVE;
		gc.weighty = 1.0;
		pnlItems.add(new JPanel(), gc);
	}

	private void redrawTotal() {

		String sTotal = String.format("Total: $%.2f", order.getTotalCost());
		lblTotal.setText(sTotal);
	}

	private void redraw() {

		redrawItems();
		redrawTotal();
		btnPurchase.setVisible(order.getItems().size() > 0);

		this.repaint();
		this.revalidate();
	}

	public void addPurchaseListener(ActionListener l) {
		btnPurchase.addActionListener(l);
	}

	private void addOrderLine(JPanel parent, FoodItem item, Integer quantity) {
		GridBagConstraints cs = new GridBagConstraints();
		cs.gridx = 0;
		cs.fill = GridBagConstraints.HORIZONTAL;
		cs.weightx = 1.0;
		cs.anchor = GridBagConstraints.NORTH;
		parent.add(new OrderItemPanel(order,item,quantity),cs);

		return;
	}

	@Override
	public void update(Observable o, Object arg) {
		redraw();
	}

}
