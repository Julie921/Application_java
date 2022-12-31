package app_fake_quizzlet_v2;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Classe ExoATrous qui étend la classe abstraite Exercice.
 */
public class ExoATrous extends Exercice {

    /**
     * Déclaration des attributs de la classe
     */
    private ArrayList<PhraseATrous> listPhrases = new ArrayList<>(); //tous les objets phrases constitués avec l'input

    /**
     * Méthode pour créer la phrase à trous et la notation depuis l'input du professeur
     * @param inputProf
     * @return rien
     */
    ExoATrous(String langue, BaremeNiveau niveau, ParseurPhraseATrous parseur, String inputProf) {

        super(langue, niveau);

        String[] listPhrasesNotParsed = inputProf.split("(?<=[?!.])"); //on split sur la ponctuation en la gardant

        for(String phraseNotParsed : listPhrasesNotParsed){ //pour chaque phrase non parsée dans la liste des phrases non parsées

            listPhrases.add(parseur.parse(phraseNotParsed)); //on parse avec le parseur de phrase à trous et on l'ajoute à la liste

        }

    }

    /**
     * Getter de l'attribut listPhrases
     * @return listPhrase
     */
    public ArrayList<PhraseATrous> getListPhrases(){
        return listPhrases;
    }

    /**
     * Implémentation de la méthode afficheExercice()
     * @param
     * @return rien
     * Elle permet d'afficher l'exercice
     */
    @Override
    public void afficheExercice() {

        //on récupère tous les mots à placer de chaque phrase de l'exercice
        ArrayList<String> allMotsAPlacer = new ArrayList<>();

        for (PhraseATrous phrase : listPhrases) {
            allMotsAPlacer.addAll(phrase.getMotsAPlacer());
        }

        // on randomise la liste des des mots à placer
        Collections.shuffle(allMotsAPlacer);

        // on affiche la liste des mots à placer
        System.out.println("Les mots à placer sont : " + String.join(", ", allMotsAPlacer) + "\n\n"); //on affiche la liste de tous les mots à placer

        // on affiche la phrase avec les trousS.
        for (PhraseATrous phrase : listPhrases) {
            System.out.println(phrase.getPhraseAvecTrous());
        }
    }


    /**
     * TODO: écrire javadoc
     * @param newPhrase
     *
     */
    public void addPhrase(String newPhrase){ //TODO écrire fonction pour pouvoir ajouter une phrase après

    }

}
