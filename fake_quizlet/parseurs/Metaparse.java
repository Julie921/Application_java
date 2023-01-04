package fake_quizlet.parseurs;

import fake_quizlet.exercice.ExoATrous;
import fake_quizlet.exercice.Phrase;
import fake_quizlet.exercice.PhraseATrous;

import java.util.regex.Pattern;

/**
 * La classe Metaparse est une interface qui définit une méthode abstraite {@code parse()} permettant de parser un input.
 *
 * Metaparse est destinée à être implémentée par des classes spécifiques aux exercices. Par exemple, {@link ParseurPhraseATrous} est une classe qui implémente cette interface et qui permet de créer les objets {@link PhraseATrous} des exercices {@link ExoATrous}.
 */
public abstract class Metaparse {

    /**
    * Chaine de caractères utilisée pour délimiter les parties de l'input à parser.
    */
    protected String delimiter = "";

    /**
     * Objet {@link Pattern} utilisé pour parser l'input.
     */
    protected Pattern pattern = null;

    /**
     * Objet {@link Pattern} utilisé pour parser l'input dans le sens inverse.
     */
    protected Pattern reversedPattern = null;

    /**
     *  Méthode {@code parse()} qui permet de parser un input en utilisant l'objet {@link Pattern} fourni en paramètre.
     * @param phraseNotParsed l'input à parser
     */
    public abstract Phrase parse(String phraseNotParsed);

    public Pattern getPattern(){
        return this.pattern;
    }

    /**
     * Méthode qui retourne l'objet {@link Pattern} utilisé pour parser l'input dans le sens inverse.
     * C'est utile pour reconstituer l'exercice avec les réponses de l'élève.
     *
     * @return l'objet {@link Pattern} utilisé pour parser l'input dans le sens inverse
     */
    public Pattern getReversedPattern(){
        return this.reversedPattern;
    }
}
