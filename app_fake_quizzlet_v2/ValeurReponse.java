package app_fake_quizzlet_v2;

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

    VRAI, FAUX, NR;

}
