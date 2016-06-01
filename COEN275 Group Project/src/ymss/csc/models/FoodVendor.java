package ymss.csc.models;

import java.util.List;

public interface FoodVendor {
	
	public void purchase();
	
	public void addItemToMenu(FoodItem item);
	
	public List<FoodItem> getMenu();
	
}
