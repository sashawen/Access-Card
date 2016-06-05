package ymss.csc.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.imageio.ImageIO;
import javax.swing.AbstractListModel;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.border.EmptyBorder;
import javax.swing.JLayeredPane;
import javax.swing.ImageIcon;
import javax.swing.border.LineBorder;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ymss.csc.models.AbstractVendor;
import ymss.csc.models.Cafe;
import ymss.csc.models.UserAccount;
import ymss.csc.models.VendingMachine;
import ymss.csc.views.AbstractVendorSelectionPanel.VendorSelectionListener;

import java.awt.Color;
import java.awt.CardLayout;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 6983316186716069456L;

	static final String title = "TechTonic University Dining Services";

	private JButton btnCafe;
	private JButton btnVendingMachine;
	private JButton btnFinance;
	private JButton btnHealth;
	private JPanel panel;
	private JPanel panel_4;
	private JPanel pnlCafeSelection;
	private JList list;
	private JList list_1;
	private JPanel panel_3;

	private List<AbstractVendor> vendors;

	private UserAccount user;

	/**
	 * Constructor
	 * 
	 * @param vendors
	 *            Vendors to add to the Map and List
	 */
	public MainFrame(UserAccount user, List<AbstractVendor> vendors) {
		this.user = user;
		this.vendors = vendors;

		// Window initialization
		setTitle(title);
		setSize(800, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));

		addQuickFlowButtons(panel);

		pnlCafeSelection = new JPanel();
		panel.add(pnlCafeSelection);
		pnlCafeSelection.setLayout(new BorderLayout(0, 0));

		VendorSelectionListener selListener = new VendorSelectionListener() {

			@Override
			public void vendorSelected(AbstractVendor vendor) {
				openFrameForVendor(vendor);
			}
		};

		VendorMapPanel pnlMap = new VendorMapPanel(vendors);
		pnlCafeSelection.add(pnlMap, BorderLayout.CENTER);
		pnlMap.setVendorSelectionListener(selListener);

		VendorListPanel pnlCafeList = new VendorListPanel(vendors);
		pnlCafeSelection.add(pnlCafeList, BorderLayout.EAST);
		pnlCafeList.setVendorSelectionListener(selListener);
	}

	private JFrame buyFoodFrame;

	private void openFrameForVendor(AbstractVendor vendor) {
		if (buyFoodFrame != null)
			buyFoodFrame.dispose();

		if (vendor instanceof Cafe) {
			buyFoodFrame = new CafeFrame(user, (Cafe) vendor);
			buyFoodFrame.setVisible(true);
		} else if (vendor instanceof VendingMachine) {
			buyFoodFrame = new VendingMachineFrame(user, (VendingMachine) vendor);
			buyFoodFrame.setVisible(true);
		}
	}

	private void addQuickFlowButtons(JPanel panel) {
		JPanel tempPanel = new JPanel();
		tempPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.add(tempPanel, BorderLayout.NORTH);
		tempPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		btnFinance = new JButton("Finance Frame");
		tempPanel.add(btnFinance);

		btnHealth = new JButton("Health Frame");
		tempPanel.add(btnHealth);

	}

	public void addOpenFinanceListener(ActionListener l) {
		btnFinance.addActionListener(l);
	}

	public void addOpenHealthListener(ActionListener l) {
		btnHealth.addActionListener(l);
	}

}
