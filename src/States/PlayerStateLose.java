package States;

import java.io.Serializable;

public class PlayerStateLose  implements PlayerStateIF,Serializable {

	@Override
	public String getState() {
		return "Lose.";
	}
	
	
	
}//end class.
