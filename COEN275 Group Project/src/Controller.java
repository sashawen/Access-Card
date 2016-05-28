import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import ymss.csc.models.UserAccount;
import ymss.csc.stores.JSONDataStore;
import ymss.csc.stores.PersistentDataStore;
import ymss.csc.views.LoginFrame;
import ymss.csc.views.MainFrame;

public class Controller {
	
	private static LoginFrame loginFrame;
	private static MainFrame mainFrame;
	private static PersistentDataStore dataStore;
	
	private static String appName = "CampusSmartCafe";
	
	public static boolean authenticate(Integer cardNumber, String password){
		// TODO: Implement
		UserAccount ua = dataStore.getAccount(cardNumber);
		if(ua != null && ua.getPassword().equals(password)){
			return true;
		}
		return false;
	}
	
	public static void launchMainFrame(){
		mainFrame = new MainFrame();
		mainFrame.setVisible(true);
	}

	public static void main(String[] args){
		dataStore = new JSONDataStore();
		dataStore.init();
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				//mainFrame = new MainFrame();
				//mainFrame.setVisible(true);
				loginFrame = new LoginFrame(appName);
				
				loginFrame.addLoginListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						if(authenticate(loginFrame.getCardNumber(),loginFrame.getPassword())){
							launchMainFrame();
							loginFrame.dispose();
						}else{
							JOptionPane.showMessageDialog(null, "Username / Password incorrect.", "Login Failed", JOptionPane.ERROR_MESSAGE);
						}
					}
				});
				
				loginFrame.addCancelListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						loginFrame.dispose();
					}
				});
				
				loginFrame.setVisible(true);
				
			}
		});
	}
}
