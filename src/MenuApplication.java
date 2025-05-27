import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

import java.util.ArrayList;

public class MenuApplication extends MenuBar{
    private String [][] menuPrinc={{"Connexion BD","Connexion","Déconnexion","Quitter"},
            {"Joueur","Afficher le plus grand numéro de joueur","Afficher un joueur","Créer un joueur","Supprimer un joueur",
	     "Mettre à jour un joueur","Afficher tous les joueurs","Afficher msg par joueur", "Afficher tous les messages","Afficher graphique messages", "Afficher répartition gauchers-droitiers"}};
    private ArrayList<MenuItem> lesItems;
    private TestJDBC testJDBC;
    private ControleurMenu controleurMenu;

    MenuApplication(TestJDBC testJDBC){
        super();
        this.testJDBC=testJDBC;
        this.lesItems=new ArrayList<MenuItem>();
        this.controleurMenu=new ControleurMenu(testJDBC);
        for (String[] listeMenu:this.menuPrinc){
            Menu m = new Menu(listeMenu[0]);
            m.setDisable(true);
            for (int i=1;i<listeMenu.length;i++){
                MenuItem mi=new MenuItem(listeMenu[i]);
                mi.setId(""+i);
                mi.setOnAction(controleurMenu);
                lesItems.add(mi);
                m.getItems().add(mi);
            }
            this.getMenus().add(m);
        }
        this.getMenus().get(0).setDisable(false);
        this.lesItems.get(1).setDisable(true);
    }

    public void connecter(){
        for (Menu m:this.getMenus()){
            m.setDisable(false);
        }
        lesItems.get(0).setDisable(true);
        lesItems.get(1).setDisable(false);

    }

    public void deconnecter(){
        for (Menu m:this.getMenus()){
            m.setDisable(true);
        }
        this.getMenus().get(0).setDisable(false);
        lesItems.get(0).setDisable(false);
        lesItems.get(1).setDisable(true);

    }
}
