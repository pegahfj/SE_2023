package client.model;



public class HalfMapModel extends MapModel{

    private Node fortPos;
    
        
    public HalfMapModel(){
        super();
        
        this.fortPos = null;
    }


	public Node getFortPos() {
		return fortPos;
	}


	public void setFortPos(Node fortPos) {
		this.fortPos = fortPos;
	}
    

}
