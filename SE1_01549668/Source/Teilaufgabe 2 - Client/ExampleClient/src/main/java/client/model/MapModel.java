package client.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import client.exceptions.MapModelException;

public class MapModel {
    protected List<Node> mapNodes;
    // protected NodeCoords lowerOrigin;
    // protected NodeCoords upperOrigin;

    private static Logger logger = LoggerFactory.getLogger(MapModel.class);

    
    public MapModel(){
        
    	this.mapNodes = new ArrayList<Node>();
    }

    
    public Node getRandomNode() {
        Random rand = new Random();
        
        int n = rand.nextInt(this.mapNodes.size());
    
        return this.mapNodes.get(n);
    }

    
    public boolean validNode(Node node) {
        
    	Node check =  mapNodes.stream().filter(n -> n.equals(node)).findFirst().orElse(null);
        
    	return check == null ? false : true;
    }

    
    public List<Node> getNeighbNodes(Node node){
		
    	List<Node> neighbList = new ArrayList<Node>();
  
        for(NodeCoords neighbCoords: node.getNeighbNodeCoords()){
        	
        	try {
        		
        		Node optional = getNodeByCoords(neighbCoords);
    			
        		neighbList.add(optional);
			
        	} catch (MapModelException e) {
				
//				logger.warn("Something went wrong when stepped into potential neighbour! Exception Msg: " + e.getMessage());
			}         
        }  
		
        return neighbList;
	}
    
    
    public Node getNodeByCoords(NodeCoords c) throws MapModelException {
    	
    	Optional<Node> optional = this.mapNodes.stream().filter(n -> n.getCoordinates().equalsTo(c)).findFirst();
    	
    	if (!optional.isPresent()) {
    		
			throw new MapModelException("Node Not Found In MapNodes");
			
		}else {
			
			return optional.get();
		}

    }
    
    
    public Node getLeftNodeCoords(Node source) throws MapModelException {
    	
    	Node optional = this.getNodeByCoords(source.getCoordinates().getLeftNodeCoords());
    	
    	return optional;		
    }
    
    
    public Node getRightNodeCoords(Node source) throws MapModelException {

    	Node optional = this.getNodeByCoords(source.getCoordinates().getRightNodeCoords());
    	
    	return optional;
    }
    
    
    public Node getLowerNodeCoords(Node source) throws MapModelException {

    	Node optional = this.getNodeByCoords(source.getCoordinates().getLowerNodeCoords());
    	
    	return optional;
    }
    
    
    public Node getUpperNodeCoords(Node source) throws MapModelException {

    	Node optional = this.getNodeByCoords(source.getCoordinates().getUpperNodeCoords());
    	
    	return optional;
    }
    

   

    public List<Node> getMapNodes() {
        
    	return this.mapNodes;
    }

    public void setMapNodes(List<Node> mapNodes) {
        
    	this.mapNodes = mapNodes;
    }

}
