package Model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import Controller.Controller;

public class SaveProject implements Serializable {
	Controller control = Controller.getInstance();
	Class ShapeOne;
	Class ShapeTwo;
	String path = System.getenv("ahmed");
	
	public void saveFile(String fileName) throws IOException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		File f = new File( System.getenv("ahmed") + fileName +".txt"); 
		FileOutputStream os = new FileOutputStream( f );
		 ObjectOutputStream out = new ObjectOutputStream(os);
		 out.writeObject( control  ); 
		 out.flush();
		 out.close();
		 os.close();
	 }//end method.
	
	 public Controller load(String fileName) throws FileNotFoundException, IOException, ClassNotFoundException{
		ObjectInputStream in = new ObjectInputStream(new FileInputStream( System.getenv("ahmed") + fileName + ".txt")); 
		Controller s = (Controller) in.readObject();
	 	in.close();
		return s;
	 }
	
}//end class.
