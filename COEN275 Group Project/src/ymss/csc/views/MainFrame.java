package ymss.csc.views;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 6983316186716069456L;

	static final String title = "TechTonic University Dining Services";

	private JButton btnCafe;
	private JButton btnVendingMachine;
	private JButton btnFinance;
	private JButton btnHealth;

	public MainFrame() {
		// Window initialization
		setTitle(title);
		setSize(800, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		JPanel tempPanel = new JPanel();
		this.getContentPane().add(tempPanel);

		btnCafe = new JButton("Cafe Frame");
		btnVendingMachine = new JButton("Vending Machine Frame");
		btnFinance = new JButton("Finance Frame");
		btnHealth = new JButton("Health Frame");

		tempPanel.add(btnCafe);
		tempPanel.add(btnVendingMachine);
		tempPanel.add(btnFinance);
		tempPanel.add(btnHealth);

		// Menu Bar
		// addMenuBar();

		// (High-level) Panels
		// initMainPanel();
		// initNDSPanel();

		// Card Panel Setup
		// cardPanel = new JPanel();
		// cards = new CardLayout();
		// cardPanel.setLayout(cards);
		// this.getContentPane().add(cardPanel);

		// cardPanel.add(mainPanel, "main");
		// cardPanel.add(ndsPanel, "nds");

		// Show mainPanel first.
		// cards.show(cardPanel, "main");
	}

	public void addOpenCafeListener(ActionListener l) {
		btnCafe.addActionListener(l);
	}

	public void removeOpenCafeListener(ActionListener l) {
		btnCafe.removeActionListener(l);
	}

	public void addOpenVendingMachineListener(ActionListener l) {
		btnVendingMachine.addActionListener(l);
	}

	public void removeOpenVendingMachineListener(ActionListener l) {
		btnVendingMachine.removeActionListener(l);
	}

	public void addOpenFinanceListener(ActionListener l) {
		btnFinance.addActionListener(l);
	}

	public void removeOpenFinanceListener(ActionListener l) {
		btnFinance.removeActionListener(l);
	}

	public void addOpenHealthListener(ActionListener l) {
		btnHealth.addActionListener(l);
	}

	public void removeOpenHealthListener(ActionListener l) {
		btnHealth.removeActionListener(l);
	}
}
