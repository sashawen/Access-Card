package ymss.csc.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import ymss.csc.models.*;
import ymss.csc.stores.*;
import ymss.csc.views.*;

public class MainController {

	// Frames
	private LoginFrame loginFrame;
	private MainFrame mainFrame;
	
	// Controllers
	private HealthController healthController = new HealthController();
	private FinanceController financeController = new FinanceController();
	private VendorController vendorController = new VendorController();
	
	// Stores
	private PersistentDataStore dataStore;
	private Cafe dummyCafe;
	private VendingMachine dummyVendingMachine;
	
	// Application "State"
	private UserAccount currentUser;

	private static String appName = "CampusSmartCafe";

	public boolean authenticate(Integer cardNumber, String password) {
		UserAccount ua = dataStore.getAccount(cardNumber);
		if (ua != null && ua.getPassword().equals(password)) {
			currentUser = ua;
			return true;
		}
		return false;
	}

	public void launchMainFrame() {
		if (mainFrame == null)
			mainFrame = new MainFrame();

		mainFrame.addOpenCafeListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vendorController.launch(currentUser,dummyCafe);
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
				vendorController.launch(currentUser,dummyVendingMachine);
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
				financeController.launch(currentUser);
				financeController.setFinanceListener(new FinanceController.FinanceListener() {
					
					@Override
					public void fundsDeposited(Double d) {
						Double newBalance = currentUser.getRemainingBalance() + d;
						currentUser.setRemainingBalance(newBalance);
						financeController.launch(currentUser);
					}
				});
			}
		});

		mainFrame.addOpenHealthListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				healthController.launch(currentUser);
				healthController.setHealthListener(new HealthController.HealthChangeListener(){
					public void profileChanged(DietaryProfile profile){
						currentUser.setDiet(profile);
						// TODO: need to save to persistent storage
					}
				});
			}
		});

		mainFrame.setVisible(true);
	}
	
	private void initFoodVendor(FoodVendor vendor){
		if(vendor == null) return;
		
		List<FoodItem> items = dataStore.getFoodItems();
		
		Iterator<FoodItem> it = items.iterator();
		while(it.hasNext()){
			vendor.addItemToMenu(it.next());
		}
	}
	
		
	public MainController(){
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
	 * @param args Command Line Arguments
	 */
	public static void main(String[] args) {
		MainController mc = new MainController();
	}
}
