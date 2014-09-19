import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;

import View.*;
import Model.*;
import Controller.*;

public class Main {
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		
		Controller control = Controller.getInstance();
		
		try {
				
				control.operate();
				
			} catch (MalformedURLException e) {
				System.out.println("Malformed Exception.");
			} catch (ClassNotFoundException e) {
				System.out.println("ClassNotFound.");
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
		
	}//end main.
	
}//end class.
