/******************************************************
 *  Compilation:  javac Plot.java
 *  Execution:    java Plot
 *
 ******************************************************/
 
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;
import java.awt.Font;

/**
 *	This class provide an utility for plot a graph with one or more lines.	
 *
 */
public class Plot {
	
	private final static double PEN_RADIUS = 0.0025;
	private final static double VERTICAL_MARGIN = 0.08;
	private final static double VERTICAL_PADDING = 0.01;
	private final static double HORIZONTAL_MARGIN = 0.09;
	private final static double HORIZONTAL_PADDING = 0.01;
	
	private ArrayList<Line> lines;
	private double maxX = Double.MIN_VALUE, minX = Double.MAX_VALUE, 
				   maxY = Double.MIN_VALUE, minY = Double.MAX_VALUE;
	private String title, xLabel, yLabel;
	
	class Line {
		double[] x;
		double[] y;
		Color color;
		
		Line(double[] x, double[] y, Color color) {
			this.x = x;
			this.y = y;
			this.color = color;
		}
	}
	
	/**
	 *	Create a new Plot with title.
	 *
	 *	@param title title of plot
	 *	@param xLabel label of x axis
	 *	@param yLabel label of y axis
	 */
	public Plot(String title, String xLabel, String yLabel) {
		lines = new ArrayList<Line>();
		this.title = title;
		this.xLabel = xLabel;
		this.yLabel = yLabel;
		
		StdDraw.enableDoubleBuffering();
		StdDraw.setCanvasSize(800,600);
        StdDraw.setPenRadius(PEN_RADIUS);
		
		StdDraw.setFont( new Font("Arial", 0, 14));
	}
	
	/**
	 *	Create a new Plot without title.
	 */
	public Plot(String xLabel, String yLabel) {
		this(null, xLabel, yLabel);
	}
	
