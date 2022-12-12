package app_fake_quizzlet_v2;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Classe ParseurPhraseATrous qui permet d'implémenter l'interface Metaparse
 */
public class ParseurPhraseATrous implements Metaparse{
    /**
     * Déclaration des attributs
     */
    String phraseCorrecte;
    String phraseAvecTrous;
    ArrayList<String> motsAPlacer = new ArrayList<>();

    @Override
    /**
     * Implémentation de la méthode parse()
     * @param phraseNotParsed (String) (//TODO: pouvoir préciser le délimiter
     * Cette méthode permet de créer les attributs de l'objet Phrase
     */
    public void parse(String phraseNotParsed) {

        phraseCorrecte = phraseNotParsed.replaceAll("#", ""); //on enlève les # depuis l'input, du coup ça correspond à la phrase correcte

        phraseAvecTrous = phraseNotParsed.replaceAll("#([^#]+)#", "___"); //notre phrase avec les trous correspond à la phrase où on a remplacé les #blabla# âr "___"

        //Pour constituer la liste des mots à placer
        Pattern p = Pattern.compile("#([^#]+)#"); //on récupère ce qui se trouve entre les #
        Matcher m = p.matcher(phraseNotParsed);

        while (m.find()) { //tant qu'on trouve des "#([^#]+)#"
            motsAPlacer.add(m.group(1)); //on rajoute ce qui se trouve entre les # à la liste de mots à placer
        }

    }

    /**
     * Getter de l'attribut phraseAvecTrous()
     * @return phraseAvecTrous
     */
    public String getPhraseAvecTrous() {
        return phraseAvecTrous;
    }
    /**
     * Getter de l'attribut phraseCorrecte()
     * @return phraseCorrecte
     */
    public String getPhraseCorrecte() {
        return phraseCorrecte;
    }
    /**
     * Getter de l'attribut motsAPlacer()
     * @return motsAPlacer
     */
    public ArrayList getMotsAPlacer(){
        return motsAPlacer;
    }
}
