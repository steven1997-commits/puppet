import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.Vector;
import java.lang.Object;
import java.util.Arrays;

public class PersonSprite extends Sprite {
	
	public PersonSprite() {
		this.initPerson();
	}
	
	public void initPerson() {
		Sprite firstSprite = new TorsoSprite(120, 200);
		Sprite headSprite = new HeadSprite(70,100,firstSprite);
		Sprite leftUpper = new UpperArmSprite(40,130,firstSprite);
		Sprite rightUpper = new UpperArmSprite(40,130,firstSprite);
		Sprite leftThigh = new ThighSprite(40,140,firstSprite);
		Sprite rightThigh = new ThighSprite(40,140,firstSprite);
		
		Sprite leftLowerArm = new LowerArmSprite(30,110,leftUpper);
		Sprite rightLowerArm = new LowerArmSprite(30,110,rightUpper);
		Sprite leftLowerLeg = new LowerLegSprite(30,140,leftThigh);
		Sprite rightLowerLeg = new LowerLegSprite(30,140,rightThigh);
		
		Sprite leftHand = new HandSprite(20,50,leftLowerArm);
		Sprite rightHand = new HandSprite(20,50,rightLowerArm);
		Sprite leftFoot = new FootSprite(20,50,leftLowerLeg);
		Sprite rightFoot = new FootSprite(20,50,rightLowerLeg);
		
		((TorsoSprite)firstSprite).translate(100,100);
		headSprite.transform(AffineTransform.getTranslateInstance(25, -105));
		leftUpper.transform(AffineTransform.getTranslateInstance(-40, 0));
		rightUpper.transform(AffineTransform.getTranslateInstance(120, 0));
		leftThigh.transform(AffineTransform.getTranslateInstance(0, 205));
		rightThigh.transform(AffineTransform.getTranslateInstance(80, 205));
		
		leftLowerArm.transform(AffineTransform.getTranslateInstance(5, 130));
		rightLowerArm.transform(AffineTransform.getTranslateInstance(5, 130));
		leftLowerLeg.transform(AffineTransform.getTranslateInstance(5, 140));
		rightLowerLeg.transform(AffineTransform.getTranslateInstance(5, 140));
		
		leftHand.transform(AffineTransform.getTranslateInstance(5, 110));
		rightHand.transform(AffineTransform.getTranslateInstance(5, 110));
		leftFoot.transform(AffineTransform.getTranslateInstance(5, 140));
		rightFoot.transform(AffineTransform.getTranslateInstance(5, 140));
		
		leftFoot.transform(AffineTransform.getRotateInstance(Math.toRadians(90),10,0));
		rightFoot.transform(AffineTransform.getRotateInstance(Math.toRadians(-90),10,0));
		
		this.children.add(firstSprite);
	}
	
	public void drawSprite(Graphics2D g) {
		
	}
	
	public String getDescription(){
		Sprite firstSprite = children.elementAt(0);
		return firstSprite.getDescription();
	}
	
	//transform ALL limbs + torso of person
	public void transformAll(double[] positions) {
		//transform the torso, then pass array to torso to transform rest
		TorsoSprite torso = (TorsoSprite)this.children.elementAt(0);
		torso.translate(positions[0],positions[1]);
		double[] limbs = Arrays.copyOfRange(positions,2,positions.length);
		torso.transformAll(limbs);
	}
	
	public boolean pointInside(Point2D p) {
		return false;
	}
}