package app_fake_quizzlet_v2;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        Boolean professeurSession = false, studentSession = false;

        Scanner scannerInputUser = new Scanner(System.in); //pour récupérer les réponses de l'utilisateur
        String inputUser = "";

        do {
            System.out.println("Bonjour ! Vous êtes un : \n - 1 : élève\n - 2 : professeur");
            System.out.print("Votre réponse: ");
            inputUser = scannerInputUser.nextLine();

            // Choix Session
            studentSession = (inputUser.equals("1"));
            professeurSession = (inputUser.equals("2"));
            if (!studentSession && !professeurSession) {
                System.out.println("Votre réponse ne convient pas");
            }
        } while (!inputUser.equals("quit") && !studentSession && !professeurSession);

        System.out.println("---");
        if (professeurSession) {
            Boolean choixActionProf1 = false, choixActionProf2 = false, choixActionProf3 = false;
            do {
                System.out.println("Que voulez-vous faire ?");
                System.out.println("" +
                        "1 : Ecrire un exercice\n" +
                        "2 : Modifier un exercice\n" +
                        "3 : Voir notes des élèves");
                System.out.print("Votre réponse: ");
                inputUser = scannerInputUser.nextLine();

                // Choix Professeur
                choixActionProf1 = inputUser.equals("1");
                choixActionProf2 = inputUser.equals("2");
                choixActionProf3 = inputUser.equals("3");
                if (!choixActionProf1 && !choixActionProf2 && !choixActionProf3) {
                    System.out.println("Votre réponse ne convient pas\n----");
                }

            } while (!inputUser.equals("quit") && !choixActionProf1 && !choixActionProf2 && !choixActionProf3);


        } else if (studentSession) {
            Boolean choixEleve1 = false, choixEleve2 = false;
            do {
                System.out.println("Que voulez-vous faire ?");
                System.out.println("" +
                        "1 : Choisir un exercice\n" +
                        "2 : Voir mon historique");

                System.out.print("Votre réponse: ");
                inputUser = scannerInputUser.nextLine();

                // Choix Eleve
                choixEleve1 = inputUser.equals("1");
                choixEleve2 = inputUser.equals("2");
                if (!choixEleve1 && !choixEleve2) {
                    System.out.println("Votre réponse ne convient pas\n----");
                }

            } while (!inputUser.equals("quit") && !choixEleve1 && !choixEleve2);
        }


        ParseurPhraseATrous parseurPhraseATrous = new ParseurPhraseATrous();
        ParseurTerminaison parseurTerminaison = new ParseurTerminaison();
        ExoATrous exercice1 = new ExoATrous(parseurPhraseATrous, "En été je porte tous les jours un t-shirt en #coton#, un short et des sandales. Tiens, je vais te donner un sac en #plastique# pour mettre tout ça. Mémé m'a acheté un magnifique pull en #laine# en Irlande. Je voudrais m'acheter une veste en #cuir# mais je n'ai pas assez d'argent. ");
        exercice1.afficheExercice();

        // Essai
        /*// phrase1 = new PhraseATrous("Un chat #blanc#", parseurPhraseATrous);

        PhraseATrous phrase2 = parseurTerminaison.parse("Je veux mang--er");

        System.out.println(phrase1.toString());
        System.out.println(phrase2.toString());


        Professeur bernard = new Professeur("Bernard");
        Eleve barnard = new Eleve("Barnard", BaremeNiveau.DEBUTANT, bernard);
        Eleve lhermite = new Eleve("L'Hermite", BaremeNiveau.INTERMEDIAIRE, bernard);

        bernard.listElevesToString();

        *//**
         * To create an object of Scanner class, we usually pass the predefined object System.in, which represents the standard input stream. We may pass an object of class File if we want to read input from a file.
         *//*

        Scanner scannerInputUser = new Scanner(System.in); //pour récupérer les réponses de l'utilisateur

        System.out.println("Bonjour ! Vous êtes un : \n - élève : E\n - professeur : P");
        String inputUser = scannerInputUser.nextLine();
        if(inputUser.equals("e")){
            System.out.println("Bonjour élève");
        } else if (inputUser.equals("p")) {
            System.out.println("Bonjour prof");
        }
        else{
            System.out.println("Pas une option");
        }

        FileInputStream file = new FileInputStream("C:\\Users\\aengp\\Desktop\\db\\login.txt");
        Scanner scannerFile = new Scanner(file);*/




      /*  //création d'un élève

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
        System.out.println("Le niveau de julie est : " + julie.getBaremeNiveau());*/
    }

    public void scenarioEleve(){

    }
}
