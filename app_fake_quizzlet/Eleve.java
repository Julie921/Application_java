/**
 * Package contenant l'application (toutes les classes et le Main)
 */
package app_fake_quizzlet;

/**
 * Importation des packages nécessaires.
 */
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Classe Eleve (qui, après, va être une extension de la classe abstraite UTILISATEUR)
 * Cette classe contient plusieurs méthodes :
 * - doExercice()
 * - afficheReponseEleve()
 */

public class Eleve {

    String nom;
    ArrayList<String> reponse_eleve = new ArrayList<>(); //correspond à reponse_eleve de Exercice.gradeExercice()

    Eleve(String nom) {
        this.nom = nom;
    }

    /**
     * Cette méthode permet de faire un exercice
     * @param exercice l'exercice à faire
     * @return rien
     */
    ArrayList doExercice(PhraseATrous exercice) {

        System.out.println("Bonjour " + this.nom);
        exercice.affichePhraseAvecTrous();
        exercice.afficheMotsAPlacer();

        for(int i=0; i<exercice.mots_a_placer.size(); i++){
            System.out.println("Quel est le mot manquant " + (i+1) + "?");
            Scanner myObj = new Scanner(System.in);
            String motDonne = myObj.nextLine();
            reponse_eleve.add(motDonne);
        }

        return reponse_eleve;

    }


    /**
     * Méthode pour afficher la phrase complétée avec les réponses de l'élève
     * @param reponse_eleve
     * @return ...
     */
    void afficheReponseEleve(ArrayList reponse_eleve){

    }

}
