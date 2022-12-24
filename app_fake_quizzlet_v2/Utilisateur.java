package app_fake_quizzlet_v2;

import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.field.DatabaseField;

@DatabaseTable(tableName = "utilisateur")
public abstract class Utilisateur {

    @DatabaseField(id = true)
    private String pseudo;

    /**
     * Constructeur de la classe abstraite Utilisateur.
     * Il prend en argument le
     * @param pseudo
     */
    Utilisateur(String pseudo){
        this.pseudo = pseudo;
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

}
