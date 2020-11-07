//package projetS4;

package view;

import controller.*;
import model.*;
import solver.*;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class DrawCase extends JPanel {

	// ATTRIBUTS
	private static final long serialVersionUID = 1L;
	private Case cases;
	private ArrayList<Pions> p;
	
	// CONSTRUCTEUR
	public DrawCase(Case cases, ArrayList<Pions> p) {
		this.cases = cases;
		this.p = p;
	}
	
	// METHODES

	/**
	 * @param color le string de la couleur voulu
	 * @return le static Color correspondant
	 */
		
	private Color getColor(String color) {
		switch(color) {
			case "rouge":
				return Color.RED;
			case "vert":
				return Color.GREEN;
			case "jaune":
				return Color.YELLOW;
			case "bleu":
				return Color.BLUE;
			default:
				return Color.BLACK;
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		//DESSINE LA GRILLE
		super.paintComponent(g);
		g.drawRect(0, 0, Draw.HEIGHT/16, Draw.WIDTH/16);
		//DESSINE LES PIONS
		if(!p.isEmpty()) { // SI L'ARRAYLIST DE PIONS N'EST PAS VIDE

			for(Pions p : this.p) {
				if(this.cases.equals(p.getPosition())) {
					g.setColor(this.getColor(p.getColor()));
					switch(p.getType()) {
						case "robot":
							// RAJOUTE UN CONTOUR NOIR AUX ROBOTS
							g.setColor(Color.BLACK);
							g.fillOval(5, 5, Draw.HEIGHT/16-11, Draw.WIDTH/16-11);
							
							// DESSINNE LES ROBOTS
							g.setColor(this.getColor(p.getColor()));
							g.fillOval(7, 7, Draw.HEIGHT/16-15, Draw.WIDTH/16-15);
							
							break;
						case "target": // DESSINE LA CIBLE
							g.fillRect(1, 1, Draw.HEIGHT/16, Draw.WIDTH/16);
							break;
					}	
				}
				if(cases.getPassageSolver()) {
					g.setColor(this.getColor(cases.getColor()));
					g.fillOval(17, 17, Draw.HEIGHT/16-35, Draw.WIDTH/16-35);
				}
			}
		}
		
		//DESSINE LES MURS
		for(int i = 0; i < cases.getMurs().length; i++) {
			g.setColor(Color.BLACK);
			if(cases.getMurs()[i]) { // SI LE COTE DE LA CASE POSSEDE UN MUR
				switch(i) {
					case 0: // DESSINE LE MUR DU HAUT
						g.fillRect(0, 0, Draw.WIDTH/16, 5);
						break;
					case 1: // DESSINE LE MUR DE DROITE
						g.fillRect(Draw.WIDTH/16-5, 0, 5, Draw.HEIGHT/16);
						break;
					case 2: // DESSINE LE MUR DU BAS
						g.fillRect(0, Draw.HEIGHT/16-5, Draw.WIDTH/16, 5);
						break;
					case 3: // DESSINE LE MUR DE GAUCHE
						g.fillRect(0, 0, 5, Draw.HEIGHT/16);
				}
			}

<<<<<<< HEAD:src/view/DrawCase.java
=======
			// ARRONDIE LES COINS
			// if(cases.getMurs()[0] && cases.getMurs()[1]){
			// 	// DESSINE ROND COIN HAUT DROITE
			// 	g.fillOval(45, 0, 13, 13);
			// 	break;
			// }
			// if(cases.getMurs()[1] && cases.getMurs()[2]){
			// 	// DESSINE ROND COIN BAS DROITE
			// }
			// if(cases.getMurs()[2] && cases.getMurs()[3]){
			// 	// DESSINE ROND COIN BAS GAUCHE
			// }
			// if(cases.getMurs()[3] && cases.getMurs()[0]){
			// 	// DESSINE ROND COIN HAUT GAUCHE
			// }
>>>>>>> 7fb90ab6417cd57afe429a0e8cbab1812e170498:DrawCase.java
		}
		
		this.repaint();
	}

}
