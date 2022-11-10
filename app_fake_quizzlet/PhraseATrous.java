package app_fake_quizzlet;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Classe pour les exercices du type "phrases à trou".
 * Le constructeur contient une liste vide de texte_a_trous et de mots_a_placer, 
 * et une liste de string contenant la phrase_complete.
 */
public class PhraseATrous extends Exercice {

    ArrayList<String> texte_a_trous = new ArrayList<>();
    ArrayList<String> mots_a_placer = new ArrayList<>();
    String[] phrase_complete;

/**
 * Méthode pour créer la phrase à trous et la notation depuis l'input du professeur et le nombre de point maximum
 * @param input_prof
 * @param nb_points
 * @return rien
 */
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

    /**
     * Méthode pour afficher la phrase avec les mots manquants. C'est cette phrase qui sera montrer à l'élève
     * @param rien
     * @return rien 
     */
    public void affichePhraseAvecTrous() {
        System.out.println("Complétez la phrase suivante : " + String.join(" ", texte_a_trous));
    }

    /**
     * Méthode pour afficher la liste des mots à placer. On affiche pour l'élève la liste des mots à placer.
     * @param rien 
     * @return rien
     */
    public void afficheMotsAPlacer() {
        System.out.println("Les mots à placer sont : " + String.join(", ", mots_a_placer));
    }

    /**
     * Méthode pour avoir la version corrigée de la phrase.
     * @param rien 
     * @return rien
     */
    public void getCorrection() {
        System.out.println("La phrase correcte est : " + String.join(" ", phrase_complete));
    }

    /**
     * Cette méthode donne la note de l'élève. Elle affiche la note.
     * @param reponse_eleve
     * @return rien
    */
    public void gradeExercice(ArrayList reponse_eleve) {
        int note = 0;
        int bareme = nb_points/mots_a_placer.size();

        for(int i=0; i<mots_a_placer.size(); i++){
            if(reponse_eleve.get(i).equals(mots_a_placer.get(i))) {
                note += bareme;
            }
        }
        System.out.println("Note : " + note + "/" + nb_points);
    }
/**
 * Méthode qui permet d'avoir la phrase de l'élève avec ses réponses. 
 * @param reponse_eleve
 * @return phraseEleve (sous forme de String)
 */
    public String phraseEleve(ArrayList<String> reponse_eleve){
        ArrayList<String> phrase = new ArrayList<>();
        int j = 0;
        for(int i=0; i<texte_a_trous.size(); i++){
            if( texte_a_trous.get(i).equals("____")) {
                phrase.add(reponse_eleve.get(j));
                j+=1;
            }
            else {
                phrase.add(texte_a_trous.get(i));
            }
        }
        return String.join(" ",phrase);
    }



}
