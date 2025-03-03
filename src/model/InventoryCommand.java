package model;

public class InventoryCommand implements Command {
    private Personnage joueur;

    public InventoryCommand(Personnage joueur) {
        this.joueur = joueur;
    }

    @Override
    public void execute() {
        joueur.afficherInventaire();
    }
}
