//package projetS4;

package model;

import view.*;
import controller.*;
import solver.*;

import java.util.Random;
//import java.util.List;
//import java.util.Arrays;
import java.util.ArrayList;
public class Setup {

	// ATTRIBUTS
	protected Random randomGenerator;
	protected Grille grille;
	protected ArrayList<Pions> pions;

	// CONSTRUCTEUR
	public Setup(){
		this.randomGenerator = new Random();
		this.grille = new Grille(); 
		this.pions = new ArrayList<>();
	}

	// GETTERS
	public Grille getGrille() {return this.grille;}
	public ArrayList<Pions> getPions() {return this.pions;}

	// METHODES

	/**
	 * MET A JOUR LA PARTIE POUR COMMENCER A JOUER, POSE LES MURS, LES PIONS ETC
	 */

	public void setup() {
		
		// REACTUALISE LES ATTRIBUTS 
		this.randomGenerator = new Random();
		this.grille = new Grille(); 
		this.pions = new ArrayList<>();

		/*** INITIALISATION ***/
		
		// INIT GRILLE
		this.grille.buildGrille();

		// INIT PIONS
		String [] colors = {"rouge", "vert", "jaune", "bleu"};

		Pions pion = new Pions(0, 0, "", "");
		
		// REPRESENTE LE CARRE NOIR AU MILIEU DE LA GRILLE
		for(int [] carre : this.grille.getCarre()) {
			pion = new Pions(carre[0], carre[1], "black", "target");
			this.pions.add(pion);
		}
		
		int jeu = this.randomGenerator.nextInt(3);
		
		switch(jeu) {
			case 0:
				switch (this.randomGenerator.nextInt(4)) {
					case 0: pion = new Pions(9, 10, "jaune", "target"); break;
					case 1: pion = new Pions(3, 12, "vert", "target"); break;
					case 2: pion = new Pions(6, 3, "bleu", "target"); break;
					case 3: pion = new Pions(14, 5, "rouge", "target"); break;
					default: break;
				}
				break;
			case 1:
				switch (this.randomGenerator.nextInt(4)) {
					case 0: pion = new Pions(4, 6, "jaune", "target"); break;
					case 1: pion = new Pions(9, 2, "vert", "target"); break;
					case 2: pion = new Pions(9, 10, "bleu", "target"); break;
					case 3: pion = new Pions(1, 11, "rouge", "target"); break;
					default: break;
				}
				break;
			case 2:
				switch (this.randomGenerator.nextInt(4)) {
					case 0: pion = new Pions(14, 6, "jaune", "target"); break;
					case 1: pion = new Pions(9, 5, "vert", "target"); break;
					case 2: pion = new Pions(4, 9, "bleu", "target"); break;
					case 3: pion = new Pions(11, 13, "rouge", "target"); break;
					default: break;
				}
				break;
			default: break;
		}
		
		this.grille.setObjColor(pion.getColor());
		this.grille.setObjPos(pion.getPosition());
		this.pions.add(pion);

		for(String color : colors){
			pion = new Pions(randomGenerator.nextInt(16), randomGenerator.nextInt(16), color, "robot");
			pion.setPion(this.pions, this.randomGenerator);
			pion.setArray(this.pions);
			this.pions.add(pion);
		}
		
		// INIT MURS
		int [][] murL = {{0}};
		int [][] murH = {{0}};
		
		switch(jeu) {
			case 0:
				murL = new int [][] {{0, 5}, {0, 8}, {1, 9}, {2, 12}, {3, 0}, {3, 11}, {4, 6}, {5, 7}, {6, 2}, {6, 13}, {7, 6}, {7, 8}, {8, 6}, {8, 8}, {9, 1}, {9, 10}, {10, 2}, {11, 6}, {11, 12}, {13, 8}, {14, 5}, {15, 6}, {15, 10}};
				murH = new int [][] {{5, 0}, {12, 0}, {2, 1}, {5, 2}, {9, 2}, {6, 3}, {14, 5}, {4, 6}, {6, 7}, {8, 7}, {10, 7}, {5, 8}, {6, 8}, {8, 8}, {12, 9}, {1, 10}, {9, 10}, {2, 12}, {10, 12}, {5, 13}, {11, 13}, {4, 15}, {12, 15}};
				break;
			case 1:
				murL = new int [][] {{0, 3}, {0, 8}, {1, 5}, {1, 10}, {2, 0}, {3, 14}, {4, 5}, {4, 9}, {6, 2}, {6, 11}, {7, 6}, {7, 8}, {8, 4}, {8, 6}, {8, 8}, {8, 11}, {9, 1}, {9, 9}, {11, 9}, {12, 14}, {13, 4}, {14, 1}, {14, 12}, {15, 5}, {15, 10}};
				murH = new int [][] {{3, 0}, {12, 0}, {2, 1}, {14, 1}, {5, 2}, {8, 2}, {12, 4}, {1 , 5}, {8, 5}, {3, 6}, {6, 7}, {8, 7}, {6, 8}, {8, 8}, {4, 9}, {11, 9}, {9, 10}, {0, 11}, {6, 12}, {7, 12}, {13, 13}, {2, 14}, {11, 14}, {1, 15}, {10, 15}};
				break;
			case 2:
				murL = new int [][] {{0, 1}, {0, 8}, {1, 4}, {1, 10}, {3, 0}, {3, 14}, {4, 9}, {5, 4}, {6, 3}, {6, 11}, {7, 6}, {7, 8}, {8, 2}, {8, 6}, {8, 8}, {9, 4}, {10, 1}, {10, 10}, {11, 12}, {12, 4}, {12, 10}, {13, 12}, {14, 5}, {15, 3}, {15, 9}};
				murH = new int [][] {{5, 0}, {12, 0}, {3, 1}, {10, 1}, {7, 2}, {6, 3}, {0, 4}, {11, 4}, {4, 5}, {9, 5}, {13, 6}, {6, 7}, {8, 7}, {6, 8}, {8, 8}, {4, 9}, {11, 10}, {0, 11}, {9, 11}, {6, 12}, {13, 12}, {11, 13}, {2, 14}, {1, 15}, {11, 15}};
				break;
		}
		
		try{	
			this.grille.initMurs(murL, murH);
			
		} catch (Exception e){
			System.err.println("ERROR: " + e);
		}
		
	}
}