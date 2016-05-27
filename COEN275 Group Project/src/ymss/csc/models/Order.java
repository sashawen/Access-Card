package ymss.csc.models;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Order {
	/**
	 * Map of FoodItem id's => Quantity.
	 */
	private Map<Integer, Integer> items = new HashMap<Integer, Integer>();

	private Date purchaseDate;

	public Order() {

	};

	public void addItemToOrder(FoodItem item) {
		int id = item.getId();
		if (!items.containsKey(id)) {
			items.put(id, 1);
		} else {
			items.put(id, items.get(id) + 1);
		}
		return;
	}

	public void removeItemFromOrder(FoodItem item) {
		int id = item.getId();
		if (!items.containsKey(id))
			return;

		items.put(id, items.get(id) - 1);
	}

	public int getTotalCalories() {
		// TODO: implement
		return 0;
	}

	public double getTotalCost() {
		// TODO: Implement
		return 0.0;
	}

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
}
