package ymss.csc.models;

import java.util.ArrayList;
import java.util.List;

public class Cafe implements FoodVendor {

	private String name;
	private String description = "";

	private List<FoodItem> menu = new ArrayList<FoodItem>();

	private Order cart = new Order();

	public Cafe(String name) {
		this.name = name;
	}
	
	public String getName(){
		return name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void addItemToMenu(FoodItem item) {
		menu.add(item);
	}

	public void removeItemFromMenu(FoodItem item) {
		menu.remove(item);
	}

	public void addItemToCart(FoodItem item) {
		cart.addItemToOrder(item);
	}

	public void removeItemFromCart(FoodItem item) {
		cart.removeItemFromOrder(item);
	}

	public Order getOrder() {
		return cart;
	}

	public void purchase() {
		cart = new Order(); // (reset)
	}
}
