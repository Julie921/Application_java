package app_fake_quizzlet_v2;

import java.util.ArrayList;

public interface Notation {

    public float getNote(Eleve eleve, ArrayList<ArrayList<ValeurReponse>> reponseEleve);

    public float getMax(Eleve eleve, ArrayList<ArrayList<ValeurReponse>> reponseEleve);
}
