package app_fake_quizzlet_v2;

import java.util.ArrayList;

/**
 *  La classe PhraseATrous hérite de la classe Phrase et représente une phrase à trous.
 *
 *  Elle contient trois attributs :
 *  - la phrase correcte, qui est la phrase sans les trous et avec les réponses correctes
 *  - la phrase avec les trous, qui est la phrase avec des "___" à la place des mots à placer
 *  - la liste des mots à placer, qui est une liste de tous les mots à placer dans l'ordre
 *
 *  La classe dispose de plusieurs méthodes permettant de récupérer ou de set ces différents attributs.
 */
public class PhraseATrous extends Phrase{

    String phraseCorrecte; //attribut qui contient la phrase sans les # et avec les réponses correctes
    String phraseAvecTrous; //attribut qui contient la phrase avec des ___ à la place des mots à placer

    ArrayList<String> motsAPlacer = new ArrayList<>(); //liste de tous les mots à placer dans l'ordre

    /**
     * Constructeur de la classe PhraseATrous.
     * Il prend en paramètre la phrase correcte, la phrase avec les trous et la liste des mots à placer.
     * Il initialise les attributs de la classe avec ces valeurs.
     * @param phraseCorrecte la phrase correcte
     * @param phraseAvecTrous la phrase avec les trous
     * @param motsAPlacer la liste des mots à placer dans l'ordre correct
     */
    PhraseATrous(String phraseCorrecte, String phraseAvecTrous, ArrayList<String> motsAPlacer) {
        this.phraseCorrecte = phraseCorrecte;
        this.phraseAvecTrous = phraseAvecTrous;
        this.motsAPlacer = motsAPlacer;
    }

    /**
     * Méthode qui permet de récupérer la phraseAvecTrous
     * @return Notre phrase avec les trous
     */
    public String getPhraseAvecTrous() {
        return phraseAvecTrous;
    }

    /**
     * Méthode qui permet de récupérer la variante correcte de l'objet Phrase.
     * Donc la phrase sans les # et avec les mots à la bonne place.
     * @return La phrase correcte
     */
    public String getPhraseCorrecte() {
        return phraseCorrecte;
    }

    /**
     * Méthode qui permet de set l'attribut phraseCorrecte d'un objet Phrase
     * @param phraseCorrecte la phrase correcte, en somme la phrase de l'input sans les "#"
     */
    public void setPhraseCorrecte(String phraseCorrecte){
        this.phraseCorrecte = phraseCorrecte;
    }

    /**
     * Méthode qui permet de set l'attribut phraseAvecTrous d'un objet Phrase
     * @param phraseAvecTrous la phrase avec les trous, en somme la phrase de l'input avec les "___" à la place des mots entre #
     */
    public void setPhraseAvecTrous(String phraseAvecTrous){
        this.phraseAvecTrous = phraseAvecTrous;
    }

    /**
     * Méthode qui permet de set l'attribut motsAPlacer d'un objet Phrase.
     * Ca correspond à la liste de tous les mots à placer pour une phrase donnée, dans l'ordre.
     * @param motsAPlacer la liste des mots
     */
    public void setMotsAPlacer(ArrayList<String> motsAPlacer){
        this.motsAPlacer.addAll(motsAPlacer);
    }

    /**
     * Méthode qui permet de récupérer l'attribut motsAPlacer qui est la liste de tous les mots
     * à placer de la phrase, dans l'ordre correct.
     * @return motsAPlacer
     */
    public ArrayList<String> getMotsAPlacer(){
        return motsAPlacer;
    }

    /**
     * Méthode toString qui permet de représenter sous forme de chaîne de caractères l'objet PhraseATrous.
     * @return (String) La phrase correcte, la phrase avec les trous et la liste des mots à placer
     */
    @Override
    public String toString() {
        return "PhraseATrous{ " +
                "phraseCorrecte='" + phraseCorrecte + '\'' +
                ", phraseAvecTrous='" + phraseAvecTrous + '\'' +
                ", motsAPlacer=" + motsAPlacer +
                '}';
    }
}
