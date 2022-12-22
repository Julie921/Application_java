package app_fake_quizzlet_v2;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Historique {

    ArrayList<Map<String, Object>> data = new ArrayList<>(); //liste de dictionnaire : un dico = une entrée dans l'historique

    public void addEntry(Exercice exercice, float note, ReponseEleve reponseEleve, BaremeNiveau niveau){
        Map<String, Object> record = new HashMap<>();
        record.put("exercice",exercice);
        record.put("note", note);
        record.put("reponse", reponseEleve);
        record.put("niveau", niveau);
        data.add(record);
    }

    public Map<String, Object> getEntry(int index){
        return data.get(index-1);
    }

    public ArrayList<Map<String, Object>> getData() {
        return data;
    }

}
