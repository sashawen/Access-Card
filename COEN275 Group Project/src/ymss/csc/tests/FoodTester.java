package ymss.csc.tests;

import java.util.Date;

import ymss.csc.models.*;

public class FoodTester {

	public static void main(String[] args){
		FoodItem pizza = new FoodItem("Pizza");
		pizza.setCalories(500);
		pizza.setDescription("With luscious sauce, gooey cheese, and crispy crust");
		pizza.setPrice(1.99);
		
		Order myOrder = new Order();
		for(int i = 0; i < 5; i++){
			myOrder.addItemToOrder(pizza);			
		}

		System.out.println("Total Calories: "+myOrder.getTotalCalories());
		System.out.println("Total Cost: "+myOrder.getTotalCost());
		
		myOrder.setPurchaseDate(new Date());
		System.out.println("Date: "+myOrder.getPurchaseDate());
	}
}
