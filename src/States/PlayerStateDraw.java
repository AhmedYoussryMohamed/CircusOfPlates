package States;

import java.io.Serializable;

public class PlayerStateDraw implements PlayerStateIF,Serializable {


	@Override
	public String getState() {
		return "Draw.";
	}
	
	
	
}//end class.
