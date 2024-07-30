import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

/**
 * this class specifies a rectangle that is determined by two points and can be drawn
 */
public class Rect extends MyShape{

	/**
	 * creates a rectangle with neither of its determining points specified
	 */
	public Rect() 
	{
		super();
	}
	
	/**
	 * creates a rectangle with both determining points specified
	 * @param p1   the first point that determines the rectangle
	 * @param p2   the second point that determines the rectangle
	 */
	public Rect( Point2D p1, Point2D p2 )
	{
		super( p1, p2 );
	}
	
	/**
	 * creates a rectangle with the x and y coordinates of its determining points specified
	 * @param x1   the x-coordinate of the first determining point
	 * @param y1   the y-coordinate of the first determining point
	 * @param x2   the x-coordinate of the second determining point
	 * @param y2   the y-coordinate of the second determining point
	 */
	public Rect( double x1, double y1, double x2, double y2 )
	{
		super( x1, y1, x2, y2 );
	}
	
	/**
	 * draws a filled or unfilled rectangle (depending on the value of isFilled) using a GraphicsContext
	 * @param gc   the GraphicsContext used to draw the rectangle
	 */
	@Override
	public void draw( GraphicsContext gc )
	{
		if( isFilled )
		{
			gc.setFill(color);
			gc.fillRect( ulx, uly, width, height );
		}
		else
		{
			gc.setStroke(color);
			gc.strokeRect( ulx, uly, width, height );
		}
	}
	
	/**
	 * gets a string with identifying information about this rect
	 * @return   a string with identifying information about this rect
	 */
	@Override 
	public String toString()
	{
		return "rect " + super.toString();
	}

}
