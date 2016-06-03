package ymss.csc.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ymss.csc.models.*;

public class CafeFrame extends JFrame implements Observer{

	private static final long serialVersionUID = -2638211781748534596L;

	static final String title = "Cafe";

	private JPanel menuPanel;
	private JLabel lblBalance;
	private JPanel pnlOrder;

	private UserAccount user;
	private Cafe cafe;
	
	public CafeFrame(UserAccount user, Cafe cafe) {
		this.user = user;
		this.cafe = cafe;
		
		// Window initialization
		setTitle(title);
		setSize(800, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());

		JPanel pnlBalance = new JPanel();
		lblBalance = new JLabel("Remaining Balance:");
		pnlBalance.add(lblBalance);
		mainPanel.add(pnlBalance, BorderLayout.NORTH);

		pnlOrder = new JPanel();
		mainPanel.add(pnlOrder, BorderLayout.EAST);

		menuPanel = new JPanel();
		menuPanel.setLayout(new GridBagLayout());

		mainPanel.add(menuPanel, BorderLayout.CENTER);
		this.setContentPane(mainPanel);
		
		user.addObserver(this);
		initialize(cafe,user);
	}

	public void initialize(Cafe cafe, UserAccount user) {
		List<FoodItem> menu = cafe.getMenu();
		menuPanel.removeAll();

		Iterator<FoodItem> it = menu.iterator();
		while (it.hasNext()) {
			FoodItem item = it.next();
			addItem(item);
		}

		String sBalance = String.format("%.2f", user.getRemainingBalance());
		lblBalance.setText("Remaining Balance: $" + sBalance);

		initOrderPanel(cafe.getOrder());

		repaint();
		revalidate();
	}
	
	private void initOrderPanel(Order order) {
		pnlOrder.removeAll();

		pnlOrder.setLayout(new BoxLayout(pnlOrder, BoxLayout.Y_AXIS));

		JLabel lblHeader = new JLabel("Order");
		pnlOrder.add(lblHeader);

		if (order != null) {
			List<FoodItem> items = order.getItems();
			Iterator<FoodItem> it = items.iterator();
			while (it.hasNext()) {
				FoodItem item = it.next();
				addOrderLine(pnlOrder, item, order.getQuantity(item));
			}

			String sTotal = String.format("%.2f", order.getTotalCost());
			JLabel lblTotal = new JLabel("Total: $" + sTotal);
			pnlOrder.add(lblTotal);
		}

		JButton btnPurchase = new JButton("Purchase");
		btnPurchase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (orderListener != null) {
					orderListener.purchase();
				}
			}
		});
		pnlOrder.add(btnPurchase);

		pnlOrder.repaint();
		pnlOrder.revalidate();
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
				if (orderListener != null) {
					orderListener.itemAdded(item);
				}
			}
		});
		pnlLine.add(btnAdd);

		JButton btnRemove = new JButton("-");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (orderListener != null) {
					orderListener.itemRemoved(item);
				}
			}
		});
		pnlLine.add(btnRemove);

		parent.add(pnlLine);

		return;
	}

	private class ItemPanel extends JPanel {
		private static final long serialVersionUID = 6978620418637694671L;

		private Integer id;
		private JButton btnSelect;

		public ItemPanel(FoodItem item) {
			this.id = item.getId();

			this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			this.setBorder(BorderFactory.createLineBorder(Color.black));

			JLabel lblName = new JLabel(item.getName());
			JLabel lblDescription = new JLabel(item.getDescription());
			JLabel lblCalories = new JLabel(item.getCalories() + " calories");
			String roundedPrice = String.format("%.2f", item.getPrice());
			JLabel lblPrice = new JLabel('$' + roundedPrice);
			btnSelect = new JButton("Select");

			this.add(lblName);
			this.add(lblDescription);
			this.add(lblCalories);
			this.add(lblPrice);
			this.add(btnSelect);
		}

		public void addSelectListener(ActionListener l) {
			btnSelect.addActionListener(l);
		}

		public Integer getId() {
			return id;
		}
	}

	private OrderListener orderListener;

	private void addItem(FoodItem item) {
		ItemPanel itemPanel = new ItemPanel(item);

		itemPanel.addSelectListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Integer id = itemPanel.getId();
				FoodItem item = FoodItem.getItem(id);
				if (item != null && orderListener != null) {
					orderListener.itemAdded(item);
				}
			}
		});

		GridBagConstraints cs = new GridBagConstraints();
		cs.gridx = 0;
		cs.fill = GridBagConstraints.HORIZONTAL;
		cs.weightx = 1.0;
		cs.weighty = 1.0;
		menuPanel.add(itemPanel, cs);
	}

	public interface OrderListener {
		public void itemAdded(FoodItem item);

		public void itemRemoved(FoodItem item);

		public void purchase();
	}

	public void setOrderListener(OrderListener l) {
		orderListener = l;
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		initialize(cafe,user);
	}
}
