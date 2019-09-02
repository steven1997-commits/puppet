import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Ellipse2D;
import java.awt.event.MouseEvent;

public class HeadSprite extends Sprite {
	
	private Ellipse2D head = null;
	private double degree = 0;
	
	public HeadSprite(int width, int height) {
		super();
		initialize(width,height);
	}
	
	public HeadSprite(int width, int height, Sprite parent) {
		super(parent);
		initialize(width,height);
	}
	
    private void initialize(int width, int height) {
        head = new Ellipse2D.Double(0, 0, width, height);
    }
	
	protected void drawSprite(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.draw(head);
	}
	
	public boolean pointInside(Point2D p) {
		AffineTransform fullTransform = this.getFullTransform();
		AffineTransform inverseTransform = null;
        try {
            inverseTransform = fullTransform.createInverse();
        } catch (NoninvertibleTransformException e) {
            e.printStackTrace();
        }
        Point2D newPoint = (Point2D)p.clone();
        inverseTransform.transform(newPoint, newPoint);
        return head.contains(newPoint);
	}
	
	protected void handleMouseDownEvent(MouseEvent e) {
		System.out.println("Head hit");
		if(lastPoint == null){
			lastPoint = e.getPoint();
		}
        if (e.getButton() == MouseEvent.BUTTON1) {
            interactionMode = InteractionMode.ROTATING;
        }
	}
	
	protected void handleMouseDragEvent(MouseEvent e) {
        Point2D oldPoint = lastPoint;
        Point2D newPoint = e.getPoint();
        switch (interactionMode) {
            case IDLE:
                ; // no-op (shouldn't get here)
                break;
            case DRAGGING:
				;
                break;
            case ROTATING:
                //System.out.println("Rotating Drag Head"); // Provide rotation code here
				calcAngle(newPoint);
                break;
            case SCALING:
                ; // Provide scaling code here
                break;
        }
        // Save our last point, if it's needed next time around
        lastPoint = e.getPoint();
	}
	
	private void calcAngle(Point2D last) {
		//make a new point at center of head, then inverse transform it to current coordinates
		Point2D anchor = new Point2D.Double(head.getWidth()/2,head.getHeight());
		AffineTransform fullTransform = this.getFullTransform();
		AffineTransform inverseTransform = null;
        try {
            inverseTransform = fullTransform.createInverse();
        } catch (NoninvertibleTransformException e) {
            e.printStackTrace();
        }
        inverseTransform.transform(last, last);
		
		double diffX = last.getX() - anchor.getX();
		double diffY = (200 - last.getY()) - anchor.getY();
	
		this.degree = 90-Math.toDegrees(Math.atan2(diffY,diffX));
		double newAng = this.angle + degree;
		
		rotate(newAng);
	}
	
	protected void rotate(double newAng) {
		AffineTransform inverseRot = new AffineTransform();
		inverseRot.rotate(-Math.toRadians(angle),head.getWidth()/2,head.getHeight());
		this.angle = newAng;
		AffineTransform rot = new AffineTransform();
		rot.rotate(Math.toRadians(angle),head.getWidth()/2,head.getHeight());
		transform.concatenate(rot);
		transform.concatenate(inverseRot);
	}
	
	public void transformAll(double[] positions) {
		double newAngle = positions[0];
		this.rotate(newAngle);
	}

}









