package ymss.csc.tests;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import ymss.csc.models.*;
import ymss.csc.stores.JSONDataStore;
import ymss.csc.stores.PersistentDataStore;

public class FoodTester {

	public static void main(String[] args) {
		PersistentDataStore pds = new JSONDataStore();

		pds.init();
		
		List<FoodItem> items = pds.getFoodItems();
		
		FoodItem pizza = items.get(0);
		
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
