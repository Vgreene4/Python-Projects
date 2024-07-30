import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.input.MouseEvent;

/**
 * handles mouse actions that move a MyShape 
 */

public class MoveHandler implements EventHandler<MouseEvent>
{
	ShapeCanvas canvas;
	MyShape closestShape;
	double x0, y0;
	double x1, y1;
	
	/**
	 * creates a new MoveHandler with a ShapeCanvas specified 
	 * @param c   the ShapeCanvas that MyShapes will be moved on 
	 */
	public MoveHandler(ShapeCanvas c) 
	{
		canvas = c;
	}
	
	/**
	 * gets the shape closest to the mousePressed location
	 * @param e   the MouseEvent corresponding to the mouse press
	 */
	public void mousePressed(MouseEvent e)
	{
		closestShape = canvas.closestShape(e.getX(), e.getY());
		
		x0 = closestShape.getCenter().getX();
		y0 = closestShape.getCenter().getY();
		x1 = e.getX();
		y1 = e.getY();
	}
	/**
	 * moves a MyShape as the mouse is dragged across the canvas by changing its bounds and center and redrawing it 
	 * @param e   the MouseEvent corresponding to the mouse being dragged 
	 */
	public void mouseDragged(MouseEvent e)
	{
		if(closestShape != null)
		{	
			closestShape.move(e.getX() - x1, e.getY() - y1);
			x1 = e.getX();
			y1 = e.getY();	
			
			canvas.paint();
		}
	}
	
	/**
	 * adds a new MoveEdit to the canvas with the differences in the x and y directions between the current and former centers of the MyShape
	 * @param e   the MouseEvent corresponding to the released mouse 
	 */
	public void mouseReleased(MouseEvent e)
	{
		canvas.addEdit(new MoveEdit(canvas, closestShape, closestShape.getCenter().getX() - x0, closestShape.getCenter().getY() - y0));
	}
	
	/**
	 * implements actions to be taken when the mouse is pressed and dragged 
	 */
	@Override
	public void handle(MouseEvent e)
	{
		EventType eventType = e.getEventType();
		
		if(eventType == MouseEvent.MOUSE_PRESSED)
		{
			mousePressed(e);
		}
		if(eventType == MouseEvent.MOUSE_DRAGGED)
		{
			mouseDragged(e);
		}
		if(eventType == MouseEvent.MOUSE_RELEASED)
		{
			mouseReleased(e);
		}
	}
}
