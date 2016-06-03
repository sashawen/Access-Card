package ymss.csc.views;

public class LoginFrame extends CredentialFrame {

	private static final long serialVersionUID = 2654206624481274499L;

	public void initialize(){
		setTitle("Login");
		this.lbCredential.setText("Card No:");
		this.btnAuthenticate.setText("Login");
	}
	

}
