package ymss.csc.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import java.awt.Component;
import javax.swing.border.LineBorder;

import ymss.csc.models.AccountTransaction;
import ymss.csc.models.Order;
import ymss.csc.models.UserAccount;
import ymss.csc.views.charts.BarChart;
import ymss.csc.views.charts.LineChart;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;

public class FinanceFrame extends JFrame implements Observer {

	private static final long serialVersionUID = -126850826291601022L;

	static final String title = "My Financial Profile";

	private JButton btnDeposit;
	private JLabel lblCurrentBalance;
	private JLabel lblDailySpent;

	private FundDepositFrame fundDepositFrame;

	private UserAccount user;
	private JPanel tempPanel;

	private BarChart chart;
	private LineChart chart1;
	private LineChart chart2;

	private static final Color COLOR_LIGHTGRAY = new Color(224, 224, 224);
	private static final Color COLOR_INRANGE = new Color(23, 106, 130);
	private static final Color COLOR_OUTRANGE = new Color(255, 68, 35);

	public FinanceFrame(UserAccount user) {
		this.user = user;

		// Window initialization
		setTitle(title);
		setSize(800, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

		tempPanel = new JPanel();
		this.getContentPane().add(tempPanel);
		tempPanel.setLayout(new BoxLayout(tempPanel, BoxLayout.Y_AXIS));

		initSummaryPanel(tempPanel);
		initChartPanel(tempPanel);

		user.addObserver(this);
		redraw(user);

	}

	private void initSummaryPanel(JPanel parent) {
		if (parent == null)
			return;

		JPanel pnlSummary = new JPanel();
		pnlSummary.setBorder(new EmptyBorder(20, 20, 20, 20));
		parent.add(pnlSummary);
		pnlSummary.setLayout(new BoxLayout(pnlSummary, BoxLayout.Y_AXIS));

		JPanel pnlBalance = new JPanel();
		pnlBalance.setBorder(new EmptyBorder(10, 10, 10, 10));
		pnlSummary.add(pnlBalance);
		pnlBalance.setLayout(new BoxLayout(pnlBalance, BoxLayout.X_AXIS));

		JLabel lblCurrentBalancePrefix = new JLabel("Current Balance: ");
		pnlBalance.add(lblCurrentBalancePrefix);

		lblCurrentBalance = new JLabel("$0.00");
		pnlBalance.add(lblCurrentBalance);

		btnDeposit = new JButton("Deposit Funds");
		btnDeposit.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnDeposit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fundDepositFrame == null)
					fundDepositFrame = new FundDepositFrame(user);

				fundDepositFrame.setVisible(true);
			}
		});
		pnlSummary.add(btnDeposit);

		JPanel pnlDailySpent = new JPanel();
		pnlDailySpent.setBorder(new EmptyBorder(10, 10, 10, 10));
		pnlSummary.add(pnlDailySpent);
		pnlDailySpent.setLayout(new BoxLayout(pnlDailySpent, BoxLayout.X_AXIS));

		JLabel lblDailySpentPrefix = new JLabel("Current Amount Spent Today: ");
		pnlDailySpent.add(lblDailySpentPrefix);

		lblDailySpent = new JLabel("$0.00");
		pnlDailySpent.add(lblDailySpent);

	}

	private void initChartPanel(JPanel parent) {
		if (parent == null)
			return;

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tempPanel.add(tabbedPane);

		JPanel pnlChart = new JPanel();
		pnlChart.setBackground(COLOR_LIGHTGRAY);
		tabbedPane.addTab("Daily Expenditures - Bar", null, pnlChart, null);
		pnlChart.setBorder(new LineBorder(new Color(0, 0, 0)));

		addBarChart(pnlChart);

		JPanel pnlChart2 = new JPanel();
		pnlChart2.setBackground(COLOR_LIGHTGRAY);
		tabbedPane.addTab("Daily Expenditures - Line", null, pnlChart2, null);
		pnlChart2.setBorder(new LineBorder(new Color(0, 0, 0)));
		addLineChart(pnlChart2);

		JPanel pnlChart3 = new JPanel();
		pnlChart3.setBackground(COLOR_LIGHTGRAY);
		tabbedPane.addTab("Account Balance Over Time", null, pnlChart3, null);
		pnlChart3.setBorder(new LineBorder(new Color(0, 0, 0)));
		addLineChart2(pnlChart3);
	}

	private List<String> getPastWeekCaptions() {

		List<String> dates = new ArrayList<String>();

		for (int i = -6; i <= 0; i++) {
			Calendar day = Calendar.getInstance();
			day.setTime(new Date());
			day.set(Calendar.DAY_OF_YEAR, day.get(Calendar.DAY_OF_YEAR) + i);
			String datestring = String.format("%d/%d", day.get(Calendar.MONTH) + 1, day.get(Calendar.DAY_OF_MONTH));
			dates.add(datestring);
		}
		return dates;
	}

	private List<Double> getPastWeekExpenses(UserAccount user) {
		List<Double> exps = new ArrayList<Double>();

		for (int i = -6; i <= 0; i++) {
			Calendar day = Calendar.getInstance();
			day.setTime(new Date());
			day.set(Calendar.DAY_OF_YEAR, day.get(Calendar.DAY_OF_YEAR) + i);
			Date d = day.getTime();
			exps.add(user.getTotalExpenses(d));
		}
		return exps;
	}

	private List<Double> getPastWeekBalances(UserAccount user) {
		List<Double> bals = new ArrayList<Double>();

		Integer daysAgo = 6;
		Date refDate = getDateXDaysAgo(new Date(), daysAgo);
		Double balance = 0.0;

		Iterator<AccountTransaction> trans = user.getHistory().iterator();
		if (!trans.hasNext())
			return bals;
		AccountTransaction t = trans.next();
		while (true) {
			Date d = t.getDate();
			if (d.before(refDate) || onSameDay(d, refDate)) {
				balance = t.getBalance();
				if (trans.hasNext()) {
					t = trans.next();
				} else {
					break;
				}
			} else if (d.after(refDate)) {
				bals.add(balance);
				daysAgo = daysAgo - 1;
				refDate = getDateXDaysAgo(new Date(), daysAgo);
			}
		}
		for (int i = daysAgo; i >= 0; i--) {
			bals.add(balance);
		}
		return bals;
	}

	private void addBarChart(JPanel parent) {
		parent.setLayout(new BorderLayout());

		chart = new BarChart();
		parent.add(chart, BorderLayout.CENTER);
		redrawChart(chart);
		
	}
	
	private void redrawChart(BarChart chart){
		chart.clear();

		List<String> captions = getPastWeekCaptions();
		List<Double> values = this.getPastWeekExpenses(user);
		for (int i = 0; i < captions.size(); i++) {
			chart.addDatum(captions.get(i), values.get(i));
		}

		Double max = 0.0;
		for (int i = 0; i < values.size(); i++) {
			max = Math.max(max, values.get(i));
		}
		Double step = 5.0;
		String cap;
		while (step < max) {
			cap = String.format("$%.2f", step);
			chart.addZoneLine(cap, step);
			step = step + 5.0;
		}
		cap = String.format("$%.2f", step);
		chart.addZoneLine(cap, step);

		chart.setCeiling(max + 5.00);
		chart.setColorInRange(COLOR_INRANGE);
		chart.setColorOutOfRange(COLOR_OUTRANGE);
		chart.setChartTitle("Daily Expenditure");
		chart.setGoalEnabled(false);
	}

	private void redrawChart1(LineChart chart){
		chart.clear();

		List<String> captions = getPastWeekCaptions();
		List<Double> values = this.getPastWeekExpenses(user);
		for (int i = 0; i < captions.size(); i++) {
			chart.addDatum(captions.get(i), values.get(i));
		}

		Double max = 0.0;
		for (int i = 0; i < values.size(); i++) {
			max = Math.max(max, values.get(i));
		}
		Double step = 5.0;
		String cap;
		while (step < max) {
			cap = String.format("$%.2f", step);
			chart.addZoneLine(cap, step);
			step = step + 5.0;
		}
		cap = String.format("$%.2f", step);
		chart.addZoneLine(cap, step);

		chart.setCeiling(max + 5.00);
		chart.setColorInRange(COLOR_INRANGE);
		chart.setColorOutOfRange(COLOR_OUTRANGE);
		chart.setChartTitle("Daily Expenditure");
		chart.setGoalEnabled(false);
	}
	private void addLineChart(JPanel parent) {
		parent.setLayout(new BorderLayout());

		LineChart chart = new LineChart();
		parent.add(chart, BorderLayout.CENTER);

		redrawChart1(chart);

		chart1 = chart;

	}

	private Date getDateXDaysAgo(Date d, Integer daysAgo) {
		Calendar day = Calendar.getInstance();
		day.setTime(d);
		day.set(Calendar.DAY_OF_YEAR, day.get(Calendar.DAY_OF_YEAR) - daysAgo);
		return day.getTime();
	}

	private void redrawChart2(LineChart chart) {
		chart.clear();

		List<String> captions = getPastWeekCaptions();
		List<Double> values = this.getPastWeekBalances(user);
		for (int i = 0; i < captions.size(); i++) {
			chart.addDatum(captions.get(i), values.get(i));
		}

		Double max = 0.0;
		for (int i = 0; i < values.size(); i++) {
			max = Math.max(max, values.get(i));
		}
		Double step = 5.0;
		String cap;
		while (step < max) {
			cap = String.format("$%.2f", step);
			chart.addZoneLine(cap, step);
			step = step + 5.0;
		}
		cap = String.format("$%.2f", step);
		chart.addZoneLine(cap, step);

		chart.setCeiling(max + 5.00);
		chart.setColorInRange(COLOR_INRANGE);
		chart.setColorOutOfRange(COLOR_OUTRANGE);
		chart.setChartTitle("Account Balance Over Time");
		chart.setGoalEnabled(false);
	}

	private void addLineChart2(JPanel parent) {
		parent.setLayout(new BorderLayout());

		LineChart chart = new LineChart();
		parent.add(chart, BorderLayout.CENTER);

		redrawChart2(chart);
		
		chart2 = chart;
	}

	private Boolean onSameDay(Date a, Date b) {
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(a);
		cal2.setTime(b);
		boolean sameDay = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
				&& cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
		return sameDay;
	}

	public void redraw(UserAccount user) {
		Double balance = user.getRemainingBalance();

		String strBalance = String.format("$%.2f", balance);
		lblCurrentBalance.setText(strBalance);

		Double spent = user.getTotalExpenses(new Date());

		String strSpent = String.format("$%.2f", spent);
		lblDailySpent.setText(strSpent);

		redrawChart(chart);
		redrawChart1(chart1);
		redrawChart2(chart2);
		
		
		repaint();
		revalidate();
	}

	public void setBalance(Double balance) {
	}

	@Override
	public void update(Observable o, Object arg) {
		redraw(user);
	}

}
