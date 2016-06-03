package ymss.csc.views;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import ymss.csc.models.DietaryProfile;
import ymss.csc.models.UserAccount;
import javax.swing.BoxLayout;
import java.awt.GridLayout;

public class EditHealthFrame extends JFrame implements Observer {

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

	private UserAccount user;
	private JPanel tempPanel;

	private void addRow(JPanel parent, JLabel label, Component comp) {
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		parent.add(panel);
		panel.setLayout(new GridLayout(0, 2, 30, 0));

		panel.add(label);
		panel.add(comp);

	}

	public EditHealthFrame(UserAccount user) {
		this.user = user;
		// Window initialization
		setTitle(title);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		tempPanel = new JPanel();
		tempPanel.setLayout(new BoxLayout(tempPanel, BoxLayout.Y_AXIS));

		lblCalorieMinimum = new JLabel("Calorie Min.");
		tfCalorieMinimum = new JTextField(5);
		addRow(tempPanel, lblCalorieMinimum, tfCalorieMinimum);

		lblCalorieMaximum = new JLabel("Calorie Max.");
		tfCalorieMaximum = new JTextField(5);
		addRow(tempPanel, lblCalorieMaximum, tfCalorieMaximum);

		lblLowSodium = new JLabel("Low Sodium");
		chbLowSodium = new JCheckBox();
		addRow(tempPanel, lblLowSodium, chbLowSodium);

		lblLowCholesterol = new JLabel("Low Cholesterol");
		chbLowCholesterol = new JCheckBox();
		addRow(tempPanel, lblLowCholesterol, chbLowCholesterol);

		lblGlutenFree = new JLabel("Gluten Free");
		chbGlutenFree = new JCheckBox();
		addRow(tempPanel, lblGlutenFree, chbGlutenFree);

		lblVegan = new JLabel("Vegan");
		chbVegan = new JCheckBox();
		addRow(tempPanel, lblVegan, chbVegan);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EmptyBorder(5, 5, 5, 5));
		tempPanel.add(panel_1);
		panel_1.setLayout(new GridLayout(0, 2, 15, 0));

		btnCancel = new JButton("Cancel");
		panel_1.add(btnCancel);

		btnSave = new JButton("Save");
		panel_1.add(btnSave);
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DietaryProfile d = new DietaryProfile();
				d.setCalorieMinimum(getCalorieMinimum());
				d.setCalorieMaximum(getCalorieMaximum());
				d.setLowSodium(getLowSodium());
				d.setLowCholesterol(getLowCholesterol());
				d.setGlutenFree(getGlutenFree());
				d.setVegan(getVegan());

				user.setDiet(d);
				
				dispose();
			}
		});
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		tempPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		setContentPane(tempPanel);

		pack();
		setLocationRelativeTo(null);

		user.addObserver(this);
		redraw(user);
	}

	private void redraw(UserAccount user) {
		DietaryProfile diet = user.getDiet();
		setCalorieMinimum(diet.getCalorieMinimum());
		setCalorieMaximum(diet.getCalorieMaximum());
		setLowSodium(diet.isLowSodium());
		setLowCholesterol(diet.isLowCholesterol());
		setGlutenFree(diet.isGlutenFree());
		setVegan(diet.isVegan());
		
		repaint();
		revalidate();
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		redraw(user);
	}

	public void setCalorieMinimum(Integer calMin) {
		tfCalorieMinimum.setText(calMin.toString());
	}

	public Integer getCalorieMinimum() {
		try {
			return Integer.parseInt(tfCalorieMinimum.getText());
		} catch (Exception e) {
			return null;
		}
	}

	public void setCalorieMaximum(Integer calMax) {
		tfCalorieMaximum.setText(calMax.toString());
	}

	public Integer getCalorieMaximum() {
		try {
			return Integer.parseInt(tfCalorieMaximum.getText());
		} catch (Exception e) {
			return null;
		}
	}

	public void setLowSodium(Boolean lowSodium) {
		chbLowSodium.setSelected(lowSodium);
	}

	public Boolean getLowSodium() {
		return chbLowSodium.isSelected();
	}

	public void setLowCholesterol(Boolean lowCholesterol) {
		chbLowCholesterol.setSelected(lowCholesterol);
	}

	public Boolean getLowCholesterol() {
		return chbLowCholesterol.isSelected();
	}

	public void setGlutenFree(Boolean glutenFree) {
		chbGlutenFree.setSelected(glutenFree);
	}

	public Boolean getGlutenFree() {
		return chbGlutenFree.isSelected();
	}

	public void setVegan(Boolean vegan) {
		chbVegan.setSelected(vegan);
	}

	public Boolean getVegan() {
		return chbVegan.isSelected();
	}

	public void addSaveListener(ActionListener l) {
		btnSave.addActionListener(l);
	}
}
