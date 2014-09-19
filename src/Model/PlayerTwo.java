package Model;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import States.*;
import Controller.Controller;

public class PlayerTwo implements Player,Serializable{
	private Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
	private static PlayerTwo secondPlayer;
	private int currentScore = 0;
	private ArrayList<String> rightPlates;
	private ArrayList<String> leftPlates;
	private long LastScoreTime = 0;
	private int widthOfClown = 247;
	private int heightOfClown = 300;
	private int yPositionForClown = d.height-45-heightOfClown;
	private int x = 0;
	private int plateWidth =82;
	private Plate rightPlate = null;
	private Plate leftPlate = null;
	private int playerSpeed = 7;
	private PlayerStateIF state = null;
	
	private PlayerTwo(){
		currentScore =0;
		LastScoreTime =0;
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		x = (int) ( d.getWidth() - widthOfClown ) ;
		rightPlate = new Plate( x + widthOfClown - plateWidth , x + widthOfClown , yPositionForClown );
		leftPlate = new Plate( x , x + plateWidth , yPositionForClown );
	}
	
	public static PlayerTwo getInstance(){
		if(secondPlayer == null){
			secondPlayer = new PlayerTwo();
		}
		return secondPlayer;
	}
	
	@Override
	public void setCurrentState(PlayerStateIF state){
		this.state = state;
	}
	
	public String getCurrentState(){
		return state.getState();
	}
	
	@Override
	public int getScore() {
		return currentScore;
	}

	@Override
	public void incrementScore() {
		currentScore++;
		LastScoreTime =System.currentTimeMillis();
	}

	@Override
	public Plate checkConsecutiveColors() {
		ArrayList<Shapes> list = leftPlate.getPlateList();
		if(list.size() >= 3){
			if( list.get( list.size() - 1 ).getColor().equals( list.get( list.size() - 2 ).getColor() ) 
					&& list.get( list.size() - 1 ).getColor().equals( list.get( list.size() - 3 ).getColor() ) ){
				return leftPlate;
			}
		}//end if.
		
		list = rightPlate.getPlateList();
		if(list.size() >= 3){
			if( list.get( list.size() - 1 ).getColor().equals( list.get( list.size() - 2 ).getColor() ) 
					&& list.get( list.size() - 1 ).getColor().equals( list.get( list.size() - 3 ).getColor() ) ){
				return rightPlate;
			}
		}//end if.
		
		return null;
	}

	@Override
	public long getLastScoreTime() {
		return LastScoreTime;
	}
	
	private void removeLastShapes( Plate p ){
		ArrayList<Shapes> list = p.getPlateList();
		int size = list.size();
		
		int h =  list.get( size - 1 ).getHeight();
		h += list.get( size - 2 ).getHeight();
		h += list.get( size - 3 ).getHeight();
		p.incrementHeight( -h );
		
		list.get(size - 1).setY( -100 );
		list.get(size - 2).setY( -100 );
		list.get(size - 3).setY( -100 );
		
		list.remove( size - 1 );
		list.remove( size - 2 );
		list.remove( size - 3 );
		
		incrementScore();
		Controller control = Controller.getInstance();
		control.makeSoundEffect();
	}//end method.
	
	@Override
	public void checkColors() {
		Plate p = checkConsecutiveColors();
		if( p != null ){
			removeLastShapes( p );
		}
		
	}//end method paint.
	
	public void moveRight(){
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		if (x + widthOfClown < d.getWidth() - playerSpeed) {
			x += playerSpeed;
			rightPlate.update(playerSpeed);
			leftPlate.update(playerSpeed);
		}
		
	}//end method.
	
	public void moveLeft(){
		if (x > playerSpeed) {
			x -= playerSpeed;
			rightPlate.update(-playerSpeed);
			leftPlate.update(-playerSpeed);
		}	
		
	}//end method.
	
	public int getWidthOfClown(){
		return widthOfClown;
	}
	
	public int getHeightOfClown(){
		return heightOfClown;
	}
	
	public int getYPositionForClown(){
		return yPositionForClown;
	}
	
	public int getX() {
		return x;
	}
	
	public Plate getLeftPlate(){
		return leftPlate;
	}
	
	public Plate getRightPlate(){
		return rightPlate;
	}
	
	@Override
	public  void update( Shapes s ){
		checkForCollision( s );
	}//end method.
	
	private void checkForCollision( Shapes s ){
		
		//Left Plate:
		int firstX = leftPlate.getFirstX();
		int secondX = leftPlate.getSecondX();
		int plateY = leftPlate.getY();
		if( Math.abs(s.getX()-firstX)<=30  && s.getY() <= plateY && s.getY() > plateY - s.getHeight()-10 ){
			s.setStateFree( false );
			s.setX(firstX+5);
			s.setY( plateY - s.getHeight() );
			leftPlate.incrementHeight( s.getHeight() );
			leftPlate.addShape( s );
		}//end if.
		
		//RightPlate
		firstX = rightPlate.getFirstX();
		secondX = rightPlate.getSecondX();
		plateY = rightPlate.getY();
		if(  Math.abs(s.getX()-firstX)<=30&& s.getY() <= plateY && s.getY() > plateY - s.getHeight()-10 ){
			s.setStateFree( false );
			s.setX(firstX+5);
			s.setY( plateY - s.getHeight() );
			rightPlate.incrementHeight( s.getHeight() );
			rightPlate.addShape( s );
		}//end if.
		
	}//end method.
	
}//end class.
