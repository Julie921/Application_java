package app_fake_quizzlet_v2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

public class ExoATrous extends Exercice{

    private ArrayList<Phrase> listPhrases = new ArrayList<>(); //tous les objets phrases constitués avec l'input

    /**
     * Méthode pour créer la phrase à trous et la notation depuis l'input du professeur
     * TODO: corriger javadoc
     * @param inputProf
     * @return rien
     */
    ExoATrous(String inputProf) {

        String[] listPhrasesNotParsed = inputProf.split("(?<=[?!.])"); //on split sur la ponctuation en la gardant

        for(String phraseNotParsed : listPhrasesNotParsed){ //pour chaque phrase non parsée dans la liste des phrases non parsées

            ParseurPhraseATrous monParseur = new ParseurPhraseATrous(); //on crée un parseur spécifique pour parser en phrases à trous

            Phrase phraseParsed = new Phrase(phraseNotParsed, monParseur); //on crée notre phrase et on la parse

            //on instancie les attributs de notre objet Phrase en récupérant les résultats de notre parseur
            phraseParsed.setPhraseCorrecte(monParseur.getPhraseCorrecte());
            phraseParsed.setPhraseAvecTrous(monParseur.getPhraseAvecTrous());
            phraseParsed.setMotsAPlacer(monParseur.getMotsAPlacer());

            listPhrases.add(phraseParsed); //on ajoute notre Phrase à la liste de Phrase

        }

    }

    /**
     * TODO: écrire javadoc
     * @return
     */
    public ArrayList<Phrase> getListPhrases(){
        return listPhrases;
    }

    /**
     * TODO: écrire javadoc
     */
    @Override
    public void afficheExercice() { //TODO écrire la fonction

        //on récupère tous les mots à placer de chaque phrase de l'exercice
        ArrayList<String> allMotsAPlacer = new ArrayList<>();

        for(Phrase phrase : listPhrases){
            for(String mot : phrase.getMotsAPlacer()){
                allMotsAPlacer.add(mot);
            }
        }

        Collections.shuffle(allMotsAPlacer); //on randomise la liste

        System.out.println("Les mots à placer sont : "+String.join(", ", allMotsAPlacer)); //on affiche la liste de tous les mots à placer

        for(Phrase phrase : listPhrases){
            System.out.println(phrase.getPhraseAvecTrous()); //on affiche la phrase avec les trous
        }
    }

    @Override
    public void corrigeExercice() { //TODO écrire la fonction

    }

    /**
     * TODO: écrire la javadoc
     */
    public void afficheTexte(){
        for(Phrase phrase : listPhrases){
            for(String mot : phrase.motsAPlacer){
                System.out.println(mot);
            }
            System.out.println("");
        }

    }

    /**
     * TODO: écrire javadoc
     * @param newPhrase
     */
    public void addPhrase(String newPhrase){ //TODO écrire fonction pour pouvoir ajouter une phrase après

    }

}
