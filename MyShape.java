import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * parent class of line, oval, and rect shapes that creates shapes
 * from two points  
 */
public class MyShape implements Serializable, Cloneable{
	
	protected transient Point2D p1;
	protected transient Point2D p2;
	protected transient Point2D center;
	protected transient Color color;
	protected boolean isFilled;
	protected double ulx, uly;   
	protected double width, height;

	/**
	 * creates a default shape
	 */
	public MyShape() 
	{
		
	}
	
	/**
	 * creates a shape from two points 
	 * @param p1   the first point the shape is determined by
	 * @param p2   the second point the shape is determined by 
	 */
	public MyShape( Point2D p1, Point2D p2 )
	{
		this.p1 = p1;
		this.p2 = p2;
		
		updateBounds();
		updateCenter();
	}
	
	/**
	 * creates a shape from two sets of coordinates for the two points that 
	 * will determine the shape
	 * @param x1   the x-coordinate of the first point
	 * @param y1   the y-coordinate of the first point
	 * @param x2   the x-coordinate of the second point
	 * @param y2   the y-coordinate of the second point 
	 */
	public MyShape( double x1, double y1, double x2, double y2 )
	{
		this(new Point2D(x1, y1), new Point2D(x2, y2));
	}
	
	//selectors 
	
	/**
	 * gets the first point that determines the shape
	 * @return   the first point that determines the shape
	 */
	public Point2D getP1()
	{
		return p1;
	}
	
	/**
	 * gets the second point that determines the shape
	 * @return   the second point that determines the shape
	 */
	public Point2D getP2()
	{
		return p2;
	}
	
	/**
	 * gets the color of the shape
	 * @return   the color of the shape
	 */
	public Color getColor()
	{
		return color;
	}
	
	/**
	 * says whether the shape will be filled or not
	 * @return   boolean saying whether the shape will be filled or not
	 */
	public boolean isFilled()
	{
		return isFilled;
	}
	
	/**
	 * gets the upper left x-coordinate of the bounding box of the shape
	 * @return   the upper left x-coordinate of the bounding box of the shape
	 */
	public double getULX()
	{
		return ulx;
	}
	
	/**
	 * gets the upper left y-coordinate of the bounding box of the shape
	 * @return   the upper left y-coordinate of the bounding box of the shape
	 */
	public double getULY()
	{
		return uly;
	}
	
	/**
	 * gets the width of the shape
	 * @return   the width of the shape
	 */
	public double getWidth()
	{
		return width;
	}
	
	/**
	 * gets the height of the shape
	 * @return   the height of the shape
	 */
	public double getHeight()
	{
		return height;
	}
	
	/**
	 * gets the point at the center of the shape
	 * @return   the point at the center of the shape
	 */
	public Point2D getCenter()
	{
		return center;
	}
	
	//mutators
	
	/**
	 * sets p1 to a new point 
	 * @param newp1   the point that p1 will be set to
	 */
	public void setP1( Point2D newp1 )
	{
		p1 = newp1;
	}
	
	/**
	 * sets p1 to a new point with coordinates (x, y)
	 * @param x   the x-coordinate of the point that will replace p1
	 * @param y   the y-coordinate of the point that will replace p1
	 */
	public void setP1( double x, double y )
	{
		p1 = new Point2D( x, y );
	}
	
	/**
	 * sets p2 to a new point
	 * @param newp2   the point that p1 will be set to 
	 */
	public void setP2( Point2D newp2 )
	{
		p2 = newp2;
		
		updateBounds();
		updateCenter();
	}
	
	/**
	 * sets p2 to a new point with coordinates (x, y)
	 * @param x   the x-coordinate of the point that will replace p2
	 * @param y   the y-coordinate of the point that will replace p2
	 */
	public void setP2( double x, double y )
	{
		p2 = new Point2D( x, y );
		
		updateBounds();
		updateCenter();
	}
	
	/**
	 * changes the color of the shape
	 * @param newColor   the new color of the shape
	 */
	public void setColor( Color newColor )
	{
		color = newColor;
	}
	
	/**
	 * sets whether the shape is filled or an outline
	 * @param filled   a boolean determining if the shape is filled or not
	 */
	public void setFilled( boolean filled )
	{
		isFilled = filled;
	}
	
	/**
	 * updates the upper left x and y coordinates, the width, and the height of the bounding box for the shape
	 */
	public void updateBounds()
	{
		ulx = Math.min( p1.getX(), p2.getX() );
		uly = Math.min( p1.getY(), p2.getY() );
		
		width = Math.abs( p2.getX() - p1.getX() );
		height = Math.abs( p2.getY() - p1.getY() );
		
		//System.out.println("Bounds: " + "ulx: " + ulx + " uly:" + uly);
		
		//test more because replaced ifs
	}
	
	/**
	 * updates the center of the shape and sets it to the point between p1 and p2
	 */
	public void updateCenter()
	{
		center = p1.midpoint(p2);
	}
	
	//others 
	
	/**
	 * finds the distance between the center of the object and the point with coordinates (x, y)
	 * @param x   the x-coordinate of the point whose distance from the center of the shape we are finding
	 * @param y   the y-coordinate of the point whose distance from the center of the shape we are finding
	 * @return    the distance between the center of the object and the point with coordinates (x, y)
	 */
	public double distance( double x, double y )
	{		
		return center.distance(x, y);
	}
	
