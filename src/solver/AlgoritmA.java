//package projetS4;

package solver;

import view.*;
import model.*;
import controller.*;

import java.util.ArrayList;
import java.util.Collections;

public class AlgoritmA {

	// ATTRIBUTS
	private Grille g;
	private Case [][] grille;
	private ArrayList<Case> openSet,  closedSet, path;
	private Case start, end;
	private Pions p;
	
	//CONSTRUCTEUR
	public AlgoritmA (Grille g, Case startCase, Case endCase, Pions p) {
		this.g = g;
		this.grille = g.getGrille();
		this.start = grille[startCase.getPosition()[0]][startCase.getPosition()[1]];
		this.end = grille[endCase.getPosition()[0]][endCase.getPosition()[1]];
		this.p = p;
		this.openSet = new ArrayList<Case>();
		this.closedSet = new ArrayList<Case>();
		this.path = new ArrayList<Case>();
		this.openSet.add(this.start);
	} 
	
	//METHODES
	
<<<<<<< HEAD:src/solver/AlgoritmA.java
	/**
	 * AFFICHAGE DE NOMBRE SUIVANT LA PROFONDEUR DES COUPS
	 * @param c la case cible
	 * @param nb nombre minimum de coup qu'un pions peut faire pour atteindre la cible c
	 * @param dir direction dans laquelle on affiche les numeros jusqu'a rencontrer un mur
	 * @throws Exception de la fonction getCase();
	 */

=======
>>>>>>> 7fb90ab6417cd57afe429a0e8cbab1812e170498:AlgoritmA.java
	private void putNb(Case c, int nb, String dir) throws Exception {
	boolean stop = false;
	int x = c.getPosition()[0];
	int y = c.getPosition()[1];
	while (!stop) {
		if(dir.equals("up")) {
			x--;
			if(this.g.getCase(new int [] {x, y}).getMurs()[0]) {
				stop = true;
			}
		}
		if(dir.equals("down")) {
			x++;
			if(this.g.getCase(new int [] {x, y}).getMurs()[2]) {
				stop = true;
			}
		}
		if(dir.equals("right")) {
			y++;
			if(this.g.getCase(new int [] {x, y}).getMurs()[1]) {
					stop = true;
			}
		}
		if(dir.equals("left")) {
			y--;
			if(this.g.getCase(new int [] {x, y}).getMurs()[3]) {
				stop = true;
			}
		}
		if(this.g.getCase(new int [] {x, y}).getNumber() == 0)
			this.g.getCase(new int [] {x, y}).setNumber(nb);
		}
	}
	
	/**
	 * AFFICHE LE 1 SUR LA GRILLE
	 * @throws Exception de la fonction getMurs();
	 */

	public void addNumberOne() throws Exception {
		Case c = this.g.getCase(this.g.getObjPos());
		if(!c.getMurs()[0]) { 
			this.putNb(c, 1, "up");
		}
		if(!c.getMurs()[2]) {
			this.putNb(c, 1, "down");
		} 
		if(!c.getMurs()[1]) {
			this.putNb(c, 1, "right");
		}
		if(!c.getMurs()[3]) {
			this.putNb(c, 1, "left");
		}
	}
	
	/**
	 * AFFICHE LE 2 SUR LA GRILLE
	 * @throws Exception de la fonction getMurs();
	 */

	public void addNumberTwo() throws Exception {
		for(int i = 0; i < this.grille.length; i++) {
			for(int j = 0; j < this.grille[i].length; j++) {
				if(this.grille[i][j].getNumber() == 1) {
					if(i == this.g.getObjPos()[0]) {
						this.putNb(this.grille[i][j], 2, "up");
						this.putNb(this.grille[i][j], 2, "down");
					}
					else if (j == this.g.getObjPos()[1]) {
						this.putNb(this.grille[i][j], 2, "right");
						this.putNb(this.grille[i][j], 2, "left");
					}
				}
			}
		}
	}
	
	/**
	 * AFFICHE LE 3 SUR LA GRILLE
	 * @throws Exception de la fonction getMurs();
	 */

	public void addNumberThree() throws Exception {
		for(int i = 0; i < this.grille.length; i++) {
			for(int j = 0; j < this.grille[i].length; j++) {
				if(this.grille[i][j].getNumber() == 2) {
					if(j + 1 < this.grille.length && !this.grille[i][j].getMurs()[1])
						this.putNb(this.grille[i][j], 3, "right");
					if(j - 1 >= 0 && !this.grille[i][j].getMurs()[3])
						this.putNb(this.grille[i][j], 3, "left");
					if(i - 1 >= 0 && !this.grille[i][j].getMurs()[0]) 
						this.putNb(this.grille[i][j], 3, "up");
					if(i + 1 < this.grille.length && !this.grille[i][j].getMurs()[2])
						this.putNb(this.grille[i][j], 3, "down");
				}			
			}
		}
	}
	
	/**
	 * AFFICHE LE 4 SUR LA GRILLE
	 * @throws Exception de la fonction getMurs();
	 */

