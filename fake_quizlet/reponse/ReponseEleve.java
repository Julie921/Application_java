package fake_quizlet.reponse;

import fake_quizlet.exercice.Exercice;
import fake_quizlet.notation.ValeurReponse;
import fake_quizlet.notation.Correction;
import fake_quizlet.notation.Notation;
import fake_quizlet.utilisateurs.Eleve;

import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 *  La classe ReponseEleve est une classe abstraite qui représente la réponse d'un élève à un exercice.
 * Elle contient :
 * - l'exercice que l'élève a fait,
 * - l'élève qui a répondu,
 * - les réponses fournies par l'élève,
 * - la correction de ses réponses : chaque réponse fournie est attribuée une {@link ValeurReponse} : VRAI, FAUX ou NR (non répondu)
 * - la note calculée
 * - et le seuil de passation : la note qu'il faut atteindre pour que l'execice soit considéré comme réussi
 * Cette classe implémente les interfaces {@link Correction} et {@link Notation}.
 *
 * La classe {@link ReponseEleveExoATrous} étend cette classe abstraite.
 *
 * @see Correction
 * @see Notation
 * @see ReponseEleveExoATrous
 */
public abstract class ReponseEleve implements Correction, Notation {

    /**
     * L'exercice auquel appartiennent ces réponses
     */
    Exercice exercice;

    /**
     * L'élève qui a fait l'exercice.
     */
    Eleve eleve; // l'élève qui fait l'exercice

    /**
     * Les réponses que l'élève a founies
     */
    ArrayList<ArrayList<String>> reponsesFournies = new ArrayList<>();

    /**
     * La correction des réponses fournies par l'élève (la correction est faite avec l'interface {@link Correction}.
     * On attribue à chaque réponse fournie une {@link ValeurReponse}.
     */
    private ArrayList<ArrayList<ValeurReponse>> reponsesCorrection = new ArrayList<>();

    /**
     * La note calculée avec l'interface {@link Notation}.
     */
    protected Float noteDonnee = 0.0F;

    /**
     *  La note qu'il faut atteindre (fixée par le professeur) pour que l'exercice soit considéré comme réussi
     */
    protected Float seuilPassation = 0.0F;

    /**
     * Constructeur pour la classe ReponseEleve.
     * @param exercice l'exercice que l'élève a fait
     * @param eleve l'élève qui a répondu à l'exercice
     */
    ReponseEleve(Exercice exercice, Eleve eleve){
        this.exercice = exercice;
        this.eleve = eleve;
    }

    /**
     * Retourne l'exercice correspondant à cette réponse.
     * @return l'exercice correspondant à cette réponse.
     */
    public Exercice getExercice(){
        return this.exercice;
    }

    /**
     * Retourne l'élève ayant répondu à l'exercice.
     * @return l'élève ayant répondu à l'exercice.
     */
    public  Eleve getEleve(){
        return this.eleve;
    }

    /**
     * Retourne les réponses fournies par l'élève.
     * @return les réponses fournies par l'élève.
     */
    public ArrayList<ArrayList<String>> getReponsesFournies(){
        return this.reponsesFournies;
    }

    /**
     * Retourne la correction des réponses fournies : chaque réponse est attribuée une {@link ValeurReponse}.
     * @return la correction des réponses fournies.
     */
    public ArrayList<ArrayList<ValeurReponse>> getReponsesCorrection() {
        return reponsesCorrection;
    }

    /**
     * Instancie la correction des réponses fournies.
     * @param reponsesCorrection la correction des réponses fournies.
     */
    public void setReponsesCorrection(ArrayList<ArrayList<ValeurReponse>> reponsesCorrection) {
        this.reponsesCorrection = reponsesCorrection;
    }

    /**
     * Retourne la note calculée pour cette réponse.
     * @return la note calculée pour cette réponse.
     */
    public Float getNoteDonnee() {
        return noteDonnee;
    }

    /**
     * Instancie la note donnée à cette réponse.
     * @param noteDonnee la nouvelle note donnée à cette réponse.
     */
    public void setNoteDonnee(Float noteDonnee) {
        this.noteDonnee = noteDonnee;
    }

    /**
     * Retourne vrai si l'élève valide (si la note calculée est supérieure ou égale au seuil de passation), faux sinon.
     * @return vrai si l'élève valide, faux sinon.
     */
    public Boolean valide() {
        return this.noteDonnee >= this.seuilPassation;
    }

    /**
     * Méthode abstraite permettant de définir le seuil de passation de l'exercice,
     * c'est-à-dire la note qu'il faut obtenir au minimum pour que l'exercice soit considéré comme réussi
     * et que l'élève valide cet exercice.
     */
    public abstract void setSeuilPassation();

    public Float getSeuilPassation(){
        return this.seuilPassation;
    }

    public abstract void affichePhrasesRempliesAvecCouleurs(Pattern pattern);
}
