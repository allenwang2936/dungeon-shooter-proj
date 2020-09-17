package dungeonshooter.entity;

import dungeonshooter.entity.property.HitBox;
import dungeonshooter.entity.property.Sprite;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import utility.Point;

public class FpsCounter implements Entity{
	
	public static final double ONE_SECOND = 1000000000L;
	public static final double HALF_SECONG = ONE_SECOND / 2F;
	private Font fpsFont;
	private String fpsDisplay;
	private int frameCount;
	private double lastTime;
	private Point pos;
	private Sprite sprite;
	
	
	public FpsCounter(double x, double y) {
		setPos(x, y);
		setFont(Font.font( Font.getDefault().getFamily(), FontWeight.BOLD, 20));
		
		sprite = new Sprite() {
			@Override
			public void draw(GraphicsContext gc) {
				Font fond = gc.getFont();
				gc.setFont(fpsFont);
				gc.setFill(getFill());
				gc.fillText(fpsDisplay, pos.x(), pos.y());
				gc.setStroke(getStroke());
				gc.setLineWidth(getWidth());
				gc.strokeText(fpsDisplay, pos.x(), pos.y());
				gc.setFont(fond);
			}
		};
	}
	
	public void calculateFPS(long now) {
		if (now-lastTime > HALF_SECONG) {
			fpsDisplay = String.valueOf(frameCount * 2);
			frameCount = 0;
			lastTime = now;		
		}
		frameCount++;
	}
	
	public FpsCounter setFont(Font font) {
		this.fpsFont = font;
		return this;		
	}

	public FpsCounter setPos(double x, double y) {
		this.pos = new Point( x, y);	
		return this;
	}
	
	//empty method
	@Override
	public void update() {
	}
	
	@Override
	public boolean hasHitbox() {
		return true;
	}
	
	@Override
	public HitBox getHitBox() {
		return null;
	}		
	
	@Override
	public boolean isDrawable() {
		return true;
	}		
	
	@Override
	public Sprite getDrawable() {
		return sprite;
	}
}
