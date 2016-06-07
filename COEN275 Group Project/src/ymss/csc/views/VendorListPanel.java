package ymss.csc.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ymss.csc.application.Constants;
import ymss.csc.models.AbstractVendor;
import ymss.csc.models.Cafe;
import ymss.csc.models.VendingMachine;

public class VendorListPanel extends AbstractVendorSelectionPanel {

	private static final long serialVersionUID = -6527928316909791100L;
	
	private List<Cafe> cafes = new ArrayList<Cafe>();
	private List<VendingMachine> vendingMachines = new ArrayList<VendingMachine>();

	public VendorListPanel(List<AbstractVendor> vendors) {
		super(vendors);
		
		Iterator<AbstractVendor> it = vendors.iterator();
		while(it.hasNext()){
			AbstractVendor v = it.next();
			if(v instanceof Cafe){
				cafes.add((Cafe)v);
			}else if(v instanceof VendingMachine){
				vendingMachines.add((VendingMachine)v);
			}
		}
		
		initialize();
	}

	private void initCafeList(JPanel parent) {
		// "Cafe" List
		JPanel pnlCafeList = new JPanel();
		parent.add(pnlCafeList);
		pnlCafeList.setLayout(new GridLayout(0, 1, 0, 0));

		JList lstCafe = new JList();
		lstCafe.setModel(new AbstractListModel() {

			@Override
			public Object getElementAt(int index) {
				return cafes.get(index).getName();
			}

			@Override
			public int getSize() {
				return cafes.size();
			}

		});
		pnlCafeList.add(lstCafe);
		lstCafe.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				JList list = (JList) evt.getSource();
				if (evt.getClickCount() == 2) {

					// Double-click detected
					int index = list.locationToIndex(evt.getPoint());
					vendorSelected(cafes.get(index));
				}
			}
		});
	}
	
	private void initVendingMachineList(JPanel parent) {
		JPanel pnlVM = new JPanel();
		parent.add(pnlVM);
		pnlVM.setLayout(new GridLayout(0, 1, 0, 0));

		JList lstVM = new JList();
		pnlVM.add(lstVM);
		lstVM.setModel(new AbstractListModel() {

			public int getSize() {
				return vendingMachines.size();
			}

			public Object getElementAt(int index) {
				return vendingMachines.get(index).getName();
			}
		});
		lstVM.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				JList list = (JList) evt.getSource();
				if (evt.getClickCount() == 2) {

					// Double-click detected
					int index = list.locationToIndex(evt.getPoint());
					vendorSelected(vendingMachines.get(index));
				}
			}
		});
	}

	private void initialize() {

		// Initialize cafe list panel.
		this.setBorder(new EmptyBorder(10, 10, 10, 10));

		setLayout(new GridLayout(2, 1, 0, 0));

		JPanel panel = new JPanel();
		add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		createListPanelHeading(panel, "Cafes");
		initCafeList(panel);

		JPanel panel_1 = new JPanel();
		add(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));

		createListPanelHeading(panel_1, "Vending Machines");
		initVendingMachineList(panel_1);

	}

	private void createListPanelHeading(JPanel parent, String heading) {

		JPanel pnlCafeHeading = new JPanel();
		parent.add(pnlCafeHeading);
		pnlCafeHeading.setLayout(new BoxLayout(pnlCafeHeading, BoxLayout.X_AXIS));
		
		JLabel lblCafeHeading = new JLabel(heading);
		pnlCafeHeading.add(lblCafeHeading);
		lblCafeHeading.setFont(Constants.FONT_HEADING_3);
	}

	private List<String> getCafeNames() {
		Iterator<AbstractVendor> it = vendors.iterator();
		List<String> cafes = new ArrayList<String>();
		while (it.hasNext()) {
			AbstractVendor v = it.next();
			if (v instanceof Cafe) {
				cafes.add(v.getName());
			}
		}
		return cafes;
	}

	private List<String> getVendingMachineNames() {
		Iterator<AbstractVendor> it = vendors.iterator();
		List<String> vms = new ArrayList<String>();
		while (it.hasNext()) {
			AbstractVendor v = it.next();
			if (v instanceof VendingMachine) {
				vms.add(v.getName());
			}
		}
		return vms;
	}

}
