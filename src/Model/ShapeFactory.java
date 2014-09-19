package Model;

import java.awt.Color;
import java.beans.Transient;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import View.*;
import Model.*;
import Controller.*;

public class ShapeFactory implements Serializable {
	
	private static ShapeFactory myFactory;
	private Class firstClass = null;
	private Class secondClass = null;
	private Class [] type = { Color.class ,int.class};
	
	private ShapeFactory(){
		
	}
	
	public static ShapeFactory getInstance(){
		if(myFactory == null){
			myFactory = new ShapeFactory();
		}
		return myFactory;
	}
	
	public Shapes getShape(int shapeNum , Color color ,int movementType) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		Shapes s = null;
		
		if(shapeNum ==0){
			Object obj = firstClass.getConstructor(type).newInstance( color ,movementType );
			s = (Shapes) obj;
		}else if(shapeNum ==1){
			Object obj2 = secondClass.getConstructor(type).newInstance( color ,movementType);
			s= (Shapes) obj2;
		}
		
		return s;
		
	}
	
	public Color getColor(int colorNum) {
		Color color = null;
		if (colorNum == 0) {
			color = Color.BLUE;
		} else if (colorNum == 1) {
			color = Color.RED;
		} else if (colorNum == 2) {
			color = Color.GREEN;
		} else if (colorNum == 3) {
			color = Color.MAGENTA;
		}
		return color;
	}
	
	public void setFirstClass( Class cl ){
		firstClass = cl;
		
	}//end method.
	
	public void setSecondClass( Class cl ){
		secondClass = cl;
		
	}//end method.
	

}//end class.
