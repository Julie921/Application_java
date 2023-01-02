package app_fake_quizzlet_v2;

import com.j256.ormlite.field.DatabaseField;

public abstract class Utilisateur {

    /* Le pseudo de l'utilisateur*/
    @DatabaseField(id = true)
    private String pseudo;

    /**
     * Constructeur vide de la classe Utilisateur nécessaire pour utiliser ORMlite lors de la création des classes concrètes qui étendent Utilisateur.
     * @see Eleve
     * @see Professeur
     */
    public Utilisateur(){}

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
