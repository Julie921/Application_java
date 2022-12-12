package app_fake_quizzlet_v2;

import java.util.ArrayList;

/**
 * Interface Notation qui permet d'implémenter des méthodes servant à construire une note.
 */
public interface Notation {

    /**
     * Méthode getNote()
     * @param eleve
     * @param reponseEleve
     */
    public float getNote(Eleve eleve, ArrayList<ArrayList<ValeurReponse>> reponseEleve);

    /**
     * Méthode getMax()
     * @param eleve
     * @param reponseEleve
     */
    public float getMax(Eleve eleve, ArrayList<ArrayList<ValeurReponse>> reponseEleve);
}
