import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JFileChooser;
import javax.swing.UIManager;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import javax.swing.JFrame;
import javax.swing.AbstractAction;
import java.awt.event.WindowEvent;

import java.util.Vector;
import java.util.Arrays;
import java.io.*;

public class Main {

	public static void main(String[] args) {		
	
		try{
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		}catch(Exception e){
			
		}
		
		JFrame f = new JFrame();
		final FileChooser fc = new FileChooser(f);
	
		// add scene graph to the canvas
		Canvas canvas = new Canvas();
		canvas.addSprite(Main.makeSprite());

		//menu
		JMenuBar menubar = new JMenuBar();
		JMenu menu = new JMenu("File");
		JMenuItem menuFile = new JMenuItem(new AbstractAction("Load") {
			public void actionPerformed(ActionEvent e) {
				//int val = fc.showOpenDialog(f);
				//ONLY perform if open button was clicked, check for it here first
				boolean val = fc.chooseFile();
				if(val){
					Vector<String> vec = fc.getPositions();
					String positions = vec.get(0);
					double[] nums = Parser.parse(positions);
					System.out.println(Arrays.toString(nums));
					canvas.transformPerson(nums);
					canvas.repaint();
				}
			}
		});
		JMenuItem menuSave = new JMenuItem(new AbstractAction("Save") {
			public void actionPerformed(ActionEvent e) {
				String positions = canvas.getSpriteDescriptions();
				System.out.println(positions);
				fc.saveFile(positions);
			}
		});
		JMenuItem menuReset = new JMenuItem(new AbstractAction("Reset") {
			public void actionPerformed(ActionEvent e) {
				canvas.emptySprites();
				canvas.addSprite(Main.makeSprite());
				canvas.repaint();
			}
		});
		JMenuItem menuQuit = new JMenuItem(new AbstractAction("Quit") {
			public void actionPerformed(ActionEvent e) {
				f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
			}
		});
		
		menu.add(menuFile);
		menu.add(menuSave);
		menu.add(menuReset);
		menu.addSeparator();
		menu.add(menuQuit);

		menubar.add(menu);
		
		// create a frame to hold it

		f.getContentPane().add(canvas);
		f.getContentPane().setLayout(new GridLayout(1, 1));
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(1024, 768);
		f.setResizable(false);
		f.setVisible(true);
		f.setJMenuBar(menubar);
	}
	
	private void resetSprite(){
		
	}
	
	/* Make sample scene graph for testing purposes. */
	private static Sprite makeSprite() {
		PersonSprite newPerson = new PersonSprite();
		return newPerson;
	}
	
	public static class Parser{
		
		//parse order should be head, torso, left arm - lower - hand, right arm - lower - hand
		//left thigh - leg - foot, right thigh - leg - foot
		public Parser(){
			
		}
		
		public static double[] parse(String positions){
			String[] split = positions.split(",");
			double[] nums = new double[split.length];
			for(int i=0;i<split.length;i++){
				nums[i] = Double.parseDouble(split[i]);
			}
			return nums;
		}
		
	}

}
