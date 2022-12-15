package app_fake_quizzlet_v2;

import java.util.ArrayList;

public class ReponseEleveExoATrous extends ReponseEleve{

    ArrayList<ArrayList<String>> mots = new ArrayList<>();

    ReponseEleveExoATrous(ArrayList<ArrayList<String>> mots){
        this.mots = mots;
    }

    public ArrayList<ArrayList<String>> getMots(){
        return this.mots;
    }

}
