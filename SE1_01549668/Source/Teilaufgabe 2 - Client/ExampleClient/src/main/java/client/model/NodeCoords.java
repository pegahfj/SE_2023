package client.model;
/**
 * Instead of using Java's local library 'Point' we set up this class to
 * represent the coordinates of each Node on the map, to be able to adjust it if
 * needed.
 * 
 * Instance of NodeCoords is used in :
 * 
 * @see Node
 * 
 */
public class NodeCoords {
    private int x;
    private int y;

    public NodeCoords(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public NodeCoords getUpperNodeCoords() {
		return new NodeCoords(this.x, this.y - 1);
	}
	
	public NodeCoords getLowerNodeCoords() {
		return new NodeCoords(this.x, this.y + 1);
	}
	
	public NodeCoords getLeftNodeCoords() {		
		return new NodeCoords(this.x - 1, this.y);
	}

	public NodeCoords getRightNodeCoords() {		
		return new NodeCoords(this.x + 1, this.y);
	}
    
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {

        return "[" + this.x + "," + this.y + "]";

    }

	public boolean equalsTo(NodeCoords other) {
		return this.x == other.getX() && this.y == other.getY();
	}

    @Override
    public int hashCode() {
        return 1 * 31 * Double.valueOf(x).hashCode() + 31 * Double.valueOf(y).hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof NodeCoords)) {
            return false;
        }
        NodeCoords n = (NodeCoords) other;
        return x == n.x && y == n.y;
    }
}