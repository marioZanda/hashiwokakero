package model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Set;


import utilities.StdCoord;
import utilities.StdEdge;
import utilities.StdNode;

@SuppressWarnings("deprecation")
public class StdGridModel extends Observable implements GridModel {

	ArrayList<StdEdge> edgeList;

	private int gridSize;
	private int width;
	private int height;
	
	private boolean isSolved = false;;
	
	private List<StdCoord> coordList;
	private List<Integer> degreeList;
	private List<StdNode> orderedNodeList;
	private Map<StdNode, List<StdNode>> neighborList;
	private Set<StdEdge> edgesPossibilities;
	
	public StdGridModel(int gridSize, int width, int height) {

		coordList = new ArrayList<StdCoord>();
		degreeList = new ArrayList<Integer>();
		orderedNodeList = new ArrayList<StdNode>();
		neighborList =  new HashMap<StdNode, List<StdNode>>();
		edgesPossibilities = new HashSet<StdEdge>();
		this.edgeList = new ArrayList<StdEdge>();
		
		this.gridSize = gridSize;
		this.width = width;
		this.height = height;
		
		this.isSolved = false;	
	}

	@Override
	public int getSize() {
		return this.gridSize;
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}


	@Override
	public boolean isValidPosition(StdCoord c) {
		return c.getColumn() <= gridSize && c.getRow() <= gridSize;
	}

	@Override
	public boolean hasNodeAt(StdCoord c) {
		
		return coordList.contains(c);
	}
	
	@Override
	public void placeNodeAt(StdCoord c, int degree) {
		coordList.add(c);
		degreeList.add(degree);
		notifyObservers();
		setChanged();
	}
	
	@Override
	public void removeNodeAt(StdCoord c) {
		degreeList.remove(coordList.indexOf(c));
		coordList.remove(c);
		notifyObservers();
		setChanged();
	}

	@Override
	public StdNode getNodeAt(StdCoord c) {
		
		if (coordList.contains(c)) {
			int index = coordList.indexOf(c);
			
			return new StdNode(index, degreeList.get(index), coordList.get(index));
		}
		
		return null;
	}
	
	
	public List<StdNode> getNodes() {
		
		List<StdNode> currentNodes = new ArrayList<StdNode>();
		
		
		for (int i = 0, size = coordList.size(); i < size; i++) {
			currentNodes.add(i, new StdNode(i, degreeList.get(i), coordList.get(i)));
		}
		
		return currentNodes;
	}
	
	public  ArrayList<StdEdge> getEdgeList() {
		return edgeList;
	}
	
	
	public void orderNodes() {
		
		orderedNodeList = getNodes();
		
		orderedNodeList.sort(new Comparator<StdNode>() {
			@Override
			public int compare(StdNode o1, StdNode o2) {
				if (o1.getDegree() > o2.getDegree())
					return -1;
				if (o1.getDegree() < o2.getDegree())
					return 1;
				return 0;
			}
		});
	}
	
	public boolean isFillable(StdNode node) {
		
		
		int possiblesBridgesNb = 0;
		
		for (StdNode neighbor : neighborList.get(node)) {
			
			possiblesBridgesNb += neighbor.getDegree();
			
		}
		
		return possiblesBridgesNb - node.getDegree() > 0 ;
		
	}
	
	/*************************************************************************
    					Rempli la liste des voisins de chaque noeud 
	 *************************************************************************/


