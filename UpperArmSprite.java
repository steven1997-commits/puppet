import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Ellipse2D;
import java.awt.event.MouseEvent;

public class UpperArmSprite extends Sprite {
	
	private Ellipse2D arm = null;
	
	public UpperArmSprite(int width, int height) {
		super();
		initialize(width,height);
	}
	
	public UpperArmSprite(int width, int height, Sprite parent) {
		super(parent);
		initialize(width,height);
	}
	
	private void initialize(int w, int h) {
		this.arm = new Ellipse2D.Double(0,0,w,h);
	}
	
	protected void drawSprite(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.draw(arm);
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
        return arm.contains(newPoint);
	}
	
	protected void handleMouseDownEvent(MouseEvent e) {
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
		Point2D anchor = new Point2D.Double(arm.getWidth()/2,0);
		AffineTransform fullTransform = this.getFullTransform();
		AffineTransform inverseTransform = null;
        try {
            inverseTransform = fullTransform.createInverse();
        } catch (NoninvertibleTransformException e) {
            e.printStackTrace();
        }
        inverseTransform.transform(last, last);
		
		double diffX = last.getX() - anchor.getX();
		double diffY = last.getY() - anchor.getY();
		
		double degree = Math.toDegrees(Math.atan2(diffY,diffX)) - 90;
		double newAng = this.angle + degree;
		
		rotate(newAng);
	}
	
	protected void rotate(double newAng) {
		AffineTransform inverseRot = new AffineTransform();
		inverseRot.rotate(-Math.toRadians(angle),arm.getWidth()/2,0);
		this.angle = newAng;
		AffineTransform rot = new AffineTransform();
		rot.rotate(Math.toRadians(angle),arm.getWidth()/2,0);
		transform.concatenate(rot);
		transform.concatenate(inverseRot);
	}
	
	public void transformAll(double[] positions) {
		double newang = positions[0];
		rotate(newang);
		LowerArmSprite lower = (LowerArmSprite) children.elementAt(0);
		lower.transformAll(positions);
	}
}















