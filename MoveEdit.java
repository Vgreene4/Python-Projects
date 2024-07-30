
/**
 * stores a edit with the amount a shape is moved by a move action
 */

public class MoveEdit extends Edit{
	private double dx;
	private double dy;

	/**
	 * creates a new MoveEdit with a ShapeCanvas, MyShape, and change in x, and change in y specified
	 * @param c    MyShape that is moved or unmoved by the edit 
	 * @param s    ShapeCanvas the MyShape is moved or unmoved on
	 * @param dx   amount the MyShape is moved or unmoved in the x-direction
	 * @param dy   amount the MyShape is moved or unmoved in the y-direction
	 */
	public MoveEdit(ShapeCanvas c, MyShape s, double dx, double dy) 
	{
		super(c, s);
		
		this.dx = dx;
		this.dy = dy;
	}
	
	/**
	 * moves the MyShape by dx in the x-direction and dy in the y-direction 
	 */
	@Override 
	public void redo()
	{
		shape.move(dx, dy);
		canvas.paint();
	}
	
	/**
	 * moves the MyShape by -dx in the x-direction and -dy in the y-direction
	 * undoes the movement of the MyShape 
	 */
	@Override 
	public void undo()
	{
		shape.move(-dx, -dy);
		canvas.paint();
		
		//System.out.println(shape.getCenter());
	}

}
