import java.io.File;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * creates an Application where we can draw Lines, Ovals, and Rects
 */
public class ShapeEditor extends Application{

	//constants
	
	private static final double APP_WIDTH = 1000;
	private static final double APP_HEIGHT = 800;
	private static final double CONTROL_HEIGHT = 40;
	private static final double CANVAS_HEIGHT = APP_HEIGHT - CONTROL_HEIGHT;
	
	//gui variables
	
	private BorderPane mainPane;
	private ColorPicker colorPicker;
	private ShapeCanvas canvas;
	private HBox controlPanel;
	private Button bnClear;
	private CheckBox cbFilled;
	private RadioButton rbLine, rbOval, rbRect, rbDelete, rbMove, rbCopy, rbGroup;
	private LineHandler lineHandler;
	private OvalHandler ovalHandler;
	private RectHandler rectHandler;
	private DeleteHandler deleteHandler;
	private MoveHandler moveHandler;
	private CopyHandler copyHandler;
	private GroupHandler groupHandler;
	private MenuBar menuBar;
	private Menu menuFile;
	private Menu menuAbout;
	private MenuItem miSave;
	private MenuItem miOpen;
	private FileChooser fc;
	private MenuItem miSaveB;
	private MenuItem miOpenB;
	private Button bnUndo, bnRedo;
	
	/**
	 * sets up the canvas and controls. the canvas and controls are then added to a BorderPane 
	 * that is made into a scene that is set on the stage
	 * @param stage   the stage where our Canvas and Controls will be placed
	 */
	@Override
	public void start( Stage stage )
	{
		mainPane = new BorderPane();
		setupCanvas();
		setupControls();
		setupMenu();
		
		Scene scene = new Scene( mainPane, APP_WIDTH, APP_HEIGHT );
		stage.setScene(scene);
		stage.setTitle("CS112 Shape Editor");
		stage.show();
	}
	
	/**
	 * creates the canvas and sets it to the center of the mainPane. the canvas is then painted
	 */
	public void setupCanvas()
	{
		canvas = new ShapeCanvas(APP_WIDTH, CANVAS_HEIGHT);
		
		mainPane.setCenter(canvas);
		canvas.paint();
	}
	
	/**
	 * sets up the buttons, checkbox, and action listeners and adds them to the controlPanel. the 
	 * MyShape handlers are also set up. the controlPanel is then set the the top of the mainPane
	 */
	public void setupControls()
	{
		controlPanel = new HBox();
		
		controlPanel.setPrefHeight(CONTROL_HEIGHT);
		controlPanel.setMaxHeight(CONTROL_HEIGHT);
		controlPanel.setMinHeight(CONTROL_HEIGHT);
		
		//setting up the ColorPicker
		colorPicker = new ColorPicker(Color.BLACK); //by default set to whatever the canvas's color is at first
	
		//setting up ColorPicker action handler
		colorPicker.setOnAction(e->{
			Color pickedColor = colorPicker.getValue();
			canvas.setColor(pickedColor);
		});
		
		//setting up shape action handlers
		lineHandler = new LineHandler(canvas);
		ovalHandler = new OvalHandler(canvas);
		rectHandler = new RectHandler(canvas);
		deleteHandler = new DeleteHandler(canvas);
		moveHandler = new MoveHandler(canvas);
		copyHandler = new CopyHandler(canvas);
		groupHandler = new GroupHandler(canvas);
		
		//setting up boring buttons 
		bnClear = new Button("clear");
		cbFilled = new CheckBox("isFilled");
		
		//setting up radio buttons
		rbLine = new RadioButton("draw Line");
		rbOval = new RadioButton("draw Oval");
		rbRect = new RadioButton("draw Rect");
		rbDelete = new RadioButton("delete");
		rbMove = new RadioButton("move");
		rbCopy = new RadioButton("copy");
		rbGroup = new RadioButton("group");
		
		//by default lines are drawn
		rbLine.setSelected(true);
		canvas.replaceMouseHandler(lineHandler);
		
		//setting up togglegroup
		ToggleGroup group = new ToggleGroup();
		rbLine.setToggleGroup(group);
		rbOval.setToggleGroup(group);
		rbRect.setToggleGroup(group);
		rbDelete.setToggleGroup(group);
		rbMove.setToggleGroup(group);
		rbCopy.setToggleGroup(group);
		rbGroup.setToggleGroup(group);
		
		//action listeners
		bnClear.setOnAction(
			new EventHandler<ActionEvent>()
			{
				@Override
				public void handle(ActionEvent e)
				{
					canvas.clear();
				}
			}
		);
		
		cbFilled.setOnAction(e->{
			canvas.changeFill();
			//System.out.println("Changed fill" + canvas.currFilled);
		});
		
		
		rbOval.setOnAction(e->{
			//System.out.println("OVAL!");
			canvas.replaceMouseHandler(ovalHandler);
		});
		
		
		rbLine.setOnAction(e->{
			//System.out.println("Finally line button!");
			canvas.replaceMouseHandler(lineHandler);
		});
		
		
		rbRect.setOnAction(e->{
			//System.out.println("RECT!");
			canvas.replaceMouseHandler(rectHandler);
		});
		
		rbDelete.setOnAction(e->{
			canvas.replaceMouseHandler(deleteHandler);
		});
		
		rbMove.setOnAction(e->{
			canvas.replaceMouseHandler(moveHandler);
		});
		
		rbCopy.setOnAction(e->{
			canvas.replaceMouseHandler(copyHandler);
		});
		
		rbGroup.setOnAction(e->{
			canvas.replaceMouseHandler(groupHandler);
		});
		
		bnUndo = new Button("undo");
		bnRedo = new Button("redo");
		
		bnUndo.setOnAction(e->{
			canvas.undo();
		});
		
		bnRedo.setOnAction(e->{
			canvas.redo();
		});
		
		controlPanel.getChildren().addAll(bnClear, cbFilled, rbLine, rbOval, rbRect, rbDelete, rbMove, rbCopy, rbGroup, colorPicker, bnUndo, bnRedo);
		controlPanel.setSpacing(10);
		mainPane.setTop(controlPanel);
	}
	
