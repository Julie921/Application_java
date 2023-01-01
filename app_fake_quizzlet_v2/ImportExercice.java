package app_fake_quizzlet_v2;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ImportExercice {

    HashMap<TypeExo, Metaparse> listParseurs = null;

    public ImportExercice(HashMap<TypeExo, Metaparse> liste) {
        this.listParseurs = liste;
    }

    public void addParseur(TypeExo type, Metaparse parseur) {
        this.listParseurs.put(type, parseur);
    }

    public Exercice readFromFile(File file) throws IOException {
        // TODO refractor readFomFile et readFile
        // Données à remplir
        Langue langueExo = null;
        TypeExo typeExo = null;
        BaremeNiveau niveauExo = null;
        Float pourcentageExo = 0.0F;

        String inputParser = "";
        Exercice exoACreer = null;

        BufferedReader reader = new BufferedReader(new FileReader(file));

        // Lit chaque ligne du fichier
        String line;
        int i = 1;
        while ((line = reader.readLine()) != null) {
            if (i == 1) {
                String[] metadata = line.split(":");
                if (metadata.length == 4) {
                    langueExo = Langue.valueOf(metadata[0]);
                    typeExo = TypeExo.valueOf(metadata[1]);
                    niveauExo = BaremeNiveau.valueOf(metadata[2]);
                    pourcentageExo = Float.parseFloat(metadata[3]);
                }
            } else {
                inputParser += line;
            }
            i++;
        }

        // Ferme le fichier
        reader.close();

        switch (typeExo) {
            case EXO_A_TROU:
                exoACreer = new ExoATrous(langueExo, niveauExo, pourcentageExo, (ParseurPhraseATrous) listParseurs.get(TypeExo.EXO_A_TROU), inputParser);
                break;

            case EXO_TERMINAISON:
                // TODO créer exercice terminaison
                // exoACreer = new ExoATrous(langueExo, niveauExo, pourcentageExo, listParseurs.get(TypeExo.EXO_TERMINAISON), inputParser);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + typeExo);
        }

        System.out.println("--------------------------------ICI");
        System.out.println(file.getPath());
        System.out.println(Main.RESSOURCES_FOLDER+"/"+file.getName());
        Path source = Paths.get(file.getPath());
        Path target = Paths.get(Main.RESSOURCES_FOLDER+"/"+file.getName());
        if (Files.notExists(target)) {
            Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Fichier copié avec succès.");
        }
        System.out.println("--------------------------------FIN");

        return exoACreer;
    }

    public Exercice readFile(String path) throws IOException {
        // Données à remplir
        Langue langueExo = null;
        TypeExo typeExo = null;
        BaremeNiveau niveauExo = null;
        Float pourcentageExo = 0.0F;

        String inputParser = "";
        Exercice exoACreer = null;

        File file = new File(path);
        BufferedReader reader = new BufferedReader(new FileReader(file));

        // Lit chaque ligne du fichier
        String line;
        int i = 1;
        while ((line = reader.readLine()) != null) {
            if (i == 1) {
                String[] metadata = line.split(":");
                if (metadata.length == 4) {
                    langueExo = Langue.valueOf(metadata[0]);
                    typeExo = TypeExo.valueOf(metadata[1]);
                    niveauExo = BaremeNiveau.valueOf(metadata[2]);
                    pourcentageExo = Float.parseFloat(metadata[3]);
                }
            } else {
                inputParser += line;
            }
            i++;
        }

        // Ferme le fichier
        reader.close();

        switch (typeExo) {
            case EXO_A_TROU:
                exoACreer = new ExoATrous(langueExo, niveauExo, pourcentageExo, (ParseurPhraseATrous) listParseurs.get(TypeExo.EXO_A_TROU), inputParser);
                break;
            case EXO_TERMINAISON:
                // TODO créer exercice terminaison
                // exoACreer = new ExoATrous(langueExo, niveauExo, pourcentageExo, listParseurs.get(TypeExo.EXO_TERMINAISON), inputParser);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + typeExo);
        }

        return exoACreer;
    }

    public List<Exercice> importDossier(String pathDossier) throws IOException {
        File dossier = new File(pathDossier);
        String[] files = dossier.list();
        List<Exercice> listeExercice = new ArrayList<>();

        if (files != null) {
            for (String file : files) {
                listeExercice.add(readFile(pathDossier+"/"+file));
            }
        } else {
            System.out.println("Directory is empty or does not exist.");
        }

        return listeExercice;
    }

}
