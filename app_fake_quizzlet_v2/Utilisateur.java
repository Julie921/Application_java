package app_fake_quizzlet_v2;

import com.j256.ormlite.field.DatabaseField;

import java.util.ArrayList;
import java.util.List;

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

    /**
     *  Cette méthode retourne la liste des exercices accessibles par l'utilisateur actif.
     *  Si l'utilisateur est un élève, la liste contient les exercices de sa langue et de son niveau.
     *  Si l'utilisateur est un professeur, la liste contient les exercices pour la langue qu'il enseigne.
     *  @param listNiveauxUtilisateur la liste des niveaux des utilisateurs
     *  @param listExercices la liste des exercices disponibles
     *  @return la liste des exercices accessibles par l'utilisateur actif
     */
    public abstract ArrayList<Exercice> getExercicesAccessibles(ArrayList<NiveauxEleves> listNiveauxUtilisateur, List<Exercice> listExercices);

    /**
     * Affiche la liste des exercices accessibles pour l'utilisateur sous la forme d'une preview (3 première phrases) de chaque exercice.
     * @see Utilisateur#getExercicesAccessibles(ArrayList, List) 
     *
     * @param exercicesAccessibles la liste des exercices accessibles
     */
    public void afficheExercicesAccessibles(ArrayList<Exercice> exercicesAccessibles){
        int i = 1;
        for (Exercice exercice : exercicesAccessibles) {
            System.out.println("\n*********** EXERCICE " + i + " *********** ");
            exercice.previewText();
            i++;
        }
    }

    /**
     *  Affiche les résultats de l'utilisateur spécifié en paramètre. Si l'utilisateur est un {@link Professeur}, ses résultats sont les résultats de tous ses élèves. Si c'est un {@link Eleve}, ses résultats sont ses résultats dans tous les cours auxquels il est inscrit, avec le nom du professeur enseignant chaque cours.
     *  Si l'utilisateur n'a pas d'élèves ou n'est pas inscrit dans un cours, un message d'information est affiché à cet effet.
     */
    public abstract void afficheResultats(ArrayList<NiveauxEleves> listNiveauxUtilisateur);

}
