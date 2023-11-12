package client.model;

public class CoordniatesSystem {
	
	private NodeCoords topOrigin;
	private NodeCoords bottomOrigin;
	
	
	public CoordniatesSystem() {

	}
	
	public CoordniatesSystem(NodeCoords top, NodeCoords bottom) {
		this.topOrigin = top;
		this.bottomOrigin = bottom;
	}
	
	
	public NodeCoords getTopOrigin() {
		return topOrigin;
	}
	
	
	public void setTopOrigin(NodeCoords topOrigin) {
		this.topOrigin = topOrigin;
	}
	
	
	public NodeCoords getBottomOrigin() {
		return bottomOrigin;
	}
	
	
	public void setBottomOrigin(NodeCoords bottomOrigin) {
		this.bottomOrigin = bottomOrigin;
	}
	
	
	
	

}
