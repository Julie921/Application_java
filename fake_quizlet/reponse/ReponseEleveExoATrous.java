package fake_quizlet.reponse;

import fake_quizlet.exercice.ExoATrous;
import fake_quizlet.exercice.PhraseATrous;
import fake_quizlet.notation.ValeurReponse;
import fake_quizlet.notation.BaremeNiveau;
import fake_quizlet.utilisateurs.Eleve;
import org.fusesource.jansi.Ansi;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public ReponseEleveExoATrous(ExoATrous exercice, Eleve eleve) {
        // Appel du constructeur de la classe parente
        super(exercice, eleve);

        // Définition du seuil de passation pour l'exercice
        this.setSeuilPassation();

        // Affichage de l'exercice complet (liste des mots à placer et phrases avec trous)
        exercice.afficheExercice();

        // Création d'un scanner pour récupérer les réponses de l'élève
        Scanner myScanner = new Scanner(System.in);

        // Liste tampon qui sera vidée après chaque phrase
        ArrayList<String> listeTampon = new ArrayList<>();

        // Compteur pour numéroter les mots manquants
        int i = 1;
        for (PhraseATrous phrase : exercice.getListPhrases()) { //pour chaque phrase de l'exercice
            listeTampon.clear(); // on vide la liste tampon
            System.out.println("\nPhrase " + i + " : " + phrase.getPhraseAvecTrous()); // on réimprime la phrase avec les trous pour la lisibilité
            int j = 1;
            for (String mot : phrase.getMotsAPlacer()) { //pour chaque mot à placer dans la phrase
                System.out.println("Quel est le mot manquant " + j + "?"); //on demande le mot manquant à la place J dans la phrase
                String motDonne = myScanner.nextLine();
                listeTampon.add(motDonne); //on ajoute notre mot à la liste tampon
                j++;
            }
            i++;
            this.reponsesFournies.add(new ArrayList<String>(listeTampon));
        }
        // On corrige les réponses de l'élève
        this.corrige();
        // On calcule la note de l'élève pour l'exercice
        this.calculNote();
    }


        @Override
    public void setSeuilPassation() {
        ExoATrous exo = (ExoATrous) this.exercice;
        Float totalReponsesAFournir = 0.0F;
        for (PhraseATrous phrase : exo.getListPhrases()) {
            totalReponsesAFournir += phrase.getMotsAPlacer().size();
        }
        this.seuilPassation = exo.getPourcentage() * totalReponsesAFournir;
    }

    @Override
    /**
     * Affiche les phrases de l'exercice avec les trous remplis par les réponses de l'élève.
     * Si la réponse de l'élève est correcte, le mot est affiché en vert.
     * Si la réponse est incorrecte, le mot est affiché en rouge.
     * Si la réponse n'a pas été fournie, "___" est affiché en jaune.
     *
     * @param pattern le motif de l'expression régulière utilisé pour détecter les trous dans
     *                les phrases
     */
    public void affichePhrasesRempliesAvecCouleurs(Pattern pattern) {
        // Crée une liste qui contient l'attribut phraseAvecTrous de chaque objet Phrase de l'exo
        ArrayList<String> allPhraseAvecTrous = new ArrayList<>();
        for (PhraseATrous phrase : ((ExoATrous) exercice).getListPhrases()) {
            allPhraseAvecTrous.add(phrase.getPhraseAvecTrous());
        }

        // Crée un StringBuffer qui contiendra les phrases dans lesquelles on a rempli les trous par les réponses de l'élève
        StringBuffer phrasesRemplies = new StringBuffer();
        for (int i = 0; i < allPhraseAvecTrous.size(); i++) {
            // Récupère la phrase et les réponses de l'élève pour cette phrase
            String phrase = allPhraseAvecTrous.get(i);
            ArrayList<String> response = reponsesFournies.get(i);
            Matcher matcher = pattern.matcher(phrase);

            int j = 0;
            while (matcher.find()) { // tant qu'on trouve des trous dans la phrase
                ValeurReponse correction = this.getReponsesCorrection().get(i).get(j);
                String replacement = null;
                switch (correction) { // en fonction de la correction, détermine la couleur de la réponse de l'élève
                    case VRAI:
                        replacement = Ansi.ansi().bg(Ansi.Color.GREEN).a(response.get(j)).reset().toString();
                        break;
                    case FAUX:
                        replacement = Ansi.ansi().bg(Ansi.Color.RED).a(response.get(j)).reset().toString();
                        break;
                    case NR:
                        replacement = Ansi.ansi().bg(Ansi.Color.YELLOW).a("___").reset().toString();
                        break;
                    default:
                        replacement = response.get(j);
                        break;
                }
                // Remplace le trou par la réponse de l'élève dans le StringBuffer
                matcher.appendReplacement(phrasesRemplies, replacement);
                j++;
            }
            // Ajoute la fin de la phrase au StringBuffer
            matcher.appendTail(phrasesRemplies);
            phrasesRemplies.append("\n");
        }

        // Affiche le StringBuffer qui contient les phrases remplies
        System.out.println(phrasesRemplies);
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
