package client.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class PathFinderService {
	
	private GameModelService gameService;
	
	public PathFinderService(GameModelService gs) {
		
		this.gameService = gs;
		
	}

	public List<Node> reconstructPath(Node source, Node destination) {
		
		this.dijkstra(source);
		
		List<Node> path = new ArrayList<Node>();
		
		Node next = destination;
		
		while (next != null && !next.equals(source)) {

			path.add(next);
			next = next.getPrevNode();
		}
		
		Collections.reverse(path);

		return path;

	}
	
	
	public void dijkstra(Node source) {
		PriorityQueue<Node> openList = new PriorityQueue<>();
		
		List<Node> visited = new ArrayList<Node>();
		
		List<Node> mapNodes = this.gameService.getGameState().getMap().getMapNodes();
		
		//set all dist to infinity
		mapNodes.stream().forEach(n -> {
			n.setDist(Integer.MAX_VALUE);
			n.setPrevNode(null);
		});
		
		//add source to pq
		openList.add(source);
		source.setDist(0);
		
		//while visited != size
		while(visited.size() != mapNodes.size()) {
			Node current = openList.poll();
			
			if(current == null){
				// throw exception
			}
			
//			this.processNeighbs(current);
			int edgeDistance = -1; 
	        int newDistance = -1; 
	   
	        // process all neighbouring nodes
	        List<Node> neighbs = this.gameService.getGameState().getMap().getNeighbNodes(current);
	        for (Node n: neighbs) { 
	   
	            //  proceed only if current node is not in 'visited'
	            if (!visited.contains(n)) { 
	                edgeDistance =n.getFieldType().getCosts(); 
	                newDistance = current.getDist() + edgeDistance; 
	   
	                // compare distances 
	                if (newDistance < n.getDist()) { 
	                    n.setDist(newDistance); 
	                    n.setPrevNode(current);
	                }
	   
	                // Add the current vertex to the PriorityQueue 
	                openList.add(n); 
	            } 
	        } 
			
		}
		
		
		// u is removed from PriorityQueue and has min distance  
//        int u = pqueue.remove().node; 
		
		// add u to visited and process its neighb
	}
	
//	private void processNeighbs(Node current) {
//		int edgeDistance = -1; 
//        int newDistance = -1; 
//   
//        // process all neighbouring nodes
//        List<Node> neighbs = this.gameState.getMap().getNeighbNodes(current);
//        for (Node n: neighbs) { 
//   
//            //  proceed only if current node is not in 'visited'
//            if (!visited.contains(n)) { 
//                edgeDistance =n.getFieldType().getCosts(); 
//                newDistance = current.getDist() + edgeDistance; 
//   
//                // compare distances 
//                if (newDistance < n.getDist()) 
//                    n.setDist(newDistance); 
//   
//                // Add the current vertex to the PriorityQueue 
//                pqueue.add(new Node(v.node, dist[v.node])); 
//            } 
//        } 
//	}
	
	
	
	
	
	
	
}
