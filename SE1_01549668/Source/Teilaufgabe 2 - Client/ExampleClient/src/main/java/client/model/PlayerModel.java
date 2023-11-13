package client.model;

import java.util.Optional;


public class PlayerModel {

	private Optional<Node> currentPos = Optional.empty();
	private Optional<Node> targetPos = Optional.empty();
	

    public PlayerModel(){
           	
    }

    
    
    public Optional<Node> getCurrentPos() {
        
    	return this.currentPos;
    }

    
    public void setCurrentPos(Node myPos) {
    	Optional<Node> pos = Optional.of(myPos);
    	
    	this.currentPos = pos;
    }


    public Optional<Node> getTargetPos() {
		
 		return this.targetPos;
 	}


 	public void setTargetPos(Node targetPos) {
    	Optional<Node> pos = Optional.of(targetPos);

 		this.targetPos = pos;
 	}


    	
    
}