	/**
	 *	Plots a new line, specifying point coordinates and color.	
	 *
	 *	@param x x component of points
	 *	@param y y component of points
	 *	@param color of line 
	 *	@throws IllegalArgumentException if x's size don't match y's size.
	 */
	public void plot(double[] x, double[] y, Color color) throws IllegalArgumentException 
	{
		if(x.length != y.length) 
			throw new IllegalArgumentException("Size of x don't math size of y in plot method.");
		
		//Add the new line
		lines.add(new Line(x,y, color));
		
		//Update max and min
		boolean changeScale = false;
		double tmp = min(x);
		if(tmp < minX){ minX = tmp; changeScale = true; }
		tmp = max(x);
		if(tmp > maxX){ maxX = tmp; changeScale = true; }
		tmp = min(y);
		if(tmp < minY){ minY = tmp; changeScale = true; }
		tmp = max(y);
		if(tmp > maxY){ maxY = tmp; changeScale = true; }
		
		//Check the actual scale for redraw
		if(changeScale) {
			StdDraw.clear();
			
			//Draw axes
			StdDraw.setPenColor(StdDraw.BLACK);
			StdDraw.line(HORIZONTAL_MARGIN, VERTICAL_MARGIN, HORIZONTAL_MARGIN, 1-VERTICAL_MARGIN);
			StdDraw.line(HORIZONTAL_MARGIN, VERTICAL_MARGIN, 1-HORIZONTAL_MARGIN, VERTICAL_MARGIN);
			StdDraw.line(HORIZONTAL_MARGIN, 1-VERTICAL_MARGIN, 1-HORIZONTAL_MARGIN, 1-VERTICAL_MARGIN);
			StdDraw.line(1-HORIZONTAL_MARGIN, VERTICAL_MARGIN, 1-HORIZONTAL_MARGIN, 1-VERTICAL_MARGIN);
			
			//Draw label on axis
			StdDraw.setFont( new Font("Arial", 0, 14));
			StdDraw.text(1-HORIZONTAL_MARGIN, VERTICAL_MARGIN-0.06, xLabel);
			StdDraw.text(HORIZONTAL_MARGIN, 1-VERTICAL_MARGIN+0.04, yLabel);
			
			//Draw values on axes
			for(int i = 0; i <= 8; i++) {
				double xPoint = i*(1-2*(HORIZONTAL_MARGIN+HORIZONTAL_PADDING))/8+HORIZONTAL_MARGIN + HORIZONTAL_PADDING,
					   yPoint = i*(1-2*(VERTICAL_MARGIN+VERTICAL_PADDING))/8+VERTICAL_MARGIN + VERTICAL_PADDING;
				
				StdDraw.line(xPoint, VERTICAL_MARGIN, xPoint, VERTICAL_MARGIN-0.01);
				StdDraw.text(xPoint, VERTICAL_MARGIN-0.03, String.format("%.2f", (minX + i*(maxX-minX)/8)) );
				StdDraw.line(HORIZONTAL_MARGIN, yPoint, HORIZONTAL_MARGIN-0.01, yPoint);
				StdDraw.textRight(HORIZONTAL_MARGIN-0.02, yPoint, String.format("%1.1e", (minY + i*(maxY-minY)/8)));
			}
			
			if(title != null){
				StdDraw.setFont( new Font("Arial", 0, 20));
				StdDraw.text(0.5, 0.96, title);
			}
			
			//Draw Lines
			double xScale = 1/(maxX-minX)*(1-2*(HORIZONTAL_MARGIN+HORIZONTAL_PADDING));
			double yScale = 1/(maxY-minY)*(1-2*(VERTICAL_MARGIN+VERTICAL_PADDING));
			
			for(Line line:lines) {
				StdDraw.setPenColor(line.color);
				
				for(int i = 0; i < line.x.length-1; i++) 
					StdDraw.line((line.x[i]-minX)*xScale + HORIZONTAL_MARGIN + HORIZONTAL_PADDING, 
								 (line.y[i]-minY)*yScale + VERTICAL_MARGIN+VERTICAL_PADDING,
								 (line.x[i+1]-minX)*xScale + HORIZONTAL_MARGIN +HORIZONTAL_PADDING, 
								 (line.y[i+1]-minY)*yScale + VERTICAL_MARGIN+VERTICAL_PADDING);
					
			}
		}
		//Draw only the last line
		else {
			StdDraw.setPenColor(color);
			
			double xScale = 1/(maxX-minX)*(1-2*(HORIZONTAL_MARGIN+HORIZONTAL_PADDING));
			double yScale = 1/(maxY-minY)*(1-2*(VERTICAL_MARGIN+VERTICAL_PADDING));
			
			for(int i = 0; i < x.length-1; i++) 
				StdDraw.line((x[i]-minX)*xScale + HORIZONTAL_MARGIN + HORIZONTAL_PADDING, 
							 (y[i]-minY)*yScale + VERTICAL_MARGIN+VERTICAL_PADDING,
							 (x[i+1]-minX)*xScale + HORIZONTAL_MARGIN + HORIZONTAL_PADDING, 
							 (y[i+1]-minY)*yScale + VERTICAL_MARGIN+VERTICAL_PADDING);
		}
	}
	
	
	/**
	 *	Plots a new line, specifying point coordinates, the color by default is black.	
	 *
	 *	@param x x component of points
	 *	@param y y component of points
	 *	@throws IllegalArgumentException if x's size don't match y's size.
	 */
	public void plot(double[] x, double[] y) throws IllegalArgumentException {
		plot(x,y, StdDraw.BLACK);
	}
	
	/**
	 *	Update the draw. 
	 */
	public void show() { StdDraw.show(); }
	
	
	/* *************************************
	 *	Utility
	 ***************************************/
	
	//Return the min of array
	private double min(double[] v) {
		if(v.length < 1) throw new IllegalArgumentException("Void array in min method!");
		
		double min = v[0];
		for(int i = 1; i < v.length; i++)
			if(v[i] < min) min = v[i];
		
		return min;
	}
	
	//Return the min of array
	private double max(double[] v) {
		if(v.length < 1) throw new IllegalArgumentException("Void array in max method!");
		
		double max = v[0];
		for(int i = 1; i < v.length; i++)
			if(v[i] > max) max = v[i];
		
		return max;
	}
	
	
	/***************************************
	 *	Test
	 ***************************************/
	public static void main(String[] args)
	{
		Plot p = new Plot("plot", "x","y");
		List<List<DirectedEdge>> list = Project3.result();
		
		double x[] = new double[20];
		int j = 25;
		for(int i=0; i<20; i=i+1)
		{
			x[i] = j;
			j=j+25;
		}
		
		double y[] = new double[20];
		for(int i=0; i<list.size(); i++)
		{
			y[i] = list.get(i).size();
			p.plot(x,y, StdDraw.BLACK);
		}
		
		p.show();
	}
}