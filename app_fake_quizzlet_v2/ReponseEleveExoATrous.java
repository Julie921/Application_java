package app_fake_quizzlet_v2;

import java.util.ArrayList;
import java.util.Scanner;

public class ReponseEleveExoATrous extends ReponseEleve {

    float note;

    ReponseEleveExoATrous(ExoATrous exercice, Eleve eleve){

        super(exercice, eleve);

        System.out.println("Bonjour " + eleve.getPseudo() + "!\n\n");

        exercice.afficheExercice();

        Scanner myScanner = new Scanner(System.in); // scanner pour récupérer les réponses de l'élève

        ArrayList<String> listeTampon = new ArrayList<>(); //on crée une liste tampon qu'on videra après chaque phrase

        int i = 1;
        for (PhraseATrous phrase : exercice.getListPhrases()) { //pour chaque phrase de l'exercice
            listeTampon.clear(); // on vide la liste tampon
            for (String mot : phrase.getMotsAPlacer()) { //pour chaque mot à placer dans la phrase
                System.out.println("Quel est le mot manquant " + i + "?"); //on demande le mot manquant à la place X
                String motDonne = myScanner.nextLine();
                listeTampon.add(motDonne); //on ajoute notre mot à la liste tampon
                i += 1;
            }
            this.reponses.add(new ArrayList<String>(listeTampon));
        }

    }

    public void setNote(){
        this.note = note;
    }

}
