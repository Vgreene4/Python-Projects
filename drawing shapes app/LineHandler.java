import javafx.scene.input.MouseEvent;

public class LineHandler extends DrawHandler{

	/**
	 * creates a new LineHandler with its ShapeCanvas specified
	 * @param sc   the ShapeCanvas of the LineHandler
	 */
	public LineHandler( ShapeCanvas sc ) 
	{
		super(sc);
	}
	
	/**
	 * triggers when the user presses the mouse - creates a new Line and assigns it to shape, then 
	 * calls the parents mousePressed method
	 * @param e   the MouseEvent the parents mousePressed method is called on
	 */
	@Override
	public void mousePressed( MouseEvent e )
	{
		shape = new Line();
		
		super.mousePressed(e);
	}

}








//public class LineHandler extends DrawHandler{
//
//	/**
//	 * creates a new LineHandler with its ShapeCanvas specified
//	 * @param sc   the ShapeCanvas of the LineHandler
//	 */
//	public LineHandler( ShapeCanvas sc ) 
//	{
//		super(sc);
//	}
//	
//	/**
//	 * triggers when the user presses the mouse - creates a new Line and assigns it to shape, then 
//	 * calls the parents mousePressed method
//	 * @param e   the MouseEvent the parents mousePressed method is called on
//	 */
//	@Override
//	public void mousePressed( MouseEvent e )
//	{
//		shape = new Line();
//		
//		super.mousePressed(e);
//	}
//
//}
