package ymss.csc.views.charts;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import ymss.csc.application.Constants;

public class BarChart extends JPanel {

	private static final long serialVersionUID = -726692311360619933L;

	private Integer nEntries = 0;
	private Double ceiling = 700.0;

	private Double rangeMin = 2000.0;
	private Double rangeMax = 2500.0;

	private Color colorInRange = Color.GREEN;
	private Color colorOutOfRange = Color.RED;

	private List<DataEntry> data = new ArrayList<DataEntry>();
	private List<DataEntry> zoneLines = new ArrayList<DataEntry>();

	private String chartTitle = "New Chart";

	private Boolean goalEnabled = true;

	public BarChart() {
	}

	public void setChartTitle(String chartTitle) {
		this.chartTitle = chartTitle;
	}

	public void setCeiling(Double ceiling) {
		this.ceiling = ceiling;
	}

	public void setRangeMin(Double rangeMin) {
		this.rangeMin = rangeMin;
	}

	public void setColorInRange(Color colorInRange) {
		this.colorInRange = colorInRange;
	}

	public void setColorOutOfRange(Color colorOutOfRange) {
		this.colorOutOfRange = colorOutOfRange;
	}

	public void setRangeMax(Double rangeMax) {
		this.rangeMax = rangeMax;
	}

	public void setGoalEnabled(Boolean enabled) {
		this.goalEnabled = enabled;
	}

	/*
	 * public void setData(List<Double> data){ this.data = data; this.nEntries =
	 * data.size(); }
	 */

	public void clear() {
		this.data.clear();
		this.nEntries = 0;
	}

	public void addDatum(String caption, Double value) {
		this.data.add(new DataEntry(caption, value));
		nEntries = nEntries + 1;
	}
	
	public void addZoneLine(String caption, Double value){
		this.zoneLines.add(new DataEntry(caption, value));
	}

	private class DataEntry {
		public Double value;
		public String caption;

		public DataEntry(String caption, Double value) {
			this.caption = caption;
			this.value = value;
		}
	}

	private static Integer INNERBAR_MARGIN = 5;
	private static Integer OUTER_MARGIN = 75;
	private static Integer BAR_FLOOR_OFFSET = 20;
	private static Integer BAR_CEILING_OFFSET = 30;

	public void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {
		// Get the FontMetrics
		FontMetrics metrics = g.getFontMetrics(font);
		// Determine the X coordinate for the text
		int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
		// Determine the Y coordinate for the text (note we add the ascent, as
		// in java 2d 0 is top of the screen)
		int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
		// Set the font
		g.setFont(font);
		// Draw the String
		g.drawString(text, x, y);
	}

	private static final Font CHART_TITLE_FONT = Constants.FONT_HEADING_3; 
	private static final Font CHART_NORMAL_FONT = Constants.FONT_NORMAL;
	
	private void drawHorzLine(Graphics2D g2, Integer chartHeight, Integer chartWidth,Double barScaleY, Double value, String caption){
		
		Double y = chartHeight - BAR_FLOOR_OFFSET - barScaleY * value;
		
		// line
		Line2D line = new Line2D.Double(0.0, y, chartWidth,y);
		
		float dash1[] = { 10.0f };
		g2.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f));
		g2.draw(line);

		// label
		g2.drawString(caption, 5,
				Math.round(y) - 5);
	}

	private void drawZoneLines(Graphics2D g2, Integer chartHeight, Integer chartWidth,Double barScaleY) {

		drawHorzLine(g2,chartHeight,chartWidth,barScaleY,this.rangeMin,rangeMin.toString());
		drawHorzLine(g2,chartHeight,chartWidth,barScaleY,this.rangeMax,rangeMax.toString());
	}

	@Override
	protected void paintComponent(Graphics g) {

		Graphics2D g2 = (Graphics2D) g;

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// Determine chart area.
		Integer chartWidth = getWidth();// this.getPreferredSize().width;
		Integer chartHeight = getHeight();// this.getPreferredSize().height;

		// Determine scale.
		Integer n = (nEntries > 0) ? nEntries : 1;
		Integer barWidth = (chartWidth - 2 * OUTER_MARGIN - INNERBAR_MARGIN) / (n) - INNERBAR_MARGIN;
		Double barScaleY = (chartHeight.doubleValue() - BAR_FLOOR_OFFSET - BAR_CEILING_OFFSET) / ceiling;

		// title
		this.drawCenteredString(g2, chartTitle, new Rectangle(0, 0, chartWidth, 20), CHART_TITLE_FONT);
		g2.setFont(CHART_NORMAL_FONT);

		// zone lines & labels
		if (this.goalEnabled)
			this.drawZoneLines(g2, chartHeight, chartWidth,barScaleY);
		
		if(this.zoneLines.size() > 0){
			for(int i = 0; i < zoneLines.size(); i++){
				DataEntry de = zoneLines.get(i);
				this.drawHorzLine(g2, chartHeight, chartWidth, barScaleY,de.value,de.caption);
			}
		}

		g2.setStroke(new BasicStroke(1.0f));

		// baseline
		Line2D baseLine = new Line2D.Double(0.0, getHeight() - BAR_FLOOR_OFFSET, chartWidth,
				getHeight() - BAR_FLOOR_OFFSET);
		g2.draw(baseLine);

		// paint bars
		int width = barWidth;
		int x = OUTER_MARGIN;

		Iterator<DataEntry> it = data.iterator();
		while (it.hasNext()) {
			DataEntry de = it.next();
			Double val = de.value;
			String cap = de.caption;

			int height = (int) Math.round(barScaleY * val);

			if (!this.goalEnabled || (val >= rangeMin && val <= rangeMax)) {
				g.setColor(this.colorInRange);
			} else {
				g.setColor(this.colorOutOfRange);
			}
			g.fillRect(x, chartHeight - BAR_FLOOR_OFFSET - height, width, height);
			g.setColor(Color.black);
			g.drawRect(x, chartHeight - BAR_FLOOR_OFFSET - height, width, height);

			// caption
			// g.drawString("6/5", x, getHeight() - BAR_FLOOR_OFFSET + 15 );
			this.drawCenteredString(g2, cap, new Rectangle(x, getHeight() - BAR_FLOOR_OFFSET, width, 20), g.getFont());

			x += (width + INNERBAR_MARGIN);
		}
	}

}
