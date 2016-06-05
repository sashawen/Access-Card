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

import ymss.csc.models.FoodItem;
import ymss.csc.models.Order;

public class OrderPanel extends JPanel implements Observer {

	private Order order;

	private JButton btnPurchase;
	private JPanel pnlItems;
	private JLabel lblTotal;

	public OrderPanel(Order order) {
		this.order = order;
		order.addObserver(this);

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JLabel lblHeader = new JLabel("Order");
		this.add(lblHeader);

		pnlItems = new JPanel();
		pnlItems.setLayout(new BoxLayout(pnlItems, BoxLayout.Y_AXIS));
		this.add(pnlItems);

		lblTotal = new JLabel("");
		this.add(lblTotal);

		btnPurchase = new JButton("Purchase");
		this.add(btnPurchase);

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

		JPanel pnlLine = new JPanel();
		pnlLine.setLayout(new BoxLayout(pnlLine, BoxLayout.X_AXIS));

		JLabel lblName = new JLabel(item.getName());
		pnlLine.add(lblName);

		JLabel lblPrice = new JLabel("$" + item.getPrice());
		pnlLine.add(lblPrice);

		JLabel lblQuantity = new JLabel("x " + quantity);
		pnlLine.add(lblQuantity);

		JLabel lblItemTotal = new JLabel("=> " + item.getPrice() * quantity);
		pnlLine.add(lblItemTotal);

		JButton btnAdd = new JButton("+");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				order.addItemToOrder(item);
			}
		});
		pnlLine.add(btnAdd);

		JButton btnRemove = new JButton("-");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				order.removeItemFromOrder(item);
			}
		});
		pnlLine.add(btnRemove);

		parent.add(pnlLine);

		return;
	}

	@Override
	public void update(Observable o, Object arg) {
		redraw();
	}

}
