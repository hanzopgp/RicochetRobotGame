//package projetS4;

package model;

import view.*;
import controller.*;
import solver.*;

public class Main{

	public static void main(String [] args){
		// INITIALISE LE JEU
		Setup setup = new Setup();
		
		// AFFICHE LE JEU
		Draw GUI = new Draw(setup);
		try { GUI.draw();} 
		catch (Exception e) { ;}
		GUI.setLocationRelativeTo(null);
	
	}
}
