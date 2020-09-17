package dungeonshooter.entity.property;

import dungeonshooter.entity.Entity;
import dungeonshooter.entity.geometry.RectangleBounds;
import dungeonshooter.entity.property.Sprite;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import utility.IntersectUtil;
import utility.Point;

public class HitBox implements Entity {
	
	private Point prev;
	private RectangleBounds bounds;
	private Sprite sprite;
	private double[][] points;
	private double[] result;
	
	//constructor 
	public HitBox() {	
		points = new double[2][4];		
		result = new double[4];		
		prev = new Point();
		
		sprite = new Sprite() {
			public void draw(GraphicsContext gc) {
				gc.setStroke(getStroke());
				gc.setLineWidth(getWidth());
				gc.strokeRect(bounds.x(), bounds.y(), bounds.w(), bounds.h());
			}
		};
		sprite.setStroke(Color.RED).setWidth(3);
	}
	
	public HitBox setBounds(RectangleBounds bounds) {
		this.bounds = bounds;
		return this;
	}
	
	//create the rectangle using x y w h call setBounds
	public HitBox setBounds(double x, double y, double w, double h) {
		setBounds(new RectangleBounds(x, y, w, h));
		return this;
	}
    
	//call prev.move(bounds.startPos()), call translate(dx, dy)on bounds
	public HitBox translate(double dx, double dy) {   
		prev.move( bounds.startPos()); 
		bounds.translate(dx, dy);
		return this;
	}
	
	public HitBox undoTranslate() {
		bounds.move(prev);		
		return this;
	}
	
	
	public boolean containsBounds(HitBox box) {
		if (bounds.contains(box.getBounds())) {
			return true;
		}else {
			return false;
		}
	}

	public boolean intersectBounds(HitBox box) {
		if (bounds.intersects(box.getBounds())) {
			return true;
		}else {
			return false;
		}
	}
	
    public boolean intersectFull(HitBox box) {	   
	  return intersectFull( box.getPoints());		   
	}
    
    //double for loop 
	protected boolean intersectFull(double[][] otherPoints) {		
		points = getPoints();
		for(int i=0, j=points[0].length-1; i < points[0].length; i++, j = i-1) {
			
			   for(int k = 0, l = otherPoints[0].length-1; k < otherPoints[0].length; k++, l = k-1) {
				   boolean intersect = IntersectUtil.getIntersection(result, points[0][i],  points[1][i],points[0][j],points[1][j],
						                   otherPoints[0][k],otherPoints[1][k],otherPoints[0][l],otherPoints[1][l]);
				   if(intersect && result[2] <= 1) {
					   return true;
				   }
			   }	   
		   }
		return false;
	}

	protected boolean hasIntersectFull() {		
		return false;		
	}

	protected double[][] getPoints() {		
		return bounds.toArray(points);	
	}
	
	//empty method
	public void update() {
	}
	
	public boolean hasHitbox() {
		return true;
	}
	
	public HitBox getHitBox() {
		return this;
	}
	
	public boolean isDrawable() {
		return true;
	}

	public Sprite getDrawable() {
		return sprite;
	}

	@Override
	public String toString() {
		return "HitBox";
	}

	public RectangleBounds getBounds() {
		return bounds;	
	}

	public Point getPrev() {
		return prev;
	}
}
