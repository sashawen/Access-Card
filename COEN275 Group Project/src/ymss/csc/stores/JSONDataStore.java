package ymss.csc.stores;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import ymss.csc.models.*;

public class JSONDataStore extends AbstractJSONStore implements PersistentDataStore {

	private static final String FILENAME_USERS = "data/users.json";

	private static final String USERS_ROOT = "users";
	private static final String ITEMS_ROOT = "items";

	private static final String USER_CARDNUMBER = "card_number";
	private static final String USER_PASSWORD = "password";
	private static final String USER_BALANCE = "balance";
	private static final String USER_DIET = "diet";
	private static final String USER_HISTORY = "history";
	private static final String TRANSACTION_AMOUNT = "amount";
	private static final String TRANSACTION_BALANCE = "balance";
	private static final String TRANSACTION_DATE = "date";
	private static final String TRANSACTION_MEMO = "memo";
	private static final String TRANSACTION_TYPE = "type";
	private static final String ORDER_ITEMS = "items";
	private static final String ORDER_TOTALCALORIES = "total_calories";
	private static final String DIET_CALORIEMINIMUM = "calorie_minimum";
	private static final String DIET_CALORIEMAXIMUM = "calorie_maximum";
	private static final String DIET_LOWSODIUM = "low_sodium";
	private static final String DIET_LOWCHOLESTEROL = "low_cholesterol";
	private static final String DIET_GLUTENFREE = "gluten_free";
	private static final String DIET_VEGAN = "vegan";

	private static final String FOOD_ID = "id";
	private static final String FOOD_NAME = "name";
	private static final String FOOD_DESCRIPTION = "description";
	private static final String FOOD_CALORIES = "calories";
	private static final String FOOD_PRICE = "price";

	List<UserAccount> accounts = new ArrayList<UserAccount>();
	List<AbstractVendor> vendors = new ArrayList<AbstractVendor>();
	List<FoodItem> items = new ArrayList<FoodItem>();

	public void init() {
		loadFoodItems();
		loadUsers();
		loadVendors();
	}

	@Override
	public List<UserAccount> getAccounts() {
		// TODO Auto-generated method stub
		return accounts;
	}

	public UserAccount getAccount(int cardNumber) {
		Iterator<UserAccount> it = accounts.iterator();
		while (it.hasNext()) {
			UserAccount ua = it.next();
			if (ua.getCardNumber() == cardNumber)
				return ua;
		}
		return null;
	}

	@Override
	public void updateAccount(UserAccount user) {
		JSONObject o = new JSONObject();
		JSONArray arr = new JSONArray();
		o.put(this.USERS_ROOT, arr);

		Iterator<UserAccount> it = accounts.iterator();
		while (it.hasNext()) {
			JSONObject userObj = createUserObject(it.next());
			arr.add(userObj);
		}
		writeJSONToFile(o, this.FILENAME_USERS);
	}

	private JSONObject createUserObject(UserAccount user) {
		JSONObject o = new JSONObject();
		o.put(this.USER_CARDNUMBER, user.getCardNumber());
		o.put(this.USER_PASSWORD, user.getPassword());
		o.put(this.USER_BALANCE, user.getRemainingBalance());
		o.put(this.USER_DIET, createDietObject(user.getDiet()));
		o.put(this.USER_HISTORY, createOrderHistory(user.getHistory()));

		return o;
	}

	private JSONObject createDietObject(DietaryProfile diet) {
		JSONObject o = new JSONObject();
		o.put(this.DIET_CALORIEMINIMUM, diet.getCalorieMinimum());
		o.put(this.DIET_CALORIEMAXIMUM, diet.getCalorieMaximum());
		o.put(this.DIET_LOWSODIUM, diet.isLowSodium());
		o.put(this.DIET_LOWCHOLESTEROL, diet.isLowCholesterol());
		o.put(this.DIET_GLUTENFREE, diet.isGlutenFree());
		o.put(this.DIET_VEGAN, diet.isVegan());

		return o;
	}

