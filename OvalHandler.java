import javafx.scene.input.MouseEvent;

/**
 * this class handles mouse events for Ovals 
 */
public class OvalHandler extends DrawHandler{

	/**
	 * creates a new OvalHandler and specifies its ShapeCanvas
	 * @param sc   the OvalHandlers ShapeCanvas
	 */
	public OvalHandler( ShapeCanvas sc ) 
	{
		super(sc);
	}
	
	/**
	 * triggers when the user presses the mouse - creates a new Oval and assigns it to shape, then 
	 * calls the parents mousePressed method 
	 * @param e   the MouseEvent the parents mousePressed method is called on
	 */
	@Override
	public void mousePressed( MouseEvent e )
	{
		shape = new Oval();
		
		super.mousePressed(e);
		
		//System.out.println("Oval mouse pressed");
	}

}
