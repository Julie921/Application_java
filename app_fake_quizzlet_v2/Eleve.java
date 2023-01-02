package app_fake_quizzlet_v2;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.HashMap;
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
}