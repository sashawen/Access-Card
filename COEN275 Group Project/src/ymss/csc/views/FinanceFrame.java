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

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;

public class FinanceFrame extends JFrame implements Observer {

	private static final long serialVersionUID = -126850826291601022L;

	static final String title = "My Financial Profile";

	private JButton btnDeposit;
	private JLabel lblCurrentBalance;

	private FundDepositFrame fundDepositFrame;

	private UserAccount user;
	private JPanel tempPanel;

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
		btnDeposit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(fundDepositFrame == null) fundDepositFrame = new FundDepositFrame(user);
				
				fundDepositFrame.setVisible(true);
			}
		});
		pnlSummary.add(btnDeposit);
	}
	
	private JPanel pnlHistory;

	private void initChartPanel(JPanel parent) {
		if (parent == null)
			return;
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tempPanel.add(tabbedPane);

		JPanel pnlChart = new JPanel();
		tabbedPane.addTab("Chart", null, pnlChart, null);
		pnlChart.setBorder(new LineBorder(new Color(0, 0, 0)));

		addChart(pnlChart);
		
		pnlHistory = new JPanel();
		tabbedPane.addTab("History", null, pnlHistory, null);
	}
	
	private JPanel pnlChart;
	private BarChart chart;

	private static final Color COLOR_LIGHTGRAY = new Color(224, 224, 224);
	private static final Color COLOR_INRANGE = new Color(23, 106, 130);
	private static final Color COLOR_OUTRANGE = new Color(255, 68, 35);

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
	private Double getExpenseForDay(UserAccount user,Date date){
		// Assumes linear order history.
		List<AccountTransaction> trans = user.getHistory();
		Iterator<AccountTransaction> it = trans.iterator();
		Double totalExpense = 0.0;
		Double oldBalance = 0.0;
		while(it.hasNext()){
			AccountTransaction t = it.next();
			if(t instanceof Order){
				Order order = (Order) t;
				if(onSameDay(order.getDate(),date)){
					totalExpense = totalExpense + oldBalance - order.getBalance();
				}
			}
			oldBalance = t.getBalance();
		}
		return totalExpense;
	}
	
	private List<Double> getPastWeekExpenses(UserAccount user){
		List<Double> exps = new ArrayList<Double>();

		for (int i = -6; i <= 0; i++) {
			Calendar day = Calendar.getInstance();
			day.setTime(new Date());
			day.set(Calendar.DAY_OF_YEAR, day.get(Calendar.DAY_OF_YEAR) + i);
			Date d = day.getTime();
			exps.add(getExpenseForDay(user, d));
		}
		return exps;
	}
	
	private void addChart(JPanel parent){
		pnlChart = new JPanel();
		parent.setLayout(new BorderLayout());
		parent.add(pnlChart,BorderLayout.CENTER);

		pnlChart.setLayout(new BorderLayout());

		chart = new BarChart();

		chart.clear();
		
		List<String> captions = getPastWeekCaptions();
		List<Double> values = this.getPastWeekExpenses(user);
		for(int i = 0; i < captions.size(); i++){
			chart.addDatum(captions.get(i), values.get(i));
		}
		
		Double max = 0.0;
		for(int i = 0; i < values.size(); i++){
			max = Math.max(max, values.get(i));
		}
		Double step = 5.0; String cap;
		while(step < max){
			cap = String.format("$%.2f", step);
			chart.addZoneLine(cap, step);
			step = step + 5.0;
		}
		cap = String.format("$%.2f", step);
		chart.addZoneLine(cap, step);
		
		chart.setCeiling(max+5.00);
		chart.setColorInRange(COLOR_INRANGE);
		chart.setColorOutOfRange(COLOR_OUTRANGE);
		chart.setChartTitle("Daily Expenditure");
		chart.setGoalEnabled(false);


		pnlChart.add(chart, BorderLayout.CENTER);
	}
	
	private Boolean onSameDay(Date a, Date b){
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(a);
		cal2.setTime(b);
		boolean sameDay = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
		                  cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
		return sameDay;
	}
	
	private void drawHistory(){
		pnlHistory.removeAll();
		
		Iterator<AccountTransaction> it = user.getHistory().iterator();
		while(it.hasNext()){
			AccountTransaction trans = it.next();
			
			if(trans == null){
				System.out.println("Something failed...");
				continue;
			}
			
			JLabel lblTrans = new JLabel();
			String date = trans.getDateString();
			String memo = trans.getMemo();
			Double change = trans.getAccountChange();
			Double balance = trans.getBalance();
			String isToday = (onSameDay(new Date(),trans.getDate())) ? "(Today)" : "";
			String strTrans = String.format("%s%s -- %s -- $%.2f -- $%.2f",date,isToday,memo,change,balance);
			lblTrans.setText(strTrans);
			pnlHistory.add(lblTrans);
		}
	}

	public void redraw(UserAccount user) {
		setBalance(user.getRemainingBalance());
		drawHistory();
	}

	public void setBalance(Double balance) {
		String strBalance = String.format("$%.2f", balance);
		lblCurrentBalance.setText(strBalance);

		lblCurrentBalance.repaint();
		lblCurrentBalance.revalidate();
	}

	@Override
	public void update(Observable o, Object arg) {
		redraw(user);
	}

}
