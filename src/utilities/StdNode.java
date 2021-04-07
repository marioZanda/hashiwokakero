package utilities;


public class StdNode implements Node {
	
	private Coord coord;
	private int degree;
	private int id;
	//private int connectionsNb;
	
	public StdNode(int id, int degree, Coord coord) {
		
		this.id = id;
		this.degree = degree;
		this.coord = coord;
		//this.connectionsNb = 0;
	}
	
	/*public int getConnectionNb () {
		return connectionsNb;
	}
	
	public void setConnectionNb(int n) {
		this.connectionNb = n;
	
	}
	*
	*
	*
	*
	*
	*
	*
	*/
	
	public int getId() {
		return this.id;
	}

	@Override
	public int getDegree() {
		return this.degree;
	}

	@Override
	public Coord getCoord() {
		return this.coord;
	}

	@Override
	public void setCoord(Coord c) {
		
		this.coord = c;
	}

	@Override
	public void setDegree(int degree) {
		
		this.degree = degree;
	}
	
	@Override
	public boolean equals(Object other) {
		
		if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        StdNode n = (StdNode) other;
			return this.id == n.getId();		
	}
	

	@Override
	public int hashCode() {
	
        return getCoord().hashCode();
    
	}
	
	public boolean canJoin(StdNode node) {
		
		if (node == null) return false;
		
		return getCoord().getRow() == node.getCoord().getRow() || getCoord().getColumn() == node.getCoord().getColumn(); 
	}
	
	
	public String toString() {
		return "{"+getId()+"|"+getCoord()+","+getDegree()+"}";
		
	}
}
