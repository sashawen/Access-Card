package ymss.csc.views.order;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ymss.csc.models.FoodItem;
import ymss.csc.models.Order;
import java.awt.GridLayout;
import java.awt.Color;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.BorderLayout;
import javax.swing.border.LineBorder;

public class OrderItemPanel extends JPanel {
	public OrderItemPanel(Order order, FoodItem item, Integer quantity) {
		setBorder(new EmptyBorder(0, 0, 10, 0));
		setLayout(new BorderLayout(0, 0));

		JPanel pnlContainer = new JPanel();
		pnlContainer.setBorder(new LineBorder(new Color(0, 0, 0)));
		pnlContainer.setBackground(Color.WHITE);
		add(pnlContainer, BorderLayout.CENTER);
		pnlContainer.setLayout(new GridLayout(3, 0, 0, 0));

		JPanel panel_3 = new JPanel();
		panel_3.setOpaque(false);
		pnlContainer.add(panel_3);

		JLabel lblQuantity = new JLabel(quantity + "  ");
		panel_3.add(lblQuantity);

		JLabel lblName = new JLabel(item.getName());
		panel_3.add(lblName);

		JPanel panel = new JPanel();
		panel.setOpaque(false);
		pnlContainer.add(panel);

		String strPrice = String.format("$%.2f each",item.getPrice());
		JLabel lblPrice = new JLabel(strPrice);
		panel.add(lblPrice);

		String strItemTotal = String.format(" = $%.2f", item.getPrice() * quantity);
		JLabel lblItemTotal = new JLabel(strItemTotal);
		panel.add(lblItemTotal);

		JPanel panel_1 = new JPanel();
		panel_1.setOpaque(false);
		panel_1.setBorder(new EmptyBorder(0, 25, 5, 25));
		pnlContainer.add(panel_1);
		panel_1.setLayout(new GridLayout(0, 2, 25, 0));

		JButton btnAdd = new JButton("+");
		panel_1.add(btnAdd);
		btnAdd.setForeground(Color.WHITE);
		// btnAdd.setContentAreaFilled(false);
		btnAdd.setBorderPainted(false);
		btnAdd.setBackground(new Color(0, 153, 51));

		JButton btnRemove = new JButton("-");
		panel_1.add(btnRemove);
		btnRemove.setBackground(Color.RED);
		btnRemove.setForeground(Color.WHITE);
		// btnRemove.setContentAreaFilled(false);
		btnRemove.setBorderPainted(false);
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				order.removeItemFromOrder(item);
			}
		});
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				order.addItemToOrder(item);
			}
		});
	}
}
