package dungeonshooter;


import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import dungeonshooter.animator.Animator;
import dungeonshooter.entity.Bullet;
import dungeonshooter.entity.Entity;
import dungeonshooter.entity.PolyShape;
import dungeonshooter.entity.property.HitBox;

public class CanvasMap{

	private Canvas map;
	private BooleanProperty drawBounds;
	private BooleanProperty drawFPS;
	private BooleanProperty drawScore;
	private List<Entity> players;
	private List<Entity> projectiles;
	private PolyShape border;
	private List<Entity> buffer;
	private Animator animator;
	private List<PolyShape> staticShapes;
	
	public CanvasMap() {
		
		buffer = new ArrayList<Entity>(500);
		projectiles = new ArrayList<Entity>(500);
		staticShapes = new ArrayList<PolyShape>(50);
		players = new ArrayList<Entity>(1);
		border = new PolyShape();
		border.getDrawable().setFill(new ImagePattern(new Image("file:assets/floor/pavin.png"),0,0,256,256,false));
		map = new Canvas();
		drawFPS = new SimpleBooleanProperty(false);		
		drawScore = new SimpleBooleanProperty(false);		
		drawBounds = new SimpleBooleanProperty(true);		
	}

	public BooleanProperty drawFPSProperty() {
		return drawFPS;
	}
	
	public boolean getDrawFPS() {
		return drawFPS.get();
	}
	
	public BooleanProperty drawBoundsProperty() {
		return drawBounds;
	}
	
	public BooleanProperty drawScoreProperty() {
		return drawScore;	
	}
	
	public boolean getDrawScore() {
		return drawScore.get();		
	}
	
	public boolean getDrawBounds() {
		return drawBounds.get();
	}
	
	public CanvasMap setDrawingCanvas(Canvas map) {

		if( map == null) {
			throw new NullPointerException();
		}
		//add listener
		this.map = map;
		this.map.widthProperty().addListener((v, e, w)-> border.setPoints( 0,0, w(),0, w(),h(), 0,h()));	
		this.map.heightProperty().addListener((v, e, w)-> border.setPoints( 0,0, w(),0, w(),h(), 0,h()));		
		return this;
	}
	
	public CanvasMap setAnimator(Animator newAnimator) {
		if (animator!=null) {
			stop();
		}
		animator = newAnimator;
		return this;
	}
	
	public void start() {
		 animator.start();
	}

	public void stop() {
		animator.stop();
	}
	
	public Canvas getCanvas() {
		return map;
	}

	public GraphicsContext gc() {
		return map.getGraphicsContext2D();
	}

	public double h() {
		return map.getHeight();
	}

	public double w() {
		return map.getWidth();
	}
	
	public List<PolyShape> staticShapes(){
		return staticShapes;
	}
	
	public List<Entity> players(){
		return players;
	}
	
	public List<Entity> projectiles(){
		return projectiles;
	}
	
	public CanvasMap addSampleShapes() {
		staticShapes.add(new PolyShape().randomize(150, 510, 90, 5, 25));
		staticShapes.add(new PolyShape().randomize(150, 300, 150, 5, 50));
		staticShapes.add(new PolyShape().randomize(350, 100, 100, 5, 50));
		staticShapes.add(new PolyShape().randomize(620, 100, 150, 5, 30));
		staticShapes.add(new PolyShape().randomize(620, 300, 100, 5, 50));
		staticShapes.add(new PolyShape().randomize(620, 500, 100, 5, 50));
		staticShapes.add(new PolyShape().randomize(350, 500, 100, 5, 30));
		return this;
	}
	
	public void fireBullet(Bullet bullet) {
		buffer.add(bullet);
	}
	
	public void updateProjectilesList() {
		projectiles.addAll( buffer);
		buffer.clear();
	}
	
	public PolyShape getMapShape() {
		return border;
	}
	
	public boolean inMap(HitBox hitbox) {
		if (border.getHitBox().containsBounds(hitbox)) {
			return true;
		}else {
			return false;
		}
	}
}