package ymss.csc.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ymss.csc.models.DietaryProfile;

public class HealthFrame extends JFrame implements IHealthFrame{

	private static final long serialVersionUID = 724764215279243988L;

	static final String title = "My Dietary Profile";

	private JButton btnEdit;

	private JLabel lblTitle;
	private JLabel lblCaloriesPrefix;
	private JLabel lblCalories;
	private JLabel lblPrefs;
	
	private JPanel pnlPrefsList;
	
	private JPanel pnlChart;
	
	
	
	public HealthFrame() {
		// Window initialization
		setTitle(title);
		setSize(800, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		JPanel tempPanel = new JPanel();
		tempPanel.setBackground(Color.PINK);
		this.getContentPane().add(tempPanel,BorderLayout.CENTER);
		
		tempPanel.setLayout(new GridBagLayout());
		GridBagConstraints cs = new GridBagConstraints();
		
		lblTitle = new JLabel("My Health");
		cs.fill = GridBagConstraints.HORIZONTAL;
		cs.weightx = 0.9;
		cs.weighty = 0.2;
		cs.gridx = 0;
		cs.gridy = 0;
		cs.gridwidth = 2;
		tempPanel.add(lblTitle,cs);
		
		lblCaloriesPrefix = new JLabel("Calories Per Day:");
		cs.fill = GridBagConstraints.HORIZONTAL;
		cs.gridx = 0;
		cs.gridy = 1;
		cs.gridwidth = 1;
		tempPanel.add(lblCaloriesPrefix,cs);
		
		lblCalories = new JLabel("1200 - 3500");
		cs.fill = GridBagConstraints.HORIZONTAL;
		cs.gridx = 1;
		cs.gridy = 1;
		cs.gridwidth = 1;
		tempPanel.add(lblCalories,cs);
		
		lblPrefs = new JLabel("Food Preferences");
		cs.fill = GridBagConstraints.HORIZONTAL;
		cs.gridx = 0;
		cs.gridy = 2;
		cs.gridwidth = 1;
		tempPanel.add(lblPrefs,cs);
		
		pnlPrefsList = new JPanel();
		cs.fill = GridBagConstraints.HORIZONTAL;
		cs.gridx = 1;
		cs.gridy = 2;
		cs.gridwidth = 1;
		tempPanel.add(pnlPrefsList,cs);
		initPreferencesList(null);

		btnEdit = new JButton("Edit Dietary Preferences");
		cs.fill = GridBagConstraints.HORIZONTAL;
		cs.gridx = 0;
		cs.gridy = 3;
		cs.gridwidth = 2;
		tempPanel.add(btnEdit,cs);
		
		pnlChart = new JPanel();
		pnlChart.setBackground(Color.CYAN);
		cs.fill = GridBagConstraints.BOTH;
		cs.gridx = 0;
		cs.gridy = 4;
		cs.gridwidth = 2;
		cs.weighty = 0.8;
		tempPanel.add(pnlChart,cs);
		
		

	}
	
	private void initPreferencesList(List<String> prefs){
		pnlPrefsList.removeAll();

		pnlPrefsList.setLayout(new BoxLayout(pnlPrefsList,BoxLayout.Y_AXIS));
		
		if(prefs == null) return;
		
		Iterator<String> it = prefs.iterator();
		while(it.hasNext()){
			String str = it.next();
			System.out.println(str);
			JLabel lblPref = new JLabel(str);
			pnlPrefsList.add(lblPref);
		}
		pnlPrefsList.repaint();
		pnlPrefsList.revalidate();
	}

	public void addEditListener(ActionListener l) {
		btnEdit.addActionListener(l);
	}

	public void removeEditListener(ActionListener l) {
		btnEdit.removeActionListener(l);
	}
	
	private void setCaloricRange(Integer min,Integer max){
		lblCalories.setText(min+" - "+max);
	}

	@Override
	public void initialize(DietaryProfile diet) {
		setCaloricRange(diet.getCalorieMinimum(),diet.getCalorieMaximum());
		List<String> preferences = new ArrayList<String>();
		
		if(diet.isLowSodium()) preferences.add("Low Sodium");
		if(diet.isLowCholesterol()) preferences.add("Low Choleseterol");
		if(diet.isGlutenFree()) preferences.add("Gluten Free");
		if(diet.isVegan()) preferences.add("Vegan");
		
		initPreferencesList(preferences);		
	}

}
