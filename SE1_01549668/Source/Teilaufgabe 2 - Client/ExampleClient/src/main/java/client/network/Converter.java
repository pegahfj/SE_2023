package client.network;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import client.enums.EDirection;
import client.enums.EField;
import client.enums.EPlayerState;
import client.model.FullMapModel;
import client.model.GameStateModel;
import client.model.HalfMapModel;
import client.model.Node;
import client.model.NodeCoords;
import client.model.PlayerModel;
import messagesbase.messagesfromclient.EMove;
import messagesbase.messagesfromclient.ETerrain;
import messagesbase.messagesfromclient.PlayerHalfMap;
import messagesbase.messagesfromclient.PlayerHalfMapNode;
import messagesbase.messagesfromclient.PlayerMove;
import messagesbase.messagesfromserver.EFortState;
import messagesbase.messagesfromserver.EPlayerGameState;
import messagesbase.messagesfromserver.EPlayerPositionState;
import messagesbase.messagesfromserver.ETreasureState;
import messagesbase.messagesfromserver.FullMapNode;
import messagesbase.messagesfromserver.GameState;
import messagesbase.messagesfromserver.PlayerState;

public class Converter {
	
    private static Logger logger = LoggerFactory.getLogger(Converter.class);

	public Converter() {}
	
	
	public PlayerHalfMap convertToHalfMap(String uniquePlayerID, HalfMapModel chm) {
		return new PlayerHalfMap(uniquePlayerID, chm.getMapNodes().stream().map(n -> {
			return new PlayerHalfMapNode(n.getCoordinates().getX(), n.getCoordinates().getY(), n.hasFort(),
					convertToETerrain(n.getFieldType()));
		}).collect(Collectors.toList()));
	}
	
	
	
	public GameStateModel convertToGameStateModel(GameState gs, String uniquePlayerID) {
		
		PlayerModel myPlayer = new PlayerModel();
		PlayerModel enemyPlayer = new PlayerModel();
		FullMapModel myFullMap = new FullMapModel();
		GameStateModel myGameState = new GameStateModel(myPlayer, enemyPlayer);
		
		for (PlayerState pl : gs.getPlayers()) {	
			if (pl.getUniquePlayerID().equals(uniquePlayerID)) {
				myGameState.setTreasureCollected(pl.hasCollectedTreasure());
				myGameState.setPlayerState(convertToEPlayerState(pl.getState()));
			}
		}



		if (!gs.getMap().getMapNodes().isEmpty()) {
			
			List<Node> mapNodes = new ArrayList<Node>();
			
			for (FullMapNode fullmapNode : gs.getMap().getMapNodes()) {
				Node myNode = new Node(
						new NodeCoords(fullmapNode.getX(), fullmapNode.getY()),
						convertToEField(fullmapNode.getTerrain()),						
						fullmapNode.getFortState().equals(EFortState.MyFortPresent) ? true : false);

//				logger.debug("FullMap converter -> Node {} :: Node position {} ", myNode, fullmapNode.getPlayerPositionState());

				mapNodes.add(myNode);

				if (fullmapNode.getPlayerPositionState() == EPlayerPositionState.MyPlayerPosition || fullmapNode.getPlayerPositionState() == EPlayerPositionState.BothPlayerPosition) {
					logger.info("FullMap converter -> Node {} :: Node position {} ", myNode, fullmapNode.getPlayerPositionState());
					
					myPlayer.setCurrentPos(myNode);
				}
				if (fullmapNode.getPlayerPositionState() == EPlayerPositionState.EnemyPlayerPosition || fullmapNode.getPlayerPositionState() == EPlayerPositionState.BothPlayerPosition) {
					logger.info("FullMap converter -> Node {} :: Node position {} ", myNode, fullmapNode.getPlayerPositionState());
					
					enemyPlayer.setCurrentPos(myNode);
				}
				
				if (fullmapNode.getTreasureState().equals(ETreasureState.MyTreasureIsPresent)) {
					logger.info("FullMap converter -> Node {} :: Node Treasure {} ", myNode, fullmapNode.getTreasureState());

					myPlayer.setTargetPos(myNode);
				}
				
				if (fullmapNode.getFortState().equals(EFortState.EnemyFortPresent)) {
					logger.info("FullMap converter -> Node {} :: Node Fort {} ", myNode, fullmapNode.getFortState());

					enemyPlayer.setTargetPos(myNode);
				}
				
				
				
			}
			
			myFullMap.setMapNodes(mapNodes);
			myGameState.setMap(myFullMap);
			
		
		}
		
		return myGameState;
	}

	
	public PlayerMove convertToPlayerMove(String uniquePlayerID, EDirection dir) {
		return PlayerMove.of(uniquePlayerID, convertToEMove(dir));
	}

	
	private ETerrain convertToETerrain(EField t) {
		return t == EField.GRASS ? ETerrain.Grass
				: t == EField.MOUNTAIN ? ETerrain.Mountain : ETerrain.Water;
	}
	
	
	private EMove convertToEMove(EDirection dir) {
		return dir == EDirection.UP ? EMove.Up
				: dir == EDirection.DOWN ? EMove.Down : dir == EDirection.LEFT ? EMove.Left : EMove.Right;
	}
	
	private EPlayerState convertToEPlayerState(EPlayerGameState plGState) {
		return plGState == EPlayerGameState.MustAct ? EPlayerState.MY_TURN
				: plGState == EPlayerGameState.MustWait ? EPlayerState.OPONENT_TURN
						: plGState == EPlayerGameState.Won ? EPlayerState.WON : EPlayerState.LOST;
	}
	
	
	private EField convertToEField(ETerrain t) {
		return t == ETerrain.Grass ? EField.GRASS
				: t == ETerrain.Mountain ? EField.MOUNTAIN : EField.WATER;

	}
}
