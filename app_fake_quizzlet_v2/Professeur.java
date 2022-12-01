package app_fake_quizzlet_v2;

public class Professeur extends Utilisateur {

    /**
     * Constructeur pour la classe Professeur. Elle permet d'instancier un objet Professeur en renseignant le nom d'utilisateur (pseudo) et le mot de passe (password).
     * Par exemple, si Marie veut créer son compte en utilisant le pseudo "marie2000" et le mot de passe "blabla", on va faire :
     * Professeur marie = new Professeur("marie2000", "blabla");
     * @param pseudo (String) Le nom d'utilisateur
     * @param password (String) Le mot de passe
     */
    Professeur(String pseudo, String password) {
        super(pseudo, password);
    }


}
