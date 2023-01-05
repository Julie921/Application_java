package fake_quizlet.exercice;

/**
 * Énumération représentant les types d'exercices disponibles.
 * EXO_A_TROU représente un exercice à trous
 * EXO_TERMINAISON représente par exemple un exercice où l'élève doit compléter les terminaisons
 * Si on veut ajouter d'autres exercices, il faut renseigner ici le code unique pour le type d'exercice.
 * Cette énumération permet au professeur de renseigner le type de l'exercice qu'il veut créer dans les métadonnées de son fichier pour que l'application utilise le bon parseur.
 */
public enum TypeExo {

    /**
     * Le code pour les exercices de la classe {@link ExoATrous}
     */
    EXO_A_TROU,

    /**
     * Le code pour les exercices où il faut remplir la terminaison
     */
    EXO_TERMINAISON;

}
