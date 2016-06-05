package ymss.csc.views;

import java.util.List;

import javax.swing.JPanel;

import ymss.csc.models.AbstractVendor;

public abstract class AbstractVendorSelectionPanel extends JPanel {

	protected List<AbstractVendor> vendors;
	
	protected AbstractVendorSelectionPanel(List<AbstractVendor> vendors){
		this.vendors = vendors;
	}
	
	public interface VendorSelectionListener{
		public void vendorSelected(AbstractVendor vendor);
	}
	
	private VendorSelectionListener selectionListener;
	
	public void setVendorSelectionListener(VendorSelectionListener l){
		selectionListener = l;
	}
	
	public void vendorSelected(AbstractVendor vendor){
		if(selectionListener != null) selectionListener.vendorSelected(vendor);
	}
}
