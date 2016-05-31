package ymss.csc.views;

import java.awt.event.ActionListener;

import ymss.csc.models.DietaryProfile;

public interface IEditHealthFrame {

	public void initialize(DietaryProfile diet);

	public Integer getCalorieMinimum();

	public void setCalorieMinimum(Integer calMin);

	public Integer getCalorieMaximum();

	public void setCalorieMaximum(Integer calMax);

	public Boolean getLowSodium();

	public void setLowSodium(Boolean lowSodium);

	public Boolean getLowCholesterol();

	public void setLowCholesterol(Boolean lowCholesterol);

	public Boolean getGlutenFree();

	public void setGlutenFree(Boolean glutenFree);

	public Boolean getVegan();

	public void setVegan(Boolean vegan);

	public void addSaveListener(ActionListener l);

}
