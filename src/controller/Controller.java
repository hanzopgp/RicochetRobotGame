	//package projetS4;

	package controller;

	import view.*;
	import model.*;
	import solver.*;

	import java.awt.event.KeyEvent;
	import java.awt.event.KeyListener;
	import java.util.ArrayList;

	public class Controller implements KeyListener {
		
		// ATTRIBUTS
		private Grille g;
		private Pions robot;
		private ArrayList<Pions> pions;
		private Draw draw;
		private boolean gameOver = false;
		
		// CONSTRUCTEUR
		public Controller(Grille g, ArrayList<Pions> pions, Draw draw){
			this.g = g;
			this.pions = pions;
			this.draw = draw;
			this.setRobot();
			this.draw.setWin("   Robot selectionne : Robot " + this.robot.getColor());
		}
		
		// SETTERS
		// INITIALISE LE ROBOT (PAR DEFAUT : meme couleur que la cible)
		private void setRobot() {
			for(Pions p : this.pions) {
				if(p.getType() == "robot" && g.getObjColor() == p.getColor())
					this.robot = p;
			}
		}
		
		// FONCTION QUI VERIFIE SI LA PARTIE EST TERMINE
		private void gameOver() {
			for(Pions p : this.pions) {
				if(p.gameOver(this.pions)) {
					this.gameOver = true;
				}
			}
		}

		// METHODES

		@Override
		public void keyPressed(KeyEvent e) {
			// SI LA PARTIE N'EST PAS TERMINE
			if(!this.gameOver) {
				for (Pions p : this.pions) {
					if(p.getType() == "robot") {	
						// PERMET DE CHOISIR LE ROBOT QUE L'ON VEUT DEPLACER
						switch (e.getKeyCode()) {
			
							case KeyEvent.VK_R: // TOUCHE 'R'
								if(p.getColor() == "rouge")
									this.robot = p;
								break;
							
							case KeyEvent.VK_V: // TOUCHE 'V'
								if(p.getColor() == "vert")
									this.robot = p;
								break;
								
							case KeyEvent.VK_J: // TOUCHE 'J'
								if(p.getColor() == "jaune")
									this.robot = p;
								break;
								
							case KeyEvent.VK_B: // TOUCHE 'B'
								if(p.getColor() == "bleu")
									this.robot = p;
								break;
							
							default:
								break;
						}
					}
				}
				// AFFICHE LE ROBOT SELECTIONE
				draw.setWin("   Robot selectionne : Robot " + this.robot.getColor());
			}		 
			//LANCE UNE NOUVELLE PARTIE
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				// REINITIALISE LE JEU
				Setup setup = new Setup();
				draw.updateSetup(setup);	
				// RECUPERE CES ATTRIBUTS MIS A JOUR
				this.g = draw.getSetup().getGrille();
				this.pions = draw.getSetup().getPions();
				// REDEFINI LE ROBOT PAR DEFAUT
				this.setRobot();
				this.draw.setWin("   Robot selectionne : Robot " + this.robot.getColor());
				//REMET LE BOOLEEN A false
				this.gameOver = false; 
			}
		}
		
		//

		@Override
		public void keyReleased(KeyEvent e){
			// SI LA PARTIE N'EST PAS TERMINE
			try {
				if(!this.gameOver) {
					if(e.getKeyCode() == KeyEvent.VK_S) { // SI "S" PRESSEE ALORS SOLVEUR S ACTIVE
						for(Pions p : this.pions) {
							if(p.getType().equals("robot") && p.getColor().equals(this.g.getObjColor())) {
								this.robot = p;
							}
						} 
						Solver solver = new Solver(g, pions, robot);
						solver.solve();
					}
					// JOUE SUIVANT LA TOUCHE
				
					if(e.getKeyCode() == KeyEvent.VK_UP) {// DEPLACE VERS LE HAUT
						this.robot.moveUp(g, false);
					}
					if(e.getKeyCode() == KeyEvent.VK_DOWN) { 	// DEPLACE VERS LE BAS 
						this.robot.moveDown(g, false);
					}
					if(e.getKeyCode() == KeyEvent.VK_RIGHT) { // DEPLACE VERS LA DROITE
						this.robot.moveRight(g, false);
					}
					if(e.getKeyCode() == KeyEvent.VK_LEFT) { // DEPLACE VERS LA GAUCHE 
						this.robot.moveLeft(g, false);
					}
					// VERIFIE SI LA PARTIE EST TERMINE
					this.gameOver();
					if(this.gameOver) {
						this.draw.setWin("   BRAVO !!!       Pressez \"Entrer\" pour recommencer.");
					}
				} 
				this.draw.update();
			} catch (Exception ex) {
				System.err.println();
			}				
			// ACTUALISE LA FRAME
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
		}

	}
