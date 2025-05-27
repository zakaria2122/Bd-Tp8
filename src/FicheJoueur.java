import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


public class FicheJoueur extends GridPane{
    private TestJDBC testJDBC;
    private Label titre;
    private TextField numJoueur;
    private TextField pseudo;
    private PasswordField passwd;
    private CheckBox abonne;
    private VueImageAvatar avatar;

    private ComboBox<String> cb;
    private ToggleGroup gr;
    private RadioButton gaucher;
    private RadioButton droitier;

    private Button bouton;

    //private byte[] imageAvatar;


    public void setNomBouton(String nomBouton) {
        this.bouton.setText(nomBouton);

    }

     public void setTitre(String titre) {
        this.titre.setText(titre);
    }

    public void setNumJoueur(int numJoueur){
        this.numJoueur.setText(""+numJoueur);
    }

    public String getTitre(){
        return this.titre.getText();
    }

    public void setJoueur(Joueur j){
        this.pseudo.setText(j.getPseudo());
        this.passwd.setText(j.getMotdepasse());
        this.abonne.setSelected(j.isAbonne());

        switch(j.getNiveau()){
            case 1: cb.setValue("Débutant"); break;
            case 2: cb.setValue("Medium"); break;
            case 3: cb.setValue("Expert"); break;
        }
        if (j.getMain()=='G')
            gr.selectToggle(gaucher);
        else
            gr.selectToggle(droitier);
    }

    public Joueur getJoueur(){
        int id=-1;
        try {
            id = Integer.parseInt(this.numJoueur.getText());
        }catch (Exception e){}
        String pseudo=this.pseudo.getText();
        String motdepasse=this.passwd.getText();

        int niveau;
        if (cb.getValue().equals("Débutant"))
            niveau=1;
        else if (cb.getValue().equals("Medium"))
            niveau=2;
        else niveau=3;
        char main;
        if (gr.getSelectedToggle()==gaucher)
            main='G';
        else main='D';
        boolean abonne=this.abonne.isSelected();
	    return new Joueur(id,pseudo,motdepasse,abonne,main,niveau);
    }

    void viderFiche(){
        this.numJoueur.setText("");
        this.passwd.setText("");
        this.pseudo.setText("");
        this.abonne.setSelected(false);
        this.gr.selectToggle(this.droitier);
        this.cb.setValue("Débutant");
        this.avatar.resetAvatar();
    }



    FicheJoueur(TestJDBC testJDBC) {
        super();
        this.testJDBC=testJDBC;
        this.setPadding(new Insets(5, 5, 5, 5));
        this.setHgap(5);
        this.setVgap(5);
        this.setBackground(new Background(new BackgroundFill(Color.LIGHTSEAGREEN, null, null)));

        this.titre=new Label("Fiche Joueur");
        this.titre.setFont(Font.font(24));
        this.numJoueur = new TextField();
        this.pseudo = new TextField();
        this.passwd = new PasswordField();
        this.abonne = new CheckBox("Abonné");
        this.avatar = new VueImageAvatar("./avatar.png");
        this.avatar.setFitWidth(100);
        this.avatar.setPreserveRatio(true);
        this.avatar.setSmooth(true);
        this.cb=new ComboBox<String>(
                FXCollections.observableArrayList("Expert","Medium","Débutant"));

        this.droitier = new RadioButton("Droitier");
        this.gaucher = new RadioButton("Gaucher");
        this.gr = new ToggleGroup();
        this.droitier.setToggleGroup(gr);
        this.gaucher.setToggleGroup(gr);
        TitledPane tp = new TitledPane();
        tp.setText("Main");
        tp.setCollapsible(false);

        VBox vp = new VBox(5);
        vp.getChildren().addAll(this.droitier,this.gaucher);
        tp.setContent(vp);

        this.bouton = new Button("OK");
        this.bouton.setOnAction(new ControleurBouton(this.testJDBC));

        this.add(this.titre,1,0,2,1);
        this.add(new Label("Numéro:"),1,1);
        this.add(this.numJoueur,2,1);
        this.add(new Label("Pseudo:"),1,2);
        this.add(this.pseudo,2,2);
        this.add(new Label("Mot de passe:"),1,3);
        this.add(this.passwd,2,3);
        this.add(new Label("Niveau:"),1,4);
        this.add(this.cb,2,4);
        this.add(this.abonne,1,5);
        this.add(tp,1,6,2,1);
        this.add(bouton,1,7,2,1);
        this.add(this.avatar,3,0);

    }
    public void activerNumJoueur(boolean actif){
        for (Node n: this.getChildren()){
            n.setDisable(actif);
        }
        this.numJoueur.setDisable(!actif);
        this.bouton.setDisable(false);
    }
    public int getNumJoueur(){
        return Integer.parseInt(this.numJoueur.getText());
    }

}
