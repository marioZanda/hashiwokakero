import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import model.StdGridModel;
import utilities.StdCoord;

public class Hashiwokakero {

	private JFrame mainFrame;
	private GraphicGrid grid;

	private JButton[] degreeButonTab;
	private JButton solveButton;
	private JButton resetButton;
	private JButton activeDegreeButton;
	private int n,w,h;
	private StdGridModel model;
	private JLabel nodeNbLabel;
	private int placeNodeNb;
	private boolean finish = false;
	public Hashiwokakero(int n, int w, int h) {
		this.n = n;
		this.w = w;
		this.h = h;
		placeNodeNb = 0;
		createModel();
		createView();
		placeComponents();
		createController();
	}

	private void createModel() {
		this.model = new StdGridModel(n,w,h);
	}

	private void createView() {
		mainFrame = new JFrame("Hashiwokakero");
		mainFrame.setResizable(false);

		degreeButonTab = new JButton[8];
		grid = new GraphicGrid(this.model);

		for (int i = 0; i < 8; i++) {
			degreeButonTab[i] = new JButton(""+(i+1));
		}

		solveButton = new JButton("Solutionner !");
		resetButton = new JButton("Réinitialiser");

		nodeNbLabel = new JLabel("Nombre d'îles : " + n);
	}

	private void placeComponents() {
		//JPanel p = new JPanel(new Flow(0, 1)); {



		JPanel q = new JPanel(new FlowLayout(FlowLayout.CENTER)); {
			JPanel r = new JPanel(new GridLayout(0, 1)); {
				JPanel s = new JPanel(new FlowLayout(FlowLayout.CENTER));{
					s.add(nodeNbLabel);
				}
				r.add(s);

				s = new JPanel(new FlowLayout(FlowLayout.CENTER)); {
					JPanel t = new JPanel(new GridLayout(2, 4)); {

						for (int i = 0; i < 8; i++) {
							t.add(degreeButonTab[i]);
						}							
					}
					s.add(t);
				}
				r.add(s);

				s = new JPanel(new FlowLayout(FlowLayout.CENTER)); {
					s.add(solveButton);
					s.add(resetButton);
				}
				r.add(s);
			}
			q.add(r);
			q.setBorder(BorderFactory.createEtchedBorder());
		}

		mainFrame.add(q, BorderLayout.EAST);

		q = new JPanel(); {
			q.add(grid, BorderLayout.CENTER);

		}
		mainFrame.add(q, BorderLayout.CENTER);
	}

	@SuppressWarnings({ "deprecation" })
	private void createController() {

		((Observable)model).addObserver( new Observer() {

			@Override
			public void update(Observable o, Object arg) {
				refresh();
			}
		});

		activeDegreeButton = degreeButonTab[0];
		activeDegreeButton.setEnabled(false);

		grid.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {


				int row = e.getY()/ GraphicGrid.GRID_SQUARE_EDGE_SIZE;
				int column = e.getX() / GraphicGrid.GRID_SQUARE_EDGE_SIZE;
				int degree = Integer.parseInt(activeDegreeButton.getText());

				StdCoord position = new StdCoord(row, column);

				if (model.hasNodeAt(position)) {

					model.removeNodeAt(position);

					placeNodeNb--;
					nodeNbLabel.setText("Nombre d'îles : " + (n - placeNodeNb));
				} 
				else if (!model.isComplete()) {
					model.placeNodeAt(position, degree);

					placeNodeNb++;
					nodeNbLabel.setText("Nombre d'îles : " + (n - placeNodeNb));
				}

				//System.out.println(position);

			}

		});


		for (JButton button : degreeButonTab) {
			button.addActionListener( new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					activeDegreeButton = button;
					button.setEnabled(false);

					for (JButton jButton : degreeButonTab) {
						if (button != jButton) {
							jButton.setEnabled(true);
						}
					}
				}
			});
		}

		solveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				solveButton.setEnabled(false);

				for (JButton degreeButton : degreeButonTab) {
					degreeButton.setEnabled(false);
				}

				model.orderNodes();

				model.findNeighbors();

				model.findPossibleEdges();


				if (model.solve()) {
					solveButton.setEnabled(false);
					JOptionPane.showMessageDialog(null, "La solution du jeu a été trouvée", "Solution trouvée !", JOptionPane.INFORMATION_MESSAGE);

				} else {
					solveButton.setEnabled(false);
					JOptionPane.showMessageDialog(null, "Le jeu entré n'admet pas de solution",
							"Pas de solution", JOptionPane.INFORMATION_MESSAGE);
				}

				finish = true;

			}
		});

		resetButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GridFrame frame = new GridFrame();
				frame.setVisible(true);
				mainFrame.dispose();

			}
		});

	}

	public void display() {
		refresh();
		mainFrame.setSize(200, 500);
		mainFrame.pack();
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
	}

	private void refresh() {
		grid.repaint();
		changeButtonsSates(model.isComplete());
	}


	private void changeButtonsSates(boolean isComplete) {

		for (JButton degreeButton : degreeButonTab) {
			if (degreeButton != activeDegreeButton) degreeButton.setEnabled(! isComplete);
		}
		solveButton.setEnabled(isComplete && !finish);

	}
}
