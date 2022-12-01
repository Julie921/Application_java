package app_fake_quizzlet_v2;

import java.util.ArrayList;

/**
 * TODO: écrire javadoc
 */
public interface Correction {

    public void corrige(ArrayList<ArrayList<String>> reponseEleve, Exercice exercice);

    public ArrayList<ArrayList<ValeurReponse>> getListValeursReponses();

}
