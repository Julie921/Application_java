package app_fake_quizzlet_v2;

/**
 * TODO: add javadoc
 */
public enum BaremeNiveau {

    DEBUTANT(1, 0, 0), INTERMEDIAIRE(1, -1, 0), AVANCE(1, -1, -1), EXPERT(1, -2, -1);

    private int vrai; //nombre de points attribués par réponse vraie de l'élève
    private int faux; //nombre de points attribués par réponse fausse de l'élève
    private int na; //nombre de points attributs par réponses non répondue de l'élève

    BaremeNiveau(int vrai, int faux, int na) { //TODO: écrire javadoc
        this.vrai = vrai;
        this.faux = faux;
        this.na = na;
    }

    public int getVrai(){ //TODO : écrire javadoc
        return vrai;
    }

    public int getFaux(){ //TODO : écrire javadoc
        return faux;
    }

    public int getNa(){ //TODO : écrire javadoc
        return na;
    }
}
