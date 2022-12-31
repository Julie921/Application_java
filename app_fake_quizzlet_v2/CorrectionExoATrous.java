package app_fake_quizzlet_v2;

import java.util.ArrayList;

/**
 * Classe CorrectionExoATrous qui implémente l'interface Correction. C'est un objet qui contient une liste
 * de VRAI/FAUX/NA correspondant aux réponses de l'élève.
 */
public class CorrectionExoATrous implements Correction{

    /**
     * Déclaration des attributs
     */
    ArrayList<ArrayList<ValeurReponse>> listValeursReponses = new ArrayList<>(); //liste de "VRAI", "FAUX", "NA"

    @Override
    /**
     * Méthode corrige() qui permet de comparer les réponses de l'élèves avec les réponses attendues. Créer l'objet Correction.
     * @param liste de listes de string et un exercice
     */
    public void corrige(ReponseEleve reponseEleve) {

        try {
            Exercice exercice = reponseEleve.getExercice(); // on récupère l'exercice

            if(reponseEleve instanceof  ReponseEleveExoATrous){ //on vérifie que c'est bien un Exercice du type ExoATrous

                ArrayList<PhraseATrous> listPhrases = ((ExoATrous) exercice).getListPhrases(); // on récupère la liste d'objets PhraseATrous

                ArrayList<ArrayList<String>> motsEleve = reponseEleve.getReponses(); // on récupère la liste des réponses de l'élève

                ArrayList<ValeurReponse> listTampon = new ArrayList<>(); // création d'une liste tampon qu'on remplirera au fur et à mesure

                for(int i=0; i<listPhrases.size();i++){ //pour i allant de 0 au nombre de phrases dans l'exo

                    listTampon.clear(); // on vide la liste tampon après chaque phrase
                    PhraseATrous phrase = listPhrases.get(i);

                    for(int j=0; j<phrase.getMotsAPlacer().size(); j++){ //pour j allant de 0 au nombre de mots à placer dans une phrase
                        if(motsEleve.get(i).get(j).equals(phrase.getMotsAPlacer().get(j))){ //si la réponse de l'élève correspond à la bonne réponse
                            listTampon.add(ValeurReponse.VRAI);
                        }
                        else if (motsEleve.get(i).get(j).isEmpty()) { //si l'élève n'a pas répondu
                            listTampon.add(ValeurReponse.NA);
                        }
                        else{ //si la réponse de l'élève est fausse
                            listTampon.add(ValeurReponse.FAUX);
                        }

                    }

                    listValeursReponses.add(new ArrayList<ValeurReponse>(listTampon));

                }
            }
        else{
            System.out.println("Pas la bonne classe"); //on ne peut pas préciser que c'est un ExoATrous
        }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Getter de l'attribut ListValeursReponses
     * @return listValeursReponses
     */
    public ArrayList<ArrayList<ValeurReponse>> getListValeursReponses(){
        return listValeursReponses;
    }

}
