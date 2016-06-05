package ymss.csc.views.charts;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

public class BarChart extends JPanel {
	
	private static final long serialVersionUID = -726692311360619933L;
	
	private Integer nEntries = 0;
	private Double ceiling = 700.0;
	
	private Double rangeMin = 2000.0;
	private Double rangeMax = 2500.0;
	
	private Color colorInRange = Color.GREEN;
	private Color colorOutOfRange = Color.RED;
	
	private List<Double> data;
	
	private JPanel parent;
	
	public BarChart() {
	}
	
	public void setCeiling(Double ceiling){
		this.ceiling = ceiling;
	}
	
	public void setRangeMin(Double rangeMin){
		this.rangeMin = rangeMin;
	}
	
	public void setColorInRange(Color colorInRange){
		this.colorInRange = colorInRange;
	}
	
	public void setColorOutOfRange(Color colorOutOfRange){
		this.colorOutOfRange = colorOutOfRange;
	}
	
	public void setRangeMax(Double rangeMax){
		this.rangeMax = rangeMax;
	}
	
	public void setData(List<Double> data){
		this.data = data;
		this.nEntries = data.size();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		
		Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        		
		// Determine chart area.
		Integer chartWidth = getWidth();//this.getPreferredSize().width;
		Integer chartHeight = getHeight();//this.getPreferredSize().height;
		
		// Determine scale.
		Integer barWidth = (chartWidth-5)/(nEntries) - 5;
		Double barScaleY = chartHeight.doubleValue() / ceiling;
		
		// zone lines
		Line2D minLine = new Line2D.Double(0.0,chartHeight-barScaleY*this.rangeMin,chartWidth,chartHeight-barScaleY*this.rangeMin);
		Line2D maxLine = new Line2D.Double(0.0,chartHeight-barScaleY*this.rangeMax,chartWidth,chartHeight-barScaleY*this.rangeMax);
		
		float dash1[] = {10.0f};
        g2.setStroke(new BasicStroke(1.0f,
        		BasicStroke.CAP_SQUARE,
        		BasicStroke.JOIN_MITER,
        		10.0f,dash1,0.0f));
		g2.draw(minLine);
		g2.draw(maxLine);
		
		g2.setStroke(new BasicStroke(1.0f));		
		// paint bars
		int width = barWidth;
		int x = 5;
		
		Iterator<Double> it = data.iterator();
		while(it.hasNext()){
			Double val = it.next();
			int height = (int) Math.round(barScaleY*val);
			
			if(val >= rangeMin && val <= rangeMax){
				g.setColor(this.colorInRange);
			}else{
				g.setColor(this.colorOutOfRange);
			}
			g.fillRect(x, getHeight()- height, width, height);
			g.setColor(Color.black);
			g.drawRect(x, getHeight()-height, width, height);
			x+= (width+5);
		}
	}

}
