package app_fake_quizzlet_v2;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *  La classe ParseurPhraseATrous implémente l'interface Metaparse
 *  et permet de parser les phrases qui composent un {@link ExoATrous}.
 *  Elle retourne un objet de type PhraseATrous contenant la phrase correcte, la phrase avec les trous et la liste des mots à trouver.
 */
public class ParseurPhraseATrous extends Metaparse {

    /**
     * Constructeur de la classe ParseurPhraseATrous.
     * Initialise le délimiteur de la phrase à trous et le motif de la regex utilisé pour extraire les mots à trouver.
     */
    public ParseurPhraseATrous() {
        delimiter = "#";
        String p = String.join("",delimiter, "([^", delimiter, "]+)", delimiter);
        pattern = Pattern.compile(p);
    }

    /**
     * Méthode qui permet de parser une phrase à trous et de retourner un objet de type PhraseATrous.
     * @param  phraseAParser la phrase à parser
     * @return l'objet PhraseATrous contenant la phrase correcte, la phrase avec les trous et la liste des mots à trouver
     * */
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
