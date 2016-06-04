package ymss.csc.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.imageio.ImageIO;
import javax.swing.AbstractListModel;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.border.EmptyBorder;
import javax.swing.JLayeredPane;
import javax.swing.ImageIcon;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.CardLayout;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 6983316186716069456L;

	static final String title = "TechTonic University Dining Services";

	private JButton btnCafe;
	private JButton btnVendingMachine;
	private JButton btnFinance;
	private JButton btnHealth;
	private JPanel panel;
	private JPanel pnlCafeSelection;
	private JPanel pnlMap;
	private JPanel pnlCafeList;
	private JList list;
	private JLabel lblNewLabel;
	private JPanel panel_1;
	private JPanel panel_2;
	private JLabel lblVendingMachines;
	private JList list_1;
	private JPanel panel_3;
	private JPanel panel_4;
	private JLayeredPane layeredPane;

	public ImageIcon scaleImage(String filename, Integer width, Integer height) {
		ImageIcon icon = new ImageIcon(filename);

		if (width > 0 && height > 0) {
			return new ImageIcon(icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
		} else {
			return new ImageIcon(filename);
		}
	}
	
	private static final String MAP_IMAGE = "pictures/cafePic2.png";

	private void addQuickFlowButtons(JPanel panel){
		JPanel tempPanel = new JPanel();
		tempPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.add(tempPanel, BorderLayout.NORTH);
		
		btnCafe = new JButton("Cafe Frame");
		btnVendingMachine = new JButton("Vending Machine Frame");
		btnFinance = new JButton("Finance Frame");
		btnHealth = new JButton("Health Frame");
		tempPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		tempPanel.add(btnCafe);
		tempPanel.add(btnVendingMachine);
		tempPanel.add(btnFinance);
		tempPanel.add(btnHealth);
	}
	
	private JPanel pnlIconLabel;
	private JLabel lblCafeVMIcons;
	
	private void addLabelPopupToIconButton(JButton icon, String popupText){
		icon.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				lblCafeVMIcons.setText(popupText);
				
				int x0 = icon.getX();
				int y0 = icon.getY();
				int wb = icon.getWidth();
				int wl = pnlIconLabel.getPreferredSize().width;
				int hl = pnlIconLabel.getPreferredSize().height;
				int m = 5;
				
				
				pnlIconLabel.setBounds(
						x0 + (wb-wl)/2,
						y0 - hl - m,
						pnlIconLabel.getPreferredSize().width,
						pnlIconLabel.getPreferredSize().height
						);
				pnlIconLabel.setVisible(true);
				pnlIconLabel.repaint();
				pnlIconLabel.revalidate();
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				pnlIconLabel.setVisible(false);
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {}

			@Override
			public void mouseReleased(MouseEvent arg0) {}
			
		});
	}
	
	private void addMapIcons(JPanel parent){
		pnlIconLabel = new JPanel();
		
		lblCafeVMIcons = new JLabel();		
		pnlIconLabel.add(lblCafeVMIcons);
		pnlIconLabel.setBackground(Color.WHITE);
		pnlIconLabel.setVisible(false);
		
		JButton btnTest = new JButton();
		ImageIcon img = new ImageIcon("pictures/cafeIcon.png");
		btnTest.setIcon(img);
		btnTest.setBounds(20,50,img.getIconWidth(),img.getIconHeight());
		btnTest.setOpaque(false);
		btnTest.setContentAreaFilled(false);
		btnTest.setBorderPainted(false);
		btnTest.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JOptionPane.showMessageDialog(null, "You clicked a cafe icon!");
			}
		});
		this.addLabelPopupToIconButton(btnTest, "Cafe");
		
		JButton btnTest2 = new JButton();
		ImageIcon img2 = new ImageIcon("pictures/vmIcon.png");
		btnTest2.setIcon(img2);
		btnTest2.setBounds(140,140,img2.getIconWidth(),img2.getIconHeight());
		btnTest2.setOpaque(false);
		btnTest2.setContentAreaFilled(false);
		btnTest2.setBorderPainted(false);
		btnTest2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JOptionPane.showMessageDialog(null, "You clicked a vending machine icon!");
			}
		});
		this.addLabelPopupToIconButton(btnTest2,"Vending Machine");
		
		
		parent.add(pnlIconLabel);
		parent.add(btnTest);
		parent.add(btnTest2);
	}
	
	private void addMapPanel(){
		pnlMap = new JPanel();
		pnlCafeSelection.add(pnlMap, BorderLayout.CENTER);
		pnlMap.setLayout(new GridLayout(0, 1, 0, 0));

		layeredPane = new JLayeredPane();
		pnlMap.add(layeredPane);
		layeredPane.setLayout(null);

		JPanel panel_6 = new JPanel();
		panel_6.setBounds(layeredPane.getBounds());
		panel_6.setBorder(null);
		layeredPane.setLayer(panel_6, 1);
		layeredPane.add(panel_6);
		panel_6.setLayout(null);

		addMapIcons(panel_6);
		panel_6.setOpaque(false);

		JPanel panel_5 = new JPanel();
		panel_5.setBounds(layeredPane.getBounds());

		layeredPane.setLayer(panel_5, 0);
		layeredPane.add(panel_5);
		panel_5.setLayout(new BorderLayout(0, 0));

		JLabel lblNewLabel_1 = new JLabel("New label");
		panel_5.add(lblNewLabel_1);

		layeredPane.addComponentListener(new ComponentListener() {

			@Override
			public void componentHidden(ComponentEvent arg0) {
				panel_5.setBounds(layeredPane.getBounds());
				panel_6.setBounds(layeredPane.getBounds());
				lblNewLabel_1.setIcon(scaleImage(MAP_IMAGE, layeredPane.getWidth(),
						layeredPane.getHeight()));

			}

			@Override
			public void componentMoved(ComponentEvent arg0) {
				panel_5.setBounds(layeredPane.getBounds());
				panel_6.setBounds(layeredPane.getBounds());
				lblNewLabel_1.setIcon(scaleImage(MAP_IMAGE, layeredPane.getWidth(),
						layeredPane.getHeight()));

			}

			@Override
			public void componentResized(ComponentEvent arg0) {
				panel_5.setBounds(layeredPane.getBounds());
				panel_6.setBounds(layeredPane.getBounds());
				lblNewLabel_1.setIcon(scaleImage(MAP_IMAGE, layeredPane.getWidth(),
						layeredPane.getHeight()));

			}

			@Override
			public void componentShown(ComponentEvent arg0) {
				panel_5.setBounds(layeredPane.getBounds());
				panel_6.setBounds(layeredPane.getBounds());
				lblNewLabel_1.setIcon(scaleImage(MAP_IMAGE, layeredPane.getWidth(),
						layeredPane.getHeight()));

			}

		});
	}
	
	private void addCafeListPanel(){
		pnlCafeList = new JPanel();
		pnlCafeList.setBorder(new EmptyBorder(10, 10, 10, 10));
		pnlCafeSelection.add(pnlCafeList, BorderLayout.EAST);
		pnlCafeList.setLayout(new BoxLayout(pnlCafeList, BoxLayout.Y_AXIS));

		panel_1 = new JPanel();
		pnlCafeList.add(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));

		lblNewLabel = new JLabel("Cafes");
		panel_1.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));

		panel_4 = new JPanel();
		pnlCafeList.add(panel_4);
		panel_4.setLayout(new GridLayout(0, 1, 0, 0));

		list = new JList();
		panel_4.add(list);
		list.setModel(new AbstractListModel() {
			String[] values = new String[] { "Cafe 1", "Cafe 2", "Cafe 3" };

			public int getSize() {
				return values.length;
			}

			public Object getElementAt(int index) {
				return values[index];
			}
		});

		panel_2 = new JPanel();
		pnlCafeList.add(panel_2);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.X_AXIS));

		lblVendingMachines = new JLabel("Vending Machines");
		lblVendingMachines.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_2.add(lblVendingMachines);

		panel_3 = new JPanel();
		pnlCafeList.add(panel_3);
		panel_3.setLayout(new GridLayout(0, 1, 0, 0));

		list_1 = new JList();
		panel_3.add(list_1);
	}
	
	public MainFrame() {
		// Window initialization
		setTitle(title);
		setSize(800, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));

		
		addQuickFlowButtons(panel);
		

		pnlCafeSelection = new JPanel();
		panel.add(pnlCafeSelection);
		pnlCafeSelection.setLayout(new BorderLayout(0, 0));

		addMapPanel();

		addCafeListPanel();
		
	}

	public void addOpenCafeListener(ActionListener l) {
		btnCafe.addActionListener(l);
	}

	public void removeOpenCafeListener(ActionListener l) {
		btnCafe.removeActionListener(l);
	}

	public void addOpenVendingMachineListener(ActionListener l) {
		btnVendingMachine.addActionListener(l);
	}

	public void removeOpenVendingMachineListener(ActionListener l) {
		btnVendingMachine.removeActionListener(l);
	}

	public void addOpenFinanceListener(ActionListener l) {
		btnFinance.addActionListener(l);
	}

	public void removeOpenFinanceListener(ActionListener l) {
		btnFinance.removeActionListener(l);
	}

	public void addOpenHealthListener(ActionListener l) {
		btnHealth.addActionListener(l);
	}

	public void removeOpenHealthListener(ActionListener l) {
		btnHealth.removeActionListener(l);
	}
}
