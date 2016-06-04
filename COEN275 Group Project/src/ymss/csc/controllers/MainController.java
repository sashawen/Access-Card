package ymss.csc.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import ymss.csc.models.*;
import ymss.csc.stores.*;
import ymss.csc.views.*;

public class MainController implements Observer{

	// Frames
	private LoginFrame loginFrame;
	private MainFrame mainFrame;
	private HealthFrame healthFrame;
	private FinanceFrame financeFrame;

	// Controllers
	private VendorController vendorController = new VendorController();

	// Stores
	private PersistentDataStore dataStore;
	private Cafe dummyCafe;
	private VendingMachine dummyVendingMachine;

	// Application "State"
	private UserAccount currentUser;

	public boolean authenticate(Integer cardNumber, String password) {
		UserAccount ua = dataStore.getAccount(cardNumber);
		if (ua != null && ua.getPassword().equals(password)) {
			currentUser = ua;
			currentUser.addObserver(this);
			return true;
		}
		return false;
	}

	public void launchMainFrame() {
		if (mainFrame == null)
			mainFrame = new MainFrame();

		mainFrame.addOpenCafeListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vendorController.launch(currentUser, dummyCafe);
				vendorController.setOrderListener(new VendorController.OrderSubmissionListener() {

					@Override
					public void orderSubmitted(Order order) {
						Double newBalance = currentUser.getRemainingBalance() - order.getTotalCost();
						currentUser.setRemainingBalance(newBalance);
						vendorController.launch(currentUser, dummyCafe); // (relaunch)
					}
				});
			}
		});

		mainFrame.addOpenVendingMachineListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vendorController.launch(currentUser, dummyVendingMachine);
				vendorController.setOrderListener(new VendorController.OrderSubmissionListener() {

					@Override
					public void orderSubmitted(Order order) {
						Double newBalance = currentUser.getRemainingBalance() - order.getTotalCost();
						currentUser.setRemainingBalance(newBalance);
						vendorController.launch(currentUser, dummyVendingMachine); // (relaunch)
					}
				});
			}
		});

		mainFrame.addOpenFinanceListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (financeFrame == null)
					financeFrame = new FinanceFrame(currentUser);

				financeFrame.setVisible(true);
			}
		});

		mainFrame.addOpenHealthListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (healthFrame == null)
					healthFrame = new HealthFrame(currentUser);

				healthFrame.setVisible(true);
			}
		});

		mainFrame.setVisible(true);
	}

	private void initFoodVendor(FoodVendor vendor) {
		if (vendor == null)
			return;

		List<FoodItem> items = dataStore.getFoodItems();

		Iterator<FoodItem> it = items.iterator();
		while (it.hasNext()) {
			vendor.addItemToMenu(it.next());
		}
	}

	public MainController() {
		dataStore = new JSONDataStore();
		dataStore.init();

		dummyVendingMachine = new VendingMachine();
		dummyCafe = new Cafe("Dummy");
		initFoodVendor(dummyVendingMachine);
		initFoodVendor(dummyCafe);

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				loginFrame = new LoginFrame();

				loginFrame.addAuthListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (authenticate(loginFrame.getCardNumber(), loginFrame.getPassword())) {
							launchMainFrame();
							loginFrame.dispose();
						} else {
							JOptionPane.showMessageDialog(null, "Username / Password incorrect.", "Login Failed",
									JOptionPane.ERROR_MESSAGE);
						}
					}
				});

				loginFrame.setVisible(true);

			}
		});
	}

	/**
	 * Main Function
	 * 
	 * @param args
	 *            Command Line Arguments
	 */
	public static void main(String[] args) {
		MainController mc = new MainController();
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if(arg0 instanceof UserAccount){
			dataStore.updateAccount(currentUser);
		}
		
	}
}
