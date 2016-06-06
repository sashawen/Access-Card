package ymss.csc.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ymss.csc.application.Constants;
import ymss.csc.models.AbstractVendor;
import ymss.csc.models.Cafe;
import ymss.csc.models.VendingMachine;

public class VendorMapPanel extends AbstractVendorSelectionPanel {

	private static final long serialVersionUID = -428048895200218400L;

	private static final String MAP_IMAGE = "pictures/cafePic2.png";
	private static final String ICON_CAFE = "pictures/cafeIcon.png";
	private static final String ICON_VENDINGMACHINE = "pictures/vmIcon.png";
	private static final Integer ICON_PROPERTY_WIDTH = 30;
	private static final Integer ICON_PROPERTY_HEIGHT = 40;

	private JPanel pnlIconLabel;
	private JLabel lblCafeVMIcons;
	
	private JLayeredPane lpnMap;
	private JPanel pnlMapImage;
	private JPanel pnlMapIcons;
	private JLabel lblMapImage;

	public VendorMapPanel(List<AbstractVendor> vendors) {
		super(vendors);
	
		initialize();
	}

	private void initialize() {
		this.setLayout(new GridLayout(0, 1, 0, 0));

		lpnMap = new JLayeredPane();
		this.add(lpnMap);
		lpnMap.setLayout(null);

		pnlMapIcons = new JPanel();
		lpnMap.setLayer(pnlMapIcons, 1);
		lpnMap.add(pnlMapIcons);
		pnlMapIcons.setLayout(null);
		pnlMapIcons.setOpaque(false);

		pnlMapImage = new JPanel();
		lpnMap.setLayer(pnlMapImage, 0);
		lpnMap.add(pnlMapImage);
		pnlMapImage.setLayout(new BorderLayout(0, 0));

		lblMapImage = new JLabel("New label");
		pnlMapImage.add(lblMapImage);

		lpnMap.addComponentListener(new ComponentListener() {

			@Override
			public void componentHidden(ComponentEvent arg0) {
				redraw();
			}

			@Override
			public void componentMoved(ComponentEvent arg0) {
				redraw();
			}

			@Override
			public void componentResized(ComponentEvent arg0) {
				redraw();
			}

			@Override
			public void componentShown(ComponentEvent arg0) {
				redraw();
			}

		});
		// temporary
		/*
		lpnMap.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {
				Integer x = arg0.getX();
				Integer y = arg0.getY();
				Integer dx = ((JLayeredPane) arg0.getSource()).getWidth();
				Integer dy = ((JLayeredPane) arg0.getSource()).getHeight();
				
				Double adj_x = x*100.0 / dx.doubleValue();
				Double adj_y = y*100.0 / dy.doubleValue();
				
				String c = String.format("(%.3f,%.3f)", adj_x,adj_y);
				System.out.println(c);
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});*/
		redraw();
	}
	
	private void redraw(){
		pnlMapImage.setBounds(lpnMap.getBounds());
		pnlMapIcons.setBounds(lpnMap.getBounds());
		pnlMapIcons.removeAll();
		addMapIcons(pnlMapIcons);
		
		lblMapImage.setIcon(scaleImage(MAP_IMAGE, lpnMap.getWidth(), lpnMap.getHeight()));
	}
	
	
	private void addMapIcon(JPanel parent, AbstractVendor vendor){
		JButton btnTest = new JButton();
		
		String imageUrl;
		String hoverText;
		if(vendor instanceof Cafe){
			imageUrl = this.ICON_CAFE;
			hoverText = vendor.getName();
		}else if(vendor instanceof VendingMachine){
			imageUrl = this.ICON_VENDINGMACHINE;
			hoverText = "Vending Machine";
		}else{
			System.out.println("Unrecognized Vendor");
			return;
		}
		
		Double lat = vendor.getLatitude();
		Double lon = vendor.getLongitude();
		
		Integer x = (int) Math.round(lat*lpnMap.getWidth() / 100.0);
		Integer y = (int) Math.round(lon*lpnMap.getHeight() / 100.0);
		
		Integer adj_x = x - ICON_PROPERTY_WIDTH / 2;
		Integer adj_y = y - ICON_PROPERTY_HEIGHT;
		
		ImageIcon img = this.scaleImage(imageUrl, ICON_PROPERTY_WIDTH, ICON_PROPERTY_HEIGHT);
		btnTest.setIcon(img);
		btnTest.setBounds(adj_x,adj_y, img.getIconWidth(), img.getIconHeight());
		btnTest.setOpaque(false);
		btnTest.setContentAreaFilled(false);
		btnTest.setBorderPainted(false);
		btnTest.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				if (evt.getClickCount() == 2) {
					vendorSelected(vendor);
				}
			}
		});
		this.addLabelPopupToIconButton(btnTest, hoverText);
		
		parent.add(btnTest);
	}

	private void addMapIcons(JPanel parent) {
		pnlIconLabel = new JPanel();

		lblCafeVMIcons = new JLabel();
		pnlIconLabel.add(lblCafeVMIcons);
		pnlIconLabel.setBackground(Color.WHITE);
		pnlIconLabel.setVisible(false);
		
		Iterator<AbstractVendor> it = this.vendors.iterator();
		while(it.hasNext()){
			addMapIcon(parent,it.next());
		}
		

		parent.add(pnlIconLabel);
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
			public void mousePressed(MouseEvent arg0) {}

			@Override
			public void mouseReleased(MouseEvent arg0) {}

		});
	}

}
