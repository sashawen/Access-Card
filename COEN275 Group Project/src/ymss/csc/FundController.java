package ymss.csc;

//Yiwen

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class FundController extends JPanel {

   // Account to control
   private Fund fund;

   // JTextField for deposit amount
   private JTextField amountTextField;

   // AccountController constructor
   public FundController( Fund controlledFund )
   {
      super();

      // account to control
      fund = controlledFund;

      // create JTextField for entering amount
      amountTextField = new JTextField( 10 );

      // create JButton for deposits
      JButton depositButton = new JButton( "Deposit" );

      depositButton.addActionListener(
         new ActionListener() {

            public void actionPerformed( ActionEvent event )
            {
               try {

                  // deposit amount entered in amountTextField
                  fund.deposit( Double.parseDouble(
                     amountTextField.getText() ) );
               }

               catch ( NumberFormatException exception ) {
                  JOptionPane.showMessageDialog (
                     FundController.this,
                     "Please enter a valid amount", "Error",
                     JOptionPane.ERROR_MESSAGE );
               }
            } // end method actionPerformed
         }
      );
      JButton viewButton = new JButton( "View Expense" );

      //viewButton.addActionListener(
      ///   new ActionListener() {
      //  	new DepositFrame().updateDisplay();
      //     
      //       // end method actionPerformed
      //   }
      //);    
	      	      	      	           
      // lay out controller components
      setLayout( new FlowLayout() );
     // add( accountBalance );
      add( amountTextField );
      
      
      add( depositButton );
      add( viewButton );
   }
}