	/**
	 * sets up the MenuBar where a File and About menu will be placed. MenuItems are then set up for 
	 * reading and writing text and binary files 
	 */
	public void setupMenu()
	{
		menuBar = new MenuBar();
		
		menuFile = new Menu("File");
		menuAbout = new Menu("About");
		
		miSave = new MenuItem("save");
		miOpen = new MenuItem("open");
		miSaveB = new MenuItem("save binary");
		miOpenB = new MenuItem("open binary");
		
		fc = new FileChooser();
		
		miSave.setOnAction(
			new EventHandler<ActionEvent>()
			{
				@Override
				public void handle(ActionEvent e)
				{
					File fOut = fc.showSaveDialog(null);
					
					if(fOut != null)
					{
						canvas.toBinaryFile(fOut);
					}
				}
			}
		);
		
		miOpen.setOnAction(e->{
			File fIn = fc.showOpenDialog(null);
			
			if(fIn != null)
			{
				canvas.toTextFile(fIn);
			}
		});
		
		miOpenB.setOnAction(e->{
			File fIn = fc.showOpenDialog(null);
			
			if(fIn != null)
			{
				canvas.fromBinaryFile(fIn);
			}
		});
		
		class handler implements EventHandler<ActionEvent>
		{
			@Override
			public void handle(ActionEvent e)
			{
				File fOut = fc.showSaveDialog(null);
				
				if(fOut != null)
				{
					canvas.toBinaryFile(fOut);
				}
			}
		}
		
		miSaveB.setOnAction( new handler() );
		
		
		menuFile.getItems().addAll(miOpen, miSave, miSaveB, miOpenB);
		
		menuBar.getMenus().addAll(menuFile, menuAbout);
		
		mainPane.setLeft(menuBar);
		
		
		
		
		
//		menuBar = new MenuBar();
//		
//		menuFile = new Menu("File");
//		menuAbout = new Menu("About");
//		
//		miSave = new MenuItem("save");
//		miOpen = new MenuItem("open");
//		miSaveB = new MenuItem("save binary");
//		miOpenB = new MenuItem("open binary");
//		
//		//action handler for miOpen
//		
//		fc = new FileChooser();
//		
//		miSave.setOnAction(e->{
//			File fIn = fc.showSaveDialog(null);
//			fc.setTitle("Save as Text");
//			
//			if(fIn != null)
//			{
//				canvas.toTextFile(fIn);
//			}
//		});
//		
//		miOpen.setOnAction(e->{
//			File fOut = fc.showOpenDialog(null);
//			fc.setTitle("Open text file");
//			
//			if(fOut != null)
//			{
//				canvas.fromTextFile(fOut);
//			}
//		});
//		
//		miSaveB.setOnAction(e->{
//			
//			File savedFile = fc.showSaveDialog(null);
//			
//			if(savedFile != null)
//			{
//				canvas.toBinaryFile(savedFile);   //savedFile
//			}
//		});
//		
//		miOpenB.setOnAction(e->{
//			File selectedFile = fc.showOpenDialog(null);
//			
//			if(selectedFile != null)
//			{
//				canvas.fromBinaryFile(selectedFile);
//			}
//		});
//		
//		menuFile.getItems().addAll(miSave, miOpen, miSaveB, miOpenB);
//		menuBar.getMenus().addAll(menuFile, menuAbout);
//		mainPane.setLeft(menuBar);
//		
	}
	
	/**
	 * this is the main method where we launch args
	 * @param args   list of String arguments passed to the main method
	 */
	public static void main(String[] args)
	{
		launch(args);
	}

}
