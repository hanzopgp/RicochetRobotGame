//package projetS4;

package model;

import view.*;
import controller.*;
import solver.*;

import java.util.ArrayList;

public class Case{

	// ATTRIBUTS
	private int [] location;
	protected boolean [] murs     = {false, false, false, false};
	private int f, g, h;
	public Case previous;
	private int number;
	private String color;
	private boolean passageSolver = false;

	// CONSTRUCTEUR
	public Case(int x, int y) {
		this.location = new int [] {x, y};
		this.f = 0;
		this.g = 0;
		this.h = 0;
		this.number = 0;
	}

	// SETTERS
	public void setF(int f) {this.f = f;}
	public void setG(int g) {this.g = g;}
	public void setH(int h) {this.h = h;}
	public void setNumber(int nb) {this.number = nb;}
	public void setColor(String c) {this.color = c;}
	public void setPassageSolver(boolean bool) {this.passageSolver = bool;}

	/**
	* Place les murs de chaque case et renvoie une exception si le mur demande n'existe pas;
	* @param c l'index correspondant a un mur;
	* @throws Exception si l'index est superieur a la taille du tableau;
	*/
	
	public void setMurs(int c) throws Exception{
		if(c > 3 || c < 0)
			throw new Exception("impossible d'ajouter ce mur, l'index " + c + " n'existe pas. Celui ci dois etre compris entre 0 et 3 (inclu).");
		this.murs[c] = true;
	}

	// GETTERS
	public int [] getPosition(){return this.location;}
	public boolean [] getMurs(){return this.murs;}
	public int getF() {return this.f;}
	public int getG() {return this.g;}	
	public int getH() {return this.h;}
	public int getNumber() {return this.number;}
	public String getColor() {return this.color;}
	public boolean getPassageSolver() { return this.passageSolver;}

	//METHODES

	/**
	 * RENVOIE UNE LISTE DES CASES VOISINES D'UN PION
	 * @param g la grille de jeu
	 * @param p le pion
	 * @return neighbors la liste des cases voisines du pion p
	 */

	public ArrayList<Case> getNeighbors(Grille g, Pions p){
		ArrayList<Case> neighbors = new ArrayList<Case>();
		p.setPosition(this.location);
		try {
			p.moveDown(g, false);
			neighbors.add(g.getCase(p.getPosition()));
			p.setPosition(this.location);
							
			p.moveUp(g, false);
			neighbors.add(g.getCase(p.getPosition()));
			p.setPosition(this.location);
			
			p.moveRight(g, false);
			neighbors.add(g.getCase(p.getPosition()));
			p.setPosition(this.location);

			p.moveLeft(g, false);
			neighbors.add(g.getCase(p.getPosition()));
			p.setPosition(this.location);
		} catch (Exception e) {
			System.err.println("ERROR" + e);
		}
		return neighbors;
	}

	/**
	 * COMPTE LE NOMBRE DE MURS D'UNE CASE
	 * @return cpt le nombre de murs
	 */

	public int countWalls(){
		int cpt = 0;
		for(boolean m : this.murs){
			if(m){
				cpt++;
			}
		}
		return cpt;
	}
	
	/**
	* @param position la position testee;
	* @return vrai si les coordonnees entre la position de la case et la position testee sont les memes;
	*/

	public boolean equals(int [] position){
		return java.util.Arrays.equals(position, this.location);
	}
	
	@Override
	public String toString() {
		return "("+this.location[0]+","+this.location[1]+")";
	}
}
