package ymss.csc.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import ymss.csc.models.*;
import ymss.csc.stores.*;
import ymss.csc.views.*;

public class MainController {

	// Frames
	private static LoginFrame loginFrame;
	private static MainFrame mainFrame;
	
	// Controllers
	private static HealthController healthController = new HealthController();
	private static FinanceController financeController = new FinanceController();
	private static VendorController vendorController = new VendorController();
	
	// Stores
	private static PersistentDataStore dataStore;
	
	// Application "State"
	private static UserAccount currentUser;

	private static String appName = "CampusSmartCafe";

	public static boolean authenticate(Integer cardNumber, String password) {
		UserAccount ua = dataStore.getAccount(cardNumber);
		if (ua != null && ua.getPassword().equals(password)) {
			currentUser = ua;
			return true;
		}
		return false;
	}

	public static void launchMainFrame() {
		if (mainFrame == null)
			mainFrame = new MainFrame();

		mainFrame.addOpenCafeListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vendorController.launch(null);
			}
		});

		mainFrame.addOpenVendingMachineListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vendorController.launch(null);
			}
		});

		mainFrame.addOpenFinanceListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				financeController.launch(currentUser);
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

	/**
	 * Main Function
	 * @param args Command Line Arguments
	 */
	public static void main(String[] args) {
		dataStore = new JSONDataStore();
		dataStore.init();

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// mainFrame = new MainFrame();
				// mainFrame.setVisible(true);
				loginFrame = new LoginFrame(appName);

				loginFrame.addLoginListener(new ActionListener() {
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

				loginFrame.addCancelListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						loginFrame.dispose();
					}
				});

				loginFrame.setVisible(true);

			}
		});
	}
}
