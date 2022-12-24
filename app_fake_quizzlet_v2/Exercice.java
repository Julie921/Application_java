package app_fake_quizzlet_v2;

/**
 * Classe abstraite exercice contenant deux méthodes :
 * -afficheExercice()
 * -corrigeExercice()
 * Ces deux méthodes devront être instrancié dans les classes qui étendent la classe Exercice
 */
public abstract class Exercice {

    private int id;

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

}
