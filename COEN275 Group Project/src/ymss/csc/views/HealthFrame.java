package ymss.csc.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ymss.csc.models.DietaryProfile;
import ymss.csc.models.UserAccount;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Font;
import java.awt.Component;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextPane;

public class HealthFrame extends JFrame implements Observer {

	private static final long serialVersionUID = 724764215279243988L;

	static final String title = "My Dietary Profile";

	private JButton btnEdit;

	private JLabel lblTitle;
	private JLabel lblCaloriesPrefix;
	private JLabel lblCalories;
	private JLabel lblPrefs;

	private JPanel pnlPrefsList;

	private JPanel pnlChart;

	private UserAccount user;

	public EditHealthFrame editHealthFrame;
	private JPanel pnlSummary;
	private JPanel pnlCalories;
	private JPanel pnlPreferences;
	private JPanel panel_2;
	private JPanel pnlButton;

	public HealthFrame(UserAccount user) {
		this.user = user;

		// Window initialization
		setTitle(title);
		// setSize(800, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		JPanel tempPanel = new JPanel();
		tempPanel.setBackground(Color.PINK);
		this.getContentPane().add(tempPanel, BorderLayout.CENTER);
		tempPanel.setLayout(new BoxLayout(tempPanel, BoxLayout.Y_AXIS));

		pnlSummary = new JPanel();
		tempPanel.add(pnlSummary);
		pnlSummary.setLayout(new BoxLayout(pnlSummary, BoxLayout.Y_AXIS));

		panel_2 = new JPanel();
		pnlSummary.add(panel_2);
		panel_2.setLayout(new GridLayout(0, 1, 0, 0));

		lblTitle = new JLabel("My Health");
		panel_2.add(lblTitle);
		lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Viner Hand ITC", Font.BOLD, 16));

		pnlCalories = new JPanel();
		pnlCalories.setBorder(new EmptyBorder(10, 10, 10, 10));
		pnlSummary.add(pnlCalories);
		pnlCalories.setLayout(new GridLayout(0, 2, 40, 0));

		lblCaloriesPrefix = new JLabel("Calories Per Day:");
		lblCaloriesPrefix.setVerticalAlignment(SwingConstants.TOP);
		pnlCalories.add(lblCaloriesPrefix);

		lblCalories = new JLabel("1200 - 3500");
		lblCalories.setVerticalAlignment(SwingConstants.TOP);
		pnlCalories.add(lblCalories);

		pnlPreferences = new JPanel();
		pnlPreferences.setBorder(new EmptyBorder(10, 10, 10, 10));
		pnlSummary.add(pnlPreferences);
		pnlPreferences.setLayout(new GridLayout(0, 2, 40, 0));

		lblPrefs = new JLabel("Food Preferences");
		lblPrefs.setVerticalAlignment(SwingConstants.TOP);
		lblPrefs.setHorizontalAlignment(SwingConstants.LEFT);
		pnlPreferences.add(lblPrefs);

		pnlPrefsList = new JPanel();
		pnlPrefsList.setBorder(null);
		pnlPreferences.add(pnlPrefsList);
		pnlPrefsList.setLayout(new BoxLayout(pnlPrefsList, BoxLayout.Y_AXIS));

		pnlButton = new JPanel();
		pnlSummary.add(pnlButton);

		btnEdit = new JButton("Edit Dietary Preferences");
		pnlButton.add(btnEdit);
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (editHealthFrame == null)
					editHealthFrame = new EditHealthFrame(user);

				((JFrame) editHealthFrame).setVisible(true);
			}
		});
		initPreferencesList(null);

		pnlChart = new JPanel();
		pnlChart.setBackground(Color.CYAN);
		tempPanel.add(pnlChart);

		user.addObserver(this);
		redraw(user);

		pack();

	}

	private void redraw(UserAccount user) {
		initialize(user.getDiet());
		repaint();
		revalidate();
	}

	public void update(Observable o, Object obj) {
		DietaryProfile diet = user.getDiet();
		redraw(user);
	}

	public void initialize(DietaryProfile diet) {
		setCaloricRange(diet.getCalorieMinimum(), diet.getCalorieMaximum());
		List<String> preferences = new ArrayList<String>();

		if (diet.isLowSodium())
			preferences.add("Low Sodium");
		if (diet.isLowCholesterol())
			preferences.add("Low Choleseterol");
		if (diet.isGlutenFree())
			preferences.add("Gluten Free");
		if (diet.isVegan())
			preferences.add("Vegan");

		initPreferencesList(preferences);
	}

	private void initPreferencesList(List<String> prefs) {
		pnlPrefsList.removeAll();

		if (prefs == null)
			return;

		Iterator<String> it = prefs.iterator();
		while (it.hasNext()) {
			JLabel lblPref = new JLabel(it.next());
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

	private void setCaloricRange(Integer min, Integer max) {
		lblCalories.setText(min + " - " + max);
	}

}
