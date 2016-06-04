package ymss.csc.models;

import java.util.ArrayList;
import java.util.List;

abstract public class AbstractVendor {
		
	protected String name;
	protected String description;
	protected Integer id;
	protected Double latitude;
	protected Double longitude;
	protected List<FoodItem> menu = new ArrayList<FoodItem>();
	
	
	public void addItemToMenu(FoodItem item){
		menu.add(item);
	};
	
	public List<FoodItem> getMenu(){
		return menu;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public void setDescription(String description){
		this.description = description;
	}
	
	public String getDescription(){
		return description;
	}
	
	public void setId(Integer id){
		this.id = id;
	}
	
	public void setCoordinates(Double _lat, Double _long){
		latitude = _lat;
		longitude = _long;
	}
	
	public Double getLatitude(){
		return latitude;
	}
	
	public Double getLongitude(){
		return longitude;
	}
}
