package application_fake_quizlet;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Classe pour les exercices du type "phrases à trou".
 */
public class PhraseATrous extends Exercice {

    ArrayList<String> texte_a_trous = new ArrayList<>();
    ArrayList<String> mots_a_placer = new ArrayList<>();
    String[] phrase_complete;

    PhraseATrous(String input_prof, int nb_points) {

        this.input_prof = input_prof;
        this.nb_points = nb_points;

        phrase_complete = input_prof.split(" ");

        for(int i=0; i<phrase_complete.length; i++) {
            Pattern p = Pattern.compile("#([A-Za-zÀ-ÿ]+)#"); //[A-Za-zÀ-ÿ '-]
            Matcher m = p.matcher(phrase_complete[i]);
            boolean matched = m.find();

            if(matched){
                mots_a_placer.add(m.group(1));
                texte_a_trous.add("____");
            }
            else{
                texte_a_trous.add(phrase_complete[i]);
            }

        }
    }

    public void affichePhraseAvecTrous() {
        System.out.println("Complétez la phrase suivante : " + String.join(" ", texte_a_trous));
    }

    public void afficheMotsAPlacer() {
        System.out.println("Les mots à placer sont : " + String.join(", ", mots_a_placer));
    }

    public void getCorrection() {
        System.out.println("La phrase correcte est : " + String.join(" ", phrase_complete));
    }

    public void gradeExercice(ArrayList reponse_eleve) {
        int note = 0;
        int bareme = nb_points/mots_a_placer.size();

        for(int i=0; i<mots_a_placer.size(); i++){
            if(reponse_eleve.get(i).equals(mots_a_placer.get(i))) {
                note += bareme;
            }
        }
        System.out.println(note);
    }



}
