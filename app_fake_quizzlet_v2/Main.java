package app_fake_quizzlet_v2;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.logger.LogBackendType;
import com.j256.ormlite.logger.LoggerFactory;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    private final static String DATABASE_URL = "jdbc:h2:file:./database.db";

    private static ConnectionSource connectionSource = null;
    private static Dao<Professeur, String> professeurDao;
    private static Dao<Eleve, String> eleveDao;
    private static Dao<NiveauxEleves, Integer> niveauElevesDao;

    public static Scanner scannerInputUser = new Scanner(System.in); //pour récupérer les réponses de l'utilisateur

    private static String QUIT_COMMAND = "!quit"; // pour quitter l'application

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

            /*Eleve eleve = eleveDao.queryForId("Den");

            ParseurPhraseATrous parseurPhraseATrous = new ParseurPhraseATrous(); // parseur pour les exo à trous

            ExoATrous exercice1 = new ExoATrous(Langue.FR, BaremeNiveau.AVANCE, parseurPhraseATrous, "En été je porte tous les jours un t-shirt en #coton#, un short et des sandales. Tiens, je vais te donner un sac en #plastique# pour mettre tout ça. Mémé m'a acheté un magnifique pull en #laine# en Irlande. Je voudrais m'acheter une veste en #cuir# mais je n'ai pas assez d'argent. \n" +
                    "Il faut que je travaille pour gagner des #sous#. Sinon, je ne pourrais pas offrir de #cadeaux# aux autres.");

            // on récupère les réponses de l'élève avec des input
            ReponseEleveExoATrous reponse1 = new ReponseEleveExoATrous(exercice1, eleve);

            System.out.println(reponse1.getReponses());

            // on crée un objet qui contiendra la correction de ses réponses
            CorrectionExoATrous correction1 = new CorrectionExoATrous();

            // on corrige
            correction1.corrige(reponse1);


            System.out.println(correction1.getListValeursReponses());*/


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

            System.out.println("\n\nIt seems to have worked\n\n");

            Boolean professeurSession = false, studentSession = false;
            String inputUser = "";
            Utilisateur utilisateur_principal = null;

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

            NiveauxEleves dunFR = niveauElevesDao.queryForId(1);
            dunFR.addScore(10.5F);
            niveauElevesDao.update(dunFR);





            System.out.println("---");
            do {
                System.out.println("Bonjour "+ utilisateur_principal.getPseudo());

                if (professeurSession) {
                    Boolean choixActionProf1 = false, choixActionProf2 = false, choixActionProf3 = false;
                    do {
                        System.out.println("Que voulez-vous faire ?");
                        System.out.println("" +
                                "1 : Ecrire un exercice\n" +
                                "2 : Modifier un exercice\n" +
                                "3 : Voir notes des élèves");
                        System.out.print("Votre réponse: ");
                        inputUser = scannerInputUser.nextLine();

                        // Choix Professeur
                        choixActionProf1 = inputUser.equals("1");
                        choixActionProf2 = inputUser.equals("2");
                        choixActionProf3 = inputUser.equals("3");

                        if (!choixActionProf1 && !choixActionProf2 && !choixActionProf3 && !inputUser.equals(QUIT_COMMAND)) {
                            System.out.println("Votre réponse ne convient pas\n----");
                        }

                    } while (!inputUser.equals(QUIT_COMMAND) && !choixActionProf1 && !choixActionProf2 && !choixActionProf3);

                } else if (studentSession) {
                    Boolean choixEleve1 = false, choixEleve2 = false;
                    do {
                        System.out.println("Que voulez-vous faire ?");
                        System.out.println("" +
                                "1 : Choisir un exercice\n" +
                                "2 : Voir mon historique");

                        System.out.print("Votre réponse: ");
                        inputUser = scannerInputUser.nextLine();

                        // Choix Eleve
                        choixEleve1 = inputUser.equals("1");
                        choixEleve2 = inputUser.equals("2");
                        if (!choixEleve1 && !choixEleve2 && !inputUser.equals(QUIT_COMMAND)) {
                            System.out.println("Votre réponse ne convient pas\n----");
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
                System.out.println("Votre réponse ne convient pas\n----");
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
}

