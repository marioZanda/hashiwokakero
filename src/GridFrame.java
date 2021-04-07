import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

public class GridFrame extends JFrame {
	private JSpinner sizeField;
	private JSpinner widthField;
	private JSpinner heightField;
	private JButton resetSizeButton;
	private JButton confirmSizeButton;
	public int islandnb = 2;
	public int width = 3;
	public int height = 3;

	public GridFrame() {
		createView();
		placeComponents();
		createController();
		setLocationRelativeTo(null);
		pack();
		setVisible(true);
	}

	private void createController() {
		confirmSizeButton.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				width = (int) widthField.getValue();
				height = (int) heightField.getValue();
				islandnb = (int) sizeField.getValue();
				if ((width * height) < islandnb) {
					JOptionPane.showMessageDialog(
	           			    null, 
	           			    "Parametres incorrect !",
	            		    "Erreur !",
	            		    JOptionPane.ERROR_MESSAGE
	            		);
				} else {
					Hashiwokakero hashi = new Hashiwokakero(islandnb, width, height);
					hashi.display();
					dispose();
				}
			}
		});
		
		resetSizeButton.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				widthField.setValue(2);
				heightField.setValue(2);
				sizeField.setValue(2);
			}
		});
	
		
	}

	private void placeComponents() {
		JPanel q = new JPanel(new FlowLayout(FlowLayout.CENTER)); {
			JPanel r = new JPanel(new GridLayout(4, 2)); {
				//JPanel s = new JPanel(new FlowLayout(FlowLayout.CENTER)); {
					r.add(new JLabel("largeur : "));
					r.add(widthField);
				//}
				//r.add(s);
				//s = new JPanel(new FlowLayout(FlowLayout.CENTER)); {
					r.add(new JLabel("Hauteur : "));
					r.add(heightField);
				//}
				//r.add(s);
				//s = new JPanel(new FlowLayout(FlowLayout.CENTER)); {
					r.add(new JLabel("Nombre d'îles : "));
					r.add(sizeField);
				//}
				//r.add(s);
				
				JPanel s = new JPanel(new FlowLayout(FlowLayout.CENTER)); {
					JPanel t = new JPanel(new GridLayout(1, 0)); {
						t.add(confirmSizeButton);
						//t.add(resetSizeButton);
					}
					s.add(t);
				}
				r.add(s);		
				 s = new JPanel(new FlowLayout(FlowLayout.CENTER)); {
					JPanel t = new JPanel(new GridLayout(1, 0)); {
						//t.add(confirmSizeButton);
						t.add(resetSizeButton);
					}
					s.add(t);
				}
				
				r.add(s);
			}
			q.add(r);
			q.setBorder(BorderFactory.createEtchedBorder());
		}	
		this.add(q, BorderLayout.CENTER);
		
	}

	private void createView() {
		
		int maxIsland = (Toolkit.getDefaultToolkit().getScreenSize().height/ GraphicGrid.GRID_SQUARE_EDGE_SIZE);
		
		sizeField = new JSpinner(new  SpinnerNumberModel(2, 2, maxIsland * maxIsland, 1));
		widthField = new JSpinner(new  SpinnerNumberModel(2, 2, maxIsland, 1));
		heightField = new JSpinner(new  SpinnerNumberModel(2, 2, maxIsland, 1));
		
		confirmSizeButton = new JButton("Confirmer");
		resetSizeButton = new JButton("Réinitialiser");
	}
	
	public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GridFrame();
            }
        });
    }


}
