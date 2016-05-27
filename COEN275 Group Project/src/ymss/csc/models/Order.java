package ymss.csc.models;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Order {

	/**
	 * Memo, for Cafe's name, etc.
	 */
	private String memo;

	/**
	 * Map of FoodItem id's => Quantity.
	 */
	private Map<Integer, Integer> items = new HashMap<Integer, Integer>();

	/**
	 * Date of purchase
	 */
	private Date purchaseDate;

	/**
	 * Constructor
	 */
	public Order() {

	};

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	/**
	 * Adds the specified item to the order.
	 * 
	 * @param item
	 *            Item
	 */
	public void addItemToOrder(FoodItem item) {
		int id = item.getId();
		if (!items.containsKey(id)) {
			items.put(id, 1);
		} else {
			items.put(id, items.get(id) + 1);
		}
		return;
	}

	/**
	 * Removes the specified item from the order.
	 * 
	 * @param item
	 *            Item
	 */
	public void removeItemFromOrder(FoodItem item) {
		int id = item.getId();
		if (!items.containsKey(id))
			return;

		items.put(id, items.get(id) - 1);
	}

	/**
	 * Returns the total calories within the order.
	 * 
	 * @return Total Calories
	 */
	public int getTotalCalories() {

		int calories = 0;

		Iterator<Integer> it = items.keySet().iterator();
		while (it.hasNext()) {
			int foodId = it.next();
			FoodItem item = FoodItem.getItem(foodId);
			int quantity = items.get(item.getId());
			if (quantity > 0) {
				calories = calories + quantity * item.getCalories();
			}
		}
		return calories;

	}

	/**
	 * Returns the total cost of the order.
	 * 
	 * @return Total Cost
	 */
	public double getTotalCost() {
		double cost = 0.0;

		Iterator<Integer> it = items.keySet().iterator();
		while (it.hasNext()) {
			int foodId = it.next();
			FoodItem item = FoodItem.getItem(foodId);
			int quantity = items.get(item.getId());
			if (quantity > 0) {
				cost = cost + quantity * item.getPrice();
			}
		}
		return cost;
	}

	/**
	 * Returns the date of purchase
	 * 
	 * @return Date of Purchase
	 */
	public Date getPurchaseDate() {
		return purchaseDate;
	}

	/**
	 * Sets the date of purchase.
	 * 
	 * @param purchaseDate
	 *            Date of Purchase
	 */
	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

}
