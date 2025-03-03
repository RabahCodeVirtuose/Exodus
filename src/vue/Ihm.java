package vue;

import model.*;

import java.util.List;
import java.util.Scanner;

public class Ihm {
    private Scanner scanner;
    private String theme;
    private AbstractFactory themeFactoryDansIhm;

    public Ihm() {
        this.scanner = new Scanner(System.in);
    }
public void setThemeFactoryDansIhm(AbstractFactory themeFactoryDansIhm) {
        this.themeFactoryDansIhm = themeFactoryDansIhm;
}
    public void afficherMessage(String message) {
        System.out.println(message);
    }
    public void setTheme(String theme) {
        this.theme = theme;
    }
    public void afficherCarte(Carte carte, Personnage joueur, List<Animal> animaux, List<AnimalPredateur> animauxpred1, List<AnimalPredateur> animauxpred2) {
        nettoyerTerminal(); // Nettoie le terminal avant d'afficher la nouvelle carte
        String bordure = "===" + "=".repeat(carte.getLargeur() * 3) + "==="; // Bordure supérieure
        System.out.println(bordure);

        for (int y = 0; y < carte.getHauteur(); y++) {
            System.out.print("|| "); // Bordure latérale gauche
            for (int x = 0; x < carte.getLargeur(); x++) {
                boolean estAnimal = false;

                // Vérifie si un animal est à cette position
                for (Animal animal : animaux) {
                    if (animal.getPosition().getX() == x && animal.getPosition().getY() == y && !(animal.estDansPoche())) {

                            if(! animal.estTue()){
                                System.out.print(animal.getCouleur()+ animal.getSymbole() + TerminalColors.ANSI_RESET);
                            } else {
                                System.out.print(animal.getCouleur()+ animal.getSymbole() + TerminalColors.ANSI_RESET);
                            }


                        estAnimal = true;
                        break;
                    }
                }

                for (AnimalPredateur animal : animauxpred1) {
                    if (animal.getPosition().getX() == x && animal.getPosition().getY() == y) {


                        System.out.print(animal.getCouleur()+ animal.getSymbole() + TerminalColors.ANSI_RESET);

                        estAnimal = true;
                        break;
                    }
                }

                for (AnimalPredateur animal : animauxpred2) {
                    if (animal.getPosition().getX() == x && animal.getPosition().getY() == y) {


                        System.out.print(animal.getCouleur()+ animal.getSymbole() + TerminalColors.ANSI_RESET);

                        estAnimal = true;
                        break;
                    }
                }




                if (!estAnimal) {
                    // Vérifie si le joueur est à cette position
                    if (joueur.getPosition().getX() == x && joueur.getPosition().getY() == y) {
                        System.out.print(joueur.getCouleur() + " @ " + TerminalColors.ANSI_RESET);
                    } else {
                        // Affichage classique des cases
                        TileState tile = carte.getCase(x, y).getEtat();
                        String symbol=themeFactoryDansIhm.getTileDecoration(tile);
                        System.out.print(symbol);
                    }
                }
            }
            System.out.println(" ||"); // Bordure latérale droite
        }

        System.out.println(bordure); // Bordure inférieure
    }
    public int demanderEntier(String message, int min, int max) {
        int choix = -1;
        do {
            afficherMessage(message);
            try {
                choix = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                afficherMessage("Veuillez entrer un nombre valide.");
            }
        } while (choix < min || choix > max);
        return choix;
    }

    public void afficherCommandes() {
        System.out.println(TerminalColors.ANSI_CYAN + "Commandes disponibles :" + TerminalColors.ANSI_RESET);
        System.out.println(TerminalColors.ANSI_YELLOWISH_TEXT + "- Déplacement : "+ TerminalColors.ANSI_RESET + "nord, sud, est, ouest");
        System.out.println(TerminalColors.ANSI_YELLOWISH_TEXT + "- Explorer : " + TerminalColors.ANSI_RESET + "explorer");
        System.out.println(TerminalColors.ANSI_YELLOWISH_TEXT + "- Inventaire : " + TerminalColors.ANSI_RESET + "inventaire");
        System.out.println(TerminalColors.ANSI_YELLOWISH_TEXT + "- Lancer un objet : " + TerminalColors.ANSI_RESET + "lancer");
        System.out.println(TerminalColors.ANSI_YELLOWISH_TEXT + "- Battre un animal : " + TerminalColors.ANSI_RESET + "battre");
        System.out.println(TerminalColors.ANSI_YELLOWISH_TEXT + "- Battre un animal Prédateur : " + TerminalColors.ANSI_RESET + "battrep");
        System.out.println(TerminalColors.ANSI_YELLOWISH_TEXT + "- Quitter le jeu : " + TerminalColors.ANSI_RESET + "quitter");
        System.out.println();
    }

    public String lireCommande() {
        System.out.print(TerminalColors.ANSI_YELLOW + "Commande : " + TerminalColors.ANSI_RESET);
        return scanner.nextLine();
    }

    public void nettoyerTerminal() {
        // Séquence d'échappement pour nettoyer la console
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
