package ymss.csc.views.order;

import java.awt.Color;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ymss.csc.application.Constants;
import ymss.csc.models.FoodItem;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.border.LineBorder;

public class ItemPanel extends JPanel {
	private static final long serialVersionUID = 6978620418637694671L;

	private FoodItem item;

	private JButton btnSelect;

	// for debug
	public ItemPanel() {
		FoodItem item = new FoodItem(20);
		item.setName("Baby Back Ribs");
		item.setDescription("Succulent as hell.");
		item.setCalories(900);
		item.setPrice(8.50);
		item.setGlutenFree(false);
		item.setLowCholesterol(false);
		item.setLowSodium(false);
		item.setVegan(false);

		init(item);
	}

	public ItemPanel(FoodItem item) {
		init(item);
	}

	private void init(FoodItem item) {
		this.item = item;
		this.setBorder(new EmptyBorder(5, 0, 5, 0));
		String roundedPrice = String.format("%.2f", item.getPrice());
		setLayout(new BorderLayout(0, 0));

		JPanel pnlContainer = new JPanel();
		pnlContainer.setBackground(Color.WHITE);
		pnlContainer.setBorder(new LineBorder(new Color(0, 0, 0)));
		add(pnlContainer);
		pnlContainer.setLayout(new GridLayout(0, 2, 0, 0));

		JPanel pnlItemInfo = new JPanel();
		pnlItemInfo.setBorder(new EmptyBorder(5, 5, 5, 0));
		pnlItemInfo.setOpaque(false);
		pnlContainer.add(pnlItemInfo);
		pnlItemInfo.setLayout(new BoxLayout(pnlItemInfo, BoxLayout.Y_AXIS));

		JLabel lblName = new JLabel(item.getName());
		lblName.setFont(Constants.FONT_NORMAL_BOLD);
		pnlItemInfo.add(lblName);
		JLabel lblDescription = new JLabel(item.getDescription());
		lblDescription.setFont(Constants.FONT_NORMAL_ITALIC);
		pnlItemInfo.add(lblDescription);
		JLabel lblCalories = new JLabel(item.getCalories() + " calories");
		lblCalories.setFont(Constants.FONT_NORMAL_ITALIC);
		lblCalories.setForeground(new Color(80, 80, 80));
		pnlItemInfo.add(lblCalories);

		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(5, 0, 5, 5));
		panel.setOpaque(false);
		pnlContainer.add(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWeights = new double[] { 1.0, 0.0, 0.0 };
		gbl_panel.rowWeights = new double[] { 0.0 };
		panel.setLayout(gbl_panel);
		JLabel lblPrice = new JLabel('$' + roundedPrice);
		GridBagConstraints gbc_lblPrice = new GridBagConstraints();
		gbc_lblPrice.insets = new Insets(0, 0, 5, 5);
		gbc_lblPrice.gridx = 0;
		gbc_lblPrice.gridy = 0;
		panel.add(lblPrice, gbc_lblPrice);

		JPanel pnlDummy = new JPanel();
		pnlDummy.setOpaque(false);
		GridBagConstraints gbc_pnlDummy = new GridBagConstraints();
		gbc_pnlDummy.weightx = 1.0;
		gbc_pnlDummy.insets = new Insets(0, 0, 5, 5);
		gbc_pnlDummy.fill = GridBagConstraints.BOTH;
		gbc_pnlDummy.gridx = 1;
		gbc_pnlDummy.gridy = 0;
		panel.add(pnlDummy, gbc_pnlDummy);
		btnSelect = new JButton("Select");
		GridBagConstraints gbc_btnSelect = new GridBagConstraints();
		gbc_btnSelect.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSelect.insets = new Insets(0, 0, 5, 10);
		gbc_btnSelect.anchor = GridBagConstraints.EAST;
		gbc_btnSelect.gridx = 2;
		gbc_btnSelect.gridy = 0;
		panel.add(btnSelect, gbc_btnSelect);
		btnSelect.setHorizontalAlignment(SwingConstants.RIGHT);
	}

	public void addSelectListener(ActionListener l) {
		btnSelect.addActionListener(l);
	}

	public FoodItem getItem() {
		return item;
	}
}
