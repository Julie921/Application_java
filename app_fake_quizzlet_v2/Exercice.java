package app_fake_quizzlet_v2;

/**
 * Classe abstraite exercice contenant deux méthodes :
 * -afficheExercice()
 * -corrigeExercice()
 * Ces deux méthodes devront être instrancié dans les classes qui étendent la classe Exercice
 */
public abstract class Exercice {

    private int id;
    private Langue langue;
    private BaremeNiveau niveau;

    private Float pourcentage;

    Exercice(Langue langue, BaremeNiveau niveau, Float pourcentage){
        this.langue = langue;
        this.niveau = niveau;
        this.pourcentage = pourcentage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * Méthode afficheExercice() permet d'afficher l'exercice à l'élève pour qu'il puisse le faire ou non.
     */
    public abstract void afficheExercice();

    public Langue getLangue(){
        return this.langue;
    }

    public BaremeNiveau getNiveau() {
        return niveau;
    }

    public Float getPourcentage() {
        return pourcentage;
    }

    public void setPourcentage(Float pourcentage) {
        this.pourcentage = pourcentage;
    }
}
