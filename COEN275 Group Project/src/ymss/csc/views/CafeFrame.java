package ymss.csc.views;

import javax.swing.JFrame;

public class CafeFrame extends JFrame {
	
	private static final long serialVersionUID = -2638211781748534596L;
	
	static final String title = "Cafe";

	public CafeFrame() {
		// Window initialization
		setTitle(title);
		setSize(800, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
}
