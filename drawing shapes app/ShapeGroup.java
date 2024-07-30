import java.util.ArrayList;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * this class creates a group of shapes that acts like a single MyShape 
 */

public class ShapeGroup extends MyShape{

	private ArrayList<MyShape> group;
	
	/**
	 * creates a new ShapeGroup
	 */
	public ShapeGroup()
	{
		super();
		
		group = new ArrayList<MyShape>();
	}
	
	/**
	 * returns true if the ShapeGroup has no MyShapes and false otherwise 
	 * @return   true if the ShapeGroup has no MyShapes and false otherwise
	 */
	public boolean isEmpty()
	{
		return group.isEmpty();
	}
	
	/**
	 * returns the number of shapes in the ShapeGroup
	 * @return   the number of shapes in the ShapeGroup
	 */
	public int size()
	{
		return group.size();
	}
	
	/**
	 * clones a ShapeGroup by deep cloning each of its MyShapes and by giving the cloned ShapeGroup the same bounds as the original
	 * @return    a clone of this ShapeGroup
	 */
	@Override
	public MyShape clone()
	{
		ShapeGroup sgClone = new ShapeGroup();
		sgClone.setP1(new Point2D(p1.getX(), p1.getY()));      //don't touch
		sgClone.setP2(new Point2D(p2.getX(), p2.getY()));      //NEED OR NO BOUNDING BOX
		
		for(MyShape s : group)
		{
			sgClone.addMember(s.clone());
		}
		
		sgClone.updateBounds();
		sgClone.updateCenter();
		
		//System.out.println("ulx: "+ sgClone.getULX() + " uly: " + " width: " + sgClone.getWidth() + " height: " + sgClone.getHeight());
		System.out.println(sgClone.toString());
		
		return sgClone;
	}
	
	/**
	 * adds a MyShape to the ShapeGroup if it is not already in the ShapeGroup and then updates the center of this ShapeGroup
	 * @param shape   the MyShape that will be added to the ShapeGroup if it is not already in the ShapeGroup
	 */
	public void addMember(MyShape shape)
	{
		if(!group.contains(shape))
		{
			group.add(shape);
		}
		
		updateCenter();
	}
	
	/**
	 * removes a MyShape from this ShapeGroup and updates the center of the ShapeGroup
	 * @param shape   the MyShape being removed from the ShapeGroup
	 */
	public void removeMember(MyShape shape)
	{
		group.remove(shape);
		updateCenter();
	}
	
	/**
	 * returns true if the center of a MyShape is within the bounds of the ShapeGroup and false otherwise 
	 * @param shape   the MyShape we are seeing if it is within the bounds of this ShapeGroup
	 * @return   true if the center of a MyShape is within the bounds of the ShapeGroup and false otherwise 
	 */
	public boolean within(MyShape shape)
	{
		double centerX = shape.getCenter().getX();
		double centerY = shape.getCenter().getY();
		
		if(centerX <= ulx + width && centerX >= ulx && centerY >= uly && centerY <= uly + height)
		{
			return true;
		}
		
		return false;
	}
	
	/**
	 * updates the center of the shapeGroup so that it is the average of all of the MyShapes contained within itself
	 */
	@Override
	public void updateCenter()
	{
		double xTot = 0;
		double yTot = 0;
		
		for(MyShape s : group)
		{
			xTot += s.getCenter().getX();
			yTot += s.getCenter().getY();
		}
		
		center = new Point2D(xTot/group.size(), yTot/group.size());
	}
	
	/**
	 * moves every MyShape in the ShapeGroup by dx in the x-direction and dy in the y-direction and then updates the bounding box of the ShapeGroup
	 */
	@Override
	public void move(double dx, double dy)
	{
		for(MyShape s : group)
		{
			s.move(dx, dy);
		}
		
		super.move(dx, dy);
		updateCenter();
		//super.updateCenter();
	}
	
	/**
	 * draws all shapes in the ShapeGroup and draws the bounding box of the ShapeGroup in gray 
	 */
	@Override
	public void draw(GraphicsContext gc)
	{	
		for(MyShape s : group)
		{
			s.draw(gc);
		}
		
		gc.setStroke(Color.GRAY);
		drawBounds(gc);
		//System.out.println("reached the bounds");
	}
	
	/**
	 * returns a string representation of this ShapeGroup
	 * @return   a string representation of this ShapeGroup
	 */
	@Override
	public String toString()
	{
		String returnString  = String.format("ShapeGroup %d %.0f %.0f %.0f %.0f \n", group.size(), p1.getX(), p1.getY(), p2.getX(), p2.getY());
		
		for(MyShape s : group)
		{
			returnString = returnString + s.toString() + "\n";
		}
		
		return returnString;
	}
	
	/**
	 * gets the members of this group
	 * @return   the members of this group
	 */
	public ArrayList<MyShape> getMembers()
	{
		return group;
	}
}