	public void findNeighbors() {
		
		
		for (StdNode node : orderedNodeList) {
			
			List<StdNode> neighbors = new ArrayList<StdNode>();
			
			int row = node.getCoord().getRow();
			int column = node.getCoord().getColumn();
			
			
			for (int rNbColumn = column+1; rNbColumn < getWidth(); rNbColumn++) {
				
				if (hasNodeAt(new StdCoord(row, rNbColumn))) {

					neighbors.add(getNodeAt(new StdCoord(row, rNbColumn)));
					break;
				}
			}
			
			for (int lNbColumn = column-1; lNbColumn >= 0; lNbColumn--) {
				
				if (hasNodeAt(new StdCoord(row, lNbColumn))) {

					neighbors.add(getNodeAt(new StdCoord(row, lNbColumn)));
					break;
				}
			}
			
			for (int upNbRow = row-1; upNbRow >= 0; upNbRow--) {
				
				if (hasNodeAt(new StdCoord(upNbRow, column))) {
					
					neighbors.add(getNodeAt(new StdCoord(upNbRow, column)));
					break;
				}
			}
			
			for (int dwNbRow = row+1; dwNbRow < getHeight(); dwNbRow++) {
				
				if (hasNodeAt(new StdCoord(dwNbRow, column))) {
					
					neighbors.add(getNodeAt(new StdCoord(dwNbRow, column)));
					break;
				}
			}
			
			neighborList.put(node, neighbors);	
		}
		notifyObservers();
		setChanged();
	}
	
	/*************************************************************************
    				initialise la liste de tout les ponts possible
	 *************************************************************************/

	public void findPossibleEdges() {
		
		for (StdNode node : getNodes()) {
			
			neighborList.get(node);
			
			for (StdNode neighbor : neighborList.get(node)) {
				
				int maxBridgNb = Math.min(node.getDegree(), neighbor.getDegree());
				
				if (maxBridgNb > 2) {
					edgesPossibilities.add(new StdEdge(node, neighbor, 2));
				} else {
					edgesPossibilities.add(new StdEdge(node, neighbor));
				}
				
			}
		}
		
		
	}

	
	/*************************************************************************
    			Retourne vrai si le pont peut etre ajouté 
	 *************************************************************************/


	public boolean canAddEdge(StdEdge edge) {
	
		
		if (!edgesPossibilities.contains(edge)) {
			return false;
		}
		
		if (isFull(edge.getXNode()) || isFull(edge.getYNode())) {
			 return false;
		}
		
		 if (crossAny(edge)) {
			 return false;
		 }
		 
		 for (int i = 0; i < edgeList.size(); i++) {
			if(edgeList.get(i).equals(edge)) {
				if (edgeList.get(i).getEdgeNb() == 2) {
					return false;
				}
			}
		}
		 return true;

	}
	
	/*************************************************************************
    	Retourne vrai si le noeud a atteint son nombre de ponts maximal 
	 *************************************************************************/

	public boolean isFull(StdNode node) {
		
		int edgeNb = 0;
		
		for (int i = 0; i < edgeList.size(); i++) {
			
			if (edgeList.get(i).containsNode(node)) {
				edgeNb += edgeList.get(i).getEdgeNb();
			}
		}
		boolean result = edgeNb >= node.getDegree();

		return result;
	}
	
	/*************************************************************************
    				 Retourne true si tout les noeuds sont placés 
	 *************************************************************************/


	@Override
	public boolean isComplete() {
		return coordList.size() == gridSize;
	}


	/*************************************************************************
    						Processus de resolution 
	 *************************************************************************/

	@Override
	public boolean solve() {
		
		for(StdNode node : neighborList.keySet()) {
			if (neighborList.get(node).size() < 1) {
				return false;
			}
		}
		
		long start = System.currentTimeMillis();
		long end = start + 100*1000;

		//resolution
		int n = 1;	//represente l'iterateur sur les noeuds connectés au noeud 0
		while(n <= (getSize() - 1) && !isSolved && System.currentTimeMillis() < end){
			if(!findSolution(getNodes().get(0), getNodes().get(n))){
				n++;
			}else{
				isSolved = true;
			}
		}		
		setChanged();
		notifyObservers();
		

		return isSolved;
	}
	
	
	/*************************************************************************
    		Retourne vrai si l'ajout de ce pont cré un croisement 
	*************************************************************************/


