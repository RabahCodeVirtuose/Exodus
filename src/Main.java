import controleur.Controleur;
import vue.Ihm;

public class Main {
    public static void main(String[] args) {
            Ihm vue = new Ihm();
            Controleur controleur = new Controleur(vue);
            controleur.demarrerJeu();
            /*J'ai ajouté ce commentaire pour voir si git  fonctionne */
    }
}
