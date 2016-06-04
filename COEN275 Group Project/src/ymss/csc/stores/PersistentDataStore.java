package ymss.csc.stores;

import java.util.List;

import ymss.csc.models.*;

public interface PersistentDataStore {

	public void init();

	public List<UserAccount> getAccounts();

	public UserAccount getAccount(int cardNumber);

	public void updateAccount(UserAccount user);

	List<AbstractVendor> getVendors();

	List<FoodItem> getFoodItems();

}
