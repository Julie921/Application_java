package app_fake_quizzlet_v2;

import java.lang.reflect.Array;
import java.util.ArrayList;

public abstract class ReponseEleve {

    Exercice exercice; // l'exercice que l'élève fait
    Eleve eleve; // l'élève qui fait l'exercice
    ArrayList<ArrayList<String>> reponses = new ArrayList<>();

    ReponseEleve(Exercice exercice, Eleve eleve){
        this.exercice = exercice;
        this.eleve = eleve;
    }

    public Exercice getExercice(){
        return this.exercice;
    }

    public  Eleve getEleve(){
        return this.eleve;
    }

    public ArrayList<ArrayList<String>> getReponses(){
        return this.reponses;
    }

}
