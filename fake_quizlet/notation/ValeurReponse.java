package fake_quizlet.notation;

import fake_quizlet.notation.BaremeNiveau;
import fake_quizlet.notation.Correction;
import fake_quizlet.notation.Notation;

/**
 * Énumération représentant les valeurs possibles d'une réponse à une question.
 * VRAI représente une réponse correcte.
 * FAUX représente une réponse incorrecte.
 * NR représente une réponse non répondue.
 *
 * Cette énumération est utilisée pour corriger et calculer une note selon le barème de l'élève
 * @see Correction
 * @see Notation
 * @see BaremeNiveau
 */
public enum ValeurReponse {

    /**
     * Valeur attribuée à une réponse qui est correcte.
     */
    VRAI,

    /**
     * Valeur attribuée à une réponse qui est fausse.
     */
    FAUX,

    /**
     * Valeur attribuée à une réponse qui n'est pas répondue.
     */
    NR;

}
