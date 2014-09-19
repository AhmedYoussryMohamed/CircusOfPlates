package States;

import java.io.Serializable;

public class ShapeState implements Serializable {
	
	private boolean move;
	
	public ShapeState(){
		move = true;
	}
	
	public void changeState(){
		move = false;
	}
	
	public boolean getState(){
		return move;
	}
	
}//end class.
