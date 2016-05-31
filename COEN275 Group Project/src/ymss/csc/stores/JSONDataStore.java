package ymss.csc.stores;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import ymss.csc.models.FoodItem;
import ymss.csc.models.FoodVendor;
import ymss.csc.models.UserAccount;

public class JSONDataStore implements PersistentDataStore {

	private String parseString(JSONObject o, String key) {
		if(!o.containsKey(key)) return null;
		return (String) o.get(key);
	}

	public Integer parseInt(JSONObject o, String key) {
		if(!o.containsKey(key)) return null;
		return Math.toIntExact((Long) o.get(key));
	}

	public Double parseDouble(JSONObject o, String key) {
		if(!o.containsKey(key)) return null;
		return (double) o.get(key);
	}

	public Boolean parseBoolean(JSONObject o, String key) {
		if(!o.containsKey(key)) return null;
		return (boolean) o.get(key);
	}

	List<UserAccount> accounts = new ArrayList<UserAccount>();
	List<FoodVendor> vendors = new ArrayList<FoodVendor>();
	List<FoodItem> items = new ArrayList<FoodItem>();
	
	public void init(){
		loadUsers();
		loadFoodItems();
	}

	@Override
	public List<UserAccount> getAccounts() {
		// TODO Auto-generated method stub
		return accounts;
	}
	
	public UserAccount getAccount(int cardNumber){
		Iterator<UserAccount> it = accounts.iterator();
		while(it.hasNext()){
			UserAccount ua = it.next();
			if(ua.getCardNumber()== cardNumber) return ua;
		}
		return null;
	}

	@Override
	public void updateAccount(UserAccount user) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<FoodVendor> getVendors() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateVendor(FoodVendor vendor) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<FoodItem> getFoodItems() {
		// TODO Auto-generated method stub		
		return items;
	}

	@Override
	public void updateFoodItem(FoodItem food) {
		// TODO Auto-generated method stub

	}

	private FoodItem parseFoodItem(JSONObject item) {
		Integer id = parseInt(item,"id");
		if(id == null) return null;

		FoodItem fi = new FoodItem(id);
		fi.setName(parseString(item, "name"));
		fi.setCalories(parseInt(item, "calories"));
		fi.setDescription(parseString(item, "description"));
		fi.setPrice(parseDouble(item, "price"));
		fi.setLowSodium(parseBoolean(item, "low_sodium"));
		fi.setLowCholesterol(parseBoolean(item, "low_cholesterol"));
		fi.setGlutenFree(parseBoolean(item, "gluten_free"));
		fi.setVegan(parseBoolean(item, "vegan"));

		return fi;

	}
	
	private interface MyJSONParser{
		public void parseJSONTree(JSONObject o);
	}
	
	private void parseJSONFile(String filename,MyJSONParser treeParser){
		JSONParser parser = new JSONParser();
		try {
			JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(filename));
			if(treeParser != null) treeParser.parseJSONTree(jsonObject);			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	private void loadFoodItems() {
		parseJSONFile("data/food_items.json",new MyJSONParser(){
			public void parseJSONTree(JSONObject o){
				items.clear();
				
				JSONArray foodItems = (JSONArray) o.get("items");

				Iterator<JSONObject> iterator = foodItems.iterator();
				while (iterator.hasNext()) {
					items.add(parseFoodItem(iterator.next()));
				}
			}
		});
	}
	
	private UserAccount parseUserAccount(JSONObject user){
		UserAccount acct = new UserAccount();
		acct.setCardNumber(parseInt(user,"card_number"));
		acct.setPassword(parseString(user,"password"));
		acct.setRemainingBalance(parseDouble(user,"balance"));
		
		return acct;
	}
	
	private void loadUsers(){
		parseJSONFile("data/users.json",new MyJSONParser(){
			public void parseJSONTree(JSONObject o){
				accounts.clear();
				
				JSONArray users = (JSONArray) o.get("users");
				
				Iterator<JSONObject> iterator = users.iterator();
				while(iterator.hasNext()){
					accounts.add(parseUserAccount(iterator.next()));
				}
			}
		});
	}

}
