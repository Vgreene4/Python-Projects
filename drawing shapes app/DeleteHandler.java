import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.input.MouseEvent;

/**
 * this class handles deleting shapes from the canvas 
 */

public class DeleteHandler implements EventHandler<MouseEvent>{

	private ShapeCanvas canvas;
	
	/**
	 * creates a new DeletHandler with a ShapeCanvas 
	 * @param c   the canvas things will be deleted from
	 */
	public DeleteHandler(ShapeCanvas c)
	{
		canvas = c;
	}
	
	/**
	 * when the mouse is clicked gets the closest shape on the canvas and deletes it
	 * @param e   the MouseEvent that happens when the mouse is clicked
	 */
	public void mouseClicked(MouseEvent e)
	{
		double mouseX = e.getX();
		double mouseY = e.getY();
		
		MyShape shapeToDelete = canvas.closestShape(mouseX, mouseY);
		canvas.delete(shapeToDelete);
		
		canvas.addEdit(new DeleteEdit(canvas, shapeToDelete));
		
		canvas.paint();
	}
	
	/**
	 * sets the action that occurs when the mouse is clicked 
	 */
	@Override 
	public void handle(MouseEvent e)
	{
		EventType eventType = e.getEventType();
		
		if(eventType == MouseEvent.MOUSE_CLICKED)
		{
			mouseClicked(e);
		}
	}

}
