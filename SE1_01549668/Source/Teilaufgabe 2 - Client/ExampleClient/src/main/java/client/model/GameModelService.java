package client.model;

public class GameModelService {

	private static GameModelService instance;
	private GameStateModel lastClientGameState;
	private GameStateModel currentClientGameState;


	public static GameModelService getInstance() {
		if (GameModelService.instance == null)
			GameModelService.instance = new GameModelService();
		return GameModelService.instance;
	}

	private GameModelService() {}

	
	public void setGameState(GameStateModel gameState) {
		this.lastClientGameState = currentClientGameState;
		this.currentClientGameState = gameState;
	}
	
	public GameStateModel getGameState() {
		
		return this.currentClientGameState;
	}
}
