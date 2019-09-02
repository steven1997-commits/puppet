import javax.swing.JFileChooser;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.awt.Component;
import java.io.FileNotFoundException;
import java.util.Vector;
import java.io.IOException;
import java.io.FileWriter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileChooser extends JFileChooser {
	
	Component parent;
	Vector<String> positions;
	//vector of strings
	
	public FileChooser(Component parent){
		super();
		String currDir = System.getProperty("user.dir");
		System.out.print(currDir);
		setCurrentDirectory(new File(currDir));
		this.parent = parent;
		positions = new Vector<String>();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("text files","txt");
		setFileFilter(filter);
	}
	
	public boolean chooseFile(){
		File currentDirectory = getCurrentDirectory();
		setSelectedFile(new File(""));
		setCurrentDirectory(currentDirectory);
		
		positions.clear();
		
		int val = showOpenDialog(parent);
		if(val == JFileChooser.APPROVE_OPTION){
			File file = getSelectedFile();

			try{
				BufferedReader br = new BufferedReader(new FileReader(file));
				String line;
				while((line = br.readLine()) != null){
					System.out.println(line);
					positions.add(line);
				}
				br.close();
			}catch(FileNotFoundException e){
				System.out.println("File could not be found!");
			}catch(Exception e){
				
			}
			return true;
		}
		return false;
	}
	
	//maybe take in file
	public void saveFile(String content){
		int val = showSaveDialog(parent);
		if(val == JFileChooser.APPROVE_OPTION){
			File file = getSelectedFile();
			//if file is null make a new one?
			
			try(FileWriter w = new FileWriter(file)){
				w.write(content);
				w.close();
			}catch(IOException e){
				System.out.println(e);
			}
		}
	}
	
	public Vector<String> getPositions(){
		return this.positions;
	}
	
}