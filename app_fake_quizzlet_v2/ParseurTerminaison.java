package app_fake_quizzlet_v2;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseurTerminaison extends Metaparse { //TODO : est-ce qu'on garde vraiment cet exemple d'autre parseur ? l'exercice est globalement le même que le phrase à trous

    /**
     * Constructeur de la classe ParseurTerminaison.
     * Initialise le délimiteur de la phrase à trous et le motif de la regex utilisé pour extraire les terminaisons à trouver.
     * Exemple : Mang--er
     */
    public ParseurTerminaison() {
        delimiter = "-";
        String p = String.join(delimiter,delimiter, "([^", delimiter, "]+)( |$)");
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

}
