package ymss.csc.models;

import java.util.ArrayList;
import java.util.List;

public class Cafe extends AbstractVendor{

	public static final String VENDOR_TYPE = "CAFE";
	
	private Order cart = new Order();
	
	public void addItemToCart(FoodItem item) {
		cart.addItemToOrder(item);
	}

	public void removeItemFromCart(FoodItem item) {
		cart.removeItemFromOrder(item);
	}

	public Order getOrder() {
		return cart;
	}

	public void clearCart(){
		cart.clear();
	}

	public List<FoodItem> getMenu() {
		return menu;
	}
}
