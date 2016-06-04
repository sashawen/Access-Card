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

	public void launch(UserAccount user, AbstractVendor vendor) {
		this.user = user;

		if (vendor instanceof Cafe) {
			launchCafeFrame((Cafe) vendor);
		} else if (vendor instanceof VendingMachine) {
			launchVendingMachineFrame((VendingMachine) vendor);
		}
	}

	public void launchCafeFrame(Cafe cafe) {
		if(cafeFrame != null) cafeFrame.dispose();
			
		cafeFrame = new CafeFrame(user,cafe);

		cafeFrame.setOrderListener(new CafeFrame.OrderListener() {

			@Override
			public void itemAdded(FoodItem item) {
				cafe.getOrder().addItemToOrder(item);
				cafeFrame.initialize(cafe, user);
			}
			
			public void itemRemoved(FoodItem item){
				cafe.getOrder().removeItemFromOrder(item);
				cafeFrame.initialize(cafe, user);
			}
			
			public void purchase(){
				Order o = cafe.getOrder();
				if (checkBalance(o.getTotalCost())) {
					o.setMemo(cafe.getName());
					confirmPurchase(o);
				} else {
					JOptionPane.showMessageDialog(vendingMachineFrame, "You do not have enough funds.");
				}
			}
		});

		cafeFrame.setVisible(true);
	}

	public boolean checkBalance(Double cost) {
		if (cost > user.getRemainingBalance())
			return false;
		return true;
	}

	public boolean authenticate(Integer cardNumber, String password) {
		return (user.getCardNumber() == cardNumber) && (user.getPassword().equals(password));
	}

	public void confirmPurchase(Order order) {

		String msg = String.format("\nTotal: $%.2f.  OK?", order.getTotalCost());
		Integer choice = JOptionPane.showConfirmDialog(vendingMachineFrame, msg);
		if (choice == JOptionPane.CANCEL_OPTION || choice == JOptionPane.NO_OPTION) {
			return;
		} else {
			OrderCredFrame credFrame = new OrderCredFrame();
			credFrame.addAuthListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (authenticate(credFrame.getCardNumber(), credFrame.getPassword())) {
						JOptionPane.showMessageDialog(credFrame, "Purchased!");
						submitOrder(order);
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

	private void submitOrder(Order o) {
		o.setPurchaseDate(new Date());

		if (orderListener != null)
			orderListener.orderSubmitted(o);
	}

	private OrderSubmissionListener orderListener;

	public void setOrderListener(OrderSubmissionListener l) {
		orderListener = l;
	}

	public interface OrderSubmissionListener {
		public void orderSubmitted(Order order);
	}

	public void launchVendingMachineFrame(VendingMachine vm) {
		if (vendingMachineFrame == null)
			vendingMachineFrame = new VendingMachineFrame();
		vendingMachineFrame.initialize(vm, user);

		vendingMachineFrame.setSelectionListener(new VendingMachineFrame.ItemSelectionListener() {

			/* (non-Javadoc)
			 * @see ymss.csc.views.VendingMachineFrame.ItemSelectionListener#itemSelected(ymss.csc.models.FoodItem)
			 */
			@Override
			public void itemSelected(FoodItem item) {
				if (checkBalance(item.getPrice())) {
					Order o = new Order();
					o.addItemToOrder(item);
					o.setMemo("Vending Machine");
					confirmPurchase(o);
				} else {
					JOptionPane.showMessageDialog(vendingMachineFrame, "You do not have enough funds.");
				}

			}
		});

		vendingMachineFrame.setVisible(true);
	}

}
