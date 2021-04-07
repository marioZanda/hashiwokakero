package utilities;


/**
 * Définit une arrête du graphe de sommet x et y
 * @inv <pre>
 *     getXNode() != null
 *     getYNode() != nulll </pre>
 * @cons <pre>
 * $DESC$ Une arrête entre deux sommet x et y.
 * $ARGS$ Node x, Node y
 * $PRE$
 *     x != null
 *     y != null
 * $POST$
 *     getXnode() == x
 *     getYnode() == y </pre>
 */
public interface Edge<S extends Node> {
	
	
	int getEdgeNb();
	
	/**
     * Retourne le sommet x de l'arrête 	     
     * */
	S getXNode();
	
	
	/**
     * Retourne le sommet y de l'arrête 	     
     * */
	S getYNode();
	
	
	void setEdgeNb(int edgeNb);
	
	/**
     * Fixe le sommet y de l'arrête
     * @pre <pre>
     *  xNode != null </pre>
     * @post <pre>
     *  getXNode() == xNode 	     
     * */
	void setXNode(S xNode);
	
	
	/**
     * Fixe le sommet y de l'arrête
     * @pre <pre>
     *  yNode != null </pre>
     * @post <pre>
     *  getYNode() == yNode 	     
     * */
	void setYNode(S yNode);
	
}