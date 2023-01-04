package app_fake_quizzlet_v2;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTable;
import org.fusesource.jansi.Ansi;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe représentant un élève dans l'application Fake Quizlet qui permet de faire des exercices de langue.
 *
 * Un élève est un utilisateur de l'application qui peut s'inscrire auprès de différents professeurs et participer à leurs quizz.
 *
 * Un élève a un niveau ({@link BaremeNiveau}) dans chaque langue enseignée par ses {@link Professeur}. Ce niveau détermine le nombre de points qu'il peut obtenir pour chaque réponse juste, fausse ou non répondue lorsqu'il fait un exercice.
 *
 * L'élève peut monter ou descendre d'un niveau dans une langue donnée en réussissant ou en ratant des exercices. Ce passage de niveau est réalisé par rapport au score qu'il a dans la langue.
 *
 * @see BaremeNiveau
 * @see Professeur
 */
@DatabaseTable(tableName = "ELEVES")
public class Eleve extends Utilisateur { //TODO association réflexive pour que le prof soit associé à chaque élève

	/**
	 * Nom de la colonne de la base de données pour l'identifiant du professeur.
	 */
	public static final String PROFESSOR_ID_FIELD_NAME = "professeur_id";

	/**
	 * Map associant à chaque langue enseignée par ses professeurs le niveau de l'élève dans cette langue.
	 */
	private Map<Langue, BaremeNiveau> niveaux = new HashMap<>();

	/**
	 * Liste des professeurs auprès desquels l'élève est inscrit.
	 */
	private ArrayList<Professeur> listProfesseurs = new ArrayList<>();

	public Historique historiqueEleve = new Historique();

	/*@DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = PROFESSOR_ID_FIELD_NAME)
	private Professeur prof;*/

	/**
	 * Constructeur vide nécessaire pour l'utilisation d'ORMlite.
	 */
	public Eleve() {
		super();
	}

	/**
	 * Constructeur permettant de créer un élève avec un pseudo donné.
	 *
	 * @param pseudo pseudo de l'élève
	 */
	public Eleve(String pseudo) {
		super(pseudo);
	}

	/**
	 * Constructeur permettant de créer un élève avec un pseudo et une liste de professeurs donnés.
	 *
	 * @param pseudo pseudo de l'élève
	 * @param profs liste des professeurs auprès desquels l'élève est inscrit
	 */
	public Eleve(String pseudo, ArrayList<Professeur> profs) {
		super(pseudo);
		this.listProfesseurs = profs;
		for (Professeur prof: profs) {
			this.niveaux.put(prof.getLangue(), BaremeNiveau.DEBUTANT);
			//prof.ajouterEleve(this);
		}
	}

	/**
	 * Méthode permettant à un élève de s'inscrire auprès d'un professeur.
	 *
	 * @param prof professeur auprès duquel l'élève s'inscrit
	 */
	public void ajouterProf(Professeur prof) {
		this.listProfesseurs.add(prof);
		this.niveaux.put(prof.getLangue(), BaremeNiveau.DEBUTANT);
	}

	/**
	 * Méthode permettant de récupérer le niveau de l'élève dans une langue donnée.
	 *
	 * @param langue langue pour laquelle on souhaite récupérer le niveau de l'élève
	 * @return niveau de l'élève dans la langue donnée
	 */
	public BaremeNiveau getBaremeNiveau(Langue langue){
		return this.niveaux.get(langue);
	}

	/**
	 * Méthode permettant de modifier le niveau de l'élève dans une langue donnée.
	 *
	 * @param niveau nouveau niveau de l'élève dans la langue donnée
	 * @param langue langue pour laquelle on souhaite modifier le niveau de l'élève
	 */
	public void setNiveau(BaremeNiveau niveau, Langue langue) {
		this.niveaux.put(langue, niveau);
	}

	public void addEntryHistorique(Exercice exercice, Float note, ReponseEleve reponseEleve, BaremeNiveau niveau){
		historiqueEleve.addEntry(exercice, note, reponseEleve, niveau);
	}

	public Map<String, Object> getEntry(int index){
		return historiqueEleve.getEntry(index); //afficher seulement une partie du tableau au lieu d'afficher les références
	}

	public void afficheHistorique(){

		System.out.printf("--------------------------------%n");
		System.out.printf("    HISTORIQUE      %n");
		System.out.printf("--------------------------------%n");
		System.out.printf("--------------------------------%n");
		System.out.printf("| %-10s | %-8s | %4s | %4s |%n", "EXERCICE", "REPONSE", "NOTE", "NIVEAU");
		System.out.printf("--------------------------------%n");

		for(Map entry: historiqueEleve.getData()){
			System.out.printf("| %h | %s | %f | %h |%n", entry.get("exercice").toString(), entry.get("reponse"),  entry.get("note"), entry.get("niveau")); //%b et toString()
		}
	}

	@Override
	public String toString() {
		return "Eleve{" +
				"nom élève = " + getPseudo() +
				", niveaux=" + niveaux +
				", historiqueEleve=" + historiqueEleve +
				", profs=" + listProfesseurs +
				'}';
	}

