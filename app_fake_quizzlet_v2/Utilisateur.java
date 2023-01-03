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

    public abstract ArrayList<Exercice> getExercicesAccessibles(ArrayList<NiveauxEleves> listNiveauxUtilisateur, List<Exercice> listExercices);

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
