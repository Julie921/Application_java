package app_fake_quizzlet_v2;

import com.j256.ormlite.field.DatabaseField;

public class NiveauxEleves {

    public static final String ELEVE_ID = "pseudo_eleve";
    public static final String LANGUE_COL = "langue";
    public static final String PROF_COL = "pseudo_professeur";

    @DatabaseField(generatedId = true)
    private int index;

    @DatabaseField(foreign = true, columnName = ELEVE_ID)
    private Eleve eleve;

    @DatabaseField(columnName = LANGUE_COL)
    private Langue langue;

    @DatabaseField
    private BaremeNiveau niveau;

    @DatabaseField(foreign = true, columnName = PROF_COL)
    private Professeur professeur;

    @DatabaseField
    private float score;

    public NiveauxEleves() {

    }

    public NiveauxEleves(Eleve eleve, Professeur professeur) {
        this.eleve = eleve;
        this.professeur = professeur;
        this.langue = professeur.getLangue();
        this.niveau = eleve.getBaremeNiveau(this.langue);
        this.score = 0;
    }

    public BaremeNiveau getNiveau() {
        updateNiveau();
        return this.niveau;
    }

    public void setNiveau(BaremeNiveau b) {
        this.niveau = b;
    }

    public void updateNiveau() {
        this.niveau = this.eleve.getBaremeNiveau(this.langue);
    }

    public Eleve getEleve() {
        return eleve;
    }

    public void setEleve(Eleve eleve) {
        this.eleve = eleve;
    }

    public Professeur getProfesseur() {
        return professeur;
    }

    public void setProfesseur(Professeur professeur) {
        this.professeur = professeur;
    }

    public float getScore(){
        return this.score;
    }

    public void addScore(float gain){
        this.score += gain;
    }

}
