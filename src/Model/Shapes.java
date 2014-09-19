package Model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Shape;
import java.io.Serializable;

public abstract class Shapes implements Serializable {
	
	protected int x,y;
	protected Color color;
	protected int dx;
	protected int beginDx = 1;
	
	public Shapes (Color color,int movementType){
		this.color=color;
		if( movementType == 0 ){
			dx = 1;
		}
		else if( movementType == 1 ){
			dx = -1;
		}
		else{
			dx = 0;
		}
//		dx = 1;
	}//end const.
	
	public abstract Shape getShape();
	public abstract void setStateFree(boolean b);
	public abstract void setX(int x);
	public abstract void setY(int y);
	public abstract int getX();
	public abstract int getY();
	public abstract int getWidth();
	public abstract int getHeight();
	public abstract boolean getShapeState();
	public abstract Color getColor();
//	public abstract void paint(Graphics g);
	public abstract void update();

}
