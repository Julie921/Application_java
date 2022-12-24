package app_fake_quizzlet_v2;

import java.util.regex.Pattern;

/**
 * Interface Metaparse qui permet d'implémenter une méthode parse en fonction des besoins des exercices.
 */
public abstract class Metaparse {

    protected String delimiter = "";
    protected Pattern pattern = null;

    public abstract PhraseATrous parse(String phraseAParser);

    /**
     * Méthode parse() qui permet de parser un input.
     * @param phraseNotParsed
     */
    public abstract void parse(String phraseNotParsed, Pattern pattern);

}
