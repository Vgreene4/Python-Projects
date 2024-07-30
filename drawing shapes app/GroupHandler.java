import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 * handles the actions that are performed on a ShapeGroup based on MouseEvents 
 */

public class GroupHandler implements EventHandler<MouseEvent>{

	private ShapeCanvas canvas;
	private ShapeGroup outliner;
	//private Rect bounder;        //
	
	
	/**
	 * creates a new GroupHandler with a ShapeCanvas specified
	 * @param c   the ShapeCanvas given to the GroupHandler 
	 */
	public GroupHandler(ShapeCanvas c) 
	{
		canvas = c;
	}
	
	/**
	 * on mouse pressed, creates a new ShapeGroup and sets the first point that defines it 
	 * @param e   the MouseEvent corresponding to the mouse press
	 */
	public void mousePressed(MouseEvent e)
	{
		outliner = new ShapeGroup();
		outliner.setP1(e.getX(), e.getY());
	}
	
	/**
	 * if the ShapeGroup is not null, sets the second point that determines the ShapeGroup, sets the ShapeCanvas's current 
	 * shape to this ShapeGroup, and draws this ShapeGroup
	 * @param e   the MouseEvent corresponding to the mouse drag 
	 */
	public void mouseDragged(MouseEvent e)
	{
		if(outliner != null)
		{
			outliner.setP2(e.getX(), e.getY());
			canvas.setCurrentShape(outliner);
			canvas.paint();
		}
	}
	
	/**
	 * iterates through the shapes in the canvas and if they are in the bounds of this ShapeGroup, they are removed from
	 * the canvas and they are added to the ShapeGroup. if the ShapeGroup is not empty, it is then added to the canvas 
	 * @param e   the MouseEvent corresponding to the mouse being released 
	 */
	public void mouseReleased(MouseEvent e)
	{	
		if(outliner != null)
		{
			int end = canvas.getShapes().size() - 1;
		
			while( end >= 0 )
			{
				MyShape currShape = canvas.getShapes().get(end);
			
				if(outliner.within(currShape))
				{
					//System.out.println("A shape is within the bounding box of outliner!");
					canvas.delete(currShape);
					outliner.addMember(currShape);
				}
				
				--end;
			}
			
			if(!outliner.isEmpty())
			{
				canvas.addShape(outliner);
			}
			
			//System.out.println(canvas.getShapes().size());
			canvas.addEdit(new GroupEdit(canvas, outliner));
			
			canvas.setCurrentShape(null);
			outliner = null;
		}
	}
	
	/**
	 * determines which action to perform based on the type of MouseEvent 
	 * @param e   a MouseEvent that determines if an action is performed or not
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
