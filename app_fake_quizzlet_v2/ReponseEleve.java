package app_fake_quizzlet_v2;

import java.util.ArrayList;

public abstract class ReponseEleve implements Correction, Notation {

    Exercice exercice; // l'exercice que l'élève fait
    Eleve eleve; // l'élève qui fait l'exercice
    ArrayList<ArrayList<String>> reponsesFournies = new ArrayList<>();

    private ArrayList<ArrayList<ValeurReponse>> reponsesCorrection = new ArrayList<>();

    protected Float noteDonnee = 0.0F;

    protected Float seuilPassation = 0.0F;

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

    public ArrayList<ArrayList<String>> getReponsesFournies(){
        return this.reponsesFournies;
    }


    public ArrayList<ArrayList<ValeurReponse>> getReponsesCorrection() {
        return reponsesCorrection;
    }

    public void setReponsesCorrection(ArrayList<ArrayList<ValeurReponse>> reponsesCorrection) {
        this.reponsesCorrection = reponsesCorrection;
    }

    public Float getNoteDonnee() {
        return noteDonnee;
    }

    public void setNoteDonnee(Float noteDonnee) {
        this.noteDonnee = noteDonnee;
    }

    public Boolean estBon() {
        return this.noteDonnee >= this.seuilPassation;
    }

    public abstract void setSeuilPassation();
}
