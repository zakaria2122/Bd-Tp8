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

  ps.setInt(1, num);  // Lier le paramètre
    ResultSet rs = ps.executeQuery();  // Exécuter une requête SELECT

    Joueur joueur = null;

    if (rs.next()) {
        String pseudo = rs.getString("pseudo");
        String motdepasse = rs.getString("motdepasse");
        String main = rs.getString("main");
        boolean abonne = rs.getString("abonne").equals("O");
        int niveau = rs.getInt("niveau");

        joueur = new Joueur(numJoueur, pseudo, motdepasse, main, abonne, niveau);
    }

    rs.close();
    ps.close();

    return joueur; // null si non trouvé
}


	ArrayList<Joueur> listeDesJoueuresultSet() throws SQLException {
		throw new SQLException("méthode listeDesJoueuresultSet à implémenter");
	}

	String rapportMessage() throws SQLException {
		return "rapportMessage A faire";
	}

	String rapportMessageComplet() throws SQLException {
		return "rapportMessageComplet A faire";
	}

	ArrayList<Map.Entry<String, Integer>> nbMsgParJour() throws SQLException {
		// Pour créer une valeur pouvant être ajoutée à l'ArrayList<Map.Entry<String,
		// Integer>>
		// faire un new AbstractMap.SimpleEntry<>("coucou",45)
		throw new SQLException("méthode nbMsgParJour à implémenter");
	}

	ArrayList<Map.Entry<String, Integer>> nbMain() throws SQLException {
		// Pour créer une valeur pouvant être ajoutée à l'ArrayList<Map.Entry<String,
		// Integer>>
		// faire un new AbstractMap.SimpleEntry<>("coucou",45)
		throw new SQLException("méthode nbMain à implémenter");
	}
}
