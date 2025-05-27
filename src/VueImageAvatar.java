import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Files;

public class VueImageAvatar extends ImageView{
    private byte[] img;
    private String imageParDefaut;
    //private ControleurChoixAvatar cca;

    VueImageAvatar(String imageParDefaut){
        this.imageParDefaut=imageParDefaut;
        this.setAvatar(imageParDefaut);
    }

    public  void resetAvatar(){
        this.setAvatar(this.imageParDefaut);
    }
    public void setAvatar(String nomFichier){
        File f= new File(nomFichier);
        try {

            this.img=Files.readAllBytes(f.toPath());
        }catch (Exception ex){this.img=null;}
        this.setImage(new Image(new ByteArrayInputStream(this.img)));
    }

    public void setAvatar(byte[] img) {
        if (img != null){
            this.img = img;
            this.setImage(new Image(new ByteArrayInputStream(this.img)));
        }
        else
            this.resetAvatar();
    }
    public byte[] getAvatar(){ return this.img;}
}
