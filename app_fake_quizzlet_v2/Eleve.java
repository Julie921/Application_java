package app_fake_quizzlet_v2;

import app_fake_quizzlet.Exercice;
import app_fake_quizzlet.PhraseATrous;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Eleve extends Utilisateur { //TODO association réflexive pour que le prof soit associé à chaque élève

	private BaremeNiveau niveau = BaremeNiveau.AVANCE;


	/**
	 * Constructeur pour la classe Eleve. Elle permet d'instancier un objet Eleve en renseignant le nom d'utilisateur (pseudo) et le mot de passe (password).
	 * Par exemple, si Marie veut créer son compte en utilisant le pseudo "marie2000" et le mot de passe "blabla", on va faire :
	 * Eleve marie = new Eleve("marie2000", "blabla");
	 * @param pseudo (String) Le nom d'utilisateur
	 * @param password (String) Le mot de passe
	 */
	Eleve(String pseudo, String password) {
        super(pseudo, password);
	}

	/**
	 * Méthode qui permet de récupérer le niveau d'un élève.
	 * Par exemple, si je veux récuperer le niveau de l'élève "marie", il faut faire :
	 * marie.getNiveau();
	 * @return Le niveau de l'élève
	 */
	public BaremeNiveau getBaremeNiveau(){
		return this.niveau;
	}

	/**
	 * Méthode qui permet de modifier le niveau d'un élève.
	 * Par exemple, si je veux changer le niveau de "marie", il faut faire :
	 * marie.setNiveau(3);
	 * @param niveau (int) Le niveau auquel on veut faire passer l'élève
	 */
	public void setNiveau(BaremeNiveau niveau){
		this.niveau = niveau;
	}

	/*public Map getBulletin(){ //TODO écrire la fonction
		return bulletin;
	}*/

	/*public getNote*/



}
