package ymss.csc;

//Yiwen

import java.util.Observable;

public class Fund extends Observable {
   private double balance = 0.00;
  
   // Account constructor
   public Fund (double balance ){
      balance = this.balance;
   }
   // set Account balance and notify observers of change
   private void setBalance( double accountBalance ){
      balance = accountBalance;
      // must call setChanged before notifyObservers to
      // indicate model has changed

      setChanged();
      // notify Observers that model has changed
      notifyObservers();
   }

   // get Account balance
   public double getBalance(){return balance;}

   // calculate current balance
   public void calBalance( double price )
      throws IllegalArgumentException
   {
      if ( (balance - price) < 0 )
         throw new IllegalArgumentException(
            "Your current balance is not enough" );
      // update Account balance
      setBalance( getBalance() - price );
   }

   // deposit funds in account
   public void deposit( double amount )
      throws IllegalArgumentException
   {
      if ( amount < 0 )
         throw new IllegalArgumentException(
            "Cannot deposit negative amount" );
      setBalance( getBalance() + amount );
   }
   
}

