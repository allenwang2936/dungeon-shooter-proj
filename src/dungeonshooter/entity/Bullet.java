package dungeonshooter.entity;

import dungeonshooter.entity.geometry.RectangleBounds;
import dungeonshooter.entity.property.HitBox;
import dungeonshooter.entity.property.Sprite;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Bullet implements Entity{
	
	private static final Image BULLET = new Image("file:assets\\bullet\\b_1.png");
	private double angle;
	private Sprite sprite;
	private HitBox hitbox;
    
	//constructor1,chain to next constructor use 6 and 6 as width and height
	public Bullet (double angle, double x, double y) {
		this( angle, x, y, 6, 6);
	}
 
	//constructor2
	public  Bullet(double angle, double x, double y, double w, double h) {
		this.angle = angle;
		this.hitbox = new HitBox().setBounds(x, y, w, h);
		sprite = new Sprite(){
			private RectangleBounds bounds = hitbox.getBounds();
			public void draw( GraphicsContext gc){
				gc.drawImage( BULLET, bounds.x(), bounds.y(), bounds.w(), bounds.h());
			}
		};
	}

	@Override
	public void update() {
		double x = Math.cos( Math.toRadians(angle)) * 7 ;
		double y = Math.sin( Math.toRadians(angle)) * 7 ;
		hitbox.translate( x, y);
	}

	@Override
	public boolean isDrawable() {
		return true;
	}

	@Override
	public String toString() {
		return "Bullet";
	}

	@Override
	public boolean hasHitbox() {
		return true;
	}

	@Override
	public HitBox getHitBox() {
		return hitbox;
	}

	@Override
	public Sprite getDrawable() {
		return sprite;
	}
}