	private JSONArray createOrderHistory(List<AccountTransaction> history) {
		JSONArray arr = new JSONArray();

		Iterator<AccountTransaction> it = history.iterator();
		while (it.hasNext()) {
			arr.add(createOrderHistoryItem(it.next()));
		}
		return arr;
	}
	
	
	private JSONObject createOrderHistoryItem(AccountTransaction trans) {
		JSONObject o = new JSONObject();
		o.put(this.TRANSACTION_MEMO, trans.getMemo());
		o.put(this.TRANSACTION_BALANCE, trans.getBalance());
		o.put(this.TRANSACTION_DATE, stringifyDate(trans.getDate()));
		o.put(this.TRANSACTION_AMOUNT, trans.getAccountChange());
		if (trans instanceof Order) {

			JSONArray arr = new JSONArray();
			Iterator<FoodItem> it = ((Order) trans).getItems().iterator();
			while (it.hasNext()) {
				arr.add(it.next().getId());
			}
			o.put(this.ORDER_ITEMS, arr);
			o.put(this.TRANSACTION_TYPE, Order.getType());
			o.put(this.ORDER_TOTALCALORIES, ((Order)trans).getTotalCalories());

		}else if(trans instanceof Deposit){
			o.put(this.TRANSACTION_TYPE, Deposit.getType());
		}
		return o;
	}

	@Override
	public List<AbstractVendor> getVendors() {
		return vendors;
	}

	@Override
	public List<FoodItem> getFoodItems() {
		return items;
	}

	private FoodItem parseFoodItem(JSONObject item) {
		Integer id = parseInt(item, this.FOOD_ID, null);
		if (id == null)
			return null;

		FoodItem fi = new FoodItem(id);
		fi.setName(parseString(item, this.FOOD_NAME, ""));
		fi.setCalories(parseInt(item, this.FOOD_CALORIES, 0));
		fi.setDescription(parseString(item, this.FOOD_DESCRIPTION, "Food Or Drink"));
		fi.setPrice(parseDouble(item, this.FOOD_PRICE, 0.00));
		fi.setLowSodium(parseBoolean(item, this.DIET_LOWSODIUM, false));
		fi.setLowCholesterol(parseBoolean(item, this.DIET_LOWCHOLESTEROL, false));
		fi.setGlutenFree(parseBoolean(item, this.DIET_GLUTENFREE, false));
		fi.setVegan(parseBoolean(item, this.DIET_VEGAN, false));

		return fi;

	}

	private interface MyJSONParser {
		public void parseJSONTree(JSONObject o);
	}

	private void parseJSONFile(String filename, MyJSONParser treeParser){
		parseJSONFile(filename,treeParser,false);
	}
	
