package ymss.csc.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ymss.csc.application.Constants;
import ymss.csc.models.AccountTransaction;
import ymss.csc.models.DietaryProfile;
import ymss.csc.models.Order;
import ymss.csc.models.UserAccount;
import ymss.csc.views.charts.BarChart;

import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Font;
import java.awt.Component;
import java.awt.Dimension;

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

	private static final Color COLOR_LIGHTGRAY = new Color(224, 224, 224);
	private static final Color COLOR_INRANGE = new Color(23, 106, 130);
	private static final Color COLOR_OUTRANGE = new Color(255, 68, 35);

	private BarChart chart;

	private Boolean onSameDay(Date a, Date b){
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(a);
		cal2.setTime(b);
		boolean sameDay = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
		                  cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
		return sameDay;
	}
	
	private List<String> getPastWeekCaptions(){
		
		List<String> dates = new ArrayList<String>();
		
		for(int i = -6; i <= 0; i++){
			Calendar day = Calendar.getInstance();
			day.setTime(new Date());
			day.set(Calendar.DAY_OF_YEAR,day.get(Calendar.DAY_OF_YEAR) + i);
			String datestring = String.format("%d/%d", day.get(Calendar.MONTH)+1,day.get(Calendar.DAY_OF_MONTH));
			dates.add(datestring);
		}
		return dates;
	}
	
	private Double getCaloriesOnDay(UserAccount user,Date date){
		// Assumes linear order history.
		List<AccountTransaction> trans = user.getHistory();
		Iterator<AccountTransaction> it = trans.iterator();
		Double totalCals = 0.0;
		while(it.hasNext()){
			AccountTransaction t = it.next();
			if(t instanceof Order){
				Order order = (Order) t;
				if(onSameDay(order.getDate(),date)){
					totalCals = totalCals + order.getTotalCalories();
				}
			}
		}
		return totalCals;
	}
	
	private List<Double> getPastWeekCalories(UserAccount user){
		List<Double> cals = new ArrayList<Double>();

		for (int i = -6; i <= 0; i++) {
			Calendar day = Calendar.getInstance();
			day.setTime(new Date());
			day.set(Calendar.DAY_OF_YEAR, day.get(Calendar.DAY_OF_YEAR) + i);
			Date d = day.getTime();
			cals.add(getCaloriesOnDay(user, d));
		}
		return cals;
	}
	
	public HealthFrame(UserAccount user) {
		this.user = user;

		// Window initialization
		setTitle(title);
		setSize(800, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		JPanel tempPanel = new JPanel();
		tempPanel.setBackground(this.COLOR_LIGHTGRAY);
		this.getContentPane().add(tempPanel, BorderLayout.CENTER);
		tempPanel.setLayout(new GridLayout(0, 1, 0, 0));

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
		lblTitle.setFont(Constants.FONT_HEADING_3);

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

		addChart(tempPanel);

		user.addObserver(this);
		redraw(user);

		// pack();

	}
	
	private void addChart(JPanel parent){
		pnlChart = new JPanel();
		parent.add(pnlChart);

		pnlChart.setLayout(new BorderLayout());

		chart = new BarChart();
		chart.setCeiling(3000.0);
		chart.setColorInRange(COLOR_INRANGE);
		chart.setColorOutOfRange(COLOR_OUTRANGE);
		chart.setChartTitle("Daily Caloric Intake");
		chart.setRangeMin((double) user.getDiet().getCalorieMinimum());
		chart.setRangeMax((double) user.getDiet().getCalorieMaximum());

		chart.clear();
		
		List<String> captions = getPastWeekCaptions();
		List<Double> values = this.getPastWeekCalories(user);
		for(int i = 0; i < captions.size(); i++){
			chart.addDatum(captions.get(i), values.get(i));
		}

		pnlChart.add(chart, BorderLayout.CENTER);
	}

	private void redraw(UserAccount user) {
		initialize(user.getDiet());
		chart.setRangeMin((double) user.getDiet().getCalorieMinimum());
		chart.setRangeMax((double) user.getDiet().getCalorieMaximum());
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
