package application_fake_quizlet;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        PhraseATrous exercice_test = new PhraseATrous("Ceci est un #chien#. J'adore les #chiens#. Mais j'aime bien les #chats# aussi", 6);
        exercice_test.affichePhraseAvecTrous();
        exercice_test.afficheMotsAPlacer();



        System.out.println("------------------------------------------");

        Eleve julie = new Eleve("Julie");
        ArrayList<String> reponse_julie = julie.doExercice(exercice_test);
        exercice_test.gradeExercice(reponse_julie);


        //Window page_accueil = new Window("Fake Quizlet", "Accueil", true);
        //JLabel message_accueil = new JLabel("Bonjour ! Qui êtes-vous ?");
        //page_accueil.panel_body.add(message_accueil);
        //JButton choix_accueil_prof = new JButton("Professeur.e");
        //JButton choix_accueil_eleve = new JButton("Elève");
        //page_accueil.panel_body.add(choix_accueil_eleve);
        //page_accueil.panel_body.add(choix_accueil_prof);



    }

}