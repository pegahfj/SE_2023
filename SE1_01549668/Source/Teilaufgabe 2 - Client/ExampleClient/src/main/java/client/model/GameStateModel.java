package client.model;

import client.enums.EPlayerState;

public class GameStateModel {
//	String gameId;
	private PlayerModel myPlayer;
	private FullMapModel fullMap;
	
	public GameStateModel(PlayerModel player, FullMapModel fmap) {
		
		this.myPlayer = player;
		this.fullMap = fmap;
	}

	
	public FullMapModel getMap() {
		
		return this.fullMap;
	}


	public boolean gameOver() {
		
		return this.myPlayer.getPlayerState() == EPlayerState.LOST
				|| this.myPlayer.getPlayerState() == EPlayerState.WON;
	}
	
	
	public boolean iWon() {
		
		return this.myPlayer.getPlayerState() == EPlayerState.WON;
	}
	
	
	public PlayerModel getMyPlayer() {
		
		return this.myPlayer;
	}
	
	
}
