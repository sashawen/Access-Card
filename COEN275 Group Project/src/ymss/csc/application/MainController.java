package ymss.csc.application;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import ymss.csc.models.*;
import ymss.csc.stores.*;
import ymss.csc.views.*;

public class MainController implements Observer{

	// Frames
	private LoginFrame loginFrame;
	private MainFrame mainFrame;
	private HealthFrame healthFrame;
	private FinanceFrame financeFrame;
	private CafeFrame cafeFrame;
	private VendingMachineFrame vendingMachineFrame;

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
			mainFrame = new MainFrame(currentUser,dataStore.getVendors());

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

	private void initFoodVendor(AbstractVendor vendor) {
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
		dummyCafe = new Cafe();
		initFoodVendor(dummyVendingMachine);
		initFoodVendor(dummyCafe);
		initFonts();

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
	
	private void initFonts(){
		UIManager.put("Button.font", Constants.FONT_NORMAL);
		UIManager.put("ToggleButton.font", Constants.FONT_NORMAL);
		UIManager.put("RadioButton.font", Constants.FONT_NORMAL);
		UIManager.put("CheckBox.font", Constants.FONT_NORMAL);
		UIManager.put("ColorChooser.font", Constants.FONT_NORMAL);
		UIManager.put("ComboBox.font", Constants.FONT_NORMAL);
		UIManager.put("Label.font", Constants.FONT_NORMAL);
		UIManager.put("List.font", Constants.FONT_NORMAL);
		UIManager.put("MenuBar.font", Constants.FONT_NORMAL);
		UIManager.put("MenuItem.font", Constants.FONT_NORMAL);
		UIManager.put("RadioButtonMenuItem.font", Constants.FONT_NORMAL);
		UIManager.put("CheckBoxMenuItem.font", Constants.FONT_NORMAL);
		UIManager.put("Menu.font", Constants.FONT_NORMAL);
		UIManager.put("PopupMenu.font", Constants.FONT_NORMAL);
		UIManager.put("OptionPane.font", Constants.FONT_NORMAL);
		UIManager.put("Panel.font", Constants.FONT_NORMAL);
		UIManager.put("ProgressBar.font", Constants.FONT_NORMAL);
		UIManager.put("ScrollPane.font", Constants.FONT_NORMAL);
		UIManager.put("Viewport.font", Constants.FONT_NORMAL);
		UIManager.put("TabbedPane.font", Constants.FONT_NORMAL);
		UIManager.put("Table.font", Constants.FONT_NORMAL);
		UIManager.put("TableHeader.font", Constants.FONT_NORMAL);
		UIManager.put("TextField.font", Constants.FONT_NORMAL);
		UIManager.put("PasswordField.font", Constants.FONT_NORMAL);
		UIManager.put("TextArea.font", Constants.FONT_NORMAL);
		UIManager.put("TextPane.font", Constants.FONT_NORMAL);
		UIManager.put("EditorPane.font", Constants.FONT_NORMAL);
		UIManager.put("TitledBorder.font", Constants.FONT_NORMAL);
		UIManager.put("ToolBar.font", Constants.FONT_NORMAL);
		UIManager.put("ToolTip.font", Constants.FONT_NORMAL);
		UIManager.put("Tree.font", Constants.FONT_NORMAL);
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
