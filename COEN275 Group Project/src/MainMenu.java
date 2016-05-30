import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
//import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;

public class MainMenu extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainMenu frame = new MainMenu();
					frame.setVisible(true);
					JTabbedPane tabbedPane = new JTabbedPane();
					tabbedPane.addTab("CaféMap", new JPanel());
					frame.getContentPane().add(tabbedPane);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void initMenuBar() {
		// menu bar
		JMenuBar menuBar = new JMenuBar();
		menuBar.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 14));
		setJMenuBar(menuBar);
		UIManager.put("MenuItem.background", Color.GRAY);
		UIManager.put("MenuItem.opaque", true);

		JMenuItem mntmFoodPreferences = new JMenuItem("Food Preferences");
		menuBar.add(mntmFoodPreferences);

		JMenuItem mntmLogout = new JMenuItem("Funds");
		menuBar.add(mntmLogout);

		JMenuItem mntmLogout_1 = new JMenuItem("Logout");
		menuBar.add(mntmLogout_1);
	}

	private void initCafeTab(JTabbedPane tabbedPane) {
		JPanel panel_cafe = new JPanel();

		panel_cafe.setLayout(null);
		tabbedPane.addTab("CafeMap", null, panel_cafe, null);

		// list of cafes
		JList list_cafe = new JList();
		list_cafe.setSelectedIndices(new int[] { 1 });
		list_cafe.setModel(new AbstractListModel() {
			String[] values = new String[] { "1. Cellar Market", "2. Mission Bakery and Cafe", "3. The Bronco",
					"4. Market Place" };

			public int getSize() {
				return values.length;
			}

			public Object getElementAt(int index) {
				return values[index];
			}
		});
		list_cafe.setBounds(509, 61, 205, 68);
		panel_cafe.add(list_cafe);

		JLabel cafeLocation = new JLabel("CafeLocations");
		cafeLocation.setBounds(6, 6, 491, 278);
		panel_cafe.add(cafeLocation);
		Image img1 = new ImageIcon(this.getClass().getResource("cafe2.jpg")).getImage();
		cafeLocation.setIcon(new ImageIcon(img1));

		JLabel lblBensonCenter = new JLabel("- Benson Center");
		lblBensonCenter.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		lblBensonCenter.setBounds(509, 33, 124, 16);
		panel_cafe.add(lblBensonCenter);

		JLabel lblLibray = new JLabel("- Learning Commons and Library");
		lblLibray.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		lblLibray.setHorizontalAlignment(SwingConstants.CENTER);
		lblLibray.setBounds(497, 141, 217, 34);
		panel_cafe.add(lblLibray);

		JList list_1 = new JList();
		list_1.setSelectedIndices(new int[] { 1 });
		list_1.setModel(new AbstractListModel() {
			String[] values = new String[] { "5. SunStream Café" };

			public int getSize() {
				return values.length;
			}

			public Object getElementAt(int index) {
				return values[index];
			}
		});
		list_1.setBounds(509, 175, 205, 20);
		panel_cafe.add(list_1);

		JButton btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				list_cafe.removeSelectionInterval(0, 3);
				list_1.removeSelectionInterval(0, 0);
			}
		});
		btnReset.setBounds(623, 230, 67, 29);
		panel_cafe.add(btnReset);

		JList list = new JList();
		list.setBounds(544, 215, 75, -31);
		panel_cafe.add(list);

		JButton btnGo = new JButton("Go");
		btnGo.setBounds(544, 230, 67, 29);
		panel_cafe.add(btnGo);
	}

	private void initVendingMachineTab(JTabbedPane tabbedPane) {
		// panel properties
		JPanel panel_vending = new JPanel();
		tabbedPane.addTab("Vending Machine Map", null, panel_vending, null);
		panel_vending.setLayout(null);

		JLabel lblVendingLocation = new JLabel("vendingLocation");
		lblVendingLocation.setBounds(6, 6, 491, 278);
		panel_vending.add(lblVendingLocation);
		Image img2 = new ImageIcon(this.getClass().getResource("Vending2.jpg")).getImage();
		lblVendingLocation.setIcon(new ImageIcon(img2));

		JLabel lblStevensStadium = new JLabel("- Stevens Stadium");
		lblStevensStadium.setBounds(515, 49, 161, 36);
		panel_vending.add(lblStevensStadium);

		JList list_vending = new JList();
		// add(new JScrollPane(list_vending)); ---------
		list_vending.setSelectedIndices(new int[] { 1 });
		list_vending.setModel(new AbstractListModel() {
			String[] values = new String[] { "Vending Machine#1", "Vending Machine#2" };

			public int getSize() {
				return values.length;
			}

			public Object getElementAt(int index) {
				return values[index];
			}
		});
		list_vending.setBounds(525, 97, 146, 45);
		panel_vending.add(list_vending);

		JButton btn_VMreset = new JButton("Reset");
		btn_VMreset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				list_vending.removeSelectionInterval(0, 1);

			}
		});
		btn_VMreset.setBounds(601, 175, 67, 29);
		panel_vending.add(btn_VMreset);

		JButton btnGo_VM = new JButton("Go");
		btnGo_VM.setBounds(536, 175, 67, 29);
		panel_vending.add(btnGo_VM);
	}

	/**
	 * Create the frame.
	 */
	public MainMenu() {

		// frame constraints
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 753, 410);

		// menu bar
		initMenuBar();

		// "main" panel
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// tabs
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(6, 6, 741, 354);
		contentPane.add(tabbedPane);

		initCafeTab(tabbedPane);
		initVendingMachineTab(tabbedPane);

	}

	class CafeMap extends JPanel {

		JLabel cafeMap;

		public CafeMap() {
			cafeMap = new JLabel();
			Image img = new ImageIcon(this.getClass().getResource("cafePic.jpg")).getImage();
			cafeMap.setIcon(new ImageIcon(img));

			// setBackground(Color.yellow);
			// add(message);
			// setComponentOrientation(
			// ComponentOrientation.RIGHT_TO_LEFT);
		}
	}
}
