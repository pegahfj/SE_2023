package client.model;


import java.util.List;
import java.util.PriorityQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import client.enums.EField;
import client.exceptions.HalfmapException;


public class HalfMapService {
  
    private static final int WATER_COUNT = 8;
    private static final int MOUNTAIN_COUNT = 8;

    private HalfMapModel hmap;
    private int waterFields;
    private int mountainFields;

    private static Logger logger = LoggerFactory.getLogger(HalfMapService.class);
    
    public HalfMapService() {
    	
    	this.hmap = new HalfMapModel();
    	this.waterFields = 0; 
        this.mountainFields = 0; 
    }
    
    
	public HalfMapModel generateHalfMap(){
        
		boolean hmapValidation = false;
        
		int numOfTries = 0;
        while (!hmapValidation && numOfTries<10){
            
        	logger.info("Generating HalfMap ... ");
            
            try {
            
            	this.reset();
                this.makeBase(new NodeCoords(10, 5));
                this.waterGenerator();
                this.mountainGenerator();
                this.placeFort();
				this.validateHalfMap();
				
				++numOfTries;
				
				hmapValidation = true;
			
            } catch (HalfmapException e) {
			
				logger.error("Caught Error In Validating HalfMap! Exception Msg: " + e.getMessage());
		
			}
        }
        
        return this.hmap;
    }

	
    private void reset() {
    	
    	this.waterFields = 0; 
        this.mountainFields = 0; 
        
    	this.hmap.getMapNodes().clear();
    	
    	this.hmap.setFortPos(null);
    }

    
    private void makeBase(NodeCoords upperLimit) {
       
    	for (int y = 0; y < upperLimit.getY(); y++){
        
    		for (int x = 0; x < upperLimit.getX(); x++) {
            
    			this.hmap.getMapNodes().add(new Node(new NodeCoords(x, y), EField.GRASS));
            }
        }
    }

    
//    private void waterGenerator() { 
//        
//    	while (waterFields <= WATER_COUNT) {
//        
//    		Node toChange = this.hmap.getRandomNode();
//            
//    		if(toChange.getFieldType() != EField.GRASS){
//            
//    			continue;
//            
//    		} else {
//                			
//    			toChange.setFieldType(EField.WATER);
//                
//    			this.waterFields++;
//            }
//        }
//    }


    private void waterGenerator() { 
      
	  	while (waterFields < WATER_COUNT) {
	  		Node toChange = this.hmap.getRandomNode();
          
	  		if(toChange.getFieldType() != EField.GRASS){
	          
	  			continue;
	          
	  		} else {
	          
	  			placeWaterField(toChange);
	  			
	         }
	  	}
    	
    }

    public void placeWaterField(Node toWater){
    	List<Node> neighbs = this.hmap.getNeighbNodes(toWater);
    	
    	neighbs.stream().forEach(n -> {
        	 if (n.getFieldType() == EField.WATER){
        		 toWater.setFieldType(EField.WATER);
        		 this.waterFields++;
                 return;
             }
         });         
         

         for(Node n: neighbs){ 
             if(n.getFieldType() != EField.GRASS) {
                 continue;
             } else {
                 toWater.setFieldType(EField.WATER);
                 n.setFieldType(EField.WATER);
                 this.waterFields += 2;
                 return;
             }
         }
     }
    
    
    private void mountainGenerator() { 
        
    	while (mountainFields <= MOUNTAIN_COUNT) {
            
        	Node toChange = this.hmap.getRandomNode();
            
            if(toChange.getFieldType() != EField.GRASS){
            	
            	continue;
            
            } else {
            	
                toChange.setFieldType(EField.MOUNTAIN);
                
                this.mountainFields++;
            }
        }
    }

    
    private void placeFort() {
       
    	while (this.hmap.getFortPos() == null) {
        
    		Node toChange = this.hmap.getRandomNode();
            
    		if(toChange.getFieldType() != EField.GRASS){
            
    			continue;
            
    		} else {
            
    			toChange.setMyFort(true);
                
    			this.hmap.setFortPos(toChange); 
                
    			logger.info("Placed fort " + toChange);
            }
        }
    }
    
   
    public void validateHalfMap() throws HalfmapException {		
		
    	flood(this.hmap.getFortPos());

		for (Node n: this.hmap.getMapNodes()) {
			
			if (n.isVisited() != true && n.getFieldType()!= EField.WATER) {
			
				logger.error("Island Found");
				
				throw new HalfmapException("Island Found -> Failed Validation!");
			}
		}
		
		if(countBorderWater() == false) {
			
			throw new HalfmapException("Borders has too many water fields -> Failed Validation!");

		}
	}

    
    
    private void flood(Node source) {

        PriorityQueue<Node> openList = new PriorityQueue<>();

        openList.add(source);
        
        while(!openList.isEmpty()) {
        	//get the next node from queue
        	Node nextNode = openList.poll();
        	

        	//check if this node fits for flooding
			if(nextNode.getFieldType() == EField.WATER)
				continue;
			
			if (nextNode.isVisited() == true)
				continue;
			
			// act of flooding
			nextNode.setVisited(true);
			
			// traversing from current node to its neighbours
			this.hmap.getNeighbNodes(nextNode).stream().forEach(n -> {if(!openList.contains(n)) openList.add(n);});

        	
        }
    }
        
        
    private boolean countBorderWater() {
    	
    	int leftBorder = 0;
    	int rightBorder = 0;
    	int upperBorder = 0;
    	int lowerBorder = 0;
		
		for (Node n : this.hmap.getMapNodes())
			
			if (n.getFieldType() == EField.WATER) { 
															
				if (n.getCoordinates().getX() == 0)
					
					leftBorder++;
				
				if (n.getCoordinates().getX() == 9)
					
					rightBorder++;
				
				if (n.getCoordinates().getY() == 0)
					
					upperBorder++;
				
				if (n.getCoordinates().getY() == 4)
					
					lowerBorder++;
			}
		
		if (leftBorder > 2 || rightBorder > 2 || upperBorder > 4 || lowerBorder > 4)
			return false;
		
		return true;
    }
    
    


        	
        	
        	
        	
    //ToDo: this is unnecessary if valid node is only being part of the map
    // public void checkRawNode(Node toCheck) throws HalfmapException {
    //     if (toCheck == null) 
    //         throw new HalfmapException("checkRawNode Method: Raw Node To Be Change -> Validation Failed: Null!");
    //     if (!super.validNode(toCheck)) 
    //         throw new HalfmapException("checkRawNode Method: Raw Node To Be Change -> Validation Failed: Not in map!");
        
    //     if (toCheck.getFieldType() != EField.GRASS) 
    //         throw new HalfmapException("checkRawNode Method: Raw Node To Be Change -> Validation Failed: Not grass!");
    // }
}
