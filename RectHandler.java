import javafx.scene.input.MouseEvent;

/**
 * this class handles mouse events for Rects
 */
public class RectHandler extends DrawHandler{

	/**
	 * creates a new RectHandler with its ShapeCanvas specified
	 * @param sc   the ShapeCanvas of the RectHandler 
	 */
	public RectHandler( ShapeCanvas sc )
	{
		super(sc);
	}
	
	/**
	 * triggers when the user presses the mouse - creates a new Rect and assigns it to shape, then 
	 * calls the parents mousePressed method
	 * @param e   the MouseEvent the parents mousePressed method is called on
	 */
	@Override
	public void mousePressed( MouseEvent e )
	{
		shape = new Rect();
		
		super.mousePressed(e);
	}

}
