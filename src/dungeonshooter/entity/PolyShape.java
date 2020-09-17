package dungeonshooter.entity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
//import raycast.entity.geometry.PolyShape;
//import raycast.entity.geometry.RectangleBounds;
import utility.Point;
import utility.RandUtil;
import dungeonshooter.entity.property.HitBox;
import dungeonshooter.entity.property.Sprite;

public class PolyShape implements Entity {
	
	private int pointCount;
	private double[][] points;
	private double minX;
	private double minY;
	private double maxX;
	private double maxY;
	private HitBox hitBox;
	private Sprite sprite;
	
	
	public PolyShape(){
		hitBox =new HitBox(){
			 protected boolean hasIntersectFull(){
				 return true;
			 }
			 protected double[][] getPoints(){
				 return points;
			 }
		};
		sprite = new Sprite(){
	        {
	            setFill( new ImagePattern( new Image( "file:assets/concrete/crete935b.png")));
	        }
	        public void draw( GraphicsContext gc){
	            gc.setLineWidth( getWidth());
	            if( getStroke() != null){
	                gc.setStroke( getStroke());
	                gc.strokePolygon( points[0], points[1], pointCount());
	            }
	            if( getFill() != null){
	                gc.setFill( getFill());
	                gc.fillPolygon( points[0], points[1], pointCount());
	            }
	        }
	    };
	}
	
//	public PolyShape randomize(double centerX, double centerY, double size, int minPoints, int maxPoints) {
//		pointCount = minPoints + (int)(Math.random()*(maxPoints - minPoints) + 1);
//		points = new double[2][pointCount];
//		double p[] = new double[pointCount];
//		for (int i = 0; i < pointCount; ++i) {
//			p[i] = Math.random() * 360;
//		}
//		Arrays.sort(p);
//		minX = minY = Double.POSITIVE_INFINITY;
//		maxX = maxY = Double.NEGATIVE_INFINITY;
//		for (int j = 0; j < pointCount; ++j) {
//			double random = Math.random() * size + 1;
//			points[0][j] = random * Math.cos(Math.toRadians(p[j])) + centerX;
//			points[1][j] = random * Math.sin(Math.toRadians(p[j])) + centerY;
//			updateMinMax(points[0][j], points[1][j]);
//		}
//		hitBox = new HitBox().setBounds(minX, minY, maxX-minX, maxY-minY);
//		return this;
//	}
	
	public PolyShape randomize( double centerX, double centerY, double size, int minPoints, int maxPoints){
		pointCount = RandUtil.getInt( minPoints, maxPoints);
		points = new double[2][pointCount];
		final Point center = new Point( centerX, centerY);
		List< Point> unsortedPoints = new ArrayList<>( pointCount);
		centerX = 0;
		centerY = 0;
		for( int i = 0; i < pointCount; i++){
			Point randP = center.randomPoint( size);
			unsortedPoints.add( randP);
			centerX += randP.x();
			centerY += randP.y();
		}
		center.set( centerX / pointCount, centerY / pointCount);
		unsortedPoints.sort( ( p1, p2) -> center.angle( p1) > center.angle( p2) ? 1 : -1);
		int i = 0;
		minX = maxX = unsortedPoints.get( 0).x();
		minY = maxY = unsortedPoints.get( 0).y();
		for( Point p : unsortedPoints){
			updateMinMax( p.x(), p.y());
			points[0][i] = p.x();
			points[1][i] = p.y();
			i++;
		}
	//	recBounds = new RectangleBounds( minX, minY, maxX-minX, maxY-minY);
		hitBox = new HitBox().setBounds(minX, minY, maxX-minX, maxY-minY);
		return this;
	}

	public PolyShape setPoints(double... nums) {
		minX = maxX = nums[0];
		minY = maxY = nums[0];
		pointCount = nums.length / 2;
		
		points = new double[2][pointCount];

		for(int i = 0, j = 0 ; i<nums.length ; j++ , i += 2) {
			updateMinMax(nums[i], nums[i+1]);
			points[0][j] = nums [i];		
			points[1][j] = nums[i+1];		
		}

		hitBox = new HitBox().setBounds(minX, minY, maxX-minX, maxY-minY);
		return this;
	}
	
	private void updateMinMax(double x, double y) {
		if (x < minX) minX = x;
		if (x > maxX) maxX = x;
		if (y < minY) minY = y;
		if (y > maxY) maxY = y;
	}
	
	public int pointCount() {
		return pointCount;
	}

	public double[][] points() {
		return points;
	}
	 
	public double pX(int index) {
	    return points[0][index];
	}
	 
	public double pY(int index) {
	    return points[1][index];
	}
	 
	@Override
	public void update() {
	}
	
	@Override
	public boolean hasHitbox() {
		if (hitBox == null) {
			return false;
		}else {
			return true;
		}
	}
	
	@Override
	public HitBox getHitBox() {
		return hitBox;
	}
	
	@Override
	public boolean isDrawable() {
		if (sprite == null) {
			return false;
		}else {
			return true;
		}
	}
	 
	public Sprite getDrawable() {
		return sprite;
	}
}
