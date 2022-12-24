package app_fake_quizzlet_v2;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Classe ParseurPhraseATrous qui permet d'implémenter l'interface Metaparse
 */
public class ParseurPhraseATrous extends Metaparse {
    /**
     * Déclaration des attributs
     */
    String phraseCorrecte;
    String phraseAvecTrous;
    ArrayList<String> motsAPlacer = new ArrayList<>();

    public ParseurPhraseATrous() {
        delimiter = "#";
        String p = String.join("",delimiter, "([^", delimiter, "]+)", delimiter);
        pattern = Pattern.compile(p);
    }

    @Override
    public PhraseATrous parse(String phraseAParser) {
        String l_phraseCorrecte = phraseAParser.replaceAll(delimiter, "");
        String l_phraseAvecTrous = phraseAParser.replaceAll(pattern.toString(), "___");

        ArrayList<String> l_motsAPlacer = new ArrayList<>();
        Matcher m = pattern.matcher(phraseAParser);
        while (m.find()) { //tant qu'on trouve des "#([^#]+)#"
            l_motsAPlacer.add(m.group(1)); //on rajoute ce qui se trouve entre les # à la liste de mots à placer
        }

        PhraseATrous l_phrase = new PhraseATrous(l_phraseCorrecte,l_phraseAvecTrous, l_motsAPlacer);
        return l_phrase;
    }

    @Override
    /**
     * Implémentation de la méthode parse()
     * @param phraseNotParsed (String) (//TODO: pouvoir préciser le délimiter
     * Cette méthode permet de créer les attributs de l'objet Phrase
     */
    public void parse(String phraseNotParsed, Pattern pattern) {

        phraseCorrecte = phraseNotParsed.replaceAll("#", ""); //on enlève les # depuis l'input, du coup ça correspond à la phrase correcte

        phraseAvecTrous = phraseNotParsed.replaceAll("#([^#]+)#", "___"); //notre phrase avec les trous correspond à la phrase où on a remplacé les #blabla# âr "___"

        //Pour constituer la liste des mots à placer

        Matcher m = pattern.matcher(phraseNotParsed);

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
    public ArrayList<String> getMotsAPlacer(){
        return motsAPlacer;
    }
}
