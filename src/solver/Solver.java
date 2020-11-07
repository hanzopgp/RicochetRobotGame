//package projetS4;

package solver;

import view.*;
import model.*;
import controller.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Solver {
	
	// ATTIBUTS
	private Grille g;
	private ArrayList<Pions> pions;
	private Pions robot;
	private ArrayList<Pions> secondaryPions					 = new ArrayList<>();
	private ArrayList<String> currentPath					 = new ArrayList<>();
	private int depth 									 	 = 0;
	private int noeuds 										 = 0;
	private Case startCase 									 = null;
	private Case endCase 									 = null;
	private Pions primaryPion								 = null;
	private ArrayList<Case> path 						 	 = new ArrayList<>();	
	private HashMap<Case, ArrayList<String>> moveList		 = new HashMap<>();
	private HashMap<Case, ArrayList<Pions>> listRobotsToMove = new HashMap<>();
	
	// CONSTRUCTEUR
	public Solver(Grille g, ArrayList<Pions> p, Pions r) {
		this.g = g;
		this.pions = p;
		this.robot = r;
	}

	// METHODES
	
	/**
	 * JOUE SELON LES DIRECTIONS DONNEES PAR L'ALGO A*
	 * @param finalPathString l'ArrayList des directions a prendre
	 * @param p le pion qui joue
	 * @throws Exception des fonctions move
	 */
	
	public void algoPlay(ArrayList<String> finalPathString, Pions p) throws Exception {
		for(String s : finalPathString){
			//Thread.sleep(500);
			//System.out.println("Color : " + this.robot.getColor() + ", Move : " + s);
			switch(s){
				case "haut": p.moveUp(g, true); break;
				case "bas": p.moveDown(g, true); break;
				case "gauche": p.moveLeft(g, true); break;
				case "droite": p.moveRight(g, true); break;					
				default: break;
			} 
			//draw.update();
		}
	}		

	/**
	 * RESOU LA PARTIE
	 * @param robotsToMove la liste de robots pouvant etre deplace
	 * @param did la liste des cases deja visite
	 * @param depth la profondeur 
	 * @param currentPath la chemin optimal actuel
	 * @param previous la case precedente  
	 * @return le currentPath le plus optimal
	 * @throws Exception de la fonction getCase();
	 */
	
	private ArrayList<String> bfs(ArrayList<Pions> robotsToMove, ArrayList<Case> did, int depth, ArrayList<String> currentPath, Case previous, HashMap<Case, ArrayList<Pions>> listRobotsToMove) throws Exception {
		int max = 0;
		if(this.g.getCase(this.primaryPion.getPosition()).getNumber() > 1) { // SI LE PION PRINCIPAL N'EST PAS EN FACE DE LA CIBLE
			// CONDITION PROFONDEUR MAX (a revoir)
			if(this.depth > 10) {
				max = 11;
			} 
			else if(this.depth > 0) {
				max = this.depth + this.g.getCase(this.primaryPion.getPosition()).getNumber() - 1;
			}
			else if(this.depth == 0) {
				max = 1;
			}
//			else {
//				max = currentPath.size() - this.g.getCase(this.primaryPion.getPosition()).getNumber() - 1;
//			}
			if((currentPath.isEmpty()) || (depth < max)) { //VERIFIE LA PROFONDEUR
				for(Pions current : robotsToMove) {
					Case caseTmp = new Case(current.getPosition()[0], current.getPosition()[1]); // CRRER UNE CASE TMP 
						 
					caseTmp.setColor(current.getColor()); // RECUPERE LA COULEUR DU ROBOT ACTUEL
					caseTmp.setNumber(depth); // PERMET DE SAVOIR L'ORDE DES COUPS A JOUER GRACE A LA VALEUR DE LA PROFONDEUR
					caseTmp.previous = previous; // PERMET D'ACCEDER A LA CASE PRECEDENTE PAR LA SUITE 
	 
					boolean move = true; // SI ON DOIT BOUGER LES ROBOTS OU SARRETER POUR CETTE RECURCIVITE 
					int size = 0;
					
					if(did.isEmpty()) { //SI PREMIER PASSAGE DANS LA BOUCLE
						did.add(caseTmp); // INDIQUE QUE LE PION EST PASSE SUR LA CASE 
						size = 1; // INDIQUE LA TAILLE DE did (car bug quand je voulais verifier avec did.size()) 
						System.out.println(current.getColor()); 
					}
					
					if(depth != 0) { 
						listRobotsToMove.put(caseTmp, robotsToMove); // AJOUTE LA LISTE DES 3 PIONS AVEC LEUR COORDONNEES ACTUELLES  
					}
					
					if(previous != null) { 
						if(current.equals(previous.getPosition())) {// SI L ACTUEL EST AU MEME ENDROIT QUE LE PRECEDANT 
							move = false; 
						}
						if(previous.previous != null && previous.getColor().equals(caseTmp.getColor())) {// SI LE PRECEDANT A UN PRECEDANT ET QUE LE PRECEDANT A LA MEME COULEUR QUE LACTUEL
							// Faire haut-bas / gauche-droite revient a faire bas / droite, on evite donc de les traiter comme si cela etait un nouvel etat
							if(previous.previous.getPosition()[0] == previous.getPosition()[0] && previous.getPosition()[0] == caseTmp.getPosition()[0]) { // stop si movement gauche-droite
								move = false;
							}
							if(previous.previous.getPosition()[1] == previous.getPosition()[1] && previous.getPosition()[1] == caseTmp.getPosition()[1]) { // stop si movement haut-bas
								move = false;
							} 
						}
					}
					
					int x = 0; 
					
					// CETTE CONDITION PERMET DE VOIR SI LES COORDONNEES DES PIONS ACTUELS N'ONT PAS ETE DEJA EXPLORE 
					
					if(!this.listRobotsToMove.isEmpty()) { 
						for(Map.Entry<Case, ArrayList<Pions>> e : this.listRobotsToMove.entrySet()) {// PARCOURT LA LISTE DE LISTE 
							for(Pions p : robotsToMove) {// PATCOURT LA LISTE DE PIONS ACTUEL
								for(Pions p2 : e.getValue()) { // PARCOURT UNE DES  LISTE DE PIONS QUI DONNE LE CHEMIN LE PLUS COURT
									if(p.getColor().equals(p2.getColor()) && p.equals(p2.getPosition())) {
										x++; 
										//System.out.println(current.getColor() + " : " + current + " profondeur : " + depth);
									}
								}
							}  
							//System.out.println("x = " + x);
							if(x == 3 && depth > e.getKey().getNumber()/*&& current.getColor().equals(e.getKey().getColor())*/) { // SI LISTE ACTUEL INCLU DANS LA LISTE 
								move = false; 
//								System.out.println(depth + " < " + e.getKey().getNumber() + " size = " + this.path.size());
//								if(depth <= e.getKey().getNumber()) {					
//									ArrayList<Case> pathTmp = new ArrayList<>();
//									System.out.println("contenu path : " + this.path + "    et l'actuel : " + caseTmp);
//									for(int i = this.path.size()-1; i > e.getKey().getNumber(); i--) {
//										pathTmp.add(this.path.get(i));
//										System.out.println(i);
//									}
//									System.out.println("contenu path tmp : " + this.path + "    et l'actuel : " + caseTmp);
//									this.newWay(caseTmp, depth + (this.depth - e.getKey().getNumber()), listRobotsToMove, pathTmp); 
//								}
							}  
							x = 0;
							break;
						}
					}
					
					ArrayList<Case> neighbors = caseTmp.getNeighbors(g, current); //OBTIENT LES CASES VOISINES D'UN PION
					//System.out.println(current.getColor() + " : " + current + "    detph = " + depth + "     previous : " + previous);  
						
					if(move) {
						for(Case neighbor : neighbors) {
							int [] tmp = current.getPosition(); // RECUPERE LES COORDONNEES POUR LE REPLACER
							neighbor = new Case(neighbor.getPosition()[0], neighbor.getPosition()[1]); // CREER LA CASE VOISINE 
							neighbor.setColor(current.getColor()); // INDIQUE SA COULEUR 
							
							boolean already = false;
							
							for(Case c : did) {
								if(c.getColor().equals(neighbor.getColor()) && c.equals(neighbor.getPosition())) { // SI ON EST DEJA PASSE SUR CETTE CASE AVEC CE PION
									already = true; 
								}
							}
								 
							this.noeuds ++;

	//						System.out.println(neighbor + " " + neighbor.getColor() + "  bool : " + already);				
							if(!already) { // SI LE VOISIN N'EST PAS UNE CASE DEJA VISITEE
								did.add(neighbor); //ON GARDE LA POSITION 
								current.setPosition(neighbor.getPosition()); // ON DEPLACE LE PION A L'ENDROIT DU VOISIN
	
								primaryPion.setArray(robotsToMove); //ON INDIQUE LA NOUVELLE POSITION DU ROBOT LORS DU DEPLACEMENT DU ROBOT PRINCIPAL
								ArrayList<String> neighborPath = this.execAStar(startCase, endCase, primaryPion); // TEST A* APRES DEPLACEMENT
								
								if((((currentPath.size() + this.depth > neighborPath.size() + depth) && !currentPath.isEmpty()) || currentPath.isEmpty()) && !neighborPath.isEmpty()) { // SI PATH APRES DEPLACEMENT PLUS PETIT
									System.out.println((currentPath.size() + this.depth) + " > " + (neighborPath.size() + depth) + " vide : " + currentPath.isEmpty());
									currentPath = neighborPath;
									neighbor.setNumber(depth + 1); 
									neighbor.previous = caseTmp;

									this.newWay(neighbor, depth, listRobotsToMove, null); 									
								}

								currentPath = bfs(robotsToMove, did, depth + 1, currentPath, caseTmp, listRobotsToMove); // LANCE UNE RECURSIVITE
								did.remove(neighbor);
								current.setPosition(tmp);// REPLACE LE PION A CA PLACE D'ORIGINE
							}
							
							
						} 	
						
					}
					if(size == 1 && depth == 0) {
						did = new ArrayList<>();
						System.out.println("fin de boucle -> did.size() = " + did.size());
						size = 0;
					}
					
					if(depth != 0) {
						//System.out.println("size before remove : " + listRobotsToMove.size());
						listRobotsToMove.remove(caseTmp);
						//System.out.println("size before after : " + listRobotsToMove.size());
					}
				}
			}
		}
		return currentPath;
	}
	
	/**
	 * TROUVE LE BON CHEMIN
	 * @param neighbor la case voisine
	 * @param depth la profondeur 
	 * @param listRobotsToMove la liste des robots secondaires
	 * @param reste les cases restantes 
	 */

	private void newWay(Case neighbor, int depth, HashMap<Case, ArrayList<Pions>> listRobotsToMove, ArrayList<Case> reste) {
		this.depth = depth;   
		this.listRobotsToMove = listRobotsToMove;
		
		// TRANSFORME LE PATH EN ARRAYLIST
		this.path = new ArrayList<>(); 
		if(reste != null) {
			this.path = reste;
			System.out.println("reste non null et size = " + this.path.size() );
		}
		
		this.path.add(neighbor);
		while(neighbor.previous != null) {
			this.path.add(neighbor.previous);
			neighbor = neighbor.previous;
		}
	}
	
	/**
	 * RESOU LA PARTIE
	 * @return le currentPath le plus optimal
	 * @throws Exception de la fonction getCase();
	 */

	public void solve() throws Exception {

		//INITIALISATION
		ArrayList<String> finalPathString = null; 
		String winColor = ""; 

		//ON TROUVE LA CIBLE
		for(Pions p : pions){
			if(p.getType().equals("target") && !p.getColor().equals("black")){ 
				endCase = p.getCase();
				winColor = p.getColor();
			}
		}

		//ON TROUVE LE ROBOT QUI DOIT ALLER SUR LA CIBLE
		for(Pions p : pions){
			if(p.getType().equals("robot")){ 
				if(p.getColor().equals(winColor)) {
					startCase = p.getCase(); 
					primaryPion = p;
				}//ON RECUPERER LES ROBOTS SECONDAIRES
				else {
					secondaryPions.add(p);}
			}
		}
		this.currentPath = execAStar(startCase, endCase, primaryPion);
			
		ArrayList<Pions> robotsToMove = this.secondaryPions;
		
		finalPathString = bfs(robotsToMove, new ArrayList<>(), 0, this.currentPath, null, new HashMap<>()); // RECUPERE LE CHEMIN OPTIMAL
		
		// AFFICHE LES DEPLACEMENTS A FAIRE
		if(!this.path.isEmpty()) {
			Case tmp  = null;
			Case same = null;
			ArrayList<Case> listTmp = new ArrayList<>(); 
			for(Case c : this.path) {
				System.out.println(c.getColor() + " : " + c + " (coup numero " + c.getNumber() + ")");
				if(tmp != null) {
					if(!tmp.getColor().equals(c.getColor()) && listTmp.size() > 1) {
						if(same != null && same.getColor().equals(c.getColor())) {
							listTmp.add(same); 
						}
						this.moveList.put(tmp, AlgoritmA.trad(listTmp));
						listTmp = new ArrayList<>();
						same = tmp;						
					} 
				}  
				tmp = c; 
				System.out.println("same : " + same + " et c : " + c);
				listTmp.add(c);
			}
			if(!listTmp.isEmpty()) {
				this.moveList.put(tmp, AlgoritmA.trad(listTmp));
			}
		}
		
		for(Map.Entry<Case, ArrayList<String>> e : this.moveList.entrySet()) {
			System.out.println(e.getKey().getColor() + " : " + e.getValue());
			for(Pions p : robotsToMove) {
				if(p.getColor().equals(e.getKey().getColor()) && !e.getValue().isEmpty()) {
					this.algoPlay(e.getValue(), p);
				}
			} 
		}
		
		System.out.println("Le solveur a trouve : " + finalPathString);
//		for(Map.Entry<String, ArrayList<String>> e : this.allPaths.entrySet()) {
//			System.out.println(e.getKey() + " : " + e.getValue());
//		}
		System.out.println("Realisable en : " + finalPathString.size() + " coups (Noeud(s) parcouru(s) : " + this.noeuds);	
		
		this.algoPlay(finalPathString, this.robot);
	}

	/**
	 * EXECUTE L'ALGORITHME A*
	 * @param startCase case de depart
	 * @param endCase case d'arrivee
	 * @param robotTmp robot qui doit se deplacer
	 * @return le chemin de depart jusqu'a l'arrivee
	 */

	public ArrayList<String> execAStar(Case startCase, Case endCase, Pions robotTmp){
		AlgoritmA algo = new AlgoritmA(this.g, startCase, endCase, robotTmp);
		algo.AStar();
		ArrayList<Case> finalPath = algo.getPath();
		return algo.trad(finalPath);
	}
}
