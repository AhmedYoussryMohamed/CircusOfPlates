package Model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Shape;
import java.awt.Toolkit;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import View.*;
import Model.*;
import Controller.*;

public class ShapePool implements Serializable {

	private static ShapePool shapePool;
	private ArrayList<Shapes> shapesArray;
	private ArrayList<Shapes> free;
	private ArrayList<Shapes> busy;
	private int poolSize = 200;
	private int numOfShapes = 3;
	private int numOfColors = 4;
	private ShapeFactory factory;
	private int maxHeight = -1;
	private int firstStringY = 80;
	private int secondStringY = 110;
	private int firstPathY = -1;
	private int secondPathY = -1;
	private int maxWidth = -1;
	private int[] currentX;
	private int[] currentY;
	private Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
	
	private ShapePool() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		factory = ShapeFactory.getInstance();
		shapesArray = new ArrayList<Shapes>(); free = new ArrayList<Shapes>(); busy = new ArrayList<Shapes>();
		Random c = new Random(); Random s = new Random(); Random w = new Random();
		
		for (int i = 0; i < poolSize; i++) {
			int colorNum = c.nextInt(numOfColors - 1);
			int shapeNum = s.nextInt(numOfShapes - 1);
			Color color = factory.getColor(colorNum);
			//5aly balak mn el random here
			Shapes object = factory.getShape(shapeNum, color, w.nextInt(3) );
			
			if( maxHeight < object.getHeight() ){ maxHeight = object.getHeight() ;}
			if( maxWidth < object.getWidth() ){ maxWidth = object.getWidth() ;}
			shapesArray.add(object);
			
		}//end for i.
		firstPathY = maxHeight + secondStringY;
		secondPathY = 3*maxHeight + secondStringY;
		Iterator it = shapesArray.iterator();
		//FourPlaces:
		int[] currentXTemp = {0 - maxWidth  ,d.width,d.width,0 - maxWidth};
		int[] currentYTemp = { firstPathY,firstPathY,secondPathY ,secondPathY};
		currentX = currentXTemp;
		currentY = currentYTemp;
		Random r = new Random();
		int p = 80;
		while( it.hasNext() ){
			Shapes object = (Shapes) it.next();
			int check = r.nextInt(4);
			object.setX( currentX[check] );
			if( check == 0 || check == 3){
				currentX[check] =  currentX[check] - object.getWidth() - p;
			}else{
				currentX[check] =  currentX[check] + object.getWidth() + 2*p;
			}
			object.setY( currentY[check] - object.getHeight() );
		}//end while.
		
	}//enc Constructor.

	public static ShapePool getInstance() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		if (shapePool == null) {
			shapePool = new ShapePool();
		}
		return shapePool;
	}
	
	public void addFree( Shapes s ){
		free.add(s);
		Iterator it = busy.iterator();
		int counter = 0;
		while( it.hasNext() ){
			Shapes shape = (Shapes) it.next();
			if( shape.equals( s ) ){
//				busy.remove(secondShape);
				busy.remove(counter);
			}
			counter++;
		}//end while
		
	}//end method.
	
	public void addBusy( Shapes s ){
		busy.add(s);
		Iterator it = free.iterator();
		int counter = 0;
		while( it.hasNext() ){
			Shapes shape = (Shapes) it.next();
			if( shape.equals( s ) ){
//				free.remove(secondShape);
				free.remove(counter);
			}
			counter++;
		}//end while
	}//end method.
	
	public void update(){
		currentX[0]++;
		currentX[1]--;
		currentX[2]--;
		currentX[3]++;
		
		if( currentX[0] >= 0 - maxWidth ){
			currentX[0] = -maxWidth;
		}
		if( currentX[3] >= 0 - maxWidth ){
			currentX[3] = -maxWidth;
		}
		if( currentX[2] <= d.width ){
			currentX[2] = d.width;
		}
		if( currentX[1] <= d.width ){
			currentX[1] = d.width; 
		}
		
	}//end method.
	
	public void addToQueue( Shapes s){
		Random r = new Random();
		int check = r.nextInt(4);
		System.out.println( check + "  " + currentX[check] );
		s.setX( currentX[check] );
		s.setY( currentY[check] - s.getHeight() );
		int p = 80;
		if( check == 0 || check == 3){
			currentX[check] =  currentX[check] - s.getWidth() - p;
		}else{
			currentX[check] =  currentX[check] + s.getWidth() + 2*p;
		}
		
	}//end method.
	
	public ArrayList<Shapes> getShapesArray(){
		return shapesArray;
	}
	
	public Iterator getIterator(){
		return shapesArray.iterator();
	}
	
	public int getMaxHeight(){
		return maxHeight;
	}
	
	public int getFirstPathY(){
		return firstPathY;
	}
	
	public int getSecondPathY(){
		return secondPathY;
	}
	
	public int[] getCurrentX(){
		return currentX;
	}
	
	public int[] getCurrentY(){
		return currentY;
	}
	
	public ArrayList<Shapes> getFreeArray(){
		return free;
	}
	
	public ArrayList<Shapes> getBusyArray(){
		return busy;
	}
	
	
}//end class.
