package app_fake_quizzlet;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        PhraseATrous exercice_test = new PhraseATrous("En été je porte tous les jours un t-shirt en #coton#, un short et des sandales. Tiens, je vais te donner un sac en #plastique# pour mettre tout ça ! Mémé m'a acheté un magnifique pull en #laine# en Irlande ? Je voudrais m'acheter une veste en #cuir# mais je n'ai pas assez d'argent. ", 6);

        System.out.println("------------------------------------------");

        Eleve julie = new Eleve("Julie");
        ArrayList<String> reponse_julie = julie.doExercice(exercice_test);
        exercice_test.gradeExercice(reponse_julie);
        System.out.println("La réponse de l'élève est : " + exercice_test.phraseEleve(reponse_julie));

        exercice_test.getCorrection();


        //Window page_test = new Window("Fake Quizlet", "Phrases à trous", true);



        //Window page_accueil = new Window("Fake Quizlet", "Accueil", true);
        //JLabel message_accueil = new JLabel("Bonjour ! Qui êtes-vous ?");
        //page_accueil.panel_body.add(message_accueil);
        //JButton choix_accueil_prof = new JButton("Professeur.e");
        //JButton choix_accueil_eleve = new JButton("Elève");
        //page_accueil.panel_body.add(choix_accueil_eleve);
        //page_accueil.panel_body.add(choix_accueil_prof);



    }

}