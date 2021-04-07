import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JComponent;

import model.StdGridModel;
import utilities.Coord;
import utilities.Direction;
import utilities.StdCoord;
import utilities.StdEdge;
import utilities.StdNode;

public class GraphicGrid extends JComponent {

	
	public static final int GRID_SQUARE_EDGE_SIZE = 80;
	public static final int GRID_MARGIN = 3;
	public static final int GRID_CIRCLE_MARGIN = 20;
	public static final int EDGE_CENTER_MARGIN = 2;
	public static final int EDGE_SPACING = GRID_SQUARE_EDGE_SIZE / 7;
 	private StdGridModel model;
	
	public GraphicGrid(StdGridModel model) {
		
		this.model = model;
		Dimension d = new Dimension(GRID_SQUARE_EDGE_SIZE * model.getWidth(), GRID_SQUARE_EDGE_SIZE * model.getHeight());
		setPreferredSize(d);
	}
	
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		drawGrid(g);
		drawIsland(g, Color.BLUE);
		drawEdges(g, Color.RED);
		
	}
	
	private void drawGrid(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.WHITE);
		g.fillRect(GRID_MARGIN, GRID_MARGIN, getWidth() - GRID_MARGIN *2, getHeight() - GRID_MARGIN *2);
		
		g.setColor(Color.BLACK);
		
		for (int i = 0; i < model.getWidth()+ 2; i++) {
			g.drawLine(
					GRID_MARGIN + i * GRID_SQUARE_EDGE_SIZE, 
					GRID_MARGIN, 
					GRID_MARGIN + i * GRID_SQUARE_EDGE_SIZE, 
					getHeight() - GRID_MARGIN);

		}
		
		for (int i = 0; i < model.getHeight()+2; i++) {
			
			g.drawLine(
					GRID_MARGIN, 
					GRID_MARGIN + i * GRID_SQUARE_EDGE_SIZE, 
					getWidth() - GRID_MARGIN,
			GRID_MARGIN + i * GRID_SQUARE_EDGE_SIZE); 
		}
	}
	
	private void drawIsland(Graphics g, Color c) {
		FontMetrics fm = g.getFontMetrics();
		
		g.setColor(c);
		
		for (StdNode node : model.getNodes()) {
				
			int x =   node.getCoord().getColumn() *GRID_SQUARE_EDGE_SIZE + GRID_SQUARE_EDGE_SIZE/4  +GRID_MARGIN;
			int y =   node.getCoord().getRow() *GRID_SQUARE_EDGE_SIZE + GRID_SQUARE_EDGE_SIZE/4;
			
			g.drawOval(x ,y , GRID_SQUARE_EDGE_SIZE/2, GRID_SQUARE_EDGE_SIZE/2);
			
			Font myFont = new Font ("Courier New", 1, 17);
			g.setFont (myFont);
			
			String s = String.valueOf(node.getDegree());
			int sWidth = fm.stringWidth(s);

			g.drawString(s, x + GRID_SQUARE_EDGE_SIZE/4 - sWidth/2, y + GRID_SQUARE_EDGE_SIZE/4 + sWidth/2);
		}
	}
	
	private void drawEdges(Graphics g, Color c) {
		
		g.setColor(c);
		
		for (StdEdge edge : model.getEdgeList()) {
		
			
			int x1 = GRID_MARGIN  + edge.getXNode().getCoord().getColumn() * GRID_SQUARE_EDGE_SIZE
					+ ((GRID_SQUARE_EDGE_SIZE)/2);
			
			int y1 = GRID_MARGIN + edge.getXNode().getCoord().getRow() * GRID_SQUARE_EDGE_SIZE
					+ ((GRID_SQUARE_EDGE_SIZE)/2);
			
			
			int x2 = GRID_MARGIN  + edge.getYNode().getCoord().getColumn() * GRID_SQUARE_EDGE_SIZE
					+ ((GRID_SQUARE_EDGE_SIZE)/2);
			int y2 = GRID_MARGIN + edge.getYNode().getCoord().getRow() * GRID_SQUARE_EDGE_SIZE
					+ ((GRID_SQUARE_EDGE_SIZE)/2);
			
			
			switch (edge.getDirection()) {
			case UP: 
				y2 += GRID_SQUARE_EDGE_SIZE /4 -GRID_MARGIN;
				y1 -= GRID_SQUARE_EDGE_SIZE /4;
				break;
			case DOWN:
				y1 += GRID_SQUARE_EDGE_SIZE /4 -GRID_MARGIN;
				y2 -= GRID_SQUARE_EDGE_SIZE /4 +GRID_MARGIN;
				break;
			case LEFT:
				x2 += GRID_SQUARE_EDGE_SIZE /4;
				x1 -= GRID_SQUARE_EDGE_SIZE /4;
				break;
			case RIGHT:
				x1 += GRID_SQUARE_EDGE_SIZE /4;
				x2 -= GRID_SQUARE_EDGE_SIZE /4;
				break;			
			}
			
			int edgeNb = edge.getEdgeNb();
			
			if (edgeNb == 1) {
				g.drawLine(x1, y1, x2, y2);
				
			} else if (edgeNb == 2) {
				if (edge.isVertical()) {
					
					g.drawLine(x1-EDGE_SPACING/2, y1, x2 -EDGE_SPACING/2, y2);
					g.drawLine(x1+EDGE_SPACING/2, y1, x2 +EDGE_SPACING/2, y2);
					
				} else if (edge.isHorizontal()) {
					
					g.drawLine(x1, y1 -EDGE_SPACING/2, x2, y2 -EDGE_SPACING/2);
					g.drawLine(x1, y1 +EDGE_SPACING/2, x2, y2 +EDGE_SPACING/2);
					
				}
			}
			
		}
	}

}
