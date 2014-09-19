package States;

import java.io.Serializable;

public class PlayerState implements PlayerStateIF,Serializable {
	
	private PlayerStateIF state;
	
	public void setState(PlayerStateIF state){
		this.state = state;
	}
	
	@Override
	public String getState() {
		return this.state.getState();
		
	}

	
}//end class.
