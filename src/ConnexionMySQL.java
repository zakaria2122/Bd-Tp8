import java.sql.*;

/**
 * La classe ConnexionMySQL gère la connexion à une base de données MySQL/MariaDB.
 * Elle permet d'établir, de vérifier et de fermer une connexion à la base de données,
 * ainsi que de créer des objets Statement et PreparedStatement pour exécuter des requêtes SQL.
 *

 * @author Zakaria
 */
public class ConnexionMySQL {
    // Attribut pour stocker l'objet Connection à la base de données
    private Connection mysql = null;
    // Attribut pour indiquer si la connexion est active ou non
    private boolean connecte = false;

    /**
     * Constructeur : initialise les attributs et charge le driver JDBC MariaDB/MySQL.
     * @throws ClassNotFoundException si le driver JDBC n'est pas trouvé
     */
    public ConnexionMySQL() throws ClassNotFoundException {
        this.mysql = null;
        this.connecte = false;
        // Charge le driver JDBC pour MariaDB/MySQL
        Class.forName("org.mariadb.jdbc.Driver");
    }

    /**
     * Établit la connexion à la base de données avec les paramètres fournis.
     * @param nomServeur l'adresse du serveur MySQL/MariaDB
     * @param nomBase le nom de la base de données
     * @param nomLogin le nom d'utilisateur
     * @param motDePasse le mot de passe
     * @throws SQLException si la connexion échoue
     * @throws ClassNotFoundException si le driver JDBC n'est pas trouvé
     */
    public void connecter(String nomServeur, String nomBase, String nomLogin, String motDePasse) throws SQLException, ClassNotFoundException {
        // Réinitialise la connexion et l'état
        this.mysql = null;
        this.connecte = false;
        // Crée la connexion à la base de données
        this.mysql = DriverManager.getConnection(
            "jdbc:mysql://" + nomServeur + ":3306/" + nomBase,
            nomLogin, motDePasse);
        // Met à jour l'état de connexion
        this.connecte = true;
    }

    /**
     * Ferme la connexion à la base de données.
     * @throws SQLException si une erreur survient lors de la fermeture
     */
    public void close() throws SQLException {
        // Met à jour l'état de connexion pour indiquer qu'elle est fermée.
        // Attention : ne ferme pas réellement la connexion JDBC !
        this.connecte = false;
    }

    /**
     * Indique si la connexion à la base de données est active.
     * @return true si connecté, false sinon
     */
    public boolean isConnecte() {
        return this.connecte;
    }

    /**
     * Crée et retourne un objet Statement pour exécuter des requêtes SQL simples.
     * @return un objet Statement
     * @throws SQLException si la connexion n'est pas valide
     */
    public Statement createStatement() throws SQLException {
        return this.mysql.createStatement();
    }

    /**
     * Crée et retourne un objet PreparedStatement pour exécuter des requêtes SQL paramétrées.
     * @param requete la requête SQL à préparer
     * @return un objet PreparedStatement
     * @throws SQLException si la connexion n'est pas valide ou la requête incorrecte
     */
    public PreparedStatement prepareStatement(String requete) throws SQLException {
        return this.mysql.prepareStatement(requete);
    }
}