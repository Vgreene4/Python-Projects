import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * this class specifies an Oval that is determined by two points and can be drawn
 */
public class Oval extends MyShape{

	/**
	 * creates an oval with neither point specified
	 */
	public Oval() 
	{
		super();
	}
	
	/**
	 * creates an oval with both points specified
	 * @param p1   the first point that determines the oval
	 * @param p2   the seconds point that determines the oval
	 */
	public Oval( Point2D p1, Point2D p2 )
	{
		super( p1, p2 );
	}
	
	/**
	 * creates an oval that is determined by the points with coordinates (x1, y1) and (x2, y2) 
	 * @param x1   the x-coordinate of the first point
	 * @param y1   the y-coordinate of the first point
	 * @param x2   the x-coordinate of the second point
	 * @param y2   the y-coordinate of the second point
	 */
	public Oval( double x1, double y1, double x2, double y2 )
	{
		super( x1, y1, x2, y2 );
	}
	
	/**
	 * draws a filled or unfilled oval (depending on the value of isFilled) using a GraphicsContext
	 * @param gc   a GraphicsContext used to draw the oval
	 */
	@Override
	public void draw( GraphicsContext gc )
	{
		if( isFilled )
		{
			gc.setFill(color);
			//drawBounds(gc);         //testing drawBounds
			//gc.setLineDashes(null);
			gc.fillOval( ulx, uly, width, height);
	
			//System.out.println("You drew an oval!");
		}
		else
		{
			gc.setStroke(color);
			//drawBounds(gc);         //testing drawBounds
			//gc.setLineDashes(null);
			gc.strokeOval( ulx, uly, width, height);
			
			//this seems like its finally running when its supposed to
			//System.out.println("Not filled oval!");
		}
		
		//System.out.println("You drew an oval!");
	}
	
	/**
	 * gets a string with identifying information about this oval
	 * @return   a string with identifying information about this oval
	 */
	@Override 
	public String toString()
	{
		return "oval " + super.toString();
	}

}
