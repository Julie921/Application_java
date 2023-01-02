package app_fake_quizzlet_v2;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * La classe Professeur hérite de la classe Utilisateur et représente un professeur dans l'application.
 * Elle a un attribut `langue` qui représente la langue que le professeur enseigne.
 * Il est possible de récupérer la liste de ses élèves en faisant une requête SQL sur la table représentée par la classe {@link NiveauxEleves}.
*/
@DatabaseTable(tableName = "PROFESSEURS")
public class Professeur extends Utilisateur {

    /**
     * @DatabaseField indique que cet attribut est mappé sur une colonne de la table "PROFESSEURS" dans la base de données.
     */
    @DatabaseField
    private Langue langue;

    /**
     * Constructeur vide pour la classe Professeur. Il est nécessaire pour que ORMLite puisse construire des instances de cette classe.
     */
    public Professeur() {
        super();
    }

    /**
     * Constructeur pour la classe Professeur.
     * Il permet d'instancier un objet Professeur en renseignant le nom d'utilisateur (pseudo) et la langue enseignée par ce professeur.
    */
    public Professeur(String pseudo, Langue langue) {
        super(pseudo);
        this.langue = langue;
    }

    //TODO : je crois que ces métodes ne servent plus à rien maintenant qu'on a la BDD
    /* PARTIE ELEVE */
    /*public ForeignCollection<Eleve> getListEleves() {
        return this.listEleves;
    }*/

    /*public void listElevesToString() {
        for (Eleve eleve : listEleves) {
            System.out.println(eleve);
        }
    }
*/
 /*   public void ajouterEleve(Eleve eleve) {
        this.listEleves.add(eleve);
    }*/


    /* PARTIE GENERALE */
    @Override
    public String toString() {
        return "Professeur { "+ this.getPseudo()+
                " - Lang: " + this.getLangue().toString() +
                " }";
    }

    /**
     * Méthode qui permet de récupérer la langue enseignée par un professeur.
     * @return (Langue) la langue enseignée par le professeur
     */
    public Langue getLangue() {
        return langue;
    }

    /**
     *  Méthode qui permet de définir la langue enseignée par le professeur.
     *  @param langue la langue enseignée par le professeur
     */
    public void setLangue(Langue langue) {
        this.langue = langue;
    }
}
