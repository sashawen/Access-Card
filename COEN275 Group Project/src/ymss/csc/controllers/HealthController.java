package ymss.csc.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import ymss.csc.models.*;
import ymss.csc.views.*;

public class HealthController {

	private HealthFrame healthFrame;
	private IEditHealthFrame editHealthFrame;
	private UserAccount user;
	
	private ActionListener saveListener;
	
	public void launch(UserAccount user){
		this.user = user;
		launchHealthFrame();
	}
	
	public void launchHealthFrame() {
		if (healthFrame == null)
			healthFrame = new HealthFrame();

		healthFrame.initialize(user.getDiet());
		
		healthFrame.addEditListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				launchEditHealthFrame();
			}
		});

		healthFrame.setVisible(true);
	}

	public void launchEditHealthFrame() {
		if (editHealthFrame == null)
			editHealthFrame = new EditHealthFrame();
		
		editHealthFrame.initialize(user.getDiet());
		
		if(saveListener == null){
			saveListener = new ActionListener(){
				public void actionPerformed(ActionEvent e){
					DietaryProfile d = new DietaryProfile();
					d.setCalorieMinimum(editHealthFrame.getCalorieMinimum());
					d.setCalorieMaximum(editHealthFrame.getCalorieMaximum());
					d.setLowSodium(editHealthFrame.getLowSodium());
					d.setLowCholesterol(editHealthFrame.getLowCholesterol());
					d.setGlutenFree(editHealthFrame.getGlutenFree());
					d.setVegan(editHealthFrame.getVegan());
					
					healthFrame.initialize(d);
					
					if(healthListener != null) healthListener.profileChanged(d);
					
					((JFrame)editHealthFrame).dispose();
				}
			};
			editHealthFrame.addSaveListener(saveListener);
			
		}
		

		((JFrame) editHealthFrame).setVisible(true);
	}
	
	public interface HealthChangeListener {
		public void profileChanged(DietaryProfile updatedProfile);
	}
	
	private HealthChangeListener healthListener;
	
	public void setHealthListener(HealthChangeListener h){
		healthListener = h;
	}

	
}
