package ymss.csc.views;

import javax.swing.JFrame;

public class EditHealthFrame extends JFrame {

	private static final long serialVersionUID = 4238472435606246405L;
	
	static final String title = "Edit Dietary Preferences";

	public EditHealthFrame() {
		// Window initialization
		setTitle(title);
		setSize(800, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

}
