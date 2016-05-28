package ymss.csc.views;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame extends JFrame {

	static final String title = "TechTonic University Dining Services";

	public MainFrame() {
		// Window initialization
		setTitle(title);
		setSize(800, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// Menu Bar
		//addMenuBar();

		// (High-level) Panels
		//initMainPanel();
		//initNDSPanel();

		// Card Panel Setup
		//cardPanel = new JPanel();
		//cards = new CardLayout();
		//cardPanel.setLayout(cards);
		//this.getContentPane().add(cardPanel);

		//cardPanel.add(mainPanel, "main");
		//cardPanel.add(ndsPanel, "nds");

		// Show mainPanel first.
		//cards.show(cardPanel, "main");
	}
}