		private boolean crossAny(StdEdge edge) {
		
		for (StdEdge stdEdge : edgeList) {
			if (edge.crossEdge(stdEdge)) {
				return true;
			}
		}
		
		return false;
	}
	
	
	/*************************************************************************
	                     Retourne vrai si le jeu est gagné 
	 *************************************************************************/
	public boolean checkIfWon(){
		
		for (StdNode node : getNodes()) {
			if (!isFull(node)) {
				return false;
			}
		}
		
		
		return true;
	}
	
	
	/*************************************************************************
    					verifie si des ponts sont ajoutables
	 *************************************************************************/

	public boolean checkIfPossibleMoves() {
		
		for (StdEdge possibleEdge : edgesPossibilities) {
			if (canAddEdge(possibleEdge)) {
			
				return true;
			}
		}

		return false;
		
	}
	
	/*************************************************************************
    							retire un pont 
	 *************************************************************************/

	public void removeEdge(StdEdge edge) {
		
		
		for (int i = 0; i < edgeList.size(); i++) {
				
			if (edgeList.get(i).equals(edge)) {
				
				if (edgeList.get(i).getEdgeNb() == 2) {		
					edgeList.get(i).decEdgeNb();
				} else {
					edgeList.remove(i);
				}
				
			}
			
		}
	}
	
	/*************************************************************************
    					retourne vrai si les sommets sont voisins 
	 *************************************************************************/

	boolean areNeigbhors(StdNode n1, StdNode n2) {
		
		
		for (StdNode neighbor : neighborList.get(n1)) {
			
			if (neighbor.equals(n2)) {
				return true;
			}
		}
		
		return false;
	}
	
	
	
	/*************************************************************************
     			boucle sur les noeuds a la recherche d'une solution
	 *************************************************************************/

	
	
	public boolean findSolution(StdNode n1, StdNode n2){
		//verification que les noeuds ne sont pas en fait le meme noeud
		if(n1.equals(n2)){
			return false;
		}
		
		//premiere etape : ajouter le premier noeud si possible 
		if (canAddEdge(new StdEdge(n1, n2))) {
			
			if (edgeList.contains(new StdEdge(n1, n2))) {
				
				for (StdEdge presentEdge : edgeList) {
					if (presentEdge.equals(new StdEdge(n1, n2))) {
						presentEdge.addEdgeNb();
						
					}
				}
			} else {
				edgeList.add(new StdEdge(n1, n2));
				
			}
			
		} else {
			//si ajout impossible retourner faux
			return false;
		}
		
		
		if(checkIfWon()){
			//si solution trouvée, return true!
			return true;
		}
		else if(!checkIfPossibleMoves()){
			//si la solutio n'est pas trouvée, et le jeu n'est pas gagné, retirer le pont ajouté et return false
			removeEdge(new StdEdge(n1, n2));
			
			return false;
		}
		//ETAPE RECURSIVE
		else{
			// on recupere dans pnode 1 et 2 les ids de n1 et n2
			int pnode1 = 0;
			int pnode2 = 0;
			for(int i = 0; i < gridSize -1; i++){
				if(n1 == getNodes().get(i))
					pnode1 = i;
				if(n2 == getNodes().get(i))
					pnode2 = i;
			}
			//la recursivite proprement ecrite
			do{
				//si n2 est le dernier ds noeuds on passe au prochain n1
				if(getNodes().get(pnode1).getDegree() == 0 || pnode2 == gridSize - 1){
					do{
						pnode1++;
						pnode2 = pnode1;
					}while(isFull(getNodes().get(pnode1)) && pnode1 < gridSize-1);
				}
				//sinon..
				else{
					pnode2++;
				}
				//appel recursif sur les nouveaux noeuds
				if(areNeigbhors(getNodes().get(pnode1), getNodes().get(pnode2))){
					if(findSolution(getNodes().get(pnode1), getNodes().get(pnode2))){
						return true;
					}
				}
			}while(!(pnode2 >= (gridSize - 2) && pnode1 >= (gridSize -1)));

			if(!findSolution(n1, n2)){
				removeEdge(new StdEdge(n1, n2));
				return false;
			}
		}
		return true;
	}

}
