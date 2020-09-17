package dungeonshooter.animator;

import java.util.Iterator;
import dungeonshooter.entity.Bullet;
import dungeonshooter.entity.Entity;
import dungeonshooter.entity.Player;
import dungeonshooter.entity.PolyShape;
import dungeonshooter.entity.property.HitBox;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Animator extends AbstractAnimator {
	
	private Color background = Color.ANTIQUEWHITE;

	@Override
	public void handle(GraphicsContext gc, long now) {
		updateEntities();
		clearAndFill(gc, background);
		drawEntities(gc);
	}

	public void updateEntities() {
		
		map.updateProjectilesList();
		map.players();
		map.projectiles();
		map.staticShapes();
	
		
		for (Entity bullet : map.projectiles()) {
			bullet .update();	
		}
		
		for (Entity player : map.players()) {
			player.update();
		}
		
		for (Entity shape : map.staticShapes()) {
			shape.update();
		}
		
		if (map.getDrawBounds()) {
			for (Entity bullet : map.projectiles()) {
				bullet.getHitBox().getDrawable().setStroke(Color.RED);
			}
			for (Entity player : map.players()) {
				player.getHitBox().getDrawable().setStroke(Color.RED);
			}
		}
		
		for (PolyShape shape : map.staticShapes()) {
			proccessEntityList(map.projectiles().iterator(), shape.getHitBox());
			proccessEntityList(map.players().iterator(), shape.getHitBox());
		}
	}

	public void proccessEntityList(Iterator<Entity> iterator, HitBox shapeHitBox) {

		while (iterator.hasNext()) {
			Entity entity = iterator.next();
			HitBox bounds = entity.getHitBox();
			
			if (!map.inMap(bounds)) {
				if (entity instanceof Player) {
					((Player) entity).stepBack();
				} else if (entity instanceof Bullet) {
					iterator.remove();
				}
			} else if (shapeHitBox.intersectBounds(bounds)) {
				if (map.getDrawBounds()) {
					shapeHitBox.getDrawable().setStroke(Color.DIMGRAY);
				}
				
				if (shapeHitBox.intersectFull(bounds)) {
					if (entity instanceof Player) {
						((Player) entity).stepBack();
					} else if (entity instanceof Bullet) {
						iterator.remove();
					}
				}
			}
		}
	}
}
