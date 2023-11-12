package client.model;

import client.enums.EPlayerState;

public class PlayerModel {

    private EPlayerState playerState;
	private boolean hasTreasure;
	private Node myPos;
	private Node treasurePos;
	private Node oponentFort;

    public PlayerModel(EPlayerState plSt, boolean treasure){
       
    	this.playerState = plSt;
    	this.hasTreasure = treasure;
    	
    }

    
    public EPlayerState getPlayerState() {
       
    	return this.playerState;
    }

    
    public void setPlayerState(EPlayerState playerState) {
        
    	this.playerState = playerState;
    }

    
    public boolean hasTreasure() {
        
    	return this.hasTreasure;
    }


    public void setHasTreasure(boolean hasTreasure) {
        
    	this.hasTreasure = hasTreasure;
    }

    
    public Node getMyPos() {
        
    	return this.myPos;
    }

    
    public void setMyPos(Node myPos) {
        
    	this.myPos = myPos;
    }


	public Node getTreasurePos() {
		
		return treasurePos;
	}


	public void setTreasurePos(Node treasurePos) {
		
		this.treasurePos = treasurePos;
	}


	public Node getOpFortPos() {
		
		return oponentFort;
	}


	public void setOpFortPos(Node fortPos) {
		
		this.oponentFort = fortPos;
	}
    	
    
}
