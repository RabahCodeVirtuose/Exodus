package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Carte {
    private Case[][] grille;
    private int largeur, hauteur;
    private Position joueurPosition;
    private List<Position> positionsAnimaux;
    private List<Position> positionsAnimauxPredateurs1;
    private List<Position> positionsAnimauxPredateurs2;
    private AbstractFactory themeFactoryDansCarte;
    public Carte() {
        this.positionsAnimaux = new ArrayList<>();
        this.positionsAnimauxPredateurs1 = new ArrayList<>();
        this.positionsAnimauxPredateurs2 = new ArrayList<>();


    }

    public void setAbstractFactory(AbstractFactory abstractFactory) {
        this.themeFactoryDansCarte = abstractFactory;
    }


    public void setDimensions(int hauteur, int largeur) {
        this.hauteur = hauteur;
        this.largeur = largeur;
        this.grille = new Case[hauteur][largeur];
    }
    public void chargerCarteDepuisFichier(String cheminFichier) {
        // Afficher le répertoire de travail
        System.out.println("Répertoire de travail : " + System.getProperty("user.dir"));

        // Convertir le chemin relatif en absolu pour le débogage
        String cheminAbsolu = Paths.get(cheminFichier).toAbsolutePath().toString();
        System.out.println("Chargement du fichier depuis : " + cheminAbsolu);
        try (BufferedReader reader = new BufferedReader(new FileReader(cheminFichier))) {
            // Lire le type de terrain (ligne 1)
            String typeTerrain = reader.readLine().trim();
            System.out.println("Type de terrain : " + typeTerrain);

            // Lire le nombre de lignes et de colonnes
            int lignes = Integer.parseInt(reader.readLine().trim());
            int colonnes = Integer.parseInt(reader.readLine().trim());
            System.out.println("Dimensions : " + lignes + " x " + colonnes);

            // Initialiser la grille
            setDimensions(lignes, colonnes);

            // Lire la matrice ligne par ligne
            for (int i = 0; i < lignes; i++) {
                String ligne = reader.readLine();
                for (int j = 0; j < colonnes; j++) {
                    char type = j < ligne.length() ? ligne.charAt(j) : ' ';
                    if(type == '@') {
                        joueurPosition = new Position(j,i);
                    } else if (type == themeFactoryDansCarte.getAnimalChar()) {
                        positionsAnimaux.add(new Position(j,i));
                    }else if (type == themeFactoryDansCarte.getPredateurChar1()) {
                        positionsAnimauxPredateurs1.add(new Position(j,i));
                    }else if (type == themeFactoryDansCarte.getPredateurChar2()) {
                        positionsAnimauxPredateurs2.add(new Position(j,i));
                    }

                   TileState remp= themeFactoryDansCarte.dechiffrerEtAppeller(type);

                    // Initialiser chaque case en fonction du caractère
                    setCase(i, j, new Case(remp));
                }
            }

            System.out.println("Carte chargée avec succès depuis le fichier : " + cheminFichier);
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement du fichier : " + e.getMessage());
        }
    }

    public Position getPersonnagePosition() { return joueurPosition; }

    public List<Position> getAnimals() { return positionsAnimaux;}
    public List<Position> getAnimalsPredateur1() {
        return positionsAnimauxPredateurs1;}
    public List<Position> getAnimalsPredateur2() { return positionsAnimauxPredateurs2;}

    public void setCase(int x, int y, Case caseCible) {
        this.grille[x][y] = caseCible;
    }

    public Case getCase(int x, int y) {
        if (x < 0 || x >= largeur || y < 0 || y >= hauteur) {
            return null;
        }
        return grille[y][x];
    }

    public int getLargeur() {
        return largeur;
    }

    public int getHauteur() {
        return hauteur;
    }
}
