import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

/**
 * this class specifies a Line from two points. The line can be drawn
 */
public class Line extends MyShape{
	
	/**
	 * makes a line with neither endpoint specified
	 */
	public Line() 
	{
		super();
	}
	
	/**
	 * makes a line with its endpoints specified
	 * @param p1   the first endpoint of the line
	 * @param p2   the second endpoint of the line
	 */
	public Line( Point2D p1, Point2D p2 )
	{
		super( p1, p2 );
	}
	
	/**
	 * makes a line and specifies the coordinates of its endpoints
	 * @param x1   the x-coordinate of the first endpoint of the line
	 * @param y1   the y-coordinate of the first endpoint of the line
	 * @param x2   the x-coordinate of the second endpoint of the line
	 * @param y2   the y-coordinate of the second endpoint of the line
	 */
	public Line( double x1, double y1, double x2, double y2 )
	{
		super( x1, y1, x2, y2 );
	}

	/**
	 * draws the line using a GraphicsContext
	 * @param gc   the GraphicsContext used to draw the line
	 */
	@Override
	public void draw( GraphicsContext gc )
	{
		gc.setStroke(color);
		gc.strokeLine( p1.getX(), p1.getY(), p2.getX(), p2.getY() );
		//System.out.println("tried to draw a line");
	}
	
	/**
	 * gets a string with identifying information about this line
	 * @return   a string with identifying information about this line
	 */
	@Override
	public String toString()
	{
		return "line " + super.toString();
	}
}
