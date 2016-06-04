package ymss.csc.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ymss.csc.models.AbstractVendor;

public class VendorMapPanel extends AbstractVendorSelectionPanel {

	private static final long serialVersionUID = -428048895200218400L;

	private static final String MAP_IMAGE = "pictures/cafePic2.png";

	private JPanel pnlIconLabel;
	private JLabel lblCafeVMIcons;

	public VendorMapPanel(List<AbstractVendor> vendors) {
		super(vendors);
	
		initialize();
	}

	private void initialize() {
		this.setLayout(new GridLayout(0, 1, 0, 0));

		JLayeredPane layeredPane = new JLayeredPane();
		this.add(layeredPane);
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
				lblNewLabel_1.setIcon(scaleImage(MAP_IMAGE, layeredPane.getWidth(), layeredPane.getHeight()));

			}

			@Override
			public void componentMoved(ComponentEvent arg0) {
				panel_5.setBounds(layeredPane.getBounds());
				panel_6.setBounds(layeredPane.getBounds());
				lblNewLabel_1.setIcon(scaleImage(MAP_IMAGE, layeredPane.getWidth(), layeredPane.getHeight()));

			}

			@Override
			public void componentResized(ComponentEvent arg0) {
				panel_5.setBounds(layeredPane.getBounds());
				panel_6.setBounds(layeredPane.getBounds());
				lblNewLabel_1.setIcon(scaleImage(MAP_IMAGE, layeredPane.getWidth(), layeredPane.getHeight()));

			}

			@Override
			public void componentShown(ComponentEvent arg0) {
				panel_5.setBounds(layeredPane.getBounds());
				panel_6.setBounds(layeredPane.getBounds());
				lblNewLabel_1.setIcon(scaleImage(MAP_IMAGE, layeredPane.getWidth(), layeredPane.getHeight()));

			}

		});
	}

	private void addMapIcons(JPanel parent) {
		pnlIconLabel = new JPanel();

		lblCafeVMIcons = new JLabel();
		pnlIconLabel.add(lblCafeVMIcons);
		pnlIconLabel.setBackground(Color.WHITE);
		pnlIconLabel.setVisible(false);

		JButton btnTest = new JButton();
		ImageIcon img = new ImageIcon("pictures/cafeIcon.png");
		btnTest.setIcon(img);
		btnTest.setBounds(20, 50, img.getIconWidth(), img.getIconHeight());
		btnTest.setOpaque(false);
		btnTest.setContentAreaFilled(false);
		btnTest.setBorderPainted(false);
		btnTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "You clicked a cafe icon!");
			}
		});
		this.addLabelPopupToIconButton(btnTest, "Cafe");

		JButton btnTest2 = new JButton();
		ImageIcon img2 = new ImageIcon("pictures/vmIcon.png");
		btnTest2.setIcon(img2);
		btnTest2.setBounds(140, 140, img2.getIconWidth(), img2.getIconHeight());
		btnTest2.setOpaque(false);
		btnTest2.setContentAreaFilled(false);
		btnTest2.setBorderPainted(false);
		btnTest2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "You clicked a vending machine icon!");
			}
		});
		this.addLabelPopupToIconButton(btnTest2, "Vending Machine");

		parent.add(pnlIconLabel);
		parent.add(btnTest);
		parent.add(btnTest2);
	}

	public ImageIcon scaleImage(String filename, Integer width, Integer height) {
		ImageIcon icon = new ImageIcon(filename);

		if (width > 0 && height > 0) {
			return new ImageIcon(icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
		} else {
			return new ImageIcon(filename);
		}
	}

	private void addLabelPopupToIconButton(JButton icon, String popupText) {
		icon.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				lblCafeVMIcons.setText(popupText);

				int x0 = icon.getX();
				int y0 = icon.getY();
				int wb = icon.getWidth();
				int wl = pnlIconLabel.getPreferredSize().width;
				int hl = pnlIconLabel.getPreferredSize().height;
				int m = 5;

				pnlIconLabel.setBounds(x0 + (wb - wl) / 2, y0 - hl - m, pnlIconLabel.getPreferredSize().width,
						pnlIconLabel.getPreferredSize().height);
				pnlIconLabel.setVisible(true);
				pnlIconLabel.repaint();
				pnlIconLabel.revalidate();
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				pnlIconLabel.setVisible(false);

			}

			@Override
			public void mousePressed(MouseEvent arg0) {
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
			}

		});
	}

}
