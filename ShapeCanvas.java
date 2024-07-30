import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * this class specifies the canvas where MyShape objects will be drawn
 */
public class ShapeCanvas extends Canvas{
	
	private GraphicsContext gc;
	private ArrayList<MyShape> shapes;
	private MyShape currShape;
	private Color currColor = Color.BLACK;
	private boolean currFilled = false;
	private double w, h;
	private Stack stackUndo;
	private Stack stackRedo;
	
	/**
	 * creates a new canvas with its width and height specified. MyShape objects will
	 * be drawn on this canvas
	 * @param width    the width of the canvas
	 * @param height   the height of the canvas
	 */
	public ShapeCanvas( double width, double height ) 
	{
		super(width, height);
		
		w = width;
		h = height;
		
		shapes = new ArrayList<MyShape>();
		
		gc = super.getGraphicsContext2D();
		
		stackUndo = new Stack();
		stackRedo = new Stack();
		
		//System.out.println("I'm a shape canvas");
	}
	
	/**
	 * clears the canvas and draws every non-null shape to the canvas including the current shape
	 */
	public void paint()
	{
		gc.clearRect(0, 0, w, h);
		
		for ( MyShape shape : shapes )
		{
			shape.draw(gc);
		}
		
		if(currShape != null)
		{
			currShape.draw(gc);
		}
		
	}
	
	/**
	 * adds a MyShape object the the ArrayList of shapes belonging to the canvas
	 * @param s   a MyShape object that is added to the ArrayList of shapes belonging to the canvas
	 */
	public void addShape( MyShape s )
	{
		shapes.add(s);
	}
	
	/**
	 * sets the currently-being-editied shape to the MyShape object s
	 * @param s   the MyShape object that the currently-being-editied shape will be assigned to
	 */
	public void setCurrentShape( MyShape s )
	{
		currShape = s;
		
		if(currShape != null)
		{
			currShape.setColor(currColor);
			currShape.setFilled(currFilled);
		}
		
	}
	
	/**
	 * empties the list of shapes held by the canvas and paints the canvas so it is blank
	 */
	public void clear()
	{
		shapes.clear();
		stackUndo.clear();
		stackRedo.clear(); //new additions
		paint();
	}
	
	/**
	 * sets the current color of the GraphicsContext to a new Color
	 * @param newColor   the Color the GraphicsContext will be assigned 
	 */
	public void setColor( Color newColor )
	{
		currColor = newColor;
	}
	
	/**
	 * gets the current color of the canvas
	 */
	public Color getColor()
	{
		return currColor;
	}
	
	/**
	 * changes whether the current shape on the canvas will be filled or not
	 */
	public void changeFill()
	{
		currFilled = !(currFilled);
	}
	
	/**
	 * replaces the current mouse listener (press/release) and mouse motion listener (drag)
	 * with the passed listener object
	 *
	 * @param listener an EventHandler object 
	 */
	public void replaceMouseHandler(EventHandler listener) {
	    setOnMousePressed(listener);
	    setOnMouseDragged(listener);
	    setOnMouseReleased(listener);
	    setOnMouseClicked(listener);
	}
	
	/**
	 * converts strokes on the canvas into text in a text file. first writes the number of strokes, then writes the 
	 * stroke as a string to a text file. if the text file cannot be created/ opened, throws FileNotFoundException
	 * @param   fileObj   the file the converted canvas is written to  
	 */
	public void toTextFile(File fileObj)
	{
		try 
		{
			PrintWriter fOut = new PrintWriter(fileObj);
			
			fOut.println(shapes.size());
			
			for(MyShape shape : shapes)
			{
				fOut.println(shape.toString());
			}
			
			fOut.close();
		}
		catch(FileNotFoundException e)
		{
			
		}
	}
	
