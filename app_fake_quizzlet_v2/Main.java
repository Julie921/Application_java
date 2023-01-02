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
            System.out.println("Bonjour "+ utilisateurActif.getPseudo() + "!");

            do {

                if (professeurSession) {
                    Boolean choixActionProf1 = false, choixActionProf2 = false, choixActionProf3 = false;
                    do {
                        System.out.println("//-------------------------------------------------//\n");
                        System.out.println("Que voulez-vous faire ?");
                        System.out.println("" +
                                "1 : Importer un exercice\n" +
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

                    /*while(!inputUser.equals(QUIT_COMMAND)) {
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
                            case "2":
                                for (Exercice exo: listExercices) { // TODO : faire en sorte que ça affiche une preview plutôt
                                    exo.afficheExercice();
                                }
                            case "3": // le prof veut voir les notes de ses élèves
                                for(NiveauxEleves niv: listNiveauxUtilisateur){ // pour chaque inscription dans la table NiveauxEleves
                                    if(niv.getProfesseur().equals(utilisateur_principal.getPseudo())){ // si l'élève a comme prof le prof qui utilise l'application
                                        System.out.println("\n" + niv.getEleve() + " :\n" + // le nom de l'élève
                                                "- " + niv.getNiveau() + "\n" + // le niveau dans la langue
                                                "- " + niv.getScore()); // le score dans la langue
                                    }
                                }
                            default:
                                System.out.println("Rien");
                                break;
                            }
                        }
                        System.out.print("Que voulez-vous faire ? ");
                        inputUser = scannerInputUser.nextLine();
                    }*/

                } else if (studentSession) {
                    Boolean choixEleve1 = false, choixEleve2 = false;
                    do {
                        System.out.println("//-------------------------------------------------//");
                        System.out.println("Que voulez-vous faire ?");
                        System.out.println("" +
                                "1 : Faire un exercice\n" +
                                "2 : Voir mes résultats");

                        System.out.print("Votre réponse: ");
                        inputUser = scannerInputUser.nextLine();

                        // Choix Eleve
                        choixEleve1 = inputUser.equals("1");
                        choixEleve2 = inputUser.equals("2");
                        /*if (!choixEleve1 && !choixEleve2 && !inputUser.equals(QUIT_COMMAND)) {
                            System.out.println("Votre réponse ne convient pas\n//-------------------------------------------------//");
                        }*/
                        switch (inputUser){
                            case "1": // l'élève veut faire un exercice

                                System.out.println("Voici une preview de chaque exercice acessible pour vos langues et votre niveau dans ces langues.");

                                ArrayList<Exercice> exercicesAccessibles = getExercicesAccessibles();

                                int i = 1;
                                for(Exercice exercice : exercicesAccessibles) {
                                    System.out.println("\n*********** EXERCICE " + i + " *********** ");
                                    exercice.previewText();
                                    i++;
                                }

                                System.out.println("\nNuméro de l'exercice choisi : ");
                                inputUser = scannerInputUser.nextLine();
                                if(inputUser.equals(QUIT_COMMAND)){
                                    fermetureConnexion();
                                    break;
                                }

                                if(Integer.parseInt(inputUser)>listExercices.size() || inputUser.equals("0")) {
                                    System.out.println("\nVotre réponse ne convient pas\n");
                                }
                                else{ // l'élève a choisi un exercice de la liste
                                    //TODO : faire la passation de niveaux
                                    Exercice exerciceChoisi = listExercices.get(Integer.parseInt(inputUser)-1);

                                    // construction de la réponse de l'exercice
                                    ReponseEleve reponseEleve = exerciceChoisi.construireReponse((Eleve) utilisateurActif);
                                    System.out.println("\nCorrection :\n");

                                    if(reponseEleve.valide()){ // l'élève a réussi l'exercice et gagne un point dans son score de la langue
                                        reponseEleve.affichePhrasesRempliesAvecCouleurs(parseurPhraseATrous.getReversedPattern());
                                        System.out.println("Félicitations, vous avez réussi l'exercice.");
                                        System.out.println("Vous deviez obtenir " + reponseEleve.getSeuilPassation() + " points pour valider et vous en avez obtenu " + reponseEleve.getNoteDonnee() + "!\n");
                                        updateScore(exerciceChoisi.getLangue(), 1F);
                                    }
                                    else{ // l'élève n'a pas réussi l'exercice
                                        reponseEleve.affichePhrasesRempliesAvecCouleurs(parseurPhraseATrous.getReversedPattern());
                                        System.out.println("Dommage, vous n'avez pas réussi l'exercice.");
                                        System.out.println("Vous deviez obtenir " + reponseEleve.getSeuilPassation() + " points pour valider et vous en avez obtenu " + reponseEleve.getNoteDonnee() + "...\n");
                                        updateScore(exerciceChoisi.getLangue(), -1F);
                                    }
                                }
                            break;

                            case "2": // l'utilisateur veut voir ses résultats
                                for(NiveauxEleves niv: listNiveauxUtilisateur){ // pour chaque inscription dans la table NiveauxEleves
                                    if(niv.getPseudoEleve().equals(utilisateurActif.getPseudo())){ // si l'inscription correspond à l'utilisateur principal
                                    System.out.println("\n" + niv.getLangue() + " :\n" + // la langue
                                            "- " + niv.getNiveau() + "\n" + // le niveau dans la langue
                                            "- " + niv.getScore()); // le score dans la langue
                                }
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
        System.out.println("\n//-------------------------------------------------//");
        System.out.println("Fermeture de l'application");
        System.out.println("//-------------------------------------------------//");
        if (connectionSource != null) {
            connectionSource.close();
        }
    }

    // Méthode qui permet d'updater le score dans l'objet NiveauxEleve et dans la Base de Données
    public static void updateScore(Langue lang, Float addScore) throws SQLException { //TODO : mettre la méthode autre part
        for(NiveauxEleves niv : listNiveauxUtilisateur) {
            if (niv.getPseudoEleve().equals(utilisateurActif.getPseudo()) && niv.getLangue() == lang) {
                niv.setScore(niv.getScore()+addScore);
                niveauElevesDao.update(niv);
            }
        }
    }

    public static void updateNiveau(Langue lang, BaremeNiveau newNiveau) throws SQLException { //TODO : mettre la méthode autre part
        for(NiveauxEleves niv : listNiveauxUtilisateur) {
            if (niv.getPseudoEleve().equals(utilisateurActif.getPseudo()) && niv.getLangue() == lang) {
                niv.setNiveau(newNiveau);
                niveauElevesDao.update(niv);
            }
        }
    }

    /**
     * Méthode statique qui re-crée les objets à partir de la base de données.
     * Cette méthode récupère les enregistrements de la table Professeur et Eleve de la base de données et les ajoute respectivement aux listes {@link #listProfs} et {@link #listEleves}.
     * Elle récupère également les enregistrements de la table NiveauEleve et les ajoute à la liste {@link #listNiveauxUtilisateur}.
     * Il est important de noter que cette méthode ne doit être utilisée qu'à l'initialisation de l'application, lorsque la base de données est vide et qu'il faut remplir la base de données avec des objets "vides" (sans élèves ni exercices). Si cette méthode est utilisée alors que la base de données contient déjà des informations, celles-ci seront écrasées par les objets "vides" créés par cette méthode.
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
                        exercicesAccessibles.add(exercice);
                    }
                }
            }
        }
        return exercicesAccessibles;
    }
}

