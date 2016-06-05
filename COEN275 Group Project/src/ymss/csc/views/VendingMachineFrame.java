package ymss.csc.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

import ymss.csc.models.*;
import ymss.csc.views.order.ItemPanel;

public class VendingMachineFrame extends JFrame {

	private static final long serialVersionUID = 8121732800555614056L;

	static final String title = "Vending Machine";
	
	
	public VendingMachineFrame(UserAccount user, VendingMachine vm) {
		
		// Window initialization
		setTitle(title);
		setSize(800, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		this.setContentPane(new VendingMachineOrderPanel(vm,user));

	}
	
	
}
