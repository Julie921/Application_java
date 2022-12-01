package app_fake_quizzlet_v2;

import java.util.ArrayList;

/**
 * TODO: écrire javadoc
 */
public class CorrectionExoATrous implements Correction{

    ArrayList<ArrayList<ValeurReponse>> listValeursReponses = new ArrayList<>(); //liste de "VRAI", "FAUX", "NA"

    @Override
    public void corrige(ArrayList<ArrayList<String>> reponseEleve, Exercice exercice) { //TODO: écrire la javadoc

        try {
            if(exercice instanceof ExoATrous){ //on vérifie que c'est bien un Exercice du type ExoATrous

                for(int i=0; i<((ExoATrous) exercice).getListPhrases().size();i++){ //pour i allant de 0 au nombre de phrases dans l'exo

                    ArrayList<ValeurReponse> listTampon = new ArrayList<>(); //on remplit la liste et on la vide pour chaque phrase

                    for(int j=0; j<((ExoATrous) exercice).getListPhrases().get(i).getMotsAPlacer().size(); j++){ //pour j allant de 0 au nombre de mots à placer dans une phrase
                        if(reponseEleve.get(i).get(j).equals(((ExoATrous) exercice).getListPhrases().get(i).getMotsAPlacer().get(j))){ //si la réponse de l'élève correspond à la bonne réponse
                            listTampon.add(ValeurReponse.VRAI);
                        }
                        else if (reponseEleve.get(i).get(j).isEmpty()) { //si l'élève n'a pas répondu
                            listTampon.add(ValeurReponse.NA);
                        }
                        else{ //si la réponse de l'élève est fausse
                            listTampon.add(ValeurReponse.FAUX);
                        }

                    }

                    listValeursReponses.add(listTampon);

                }
            }
        else{
            System.out.println("Pas la bonne classe"); //on ne peut pas préciser que c'est un ExoATrous
        }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<ArrayList<ValeurReponse>> getListValeursReponses(){
        return listValeursReponses;
    }

}
