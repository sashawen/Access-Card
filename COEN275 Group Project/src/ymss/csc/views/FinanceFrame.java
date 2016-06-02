package ymss.csc.views;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import java.awt.Component;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.border.EmptyBorder;

public class FinanceFrame extends JFrame {

	private static final long serialVersionUID = -126850826291601022L;

	static final String title = "My Financial Profile";

	private JButton btnDeposit;
	private JLabel lblCurrentBalance;

	public FinanceFrame() {
		// Window initialization
		setTitle(title);
		setSize(800, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

		JPanel tempPanel = new JPanel();
		this.getContentPane().add(tempPanel);
		tempPanel.setLayout(new BoxLayout(tempPanel, BoxLayout.Y_AXIS));

		initSummaryPanel(tempPanel);
		initChartPanel(tempPanel);

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
		pnlSummary.add(btnDeposit);
	}

	private void initChartPanel(JPanel parent) {
		if (parent == null)
			return;

		JPanel pnlChart = new JPanel();
		pnlChart.setBorder(new LineBorder(new Color(0, 0, 0)));

		JLabel lblChartTitle = new JLabel("Chart Goes Here");
		pnlChart.add(lblChartTitle);

		parent.add(pnlChart);
	}

	public void setBalance(Double balance) {
		String strBalance = String.format("$%.2f", balance);
		lblCurrentBalance.setText(strBalance);

		lblCurrentBalance.repaint();
		lblCurrentBalance.revalidate();
	}

	public void addDepositListener(ActionListener l) {
		btnDeposit.addActionListener(l);
	}

}
