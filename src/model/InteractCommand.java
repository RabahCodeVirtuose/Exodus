package model;

import vue.Ihm;

public class InteractCommand implements Command {
    private Carte carte;
    private Personnage joueur;
    private Ihm vue;

    public InteractCommand(Carte carte, Personnage joueur, Ihm vue) {
        this.carte = carte;
        this.joueur = joueur;
        this.vue = vue;
    }

    @Override
    public void execute() {
        var caseActuelle = carte.getCase(joueur.getPosition().getX(), joueur.getPosition().getY());
        if (caseActuelle != null) {
            vue.afficherMessage("Vous explorez la case...");
            vue.afficherMessage(caseActuelle.estVide() ? "La case est vide." : "La case contient des éléments.");
        }
    }
}
