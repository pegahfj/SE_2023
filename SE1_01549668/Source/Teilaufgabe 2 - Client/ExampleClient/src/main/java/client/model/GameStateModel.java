package client.model;

import java.util.Optional;

import client.enums.EPlayerState;

public class GameStateModel {
//	String gameId;

	private PlayerModel myPlayer;
	private PlayerModel enemyPlayer;
	private EPlayerState playerState;
	private boolean treasureCollected;
	private FullMapModel fullMap;
	
	
	public GameStateModel(PlayerModel myPlayer, PlayerModel otherPlayer) {
		
		this.myPlayer = myPlayer;
		this.enemyPlayer = otherPlayer;
		this.fullMap = new FullMapModel();
	}

	
	public FullMapModel getMap() {
		
		return this.fullMap;
	}

	
	public void setMap(FullMapModel fullmap) {
		
		this.fullMap = fullmap;
		
	}
	
	public boolean fullMapPresent() {
		if (this.fullMap.getMapNodes().size() > 0)
			return true;
		return false;
	}
	
	public boolean gameOver() {
		
		return this.playerState == EPlayerState.LOST
				|| this.playerState == EPlayerState.WON;
	}
	
	
	public boolean iWon() {
		
		return this.playerState == EPlayerState.WON;
	}
	
	public boolean treasureCollected() {
        
    	return this.treasureCollected;
    }


    public void setTreasureCollected(boolean hasTreasure) {
        
    	this.treasureCollected = hasTreasure;
    }

        
    public EPlayerState getPlayerState() {
       
    	return this.playerState;
    }

    
    public void setPlayerState(EPlayerState playerState) {
        
    	this.playerState = playerState;
    }


	public PlayerModel getMyPlayer() {
		return myPlayer;
	}


	public void setMyPlayer(PlayerModel myPlayer) {
		this.myPlayer = myPlayer;
	}


	public PlayerModel getEnmyPlayer() {
		return enemyPlayer;
	}


	public void setEnmyPlayer(PlayerModel enmyPlayer) {
		this.enemyPlayer = enmyPlayer;
	}

 
}
