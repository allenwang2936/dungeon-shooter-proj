package dungeonshooter.entity;
import dungeonshooter.entity.property.Drawable;
import dungeonshooter.entity.property.HitBox;


// an interface of entity object
public interface Entity {
	
	public void update();
	public boolean hasHitbox();
	public Drawable<?> getDrawable();
	public boolean isDrawable();
	public HitBox getHitBox();
	
	
}
