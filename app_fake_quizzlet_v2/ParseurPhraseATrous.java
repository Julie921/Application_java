package app_fake_quizzlet_v2;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseurPhraseATrous implements Metaparse{

    String phraseCorrecte;
    String phraseAvecTrous;
    ArrayList<String> motsAPlacer = new ArrayList<>();

    @Override
    public void parse(String phraseNotParsed) { //TODO: pouvoir préciser le délimiter

        phraseCorrecte = phraseNotParsed.replaceAll("#", ""); //on enlève les # depuis l'input, du coup ça correspond à la phrase correcte

        phraseAvecTrous = phraseNotParsed.replaceAll("#([^#]+)#", "___"); //notre phrase avec les trous correspond à la phrase où on a remplacé les #blabla# âr "___"

        //Pour constituer la liste des mots à placer
        Pattern p = Pattern.compile("#([^#]+)#"); //on récupère ce qui se trouve entre les #
        Matcher m = p.matcher(phraseNotParsed);

        while (m.find()) { //tant qu'on trouve des "#([^#]+)#"
            motsAPlacer.add(m.group(1)); //on rajoute ce qui se trouve entre les # à la liste de mots à placer
        }

    }

    public String getPhraseAvecTrous() {
        return phraseAvecTrous;
    }

    public String getPhraseCorrecte() {
        return phraseCorrecte;
    }

    public ArrayList getMotsAPlacer(){
        return motsAPlacer;
    }
}
