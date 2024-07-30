import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.input.MouseEvent;

/**
 * base class for all shape drawing mouse event handlers for the MyShape objects 
 */

public class DrawHandler implements EventHandler<MouseEvent>{

	protected MyShape shape;
	protected ShapeCanvas canvas;
	
	/**
	 * creates a new DrawHandler with ShapeCanvas sc
	 * @param sc   the ShapeCanvas belonging to this DrawHandler
	 */
	public DrawHandler( ShapeCanvas sc ) 
	{
		canvas = sc;
	}
	
	/**
	 * triggered if the user presses their mouse - if shape is not null sets the canvas's current shape to shape and sets the shapes 
	 * first point to wherever the mouse is
	 * @param e   the MouseEvent whose x and y coordinates the shapes first point will have 
	 */
	protected void mousePressed( MouseEvent e )
	{
		if( shape != null )
		{
			canvas.setCurrentShape(shape);
			shape.setP1( e.getX(), e.getY() );
			//System.out.println("The shape isn't null!");
		}
		
		//System.out.println("You pressed the mouse");
	}
	
	/**
	 * triggered if the user drags their mouse - if the shape is not null sets the shapes first point to wherever the mouse is dragged.
	 * the bounds and the center of the shape are updated as p2 changes. the canvas is then painted
	 * @param e   the MouseEvent whose x and y coordinates the shapes second point will have 
	 */
	protected void mouseDragged( MouseEvent e )
	{
		if( shape != null )
		{
			shape.setP2( e.getX(), e.getY() );
			
			canvas.paint();
		}
	}
	
	/**
	 * triggered if the user releases their mouse - if the shape is not null the shape is added to the canvas, the canvas's current shape
	 * is set to null, and shape is set to null. 
	 * @param e   a MouseEvent 
	 */
	protected void mouseReleased( MouseEvent e )
	{
		if( shape != null )
		{
			canvas.addShape(shape);
			canvas.addEdit(new DrawEdit(canvas, shape));
			
			canvas.setCurrentShape(null);
			shape = null;
		}
	}
	
	/**
	 * takes a MouseEvent and executes the appropriate mouse handler based on the type of MouseEvent
	 * @param event   the MouseEvent whose type determines which mouse handler is executed
	 */
	@Override
	public void handle( MouseEvent event )
	{
		EventType eventType = event.getEventType();
		
		if( eventType == MouseEvent.MOUSE_PRESSED )
		{
			mousePressed(event);
			//System.out.println(eventType.getName());
		}
		else if(eventType == MouseEvent.MOUSE_DRAGGED )
		{
			mouseDragged(event);
			//System.out.println(eventType.getName());
		}
		else if(eventType == MouseEvent.MOUSE_RELEASED )
		{
			mouseReleased(event);
			//System.out.println(eventType.getName());
		}
	}
	
}


