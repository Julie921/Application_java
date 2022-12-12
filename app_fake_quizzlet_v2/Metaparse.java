package app_fake_quizzlet_v2;

/**
 * Interface Metaparse qui permet d'implémenter une méthode parse en fonction des besoins des exercices.
 */
public interface Metaparse {

    /**
     * Méthode parse() qui permet de parser un input.
     * @param phraseNotParsed
     */
    public void parse(String phraseNotParsed);

}
