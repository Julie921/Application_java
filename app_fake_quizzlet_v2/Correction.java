package app_fake_quizzlet_v2;

import java.util.ArrayList;

/**
 * Interface Correction. Elle permet d'implémenter la méthode correction dans des classes.
 */
public interface Correction {

    /**
     * Méthode corrige()
     * @param reponseEleve qui est une liste de liste de string
     */
    public void corrige(ReponseEleve reponseEleve);

    /** Comprends pas
     * @param
     * @return
     */
    public ArrayList<ArrayList<ValeurReponse>> getListValeursReponses();

}
