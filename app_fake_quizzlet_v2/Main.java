package app_fake_quizzlet_v2;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.logger.LogBackendType;
import com.j256.ormlite.logger.LoggerFactory;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import org.fusesource.jansi.Ansi;

import javax.swing.*;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    private final static String DATABASE_URL = "jdbc:h2:file:./database.db";

    /**
     * Dossier dans lequel se trouve les ressources de l'application = les fichiers .txt contenant des exercices
     */
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

            // création des tables dans la base de données pour les objets Professeur, Eleve et NiveauxEleves
            TableUtils.createTableIfNotExists(connectionSource, Professeur.class);
            TableUtils.createTableIfNotExists(connectionSource, Eleve.class);
            TableUtils.createTableIfNotExists(connectionSource, NiveauxEleves.class);

            ///////////////////////////////////////////////////////////////////////


            createFromDatabase(); // création des objets Eleve, Prof et NiveuxEleves depuis les infos de la database
            remplirListeParseurs(); // création des parseurs

            // création de l'objet pour importer des exercices
            importExercice = new ImportExercice(listParseurs);
            listExercices = importExercice.importDossier(RESSOURCES_FOLDER); // récupération des exercices qui sont dans le dossier de ressources de l'application

            String inputUser = ""; // initilisation de la string qui contiendra ce que l'utilisateur rentre

            //Cette boucle permet à l'utilisateur de choisir son type de session (élève ou professeur).
             //Si l'utilisateur entre 1, la variable studentSession sera mise à true. Si l'utilisateur entre 2, la variable professeurSession sera mise à true.
             //Si l'utilisateur entre !quit, la méthode fermetureConnexion sera appelée et le programme se termine.
             //Si l'utilisateur entre une autre réponse, il sera informé que sa réponse ne convient pas.
             //La boucle se répète jusqu'à ce que l'utilisateur entre 1, 2 ou !quit.

            boolean studentSession = false;
            boolean professeurSession = false;

            while (!studentSession && !professeurSession) { // tant qu'on ne sait pas si c'est un étudiant ou un prof
                System.out.println("Bonjour ! Vous êtes un : \n " +
                        "- 1 : élève\n " +
                        "- 2 : professeur");
                System.out.print("Votre réponse: ");
                inputUser = scannerInputUser.nextLine();

                if (inputUser.equals("1")) { // c'est un élève
                    studentSession = true;
                } else if (inputUser.equals("2")) { // c'est un prof
                    professeurSession = true;
                } else if (!inputUser.equals(QUIT_COMMAND)) { // input différent de "1", "2" ou "!quit"
                    System.out.println(Ansi.ansi().fg(Ansi.Color.RED).a("\n/!\\ Votre réponse ne convient pas.\n").reset());

                } else { // l'utilisateur a rentré la commande pour quitter
                    fermetureConnexion();
                    return;
                }
            }

            // boucle pour s'identifier

            Utilisateur utilisateurActif = null; // l'utilisateur qui utilise l'application ne s'est pas encore identifié

            while (utilisateurActif == null) { // tant que l'utilisateur ne s'est pas identifié
                System.out.println("\nQuel est votre identifiant ?");
                System.out.print("Identifiant/pseudo : ");
                inputUser = scannerInputUser.nextLine();

                if (inputUser.equals(QUIT_COMMAND)) { // l'utilisateur veut quitter l'application
                    fermetureConnexion();
                    return;
                }

                String type = studentSession ? "eleve" : "prof"; // si studentSession est true, la valeur du type est "eleve", si studentSession est false, la valeur est "prof"
                utilisateurActif = checkSession(inputUser, type); // l'utilisateur s'identifie
            }

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
     *  Cette méthode vérifie si l'identifiant d'un professeur ou d'un élève existe déjà dans la base de données.
     *  Si l'identifiant existe, l'utilisateur correspondant devient l'utilisateur actif.
     *  Si l'identifiant n'existe pas, l'utilisateur est invité à se créer un compte.
     *  Si l'utilisateur décide de se créer un compte, il est redirigé vers la fonction de création de compte correspondant à son type (professeur ou élève).
     *  Si l'utilisateur décide de ne pas se créer de compte, la fonction retourne null.
     *  @param pseudo l'identifiant de l'utilisateur à vérifier
     *  @param type le type d'utilisateur à vérifier ("prof" ou "eleve")
     *  @return l'utilisateur actif ou null si l'utilisateur n'a pas choisi de se créer de compte
     */
    public static Utilisateur checkSession(String pseudo, String type) throws Exception {
        String inputUser = "";
        Utilisateur user = null;

        try { // vérification de si le prof ou l'élève existe déjà dans la BDD
            if (type.equals("prof")) {
                user = professeurDao.queryForId(pseudo);
            } else if (type.equals("eleve")) {
                user = eleveDao.queryForId(pseudo);
            }

            if (user != null) { // si l'utilisateur existe, il devient l'utilisateur actif
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        while (true) { // boucle pour demander si l'utilisateur veut créer un compte vu que son identifiant n'existe pas
            System.out.println(Ansi.ansi().fg(Ansi.Color.RED).a("\n/!\\ L'identifiant '" + pseudo + "' n'existe pas. Voulez-vous vous le créer ?\n").reset());
            System.out.println("1 : Oui");
            System.out.println("2 : Non");
            System.out.print("Création : ");
            inputUser = scannerInputUser.nextLine();

            if (inputUser.equals("1")) { // l'utilisateur veut créer un compte
                if (type.equals("prof")) { // si c'est un prof, on crée un prof
                    user = createProf(pseudo);
                } else { // si c'est un élève, on crée un élève
                    user = createEleve(pseudo);
                }
                return user; // l'utilisateur créé devient l'utilisateur actif
            }

            else if (inputUser.equals("2")) { // l'utilisateur ne veut pas créer de compte
                System.out.println(Ansi.ansi().fg(Ansi.Color.RED).a("\n/!\\ Vous ne pouvez pas utiliser l'application sans vous identifier.\n").reset());
                return null;
            }

            else { // le choix n'est pas reconnu
                System.out.println(Ansi.ansi().fg(Ansi.Color.RED).a("\n/!\\ Votre réponse ne convient pas.\n").reset());
            }
        }
    }

    /**
     *  Cette méthode permet de créer un professeur dans la base de données.
     *  L'utilisateur est invité à saisir la langue qu'il souhaite enseigner.
     *  Si la saisie de l'utilisateur est incorrecte ou n'est pas comprise dans les valeurs possibles, l'utilisateur est invité à ressaisir sa réponse.
     *  Si l'utilisateur entre la commande !quit, la méthode fermetureConnexion est appelée et la méthode actuelle retourne null.
     *  @param pseudo Le pseudo du professeur à créer
     *  @return L'objet Professeur créé
     */
    public static Professeur createProf(String pseudo) {
        Professeur prof = null;
        String inputUser = "";
        try {
            while (prof == null) { // tant que le prof n'est pas créé
                // choix de la langue enseignée (il ne peut enseigner qu'une seule langue)
                System.out.println("\nQuelle langue enseignez-vous ?");
                List<Langue> allLangues = Arrays.asList(Langue.values()); //affichage de la liste des langues disponibles
                for (int i = 0; i < allLangues.size(); i++) {
                    System.out.println((i + 1) + " : " + allLangues.get(i));
                }
                System.out.print("Langue choisie : ");
                inputUser = scannerInputUser.nextLine();

                if(inputUser.equals(QUIT_COMMAND)){
                    System.out.println(Ansi.ansi().fg(Ansi.Color.RED).a("\n/!\\ Votre identifiant ne sera pas créé.\n").reset());
                    fermetureConnexion();
                    return null;
                }

                int indexLangue = Integer.parseInt(inputUser) - 1;

                if (((indexLangue) < 0) || (indexLangue >= allLangues.size())) { // si le numéro n'est pas compris dans la liste des langues
                    System.out.println(Ansi.ansi().fg(Ansi.Color.RED).a("\n/!\\ Saisie invalide.\n").reset());
                } else { // si le numéro est bon, on crée le prof
                    prof = new Professeur(pseudo, allLangues.get(indexLangue));
                    professeurDao.create(prof);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return prof;
    }

    /**
     * Méthode permettant la création d'un nouvel élève et son inscription dans les cours de ses professeurs.
     * Si la saisie de l'utilisateur est incorrecte ou n'est pas comprise dans les valeurs possibles, l'utilisateur est invité à ressaisir sa réponse.
     * Si l'utilisateur entre la commande !quit, la méthode fermetureConnexion est appelée et la méthode actuelle retourne null.
     * @param pseudo le pseudo de l'élève
     * @return l'élève créé
     */
    public static Eleve createEleve(String pseudo) {
        Eleve eleve = null;
        String inputUser = "";
        try {
            eleve = new Eleve(pseudo);
            eleveDao.create(eleve);

            Boolean profsFini = false;
            do { // tant que l'élève n'a pas fini de rentrer tous ses profs
                List<Professeur> allProf = professeurDao.queryForAll(); // on affiche la liste des professeurs avec la langue qu'ils enseignent
                System.out.println("\nQui sont vos professeurs parmi ceux-ci ?\n" +
                        "NOTE : Entrez '0' quand vous avez rentré tous vos professeurs.\n");
                for (int i = 0; i < allProf.size(); i++) {
                    Professeur p = allProf.get(i);
                    System.out.println((i + 1) + " : " + p.getPseudo() + " - " + p.getLangue());
                }
                System.out.print("\nProfesseur choisi: ");
                inputUser = scannerInputUser.nextLine();

                if(inputUser.equals(QUIT_COMMAND)){
                    System.out.println(Ansi.ansi().fg(Ansi.Color.RED).a("\n/!\\ Votre compte est créé. Vous pourrez vous inscrire des cours plus tard.\n").reset());
                    fermetureConnexion();
                    return null;
                }

                int indexProf = Integer.parseInt(inputUser) - 1;

                // Si l'eleve a rentrée 0, il a fini de choisir ses professeurs
                if (indexProf == -1) {
                    profsFini = true;
                } else {
                    // Sinon, on vérifie que l'index donné est correct
                    if ((indexProf < 0) || (indexProf >= allProf.size())) {
                        System.out.println(Ansi.ansi().fg(Ansi.Color.RED).a("\n/!\\ Saisie invalide, index non compris.\n").reset());
                    } else { // si il est correct
                        // On inscrit l'élève dans le cours du professeur
                        Professeur prof = allProf.get(indexProf);
                        eleve.inscriptionLangue(prof, listNiveauxUtilisateur, niveauElevesDao);
                    }
                }
            } while (!profsFini);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return eleve;
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