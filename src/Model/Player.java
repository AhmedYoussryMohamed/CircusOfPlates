package Model;

import java.awt.Graphics;
import java.io.Serializable;
import java.util.ArrayList;
import States.*;

public interface Player {

	public int getScore();
	public void incrementScore();
	public Plate checkConsecutiveColors();
	public long getLastScoreTime();
	public void checkColors();
	public Plate getLeftPlate();
	public Plate getRightPlate();
	public void update( Shapes s );
	public int getX();
	public int getYPositionForClown();
	public int getWidthOfClown();
	public int getHeightOfClown();
	public void moveRight();
	public void moveLeft();
	public void setCurrentState( PlayerStateIF state );
	public String getCurrentState();
	
}
