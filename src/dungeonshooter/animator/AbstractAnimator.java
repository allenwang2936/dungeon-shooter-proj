package dungeonshooter.animator;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.function.Consumer;
import dungeonshooter.CanvasMap;
import dungeonshooter.entity.Entity;
import dungeonshooter.entity.FpsCounter;
import dungeonshooter.entity.PolyShape;
import dungeonshooter.entity.Score;
import utility.Point;

public abstract class AbstractAnimator extends AnimationTimer{
	
	protected CanvasMap map;
	protected Point mouse;
	private FpsCounter fps;
	private Score score;
	
	

	public AbstractAnimator() {
		mouse = new Point();
		fps = new FpsCounter(50, 50);
		fps.getDrawable().setFill(Color.LIGHTCORAL).setStroke(Color.DARKSLATEGREY).setWidth(2);
	    score =  new Score(15,15);
	    score.getDrawable().setFill(Color.SILVER).setStroke(Color.DARKMAGENTA).setWidth(2);
		
	}

	public void setCanvas(CanvasMap map) {
		this.map = map;
	}
	
	public void clearAndFill(GraphicsContext gc, Color background) {
		gc.setFill(background);
		gc.clearRect(0, 0, map.w(), map.h());
		gc.fillRect(0, 0, map.w(), map.h());
	}
	
	public void drawEntities(GraphicsContext gc) {
		Consumer<Entity> draw = (Entity e)->{
			if (e.isDrawable()) {
				e.getDrawable().draw(gc);
				if (map.getDrawBounds()) {
					e.getHitBox().getDrawable().draw(gc);
				}
			}
		};
	
		draw.accept(map.getMapShape());
		for(PolyShape e : map.staticShapes()) {
			draw.accept(e);
		}
		for(Entity e : map.projectiles()) {
			draw.accept(e);
		}
		for(Entity e : map.players()) {
			draw.accept(e);
		}
	}
	

	public void handle(long now) {
		GraphicsContext gc = map.gc();
		if (map.getDrawFPS()) { 
			fps.calculateFPS(now);
		}
		
		if(map.getDrawScore()) {
			score.calculateScore(now);
		}
		
		handle(gc, now);	
		if (map.getDrawFPS()) {
			fps.getDrawable().draw(gc);
		}
		
		if(map.getDrawScore()) {
			score.getDrawable().draw(gc);
		}
	}

	protected abstract void handle(GraphicsContext gc, long now);

}
