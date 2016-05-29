package ymss.csc.views;

import javax.swing.JFrame;

public class VendingMachineFrame extends JFrame {

	private static final long serialVersionUID = 8121732800555614056L;
	
	static final String title = "Vending Machine";

	public VendingMachineFrame() {
		// Window initialization
		setTitle(title);
		setSize(800, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

}
