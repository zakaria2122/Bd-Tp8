import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;


public class LoginBD extends GridPane {
    private PasswordField motDePasse;
    private TextField login, nomServeur, nomBD;

    private class EcouteurLogin implements ChangeListener<Boolean>{

        @Override
        public void changed(ObservableValue<? extends Boolean> observableValue, Boolean b, Boolean b1) {
            if (! b1 && nomBD.getText().equals("") && ! (login.getText().equals(""))){
                nomBD.setText("DB"+login.getText());
            }
        }
    }
    public LoginBD(TestJDBC testJDBC){
        super();
        this.setPadding(new Insets(10));
        this.setHgap(10);
        this.setVgap(10);

        Label ln=new Label("Login :");
        ln.setAlignment(Pos.BASELINE_RIGHT);
        Label lp=new Label("Mode de passe :");
        lp.setAlignment(Pos.BASELINE_RIGHT);
        Label ls=new Label("Nom du serveur :");
        ls.setAlignment(Pos.BASELINE_RIGHT);
        Label lbd=new Label("Nom de la base de données :");
        ls.setAlignment(Pos.BASELINE_RIGHT);

        this.motDePasse = new PasswordField();
        this.motDePasse.setAlignment(Pos.BASELINE_LEFT);
        this.login = new TextField();
        this.login.focusedProperty().addListener(new EcouteurLogin());
        this.login.setAlignment(Pos.BASELINE_LEFT);
        this.nomServeur = new TextField("servinfo-maria");
        this.nomServeur.setAlignment(Pos.BASELINE_LEFT);
        this.nomBD = new TextField();
        this.nomBD.setAlignment(Pos.BASELINE_LEFT);

        Button b= new Button("Valider");
        b.setAlignment(Pos.BASELINE_CENTER);
        b.setOnAction(new ControleurConnexion(testJDBC));
        this.add(ln,1,1);
        this.add(lp,1,2);
        this.add(ls,1,3);
        this.add(lbd,1,4);
        this.add(this.login,2,1);
        this.add(this.motDePasse,2,2);
        this.add(this.nomServeur,2,3);
        this.add(this.nomBD,2,4);
        this.add(b,1,5,2,1);

    }

    public String getMotDePasse() {
        return this.motDePasse.getText();
    }

    public String getLogin() {
        return this.login.getText();
    }

    public String getNomServeur() {
        return this.nomServeur.getText();
    }

    public String getNomBD() {
        return this.nomBD.getText();
    }

}
