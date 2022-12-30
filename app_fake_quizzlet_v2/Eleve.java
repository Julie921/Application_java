package app_fake_quizzlet_v2;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Map;

@DatabaseTable(tableName = "ELEVES")
public class Eleve extends Utilisateur { //TODO association réflexive pour que le prof soit associé à chaque élève

	public static final String PROFESSOR_ID_FIELD_NAME = "professeur_id";

	@DatabaseField
	private BaremeNiveau niveau;
	@DatabaseField(canBeNull = true)
	private float moyenne;
	public Historique historiqueEleve = new Historique();

	@DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = PROFESSOR_ID_FIELD_NAME)
	private Professeur prof;

	public Eleve() {
		super();
	}

	/**
	 * Constructeur pour la classe Eleve. Elle permet d'instancier un objet Eleve en renseignant le nom d'utilisateur (pseudo) et le mot de passe (password).
	 * Par exemple, si Marie veut créer son compte en utilisant le pseudo "marie2000" et le mot de passe "blabla", on va faire :
	 * Eleve marie = new Eleve("marie2000", "blabla");
	 * @param pseudo (String) Le nom d'utilisateur
	 */
	Eleve(String pseudo, BaremeNiveau niveau, Professeur prof) {
        super(pseudo);
		this.niveau = niveau;
		this.moyenne = 0;
		this.prof = prof;
		this.prof.ajouterEleve(this);
	}

	/**
	 * Méthode qui permet de récupérer le niveau d'un élève.
	 * Par exemple, si je veux récuperer le niveau de l'élève "marie", il faut faire :
	 * marie.getNiveau();
	 * @return Le niveau de l'élève
	 */
	public BaremeNiveau getBaremeNiveau(){
		return this.niveau;
	}

	/**
	 * Méthode qui permet de modifier le niveau d'un élève.
	 * Par exemple, si je veux changer le niveau de "marie", il faut faire :
	 * marie.setNiveau(3);
	 * @param niveau (int) Le niveau auquel on veut faire passer l'élève
	 */
	public void setNiveau(BaremeNiveau niveau){
		this.niveau = niveau;
	}

	public float getMoyenne() {
		return moyenne;
	}

	public void updateMoyenne(float mo) {
		this.moyenne= mo;
	}

	public Professeur getProf() {
		return prof;
	}

	public void setProf(Professeur prof) {
		this.prof = prof;
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

	public void updateNiveau(BaremeNiveau newNiveau){
		this.niveau = newNiveau;
	}

	public boolean mustChangeNiveau(){

		int compteur = 0;

		float palier = 15.00F;

		if(historiqueEleve.getData().size() < 1){ //l'élève n'a pas encore 5 notes donc il ne peut pas changer de niveau
			return false;
		}
		else { //élève a plus de 5 notes
			for(int i = historiqueEleve.getData().size()-1;  i < historiqueEleve.getData().size(); i++){ //on regarde les 5 dernière notes obtenues
				if(historiqueEleve.getData().get(i).get("niveau").toString().equals(this.niveau.toString())){ //si le niveau est celui de l'élève
					Float note = (Float) historiqueEleve.getData().get(i).get("note");
					System.out.println(note);
					if(note.compareTo(palier)>0) {
						compteur++;
						System.out.println("La note est au dessus de 15");
						System.out.println("le compteur : "+compteur);

					}
				}
			}

		}
		if(compteur==1){
			return true;
		}
		else{

			return false;
		}
	}

	@Override
	public String toString() {
		return "Eleve{" +
				"nom élève = " + getPseudo() +
				", niveau=" + niveau +
				", moyenne=" + moyenne +
				", historiqueEleve=" + historiqueEleve +
				", prof=" + prof +
				'}';
	}
}