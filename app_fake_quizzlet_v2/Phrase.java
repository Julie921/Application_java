package app_fake_quizzlet_v2;

import java.util.ArrayList;

public class Phrase {

    String phraseCorrecte; //attribut qui contient la phrase sans les # et avec les réponses correctes
    String phraseAvecTrous; //attribut qui contient la phrase avec des ___ à la place des mots à placer

    ArrayList<String> motsAPlacer = new ArrayList<>(); //liste de tous les mots à placer dans l'ordre

    /**
     * TODO: update javadoc
     * Constructeur de la classe Phrase. Il prend en argument une String qui correspond à une phrase où aucun traitement n'a encore été effectué.
     * Il crée l'objet Phrase qui possède 3 attributs :
     * (1) la phrase correcte,
     * (2) la phrase avec les trous,
     * (3) la liste des mots à placer dans l'ordre
     * @param phraseNotParsed
     */
    Phrase(String phraseNotParsed, Metaparse unParseur){

        unParseur.parse(phraseNotParsed);

    }

    /**
     * Méthode qui permet de récupérer la variante à trous de l'objet Phrase.
     * @return (String) Notre phrase avec les trous
     */
    public String getPhraseAvecTrous() {
        return phraseAvecTrous;
    }

    /**
     * Méthode qui permet de récupérer la variante correcte de l'objet Phrase. Donc la phrase sans les # et avec les mots à la bonne place.
     * @return (String) La phrase correcte
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

    public ArrayList<String> getMotsAPlacer(){
        return motsAPlacer;
    }

}
