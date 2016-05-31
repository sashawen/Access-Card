package ymss.csc.views;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import ymss.csc.models.DietaryProfile;

public class EditHealthFrame extends JFrame implements IEditHealthFrame{

	private static final long serialVersionUID = 4238472435606246405L;

	static final String title = "Edit Dietary Preferences";

	private JLabel lblCalorieMinimum;
	private JTextField tfCalorieMinimum;
	private JLabel lblCalorieMaximum;
	private JTextField tfCalorieMaximum;
	private JLabel lblLowSodium;
	private JCheckBox chbLowSodium;
	private JLabel lblLowCholesterol;
	private JCheckBox chbLowCholesterol;
	private JLabel lblGlutenFree;
	private JCheckBox chbGlutenFree;
	private JLabel lblVegan;
	private JCheckBox chbVegan;

	private JButton btnCancel;
	private JButton btnSave;
	
	public void initialize(DietaryProfile diet){
		setCalorieMinimum(diet.getCalorieMinimum());
		setCalorieMaximum(diet.getCalorieMaximum());
		setLowSodium(diet.isLowSodium());
		setLowCholesterol(diet.isLowCholesterol());
		setGlutenFree(diet.isGlutenFree());
		setVegan(diet.isVegan());
	}
	
	private GridBagConstraints createGbc(int x, int y) {
	      GridBagConstraints gbc = new GridBagConstraints();
	      gbc.gridx = x;
	      gbc.gridy = y;
	      gbc.gridwidth = 1;
	      gbc.gridheight = 1;

	      gbc.anchor = (x == 0) ? GridBagConstraints.WEST : GridBagConstraints.EAST;
	      gbc.fill = (x == 0) ? GridBagConstraints.BOTH
	            : GridBagConstraints.HORIZONTAL;

	      //gbc.insets = (x == 0) ? WEST_INSETS : EAST_INSETS;
	      gbc.weightx = (x == 0) ? 0.1 : 1.0;
	      gbc.weighty = 1.0;
	      return gbc;
	   }
	
	private void addFormPair(JPanel dest, JLabel label, Component field, Integer row) {
		
		if(dest == null || label == null || field == null) return;
		
		dest.add(label, createGbc(0,row));
		dest.add(field, createGbc(1,row));

		return;
	}

	public EditHealthFrame() {
		// Window initialization
		setTitle(title);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		JPanel tempPanel = new JPanel();

		tempPanel.setLayout(new GridBagLayout());

		GridBagConstraints cs = new GridBagConstraints();

		lblCalorieMinimum = new JLabel("Calorie Min.");
		tfCalorieMinimum = new JTextField(5);
		addFormPair(tempPanel, lblCalorieMinimum, tfCalorieMinimum, 0);

		lblCalorieMaximum = new JLabel("Calorie Max.");
		tfCalorieMaximum = new JTextField(5);
		addFormPair(tempPanel, lblCalorieMaximum, tfCalorieMaximum, 1);
		
		lblLowSodium = new JLabel("Low Sodium");
		chbLowSodium = new JCheckBox();
		addFormPair(tempPanel, lblLowSodium, chbLowSodium, 2);
		
		lblLowCholesterol = new JLabel("Low Cholesterol");
		chbLowCholesterol = new JCheckBox();
		addFormPair(tempPanel, lblLowCholesterol, chbLowCholesterol, 3);
		
		lblGlutenFree = new JLabel("Gluten Free");
		chbGlutenFree = new JCheckBox();
		addFormPair(tempPanel,lblGlutenFree,chbGlutenFree,4);
		
		lblVegan = new JLabel("Vegan");
		chbVegan = new JCheckBox();
		addFormPair(tempPanel,lblVegan,chbVegan,5);
		
		btnCancel = new JButton("Cancel");
		cs.gridx = 0;
		cs.gridy = 6;
		cs.weightx = 0.5;
		btnCancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				dispose();
			}
		});
		tempPanel.add(btnCancel,cs);
		
		btnSave = new JButton("Save");
		cs.gridx = 1;
		cs.gridy = 6;
		cs.weightx = 0.5;
		tempPanel.add(btnSave,cs);
		

		tempPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(tempPanel);	
		
		//pack();
		this.setSize(400, 300);
		setLocationRelativeTo(null);
	}
	
	public void setCalorieMinimum(Integer calMin){
		tfCalorieMinimum.setText(calMin.toString());
	}
	
	public Integer getCalorieMinimum(){
		try{
			return Integer.parseInt(tfCalorieMinimum.getText());
		}catch(Exception e){
			return null;
		}
	}
	
	public void setCalorieMaximum(Integer calMax){
		tfCalorieMaximum.setText(calMax.toString());
	}

	public Integer getCalorieMaximum(){
		try{
			return Integer.parseInt(tfCalorieMaximum.getText());
		}catch(Exception e){
			return null;
		}
	}
	
	public void setLowSodium(Boolean lowSodium){
		chbLowSodium.setSelected(lowSodium);
	}
	public Boolean getLowSodium(){
		return chbLowSodium.isSelected();
	}
	
	public void setLowCholesterol(Boolean lowCholesterol){
		chbLowCholesterol.setSelected(lowCholesterol);
	}
	
	public Boolean getLowCholesterol(){
		return chbLowCholesterol.isSelected();
	}
	
	public void setGlutenFree(Boolean glutenFree){
		chbGlutenFree.setSelected(glutenFree);
	}

	public Boolean getGlutenFree(){
		return chbGlutenFree.isSelected();
	}
	
	public void setVegan(Boolean vegan){
		chbVegan.setSelected(vegan);
	}
	
	public Boolean getVegan(){
		return chbVegan.isSelected();
	}
	
	public void addSaveListener(ActionListener l){
		btnSave.addActionListener(l);
	}
}
