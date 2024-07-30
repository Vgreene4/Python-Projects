import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.input.MouseEvent;

/**
 * this class handles cloning actions on a ShapeCanvas 
 */

public class CopyHandler implements EventHandler<MouseEvent>{

	private ShapeCanvas canvas;
	private MyShape copy;
	private double x0, y0;
	private double x1, y1;
	
	/**
	 * creates a new CopyHandler with a ShapeCanvas specified 
	 * @param c   the ShapeCanvas the CopyHandler will act on 
	 */
	public CopyHandler(ShapeCanvas c) 
	{
		canvas = c;
	}
	
	/**
	 * on mouse pressed, creates a clone of the closest MyShape and adds it to the canvas
	 * @param e   the MouseEvent corresponding to the mouse press
	 */
	public void mousePressed(MouseEvent e)
	{	
		MyShape closestShape = canvas.closestShape(e.getX(), e.getY());

		x1 = e.getX();
		y1 = e.getY();
		
		if(closestShape != null)
		{
			copy = closestShape.clone();
			canvas.addShape(copy);
		}
	}
	
	/**
	 * on mouse dragged, moves the clone around the ShapeCanvas by changing its coordinate and repainting it  
	 * @param e   the MouseEvent corresponding to the mouse drag
	 */
	public void mouseDragged(MouseEvent e)
	{
		if(copy != null)
		{
			copy.move(e.getX() - x1, e.getY() - y1);
			
			x1 = e.getX();
			y1 = e.getY();
				
			canvas.paint();
		}
	}
	
	/**
	 * adds a CopyEdit to the stack of actions to undo
	 * @param e   the MouseEvent corresponding to the mouse being released 
	 */
	public void mouseReleased(MouseEvent e)
	{
		canvas.addEdit(new CopyEdit(canvas, copy));
	}
	
	/**
	 * sets the actions that will be performed when the mouse is pressed and dragged 
	 * @param e   the MouseEvent that determines the action performed
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
