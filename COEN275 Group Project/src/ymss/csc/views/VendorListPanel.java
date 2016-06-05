package ymss.csc.views;

import java.awt.BorderLayout;
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

	

	private void initialize() {

		// Initialize cafe list panel.
		this.setBorder(new EmptyBorder(10, 10, 10, 10));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		createListPanelHeading(this, "Cafes");

		// "Cafe" List
		JPanel panel_4 = new JPanel();
		this.add(panel_4);
		panel_4.setLayout(new GridLayout(0, 1, 0, 0));

		JList list = new JList();
		list.setModel(new AbstractListModel() {

			@Override
			public Object getElementAt(int index) {
				return cafes.get(index).getName();
			}

			@Override
			public int getSize() {
				return cafes.size();
			}

		});
		panel_4.add(list);
		list.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				JList list = (JList) evt.getSource();
				if (evt.getClickCount() == 2) {

					// Double-click detected
					int index = list.locationToIndex(evt.getPoint());
					//JOptionPane.showMessageDialog(null, "You clicked on "+cafes.get(index));
					vendorSelected(cafes.get(index));
				}
			}
		});

		createListPanelHeading(this, "Vending Machines");

		JPanel panel_3 = new JPanel();
		this.add(panel_3);
		panel_3.setLayout(new GridLayout(0, 1, 0, 0));

		JList list_1 = new JList();
		panel_3.add(list_1);
		list_1.setModel(new AbstractListModel() {

			public int getSize() {
				return vendingMachines.size();
			}

			public Object getElementAt(int index) {
				return vendingMachines.get(index).getName();
			}
		});
		list_1.addMouseListener(new MouseAdapter() {
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
	
	private void createListPanelHeading(JPanel parent, String heading) {
		JPanel pnlCafeHeading = new JPanel();
		parent.add(pnlCafeHeading);
		pnlCafeHeading.setLayout(new BoxLayout(pnlCafeHeading, BoxLayout.X_AXIS));

		JLabel lblCafeHeading = new JLabel(heading);
		pnlCafeHeading.add(lblCafeHeading);
		lblCafeHeading.setFont(new Font("Tahoma", Font.PLAIN, 16));
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
