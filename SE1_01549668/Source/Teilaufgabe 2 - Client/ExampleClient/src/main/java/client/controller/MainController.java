package client.controller;

import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import client.enums.EDirection;
import client.model.GameModelService;
import client.model.GameStateModel;
import client.model.HalfMapModel;
import client.model.HalfMapService;
import client.model.PathFinderService;
import client.network.ServerEndPoint;

public class MainController {
	
	private ServerEndPoint endPoint;
	private GameModelService gameService;
	private MoveController moveCntrl;

	private static Logger logger = LoggerFactory.getLogger(MainController.class);

	public MainController(String serverURL, String gameID) {
		
		this.gameService = GameModelService.getInstance();
		this.endPoint = new ServerEndPoint(serverURL, gameID, gameService);
		this.moveCntrl = new MoveController(gameService);

	}

	public void gameHandler() {
		this.registerHandler();
		this.movementHandler();
	}	
	public void registerHandler() {
		
		this.endPoint.registerPlayer("Pegah", "Farajzadeh", "farajzadep54");
		
		HalfMapService service = new HalfMapService();
		HalfMapModel hmap = service.generateHalfMap();
		
		this.endPoint.sendHalfMap(hmap);

	}
	
	
	public void movementHandler() {
		while(!this.gameService.getGameState().fullMapPresent()) {
			
			this.endPoint.getGameState();	
		}
		
		this.gameService.getGameState().getMap().setIsSquare();
		
		while(this.gameService.getGameState().getMyPlayer().getCurrentPos().isEmpty()) {
		
			this.endPoint.getGameState();
		}
		
		this.gameService.getGameState().getMap().determineBorders(
				this.gameService.getGameState().getMyPlayer().getCurrentPos().get().getCoordinates());
		
		while (!this.gameService.getGameState().gameOver()) {
			Stack<EDirection> directions = this.moveCntrl.getDirectionStack();
			while(directions.size() != 0) {
		        EDirection nextDir = directions.pop();
//		        if (move == null)
//		        	continue;
		        this.endPoint.sendMove(nextDir);
		        this.endPoint.getGameState();

			}

		}
	}
	
	
	
	
	
//	public void updateGameState(GameStateModel gs) {
//
//		this.gameState = gs;
//	}
}
