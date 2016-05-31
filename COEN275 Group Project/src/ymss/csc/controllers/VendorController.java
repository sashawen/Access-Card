package ymss.csc.controllers;

import ymss.csc.models.Cafe;
import ymss.csc.models.FoodVendor;
import ymss.csc.models.VendingMachine;
import ymss.csc.views.CafeFrame;
import ymss.csc.views.VendingMachineFrame;

public class VendorController {
	
	private CafeFrame cafeFrame;
	private VendingMachineFrame vendingMachineFrame;
	
	public void launch(FoodVendor vendor){
		if(vendor instanceof Cafe){
			launchCafeFrame((Cafe)vendor);
		}else if(vendor instanceof VendingMachine){
			launchVendingMachineFrame((VendingMachine)vendor);
		}
	}
	
	public void launchCafeFrame(Cafe cafe) {
		if (cafeFrame == null)
			cafeFrame = new CafeFrame();
		cafeFrame.setVisible(true);
	}

	public void launchVendingMachineFrame(VendingMachine vm) {
		if (vendingMachineFrame == null)
			vendingMachineFrame = new VendingMachineFrame();
		cafeFrame.setVisible(true);
	}

}
