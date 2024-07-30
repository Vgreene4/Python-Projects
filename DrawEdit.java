
/**
 * creates edits that for drawing that allow MyShapes to be draw and erased on the canvas 
 */

public class DrawEdit extends Edit{

	/**
	 * creates a new DrawEdit with ShapeCanvas and MyShape specified
	 * @param c   the ShapeCanvas to be drawn on
	 * @param s   the Shape being drawn/erased
	 */
	public DrawEdit(ShapeCanvas c, MyShape s) 
	{
		super(c, s);
	}
	
	/**
	 * adds a shape to the canvas and draws it 
	 */
	@Override 
	public void redo()
	{
		canvas.addShape(shape);
		canvas.paint();
	}
	
	/**
	 * deletes a shape from the canvas and paints the canvas without the deleted shape 
	 */
	public void undo()
	{
		canvas.delete(shape);
		canvas.paint();
	}

}
