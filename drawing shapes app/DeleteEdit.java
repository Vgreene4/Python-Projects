
/**
 * creates edits that allow deletion of MyShapes to be undone and redone 
 */

public class DeleteEdit extends Edit{

	/**
	 * creates a new DeleteEdit object with ShapeCanvas and MyShape specified
	 * @param c   the ShapeCanvas the shape is deleted from
	 * @param s   the MyShape that is deleted
	 */
	public DeleteEdit(ShapeCanvas c, MyShape s) 
	{
		super(c, s);
	}
	
	
	/**
	 * deletes the MyShape from the canvas 
	 */
	@Override
	public void redo()
	{
		canvas.delete(shape);
		canvas.paint();
	}
	
	/**
	 * adds the MyShape to the canvas 
	 */
	@Override
	public void undo()
	{
		canvas.addShape(shape);
		canvas.paint();
	}

}
