package app_fake_quizzlet_v2;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        //création d'un élève

        Eleve julie = new Eleve("julie28", "blabla");

        //création d'un exercice de phrases à trous
        ExoATrous exerciceTest = new ExoATrous("Ceci est un #test# pour #tester#. Ce test a pour #but# de voir si notre #programme# fonctionne #correctement#. Est-ce que ça #marche# ?");
        exerciceTest.afficheExercice(); //on affiche les phrases à trous et la liste des mots à placer

        //création d'une liste de liste de réponses pour tester notre classe Correction
        ArrayList<ArrayList<String>> listeMotsEleves = new ArrayList<>();
        ArrayList<String> phrase1 = new ArrayList<>();
        phrase1.add("test");
        phrase1.add("tester");
        ArrayList<String> phrase2 = new ArrayList<>();
        phrase2.add("but");
        phrase2.add("");
        phrase2.add("programme");
        ArrayList<String> phrase3 = new ArrayList<>();
        phrase3.add("marche");
        listeMotsEleves.add(phrase1);
        listeMotsEleves.add(phrase2);
        listeMotsEleves.add(phrase3);

        ReponseEleveExoATrous reponseTest = new ReponseEleveExoATrous(listeMotsEleves); //création d'un objet ReponseEleveExoATrous

        //test de la classe Correction
        Correction correction1 = new CorrectionExoATrous();
        correction1.corrige(reponseTest.getMots(), exerciceTest);




        System.out.println(((CorrectionExoATrous) correction1).getListValeursReponses());

        NotationExoATrous notation1 = new NotationExoATrous();
        System.out.println("La note obtenue est : " + notation1.getNote(julie, correction1.getListValeursReponses()) + "/20");
    }
}
