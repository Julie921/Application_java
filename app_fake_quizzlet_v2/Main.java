package app_fake_quizzlet_v2;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.logger.LogBackendType;
import com.j256.ormlite.logger.LoggerFactory;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import javax.swing.*;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    private final static String DATABASE_URL = "jdbc:h2:file:./database.db";

    public final static String RESSOURCES_FOLDER = "./ressources";
    private static ConnectionSource connectionSource = null;
    private static Dao<Professeur, String> professeurDao;
    private static Dao<Eleve, String> eleveDao;
    private static Dao<NiveauxEleves, Integer> niveauElevesDao;

    public static Scanner scannerInputUser = new Scanner(System.in); //pour récupérer les réponses de l'utilisateur

    private static String QUIT_COMMAND = "!quit"; // pour quitter l'application

    // Référence de l'utilisateur principal pour toutes les opérations
    private static Utilisateur utilisateurActif = null;

    // Dictionnaire qui permet de récupérer plus facilement les objets Professeur
    private static HashMap<String, Professeur> listProfs = new HashMap<>();

    // Dictionnaire qui permet de récupérer plus facilement les objets Eleve
    private static HashMap<String, Eleve> listEleves = new HashMap<>();

    // Liste des NiveauxEleves qui fait le lien entre Eleve et Professeur
    private static ArrayList<NiveauxEleves> listNiveauxUtilisateur = new ArrayList<>();

    private static List<Exercice> listExercices = new ArrayList<>();

    private static HashMap<TypeExo, Metaparse> listParseurs = new HashMap<>();

    private static ImportExercice importExercice = null;

    private static JFileChooser fileChooser = new JFileChooser(".");


    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        LoggerFactory.setLogBackendFactory(LogBackendType.NULL); //enlève tous les print de ormlite

        try {
            // create our data-source for the database
            connectionSource = new JdbcConnectionSource(DATABASE_URL);

            // setup our database and DAOs
            professeurDao = DaoManager.createDao(connectionSource, Professeur.class);
            eleveDao = DaoManager.createDao(connectionSource, Eleve.class);
            niveauElevesDao = DaoManager.createDao(connectionSource, NiveauxEleves.class);

            // if you need to create the table
            TableUtils.createTableIfNotExists(connectionSource, Professeur.class);
            TableUtils.createTableIfNotExists(connectionSource, Eleve.class);
            TableUtils.createTableIfNotExists(connectionSource, NiveauxEleves.class);

            ///////////////////////////////////////////////////////////////////////


            createFromDatabase();
            remplirListeParseurs();
            importExercice = new ImportExercice(listParseurs);
            listExercices = importExercice.importDossier(RESSOURCES_FOLDER);

            Boolean professeurSession = false, studentSession = false;
            String inputUser = "";

            do {
                System.out.println("Bonjour ! Vous êtes un : \n - 1 : élève\n - 2 : professeur");
                System.out.print("Votre réponse: ");
                inputUser = scannerInputUser.nextLine();

                // Choix Session
                studentSession = (inputUser.equals("1"));
                professeurSession = (inputUser.equals("2"));
                if (!studentSession && !professeurSession && !inputUser.equals(QUIT_COMMAND)) {
                    System.out.println("Votre réponse ne convient pas");
                }

                if (inputUser.equals(QUIT_COMMAND)) {
                    fermetureConnexion();
                    return;
                }

            } while (!inputUser.equals(QUIT_COMMAND) && !studentSession && !professeurSession);

            do {
                System.out.println("\nQuel est votre identifiant ?");
                System.out.print("Idenfitiant/pseudo: ");
                inputUser = scannerInputUser.nextLine();

                if (inputUser.equals(QUIT_COMMAND))
                    break;

                if (professeurSession) { //TODO : changer et mettre dans Utilisateur ?
                    utilisateurActif = checkSession(inputUser, "prof");
                } else if (studentSession) {
                    utilisateurActif = checkSession(inputUser, "eleve");
                }

            } while (utilisateurActif == null);

            if (utilisateurActif == null) {
                fermetureConnexion();
                return;
            }

            System.out.println("\n//-------------------------------------------------//");
            System.out.println("Bonjour " + utilisateurActif.getPseudo() + "!");

            do {

                if (professeurSession) {
                    Boolean choixActionProf1 = false, choixActionProf2 = false, choixActionProf3 = false;
                    do {
                        System.out.println("//-------------------------------------------------//\n");
                        System.out.println("Que voulez-vous faire ?");
                        System.out.println("" +
                                "1 : Ajouter un exercice\n" +
                                "2 : Voir les exercices sauvegardés\n" +
                                "3 : Voir les résultats de mes élèves");
                        System.out.print("Votre réponse: ");
                        inputUser = scannerInputUser.nextLine();

                        // Choix Professeur
                        choixActionProf1 = inputUser.equals("1");
                        choixActionProf2 = inputUser.equals("2");
                        choixActionProf3 = inputUser.equals("3");

                        if (!choixActionProf1 && !choixActionProf2 && !choixActionProf3 && !inputUser.equals(QUIT_COMMAND)) {
                            System.out.println("Votre réponse ne convient pas\n//-------------------------------------------------//");
                        }

                    } while (!inputUser.equals(QUIT_COMMAND) && !choixActionProf1 && !choixActionProf2 && !choixActionProf3);

                    while (!inputUser.equals(QUIT_COMMAND)) {
                        // TODO compléter les cases
                        switch (inputUser) {
                            case "1": // ajouter un exercice
                                System.out.println("Attention à bien préciser les métadonnées sur la première lignes : \n " +
                                        "LANG:TYPE_EXO:NIVEAU:POURCENTAGE_POINT_POUR_REUSSIR\nPar exemple: 'FR:EXO_A_TROU:DEBUTANT:0.5' signifie qu'il s'agit d'un exercice à trous en français, pour les débutants, et qu'il faut obtenir 50% des points pour que l'exercice soit considéré comme réussi.\n" +
                                        "Langues disponibles : " + getEnumValuesAsString(Langue.class) + "\n" +
                                        "Type d'exercices : " + getEnumValuesAsString(TypeExo.class) + "\n" +
                                        "Niveaux : " + getEnumValuesAsString(BaremeNiveau.class) + "\n" +
                                        "Pourcentage : entre 0 et 1");
                                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                                int res = fileChooser.showOpenDialog(null);
                                if (res == JFileChooser.APPROVE_OPTION) {
                                    listExercices.add(importExercice.readFromFile(fileChooser.getSelectedFile()));
                                }
                                break;
                            case "2":
                                ArrayList<Exercice> exercicesAccessibles = utilisateurActif.getExercicesAccessibles(listNiveauxUtilisateur, listExercices);

                                utilisateurActif.afficheExercicesAccessibles(exercicesAccessibles);
                                break;
                            case "3": // le prof veut voir les notes de ses élèves
                                utilisateurActif.afficheResultats(listNiveauxUtilisateur);
                                break;
                            default:
                                System.out.println("Rien");
                                break;
                        }
                        System.out.print("Que voulez-vous faire ? ");
                        inputUser = scannerInputUser.nextLine();
                    }


                } else if (studentSession) {
                    Boolean choixEleve1 = false, choixEleve2 = false;
                    do {
                        System.out.println("//-------------------------------------------------//");
                        System.out.println("Que voulez-vous faire ?");
                        System.out.println("" +
                                "1 : Faire un exercice\n" +
                                "2 : Voir mes résultats\n" +
                                "3 : S'inscrire dans un nouveau cours de langue");

                        System.out.print("Votre réponse: ");
                        inputUser = scannerInputUser.nextLine();

                        // Choix Eleve
                        choixEleve1 = inputUser.equals("1");
                        choixEleve2 = inputUser.equals("2");
                        /*if (!choixEleve1 && !choixEleve2 && !inputUser.equals(QUIT_COMMAND)) {
                            System.out.println("Votre réponse ne convient pas\n//-------------------------------------------------//");
                        }*/
                        switch (inputUser) {
                            case "1": // l'élève veut faire un exercice

                                System.out.println("Voici une preview de chaque exercice acessible pour vos langues et votre niveau dans ces langues.");

                                ArrayList<Exercice> exercicesAccessibles = utilisateurActif.getExercicesAccessibles(listNiveauxUtilisateur, listExercices);

                                utilisateurActif.afficheExercicesAccessibles(exercicesAccessibles);

                                System.out.println("\nNuméro de l'exercice choisi : ");
                                inputUser = scannerInputUser.nextLine();
                                if (inputUser.equals(QUIT_COMMAND)) {
                                    fermetureConnexion();
                                    break;
                                }

                                if (Integer.parseInt(inputUser) > exercicesAccessibles.size() || inputUser.equals("0")) {
                                    System.out.println("\nVotre réponse ne convient pas\n");
                                } else { // l'élève a choisi un exercice de la liste
                                    Exercice exerciceChoisi = exercicesAccessibles.get(Integer.parseInt(inputUser) - 1);

                                    // construction de la réponse de l'exercice
                                    ReponseEleve reponseEleve = exerciceChoisi.construireReponse((Eleve) utilisateurActif);
                                    System.out.println("\nCorrection :\n");

                                    if (reponseEleve.valide()) { // l'élève a réussi l'exercice et gagne un point dans son score de la langue
                                        reponseEleve.affichePhrasesRempliesAvecCouleurs(listParseurs.get(exerciceChoisi.getType()).getReversedPattern()); // on récupère le reversed pattern du parseur utilisé pour le type de l'exercice spécifique
                                        System.out.println("Félicitations, vous avez réussi l'exercice.");
                                        System.out.println("Vous deviez obtenir " + reponseEleve.getSeuilPassation() + " points pour valider et vous en avez obtenu " + reponseEleve.getNoteDonnee() + "!\n");
                                    } else { // l'élève n'a pas réussi l'exercice
                                        reponseEleve.affichePhrasesRempliesAvecCouleurs(listParseurs.get(exerciceChoisi.getType()).getReversedPattern());
                                        System.out.println("Dommage, vous n'avez pas réussi l'exercice.");
                                        System.out.println("Vous deviez obtenir " + reponseEleve.getSeuilPassation() + " points pour valider et vous en avez obtenu " + reponseEleve.getNoteDonnee() + "...\n");
                                    }
                                    Boolean eleveValide = reponseEleve.valide();
                                    for (NiveauxEleves niv : listNiveauxUtilisateur) {
                                        if (niv.getPseudoEleve().equals(utilisateurActif.getPseudo()) && niv.getLangue() == exerciceChoisi.getLangue()) {
                                            niv.updateScore(eleveValide, niveauElevesDao);
                                            niv.updateNiveau(exerciceChoisi.getLangue(), niveauElevesDao);
                                            break;
                                        }
                                    }
                                }
                                break;

                            case "2": // l'utilisateur veut voir ses résultats
                                utilisateurActif.afficheResultats(listNiveauxUtilisateur);
                                break;
                            case "3":
                                List<Professeur> allProf = professeurDao.queryForAll();
                                System.out.println("Dans quel cours voulez-vous vous inscrire ?");
                                for (int i = 0; i < allProf.size(); i++) {
                                    Professeur p = allProf.get(i);
                                    System.out.println((i + 1) + ": " + p.getPseudo() + " - " + p.getLangue());
                                }
                                System.out.print("Professeur choisi: ");
                                inputUser = scannerInputUser.nextLine();
                                int indexProf = Integer.parseInt(inputUser) - 1;

                                // Si l'eleve a rentrée 0 dans sa donnée
                                // il a fini de choisir ses professeurs

                                // Sinon, on vérifie que l'index donné est correct
                                if ((indexProf < 0) || (indexProf >= allProf.size())) { //TODO problème quand on fait !quit
                                    System.out.println("Saisie invalide, index non compris.");
                                } else {
                                    Professeur prof = allProf.get(indexProf);
                                    ((Eleve) utilisateurActif).inscriptionLangue(prof, listNiveauxUtilisateur, niveauElevesDao);
                                }
                                break;

                            default:
                                System.out.println("Votre réponse ne convient pas.");
                                break;

                        }

                    } while (!inputUser.equals(QUIT_COMMAND) && !choixEleve1 && !choixEleve2);
                }

            } while (!inputUser.equals(QUIT_COMMAND));


        } finally {
            // destroy the data source which should close underlying connections
            if (connectionSource != null) {
                connectionSource.close();
            }
        }
    }

    /**
     * Cette méthode remplit la liste des parseurs (objets implémentant l'interface {@link Metaparse}) disponibles dans l'application.
     * Ces parseurs sont utilisés pour analyser et transformer des données d'input en objets {@link Phrase} spécifiques aux types d'exercices.
     * Actuellement, cette méthode ajoute les parseurs suivants :
     * <ul>
     * <li>{@link ParseurPhraseATrous} pour les exercices de type {@link TypeExo#EXO_A_TROU}</li>
     * <li>{@link ParseurTerminaison} pour les exercices de type {@link TypeExo#EXO_TERMINAISON}</li>
     * </ul>
     */
    private static void remplirListeParseurs() {
        listParseurs.put(TypeExo.EXO_A_TROU, new ParseurPhraseATrous());
        listParseurs.put(TypeExo.EXO_TERMINAISON, new ParseurTerminaison());
        // Rajouter nouveaux parseurs ici si on veut créer d'autres types d'exercices
    }

    /**
     * Cette méthode vérifie si l'utilisateur actif (un élève ou un professeur) existe dans la base de données et, le cas échéant, renvoie l'objet correspondant.
     * Si l'utilisateur n'existe pas, l'utilisateur peut choisir de s'enregistrer.
     * Si l'utilisateur est un élève, il doit en outre sélectionner ses professeurs parmi ceux existants dans la base de données.
     *
     * @param pseudo le pseudo de l'utilisateur actif
     * @param type   le type d'utilisateur actif ("prof" ou "eleve")
     * @return l'objet Utilisateur correspondant à l'utilisateur actif
     */
    public static Utilisateur checkSession(String pseudo, String type) {
        String inputUser = "";
        Boolean choix1, choix2 = false;
        Utilisateur user = null;
        try {
            if (type.equals("prof")) {
                user = professeurDao.queryForId(pseudo);
            } else if (type.equals("eleve")) {
                user = eleveDao.queryForId(pseudo);
            }

            if (user != null) {
                return user;
            }

            // L'utilisateur n'existe pas dans la DB
            System.out.println("/!\\ Identifiant inconnu ! Voulez-vous vous le créer ? (pseudo : " + pseudo + ")");
            System.out.println("1 : Oui");
            System.out.println("2 : Non");
            inputUser = scannerInputUser.nextLine();

            // Choix User
            choix1 = inputUser.equals("1");
            choix2 = inputUser.equals("2");

            if (!choix1 && !choix2) {
                System.out.println("Votre réponse ne convient pas\n//-------------------------------------------------//");
            }

            // L'utilisateur veut s'enregistrer
            if (choix1) {
                // Si l'utilisateur n'existe pas, création.
                if (type.equals("prof")) {

                    // Langue
                    System.out.println("Quelle langue enseignez-vous ?");
                    List<Langue> allLangues = Arrays.asList(Langue.values());
                    for (int i = 0; i < allLangues.size(); i++) {
                        System.out.println((i + 1) + ": " + allLangues.get(i));
                    }
                    System.out.print("Langue choisie: ");
                    inputUser = scannerInputUser.nextLine();
                    int indexLangue = Integer.parseInt(inputUser) - 1;
                    if (((indexLangue) < 0) || (indexLangue >= allLangues.size())) {
                        System.out.println("Saisie invalide, index non compris");
                        return null;
                    }

                    user = new Professeur(pseudo, allLangues.get(indexLangue));
                    professeurDao.create((Professeur) user);
                } else {
                    // Creation de l'utilsateur principal
                    user = new Eleve(pseudo);
                    eleveDao.create((Eleve) user);

                    Boolean profsFini = false;
                    do {
                        List<Professeur> allProf = professeurDao.queryForAll();
                        System.out.println("Qui sont vos professeurs parmi ceux-ci ?\n" +
                                "Note: Rentrer la valeur 0 lorsque vous avez fini de choisir tous vos professeurs.");
                        for (int i = 0; i < allProf.size(); i++) {
                            Professeur p = allProf.get(i);
                            System.out.println((i + 1) + ": " + p.getPseudo() + " - " + p.getLangue());
                        }
                        System.out.print("Professeur choisi: ");
                        inputUser = scannerInputUser.nextLine();
                        int indexProf = Integer.parseInt(inputUser) - 1;

                        // Si l'eleve a rentrée 0 dans sa donnée
                        // il a fini de choisir ses professeurs
                        if (indexProf == -1) {
                            profsFini = true;
                        } else {
                            // Sinon, on vérifie que l'index donné est correct
                            if ((indexProf < 0) || (indexProf >= allProf.size())) {
                                System.out.println("Saisie invalide, index non compris.");
                                return null;
                            }
                            // et on l'ajoute dans la liste des profs de l'eleve.
                            Professeur prof = allProf.get(indexProf);
                            ((Eleve) user).inscriptionLangue(prof, listNiveauxUtilisateur, niveauElevesDao);
                        }
                    } while (!profsFini);
                }

            }
            // Le prof ne veut pas s'enregistrer
            else if (choix2) {
                System.out.println("WARNING: Vous ne pouvez pas utiliser l'application sans créer de compte.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return user;
    }

    /**
     * Cette méthode permet de fermer la connexion à la base de données en utilisant l'objet {@link ConnectionSource} fourni en paramètre.
     * Elle affiche un message de confirmation de fermeture de l'application.
     *
     * @throws Exception s'il y a une erreur lors de la fermeture de la connexion
     */
    public static void fermetureConnexion() throws Exception {
        System.out.println("\n//-------------------------------------------------//");
        System.out.println("Fermeture de l'application");
        System.out.println("//-------------------------------------------------//");
        if (connectionSource != null) {
            connectionSource.close();
        }
    }

    /**
     * Méthode statique qui re-crée les objets à partir de la base de données.
     * Cette méthode récupère les enregistrements de la table Professeur et Eleve de la base de données et les ajoute respectivement aux listes {@link #listProfs} et {@link #listEleves}.
     * Elle récupère également les enregistrements de la table NiveauEleve et les ajoute à la liste {@link #listNiveauxUtilisateur}.
     * Il est important de noter que cette méthode ne doit être utilisée qu'à l'initialisation de l'application, lorsque la base de données est vide et qu'il faut remplir la base de données avec des objets "vides" (sans élèves ni exercices). Si cette méthode est utilisée alors que la base de données contient déjà des informations, celles-ci seront écrasées par les objets "vides" créés par cette méthode.
     *
     * @throws SQLException Si une erreur se produit lors de l'exécution de la requête SQL.
     */
    public static void createFromDatabase() throws SQLException {

        // Récupération des enregistrements de la table Professeur de la base de données
        List<Professeur> results = professeurDao.queryForAll();
        // Pour chaque enregistrement, on ajoute l'objet Professeur à la liste listProfs
        for (Professeur p : results) {
            listProfs.put(p.getPseudo(), p);
        }

        // Récupération des enregistrements de la table Eleve de la base de données
        List<Eleve> resultsEleves = eleveDao.queryForAll();
        // Pour chaque enregistrement, on ajoute l'objet Eleve à la liste listEleves
        for (Eleve e : resultsEleves) {
            listEleves.put(e.getPseudo(), e);
        }

        // Récupération de tous les enregistrements de la table NiveauEleve de la base de données
        listNiveauxUtilisateur = (ArrayList<NiveauxEleves>) niveauElevesDao.queryForAll();
    }

    /**
     * Cette méthode prend en entrée une classe qui étend l'interface Enum, et retourne une chaîne de caractères qui contient les valeurs de l'énumération séparées par une virgule.
     *
     * @param e la classe de l'énumération dont on veut récupérer les valeurs
     * @return une chaîne de caractères qui contient les valeurs de l'énumération séparées par une virgule
     */
    public static String getEnumValuesAsString(Class<? extends Enum<?>> e) {
        // On utilise un stream pour parcourir chaque élément de l'énumération, on map chaque élément sur son nom, et on utilise Collectors.joining pour concaténer les éléments en une chaîne de caractères séparée par une virgule.
        return Arrays.stream(e.getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.joining(", "));
    }

}