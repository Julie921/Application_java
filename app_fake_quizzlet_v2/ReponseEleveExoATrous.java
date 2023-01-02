package app_fake_quizzlet_v2;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Classe représentant une réponse à un exercice à trous donnée par un élève.
 * Cette classe hérite de ReponseEleve et définit la méthode abstraite setSeuilPassation() pour déterminer le seuil de passation de l'exercice.
 * Elle implémente également les méthodes calculNote() et corrige() pour respectivement calculer la note de la réponse et corriger les réponses de l'élève.
 */
public class ReponseEleveExoATrous extends ReponseEleve {

    /**
     * Constructeur de la classe ReponseEleveExoATrous.
     * Demande à l'élève de répondre à l'exercice à trous, corrige ses réponses ({@link #corrige}) et calcule sa note ({@link #calculNote()})
     * @param exercice l'exercice à trous à compléter.
     * @param eleve l'élève qui répond à l'exercice.
     */
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

    /**
     * Méthode qui définit le seuil de passation de l'exercice à trous,
     * en somme la note qu'il faut atteindre pour que l'exercice soit considéré comme réussi
     * Le seuil de passation est égal au pourcentage d'exigence de l'exercice (renseigné par le professeur lors de la création des exercices) multiplié par le nombre de réponses à fournir en tout, multiplié par le nombre de points attribués à une réponse correcte selon le {@link BaremeNiveau} de l'élève.
     */
    @Override
    public void calculNote() {
        BaremeNiveau niveauExercice = this.getExercice().getNiveau();
        for (ArrayList<ValeurReponse> phraseCorrigee : this.getReponsesCorrection()) {
            for (ValeurReponse v : phraseCorrigee) {
                switch (v) {
                    case NR:
                        this.noteDonnee += niveauExercice.getNr();
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

    /**
     * Méthode qui corrige les réponses de l'élève à l'exercice à trous.
     * Pour chaque phrase de l'exercice, la méthode compare chaque réponse de l'élève à la réponse attendue et attribue la valeur VRAI si la réponse de l'élève est correcte, FAUX si elle est incorrecte et NR si elle est vide.
     */
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
                    listTampon.add(ValeurReponse.NR);
                }
                else{ //si la réponse de l'élève est fausse
                    listTampon.add(ValeurReponse.FAUX);
                }

            }

            this.getReponsesCorrection().add(new ArrayList<ValeurReponse>(listTampon));
        }
    }
}
