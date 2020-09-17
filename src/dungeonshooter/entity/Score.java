package dungeonshooter.entity;

import dungeonshooter.entity.property.HitBox;
import dungeonshooter.entity.property.Sprite;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import utility.Point;

public class Score implements Entity{
	
	public static final double ONE_SECOND = 1000000000L;
	public static final double HALF_SECONG = ONE_SECOND / 2F;
	private Sprite sprite;
	private static int score = 10;
	private Font font;
	private String displayScore;
	private double lastTime;
	private int frameCount;
	private Point pos;

	
	public Score (double x, double y) {
		sprite = new Sprite() {
			@Override
			public void draw(GraphicsContext gc) {
				Font fond = gc.getFont();
				gc.setFont(font);
				gc.setFill(getFill());
				gc.fillText(displayScore, pos.x(), pos.y());
				gc.setStroke(getStroke());
				gc.setLineWidth(getWidth());
				gc.strokeText(displayScore, pos.x(), pos.y());
				gc.setFont(fond);
			}
		};
		setPos(x, y);
		setFont(Font.font( Font.getDefault().getFamily(), FontWeight.BLACK, 20));	
	}
	
	public void calculateScore(long now) {
		if (now-lastTime > HALF_SECONG) {
			displayScore = String.valueOf(score=score+50);
			frameCount = 0;
			lastTime = now;		
		}
		frameCount++;
	}
	
	public Score setFont(Font font) {
		this.font = font;
		return this;		
	}

	public Score setPos(double x, double y) {
		this.pos = new Point( x,  y);	
		return this;
	}
	
	@Override
	public void update() {
	}
	
	@Override
	public boolean hasHitbox() {
		return false;
	}
	
	@Override
	public HitBox getHitBox() {
		return null;
	}	
	
	@Override
	public Sprite getDrawable() {
		return sprite;
	}
	
	@Override
	public boolean isDrawable() {
		return true;
	}
	
	
}
