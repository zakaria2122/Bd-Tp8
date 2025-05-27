public class Joueur {
    private int identifiant;
    private String pseudo;
    private String motdepasse;
    private int niveau;
    private char main;
    private boolean abonne;
    //private byte[] avatar;


    public Joueur(int identifiant, String pseudo, String motdepasse, boolean abonne, char main, int niveau) {
        this.identifiant = identifiant;
        this.pseudo = pseudo;
        this.motdepasse = motdepasse;
        this.niveau = niveau;
        this.main = main;
        this.abonne = abonne;
    }


    public Joueur(String pseudo2, String motdepasse2, String main2, boolean abonne2, int niveau2) {
        //TODO Auto-generated constructor stub
    }


    public int getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(int identifiant) {
        this.identifiant = identifiant;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getMotdepasse() {
        return motdepasse;
    }

    public void setMotdepasse(String motdepasse) {
        this.motdepasse = motdepasse;
    }

    public int getNiveau() {
        return niveau;
    }

    public void setNiveau(int niveau) {
        this.niveau = niveau;
    }

    public char getMain() {
        return main;
    }

    public void setMain(char main) {
        this.main = main;
    }

    public boolean isAbonne() {
        return abonne;
    }

    public void setAbonne(boolean abonne) {
        this.abonne = abonne;
    }

//    public byte[] getAvatar() {
//        return avatar;
//    }

//    public void setAvatar(byte[] avatar) {
//        this.avatar = avatar;
//    }
}
