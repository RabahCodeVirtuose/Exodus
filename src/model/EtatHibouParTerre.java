package model;

public class EtatHibouParTerre implements EtatPredateur{
    private Hibou hibou;
    private int nbToursRestants = 1; // Temps passé au sol

    public EtatHibouParTerre(Hibou hibou) {
        this.hibou = hibou;
    }

    @Override
    public void attaquer(Animal cible, Carte carte) {
        System.out.println("Hibou N° " + hibou.getId() + " est au sol et ne peut pas attaquer.");
    }

    @Override
    public void seDeplacer(Carte carte) {

        if (nbToursRestants > 0) {
            System.out.println("Hibou N° " + hibou.getId() + " reste au sol pour " + nbToursRestants + " tour(s).");
            nbToursRestants--;
        } else {
            System.out.println("Hibou N° " + hibou.getId() + " sort de son repos.");
            hibou.setEtat(new EtatHibouPeutAttaquer(hibou));// Retour à l'état PeutAttaquer
        }
    }

    @Override
    public String getCouleur() {
        return TerminalColors.ANSI_GREEN + TerminalColors.ANSI_BROWN_BACKGROUND + TerminalColors.ANSI_BOLD;
    }
}
