package ymss.csc.tests;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import ymss.csc.models.*;

public class FoodTester {

	private static void parseFoodItem(JSONObject item) {

		String name = (String) item.get("name");
		String description = (String) item.get("description");
		int calories = Math.toIntExact((Long)item.get("calories"));
		double price = (double) item.get("price");

		boolean lowSodium = (boolean) item.get("low_sodium");
		boolean lowCholesterol = (boolean) item.get("low_cholesterol");
		boolean glutenFree = (boolean) item.get("gluten_free");
		boolean vegan = (boolean) item.get("vegan");

		FoodItem fi = new FoodItem(name);
		fi.setCalories(calories);
		fi.setDescription(description);
		fi.setPrice(price);
		fi.setLowSodium(lowSodium);
		fi.setLowCholesterol(lowCholesterol);
		fi.setGlutenFree(glutenFree);
		fi.setVegan(vegan);

	}

	private static void loadFoodItems() {
		JSONParser parser = new JSONParser();
		try {
			Object object = parser.parse(new FileReader("data/food_items.json"));
			JSONObject jsonObject = (JSONObject) object;

			JSONArray foodItems = (JSONArray) jsonObject.get("items");

			Iterator<JSONObject> iterator = foodItems.iterator();
			while (iterator.hasNext()) {
				parseFoodItem(iterator.next());
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		loadFoodItems();

		FoodItem pizza = FoodItem.getItem(0);

		Order myOrder = new Order();
		for (int i = 0; i < 5; i++) {
			myOrder.addItemToOrder(pizza);
		}

		System.out.println("Total Calories: " + myOrder.getTotalCalories());
		System.out.println("Total Cost: " + myOrder.getTotalCost());

		myOrder.setPurchaseDate(new Date());
		System.out.println("Date: " + myOrder.getPurchaseDate());

	}
}
