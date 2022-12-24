package app_fake_quizzlet_v2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Classe ExoATrous qui étend la classe abstraite Exercice.
 */
public class ExoATrous extends Exercice{

    /**
     * Déclaration des attributs de la classe
     */
    private ArrayList<Phrase> listPhrases = new ArrayList<>(); //tous les objets phrases constitués avec l'input

    /**
     * Méthode pour créer la phrase à trous et la notation depuis l'input du professeur
     * @param inputProf
     * @return rien
     */
    ExoATrous(String inputProf) {

        String[] listPhrasesNotParsed = inputProf.split("(?<=[?!.])"); //on split sur la ponctuation en la gardant

        Pattern pattern = Pattern.compile("#([^#]+)#"); //on récupère ce qui se trouve entre les #

        for(String phraseNotParsed : listPhrasesNotParsed){ //pour chaque phrase non parsée dans la liste des phrases non parsées

            ParseurPhraseATrous monParseur = new ParseurPhraseATrous(); //on crée un parseur spécifique pour parser en phrases à trous

            Phrase phraseParsed = new Phrase(phraseNotParsed, monParseur, pattern); //on crée notre phrase et on la parse

            //on instancie les attributs de notre objet Phrase en récupérant les résultats de notre parseur
            phraseParsed.setPhraseCorrecte(monParseur.getPhraseCorrecte());
            phraseParsed.setPhraseAvecTrous(monParseur.getPhraseAvecTrous());
            phraseParsed.setMotsAPlacer(monParseur.getMotsAPlacer());

            listPhrases.add(phraseParsed); //on ajoute notre Phrase à la liste de Phrase

        }

    }

    /**
     * Getter de l'attribut listPhrases
     * @return listPhrase
     */
    public ArrayList<Phrase> getListPhrases(){
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

        for (Phrase phrase : listPhrases) {
            for (String mot : phrase.getMotsAPlacer()) {
                allMotsAPlacer.add(mot);
            }
        }

        // on randomise la liste des des mots à placer
        Collections.shuffle(allMotsAPlacer);

        // on affiche la liste des mots à placer
        System.out.println("Les mots à placer sont : " + String.join(", ", allMotsAPlacer)); //on affiche la liste de tous les mots à placer

        // on affiche la phrase avec les trousS.
        for (Phrase phrase : listPhrases) {
            System.out.println(phrase.getPhraseAvecTrous());
        }
    }


    /**
     * TODO: écrire javadoc
     * @param newPhrase
     */
    public void addPhrase(String newPhrase){ //TODO écrire fonction pour pouvoir ajouter une phrase après

    }

}
