package fake_quizlet.utilisateurs;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import fake_quizlet.exercice.Exercice;
import fake_quizlet.exercice.Langue;
import fake_quizlet.bdd.NiveauxEleves;

import java.util.ArrayList;
import java.util.List;

/**
 * La classe Professeur hérite de la classe Utilisateur et représente un professeur dans l'application.
 * Elle a un attribut `langue` qui représente la langue que le professeur enseigne.
 * Il est possible de récupérer la liste de ses élèves en faisant une requête SQL sur la table représentée par la classe {@link NiveauxEleves}.
*/
@DatabaseTable(tableName = "PROFESSEURS")
public class Professeur extends Utilisateur {

    /**
     * DatabaseField indique que cet attribut est mappé sur une colonne de la table "PROFESSEURS" dans la base de données.
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
     * @param pseudo  le pseudo du professeur
     * @param langue la langue enseignée par le professeur
    */
    public Professeur(String pseudo, Langue langue) {
        super(pseudo);
        this.langue = langue;
    }

    /**
     *  Méthode qui retourne une chaîne de caractères représentant le professeur.
     *  @return Une chaîne de caractères contenant le pseudo du professeur et la langue qu'il enseigne.
     */
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

    /**
     * Cette méthode retourne la liste des exercices accessibles pour un professeur donné, en prenant en compte la langue enseignée par le professeur et les exercices disponibles. Si aucun exercice n'est disponible dans la langue enseignée par le professeur, un message est affiché à l'utilisateur.
     * @param listNiveauxUtilisateur la liste des niveaux des utilisateurs (pas utilisé mais vu que c'est une méthode abstraite redéfinie, on est obligé de le mettre)
     * @param listExercices la liste des exercices disponibles
     * @return une liste d'objet {@link Exercice} correspondant à tous les exercices disponibles dans la langue enseignée par le professeur
     */
    @Override
    public ArrayList<Exercice> getExercicesAccessibles(ArrayList<NiveauxEleves> listNiveauxUtilisateur, List<Exercice> listExercices) {
        ArrayList<Exercice> exercicesAccessibles = new ArrayList<>();

        for (Exercice exercice : listExercices) { //pour chaque exercice
            // si l'exercice est de la même langue que celle enseignée par le prof
            if (exercice.getLangue().equals(this.getLangue())) {
                exercicesAccessibles.add(exercice); //on l'ajoute à la liste
            }
        }

        if(exercicesAccessibles.isEmpty()){
            System.out.println("\nIl n'y a encore aucun exercice enregistré en " + this.getLangue() + ".\n");
        }
        return exercicesAccessibles;
    }

    /**
     *  Affiche les résultats des élèves du professeur actif. Si le professeur n'a pas d'élèves, affiche un message indiquant qu'il n'a pas encore d'élèves inscrits dans son cours.
     *  @param listNiveauxUtilisateur la liste des niveaux des élèves dans chaque langue
     */
    @Override
    public void afficheResultats(ArrayList<NiveauxEleves> listNiveauxUtilisateur) {
        // On vérifie s'il y a des élèves inscrits dans la langue enseignée par le professeur
        boolean aDesEleves = false;
        for (NiveauxEleves niv : listNiveauxUtilisateur) {
            if (niv.getPseudoProfesseur().equals(this.getPseudo())) { // le pseudo du prof à cette ligne correspond à ce prof là
                aDesEleves = true; // donc le prof a des élèves
                break;
            }
        }
        // Si le professeur a des élèves, on affiche leurs résultats
        if (aDesEleves) {
            System.out.println("Résultats de vos élèves :");
            System.out.println("+-------------+---------------+--------+");
            System.out.println("|   Elève     |  Niveau       | Score  |");
            System.out.println("+-------------+---------------+--------+");
            for (NiveauxEleves niv : listNiveauxUtilisateur) {
                if(niv.getPseudoProfesseur().equals(this.getPseudo())){
                    String eleve = String.format("| %-13s ", niv.getPseudoEleve());
                    String niveau = String.format("| %-15s ", niv.getNiveau());
                    String score = String.format("| %-8s ", niv.getScore());
                    System.out.println(eleve + niveau + score);
                }
            }
            System.out.println("+-------------+---------------+--------+\n");
        } else { // le prof n'a pas d'élèves
            System.out.println("Vous n'avez pas encore d'élèves inscrits dans votre cours.\n");
        }
    }
}
