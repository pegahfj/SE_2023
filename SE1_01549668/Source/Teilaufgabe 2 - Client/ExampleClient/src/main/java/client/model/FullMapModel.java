package client.model;

import java.util.List;
import java.util.stream.Collectors;

public class FullMapModel extends MapModel {

	private boolean isSquare;
	private CoordniatesSystem myHmapCoords;
	private CoordniatesSystem enemyHmapCoords;
	
	public FullMapModel() {
		this.myHmapCoords = new CoordniatesSystem();
		this.enemyHmapCoords = new CoordniatesSystem();
	}
	
	
	public List<Node> getMyHM(){        
		return this.mapNodes.stream().filter(n -> (
				n.getCoordinates().getX() >= myHmapCoords.getBottomOrigin().getX() &&
				n.getCoordinates().getY() >= myHmapCoords.getBottomOrigin().getY() &&
				n.getCoordinates().getX() <= myHmapCoords.getTopOrigin().getX() &&
				n.getCoordinates().getY() <= myHmapCoords.getTopOrigin().getY())).collect(Collectors.toList());
	}
	
	
	public List<Node> getEnemyHM(){
		return this.mapNodes.stream().filter(n -> (
				n.getCoordinates().getX() >= enemyHmapCoords.getBottomOrigin().getX() && 
				n.getCoordinates().getY() >= enemyHmapCoords.getBottomOrigin().getY() && 
				n.getCoordinates().getX() <= enemyHmapCoords.getTopOrigin().getX() && 
				n.getCoordinates().getY() <= enemyHmapCoords.getTopOrigin().getY())).collect(Collectors.toList());
	}
	
	
	//isSquare -> 10x10 -> maxY = 9, maxX = 9
	//!isSqaure -> 5x20 -> maxY = 4, maxX = 19
	
	public void determineBorders(NodeCoords pos) {
		if (!isSquare) {
			if (pos.getX() >= 0 && pos.getX() <= 9) {
				
				this.myHmapCoords.setBottomOrigin(new NodeCoords(0, 0));
				this.myHmapCoords.setTopOrigin(new NodeCoords(9,4));
				
				this.enemyHmapCoords.setBottomOrigin(new NodeCoords(10, 0));
				this.enemyHmapCoords.setTopOrigin(new NodeCoords(19, 4));
				
			} else {

				this.myHmapCoords.setBottomOrigin(new NodeCoords(10, 0));
				this.myHmapCoords.setTopOrigin(new NodeCoords(19, 4));
				
				this.enemyHmapCoords.setBottomOrigin(new NodeCoords(0, 0));
				this.enemyHmapCoords.setTopOrigin(new NodeCoords(9,4));
				
			}
		} else {
			if (pos.getY() >= 0 && pos.getY() <= 4) {
				
				this.myHmapCoords.setBottomOrigin(new NodeCoords(0, 0));
				this.myHmapCoords.setTopOrigin(new NodeCoords(9,4));
				
				this.enemyHmapCoords.setBottomOrigin(new NodeCoords(0, 5));
				this.enemyHmapCoords.setTopOrigin(new NodeCoords(9, 9));
				
			} else {

				this.myHmapCoords.setBottomOrigin(new NodeCoords(0, 5));
				this.myHmapCoords.setTopOrigin(new NodeCoords(9, 9));
				
				this.enemyHmapCoords.setBottomOrigin(new NodeCoords(0, 0));
				this.enemyHmapCoords.setTopOrigin(new NodeCoords(9,4));
				
			}
		}
	}


	public boolean isSquare() {

		this.isSquare = this.mapNodes.stream().filter(n -> n.getCoordinates().getY() > 4).findFirst().isPresent();

		return isSquare;
	}

	
}
