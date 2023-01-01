package app_fake_quizzlet_v2;

import java.util.ArrayList;
import java.util.Scanner;

public class ReponseEleveExoATrous extends ReponseEleve {

    ReponseEleveExoATrous(ExoATrous exercice, Eleve eleve){

        super(exercice, eleve);
        this.setSeuilPassation();

        System.out.println("Bonjour " + eleve.getPseudo() + "!\n\n");

        exercice.afficheExercice();

        Scanner myScanner = new Scanner(System.in); // scanner pour récupérer les réponses de l'élève

        ArrayList<String> listeTampon = new ArrayList<>(); //on crée une liste tampon qu'on videra après chaque phrase

        int i = 1;
        for (PhraseATrous phrase : exercice.getListPhrases()) { //pour chaque phrase de l'exercice
            listeTampon.clear(); // on vide la liste tampon
            for (String mot : phrase.getMotsAPlacer()) { //pour chaque mot à placer dans la phrase
                System.out.println("Quel est le mot manquant " + i + "?"); //on demande le mot manquant à la place X
                String motDonne = myScanner.nextLine();
                listeTampon.add(motDonne); //on ajoute notre mot à la liste tampon
                i += 1;
            }
            this.reponsesFournies.add(new ArrayList<String>(listeTampon));
        }

        this.corrige();
        this.calculNote();

    }

    @Override
    public void setSeuilPassation() {
        ExoATrous exo = (ExoATrous) this.exercice;
        Float totalReponsesAFournir = 0.0F;
        for (PhraseATrous phrase : exo.getListPhrases()) {
            totalReponsesAFournir += phrase.motsAPlacer.size();
        }
        this.seuilPassation = exo.getPourcentage() * totalReponsesAFournir * exo.getNiveau().getVrai();
    }

    @Override
    public void calculNote() {
        BaremeNiveau niveauExercice = this.getExercice().getNiveau();
        for (ArrayList<ValeurReponse> phraseCorrigee : this.getReponsesCorrection()) {
            for (ValeurReponse v : phraseCorrigee) {
                switch (v) {
                    case NA:
                        this.noteDonnee += niveauExercice.getNa();
                        break;
                    case FAUX:
                        this.noteDonnee += niveauExercice.getFaux();
                        break;
                    case VRAI:
                        this.noteDonnee += niveauExercice.getVrai();
                        break;
                    default:
                        System.out.println("Valeur incorrecte");
                        break;
                }
            }
        }
    }

    @Override
    public void corrige() {
        ArrayList<ValeurReponse> listTampon = new ArrayList<>();
        ExoATrous exo = (ExoATrous) exercice;
        ArrayList<PhraseATrous> listPhrases = exo.getListPhrases();

        for(int i=0; i < listPhrases.size(); i++) { //pour i allant de 0 au nombre de phrases dans l'exo

            listTampon.clear(); // on vide la liste tampon après chaque phrase
            PhraseATrous phrase = listPhrases.get(i);

            for(int j=0; j < phrase.getMotsAPlacer().size(); j++){ //pour j allant de 0 au nombre de mots à placer dans une phrase
                String reponseElevePourLaPhraseIEtLeMotJ = this.reponsesFournies.get(i).get(j);

                if(reponseElevePourLaPhraseIEtLeMotJ.equals(phrase.getMotsAPlacer().get(j))){ //si la réponse de l'élève correspond à la bonne réponse
                    listTampon.add(ValeurReponse.VRAI);
                }
                else if (reponseElevePourLaPhraseIEtLeMotJ.isEmpty()) { //si l'élève n'a pas répondu
                    listTampon.add(ValeurReponse.NA);
                }
                else{ //si la réponse de l'élève est fausse
                    listTampon.add(ValeurReponse.FAUX);
                }

            }

            this.getReponsesCorrection().add(new ArrayList<ValeurReponse>(listTampon));
        }
    }
}
