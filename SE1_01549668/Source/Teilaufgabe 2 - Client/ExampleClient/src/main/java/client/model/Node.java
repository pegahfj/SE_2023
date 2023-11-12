package client.model;

import java.util.ArrayList;
import java.util.List;

import client.enums.EField;

/**
 * Description:
 * 
 * Instance  is used in :
 * 
 * @see 
 * 
 */



public class Node implements Comparable<Node>{

	private NodeCoords nodeCoordinates;
	private EField fieldType;
	private boolean myFort;
	private int dist;
	private Node prevNode;
	private boolean visited;
	
	
	public Node(NodeCoords nodeCoords, EField field) {
		this.nodeCoordinates = nodeCoords;
		this.fieldType = field;

	}

	
	public Node(NodeCoords nodeCoords, EField field, boolean fort) {
		this.nodeCoordinates = nodeCoords;
		this.fieldType = field;
		this.myFort = fort;
		
	}
	

	public List<NodeCoords> getNeighbNodeCoords(){
		List<NodeCoords> neighbList = new ArrayList<NodeCoords>();
		
		neighbList.add(this.nodeCoordinates.getLeftNodeCoords());
		neighbList.add(this.nodeCoordinates.getRightNodeCoords());
		neighbList.add(this.nodeCoordinates.getLowerNodeCoords());
		neighbList.add(this.nodeCoordinates.getUpperNodeCoords());
		
		return neighbList;
	}

	
	public NodeCoords getCoordinates() {
		
		return this.nodeCoordinates;
	}

	
	public void setNodeCoordinates(NodeCoords nodeCoordinates) {
		
		this.nodeCoordinates = nodeCoordinates;
	}

	
	public EField getFieldType() {
		
		return fieldType;
	}

	
	public void setFieldType(EField field) {
		
		this.fieldType = field;
	}

	
	public boolean hasFort() {
	
		return this.myFort;
	}

	
	public void setMyFort(boolean myFort) {
	
		this.myFort = myFort;
	}
	
	
	public int getDist() {
	
		return this.dist;
	}

	
	public void setDist(int distance) {
	
		this.dist = distance;
	}

	
	public Node getPrevNode() {
	
		return this.prevNode;
	}

	
	public void setPrevNode(Node prevNode) {
	
		this.prevNode = prevNode;
	}
	
	
	public NodeCoords getNodeCoordinates() {
		
		return this.nodeCoordinates;
	}

	
	public boolean getMyFort() {
	
		return this.myFort;
	}

	
	public boolean isVisited() {
	
		return this.visited;
	}

	
	public void setVisited(boolean visited) {
	
		this.visited = visited;
	}
	
	

    @Override
    public int hashCode() {
        final int prime = 31;
        
        int result = 1;
        
        result = prime * result + this.nodeCoordinates.getX();
        result = prime * result + this.nodeCoordinates.getY();
        
        return result;
    }

    
	@Override
	public boolean equals(Object other) {
	
		if (!(other instanceof Node)) {
        
			return false;
        }
        
		Node n = (Node) other;
		
		return nodeCoordinates.equals(n.nodeCoordinates) && fieldType.equals(n.fieldType);
	}
	
	
	 @Override public String toString() {

		return "|___ "+this.getCoordinates()+" / "+this.getFieldType().toString()+"___| ";
	 
	}

	 
	@Override
	public int compareTo(Node other) {
	
		return compare(this.dist, other.dist);
	}

	
	private static int compare(int dist1, int dist2) {
	
		return (dist1 < dist2) ? -1 : ((dist1 == dist2) ? 0 : 1);
	}
	 
}
