package ymss.csc.models;

public class DietaryProfile {

	private int calorieMinimum;
	private int calorieMaximum;
	
	private boolean lowSodium;
	private boolean lowCholesterol;
	private boolean glutenFree;
	private boolean vegan;
	
	public DietaryProfile(){
		setCalorieMinimum(1200);
		setCalorieMaximum(3500);
		
		setLowSodium(false);
		setLowCholesterol(false);
		setGlutenFree(false);
		setVegan(false);
	}
	
	public int getCalorieMinimum() {
		return calorieMinimum;
	}

	public void setCalorieMinimum(int calorieMinimum) {
		this.calorieMinimum = calorieMinimum;
	}

	public int getCalorieMaximum() {
		return calorieMaximum;
	}

	public void setCalorieMaximum(int calorieMaximum) {
		this.calorieMaximum = calorieMaximum;
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
