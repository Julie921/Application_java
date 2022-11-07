package app_fake_quizzlet;

/**
 * Classe abstraite pour instancier tous les types d'exercices. 
 * Chaque exercice aura dans son constructeur : 
 * - un input d'un professeur sous forme de chaîne de caractère
 * - un nombre de point maximum pour attribuer ensuite la note
 * - une méthode getCorrection() pour noter l'élève
 */
public abstract class Exercice {

    String input_prof;
    int nb_points;

    abstract void getCorrection();

}
