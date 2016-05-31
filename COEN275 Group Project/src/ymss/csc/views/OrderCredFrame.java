package ymss.csc.views;

public class OrderCredFrame extends CredentialFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4480583397392768978L;
	
	public void initialize(){
		setTitle("Enter your credentials");
		this.lbCredential.setText("Card No.:");
		this.btnAuthenticate.setText("Purchase");
	}
}
