package app_fake_quizzlet_v2;

import java.util.ArrayList;
import java.util.Scanner;

public class ReponseEleveExoATrous extends ReponseEleve{

    ArrayList<ArrayList<String>> mots = new ArrayList<>();
    int note;

    ReponseEleveExoATrous(ExoATrous exercice, Eleve eleve){

        System.out.println("Bonjour " + eleve.getPseudo());
        exercice.afficheExercice();

        int i = 1;
        for (Phrase phrase : exercice.getListPhrases()) { //pour chaque phrase de l'exercice
            ArrayList<String> listeTampon = new ArrayList<>(); //on crée une liste tampon
            for (String mot : phrase.getMotsAPlacer()) { //pour chaque mot à placer dans la phrase
                System.out.println("Quel est le mot manquant " + i + "?"); //on demande le mot manquant à la place X
                Scanner myObj = new Scanner(System.in);
                String motDonne = myObj.nextLine();
                listeTampon.add(motDonne); //on ajoute notre mot à la liste tampon
                i += 1;
            }
            this.mots.add(listeTampon);
        }

    }

    public ArrayList<ArrayList<String>> getMots(){
        return this.mots;
    }

    @Override
    public void remplirReponse() {
        /*exercice.affichePhraseAvecTrous(); //changer en getter
        exercice.afficheMotsAPlacer(); //changer en getter

        for (int i = 0; i < exercice.mots_a_placer.size(); i++) {
            System.out.println("Quel est le mot manquant " + (i + 1) + "?");
            Scanner myObj = new Scanner(System.in);
            String motDonne = myObj.nextLine();
            reponse_eleve.add(motDonne);
        }

        historique.append(reponse_eleve, exercice, pseudo); //pour remplir l'historique au fur et à mesure

        return reponse_eleve;*/
    }
}
