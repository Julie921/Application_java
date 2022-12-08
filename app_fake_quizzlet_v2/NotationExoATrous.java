package app_fake_quizzlet_v2;

import java.util.ArrayList;

public class NotationExoATrous implements Notation {

    @Override
    public float getNote(Eleve eleve, ArrayList<ArrayList<ValeurReponse>> reponseEleve) { //TODO: écrire javadoc

        float note = 0;
        float max;
        float noteFinale;
        max = getMax(eleve, reponseEleve);

        for (ArrayList list : reponseEleve) {
            for (Object valeur : list) {
                if (valeur == ValeurReponse.VRAI) {
                    note = note + eleve.getBaremeNiveau().getVrai();
                } else if (valeur == ValeurReponse.FAUX) {
                    note = note + eleve.getBaremeNiveau().getFaux();
                } else if (valeur == ValeurReponse.NA) {
                    note = note + eleve.getBaremeNiveau().getNa();
                }
            }
        }
        if(note < 0){
            noteFinale = 0;
        }
        else{
            noteFinale = (note/max) * 20;
        }
        return noteFinale;
    }

        @Override
        public float getMax(Eleve eleve, ArrayList<ArrayList<ValeurReponse>> reponseEleve){ //TODO: rempalcer par objet Reponse

            int maxPoints;
            int nbMots = 0;

            for (ArrayList list : reponseEleve) { //on récupère le nombre de mots à placer, ce qui correspond au nombre d'éléments dans reponseEleve
                nbMots = nbMots + list.size();
            }

            maxPoints = nbMots * eleve.getBaremeNiveau().getVrai(); //récupérer le maximum de points qu'il est possible d'obtenir pour un exercice donné

            return maxPoints;
        }


}