	private void parseJSONFile(String filename, MyJSONParser treeParser,boolean retry) {
		JSONParser parser = new JSONParser();
		try {
			JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(filename));
			if (treeParser != null)
				treeParser.parseJSONTree(jsonObject);
		} catch (FileNotFoundException e) {
			InputStream in = getClass().getResourceAsStream(filename);
			BufferedReader input = new BufferedReader(new InputStreamReader(in));
			try {
				JSONObject jsonObject = (JSONObject) parser.parse(input);
				if (treeParser != null)
					treeParser.parseJSONTree(jsonObject);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	private void loadFoodItems() {
		parseJSONFile("data/food_items.json", new MyJSONParser() {
			public void parseJSONTree(JSONObject o) {
				items.clear();

				JSONArray foodItems = (JSONArray) o.get(ITEMS_ROOT);

				Iterator<JSONObject> iterator = foodItems.iterator();
				while (iterator.hasNext()) {
					items.add(parseFoodItem(iterator.next()));
				}
			}
		});
	}

	private DietaryProfile parseUserDiet(JSONObject diet) {
		DietaryProfile profile = new DietaryProfile();
		if (diet != null) {
			profile.setCalorieMinimum(parseInt(diet, this.DIET_CALORIEMINIMUM, 2000));
			profile.setCalorieMaximum(parseInt(diet, this.DIET_CALORIEMAXIMUM, 2500));
			profile.setLowSodium(parseBoolean(diet, this.DIET_LOWSODIUM, false));
			profile.setLowCholesterol(parseBoolean(diet, this.DIET_LOWCHOLESTEROL, false));
			profile.setGlutenFree(parseBoolean(diet, this.DIET_GLUTENFREE, false));
			profile.setVegan(parseBoolean(diet, this.DIET_VEGAN, false));
		}

		return profile;
	}

	private Order parseUserOrder(JSONObject trans) {
		Order order = new Order();
		order.setMemo(parseString(trans, TRANSACTION_MEMO, "Purchase from Cafe"));
		order.setBalance(parseDouble(trans, TRANSACTION_BALANCE, 0.00));
		order.setPurchaseDate(parseDate(trans, TRANSACTION_DATE));
		
		// order has been purchased... (this sets flag)
		order.setTotalCaloriesFromFile(parseInt(trans,ORDER_TOTALCALORIES,0));
		order.purhcase();

		JSONArray items = parseArray(trans, ORDER_ITEMS);
		if (items != null) {
			Iterator<Long> it = items.iterator();
			while (it.hasNext()) {
				order.addItemToOrder(FoodItem.getItem(it.next().intValue()));
			}
		}

		return order;
	}

	private Deposit parseUserDeposit(JSONObject trans) {
		Double amt = parseDouble(trans, TRANSACTION_AMOUNT, 0.00);
		Double balance = parseDouble(trans, TRANSACTION_BALANCE, 0.00);
		Date date = parseDate(trans, TRANSACTION_DATE);
		return new Deposit(amt, balance, date);
	}

	private AccountTransaction parseUserTransaction(JSONObject trans) {
		String type = parseString(trans, TRANSACTION_TYPE, "NONE");

		if (type.equals(Order.getType())) {
			return parseUserOrder(trans);
		} else if (type.equals(Deposit.getType())) {
			return parseUserDeposit(trans);
		}
		return null;
	}

	private UserAccount parseUserAccount(JSONObject user) {
		UserAccount acct = new UserAccount();
		acct.setCardNumber(parseInt(user, USER_CARDNUMBER, null));
		acct.setPassword(parseString(user, USER_PASSWORD, null));
		acct.setRemainingBalance(parseDouble(user, USER_BALANCE, 0.00));
		acct.setDiet(parseUserDiet(parseObject(user, USER_DIET)));

		JSONArray history = parseArray(user, USER_HISTORY);
		if (history != null) {
			Iterator<JSONObject> it = history.iterator();
			while (it.hasNext()) {
				acct.addTransaction(parseUserTransaction(it.next()));
			}
		}

		return acct;
	}

	private void loadUsers() {
		parseJSONFile(this.FILENAME_USERS, new MyJSONParser() {
			public void parseJSONTree(JSONObject o) {
				accounts.clear();

				JSONArray users = (JSONArray) o.get(USERS_ROOT);

				Iterator<JSONObject> iterator = users.iterator();
				while (iterator.hasNext()) {
					accounts.add(parseUserAccount(iterator.next()));
				}
			}
		});
	}

	private static final String FILENAME_VENDORS = "data/vendors.json";
	private static final String VENDORS_ROOT = "vendors";
	private static final String VENDOR_ID = "id";
	private static final String VENDOR_TYPE = "type";
	private static final String VENDOR_NAME = "name";
	private static final String VENDOR_DESCRIPTION = "description";
	private static final String VENDOR_LATITUDE = "latitude";
	private static final String VENDOR_LONGITUDE = "longitude";
	private static final String VENDOR_MENU = "menu";

	private void loadVendors() {
		parseJSONFile(this.FILENAME_VENDORS, new MyJSONParser() {
			public void parseJSONTree(JSONObject o) {
				vendors.clear();

				JSONArray arr = (JSONArray) o.get(VENDORS_ROOT);

				Iterator<JSONObject> iterator = arr.iterator();
				while (iterator.hasNext()) {
					JSONObject obj = iterator.next();
					vendors.add(parseVendor(obj));
				}
			}
		});
	}

	private AbstractVendor parseVendor(JSONObject obj) {
		String type = parseString(obj, VENDOR_TYPE, VendingMachine.VENDOR_TYPE);
		AbstractVendor vendor;

		if (type.equals(Cafe.VENDOR_TYPE)) {
			vendor = new Cafe();
		} else if (type.equals(VendingMachine.VENDOR_TYPE)) {
			vendor = new VendingMachine();
		} else {
			return null;
		}

		vendor.setName(parseString(obj, VENDOR_NAME, ""));
		vendor.setDescription(parseString(obj, VENDOR_DESCRIPTION, ""));
		vendor.setId(parseInt(obj, VENDOR_ID, 0));
		vendor.setCoordinates(parseDouble(obj, VENDOR_LATITUDE, 0.0), parseDouble(obj, VENDOR_LONGITUDE, 0.0));

		JSONArray items = parseArray(obj, VENDOR_MENU);
		if (items != null) {
			Iterator<Long> it = items.iterator();
			while (it.hasNext()) {
				vendor.addItemToMenu(FoodItem.getItem(it.next().intValue()));
			}
		}

		return vendor;
	}

}
