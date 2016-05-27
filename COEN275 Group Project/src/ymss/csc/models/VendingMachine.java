package ymss.csc.models;

import java.util.ArrayList;
import java.util.List;

public class VendingMachine implements FoodVendor {

	private List<FoodItem> menu = new ArrayList<FoodItem>();
	
	private FoodItem selectedItem;

	public VendingMachine() {
	}

	public void addItemToMenu(FoodItem item) {
		menu.add(item);
	}

	public void removeItemFromMenu(FoodItem item) {
		menu.remove(item);
	}

	public void selectItem(FoodItem item) {
		selectedItem = item;
	}
	
	public FoodItem getSelectedItem(){
		return selectedItem;
	}
	
	public Order getOrder(){
		Order o = new Order();
		o.addItemToOrder(selectedItem);
		return o;
	}

	public void purchase() {
		selectedItem = null;
	}
}
