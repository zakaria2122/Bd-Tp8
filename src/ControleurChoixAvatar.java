import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.File;


public class ControleurChoixAvatar implements EventHandler<MouseEvent> {
    private VueImageAvatar via;
    private FileChooser fc;
    private Tooltip tt;
    ControleurChoixAvatar(VueImageAvatar via){
        this.via=via;
        this.fc=new FileChooser();
        this.fc.setTitle("Choix de l'avatar");
	this.fc.setInitialDirectory(new File("./img"));
        this.tt=new Tooltip("Cliquez sur l'image pour changer l'avatar");
    }
    @Override
    public void handle(MouseEvent mouseEvent) {
        if (mouseEvent.getEventType()==MouseEvent.MOUSE_CLICKED) {
            File f = this.fc.showOpenDialog(null);
            if (f != null)
                via.setAvatar(f.getAbsolutePath());
        }
        if (mouseEvent.getEventType()==MouseEvent.MOUSE_ENTERED) {
            Bounds limites = via.localToScreen(via.getBoundsInLocal());

            this.tt.show(via,limites.getMinX()+mouseEvent.getX(),limites.getMinY()+mouseEvent.getY());
        }
        if (mouseEvent.getEventType()==MouseEvent.MOUSE_EXITED) {
            this.tt.hide();
        }

    }
}
