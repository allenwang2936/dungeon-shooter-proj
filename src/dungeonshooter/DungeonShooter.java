package dungeonshooter;

import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.ToolBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import utility.InputAdapter;
import dungeonshooter.CanvasMap;
import dungeonshooter.animator.Animator;
import dungeonshooter.entity.Player;
import dungeonshooter.entity.PlayerInput;


public class DungeonShooter extends Application{
	
	private double width = 700;
	private double height = 700;
	private Canvas canvas;	
	private BorderPane root;
	private CanvasMap board;
	private PlayerInput input;	
	
	
	@Override
	public void init() throws Exception{
		
		canvas = new Canvas();	
		InputAdapter ia = new InputAdapter(canvas);
		input = new PlayerInput(ia);
		board = new CanvasMap();
		Player player = new Player(width/2,height/2,70,46);
		player.setInput(input);
		player.setMap(board);
		Animator animator = new Animator();
		animator.setCanvas(board);
		board.setDrawingCanvas(canvas);
		board.setAnimator(animator);
		ObservableList<Animator> animators;
		animators = FXCollections.observableArrayList(animator);
		board.addSampleShapes();			
		board.players().add(player);
		
		ToolBar statusBar = createStatusBar();
		ToolBar optionsBar = createOptionsBar();			
		root = new BorderPane();
		root.setTop(optionsBar);
		root.setCenter(board.getCanvas());
		root.setBottom(statusBar);		
		board.getCanvas().widthProperty().bind( root.widthProperty());
		board.getCanvas().heightProperty().bind( root.heightProperty().subtract( statusBar.heightProperty())
			 .subtract( optionsBar.heightProperty()));
		animators.forEach( a -> a.setCanvas( board));
	}

	public void start(Stage primaryStage) {
		Color background = Color.CYAN;
		Scene scene = new Scene( root, width, height, background);
		primaryStage.setScene( scene);
		String title = "Dungeon Shooter";
		primaryStage.setTitle( title);
		primaryStage.addEventHandler( KeyEvent.KEY_RELEASED, ( KeyEvent event) -> {
			if( KeyCode.ESCAPE == event.getCode()){
				primaryStage.hide();
			}
		});
		primaryStage.show();
		board.start();
	}
	
	@Override
	public void stop() throws Exception{
		board.stop();
	}

	public ToolBar createOptionsBar(){
		Button startButton = createButton( "Start", a -> board.start());
		Button stopButton = createButton( "Stop", a -> board.stop());
		Pane menuBarFiller1 = new Pane();
		Pane menuBarFiller2 = new Pane();
		HBox.setHgrow( menuBarFiller1, Priority.ALWAYS);
		HBox.setHgrow( menuBarFiller2, Priority.ALWAYS);
		Spinner< Integer> rayCount = new Spinner<>( 0, Integer.MAX_VALUE, 360 * 5);
		rayCount.setEditable( true);
		rayCount.setMaxWidth( 100);
		//create FPScount, score and bounds checkBox for the menu
		MenuButton options = new MenuButton( "Options", null,
							    createCheckBox( "FPS", true, board.drawFPSProperty()),
							    createCheckBox( "Score", true, board.drawScoreProperty()),
							    createCheckBox( "Bounds", false, board.drawBoundsProperty()));
		return new ToolBar(startButton, stopButton,menuBarFiller1,rayCount, options,menuBarFiller2,new Label( "Animators"));
	}

	public ToolBar createStatusBar() {
		Label mouseCoordLabel = new Label( "(0,0)");
		Label dragCoordLabel = new Label( "(0,0)");
	    InputAdapter ia = new InputAdapter(canvas);
	    ia.addEventHandler( MouseEvent.MOUSE_MOVED, e -> mouseCoordLabel.setText( "(" + e.getX() + "," + e.getY() + ")"));
		ia.addEventHandler( MouseEvent.MOUSE_DRAGGED, e -> dragCoordLabel.setText( "(" + e.getX() + "," + e.getY() + ")"));
	    return new ToolBar(new Label( "Mouse: "), mouseCoordLabel,new Label( "Drag: "), dragCoordLabel);
	}

	public CheckMenuItem  createCheckBox(String text, boolean isSelected, BooleanProperty binding) {
		CheckMenuItem check = new CheckMenuItem( text);
		binding.bind( check.selectedProperty());
		check.setSelected( isSelected);
		return check;
	}

	public Button createButton(String text, EventHandler<ActionEvent> action) {
		Button button = new Button( text);
		button.setOnAction(action);
		return button;
	}
	
	public static void main( String[] args){
		// launch( args); method starts the javaFX application.
		// some IDEs are capable of starting JavaFX without this method.
		launch( args);
	}
}
