package utilities;

import java.util.ArrayList;
import java.util.List;


public class StdEdge {

	private StdNode xNode;
	private StdNode yNode;
	private int edgeNb;
	private Direction direction;
	
	
	public StdEdge(StdNode xNode, StdNode yNode, int edgeNb) {
		
		this.xNode  = xNode;
		this.yNode  = yNode;
		this.edgeNb = (edgeNb > 2 ) ? 2 : edgeNb;
		this.initDirection();
	}
	
	public StdEdge(StdNode xNode, StdNode yNode) {
	
		this.xNode  = xNode;
		this.yNode  = yNode;
		this.edgeNb = 1;
		this.initDirection();
	}
	
	private void initDirection() {
		
		if (xNode.getCoord().getColumn() == yNode.getCoord().getColumn()) {
			if (xNode.getCoord().getRow() < yNode.getCoord().getRow()) {
				this.direction = Direction.DOWN;
			} else {
				this.direction = Direction.UP;
			}
		}else {
			if (xNode.getCoord().getColumn() < yNode.getCoord().getColumn()) {
				this.direction = Direction.RIGHT;
			} else {
				this.direction = Direction.LEFT;
			}
		}
		
	}

	public StdNode getXNode() {
		return this.xNode;
	}

	public StdNode getYNode() {
		return this.yNode;
	}
	
	public int getEdgeNb() {
		return this.edgeNb;
	}
	
	public Direction getDirection() {
		return this.direction;
	}
	
	public boolean containsNode(StdNode node) {
		
		return (node.equals(getXNode()) || node.equals(getYNode()));
		
	}
	
	/*public void connectEdge() {
		int cNx = this.getXnode.getDegree();
		this.getXnode.setConnectionNb(cNx-getEdgeNb());
		
		int cNy = this.getYnode.getDegree();
		this.getYnode.setConnectionNb(cNy-getEdgeNb());
		
		
		public void disconnectEdge() {
		int cNx = this.getXnode.getConnectionNb();
		this.getXnode.setConnectionNb(cNx+1);
		
		int cNy = this.getYnode.getConnectionNb();
		this.getYnode.setConnectionNb(cNy+1);
	}*/
	
	
	public void addEdgeNb() {
		if (edgeNb == 1) this.edgeNb = 2;
	}
	
	public void decEdgeNb() {
		if (edgeNb == 2) this.edgeNb = 1;
	}

	public void setXNode(StdNode xNode) {
		//Contract.checkCondition(xNode != null, "Sommet invalide ("+ xNode +") !");
	
		this.xNode = xNode;
	}

	public void setYNode(StdNode yNode) {
		//Contract.checkCondition(yNode != null, "Sommet invalide ("+ yNode +") !");
		
		this.yNode = yNode;
	}
	
	public boolean isJoining(int x, int y) {
		
		boolean haveX = (getXNode().getId() == x || getYNode().getId() == x);
		boolean haveY = (getXNode().getId() == y || getYNode().getId() == y);
		
		return (haveX && haveY);
	}
	
	public boolean isVertical() {
		return direction.vertical();
	}
	
	public boolean isHorizontal() {
		return direction.horizontal();
	}
	
	public boolean crossEdge(StdEdge edge) {
		if (! hasCommonNode(edge)) {
			if (isHorizontal() && edge.isVertical()) {
				
				if (getXNode().getCoord().getRow() >= Math.min(edge.getXNode().getCoord().getRow(), edge.getYNode().getCoord().getRow())
						&& getXNode().getCoord().getRow() <= Math.max(edge.getXNode().getCoord().getRow(), edge.getYNode().getCoord().getRow())) {
					return (
							edge.getXNode().getCoord().getColumn() >= Math.min(xNode.getCoord().getColumn(), yNode.getCoord().getColumn())
							
							&& edge.getXNode().getCoord().getColumn() <= Math.max(xNode.getCoord().getColumn(), yNode.getCoord().getColumn())); 

				}
				return false;
								
			} else if (isVertical() && edge.isHorizontal()) {
				
				if (getXNode().getCoord().getColumn() >= Math.min(edge.getXNode().getCoord().getColumn(), edge.getYNode().getCoord().getColumn())
						&& getXNode().getCoord().getColumn() <= Math.max(edge.getXNode().getCoord().getColumn(), edge.getYNode().getCoord().getColumn())) {
					return (
							edge.getXNode().getCoord().getRow() >= Math.min(xNode.getCoord().getRow(), yNode.getCoord().getRow())
							
							&& edge.getXNode().getCoord().getRow() <= Math.max(xNode.getCoord().getRow(), yNode.getCoord().getRow())); 

				}
				return false;
				
			} 
		} 
		return false;
	}
	
	
public boolean equals(Object other) {
		
		if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        StdEdge e = (StdEdge) other;
		return this.xNode.equals(e.getXNode()) && this.yNode.equals(e.getYNode()) 
					|| this.xNode.equals(e.getYNode()) && this.yNode.equals(e.getXNode());
	}
	
	@Override
	public int hashCode() {
		
		int x = xNode.hashCode();
		int y = yNode.hashCode();
		int tmp = ( x +  ((y+1)/2));
        return x +  ( tmp * tmp);
	}

	
	public boolean hasCommonNode(StdEdge edge) {
		return (xNode.equals(edge.getXNode()) || xNode.equals(edge.getYNode()) || yNode.equals(edge.getXNode()) || yNode.equals(edge.getYNode()));
	}
	
	public String toString() {
		return "["+getXNode()+"  "+getYNode()+","+getEdgeNb()+","+getDirection()+"]";
	}
	
	
	public List<StdNode> getNodes() {
		
		List<StdNode> edgeNodes = new ArrayList<StdNode>();
		
		edgeNodes.add(xNode);
		edgeNodes.add(yNode);
		
		return edgeNodes;
		
	}
	
	
}