	/**
	 * draws the bounding box of the shape
	 * @param gc   the GraphicsContext that will draw the bounding box 
	 */
	public void drawBounds( GraphicsContext gc )
	{
		//gc.setStroke(color);       //messes up coloring by forcing bounding box to have color picker color
		gc.setLineDashes(2);         //setting line dashes
		gc.strokeRect(ulx, uly, width, height);
		gc.setLineDashes(null);
		
		//System.out.println("Drawing bounds");
	}
	
	/**
	 * draws the shape 
	 * @param gc   the GraphicsContext that will draw the shape
	 */
	public void draw( GraphicsContext gc )
	{
		
	}
	
	/**
	 * gets a String with the coordinates, color components, and isFilled values of this shape
	 * @return a String with the coordinates, color components, and isFilled values of this shape
	 */
	@Override 
	public String toString()
	{
		return String.format("%.0f %.0f %.0f %.0f %.3f %.3f %.3f %b", 
				p1.getX(), p1.getY(), p2.getX(), p2.getY(), color.getRed(), color.getGreen(), color.getBlue(), isFilled);
	}
	
	/**
	 * moves this MyShape by dx in the x-direction and dy in the y-direction
	 * @param dx   the amount this MyShape will move in the x-direction
	 * @param dy   the amount this MyShape will move in the y-direction
	 */
	public void move(double dx, double dy)
	{
		p1 = p1.add(dx, dy);
		p2 = p2.add(dx, dy);
		
		updateBounds();
		updateCenter();
	}
	
	/**
	 * makes a deep copy of this MyShape and returns it 
	 * @return   a deep copy of this MyShape
	 */
	@Override
	public MyShape clone()
	{
		try
		{
			MyShape copy = (MyShape) super.clone();
		
			copy.setP1(new Point2D(p1.getX(), p1.getY()));
			copy.setP2(new Point2D(p2.getX(), p2.getY()));
			
			copy.setColor(Color.color(color.getRed(), color.getGreen(), color.getBlue()));
			copy.setFilled(Boolean.valueOf(isFilled));
			
			copy.updateBounds();
			copy.updateCenter();
			
			//System.out.println("ulx: "+copy.getULX()+ " uly: "+ copy.getULY() + " width: "+ width + " height "+ height);
		
			return copy;
		}
		catch(CloneNotSupportedException e)
		{
			System.err.println("MyShape cloning was not successful");
			return null;
		}
	}
	
	/**
	 * serializes this MyShape object by writing its coordinates, color components, width, height, and isFilled value
	 * @param out            the ObjectOutputStream the serialized MyShape attributes are written to
	 * @throws IOException   indicates a failure to write to out
	 */
//	private void writeObject(ObjectOutputStream out) throws IOException
//	{
//		out.defaultWriteObject(); //isFilled, width, height, ulx, uly
//		
//		out.writeDouble(p1.getX());
//		out.writeDouble(p1.getY());
//		
//		out.writeDouble(p2.getX());
//		out.writeDouble(p2.getY());
//		
//		//writing the colors
//		out.writeDouble(color.getRed());
//		out.writeDouble(color.getGreen());
//		out.writeDouble(color.getBlue());
//	}
	
	
	
	
	
	
	private void writeObject(ObjectOutputStream fOut) throws IOException
	{
		fOut.defaultWriteObject();
		
		fOut.writeDouble(p1.getX());
		fOut.writeDouble(p1.getY());
		
		fOut.writeDouble(p2.getX());
		fOut.writeDouble(p2.getY());
		
		fOut.writeDouble(color.getRed());
		fOut.writeDouble(color.getGreen());
		fOut.writeDouble(color.getBlue());
	}
	
	
	/**
	 * reads serialized MyShape object from in and sets the parameters of MyShape to the read parameters
	 * @param in                        the Object InputStream the serialized MyShape object will be read from
	 * @throws IOException              indicates a failure to read from in
	 * @throws ClassNotFoundException   thrown when Java cannot find a class when reading the serialized MyShape object
	 */

	private void readObject(ObjectInputStream fIn) throws ClassNotFoundException, IOException
	{
		fIn.defaultReadObject();
		
		double p1x = fIn.readDouble();
		double p1y = fIn.readDouble();
		
		p1 = new Point2D(p1x, p1y);
		
		double p2x = fIn.readDouble();
		double p2y = fIn.readDouble();
		
		p2 = new Point2D(p2x, p2y);
		
		double r = fIn.readDouble();
		double g = fIn.readDouble();
		double b = fIn.readDouble();
		
		color = Color.color(r, g, b);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
//		
//		in.defaultReadObject();       //isFilled, width, height
//		
//		double p1x = in.readDouble();
//		double p1y = in.readDouble();
//		p1 = new Point2D(p1x, p1y);
//		
//		double p2x = in.readDouble();
//		double p2y = in.readDouble();
//		p2 = new Point2D(p2x, p2y);
//		
//		double r = in.readDouble();
//		double g = in.readDouble();
//		double b = in.readDouble();
//		color = Color.color(r, g, b);
//		
//		//update bounds things are already read in by default
//		updateCenter();
//	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
