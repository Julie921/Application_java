package app_fake_quizzlet_v2;

/**
 * La classe BaremeNiveau permet de créer des objets sous forme de constante.Elles servent à attribuer
 * un niveau à un élève.
 * Il y a l'objet DEBUTANT, INTERMEDIAIRE,AVANCE et EXPERT. A chaque constante est associé
 * une valeur pour les réponses vraies,fausses et NA. Cela permet de construire la notation
 * par la suite.
 */
public enum BaremeNiveau {
    // Déclaration des constantes
    DEBUTANT(1, 0, 0), INTERMEDIAIRE(1, -1, 0), AVANCE(1, -1, -1), EXPERT(1, -2, -1);

    // Déclaration des attributs pour les valeurs à l'interieur de nos constantes.
    private int vrai; //nombre de points attribués par réponse vraie de l'élève
    private int faux; //nombre de points attribués par réponse fausse de l'élève
    private int na; //nombre de points attributs par réponses non répondue de l'élève

    /**
     * Constructeur de la classe
     * @param vrai
     * @param faux
     * @param na
     */
    BaremeNiveau(int vrai, int faux, int na) {
        this.vrai = vrai;
        this.faux = faux;
        this.na = na;
    }

    /** Getter de l'attribut vrai
     * @param
     * @return vrai
     */
    public int getVrai(){
        return vrai;
    }
    /** Getter de l'attribut faux
     * @param
     * @return faux
     */
    public int getFaux(){
        return faux;
    }

    /** Getter de l'attribut na
     * @param
     * @return na
     */
    public int getNa(){
        return na;
    }
}