	public void fromTextFile(File fileObj)
	{
		try
		{
			Scanner fIn = new Scanner(fileObj);
			
			clear();
			
			int nShapes = fIn.nextInt();
			
			for(int i = 0; i < nShapes; i++)
			{
				String type = fIn.next();
				
				Point2D p1 = new Point2D(fIn.nextInt(), fIn.nextInt());
				Point2D p2 = new Point2D(fIn.nextInt(), fIn.nextInt());
				
				double r = fIn.nextDouble();
				double g = fIn.nextDouble();
				double b = fIn.nextDouble();
				
				Color col = Color.color(r, g, b);
				
				boolean isFilled = fIn.nextBoolean();
				
				MyShape shape = null;
				
				if(type.equals("rect"))
				{
					shape = new Rect(p1, p2);
				}
				else if(type.equals("oval"))
				{
					shape = new Oval(p1, p2);
				}
				else
				{
					shape = new Line(p1, p2);
				}
				
				shape.setColor(col);
				shape.setFilled(isFilled);
				shape.updateBounds();
				shape.updateCenter();
				
				shapes.add(shape);
			}
			
			fIn.close();
			paint();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
//	public void toTextFile(File fileObj)
//	{
//		try
//		{
//			PrintWriter fileOut = new PrintWriter(fileObj);
//			
//			fileOut.println(shapes.size());
//			
//			for(MyShape s : shapes)
//			{
//				fileOut.println(s.toString());
//			}
//			
//			fileOut.close();
//		}
//		catch(FileNotFoundException e)
//		{
//			System.out.println("file could not be created or opened for writing");
//			e.printStackTrace();  //prints the long trace of errors and where they came from
//		}
//	}
	
	/**
	 * reads a text file containing strokes as strings. first clears the canvas, then reads the strokes from a text file
	 * and draws them on the canvas
	 * @param fileObj   the file that the strokes are read off of 
	 */
//	public void fromTextFile(File fileObj)
//	{
//		try
//		{
//			Scanner fileIn = new Scanner(fileObj);
//			
//			clear();
//			
//			int nMyShapes = fileIn.nextInt();
//			
//			for(int i = 0; i < nMyShapes; i++)
//			{
//				String type = fileIn.next();
//				
//				if(type.equals("ShapeGroup"))
//				{
//					shapes.add(loadGroup(fileIn));
//				}
//				else
//				{
//					shapes.add(loadSingletonText(fileIn, type));
//				}
//			}
//			
//			fileIn.close();
//			
//			paint();
//		}
//		catch(FileNotFoundException e)
//		{
//			System.out.println("file could not be opened for reading");
//			e.printStackTrace();  //prints the long trace of errors and where they came from 
//		}
//	}
	
//	public void fromTextFile(File fileObj)
//	{
//		try
//		{
//			Scanner fileIn = new Scanner(fileObj);
//			
//			clear();
//			
//			int nMyShapes = fileIn.nextInt();
//			
//			for(int i = 0; i < nMyShapes; i++)
//			{
//				String type = fileIn.next();
//				
//				double p1x = fileIn.nextDouble();
//				double p1y = fileIn.nextDouble();
//				
//				double p2x = fileIn.nextDouble();
//				double p2y = fileIn.nextDouble();
//				
//				double r = fileIn.nextDouble();
//				double g = fileIn.nextDouble();
//				double b = fileIn.nextDouble();
//				Color col = Color.color(r, g, b);
//				
//				Boolean filled = fileIn.nextBoolean();
//				
//				MyShape shape = null;
//				
//				if(type.equals("line"))
//				{
//					shape = new Line(p1x, p1y, p2x, p2y);  //bounds and center updated in constructor 
//				}
//				else if(type.equals("rect"))
//				{
//					shape = new Rect(p1x, p1y, p2x, p2y);
//				}
//				else 
//				{
//					shape = new Oval(p1x, p1y, p2x, p2y);
//				}
//				
//				shape.setColor(col);
//				shape.setFilled(filled);
//				
//				shapes.add(shape);
//			}
//			
//			fileIn.close();
//			
//			paint();
//		}
//		catch(FileNotFoundException e)
//		{
//			System.out.println("file could not be opened for reading");
//			e.printStackTrace();  //prints the long trace of errors and where they came from 
//		}
//	}
	
	/**
	 * converts strokes on the canvas to elements in binary file. for all the strokes on the canvas, writes their 
	 * information to binary file. if the strokes are unable to be written to a binary file, throws IOException
	 * @param fileObj   the file the strokes will be written to in binary
	 */
	
	
	public void toBinaryFile(File fileObj)
	{
		try
		{
			FileOutputStream fOS = new FileOutputStream(fileObj);
			ObjectOutputStream fOut = new ObjectOutputStream(fOS);
			
			fOut.writeInt(shapes.size());
			
			for(MyShape shape: shapes)
			{
				fOut.writeObject(shape);
			}
			
			fOut.close();
			fOS.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	
	public void fromBinaryFile(File fileObj)
	{
		try
		{
			FileInputStream fIS = new FileInputStream(fileObj);
			ObjectInputStream fIn = new ObjectInputStream(fIS);
			
			int nShapes = fIn.readInt();
			
			clear();
			
			for(int i = 0; i < nShapes; i++)
			{
				MyShape currShape = (MyShape) fIn.readObject();
				
				shapes.add(currShape);
			}
			
			fIn.close();
			fIS.close();
			
			paint();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	public void toBinaryFile(File fileObj)
//	{
//		try 
//		{
//			FileOutputStream fOS = new FileOutputStream(fileObj);
//			ObjectOutputStream fOut = new ObjectOutputStream(fOS);
//			
//			fOut.writeInt(shapes.size());
//			
//			for(MyShape shape : shapes)
//			{
//				fOut.writeObject(shape);
//			}
//			
//			fOut.close();
//			fOS.close();
//		}
//		catch(IOException e)
//		{
//			e.printStackTrace();
//		}
//	}
	
	/**
	 * reads the strokes information from a binary file. first clears the canvas, then gets each stroke mentioned in the file and adds it to 
	 * the list of shapes in the canvas. finally the file is closed and the shapes are painted onto the canvas. if the strokes cannot be read from the file
	 * IOException is thrown, if a class cannot be found ClassNotFoundException is thrown.
	 * @param fileObj   the binary file the strokes will be read from 
	 */
//	public void fromBinaryFile(File fileObj)
//	{
//		try
//		{
//			FileInputStream fIS = new FileInputStream(fileObj);
//			ObjectInputStream fIn = new ObjectInputStream(fIS);
//			
//			clear();
//			
//			int n = fIn.readInt();
//			
//			for(int i = 0; i < n; i++)
//			{
//				MyShape shape = (MyShape) fIn.readObject();
//				
//				shapes.add(shape);
//			}
//			
//			fIn.close();
//			fIS.close();
//			
//			paint();
//		}
//		catch(IOException e)
//		{
//			e.printStackTrace();
//		}
//		catch(ClassNotFoundException e2)
//		{
//			e2.printStackTrace();
//		}
//	}
	
	/**
	 * returns the shape whose center is closest to the point with coordinates (x, y)
	 * @param x   the x-coordinate of the point 
	 * @param y   the y-coordinate of the point
	 * @return   the shape whose center is closest to the point with coordinates (x, y)
	 */
	public MyShape closestShape(double x, double y)
	{
		double smallDistance = Double.MAX_VALUE;
		MyShape closeShape = null;
		
		for(MyShape s : shapes)
		{
			double currDistance = s.distance(x, y);
			
			if( currDistance < smallDistance )
			{
				smallDistance = currDistance;
				closeShape = s;
			}
		}
		
		return closeShape;
	}
	
	/**
	 * removes a MyShape s from the canvas
	 * @param s   the shape being removed from the canvas
	 */
	public void delete(MyShape s)
	{
		shapes.remove(s);
	}
	
	/**
	 * gets the shapes that are on this ShapeCanvas
	 * @return   the shapes that are on this ShapeCanvas
	 */
	public ArrayList<MyShape> getShapes()
	{
		return shapes;
	}
	
	/**
	 * loads a single MyShape from a text file and returns it 
	 * @param fileIn   the file the MyShape is loaded from
	 * @param shapeType   the type of MyShape that is loaded
	 * @return   a single MyShape from a text file
	 */
	public MyShape loadSingletonText(Scanner fileIn, String shapeType)
	{	
		double p1x = fileIn.nextDouble();
		double p1y = fileIn.nextDouble();
				
		double p2x = fileIn.nextDouble();
		double p2y = fileIn.nextDouble();
				
		double r = fileIn.nextDouble();
		double g = fileIn.nextDouble();
		double b = fileIn.nextDouble();
		Color col = Color.color(r, g, b);
				
		Boolean filled = fileIn.nextBoolean();
				
		MyShape shape = null;
				
		if(shapeType.equals("line"))
		{
			shape = new Line(p1x, p1y, p2x, p2y);  //bounds and center updated in constructor 
		}
		else if(shapeType.equals("rect"))
		{
			shape = new Rect(p1x, p1y, p2x, p2y);
		}
		else 
		{
			shape = new Oval(p1x, p1y, p2x, p2y);
		}
				
		shape.setColor(col);
		shape.setFilled(filled);
				
		return shape;
	}
	
	/**
	 * loads a single ShapeGroup from a text file and returns it 
	 * @param fIn   the text file the ShapeGroup will be loaded from
	 * @return   the ShapeGroup loaded from the text file
	 */
	public ShapeGroup loadGroup(Scanner fIn)
	{
		int nMembers = fIn.nextInt();
		
		ShapeGroup currSG = new ShapeGroup();
		currSG.setP1(fIn.nextDouble(), fIn.nextDouble());
		currSG.setP2(fIn.nextDouble(), fIn.nextDouble()); //bounds and center updated here
		
		for(int i = 0; i < nMembers; i++)
		{
			String type = fIn.next();
			
			if(type.equals("ShapeGroup"))
			{
				currSG.addMember(loadGroup(fIn));
			}
			else
			{
				currSG.addMember(loadSingletonText(fIn, type));
			}
		}
		
		return currSG;
	}
	
	
	
	//hw10
	
	
	/**
	 * adds edit to the stack of edits to undo 
	 * @param edit   the edit that is added to the stack of edits to undo
	 */
	public void addEdit(Edit edit)
	{
		stackUndo.push(edit);
	}
	
	/**
	 * the latest edit from the stack of edits to undo is undone, and it is added to the stack of edits to redo
	 */
	public void undo()
	{
		if(!stackUndo.isEmpty())
		{
			Edit currEdit = (Edit) stackUndo.pop();
			currEdit.undo();
			stackRedo.push(currEdit);
		}
		
		//System.out.println(stackUndo);
	}
	
	/**
	 * the latest edit from the stack of edits to redo is redone, and the edit is added to the stack of edits to undo
	 */
	public void redo()
	{
		if(!stackRedo.isEmpty())
		{
			Edit currEdit = (Edit) stackRedo.pop();
			currEdit.redo();
			stackUndo.push(currEdit);
		}
		
		//System.out.println(stackUndo);
	}
}
