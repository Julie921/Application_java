package fake_quizlet.bdd;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import fake_quizlet.exercice.Exercice;
import fake_quizlet.exercice.Langue;
import fake_quizlet.notation.BaremeNiveau;
import fake_quizlet.notation.Notation;
import fake_quizlet.reponse.ReponseEleve;
import fake_quizlet.utilisateurs.Eleve;
import fake_quizlet.utilisateurs.Professeur;

import java.sql.SQLException;

/**
 *  La classe NiveauxEleves représente le niveau de chaque élève dans les langues qu'il étudie.
 *  Elle est utilisée pour enregistrer et mettre à jour le score de l'élève dans cette langue.
 *  Elle permet également de lier l'élève à son professeur pour chaque langue étudiée.
 *
 *  Il est important de comprendre que chaque méthode est appelée sur un objet de classe NiveauxEleves,
 *  et non sur un objet de classe Eleve. C'est pour cela que l'on a pas besoin de fournir en argument la langue ou le pseudo de l'élève.
 *  Il faut voir ça comme une table où chaque ligne possède un identifiant unique (la clé primaire).
 *  Un même élève peut être sur plusieurs lignes car il étudie plusieurs langues.
 *
 *  Par exemple, si Marie étudie le français avec Jean et l'allemand avec Claude, on pourrait avoir dans notre table les deux lignes suivantes :
 *  1 -- Marie -- français -- Jean -- DEBUTANT -- 10
 *  2 -- Marie -- allemand -- Claude -- AVANCE -- 41
 *
 *  Au départ, ces informations étaient stockées sous la forme d'une HashMap dans les attributs de chaque {@code Eleve},
 *  mais ORMlite ne permet pas de stocker des HashMap comme ça.
 */
public class NiveauxEleves {

    /**
     * Constante de classe utilisée pour nommer la colonne "pseudo_eleve" dans la base de données.
     */
    public static final String ELEVE_ID = "pseudo_eleve";

    /**
     * Constante de classe utilisée pour nommer la colonne "langue" dans la base de données.
     */
    public static final String LANGUE_COL = "langue";

    /**
     * Constante de classe utilisée pour nommer la colonne "pseudo_professeur" dans la base de données.
     */
    public static final String PROF_COL = "pseudo_professeur";

    /**
     * Clé primaire générée automatiquement utilisée comme index dans la base de données.
     */
    @DatabaseField(generatedId = true)
    private int index;


    /**
     * Le pseudo de l'élève. Stocke la valeur de la colonne "pseudo_eleve" dans la base de données.
     */
    @DatabaseField(columnName = ELEVE_ID)
    private String pseudoEleve;

    /**
     * La langue enseignée par le professeur. Stocke la valeur de la colonne "langue" dans la base de données.
     */
    @DatabaseField(columnName = LANGUE_COL)
    private Langue langue;


    /**
     * Le niveau de l'élève dans la langue étudiée.
     * Les niveaux posibles sont : DEBUTANT, INTERMEDIAIRE, AVANCE, EXPERT
     */
    @DatabaseField
    private BaremeNiveau niveau;

    /**
     * Le pseudo du professeur en charge de l'enseignement de l'élève dans cette langue.
     * Stocke la valeur de la colonne "pseudo_professeur" dans la base de données.
     */
    @DatabaseField(columnName = PROF_COL)
    private String pseudoProfesseur;

    /**
     * Le score de l'élève dans cette langue.
     */
    @DatabaseField
    private float score;

    /**
     * Constructeur vide de la classe NiveauxEleves qui est nécessaire pour utiliser ORMlite
     */
    public NiveauxEleves() {

    }

    /**
     * Constructeur de la classe NiveauxEleves. Initialise les attributs eleve, professeur, langue et niveau en fonction de l'objet Eleve et Professeur passés en paramètres. Le score est initialisé à 0 et le niveau à DEBUTANT ({@link Eleve#ajouterProf(Professeur prof) ajouterProf})
     *
     * @param eleve l'objet Eleve pour lequel on crée un niveau dans une langue
     * @param professeur l'objet Professeur en charge de l'enseignement de l'élève dans cette langue
     */
    public NiveauxEleves(Eleve eleve, Professeur professeur) {
        this.pseudoEleve = eleve.getPseudo();
        this.pseudoProfesseur = professeur.getPseudo();
        this.langue = professeur.getLangue();
        this.niveau = eleve.getBaremeNiveau(this.langue);
        this.setScore(0);
    }

    /**
     * Retourne le niveau de l'élève dans la langue enseignée par le professeur après l'avoir mis à jour.
     *
     * @return le niveau de l'élève
     */
    public BaremeNiveau getNiveau() {
        updateNiveau();
        return this.niveau;
    }

    /**
     * Modifie le niveau de l'élève dans la langue enseignée par le professeur.
     *
     * @param newNiveau le nouveau niveau de l'élève
     */
    public void setNiveau(BaremeNiveau newNiveau) {
        this.niveau = newNiveau;
    }

