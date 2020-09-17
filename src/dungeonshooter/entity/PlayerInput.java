package dungeonshooter.entity;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import utility.InputAdapter;

public class PlayerInput{
	private double x;
	private double y; 
	private boolean left = false;
	private boolean right = false;
	private boolean up = false;
	private boolean down = false;
	private boolean leftClick = false;
	private boolean rightClick = false;
	private boolean middleClick = false;
	private boolean space = false;
	private boolean shift = false;
	private InputAdapter adapter;
	
	
	public PlayerInput(InputAdapter adapter) {
		this.adapter=adapter;
		//adapter.forceFocusWhenMouseEnters();
		adapter.forceFocusWhenMouseEnters();
		adapter.registerMouseMovment(this::moved,this:: dragged);
		adapter.registerMouseClick(this::mousePressed,this:: mouseReleased);
		adapter.registerKey(this::keyPressed,this::keyReleased);
	}

	public boolean hasMoved() {
		return up || down || left || right;
	}

	
	public int leftOrRight() {
		if (!right && !left) {
			return 0;		
		}
		if(right) {
			return 1;	
		}
		return -1;
	}

	public int upOrDown() {
		if (!up && !down) {
			return 0;
		}
		if(up) {
			return -1;
		}
		return 1;		
	}

	public boolean leftClicked() {
		return leftClick;
	}

	public boolean rightClicked() {
		return rightClick;
	}

	public boolean middleClicked() {
		return middleClick;
	}

	public double x() {
		return x;
	}

	public double y() {
		return y;
	}

	protected void mousePressed(MouseEvent e) {
		this.x=e.getX();
		this.y=e.getY();
		leftClick=e.isPrimaryButtonDown();
		rightClick = e.isSecondaryButtonDown();
		middleClick = e.isMiddleButtonDown();		
	}

	protected void mouseReleased(MouseEvent e) {	
		leftClick=false;
		rightClick=false;	
		middleClick = false;		
	}
	
	public void changeKeyStatus(KeyCode key, boolean isPressed) {
		switch(key) {					
		case W:	        
			up = isPressed;
			break;
		case A:
			left = isPressed;
			break;
		case S:
			down = isPressed;
			break;
		case D:
			right = isPressed;
			break;
		case SHIFT:
			shift = isPressed;
			break;
		case SPACE:
			space = isPressed;
			break;
		default:
			break;
		}
	}

	protected void keyPressed(KeyEvent key) {	
		changeKeyStatus(key.getCode(), true);	
	}

	protected void keyReleased(KeyEvent key) {	
		changeKeyStatus(key.getCode(), false);		
	}

	public boolean isSpace() {
		return space;
	}

	public boolean isShift() {
		return shift;
	}

	protected void moved(MouseEvent e) {
		this.x=e.getX();
		this.y=e.getY();
	}

	protected void dragged(MouseEvent e) {
		this.x=e.getX();
		this.y=e.getY();
	}
		
}
