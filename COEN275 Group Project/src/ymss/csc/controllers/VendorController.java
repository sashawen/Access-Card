package ymss.csc.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JOptionPane;

import ymss.csc.models.*;
import ymss.csc.views.CafeFrame;
import ymss.csc.views.OrderCredFrame;
import ymss.csc.views.VendingMachineFrame;

public class VendorController {
	
	private CafeFrame cafeFrame;
	private VendingMachineFrame vendingMachineFrame;
	
	private UserAccount user;
	
	public void launch(UserAccount user, FoodVendor vendor){
		this.user = user;
		
		if(vendor instanceof Cafe){
			launchCafeFrame((Cafe)vendor);
		}else if(vendor instanceof VendingMachine){
			launchVendingMachineFrame((VendingMachine)vendor);
		}
	}
	
	public void launchCafeFrame(Cafe cafe) {
		if (cafeFrame == null)
			cafeFrame = new CafeFrame();
		cafeFrame.setVisible(true);
	}
	
	public boolean checkBalance(Double cost){
		if(cost > user.getRemainingBalance()) return false;
		return true;
	}
	
	public boolean authenticate(Integer cardNumber, String password){
		return (user.getCardNumber() == cardNumber) && (user.getPassword().equals(password));
	}
	
	public void confirmPurchase(FoodItem item){
		
		String msg = String.format("%s\n\nTotal: $%.2f.  OK?", item.getName(),item.getPrice());
		Integer choice = JOptionPane.showConfirmDialog(vendingMachineFrame, msg);
		if(choice == JOptionPane.CANCEL_OPTION || choice == JOptionPane.NO_OPTION){
			return;
		}else{
			OrderCredFrame credFrame = new OrderCredFrame();
			credFrame.addAuthListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (authenticate(credFrame.getCardNumber(), credFrame.getPassword())) {
						JOptionPane.showMessageDialog(credFrame, "Purchased!");
						submitOrder(item);
						credFrame.dispose();
					} else {
						JOptionPane.showMessageDialog(null, "Username / Password incorrect.", "Purchase Incomplete",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			});
			credFrame.setVisible(true);
		}
	}
	
	private void submitOrder(FoodItem item){
		Order o = new Order();
		o.addItemToOrder(item);
		o.setMemo("Vending Machine");
		o.setPurchaseDate(new Date());
		
		if(orderListener != null) orderListener.orderSubmitted(o);
	}
	
	private OrderSubmissionListener orderListener;
	
	public void setOrderListener(OrderSubmissionListener l){
		orderListener = l;
	}
	
	public interface OrderSubmissionListener{
		public void orderSubmitted(Order order);
	}

	public void launchVendingMachineFrame(VendingMachine vm) {
		if (vendingMachineFrame == null)
			vendingMachineFrame = new VendingMachineFrame();
		vendingMachineFrame.initialize(vm,user);
		
		vendingMachineFrame.setSelectionListener(new VendingMachineFrame.ItemSelectionListener() {
			
			@Override
			public void itemSelected(FoodItem item) {
				if(checkBalance(item.getPrice())){
					confirmPurchase(item);
				}else{
					JOptionPane.showMessageDialog(vendingMachineFrame, "You do not have enough funds.");
				}
				
			}
		});
		
		vendingMachineFrame.setVisible(true);
	}
	

}
