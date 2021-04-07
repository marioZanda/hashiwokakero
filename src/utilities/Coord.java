package utilities;


/**
 * Définit la notion coordonée dans une grille
 * @inv <pre>
 *    0 <= getRow()
 *    0 <= getColumn() 
 *    </pre>
 * @cons <pre>
 * $DESC$ Une coordonnée dans une grille.
 * $ARGS$ int row, int column
 * $PRE$
 *     row >= 0
 *     column >= 0
 * $POST$
 *     getRow() == row
 *     getColumn == column </pre>
 */

public interface Coord {

	// REQUETES
    
    /**
     * Retourne la ligne de la coordonnée 
     */
	int getRow();
	
	/**
     * Retourne la colonne de la coordonnée 
     */
	int getColumn();
	  
	
	// COMMANDES
    
    /**
     * Fixe la ligne de la coordonnée.
     * @pre <pre>
     *    row >= 0 </pre>
     * @post <pre>
     *    getRow() == row </pre>
     */
	void setRow(int row);
	
	
	/**
     * Fixe la colonne de la coordonnée.
     * @pre <pre>
     *    column >= 0 </pre>
     * @post <pre>
     *    column() == row </pre>
     */
	void setColumn(int column);
}