package client.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import client.enums.EDirection;
import client.enums.EField;
import client.enums.EPathStrategy;
import client.model.GameModelService;
import client.model.GameStateModel;
import client.model.HalfMapModel;
import client.model.Node;
import client.model.PathFinderService;

public class MoveController {
	
	private GameModelService gameService;
	private PathFinderService pathFinder;

//	private boolean foundTreasure;
	private Node currDestination;
	private List<Node> currPath;
	private List<Node> visitedNodes;
	private int pathItr;
	
    private static Logger logger = LoggerFactory.getLogger(MoveController.class);

	
	public MoveController(GameModelService gs) {
		this.gameService = gs;
		this.pathFinder = new PathFinderService(this.gameService);
		
		this.currDestination = null;
		this.currPath = new ArrayList<Node>();
		this.visitedNodes = new ArrayList<Node>();
		this.pathItr = 0;
		
	}
	

    public Stack<EDirection> getDirectionStack() {
    	
    	Node myPos = this.gameService.getGameState().getMyPlayer().getMyPos();
		Node nextPos = this.getNextNode();
		
    	Stack<EDirection> directions = new Stack<>();

        int edgeWeight = myPos.getFieldType().getCosts() + nextPos.getFieldType().getCosts();
        
        EDirection dir = this.directionBetwNodes(myPos, nextPos);

        while (edgeWeight > 0) {
            directions.push(dir);
            // logger.debug("populateDirections {} :: weightCounter {} :: ", weight);
            --edgeWeight;
        }
        
        return directions;
    }

    // go through this n neighbours (list of Edge) find the one edge connected to
    // nPrev
    

    public EDirection directionBetwNodes(Node myPos, Node nextPos) {
        HashMap< List<Integer>, EDirection> direction_map = new LinkedHashMap<>();
        direction_map.put(EDirection.LEFT.get_value(), EDirection.LEFT );
        direction_map.put(EDirection.RIGHT.get_value(), EDirection.RIGHT);
        direction_map.put(EDirection.DOWN.get_value(), EDirection.DOWN);
        direction_map.put(EDirection.UP.get_value(), EDirection.UP);
        
        int x1 = myPos.getNodeCoordinates().getX();
        int x2 = nextPos.getNodeCoordinates().getX();
        int y1 = myPos.getNodeCoordinates().getY();
        int y2 = nextPos.getNodeCoordinates().getY();

        int x = x2 - x1;
        int y = y2 - y1;

        return direction_map.get(Arrays.asList(x, y));
    }
	//get next node and compare with current position to get the move
//	public EDirection getNextMove(){
//		Node myPos = this.gameService.getGameState().getMyPlayer().getMyPos();
//		Node next = this.getNextNode();
//	}
	
	
	//get next node from path if there's no node generate new path
	public Node getNextNode(){
		if(this.currPath.size() == 0 || 
				this.pathItr == this.currPath.size() ||
				this.canFindTreasure()||
				this.canFindFort()) {
		
			this.getNewPath();
		}
		
		Node next = this.currPath.get(this.pathItr);
		
		this.pathItr++;
		
		this.visitedNodes.add(next);
		
		return next;
	
	}
	
	
	
	private boolean canFindTreasure() {
		if(gameService.getGameState().getMyPlayer().getTreasurePos() != null && 
				!gameService.getGameState().getMyPlayer().getTreasurePos().equals(this.currDestination) && 
				!gameService.getGameState().getMyPlayer().hasTreasure()) {
						
			return true;
					
		}
		
		return false;
	}
	
	
	private boolean canFindFort() {
		if (gameService.getGameState().getMyPlayer().getOpFortPos() != null && 
				gameService.getGameState().getMyPlayer().getOpFortPos().equals(this.currDestination) && 
				gameService.getGameState().getMyPlayer().hasTreasure()) {
						
			return true;
		}
		
		return false;
	}
	
	
//	public void setPathStrategy() {
//		
//		if(gameService.getGameState().getMyPlayer().getTreasurePos() != null && 
//				!gameService.getGameState().getMyPlayer().getTreasurePos().equals(this.currDestination.getCoordinates())&& 
//				!gameService.getGameState().getMyPlayer().hasTreasure()) {
//			
//			logger.info("Treasure Search At Next Destination " + this.currDestination.getCoordinates());
//			
//			this.pathStrategy = EPathStrategy.MY_HALFMAP;
//		
//		
//		}
//		
//		if (!this.foundTreasure && gameService.getGameState().getMyPlayer().hasTreasure()) {
//			
//			logger.info("Treasure Has Been Found At " + gameService.getGameState().getMyPlayer().getTreasurePos());
//			
//			this.foundTreasure = true;
//			
//			this.pathStrategy = EPathStrategy.MY_TO_ENEMY;
//		} 
//		
//		if (gameService.getGameState().getMyPlayer().getFortPos() != null
//						&& gameService.getGameState().getMyPlayer().getFortPos().equals(this.currDestination.getCoordinates())
//						&& gameService.getGameState().getMyPlayer().hasTreasure()) {
//			
//			logger.info("Fort Has Been Found At " + gameService.getGameState().getMyPlayer().getFortPos());
//
//			this.pathStrategy = EPathStrategy.ENEMY_HALFMAP;
//
//		}
//		
//	}
	
	//get new path
	public void getNewPath() {
		
		this.pathItr = 0;
		
		// need to get position node directly from map?
		// throw exception if source doesn't exist
		Node source = this.gameService.getGameState().getMap().getMapNodes().stream().filter(n -> (n.equals(this.gameService.getGameState().getMyPlayer().getMyPos()))).findFirst().get();

		visitedNodes.add(source);

//		this.pathFinder.dijkstra(source);
		
		Node destination = null;
		
		//pass destination to generatpath
		if (this.gameService.getGameState().getMyPlayer().getTreasurePos() != null && !this.gameService.getGameState().getMyPlayer().hasTreasure())
			
			destination = this.gameService.getGameState().getMap().getMapNodes().stream().filter(n -> n.equals(
					this.gameService.getGameState().getMyPlayer().getTreasurePos())).findFirst().get();
		
		else if (this.gameService.getGameState().getMyPlayer().getOpFortPos() != null && this.gameService.getGameState().getMyPlayer().hasTreasure())
		
			destination = this.gameService.getGameState().getMap().getMapNodes().stream().filter(n -> n.equals(
					this.gameService.getGameState().getMyPlayer().getOpFortPos())).findFirst().get();
		
		else {

			destination = randDestination(this.gameService.getGameState().getMap().getMapNodes());
			
//			if(this.gameService.getGameState().getMyPlayer().hasTreasure())
//
//				destination = randDestination(this.gameService.getGameState().getMap().getEnemyHM());
//		
//			else {
//				
//				destination = randDestination(this.gameService.getGameState().getMap().getMyHM());
//			}
		}		


		this.currPath = this.pathFinder.reconstructPath(source, destination);
			
		
		//this is for view purposes
//		this.handler.setTarget(this.targetCoordinate);
	}
	
	private Node randDestination(List<Node> mapNodes) {
		 Random rand = new Random();
	       
		 Node dest = mapNodes.get(rand.nextInt(mapNodes.size()));

		 while (this.visitedNodes.contains(dest) || dest.getFieldType() == EField.WATER) {
		
			 dest = mapNodes.get(rand.nextInt(mapNodes.size()));
			
		}
		 
		return dest;
	}

}
