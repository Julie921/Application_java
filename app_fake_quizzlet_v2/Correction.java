package app_fake_quizzlet_v2;

import java.util.ArrayList;

/**
 * Interface Correction. Elle permet d'implémenter la méthode correction dans des classes.
 */
public interface Correction {

    /**
     * Méthode corrige()
     * @param reponseEleve qui est une liste de liste de string
     * @param exercice qui est un objet de la classe Exercice.
     */
    public void corrige(ArrayList<ArrayList<String>> reponseEleve, Exercice exercice);

    /** Comprends pas
     * @param
     * @return
     */
    public ArrayList<ArrayList<ValeurReponse>> getListValeursReponses();

}
