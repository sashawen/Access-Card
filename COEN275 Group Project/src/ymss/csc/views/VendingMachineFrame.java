package ymss.csc.views;

import java.util.Iterator;
import java.util.List;

import javax.swing.*;

import ymss.csc.models.*;

public class VendingMachineFrame extends JFrame {

	private static final long serialVersionUID = 8121732800555614056L;

	static final String title = "Vending Machine";

	private JPanel menuPanel;

	public VendingMachineFrame() {
		// Window initialization
		setTitle(title);
		setSize(800, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		menuPanel = new JPanel();
	}
	
	public void initialize(VendingMachine vm){
		List<FoodItem> menu = vm.getMenu();
		menuPanel.removeAll();
		
		Iterator<FoodItem> it = menu.iterator();
		while(it.hasNext()){
			FoodItem item = it.next();
			addItem(item.getName(),item.getDescription(),item.getCalories(),item.getPrice());
		}
	}

	private void addItem(String name, String desc, Integer cals, Double pr) {
		JPanel itemPanel = new JPanel();

		JLabel lblName = new JLabel(name);
		JLabel lblDescription = new JLabel(desc);
		JLabel lblCalories = new JLabel(cals + " calories");
		Double roundedPrice = Math.round(pr * 100.0) / 100.0;
		JLabel lblPrice = new JLabel('$' + roundedPrice.toString());
		JButton btnSelect = new JButton("Select");

		itemPanel.add(lblName);
		itemPanel.add(lblDescription);
		itemPanel.add(lblCalories);
		itemPanel.add(lblPrice);
		itemPanel.add(btnSelect);

		menuPanel.add(itemPanel);
	}

}
