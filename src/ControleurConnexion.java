import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;

import java.sql.SQLException;

public class ControleurConnexion implements EventHandler<ActionEvent>{
    TestJDBC testJDBC;

    public ControleurConnexion(TestJDBC testJDBC) {
        this.testJDBC=testJDBC;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        LoginBD l=testJDBC.getLoginBD();
        ConnexionMySQL connexionMySQL=testJDBC.getConnexionMySQL();

        try {
            connexionMySQL.connecter(l.getNomServeur(),l.getNomBD(),l.getLogin(), l.getMotDePasse());
	    if (connexionMySQL.isConnecte())
		testJDBC.connexionReussie();
	    else
		testJDBC.setMessage("Vous n'êtes pas connecté");	
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Echec !!!! ");
			alert.setResizable(true);
			alert.setWidth(500);
			alert.setHeaderText("Echec de la connexion au serveur");
			alert.setContentText("Voici le message envoyé par le serveur\n"+e.getMessage());
			alert.showAndWait();
			testJDBC.setMessage("Vous n'êtes pas connecté");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
