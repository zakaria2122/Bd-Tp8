import java.sql.*;
import java.util.ArrayList;
import java.util.AbstractMap;
import java.util.Map;

public class JoueurBD {
	ConnexionMySQL laConnexion;
	Statement st;

	JoueurBD(ConnexionMySQL laConnexion) {
		this.laConnexion = laConnexion;
	}

	int maxNumJoueur() throws SQLException {
		st = laConnexion.createStatement(); // exécution requete

		ResultSet resultSet = st.executeQuery("SELECT IFNULL(max(numJoueur),0) leMax from JOUEUR"); // chargement de la
		// premiere ligne du
		// resultat

		resultSet.next(); // consultation de cette ligne

		int res = resultSet.getInt(1); // recupere la premiere ligne

		resultSet.close();

		return res;
	}

	int insererJoueur(Joueur j) throws SQLException {
		PreparedStatement ps = laConnexion.prepareStatement(
				"insert into JOUEUR(numJoueur, pseudo, motdepasse, main, abonne, niveau)" + "VALUES (?,?,?,?,?,?);");

		int nouvNum = maxNumJoueur() + 1;
		ps.setInt(1, nouvNum);
		ps.setString(2, j.getPseudo());
		ps.setString(3, j.getMotdepasse());
		ps.setString(4, "" + j.getMain());
		String abo;

		if (j.isAbonne()) {
			abo = "O";
		}

		else {
			abo = "N";
		}

		ps.setString(5, abo);
		ps.setInt(6, j.getNiveau());
		ps.executeUpdate();

		return nouvNum;
	}

	void effacerJoueur(int num) throws SQLException {
		PreparedStatement ps = laConnexion.prepareStatement("DELETE FROM JOUEUR WHERE numJoueur = ?");
		ps.setInt(1, num); // 1 est le numero de la colonne de la table Joueur
		ps.executeUpdate();
		ps.close();
	}

	void majJoueur(Joueur j) throws SQLException {
		PreparedStatement ps = laConnexion.prepareStatement(
				"UPDATE JOUEUR SET pseudo = ?, motdepasse = ?, main = ?, abonne = ?, niveau = ? WHERE numJoueur = ?");
		ps.setString(2, j.getPseudo());
		ps.setString(3, j.getMotdepasse());
		ps.setString(4, "" + j.getMain());
		String abo;

		if (j.isAbonne()) {
			abo = "O";
		} else {
			abo = "N";
		}

		ps.setString(5, abo);
		ps.setInt(6, j.getNiveau());
		ps.executeUpdate();
		ps.close();
	}

	Joueur rechercherJoueurParNum(int num) throws SQLException {
		PreparedStatement ps = laConnexion.prepareStatement(
				"SELECT * From JOUEUR WHERE numJoueur = ?");

		ps.setInt(1, num); // Lier le paramètre
		ResultSet rs = ps.executeQuery(); // Exécuter une requête SELECT

		Joueur joueur = null;

		if (rs.next()) {
			String pseudo = rs.getString("pseudo");
			String motdepasse = rs.getString("motdepasse");
			String main = rs.getString("main");
			boolean abonne = rs.getString("abonne").equals("O");
			int niveau = rs.getInt("niveau");

			joueur = new Joueur(pseudo, motdepasse, main, abonne, niveau);
		}

		rs.close();
		ps.close();

		return joueur; // null si non trouvé
	}

	ArrayList<Joueur> recupererTousLesJoueurs() throws SQLException {
		ArrayList<Joueur> liste = new ArrayList<>();
		String requete = "SELECT * FROM JOUEUR";
	
		try (Statement st = laConnexion.createStatement();
			 ResultSet resultSet = st.executeQuery(requete)) {
	
			while (resultSet.next()) {
				int numJoueur = resultSet.getInt("numJoueur");
				String pseudo = resultSet.getString("pseudo");
				String motdepasse = resultSet.getString("motdepasse");
				String main = resultSet.getString("main");
				boolean abonne = resultSet.getString("abonne").equals("O");
				int niveau = resultSet.getInt("niveau");
	
				Joueur j = new Joueur(pseudo, motdepasse, main, abonne, niveau);
				j.setNumJoueur(numJoueur);
				liste.add(j);
			}
		}
	
		return liste;
	}
	