	@Override
	public ArrayList<Exercice> getExercicesAccessibles(ArrayList<NiveauxEleves> listNiveauxUtilisateur, List<Exercice> listExercices) {
		ArrayList<Exercice> exercicesAccessibles = new ArrayList<>();
		// Pour chaque enregistrement de niveau de l'utilisateur actif
		for (NiveauxEleves niveauEleve : listNiveauxUtilisateur) {
			// Si l'enregistrement concerne l'utilisateur actif
			if (niveauEleve.getPseudoEleve().equals(this.getPseudo())) {
				// Pour chaque exercice de la liste
				for (Exercice exercice : listExercices) {
					// Si l'exercice a la même langue et le même niveau que l'enregistrement de l'utilisateur actif, on l'ajoute à la liste des exercices accessibles
					if (exercice.getLangue().equals(niveauEleve.getLangue()) && exercice.getNiveau().equals(niveauEleve.getNiveau())) {
						exercicesAccessibles.add(exercice);
					}
				}
			}
		}
		if(exercicesAccessibles.isEmpty()){
			System.out.println("Il n'y a encore aucun exercice accessible pour vos langues et votre niveau.");
		}
		return exercicesAccessibles;
	}

	/**
	 *  Affiche les résultats de l'élève, c'est-à-dire la liste de ses cours et ses scores pour chacun d'eux.
	 *  Si l'élève n'est inscrit dans aucun cours, affiche un message indiquant qu'il n'est pas encore inscrit dans un cours.
	 *  @param listNiveauxUtilisateur la liste de tous les niveaux de tous les élèves
	 */
	public void afficheResultats(ArrayList<NiveauxEleves> listNiveauxUtilisateur){
		// On vérifie s'il y a des cours dans lesquels l'élève est inscrit
		boolean estInscrit = false;
		for (NiveauxEleves niv : listNiveauxUtilisateur) {
			if (niv.getPseudoEleve().equals(this.getPseudo())) {
				estInscrit = true;
				break;
			}
		}
		if (estInscrit) {
			System.out.println("+--------------+----------------+------------+-----------+");
			System.out.println("|   Langue     |    Professeur  |  Niveau    | Score  |");
			System.out.println("+--------------+----------------+------------+-----------+");
			for (NiveauxEleves niv : listNiveauxUtilisateur) {
				if(niv.getPseudoEleve().equals(this.getPseudo())){
					String langue = String.format("| %-12s ", niv.getLangue());
					String prof = String.format("| %-14s ", niv.getPseudoProfesseur());
					String niveau = String.format("| %-11s ", niv.getNiveau());
					String score = String.format("| %-11s |", niv.getScore());
					System.out.println(langue + prof + niveau + score);
				}
			}
			System.out.println("+--------------+----------------+------------+-----------+");
		} else {
			System.out.println("Vous n'êtes pas encore inscrit dans un cours.\n");
		}

	}

	/**
	 *  Cette méthode permet à un élève de s'inscrire à un cours de langue avec un professeur donné.
	 *  Si l'élève est déjà inscrit dans un cours de cette langue, avec un autre professeur ou le même professeur,
	 *  une notification est affichée à l'écran.
	 *  Sinon, un enregistrement de {@link NiveauxEleves} est créé pour enregistrer l'inscription de l'élève dans ce cours de langue,
	 *  et une notification de réussite de l'inscription est affichée.
	 *  @param professeur le professeur avec lequel l'élève souhaite s'inscrire
	 *  @param listNiveauxUtilisateur la liste des enregistrements de niveaux des élèves
	 *  @param niveauElevesDao l'objet Dao pour accéder à la table des enregistrements de niveaux des élèves dans la base de données
	 *  @throws SQLException si une erreur SQL se produit lors de l'accès à la base de données
	 */
	public void inscriptionLangue(Professeur professeur, ArrayList<NiveauxEleves> listNiveauxUtilisateur, Dao niveauElevesDao) throws SQLException {
		// On vérifie si l'élève est déjà inscrit dans la langue (que ça soit avec un autre prof ou le même prof)
		boolean estInscrit = false;
		for (NiveauxEleves niveau : listNiveauxUtilisateur) {
			if (niveau.getPseudoEleve().equals(this.getPseudo()) && niveau.getLangue().equals(professeur.getLangue())) {
				estInscrit = true;
				System.out.println(Ansi.ansi().fg(Ansi.Color.RED).a("\nVous êtes déjà inscrit dans un cours de cette langue.\n").reset());
				break;
			}
		}
		// Si l'élève n'est pas inscrit dans la langue, on crée un enregistrement de NiveauxEleves pour l'inscrire dans cette langue
		if (!estInscrit) {
			this.ajouterProf(professeur);
			NiveauxEleves niveau = new NiveauxEleves(this, professeur);
			listNiveauxUtilisateur.add(niveau);
			niveauElevesDao.create(niveau);
			System.out.println(Ansi.ansi().fg(Ansi.Color.BLUE).a("\nInscription réussie.\n").reset());

		}
	}

}