import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import ymss.csc.models.*;
import ymss.csc.stores.*;
import ymss.csc.views.*;

public class Controller {

	// Frames
	private static LoginFrame loginFrame;
	private static MainFrame mainFrame;
	private static CafeFrame cafeFrame;
	private static VendingMachineFrame vendingMachineFrame;
	private static HealthFrame healthFrame;
	private static EditHealthFrame editHealthFrame;
	private static FinanceFrame financeFrame;
	private static FundDepositFrame fundDepositFrame;

	// Stores
	private static PersistentDataStore dataStore;

	private static String appName = "CampusSmartCafe";

	public static boolean authenticate(Integer cardNumber, String password) {
		// TODO: Implement
		UserAccount ua = dataStore.getAccount(cardNumber);
		if (ua != null && ua.getPassword().equals(password)) {
			return true;
		}
		return false;
	}

	public static void launchMainFrame() {
		if (mainFrame == null)
			mainFrame = new MainFrame();

		mainFrame.addOpenCafeListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				launchCafeFrame(null);
			}
		});

		mainFrame.addOpenVendingMachineListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				launchVendingMachineFrame(null);
			}
		});

		mainFrame.addOpenFinanceListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				launchFinanceFrame();
			}
		});

		mainFrame.addOpenHealthListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				launchHealthFrame();
			}
		});

		mainFrame.setVisible(true);
	}

	public static void launchCafeFrame(Cafe cafe) {
		if (cafeFrame == null)
			cafeFrame = new CafeFrame();
		cafeFrame.setVisible(true);
	}

	public static void launchVendingMachineFrame(VendingMachine vm) {
		if (vendingMachineFrame == null)
			vendingMachineFrame = new VendingMachineFrame();
		cafeFrame.setVisible(true);
	}

	public static void launchHealthFrame() {
		if (healthFrame == null)
			healthFrame = new HealthFrame();

		healthFrame.addEditListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				launchEditHealthFrame();
			}
		});

		healthFrame.setVisible(true);
	}

	public static void launchEditHealthFrame() {
		if (editHealthFrame == null)
			editHealthFrame = new EditHealthFrame();

		editHealthFrame.setVisible(true);
	}

	public static void launchFinanceFrame() {
		if (financeFrame == null)
			financeFrame = new FinanceFrame();

		financeFrame.addDepositListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				launchFundDepositFrame();
			}
		});

		financeFrame.setVisible(true);
	}

	public static void launchFundDepositFrame() {
		if (fundDepositFrame == null)
			fundDepositFrame = new FundDepositFrame();
		fundDepositFrame.setVisible(true);
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
