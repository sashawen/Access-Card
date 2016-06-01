package ymss.csc.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Order {

	/**
	 * Memo, for Cafe's name, etc.
	 */
	private String memo;

	/**
	 * Map of FoodItem id's => Quantity.
	 */
	private Map<Integer, FoodItem> items = new HashMap<Integer, FoodItem>();
	
	/**
	 * 
	 */
	private Map<Integer, Integer> quantities = new HashMap<Integer,Integer>();

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
			items.put(id, item);
			quantities.put(id, 1);
		} else {
			quantities.put(id, quantities.get(id) + 1);
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

		quantities.put(id, quantities.get(id) - 1);
	}
	
	public List<FoodItem> getItems(){
		List<FoodItem> list = new ArrayList<FoodItem>();
		Iterator<Integer> it = items.keySet().iterator();
		while(it.hasNext()){
			Integer id = it.next();
			if(quantities.get(id) > 0){
				list.add(items.get(id));
			}
		}
		return list;
	}
	
	public Integer getQuantity(FoodItem item){
		if(item == null) return -1; // should indicate error...
		return quantities.get(item.getId());
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
			FoodItem item = items.get(foodId);
			int quantity = quantities.get(item.getId());
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
			FoodItem item = items.get(foodId);
			int quantity = quantities.get(item.getId());
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
