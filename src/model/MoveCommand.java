package model;

public class MoveCommand implements Command {
    private Personnage joueur;
    private Direction direction;
    private Carte carte;

    public MoveCommand(Personnage joueur, Direction direction, Carte carte) {
        this.joueur = joueur;
        this.direction = direction;
        this.carte = carte;
    }

    @Override
    public void execute() {
        joueur.seDeplacer(direction, carte); // Passe la carte pour mettre à jour les cases
    }
}
