
/**
 * this class creates edit objects that will allow us to undo and redo edits
 */

public abstract class Edit{ 
	
	protected MyShape shape;
	protected ShapeCanvas canvas;

	/**
	 * creates a new edit object with a ShapeCanvas and MyShape specified
	 * @param c   the ShapeCanvas the edit will be applied to
	 * @param s   the MyShape the edit corresponds to 
	 */
	public Edit(ShapeCanvas c, MyShape s)
	{
		shape = s;
		canvas = c;
	}
	
	/**
	 * lets the user undo edits
	 */
	public abstract void undo();
	
	/**
	 * lets the user redo edits 
	 */
	public abstract void redo();

}
