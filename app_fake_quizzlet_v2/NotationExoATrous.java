package app_fake_quizzlet_v2;

import java.util.ArrayList;

/**
 * Classe NotationExoATrous qui implémente l'interface Notation. Elle contient la notation de l'élève.
 */
public class NotationExoATrous implements Notation {

    @Override
    /**
     * Implémentation de la méthode getNote()
     * @param Eleve eleve, Liste de lsite de string
     * @return noteFinale
     */
    public float getNote(Eleve eleve, ArrayList<ArrayList<ValeurReponse>> reponseEleve) {
        // déclaration de variable
        float note = 0;
        float max;
        float noteFinale;
        // on récupère le maximum possible
        max = getMax(eleve, reponseEleve);

        // Boucle qui permet de récupérer la valeur des réponses d'un élève. On attribut la note
        // en fonction du niveau de l'élève.
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
            // Si la note final est négative, on met juste noteFinale=0
            noteFinale = 0;
        }
        else{
            // Sinon, on construit la note sur 20.
            noteFinale = (note/max) * 20;
        }
        return noteFinale;
    }

        @Override
        /**
         * Implémentation de la méthode getMax()
         * @param Eleve eleve, Liste de liste de String
         * @return maxPoints, nombre de points maximum qu'un élève peut avoir sur un exercice en fonction de son niveau
         * // TODO : création classe Reponse ?
         */
        public float getMax(Eleve eleve, ArrayList<ArrayList<ValeurReponse>> reponseEleve){

            int maxPoints;
            int nbMots = 0;

            for (ArrayList list : reponseEleve) { //on récupère le nombre de mots à placer, ce qui correspond au nombre d'éléments dans reponseEleve
                nbMots = nbMots + list.size();
            }

            maxPoints = nbMots * eleve.getBaremeNiveau().getVrai(); //récupérer le maximum de points qu'il est possible d'obtenir pour un exercice donné

            return maxPoints;
        }


}
