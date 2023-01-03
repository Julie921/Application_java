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
    private static List<NiveauxEleves> listNiveauxUtilisateur = new ArrayList<>();

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

            //TODO : enlever car il existe déjà avec remplir
            // test pour changer la méthode de passage de niveau : juste une somme
            ParseurPhraseATrous parseurPhraseATrous = new ParseurPhraseATrous(); // parseur pour les exo à trous

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

                if (professeurSession) {
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
                                for (Exercice exo : listExercices) { // TODO : faire en sorte que ça affiche une preview plutôt
                                    exo.afficheExercice();
                                }
                            case "3": // le prof veut voir les notes de ses élèves
                                afficherResultats((Professeur) utilisateurActif);
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

                                ArrayList<Exercice> exercicesAccessibles = getExercicesAccessibles();

                                int i = 1;
                                for (Exercice exercice : exercicesAccessibles) {
                                    System.out.println("\n*********** EXERCICE " + i + " *********** ");
                                    exercice.previewText();
                                    i++;
                                }

                                System.out.println("\nNuméro de l'exercice choisi : ");
                                inputUser = scannerInputUser.nextLine();
                                if (inputUser.equals(QUIT_COMMAND)) {
                                    fermetureConnexion();
                                    break;
                                }

                                if (Integer.parseInt(inputUser) > exercicesAccessibles.size() || inputUser.equals("0")) {
                                    System.out.println("\nVotre réponse ne convient pas\n");
                                } else { // l'élève a choisi un exercice de la liste
                                    //TODO : faire la passation de niveaux
                                    Exercice exerciceChoisi = exercicesAccessibles.get(Integer.parseInt(inputUser) - 1);

                                    // construction de la réponse de l'exercice
                                    ReponseEleve reponseEleve = exerciceChoisi.construireReponse((Eleve) utilisateurActif);
                                    System.out.println("\nCorrection :\n");

                                    if (reponseEleve.valide()) { // l'élève a réussi l'exercice et gagne un point dans son score de la langue
                                        reponseEleve.affichePhrasesRempliesAvecCouleurs(parseurPhraseATrous.getReversedPattern());
                                        System.out.println("Félicitations, vous avez réussi l'exercice.");
                                        System.out.println("Vous deviez obtenir " + reponseEleve.getSeuilPassation() + " points pour valider et vous en avez obtenu " + reponseEleve.getNoteDonnee() + "!\n");
                                        updateScore(exerciceChoisi.getLangue(), 1F);
                                        updateNiveau(exerciceChoisi.getLangue());
                                    } else { // l'élève n'a pas réussi l'exercice
                                        reponseEleve.affichePhrasesRempliesAvecCouleurs(parseurPhraseATrous.getReversedPattern());
                                        System.out.println("Dommage, vous n'avez pas réussi l'exercice.");
                                        System.out.println("Vous deviez obtenir " + reponseEleve.getSeuilPassation() + " points pour valider et vous en avez obtenu " + reponseEleve.getNoteDonnee() + "...\n");
                                        updateScore(exerciceChoisi.getLangue(), -1F);
                                        updateNiveau(exerciceChoisi.getLangue());
                                    }
                                }
                                break;

                            case "2": // l'utilisateur veut voir ses résultats
                                afficherResultats((Eleve) utilisateurActif);
                                break;
                            case "3":
                                List<Professeur> allProf = professeurDao.queryForAll();
                                System.out.println("Dans quel cours voulez-vous vous inscrire ?");
                                for (i = 0; i < allProf.size(); i++) {
                                    Professeur p = allProf.get(i);
                                    System.out.println((i + 1) + ": " + p.getPseudo() + " - " + p.getLangue());
                                }
                                System.out.print("Professeur choisi: ");
                                inputUser = scannerInputUser.nextLine();
                                int indexProf = Integer.parseInt(inputUser) - 1;

                                // Si l'eleve a rentrée 0 dans sa donnée
                                // il a fini de choisir ses professeurs

                                // Sinon, on vérifie que l'index donné est correct
                                if ((indexProf < 0) || (indexProf >= allProf.size())) {
                                    System.out.println("Saisie invalide, index non compris.");
                                } else {
                                    inscriptionLangue((Eleve) utilisateurActif, allProf.get(indexProf).getPseudo());
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
                            ((Eleve) user).ajouterProf(allProf.get(indexProf));
                            inscriptionLangue((Eleve) user, allProf.get(indexProf).getPseudo());
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
     * Cette méthode permet de mettre à jour le score d'un élève dans une langue donnée.
     * Elle parcourt la liste des enregistrements de niveaux de l'utilisateur actif et si l'enregistrement concerne l'utilisateur actif et la langue donnée, le score de cet enregistrement est mis à jour avec la valeur donnée en paramètre.
     *
     * @param lang     la langue pour laquelle mettre à jour le score
     * @param addScore la valeur à ajouter au score actuel
     * @throws SQLException
     */
    public static void updateScore(Langue lang, Float addScore) throws SQLException {
        // Pour chaque enregistrement de niveau de l'utilisateur actif
        for (NiveauxEleves niv : listNiveauxUtilisateur) {
            // Si l'enregistrement concerne l'utilisateur actif et la langue donnée
            if (niv.getPseudoEleve().equals(utilisateurActif.getPseudo()) && niv.getLangue() == lang) {
                // Mise à jour du score de l'enregistrement
                niv.setScore(niv.getScore() + addScore);
                niveauElevesDao.update(niv); // actualisation dans la base de données concrètement
            }
        }
    }

    /**
     * Cette méthode met à jour le niveau de l'utilisateur actif (un élève) dans une langue donnée, en fonction de son score dans cette langue.
     * Si le score est compris entre 20 et 40, le niveau est défini comme "intermédiaire".
     * Si le score est compris entre 40 et 60, le niveau est défini comme "avancé".
     * Si le score est supérieur à 60, le niveau est défini comme "expert".
     *
     * @param lang la langue pour laquelle mettre à jour le niveau de l'utilisateur actif
     * @throws SQLException
     */
    public static void updateNiveau(Langue lang) throws SQLException {
        for (NiveauxEleves niv : listNiveauxUtilisateur) {
            if (niv.getPseudoEleve().equals(utilisateurActif.getPseudo()) && niv.getLangue() == lang) {
                if (niv.getScore() < 20) {
                    niv.setNiveau(BaremeNiveau.DEBUTANT);
                } else if (niv.getScore() >= 20 && niv.getScore() < 40) {
                    niv.setNiveau(BaremeNiveau.INTERMEDIAIRE);
                } else if (niv.getScore() >= 40 && niv.getScore() < 60) {
                    niv.setNiveau(BaremeNiveau.AVANCE);
                } else if (niv.getScore() >= 60) {
                    niv.setNiveau(BaremeNiveau.EXPERT);
                }
                niveauElevesDao.update(niv);
            }
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
        listNiveauxUtilisateur = niveauElevesDao.queryForAll();
    }

    /**
     * Cette méthode retourne la liste des exercices auxquels l'utilisateur actif (un élève) a accès.
     * Un élève a accès à un exercice s'il est inscrit dans la langue de l'exercice et que le niveau de l'exercice correspond à son niveau dans cette langue.
     *
     * @return la liste des exercices auxquels l'utilisateur actif a accès
     */
    public static ArrayList<Exercice> getExercicesAccessibles() {
        ArrayList<Exercice> exercicesAccessibles = new ArrayList<>();
        // Pour chaque enregistrement de niveau de l'utilisateur actif
        for (NiveauxEleves niveauEleve : listNiveauxUtilisateur) {
            // Si l'enregistrement concerne l'utilisateur actif
            if (niveauEleve.getPseudoEleve().equals(utilisateurActif.getPseudo())) {
                // Pour chaque exercice de la liste
                for (Exercice exercice : listExercices) {
                    // Si l'exercice a la même langue et le même niveau que l'enregistrement de l'utilisateur actif, on l'ajoute à la liste des exercices accessibles
                    if (exercice.getLangue().equals(niveauEleve.getLangue()) && exercice.getNiveau().equals(niveauEleve.getNiveau())) {
                        System.out.println(niveauEleve.getNiveau());
                        exercicesAccessibles.add(exercice);
                    }
                }
            }
        }
        return exercicesAccessibles;
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

    /**
     * Cette méthode permet à un élève de s'inscrire dans une nouvelle langue.
     * Si l'élève n'est pas déjà inscrit dans la langue, un enregistrement de NiveauxEleves est créé avec le niveau "débutant" et le score initialisé à 0.
     * Si l'élève est déjà inscrit dans la langue, la méthode ne fait rien.
     *
     * @param pseudoProf le pseudo du professeur enseignant la langue dans laquelle l'élève souhaite s'inscrire
     */
    public static void inscriptionLangue(Eleve user, String pseudoProf) throws SQLException {
        // On vérifie si l'élève est déjà inscrit dans la langue
        boolean estInscrit = false;
        for (NiveauxEleves niveau : listNiveauxUtilisateur) {
            if (niveau.getPseudoEleve().equals(user.getPseudo()) && niveau.getLangue().equals(listProfs.get(pseudoProf).getLangue())) {
                estInscrit = true;
                System.out.println("Vous êtes déjà inscrit dans un cours de cette langue.\n");
                break;
            }
        }
        // Si l'élève n'est pas inscrit dans la langue, on crée un enregistrement de NiveauxEleves pour l'inscrire dans cette langue
        if (!estInscrit) {
            System.out.println("Inscription réussie.");
            user.ajouterProf(listProfs.get(pseudoProf));
            NiveauxEleves niveau = new NiveauxEleves(user, listProfs.get(pseudoProf));
            listNiveauxUtilisateur.add(niveau);
            niveauElevesDao.create(niveau);
        }
    }

    /**
     *  Affiche les résultats de l'utilisateur spécifié en paramètre. Si l'utilisateur est un {@link Professeur}, ses résultats sont les résultats de tous ses élèves. Si c'est un {@link Eleve}, ses résultats sont ses résultats dans tous les cours auxquels il est inscrit, avec le nom du professeur enseignant chaque cours.
     *  Si l'utilisateur n'a pas d'élèves ou n'est pas inscrit dans un cours, un message d'information est affiché à cet effet.
     */
    public static void afficherResultats(Utilisateur utilisateur) {
        if (utilisateur instanceof Professeur) {
            // On vérifie s'il y a des élèves inscrits dans la langue enseignée par le professeur
            boolean aDesEleves = false;
            for (NiveauxEleves niv : listNiveauxUtilisateur) {
                if (niv.getPseudoProfesseur().equals(utilisateur.getPseudo())) {
                    aDesEleves = true;
                    break;
                }
            }
            // Si le professeur a des élèves, on affiche leurs résultats
            if (aDesEleves) {
                System.out.println("Résultats de vos élèves :");
                for (NiveauxEleves niv : listNiveauxUtilisateur) {
                    if (niv.getPseudoProfesseur().equals(utilisateur.getPseudo())) {
                        System.out.println("\n" + niv.getPseudoEleve() + " :\n" +
                                "- " + niv.getNiveau() + "\n" + // le niveau dans la langue
                                "- " + niv.getScore()); // le score dans la langue
                    }
                }
            } else {
                System.out.println("Vous n'avez pas encore d'élèves inscrits dans votre cours.\n");
            }
        } else if (utilisateur instanceof Eleve) {
            // On vérifie s'il y a des cours dans lesquels l'élève est inscrit
            boolean estInscrit = false;
            for (NiveauxEleves niv : listNiveauxUtilisateur) {
                if (niv.getPseudoEleve().equals(utilisateur.getPseudo())) {
                    estInscrit = true;
                    break;
                }
            }
            if (estInscrit) {
                System.out.println("Bulletin :\n");
                for (NiveauxEleves niv : listNiveauxUtilisateur) {
                    if (niv.getPseudoEleve().equals(utilisateur.getPseudo())) {
                        System.out.println("\n" + niv.getLangue() + " (" + niv.getPseudoProfesseur() + ") :\n" +
                                "- " + niv.getNiveau() + "\n" + // le niveau dans la langue
                                "- " + niv.getScore() + "\n"); // le score dans la langue
                    }
                }
            } else {
                System.out.println("Vous n'êtes pas encore inscrit dans un cours.\n");
            }
        }
    }
}