    /**
     * Met à jour le niveau de l'élève en fonction de sa performance dans la langue enseignée par le professeur.
     */
    public void updateNiveau() {
        //this.niveau = this.eleve.getBaremeNiveau(this.langue);
    }

    /**
     * Retourne le pseudo de l'élève.
     *
     * @return le pseudo de l'élève
     */
    public String getPseudoEleve() {
        return pseudoEleve;
    }

    /**
     * Modifie l'élève.
     *
     * @param eleve
     */
    public void setEleve(Eleve eleve) {
        this.pseudoEleve = eleve.getPseudo();
    }

    /**
     * Retourne le pseudo du professeur en charge de l'enseignement de l'élève dans cette langue.
     *
     * @return le pseudo du professeur
     */
    public String getPseudoProfesseur() {
        return pseudoProfesseur;
    }

    /**
     * Modifie le professeur en charge de l'enseignement de l'élève dans cette langue.
     *
     * @param professeur le nouvel objet Professeur en charge de l'enseignement de l'élève
     */
    public void setProfesseur(Professeur professeur) {
        this.pseudoProfesseur = professeur.getPseudo();
    }

    /**
     * Retourne le score de l'élève dans cette langue.
     *
     * @return le score de l'élève
     */
    public float getScore(){
        return this.score;
    }

    /**
     * Retourne la langue de la ligne.
     *
     * @return la langue
     */
    public Langue getLangue() {
        return langue;
    }

    /**
     * Modifie la langue de la ligne unique
     *
     * @param langue la nouvelle langue
     */
    public void setLangue(Langue langue) {
        this.langue = langue;
    }

    /**
     * Modifie le score de l'élève dans la langue.
     *
     * @param score le nouveau score de l'élève
     */
    public void setScore(float score) {
        this.score = score;
    }

    /**
     *  Représentation textuelle de la classe NiveauxEleves.
     *  Cette méthode est appelée automatiquement lorsque l'on utilise la fonction {@code print} sur un objet de type NiveauxEleves.
     *  Elle permet d'afficher de manière lisible les informations contenues dans l'objet.
     *  @return une chaîne de caractères contenant les informations de l'objet NiveauxEleves.
     */
    @Override
    public String toString() {
        return "NiveauxEleves{" +
                "\n index=" + index +
                "\n pseudoEleve=" + pseudoEleve +
        "\n langue=" + langue +
                "\n niveau=" + niveau +
                "\n pseudoProfesseur=" + pseudoProfesseur +
        "\n score=" + score +
                "\n}";
    }

    /**
     * Cette méthode met à jour le niveau de l'utilisateur actif (un élève) dans une langue donnée, en fonction de son score dans cette langue.
     * Si le score est inférieur à 20, le niveaue st défini comme "débutant"
     * Si le score est compris entre 20 et 40, le niveau est défini comme "intermédiaire".
     * Si le score est compris entre 40 et 60, le niveau est défini comme "avancé".
     * Si le score est supérieur à 60, le niveau est défini comme "expert".
     * La régression d'un niveau supérieur à un niveau inférieur est possible.
     * @param lang la langue pour laquelle mettre à jour le niveau de l'utilisateur actif
     * @param niveauElevesDao l'objet Dao pour accéder à la table des enregistrements de niveaux des élèves dans la base de données
     * @see NiveauxEleves#updateScore(Boolean, Dao)
     * @throws SQLException
     */
    public void updateNiveau(Langue lang, Dao niveauElevesDao) throws SQLException {
        if (this.getScore() < 20) {
            this.setNiveau(BaremeNiveau.DEBUTANT);
        } else if (this.getScore() >= 20 && this.getScore() < 40) {
            this.setNiveau(BaremeNiveau.INTERMEDIAIRE);
        } else if (this.getScore() >= 40 && this.getScore() < 60) {
            this.setNiveau(BaremeNiveau.AVANCE);
        } else if (this.getScore() >= 60) {
            this.setNiveau(BaremeNiveau.EXPERT);
        }
        niveauElevesDao.update(this);
    }

    /**
     *  Cette méthode permet de mettre à jour le score d'un élève pour un enregistrement de niveau donné.
     *  Si l'élève a réussi l'exercice, son score est incrémenté de 1.
     *  Si l'élève a échoué l'exercice, son score est décrémenté de 1.
     *  La réussite de l'élève dépend de si son score obtenu est supérieur ou non à la note de passation (fixée par rapport au {@link Exercice#pourcentage} de réussite fixé par le professeur lors de la création de l'exercice.
     *  @param eleveValide booléen indiquant si l'élève a réussi ou échoué l'exercice
     *  @param niveauElevesDao Dao permettant la mise à jour de l'enregistrement de niveau dans la base de données
     * @see Notation
     * @see ReponseEleve#valide()
     *  @throws SQLException
     */
    public void updateScore(Boolean eleveValide, Dao niveauElevesDao) throws SQLException {
        // Mise à jour du score de l'enregistrement
        if(eleveValide){
            this.score++;
        }
        else {
            this.score--;
        }
        niveauElevesDao.update(this); // actualisation dans la base de données concrètement
    }
}

