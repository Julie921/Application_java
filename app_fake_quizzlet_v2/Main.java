package app_fake_quizzlet_v2;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        //création d'un élève

        Eleve julie = new Eleve("julie28", "blabla", BaremeNiveau.DEBUTANT);

        //création d'un exercice de phrases à trous
        ExoATrous exerciceTest = new ExoATrous("Je suis un #test#.");

        ReponseEleveExoATrous reponseTest = new ReponseEleveExoATrous(exerciceTest, julie); //création d'un objet ReponseEleveExoATrous

        //test de la classe Correction
        Correction correction1 = new CorrectionExoATrous();
        correction1.corrige(reponseTest.getMots(), exerciceTest);


        System.out.println(((CorrectionExoATrous) correction1).getListValeursReponses());

        NotationExoATrous notation1 = new NotationExoATrous();
        float note = notation1.getNote(julie, correction1.getListValeursReponses());

        julie.addEntryHistorique(exerciceTest, note, reponseTest, julie.getBaremeNiveau());

        if (julie.mustChangeNiveau()) {
            julie.updateNiveau(BaremeNiveau.INTERMEDIAIRE);
            System.out.println("Julie doit changer de niveau : " + julie.mustChangeNiveau());
        }
        System.out.println("Le niveau de julie est : " + julie.getBaremeNiveau());

        System.out.println("##############################################################################");

        ExoATrous exercice2 = new ExoATrous("Je suis un #autre# test.");

        ReponseEleveExoATrous reponse2 = new ReponseEleveExoATrous(exercice2, julie); //création d'un objet ReponseEleveExoATrous

        //test de la classe Correction
        Correction correction2 = new CorrectionExoATrous();
        correction2.corrige(reponse2.getMots(), exercice2);


        System.out.println(((CorrectionExoATrous) correction2).getListValeursReponses());

        float note2 = notation1.getNote(julie, correction2.getListValeursReponses());

        julie.addEntryHistorique(exercice2, note2, reponse2, julie.getBaremeNiveau());

        if (julie.mustChangeNiveau()) {
            julie.updateNiveau(BaremeNiveau.INTERMEDIAIRE);
            System.out.println("Julie doit changer de niveau : " + julie.mustChangeNiveau());
        }
        System.out.println("Le niveau de julie est : " + julie.getBaremeNiveau());
    }
}
