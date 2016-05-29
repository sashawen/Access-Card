package ymss.csc.views;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class HealthFrame extends JFrame {

	private static final long serialVersionUID = 724764215279243988L;

	static final String title = "My Dietary Profile";

	private JButton btnEdit;

	public HealthFrame() {
		// Window initialization
		setTitle(title);
		setSize(800, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		JPanel tempPanel = new JPanel();
		this.getContentPane().add(tempPanel);

		btnEdit = new JButton("Edit Dietary Preferences");
		tempPanel.add(btnEdit);
	}

	public void addEditListener(ActionListener l) {
		btnEdit.addActionListener(l);
	}

	public void removeEditListener(ActionListener l) {
		btnEdit.removeActionListener(l);
	}

}
