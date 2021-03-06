package ymss.csc.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FoodItem {
	private int id = 0;
	
	private static Map<Integer,FoodItem> items = new HashMap<Integer,FoodItem>();
	
	public static FoodItem getItem(int id){
		if(items.size() > id) return items.get(id);
		return null;
	}

	private String name = "";
	private String description = "";
	
	private int calories = 0;
	
	private double price = 0.0; // Because we're going to say they all cost the same per food item.
	
	private boolean lowSodium = false;
	private boolean lowCholesterol= false;
	private boolean glutenFree = false;
	private boolean vegan = false;
	
	/**
	 * Constructor
	 */
	public FoodItem(int id){
		this.id = id;
		items.put(id, this);
	}

	/**
	 * Returns the food id. 
	 * @return ID
	 */
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getCalories() {
		return calories;
	}

	public void setCalories(Integer calories) {
		if(calories == null) return;
		this.calories = calories;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		if(price == null) return;
		this.price = price;
	}

	public boolean isLowSodium() {
		return lowSodium;
	}

	public void setLowSodium(boolean lowSodium) {
		this.lowSodium = lowSodium;
	}

	public boolean isLowCholesterol() {
		return lowCholesterol;
	}

	public void setLowCholesterol(boolean lowCholesterol) {
		this.lowCholesterol = lowCholesterol;
	}

	public boolean isGlutenFree() {
		return glutenFree;
	}

	public void setGlutenFree(boolean glutenFree) {
		this.glutenFree = glutenFree;
	}

	public boolean isVegan() {
		return vegan;
	}

	public void setVegan(boolean vegan) {
		this.vegan = vegan;
	}
		
}
