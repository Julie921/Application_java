package app_fake_quizzlet_v2;

public abstract class Utilisateur {

    private String pseudo;
    private String password;

    /**
     * Constructeur de la classe abstraite Utilisateur.
     * Il prend en argument le
     * @param pseudo
     * @param password
     */
    Utilisateur(String pseudo, String password){
        this.pseudo = pseudo;
        this.password = password;
    }

    /**
     * Méthode pour changer le mot de passe d'un utilisateur.
     * Par exemple, si "marie2000" veut changer son mot de passe en "blibli", il faut faire :
     * marie.changePassword("blibli");
     * @param newPassword Le nouveau mot de passe qui remplacera l'ancien
     */
    void changePassword(String newPassword){
        this.password = newPassword;
    }

    /**
     * Méthode pour changer le pseudo d'un utilisateur.
     * Par exemple, si "marie2000" veut se renommer en "marie2001", il faut faire :
     * marie.changePseudo("marie2001");
     * @param newPseudo (String) Le nouveau pseudo qui remplacera l'ancien
     */
    void changePseudo(String newPseudo){
        this.pseudo = newPseudo;
    }

    /**
     * Méthode pour récupérer le pseudo d'un utilisateur.
     * Par exemple, si je veux récupérer le pseudo de l'utilisateur stocké à l'adresse "marie", il faut faire :
     * marie.getPseudo();
     * @return Le pseudo de l'utilisateur
     */
    public String getPseudo(){
        return this.pseudo;
    }

    /**
     * Méthode pour récupérer le mot de passe d'un utilisateur.
     * Par exemple, si je veux récupérer le mot de passe de l'utilisateur stocké à l'adresse "marie", il faut faire :
     * marie.getPassword();
     * @return Le mot de passe de l'utilisateur
     */
    public String getPassword(){
        return this.password;
    }

}
