
/**
 * creates edits that allows a copy to be added and removed from the canvas 
 */

public class CopyEdit extends Edit{
	
	/**
	 * creates a new CopyEdit with a ShapeCanvas and MyShape specified
	 * @param c   the canvas the copy will be added and removed from
	 * @param s   the copy that will be added to and removed from the canvas
	 */
	public CopyEdit(ShapeCanvas c, MyShape s) 
	{
		super(c, s);
	}
	
	/**
	 * adds the copy to the canvas and paints the canvas
	 */
	@Override
	public void redo()
	{
		canvas.addShape(shape);
		canvas.paint();
	}
	
	/**
	 * removes the copy from the canvas and paints the canvas 
	 */
	@Override
	public void undo()
	{
		canvas.delete(shape);
		canvas.paint();
		
		//System.out.println("Got rid of a clone!");
	}

}
