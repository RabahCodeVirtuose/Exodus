package model;

import java.util.List;
import java.util.Scanner;

public class ThrowCommand implements Command {
    private Personnage joueur;
    private Direction direction;
    private Carte carte;

    public ThrowCommand(Personnage joueur, Direction direction, Carte carte) {
        this.joueur = joueur;
        this.direction = direction;
        this.carte = carte;
    }

    @Override
    public void execute() {
        List<Objet> inventaire = joueur.getInventaire();

        // Vérifier si l'inventaire est vide
        if (inventaire.isEmpty()) {
            System.out.println("Votre inventaire est vide.");
            return;
        }

        // Afficher l'inventaire avec les indices
        System.out.println(TerminalColors.ANSI_YELLOWISH_TEXT + "Inventaire :" + TerminalColors.ANSI_RESET);
        for (int i = 0; i < inventaire.size(); i++) {
            System.out.println(TerminalColors.ANSI_CYAN +i + ". " + inventaire.get(i).getType() + TerminalColors.ANSI_RESET);
        }

        // Demander à l'utilisateur de choisir un objet
        Scanner scanner = new Scanner(System.in);
        System.out.println(TerminalColors.ANSI_YELLOWISH_TEXT + "Entrez l'indice de l'objet à jeter :"+ TerminalColors.ANSI_RESET);
        int choix;
        try {
            choix = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println(TerminalColors.ANSI_YELLOW + "/!\\ Indice invalide."+ TerminalColors.ANSI_RESET);

            return;
        }

        // Vérifier si l'indice est valide
        if (choix < 0 || choix >= inventaire.size()) {
            System.out.println(TerminalColors.ANSI_YELLOW + "/!\\ Indice invalide."+ TerminalColors.ANSI_RESET);
            return;
        }

        // Retirer l'objet choisi
        Objet objet = inventaire.get(choix);
        inventaire.remove(choix);

        // Déterminer la position cible
        Position cible = joueur.getPosition().positionTowards(direction, 1);
        Case caseCible = carte.getCase(cible.getX(), cible.getY());

        // Placer l'objet sur la carte
        if (caseCible != null && caseCible.getEtat() instanceof EmptyTile) {
            caseCible.setEtat(
                    objet.getType1());
            caseCible.setJeteparJouer();
            System.out.println(TerminalColors.ANSI_VIVD_RED + "(-) Vous avez lancé " + objet.getType() + " vers " + direction + "." + TerminalColors.ANSI_RESET);
        }  else {
            System.out.println("Impossible de lancer l'objet ici.");
            joueur.ajouterObjet(objet); // Remettre l'objet dans l'inventaire
        }
    }
}
