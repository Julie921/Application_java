package fake_quizlet.notation;

import fake_quizlet.reponse.ReponseEleve;

/**
 * Interface Notation qui permet d'implémenter des méthodes servant à construire une note.
 * Par exemple, {@link ReponseEleve} implémente cette interface.
 */
public interface Notation {

    /**
     * Méthode permettant de calculer une note.
     * La logique de calcul de la note doit être définie dans les classes qui implémentent cette interface.
     */
    public void calculNote();
}
