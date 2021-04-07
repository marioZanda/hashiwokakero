package model;

import utilities.StdCoord;
import utilities.StdNode;

public interface GridModel {
	
	
	//REQUÊTES
	
	/**
	 * Retourne la taille de la grille
	 */
	int getSize();
	
	
	/**
	 * Précise si la coordonnée est valide dans la grille.
	 * @pre <pre>
	 *  c != null </pre>
	 */
	boolean isValidPosition(StdCoord c);
	  
	  
	/**
	 * Précise si un sommet existe à la position StdCoord.
	 *@pre <pre>
	 * isValidPosition(c) </pre>
	 */
	boolean hasNodeAt(StdCoord c);
	
	
	
	/**
	 * Renvoie le noeud à la position StdCoord.
	 *@pre <pre>
	 * isValidPosition(c) </pre>
	 */
	StdNode getNodeAt(StdCoord c);
	
	
	
	
	
	  
	/**
	 * Ajoute un sommet à la position c.
	 * @return 
	 *@pre <pre>
	 * isValidPosition(c) </pre>
	 */
	void placeNodeAt(StdCoord c, int degree);
	  
	/**
	 * Retire le sommet à la position [line, column].
	 *@pre
	 * isValidPosition(line, column)
	 */
	void removeNodeAt(StdCoord c);
	  
	  
	/**
	 * Précise si tous les sommets sont placés sur la grille.
	 * @return 
	 *  
	 */
	boolean isComplete();
	  
	/**
	 * Recherche une solution 
	 * @return 
	 *@pre
	 * isComplete()
	 */
	boolean solve();
}
