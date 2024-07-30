import java.util.ArrayList;

/**
 * creates an edit for groups that allows edits to be undone and redone
 */

public class GroupEdit extends Edit{

	/**
	 * creates a new GroupEdit with a ShapeCanvas and ShapeGroup specified
	 * @param c   the ShapeCanvas the group inhabits
	 * @param s   the ShapeGroup that is on the canvas
	 */
	public GroupEdit(ShapeCanvas c, ShapeGroup s)
	{
		super(c, s);
	}
	
	/**
	 * removes the ShapeGroup from the canvas and then adds back the members of the group to the canvas 
	 */
	@Override
	public void undo()
	{
		canvas.delete(shape);
		
		ArrayList<MyShape> members = ((ShapeGroup) shape).getMembers();
		
		for(MyShape member : members)
		{
			canvas.addShape(member);
		}
		canvas.paint();
	}
	
	/**
	 * deletes the members of the ShapeGroup from the canvas then adds the ShapeGroup to the canvas 
	 */
	@Override
	public void redo()
	{
		ArrayList<MyShape> members = ((ShapeGroup) shape).getMembers();
		
		for(MyShape member : members)
		{
			canvas.delete(member);
		}
		
		canvas.addShape(shape);
		canvas.paint();
	}

}
