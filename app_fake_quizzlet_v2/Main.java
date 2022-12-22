package app_fake_quizzlet_v2;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        //création d'un élève

        Eleve julie = new Eleve("julie28", "blabla");

        //création d'un exercice de phrases à trous
        ExoATrous exerciceTest = new ExoATrous("Ceci est un #test# pour #tester#. Ce test a pour #but# de voir si notre #programme# fonctionne #correctement#. Est-ce que ça #marche# ?");

        ReponseEleveExoATrous reponseTest = new ReponseEleveExoATrous(exerciceTest, julie); //création d'un objet ReponseEleveExoATrous

        //test de la classe Correction
        Correction correction1 = new CorrectionExoATrous();
        correction1.corrige(reponseTest.getMots(), exerciceTest);


        System.out.println(((CorrectionExoATrous) correction1).getListValeursReponses());

        NotationExoATrous notation1 = new NotationExoATrous();
        float note = notation1.getNote(julie, correction1.getListValeursReponses());

        julie.addEntryHistorique(exerciceTest, note, reponseTest);
        julie.afficheHistorique();
    }
}