	public void addNumberFour() {
		for (int i = 0; i < this.grille.length; i++) {
			for (int j = 0; j < this.grille[i].length; j++) {
				if(this.grille[i][j].getNumber() == 0 && !this.grille[i][j].equals(this.g.getObjPos())) {
					this.grille[i][j].setNumber(4);
				}
			}
		}
	}
	
	/**
	 * AFFICHE TOUT LES NUMEROS SUR LA GRILLE
	 */

	public void addAdvNumber() {
		try {
			this.addNumberOne();
			this.addNumberTwo();
			this.addNumberThree();
			this.addNumberFour();
		} catch (Exception e){
			e.printStackTrace();
		}
		
//		for(int i = 0; i < this.grille.length; i++) {
//			for(int j = 0; j < this.grille[i].length; j++) {
//				System.out.print(this.grille[i][j].getNumber() + " ");
//			}
//			System.out.println();
//		}
	}

	public ArrayList<Case> getPath() {
		return this.path;
	}
	
	/**
	 * CALCULE L'HEURISTIQUE
	 * @param a la case de depart
	 * @param b la case d'arrivee
	 * @return l'heuristique
	 */

	private int heuristic(Case a, Case b) {
		int [] x = a.getPosition();
		int [] y = b.getPosition();
		return (int) Math.sqrt(Math.pow(((double) y[0] - (double) x[0]), 2) + Math.pow(((double) y[1] - (double) x[1]), 2));
	}
	
	/**
	 * CREER LE PATH A PARTIR DES CASES PRECEDENTES
	 * @param current la case actuelle
	 */

	public void setPath(Case current){
		Case tmp = current;
		this.path.add(tmp);
		while(tmp.previous != null) {
			path.add(tmp.previous);
			tmp = tmp.previous;
		}
	}
	
	/**
	 * CHANGE LE PATH CASE PAR CASE PAR UNE LISTE DES MOUVEMENTS A EFFECTUER
	 * @param array la liste des cases a parcourir
	 * @return la liste de String des mouvementes a effectuer
	 */


	// CHANGE LE PATH CASE PAR CASE PAR UNE LISTE DES MOUVEMENTS A EFFECTUER
	public static ArrayList<String> trad(ArrayList<Case> array){
		Collections.reverse(array);
		ArrayList<String> res = new ArrayList<String>();
		for(int i = 0; i < array.size() - 1; i++){
			Case elt = array.get(i);
			Case eltB = array.get(i+1); 
			int x = elt.getPosition()[0]; 
			int y = elt.getPosition()[1];
			int x2 = eltB.getPosition()[0];
			int y2 = eltB.getPosition()[1];
			if(x2 > x && y == y2){
				res.add("bas");
			}
			if(x2 < x && y == y2){
				res.add("haut");
			}
			if(y2 > y && x == x2){
				res.add("droite");
			}
			if(y2 < y && x == x2){
				res.add("gauche"); 
			}
		}
		int finalSize = res.size();
		for(int i = 1; i < finalSize; i++) {
			//System.out.println(res.get(i-1) + ", " + res.get(i));
			if(res.get(i).equals("droite") && res.get(i-1).equals("gauche")) {
				res.remove(i-1);
				i--;
				finalSize--;
			}
			if(res.get(i).equals("gauche") && res.get(i-1).equals("droite")) {
				res.remove(i-1);
				i--;
				finalSize--;
			}
			if(res.get(i).equals("bas") && res.get(i-1).equals("haut")) {
				res.remove(i-1);
				i--;
				finalSize--;
			}
			if(res.get(i).equals("haut") && res.get(i-1).equals("bas")) {
				res.remove(i-1);
				i--;
				finalSize--;
			}
		}
		return res;
	}

	/**
	 * ALGORITHME A*
	 */

	public void AStar() {
		int winner = 0;
		int counter = 1;
		while(!this.openSet.isEmpty())  {
			counter++;
			for(int i = 0; i < this.openSet.size(); i++) {
				if(winner == openSet.size()){
					winner--;
				}
				if(openSet.get(i).getF() < openSet.get(winner).getF()) {
					winner = i;
				}
			}
			Case current = openSet.get(winner); 
			if(current == this.end) {
				this.setPath(current);
				//System.out.println("Finit, " + counter + " noeuds explores"); 
				//System.out.println(this.getPath().size());
				break;
			}
			this.openSet.remove(current);
			this.closedSet.add(current); 
			ArrayList<Case> neighbors = current.getNeighbors(g, p); 
			for (int i = 0; i < neighbors.size(); i++) {
				
				Case neighbor = neighbors.get(i);
				
				if (!closedSet.contains(neighbor)) {
					int tmpG = current.getG() + 1;
					
					if (openSet.contains(neighbor)) {
						if(tmpG < neighbor.getG()) 
							neighbor.setG(tmpG);
					} else {
						neighbor.setG(tmpG);
						openSet.add(neighbor);
					}	
					neighbor.setH(this.heuristic(neighbor, end));
					neighbor.setF(neighbor.getG() + neighbor.getH());
					neighbor.previous = current;
				}
			}
		}	
		this.p.setPosition(this.start.getPosition());
		this.addAdvNumber();
	}
}


