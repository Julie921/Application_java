package app_fake_quizzlet_v2;

import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.field.DatabaseField;

public class NiveauxEleves {

    public static final String ELEVE_ID = "pseudo_eleve";
    public static final String LANGUE_COL = "langue";
    public static final String PROF_COL = "pseudo_professeur";

    @DatabaseField(generatedId = true)
    private int index;

    @DatabaseField(columnName = ELEVE_ID)
    private String eleve;

    @DatabaseField(columnName = LANGUE_COL)
    private Langue langue;

    @DatabaseField
    private BaremeNiveau niveau;

    @DatabaseField(columnName = PROF_COL)
    private String professeur;

    @DatabaseField
    private float score;

    public NiveauxEleves() {

    }

    public NiveauxEleves(Eleve eleve, Professeur professeur) {
        this.eleve = eleve.getPseudo();
        this.professeur = professeur.getPseudo();
        this.langue = professeur.getLangue();
        this.niveau = eleve.getBaremeNiveau(this.langue);
        this.setScore(0);
    }

    public BaremeNiveau getNiveau() {
        updateNiveau();
        return this.niveau;
    }

    public void setNiveau(BaremeNiveau b) {
        this.niveau = b;
    }

    public void updateNiveau() {
        //this.niveau = this.eleve.getBaremeNiveau(this.langue);
    }

    public String getEleve() {
        return eleve;
    }

    public void setEleve(Eleve eleve) {
        this.eleve = eleve.getPseudo();
    }

    public String getProfesseur() {
        return professeur;
    }

    public void setProfesseur(Professeur professeur) {
        this.professeur = professeur.getPseudo();
    }

    public float getScore(){
        return this.score;
    }

    public Langue getLangue() {
        return langue;
    }

    public void setLangue(Langue langue) {
        this.langue = langue;
    }

    public void setScore(float score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "NiveauxEleves{" +
                "index=" + index +
                ", eleve=" + eleve +
                ", langue=" + langue +
                ", niveau=" + niveau +
                ", professeur=" + professeur +
                ", score=" + score +
                '}';
    }
}
