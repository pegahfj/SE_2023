package client.network;

import java.util.ArrayList;
import java.util.List;

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
	
	public GameStateModel convertToGameStateModel(GameState gs, String uniquePlayerID) {
		
		PlayerModel myPlayer = null;

		for (PlayerState pl : gs.getPlayers()) {
			
			if (pl.getUniquePlayerID().equals(uniquePlayerID))
				myPlayer = new PlayerModel(convertToEPlayerState(pl.getState()), pl.hasCollectedTreasure());
			}



		if (!gs.getMap().getMapNodes().isEmpty()) {
			
			FullMapModel myfMap = new FullMapModel();
			
			List<Node> mapNodes = new ArrayList<Node>();
			
			for (FullMapNode fullmapNode : gs.getMap().getMapNodes()) {
				Node myNode = new Node(
						new NodeCoords(fullmapNode.getX(), fullmapNode.getY()),
						convertToEField(fullmapNode.getTerrain()),						
						fullmapNode.getFortState().equals(EFortState.MyFortPresent) ? true : false);

				logger.debug("FullMap converter -> Node {} :: Node position {} ", myNode, fullmapNode.getPlayerPositionState());

				mapNodes.add(myNode);

				if (fullmapNode.getPlayerPositionState() == EPlayerPositionState.MyPlayerPosition || fullmapNode.getPlayerPositionState() == EPlayerPositionState.BothPlayerPosition) {
					myPlayer.setMyPos(myNode);
				}
				
				if (fullmapNode.getTreasureState().equals(ETreasureState.MyTreasureIsPresent))
					myPlayer.setTreasurePos(myNode);
				
				if (fullmapNode.getFortState().equals(EFortState.EnemyFortPresent))
					myPlayer.setOpFortPos(myNode);
				
				
				
			}
			
			myfMap.setMapNodes(mapNodes);

			return new GameStateModel(myPlayer, myfMap);
		
		}
		
		return new GameStateModel(myPlayer, null);
	}

	public PlayerMove convertToPlayerMove(String uniquePlayerID, EDirection dir) {
		return PlayerMove.of(uniquePlayerID, convertToEMove(dir));
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
