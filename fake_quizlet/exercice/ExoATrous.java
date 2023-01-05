package fake_quizlet.exercice;

import fake_quizlet.notation.BaremeNiveau;
import fake_quizlet.parseurs.ParseurPhraseATrous;
import fake_quizlet.reponse.ReponseEleveExoATrous;
import fake_quizlet.utilisateurs.Eleve;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Classe ExoATrous qui étend la classe abstraite Exercice.
 * Elle représente un exercice de type "phrase à trous" où l'élève doit remplir les trous avec les mots à placer fournis.
 *
 * Elle contient une liste de phrases {@link PhraseATrous} qui constituent l'exercice.
 *
 * Elle implémente la méthode {@link #afficheExercice()} pour afficher l'exercice à l'élève.
 */
public class ExoATrous extends Exercice {

    /**
     * ArrayList qui contient les objets {@link PhraseATrous} créés à partir de l'input donné par le professeur.
     */
    private ArrayList<PhraseATrous> listPhrases = new ArrayList<>();

    /**
     * Constructeur de la classe ExoATrous.
     * Il permet de créer l'exercice de type "phrase à trous" en utilisant le {@link ParseurPhraseATrous}
     * pour parser les phrases données en entrée par le professeur (paramètre {@code inputProf}).
     *
     * @param langue langue de l'exercice
     * @param niveau niveau de difficulté de l'exercice
     * @param pourcentage pourcentage de points par l'élève en faisant l'exercice pour que l'exercice soit considéré comme réussi
     * @param parseur parseur de phrases à trous
     * @param inputProf entrée du professeur contenant les phrases de l'exercice
     */
    public ExoATrous(Langue langue, BaremeNiveau niveau, Float pourcentage, ParseurPhraseATrous parseur, String inputProf) {

        super(langue, niveau, pourcentage);

        String afterPunct = "(?<=[?!.。])";

        String[] listPhrasesNotParsed = inputProf.split(afterPunct); //on split sur la ponctuation en la gardant

        for(String phraseNotParsed : listPhrasesNotParsed){ //pour chaque phrase non parsée dans la liste des phrases non parsées

            listPhrases.add(parseur.parse(phraseNotParsed)); //on parse avec le parseur de phrase à trous et on l'ajoute à la liste

        }

        super.setType(TypeExo.EXO_A_TROU);
    }

    /**
     * Getter de l'attribut listPhrases. Il contient la liste de tous les objets {@link PhraseATrous} qui composent l'exercice
     * @return listPhrase
     */
    public ArrayList<PhraseATrous> getListPhrases(){
        return listPhrases;
    }

    /**
     * Implémentation de la méthode abstraite afficheExercice() de la classe {@link Exercice}.
     * Elle affiche :
     *
     * - la liste des mots à placer arrangés de manière aléatoire
     * - le texte avec des "___" à la place des mots à placer
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
        System.out.println("Les mots à placer sont : " + String.join(", ", allMotsAPlacer) + "\n"); //on affiche la liste de tous les mots à placer

        // on affiche la phrase avec les trousS.
        for (PhraseATrous phrase : listPhrases) {
            System.out.println(phrase.getPhraseAvecTrous());
        }
    }

    @Override
    public void previewText() {
        // Affiche seulement les 10 premières phrases du texte
        for (int i = 0; i < 3 && i < listPhrases.size(); i++) {
            System.out.println(listPhrases.get(i).getPhraseAvecTrous());
        }
        // Affiche un message indiquant qu'il y a plus de phrases dans le texte
        if (listPhrases.size() > 3) {
            System.out.println("...");
        }
        else {
            System.out.println("");
        }
    }

    //TODO: écrire la javadoc
    @Override
    public ReponseEleveExoATrous construireReponse(Eleve eleve) {
        return new ReponseEleveExoATrous(this, eleve);
    }

    /**
     *  Affiche la correction de chaque phrase de l'exercice à trous, c'est-à-dire les vraies réponses qu'il fallait fournir.
     */
    @Override
    public void afficherCorrection() {
        System.out.println("\n");
        for(PhraseATrous phrase: listPhrases){
            System.out.println(phrase.getPhraseCorrecte());
        }
        System.out.println("\n");
    }

    /**
     * Redéfinition de la méthode toString() de la classe Object.
     * @return (String) Une chaîne de caractères qui représente l'objet ExoATrous.
     */
    @Override
    public String toString() {
        return "ExoATrous{" +
                "listPhrases=" + listPhrases +
                '}';
    }


}
