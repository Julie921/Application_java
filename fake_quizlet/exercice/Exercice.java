package fake_quizlet.exercice;

import fake_quizlet.notation.BaremeNiveau;
import fake_quizlet.reponse.ReponseEleve;
import fake_quizlet.utilisateurs.Eleve;

/**
 * Classe abstraite représentant un exercice. Un exercice peut être un texte à trous, un QCM ou une traduction, par exemple.
 *
 * Cette classe contient deux méthodes abstraites :
 * - afficheExercice() : permet d'afficher l'exercice à l'élève pour qu'il puisse le faire ou non
 *
 * Ces deux méthodes devront être instanciées dans les classes qui étendent la classe Exercice.
 *
 * La classe {@link ExoATrous} étend la classe Exercice.
 */
public abstract class Exercice {

    /** Langue de l'exercice */
    private Langue langue;

    /** Niveau de l'exercice :
     * - DEBUTANT
     * - INTERMEDIAIRE
     * - AVANCE
     * - EXPERT
     * */
    private BaremeNiveau niveau;

    /**
     * Pourcentage de points qu'un élève doit obtenir en faisant l'exercice pour que l'exercice soit considéré comme réussi.
     */
    private Float pourcentage;

    /**
     * Le type d'exercice
     * @see TypeExo
     */
    private TypeExo type;

    /**
     * Constructeur de la classe Exercice.
     *
     * @param langue langue de l'exercice
     * @param niveau niveau de l'exercice
     * @param pourcentage pourcentage de points qu'un élève doit obtenir en faisant l'exercice pour que l'exercice soit considéré comme réussi.
     */
    public Exercice(Langue langue, BaremeNiveau niveau, Float pourcentage){
        this.langue = langue;
        this.niveau = niveau;
        this.pourcentage = pourcentage;
    }

    /**
     * Méthode afficheExercice() permet d'afficher l'exercice à l'élève pour qu'il puisse le faire ou non.
     */
    public abstract void afficheExercice();

    /**
     * Getter permettant de récupérer la langue de l'exercice.
     *
     * Liste des langues possibles à ce jour :
     * TODO: mettre la liste des langues
     *
     * @return langue de l'exercice
     */
    public Langue getLangue(){
        return this.langue;
    }

    /**
     * Getter permettant de récupérer le niveau de l'exercice :
     *
     * Liste des niveaux possibles :
     * - DEBUTANT
     * - INTERMEDIAIRE
     * - AVANCE
     * - EXPERT
     *
     * @return niveau de l'exercice
     */
    public BaremeNiveau getNiveau() {
        return niveau;
    }

    /**
     * Getter permettant de récupérer le pourcentage de points qu'un élève doit obtenir en faisant l'exercice pour que l'exercice soit
     * considéré comme réussi.
     *
     * @return pourcentage de points qu'un élève doit obtenir en faisant l'exercice pour que l'exercice soit considéré comme réussi.
     */
    public Float getPourcentage() {
        return pourcentage;
    }

    /**
     * Setter permettant de modifier le pourcentage de points qu'un élève doit obtenir en faisant l'exercice pour que l'exercice soit considéré comme réussi.
     * @param pourcentage le pourcentage de points qu'un élève doit obtenir en faisant l'exercice pour que l'exercice soit considéré comme réussi.
    */
    public void setPourcentage(Float pourcentage) {
        this.pourcentage = pourcentage;
    }

    /**
     * Affiche un aperçu de l'exercice. Elle est utilisée pour que les professeurs puissent voir les exercices déjà enregistrés et que les élèves puissent choisir l'exercice qu'ils veulent faire.
     * Elle est à définir dans les classe filles de `Exercice`
     */
    public abstract void previewText();

    /**
     *  Cette méthode permet de construire la réponse d'un élève à un exercice.
     *  Elle est définie dans les classes filles de `Exercice`.
     *  @param eleve l'élève dont on souhaite construire la réponse
     *  @return La réponse de l'élève à l'exercice
     * @see ExoATrous#construireReponse(Eleve) 
     */
    public abstract ReponseEleve construireReponse(Eleve eleve);

    /**
     * Affiche la correction de l'exercice, c'est-à-dire les vraies réponses qu'il fallait fournir.
     */
    public abstract void afficherCorrection();

    /**
     * Getter pour récupérer le type de l'exercice.
     * @see TypeExo
     * @return le type de l'exercice
     */
    public TypeExo getType(){
        return this.type;
    }

    /**
     * Setter pour définir le type d'exercice dont il s'agit.
     * @see TypeExo
     * @param type le type de l'exercice
     */
    public void setType(TypeExo type){
        this.type = type;
    }
}
