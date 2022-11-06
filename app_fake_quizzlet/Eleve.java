package app_fake_quizzlet;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

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
     */
    void afficheReponseEleve(ArrayList reponse_eleve){

    }

}
