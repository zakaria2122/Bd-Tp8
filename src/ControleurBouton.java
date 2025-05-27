import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import java.sql.SQLException;

public class ControleurBouton implements EventHandler<ActionEvent> {
    private TestJDBC testJDBC;

    public ControleurBouton(TestJDBC testJDBC) {
        this.testJDBC=testJDBC;
    }

    private void alertOK( String message){
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setResizable(true);
        alert.setWidth(500);
        alert.setTitle("Bravo !!!! ");
        alert.setHeaderText("Opération réussie");
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void alertEchec( SQLException ex){
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setResizable(true);
        alert.setWidth(500);
        alert.setTitle("Echec !!!! ");
        alert.setHeaderText("Opération a echoué");
        alert.setContentText("Voici le message envoyé par le serveur\n"+ex.getMessage());
        alert.showAndWait();
    }
    @Override

    public void handle(ActionEvent actionEvent) {
        String nom=((Button)actionEvent.getTarget()).getText();
        Joueur j;
        JoueurBD jbd=this.testJDBC.getJoueurBD();
        FicheJoueur ficheJoueur=this.testJDBC.getFicheJoueur();
        switch (nom) {
            case "Créer":
                j = ficheJoueur.getJoueur();
                try {
                    int nj = jbd.insererJoueur(j);
                    ficheJoueur.setNumJoueur(nj);
                    alertOK("Insertion du joueur a réussi\nLe nouveau joueur porte le numéro "+nj);
                    ficheJoueur.viderFiche();
                } catch (SQLException ex) {
                    alertEchec(ex);
                }
                break;
            case "Rechercher":
                try{
                    j = jbd.rechercherJoueurParNum(ficheJoueur.getNumJoueur());
                    ficheJoueur.setJoueur(j);
                    String titre = ficheJoueur.getTitre();
                    switch (titre) {
                        case "Suppression":
                            ficheJoueur.setNomBouton("Supprimer");
                            break;
                        case "Mise à jour":
                            ficheJoueur.setNomBouton("Mettre à jour");
                            ficheJoueur.activerNumJoueur(false);
                            break;
                    }

                } catch (SQLException ex) {
                    alertEchec(ex);
                }
                break;
            case "Supprimer":
                try{
                    jbd.effacerJoueur(ficheJoueur.getNumJoueur());
                    alertOK("Le joueur "+ficheJoueur.getNumJoueur()+" a bien été supprimé");
                    ficheJoueur.viderFiche();
                    ficheJoueur.setNomBouton("Rechercher");
                }catch (SQLException ex) {
                    alertEchec(ex);
                }
                break;
            case "Mettre à jour":
                try{
                    jbd.majJoueur(ficheJoueur.getJoueur());
                    alertOK("Le joueur "+ficheJoueur.getNumJoueur()+" a bien été mis à jour");
                    ficheJoueur.viderFiche();
                    ficheJoueur.setNomBouton("Rechercher");
                    ficheJoueur.activerNumJoueur(true);
                }catch (SQLException ex) {
                    alertEchec(ex);
                }
                break;
        }

    }
}
