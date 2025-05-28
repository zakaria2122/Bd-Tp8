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

		ps.setInt(1, num);
		ResultSet rs = ps.executeQuery();

		Joueur joueur = null;

		if (rs.next()) {
			int numJoueur = rs.getInt("numJoueur");        // Ajouté
			String pseudo = rs.getString("pseudo");
			String motdepasse = rs.getString("motdepasse");
			char main = rs.getString("main").charAt(0);
			boolean abonne = rs.getString("abonne").equals("O");
			int niveau = rs.getInt("niveau");

			joueur = new Joueur(numJoueur, pseudo, motdepasse, abonne, main, niveau);  // Corrigé
		}

		rs.close();
		ps.close();

		return joueur;
	}


	ArrayList<Joueur> listeDesJoueuresultSet() throws SQLException {

		ArrayList<Joueur> liste = new ArrayList<>();
		PreparedStatement ps = laConnexion.prepareStatement(
				"SELECT * From JOUEUR" );
		ResultSet rs = ps.executeQuery(); 

		while (rs.next()) {
				int numJoueur = rs.getInt("numJoueur");    // Ajouté
				String pseudo = rs.getString("pseudo");
				String motdepasse = rs.getString("motdepasse");
				char main = rs.getString("main").charAt(0);
				boolean abonne = rs.getString("abonne").equals("O");
				int niveau = rs.getInt("niveau");

				Joueur j = new Joueur(numJoueur, pseudo, motdepasse, abonne, main, niveau);  
				liste.add(j);
	}
	
	rs.close();    
    ps.close();    
    
	return liste;
	}

	String rapportMessage() throws SQLException {
	PreparedStatement ps = laConnexion.prepareStatement(
				"SELECT pseudo, date(dateMsg) ladate , count(*) nbmssge " +
				"from MESSAGE,JOUEUR " +
				"where JOUEUR.numJoueur = idUt1 " +
				"group by date(dateMsg),pseudo "  );
		ResultSet rs = ps.executeQuery(); 
		String res = "";
		while(rs.next()){
			res +=  rs.getString("pseudo") + rs.getDate("ladate")+ rs.getInt("nbmssge")+ "\n";
		}
		rs.close();
		ps.close();


		return res;
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
