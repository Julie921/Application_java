package app_fake_quizzlet_v2;

import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.field.DatabaseField;

import java.util.ArrayList;
import java.util.HashMap;

@DatabaseTable(tableName = "PROFESSEURS")
public class Professeur extends Utilisateur {

    @DatabaseField(canBeNull = true)
    private ArrayList<Eleve> listEleves = new ArrayList<Eleve>();

    @DatabaseField(canBeNull = true)
    private HashMap<Integer, Exercice> listExercices = new HashMap<>();

    /**
     * Constructeur pour la classe Professeur. Elle permet d'instancier un objet Professeur en renseignant le nom d'utilisateur (pseudo) et le mot de passe (password).
     * Par exemple, si Marie veut créer son compte en utilisant le pseudo "marie2000" et le mot de passe "blabla", on va faire :
     * Professeur marie = new Professeur("marie2000", "blabla");
     * @param pseudo (String) Le nom d'utilisateur
     */
    Professeur(String pseudo) {
        super(pseudo);
    }

    /* PARTIE ELEVE */
    public ArrayList<Eleve> getListEleves() {
        return this.listEleves;
    }

    public void listElevesToString() {
        for (Eleve eleve : listEleves) {
            System.out.println(eleve);
        }
    }

    public void ajouterEleve(Eleve eleve) {
        this.listEleves.add(eleve);
    }

    /* PARTIE EXERCICES */
    public HashMap<Integer, Exercice> getListExercices() {
        return this.listExercices;
    }

    /* PARTIE GENERALE */
    @Override
    public String toString() {
        return "Professeur { "+ this.getPseudo()+" }";
    }
}
