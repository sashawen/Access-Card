package ymss.csc.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ymss.csc.models.*;
import ymss.csc.views.order.ItemPanel;
import ymss.csc.views.order.OrderPanel;

public class CafeFrame extends JFrame{

	private static final long serialVersionUID = -2638211781748534596L;

	static final String title = "Cafe";

	private JPanel menuPanel;
	private JLabel lblBalance;
	private OrderPanel pnlOrder;

	private UserAccount user;
	private Cafe cafe;
	private Order order;
	
	public CafeFrame(UserAccount user, Cafe cafe) {
		this.user = user;
		this.cafe = cafe;
		this.order = new Order();
		
		// Window initialization
		setTitle(cafe.getName());
		setSize(800, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		this.setContentPane(new CafeOrderPanel(cafe,user));
		
	}

}
