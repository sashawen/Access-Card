package ymss.csc.stores;

import java.util.List;

import ymss.csc.models.FoodItem;
import ymss.csc.models.FoodVendor;
import ymss.csc.models.UserAccount;

public interface PersistentDataStore {
	
	public void init();

	public List<UserAccount> getAccounts();
	
	public UserAccount getAccount(int cardNumber);
	
	public void updateAccount(UserAccount user);
	
	List<FoodVendor> getVendors();
	
	public void updateVendor(FoodVendor vendor);
	
	List<FoodItem> getFoodItems();
	
	public void updateFoodItem(FoodItem food);
	
	
}
