package ymss.csc.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;

import javax.swing.*;

import ymss.csc.models.*;

public class VendingMachineFrame extends JFrame {

	private static final long serialVersionUID = 8121732800555614056L;

	static final String title = "Vending Machine";
	
	private JPanel menuPanel;
	private JLabel lblBalance;

	public VendingMachineFrame() {
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
		
		menuPanel = new JPanel();
		menuPanel.setLayout(new GridBagLayout());
		
		mainPanel.add(menuPanel,BorderLayout.CENTER);
		this.setContentPane(mainPanel);
	}
	
	public void initialize(VendingMachine vm, UserAccount user){		
		List<FoodItem> menu = vm.getMenu();
		menuPanel.removeAll();
		
		Iterator<FoodItem> it = menu.iterator();
		while(it.hasNext()){
			FoodItem item = it.next();
			addItem(item);
		}
		
		String sBalance = String.format("%.2f", user.getRemainingBalance());
		lblBalance.setText("Remaining Balance: $"+sBalance);
		
		repaint();
		revalidate();
	}
	
	private class ItemPanel extends JPanel{
		private static final long serialVersionUID = 6978620418637694671L;
		
		private Integer id;
		private JButton btnSelect;
		
		public ItemPanel(FoodItem item){
			this.id = item.getId();
			
			this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
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
		
		public void addSelectListener(ActionListener l){
			btnSelect.addActionListener(l);
		}
		
		public Integer getId(){
			return id;
		}
	}
	
	private ItemSelectionListener selectionListener;

	private void addItem(FoodItem item) {
		ItemPanel itemPanel = new ItemPanel(item);
		
		itemPanel.addSelectListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Integer id = itemPanel.getId();
				FoodItem item = FoodItem.getItem(id);
				if(item != null && selectionListener != null){
					selectionListener.itemSelected(item);
				}
			}
		});
		
		GridBagConstraints cs = new GridBagConstraints();
		cs.gridx = 0;
		cs.fill = GridBagConstraints.HORIZONTAL;
		cs.weightx = 1.0;
		cs.weighty = 1.0;
		menuPanel.add(itemPanel,cs);
	}
	
	public interface ItemSelectionListener{
		public void itemSelected(FoodItem item);
	}
	
	public void setSelectionListener(ItemSelectionListener l){
		selectionListener = l;
	}

}
