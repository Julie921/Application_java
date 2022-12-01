package app_fake_quizzlet_v2;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        //création d'un exercice de phrases à trous
        ExoATrous exerciceTest = new ExoATrous("Ceci est un #test# pour #tester#. Ce test a pour #but# de voir si notre #programme# fonctionne #correctement#. Est-ce que ça #marche# ?", 10);
        exerciceTest.afficheExercice(); //on affiche les phrases à trous et la liste des mots à placer

        //création d'une liste de liste de réponses pour tester notre classe Correction
        ArrayList<ArrayList<String>> reponsesEleve1 = new ArrayList<>();
        ArrayList<String> phrase1 = new ArrayList<>();
        phrase1.add("test");
        phrase1.add("tester");
        ArrayList<String> phrase2 = new ArrayList<>();
        phrase2.add("but");
        phrase2.add("");
        phrase2.add("programme");
        ArrayList<String> phrase3 = new ArrayList<>();
        phrase3.add("marche");
        reponsesEleve1.add(phrase1);
        reponsesEleve1.add(phrase2);
        reponsesEleve1.add(phrase3);

        //test de la classe Correction
        Correction correction1 = new CorrectionExoATrous();
        correction1.corrige(reponsesEleve1, exerciceTest);
        System.out.println(((CorrectionExoATrous) correction1).getListValeursReponses());
    }
}
