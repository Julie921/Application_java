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
    private static Utilisateur utilisateur_principal = null;

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


            // test pour changer la méthode de passage de niveau : juste une somme
            ParseurPhraseATrous parseurPhraseATrous = new ParseurPhraseATrous(); // parseur pour les exo à trous




            ///////////////////////////////////////////////////////////////////////


            // Essai sur les databases
            //Professeur account2 = professeurDao.queryForId("Jean");
            //System.out.println("Essai: " + account2.getPseudo());

            /*Eleve julie = new Eleve("Julie",BaremeNiveau.INTERMEDIAIRE,account2);
            Eleve olive = new Eleve("Olive",BaremeNiveau.DEBUTANT,account2);
            Eleve sarah = new Eleve("Sarah",BaremeNiveau.EXPERT,account2);
            Eleve louis = new Eleve("Louis",BaremeNiveau.AVANCE,account2);*/

            /*Eleve essai2 = eleveDao.queryForId("Den");
            essai2.updateMoyenne(15);
            eleveDao.update(essai2);*/
           //System.out.println(essai2);
            // account2.listElevesToString();

            createFromDatabase();
            remplirListeParseurs();
            importExercice = new ImportExercice(listParseurs);
            listExercices = importExercice.importDossier(RESSOURCES_FOLDER);

            System.out.println("\n\nIt seems to have worked\n\n");

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
                System.out.println("Quel est votre identifiant ?");
                System.out.print("Idenfitiant/pseudo: ");
                inputUser = scannerInputUser.nextLine();

                if (inputUser.equals(QUIT_COMMAND))
                    break;

                if (professeurSession) {
                    utilisateur_principal = checkSession(inputUser, "prof");
                } else if (studentSession) {
                    utilisateur_principal = checkSession(inputUser, "eleve");
                }

            } while (utilisateur_principal == null);

            if (utilisateur_principal == null) {
                fermetureConnexion();
                return;
            }

            System.out.println("//-------------------------------------------------//");
            do {
                System.out.println("Bonjour "+ utilisateur_principal.getPseudo());

                if (professeurSession) {
                    Boolean choixActionProf1 = false, choixActionProf2 = false, choixActionProf3 = false;
                    do {
                        System.out.println("Que voulez-vous faire ?");
                        System.out.println("" +
                                "1 : Importer un exercice\n" +
                                "2 : Voir les exercices sauvegardés\n" +
                                "3 : Voir notes des élèves");
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

                    while(!inputUser.equals(QUIT_COMMAND)) {
                        // TODO compléter les cases
                        switch (inputUser) {
                            case "1":
                                System.out.println("Renseignez le nom du fichier");
                                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                                int res = fileChooser.showOpenDialog(null);
                                if (res == JFileChooser.APPROVE_OPTION){
                                    listExercices.add(importExercice.readFromFile(fileChooser.getSelectedFile()));
                                }
                                break;
                            default:
                                System.out.println("Rien");
                                break;
                        }
                        for (Exercice exo: listExercices) {
                            exo.afficheExercice();
                        }
                        System.out.print("Que voulez-vous faire ? ");
                        inputUser = scannerInputUser.nextLine();
                    }

                } else if (studentSession) {
                    Boolean choixEleve1 = false, choixEleve2 = false;
                    do {
                        System.out.println("Que voulez-vous faire ?");
                        System.out.println("" +
                                "1 : Faire un exercice\n" +
                                "2 : Voir mes résultats");

                        System.out.print("Votre réponse: ");
                        inputUser = scannerInputUser.nextLine();

                        // Choix Eleve
                        choixEleve1 = inputUser.equals("1");
                        choixEleve2 = inputUser.equals("2");
                        if (!choixEleve1 && !choixEleve2 && !inputUser.equals(QUIT_COMMAND)) {
                            System.out.println("Votre réponse ne convient pas\n//-------------------------------------------------//");
                        }
                        switch (inputUser){
                            case "1":
                                //TODO : faire une preview de l'exercice pour pouvoir l'afficher
                                ExoATrous exercice1 = new ExoATrous(Langue.FR, BaremeNiveau.INTERMEDIAIRE, 0.5F, parseurPhraseATrous, "Ceci est un #test#.");

                                Eleve eleve = eleveDao.queryForId("doun");

                                // on récupère les réponses de l'élève avec des input
                                ReponseEleveExoATrous reponse1 = new ReponseEleveExoATrous(exercice1, eleve);
                                System.out.println("Les réponses fournies:\n" + reponse1.getReponsesFournies());
                                System.out.println("Les réponses corrigees:\n" + reponse1.getReponsesCorrection());
                                System.out.println("La note:\n" + reponse1.getNoteDonnee());
                                System.out.println("Eleve passe:\n" + reponse1.valide());
                                if(reponse1.valide()){
                                    updateScore(reponse1.getExercice().getLangue(), 2F);
                                    System.out.println("Le score a été updaté");
                                break;
                                }
                            case "2": // l'utilisateur veut voir ses résultats
                                for(NiveauxEleves niv: listNiveauxUtilisateur){ // pour chaque inscription dans la table NiveauxEleves
                                    if(niv.getEleve().equals(utilisateur_principal.getPseudo())){ // si l'inscription correspond à l'utilisateur principal
                                    System.out.println("\n" + niv.getLangue() + " :\n" + // la langue
                                            "- " + niv.getNiveau() + "\n" + // le niveau dans la langue
                                            "- " + niv.getScore()); // le score dans la langue
                                }
                                }
                            default:
                                System.out.println("Rien");
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

    private static void remplirListeParseurs() {
        listParseurs.put(TypeExo.EXO_A_TROU, new ParseurPhraseATrous());
        listParseurs.put(TypeExo.EXO_TERMINAISON, new ParseurTerminaison());
        // Rajouter nouveaux parseurs ici si on veut créer d'autres types d'exercices
    }

    public void scenarioEleve(){

    }

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
                        System.out.println((i+1) + ": " + allLangues.get(i));
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
                    ArrayList<Professeur> listProfEleve = new ArrayList<>();
                    do {
                        List<Professeur> allProf = professeurDao.queryForAll();
                        System.out.println("Qui sont vos professeurs parmi ceux-ci ?\n" +
                                "Note: Rentrer la valeur 0 lorsque vous avez fini de choisir tous vos professeurs.");
                        for (int i = 0; i < allProf.size(); i++) {
                            Professeur p = allProf.get(i);
                            if (!listProfEleve.contains(p)) {
                                System.out.println((i+1) + ": " + p.getPseudo());
                            }
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
                                System.out.println("Saisie invalide, index non compris");
                                return null;
                            }
                            // et on l'ajoute dans la liste des profs de l'eleve.
                            listProfEleve.add(allProf.get(indexProf));
                            ((Eleve) user).ajouterProf(allProf.get(indexProf));
                            NiveauxEleves niv = new NiveauxEleves((Eleve) user, allProf.get(indexProf));
                            listNiveauxUtilisateur.add(niv);
                            niveauElevesDao.create(niv);
                        }
                    } while(!profsFini);
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

    public static void fermetureConnexion() throws Exception {
        System.out.println("Fermeture de l'application");
        if (connectionSource != null) {
            connectionSource.close();
        }
    }

    // Méthode qui permet d'updater le score dans l'objet NiveauxEleve et dans la Base de Données
    public static void updateScore(Langue lang, Float addScore) throws SQLException { //TODO : mettre la méthode autre part
        for(NiveauxEleves niv : listNiveauxUtilisateur) {
            if (niv.getEleve().equals(utilisateur_principal.getPseudo()) && niv.getLangue() == lang) {
                niv.setScore(niv.getScore()+addScore);
                niveauElevesDao.update(niv);
            }
        }
    }

    // Méthode qui re-créée les objets à partir de la base de données.
    public static void createFromDatabase() throws SQLException {
        System.out.println("----- CREATION A PARTIR DE LA BASE DE DONNEES -----");
        // build a query that returns all Foo objects where the `name` field starts with "A"
        List<Professeur> results = professeurDao.queryForAll();
        for (Professeur p : results) {
            listProfs.put(p.getPseudo(), p);
        }
        System.out.println(listProfs);

        List<Eleve> resultsEleves = eleveDao.queryForAll();
        for (Eleve e : resultsEleves) {
            listEleves.put(e.getPseudo(), e);
        }
        System.out.println(listEleves);

        listNiveauxUtilisateur = niveauElevesDao.queryForAll();
        System.out.println(listNiveauxUtilisateur);
        System.out.println("----- FIN DE LA CREATION A PARTIR DE LA BASE DE DONNEES -----");
    }
}

