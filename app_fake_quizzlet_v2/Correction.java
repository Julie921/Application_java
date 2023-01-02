package app_fake_quizzlet_v2;

/**
 * Interface permettant de définir la méthode de correction de réponses d'un élève pour un exercice.
 *
 * La méthode {@link #corrige()} doit être implémentée pour définir la logique de correction des réponses.
 *
 * La classe {@link ReponseEleve} implémente cette interface.
 *
 * @see ReponseEleve
 */
public interface Correction {

    /**
     * Méthode de correction de réponses.
     *
     * Cette méthode doit être implémentée par les classes qui utilisent cette interface pour définir la logique de correction des réponses.
     */
    public void corrige();

}
