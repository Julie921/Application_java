package app_fake_quizzlet_v2;

import com.j256.ormlite.field.DatabaseField;

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
    private String eleve;

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
    private String professeur;

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
        this.eleve = eleve.getPseudo();
        this.professeur = professeur.getPseudo();
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
    public String getEleve() {
        return eleve;
    }

    //TODO  à quoi elle sert ?
    public void setEleve(Eleve eleve) {
        this.eleve = eleve.getPseudo();
    }

    /**
     * Retourne le pseudo du professeur en charge de l'enseignement de l'élève dans cette langue.
     *
     * @return le pseudo du professeur
     */
    public String getProfesseur() {
        return professeur;
    }

    /**
     * Modifie le professeur en charge de l'enseignement de l'élève dans cette langue.
     *
     * @param professeur le nouvel objet Professeur en charge de l'enseignement de l'élève
     */
    public void setProfesseur(Professeur professeur) {
        this.professeur = professeur.getPseudo();
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
     * Retourne une chaîne de caractères représentant l'objet NiveauxEleves sous la forme "NiveauxEleves{index=x, eleve=y, langue=z, niveau=w, professeur=u, score=v}".
     *
     * @return une chaîne de caractères représentant l'objet NiveauxEleves
     */
    @Override
    public String toString() {
        return "NiveauxEleves{" +
                "index=" + index +
                ", eleve=" + eleve +
                ", langue=" + langue +
                ", niveau=" + niveau +
                ", professeur=" + professeur +
                ", score=" + score +
                '}';
    }
}
