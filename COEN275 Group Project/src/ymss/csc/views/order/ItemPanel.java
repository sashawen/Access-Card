package ymss.csc.views.order;

import java.awt.Color;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ymss.csc.models.FoodItem;

public class ItemPanel extends JPanel {
	private static final long serialVersionUID = 6978620418637694671L;

	private FoodItem item;
	
	private JButton btnSelect;

	public ItemPanel(FoodItem item) {
		this.item = item;

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

	public FoodItem	getItem() {
		return item;
	}
}
