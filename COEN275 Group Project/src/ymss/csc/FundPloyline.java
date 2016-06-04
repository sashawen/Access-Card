package ymss.csc;

//Yiwen
// AccountBarGraphView.java
// AccountBarGraphView is an AbstractAccountView subclass
// that displays an Account balance as a bar graph.

import java.awt.*;
import java.awt.geom.Line2D;

import javax.swing.*;

public class FundPloyline extends JFrame {
	private Fund fund; 
	
   // FundPloyline constructor
   public FundPloyline( Fund fund )
   {
      fund= this.fund;
   }

   // draw Account balance as a bar graph
   public void paintComponent( Graphics g )
   {
      // get Account balance
	   Fund b = new Fund(0.00);
      double balance = b.getBalance();
      g.setStroke(new BasicStroke(5.0f));
      
      double startX = 50;
      double startY = 50;
      
      
      Line2D line = new Line2D.Double(startX,startY,endX,endY); 
      g.draw(line);

      // if balance is positive, draw graph in black
      if ( balance >= 0.00 ) {
         g.setColor( Color.blue );
         
      }
   }


     
   public void updateDisplay()
   {
      repaint();
   }

  
}

