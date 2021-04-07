package utilities;

/**
 * Définit la notion de sommet dans la grille
 * @inv <pre>
 *    getDegree() >= 0
 *    getId() != null
 *    </pre>
 * @cons <pre>
 * $DESC$ Une coordonnée dans une grille.
 * $ARGS$ E id 
 * $PRE$
 * $POST$
 *     getDegree() == 0
 *     getCoord == null
 *     getId() = id
 *      </pre>
 */
public interface Node {
	
	public static int MAX_DEGREE = 8;
	
	// REQUETES
	
	int getId();
    
   /**
     * Retourne le degré du sommet 
     */
	int getDegree();
	
	/**
     * Retourne la coordonnée du sommet
     */
	Coord getCoord();
	

	//COMMANDES
			
	/**
     * Fixe la coordonnée du sommet.
     * @post <pre>
     *    getCoord == c </pre>
     */
	void setCoord(Coord c);
	
	
	/**
     * Fixe le degré du sommet.
     * @pre <pre>
     *    degree >= 0 </pre>
     * @post <pre>
     *    getDegree == degree </pre>
     */
	void setDegree(int degree);
	
}
