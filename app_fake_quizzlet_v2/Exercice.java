package app_fake_quizzlet_v2;

/**
 * Classe abstraite représentant un exercice. Un exercice peut être un texte à trous, un QCM ou une traduction, par exemple.
 *
 * Cette classe contient deux méthodes abstraites :
 * - afficheExercice() : permet d'afficher l'exercice à l'élève pour qu'il puisse le faire ou non
 *
 * Ces deux méthodes devront être instanciées dans les classes qui étendent la classe Exercice.
 *
 * La classe {@link ExoATrous} étend la classe Exercice.
 */
public abstract class Exercice {

    /** Langue de l'exercice */
    private Langue langue;

    /** Niveau de l'exercice :
     * - DEBUTANT
     * - INTERMEDIAIRE
     * - AVANCE
     * - EXPERT
     * */
    private BaremeNiveau niveau;

    /**
     * Pourcentage de points qu'un élève doit obtenir en faisant l'exercice pour que l'exercice soit considéré comme réussi.
     */
    private Float pourcentage;

    /**
     * Constructeur de la classe Exercice.
     *
     * @param langue langue de l'exercice
     * @param niveau niveau de l'exercice
     * @param pourcentage pourcentage de points qu'un élève doit obtenir en faisant l'exercice pour que l'exercice soit considéré comme réussi.
     */
    public Exercice(Langue langue, BaremeNiveau niveau, Float pourcentage){
        this.langue = langue;
        this.niveau = niveau;
        this.pourcentage = pourcentage;
    }

    /**
     * Méthode afficheExercice() permet d'afficher l'exercice à l'élève pour qu'il puisse le faire ou non.
     */
    public abstract void afficheExercice();

    /**
     * Getter permettant de récupérer la langue de l'exercice.
     *
     * Liste des langues possibles à ce jour :
     * TODO: mettre la liste des langues
     *
     * @return langue de l'exercice
     */
    public Langue getLangue(){
        return this.langue;
    }

    /**
     * Getter permettant de récupérer le niveau de l'exercice :
     *
     * Liste des niveaux possibles :
     * - DEBUTANT
     * - INTERMEDIAIRE
     * - AVANCE
     * - EXPERT
     *
     * @return niveau de l'exercice
     */
    public BaremeNiveau getNiveau() {
        return niveau;
    }

    /**
     * Getter permettant de récupérer le pourcentage de points qu'un élève doit obtenir en faisant l'exercice pour que l'exercice soit
     * considéré comme réussi.
     *
     * @return pourcentage de points qu'un élève doit obtenir en faisant l'exercice pour que l'exercice soit considéré comme réussi.
     */
    public Float getPourcentage() {
        return pourcentage;
    }

    /**
     * Setter permettant de modifier le pourcentage de points qu'un élève doit obtenir en faisant l'exercice pour que l'exercice soit
     * considéré comme réussi.
    */
    public void setPourcentage(Float pourcentage) {
        this.pourcentage = pourcentage;
    }
}
