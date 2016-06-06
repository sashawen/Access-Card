package ymss.csc.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ymss.csc.models.*;

abstract public class AbstractVendorOrderPanel extends JPanel implements Observer {
	
	protected AbstractVendor vendor;
	protected UserAccount user;
	
	public AbstractVendorOrderPanel(){}
	
	public AbstractVendorOrderPanel(AbstractVendor vendor, UserAccount user){
		this.vendor = vendor;
		this.user = user;
		user.addObserver(this);
	}
	
	abstract public void redraw();

	@Override
	public void update(Observable o, Object arg) {
		redraw();
	}
	public Boolean authenticate(Integer cardNumber, String password){
		if(user.getCardNumber() == cardNumber && password.equals(user.getPassword())){
			return true;
		}
		return false;
	}
	
	private void submitOrder(Order o) {
		o.setPurchaseDate(new Date());
		Double balance = user.getRemainingBalance() - o.getTotalCost();
		o.setBalance(balance);
		user.setRemainingBalance(balance);
		user.addTransaction(o);
		o.purhcase();
	}
	
	protected String finalMessage = "Purchased!";
	
	public void confirmPurchase(Order order) {

		String msg = String.format("\nTotal: $%.2f.  OK?", order.getTotalCost());
		Integer choice = JOptionPane.showConfirmDialog(this, msg);
		if (choice == JOptionPane.CANCEL_OPTION || choice == JOptionPane.NO_OPTION) {
			return;
		} else {
			OrderCredFrame credFrame = new OrderCredFrame();
			credFrame.addAuthListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (authenticate(credFrame.getCardNumber(), credFrame.getPassword())) {
						JOptionPane.showMessageDialog(credFrame, finalMessage);
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

}
