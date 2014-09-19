package Model;

import java.io.Serializable;
import java.util.ArrayList;

import View.*;
import Controller.*;

public class Plate implements Serializable {
	private int firstX;
	private int secondX;
	private int y;
	private ArrayList<Shapes> list;
	
	public Plate( int x1 , int x2 ,int y){
		firstX = x1;
		secondX = x2;
		this.y = y;
		list = new ArrayList<Shapes>();
	}//end const.
	
	public void update(int value){
		firstX += value;
		secondX += value;
		updateList(value);
	}//end method.
	
	private void updateList(int value){
		for( int i = 0 ;i < list.size() ;i++){
			list.get(i).setX( list.get(i).getX() + value );
		}
	}//end method.
	
	public int getFirstX(){
		return firstX;
	}
	
	public int getSecondX(){
		return secondX;
	}
	
	public int getY(){
		return y;
	}
	
	public void incrementHeight(int h){
		y -= h;
	}
	
	public void addShape( Shapes s ){
		list.add(s);
	}
	
	public ArrayList<Shapes> getPlateList(){
		return list;
	}
	
}//end class.
