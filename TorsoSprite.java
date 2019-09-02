import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.io.*;
import java.awt.event.MouseEvent;
import java.util.Arrays;

public class TorsoSprite extends Sprite {

	double x, y = 0;
    private RoundRectangle2D rect = null;

    /**
     * Creates a rectangle based at the origin with the specified
     * width and height
     */
    public TorsoSprite(int width, int height) {
        super();
        this.initialize(width, height);
    }
    /**
     * Creates a rectangle based at the origin with the specified
     * width, height, and parent
     */
    public TorsoSprite(int width, int height, Sprite parentSprite) {
        super(parentSprite);
        this.initialize(width, height);
    }
    
    private void initialize(int width, int height) {
        rect = new RoundRectangle2D.Double(0, 0, width, height, width*0.2, height*0.2);
    }
    
    /**
     * Test if our rectangle contains the point specified.
     */
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
        return rect.contains(newPoint);
    }

    protected void drawSprite(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.draw(rect);
    }
    
    public String toString() {
        return "RectangleSprite: " + rect;
    }
	
	//translate back to 0,0 - then translate to the given coordinates
	public void translate(double x, double y) {
		this.transform(AffineTransform.getTranslateInstance(x, y));
		this.transform(AffineTransform.getTranslateInstance(-this.x, -this.y));
		this.x = x;
		this.y = y;
	}
	
	public void transformAll(double[] positions) {
		//first transform the head
		HeadSprite head = (HeadSprite) children.elementAt(0);
		head.transformAll(positions);
		//then split array in half - first for left arm + leg, second for right arm + leg
		positions = Arrays.copyOfRange(positions,1,positions.length);
		
		double[] left = Arrays.copyOfRange(positions,0,positions.length/2);
		double[] right = Arrays.copyOfRange(positions,positions.length/2,positions.length);
		
		double[] leftArm = Arrays.copyOfRange(left,0,left.length/2);
		double[] leftLeg = Arrays.copyOfRange(left,left.length/2,left.length);
		
		double[] rightArm = Arrays.copyOfRange(right,0,right.length/2);
		double[] rightLeg = Arrays.copyOfRange(right,right.length/2,right.length);
		
		UpperArmSprite leftUpper = (UpperArmSprite) children.elementAt(1);
		leftUpper.transformAll(leftArm);
		
		ThighSprite leftThigh = (ThighSprite) children.elementAt(3);
		leftThigh.transformAll(leftLeg);
		
		UpperArmSprite rightUpper = (UpperArmSprite) children.elementAt(2);
		rightUpper.transformAll(rightArm);
		
		ThighSprite rightThigh = (ThighSprite) children.elementAt(4);
		rightThigh.transformAll(rightLeg);
	}
	
    protected void handleMouseDragEvent(MouseEvent e) {
        Point2D oldPoint = lastPoint;
        Point2D newPoint = e.getPoint();
        switch (interactionMode) {
            case IDLE:
                ;
                break;
            case DRAGGING:
                double x_diff = newPoint.getX() - oldPoint.getX();
                double y_diff = newPoint.getY() - oldPoint.getY();
				
				double newx = this.x + x_diff;
				double newy = this.y + y_diff;
				this.translate(newx,newy);
                break;
            case ROTATING:
				;
                break;
            case SCALING:
                break;
        }
        lastPoint = e.getPoint();
    }
	
	public String getDescription() {
		String myX = Double.toString(this.x);
		String myY = Double.toString(this.y);
		String value = "";
		
		String head = children.elementAt(0).getDescription();
		String leftArm = children.elementAt(1).getDescription();
		String leftLeg = children.elementAt(3).getDescription();
		String rightArm = children.elementAt(2).getDescription();
		String rightLeg = children.elementAt(4).getDescription();
		
		value = myX + "," + myY + "," + head + "," + leftArm + "," + leftLeg + "," + rightArm + "," + rightLeg;
		return value;
	}
}

