	String rapportMessage() throws SQLException {
		StringBuilder sb = new StringBuilder();
		st = laConnexion.createStatement();
		ResultSet resultSet = st.executeQuery(
				"SELECT COUNT(*) AS nbMsg, DATE_FORMAT(dateEnvoi, '%Y-%m-%d') AS date FROM MESSAGE GROUP BY dateEnvoi");

		while (resultSet.next()) {
			int nbMsg = resultSet.getInt("nbMsg");
			String date = resultSet.getString("date");
			sb.append("Date: ").append(date).append(", Nombre de messages: ").append(nbMsg).append("\n");
		}

		resultSet.close();
		return sb.toString();
	}

	String rapportMessageComplet() throws SQLException {
		StringBuilder sb = new StringBuilder();
		st = laConnexion.createStatement();
		ResultSet resultSet = st.executeQuery("SELECT * FROM MESSAGE");

		while (resultSet.next()) {
			int numMessage = resultSet.getInt("numMessage");
			int numJoueur = resultSet.getInt("numJoueur");
			String contenu = resultSet.getString("contenu");
			Timestamp dateEnvoi = resultSet.getTimestamp("dateEnvoi");

			sb.append("Numéro de message: ").append(numMessage)
					.append(", Numéro de joueur: ").append(numJoueur)
					.append(", Contenu: ").append(contenu)
					.append(", Date d'envoi: ").append(dateEnvoi).append("\n");
		}

		resultSet.close();
		return sb.toString();
	}

	ArrayList<Map.Entry<String, Integer>> nbMsgParJour() throws SQLException {
		// Pour créer une valeur pouvant être ajoutée à l'ArrayList<Map.Entry<String,
		// Integer>>
		// faire un new AbstractMap.SimpleEntry<>("coucou",45)

		ArrayList<Map.Entry<String, Integer>> liste = new ArrayList<>(); // Crée une liste vide pour stocker les paires
																			// date/nombre de messages
		st = laConnexion.createStatement(); // Crée un objet Statement pour exécuter la requête SQL
		ResultSet resultSet = st.executeQuery( // Exécute la requête SQL qui :
				"SELECT DATE_FORMAT(dateEnvoi, '%Y-%m-%d') AS date, " + // - Formate la date d'envoi au format
																		// YYYY-MM-DD
						"COUNT(*) AS nbMsg FROM MESSAGE GROUP BY dateEnvoi"); // - Compte le nombre de messages et
																				// groupe par date d'envoi

		while (resultSet.next()) { // Parcourt chaque ligne du résultat de la requête
			String date = resultSet.getString("date"); // Récupère la date formatée de la colonne "date"
			int nbMsg = resultSet.getInt("nbMsg"); // Récupère le nombre de messages de la colonne "nbMsg"
			liste.add(new AbstractMap.SimpleEntry<>(date, nbMsg)); // Crée une paire clé-valeur (date, nombre) et
																	// l'ajoute à la liste
		}

		resultSet.close(); // Ferme le ResultSet pour libérer les ressources mémoire
		return liste; // Retourne la liste complète des statistiques par jour
	}

	ArrayList<Map.Entry<String, Integer>> nbMain() throws SQLException {
		// Pour créer une valeur pouvant être ajoutée à l'ArrayList<Map.Entry<String,
		// Integer>>
		// faire un new AbstractMap.SimpleEntry<>("coucou",45)

		ArrayList<Map.Entry<String, Integer>> liste = new ArrayList<>(); // Crée la liste de résultats
		st = laConnexion.createStatement(); // Crée l'objet Statement pour exécuter la requête
		ResultSet resultSet = st.executeQuery( // Exécute la requête SQL qui groupe par type de main
				"SELECT main, COUNT(*) AS nbJoueurs FROM JOUEUR GROUP BY main"); // Compte le nombre de joueurs pour
																					// chaque type de main

		while (resultSet.next()) { // Parcourt tous les résultats de la requête
			String typeMain = resultSet.getString("main"); // Récupère le type de main (droitier/gaucher)
			int nbJoueurs = resultSet.getInt("nbJoueurs"); // Récupère le nombre de joueurs pour ce type
			liste.add(new AbstractMap.SimpleEntry<>(typeMain, nbJoueurs)); // Ajoute une entrée clé-valeur à la liste
		}

		resultSet.close(); // Ferme le ResultSet pour libérer les ressources
		return liste; // Retourne la liste des types de main avec leurs effectifs
	}
}
