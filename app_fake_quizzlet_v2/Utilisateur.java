package app_fake_quizzlet_v2;

public abstract class Utilisateur {

    /* Le pseudo de l'utilisateur*/
    private String pseudo;

    /**
     * Constructeur de la classe Utilisateur.
     * @param pseudo le pseudo de l'utilisateur.
     */
    public Utilisateur(String pseudo){
        this.pseudo = pseudo;
    }

    /**
     * Retourne le pseudo de l'utilisateur.
     * @return le pseudo de l'utilisateur.
     */
    public String getPseudo(){
        return this.pseudo;
    }

}
