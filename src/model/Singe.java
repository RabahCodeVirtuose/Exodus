package model;

public class Singe extends Animal {
    private int nourrituresProches = 0; // Compteur de nourritures consommées à proximité du joueur
    public Singe(Position position,Personnage monMaitre,int id) {
        super(position,monMaitre,3,id); // Les singes ont 3 tours par défaut
    }

    public void incrementerNourrituresProches() {
        nourrituresProches++;
    }

    public int getNourrituresProches() {
        return nourrituresProches;
    }

    @Override
    void setNbToursPourPoserAnimal() {

    }

    @Override
    int getNbToursPourPoserAnimal() {
        return 0;
    }

    @Override
    void decrementeNbToursPourPoserAnimal() {

    }

    @Override
    public int getCompteur_perte_de_danger() {
        return compteur_perte_de_danger;
    }

    @Override
    public void decerement_compteur_perte_de_danger() {
        compteur_perte_de_danger --;
    }

    @Override
    public void reset_compteur_perte_de_danger() {
        compteur_perte_de_danger=3;
    }

    @Override
    public void setEstDansPoche() {
        this.estDansPoche = true;
    }

    @Override
    public void resetEstDansPoche() {
        this.estDansPoche = false;
    }

    @Override
    public boolean estDansPoche() {
        return this.estDansPoche;
    }

    @Override
    public void ressetNbToursPourEtatBizzare() {
        this.nbToursPourEtatBizzare=3;
    }

    @Override
    public int getNbToursPourEtatBizzare() {
        return this.nbToursPourEtatBizzare;
    }

    @Override
    public void decrementeNbToursPourEtatBizzare() {
        this.nbToursPourEtatBizzare --;
    }

    @Override
    public String getSymbole() {
        return " S ";
    }
    @Override
    public String getCouleur() {
        if (etatCourant instanceof EtatAnimalAffame) {
            return TerminalColors.ANSI_BLACK + TerminalColors.ANSI_MONKEY_BACKGROUND + TerminalColors.ANSI_BOLD; // Affamé : Noir
        } else if (etatCourant instanceof EtatAnimalRassasie) {
            return TerminalColors.ANSI_LIGHT_CYAN + TerminalColors.ANSI_BROWN_BACKGROUND + TerminalColors.ANSI_BOLD; // Rassasié : Bleu
        } else if (etatCourant instanceof EtatAnimalAmiDuPersonnage) {
            return TerminalColors.ANSI_LIGHT_PURPLE + TerminalColors.ANSI_BROWN_BACKGROUND + TerminalColors.ANSI_BOLD; // Ami : Violet
        } else if (etatCourant instanceof EtatAnimalJunkie) {
            return TerminalColors.ANSI_VIVD_RED + TerminalColors.ANSI_BROWN_BACKGROUND + TerminalColors.ANSI_BOLD; // Junkie : Rouge
        } else if (etatCourant instanceof EtatAnimalPerche) {
            return TerminalColors.LIGHT_GREEN + TerminalColors.ANSI_BROWN_BACKGROUND + TerminalColors.ANSI_BOLD; // Perché dans un arbre : Vert
        } else if (etatCourant instanceof EtatAnimalCache) {
            return TerminalColors.ANSI_BROWN_BACKGROUND + TerminalColors.ANSI_BROWN_BACKGROUND + TerminalColors.ANSI_BOLD; // Caché dans un buisson : Jaune
        }
        return TerminalColors.ANSI_WHITE; // Par défaut
    }

    @Override
    public String seCacheOu1() {
        return " Cocotier ";
    }

    @Override
    public String seCacheOu2() {
        return " Petit Rocher ";
    }

    @Override
    public TileState getTileCachettePrincipale() {
        return new CocotierTile();
    }

    @Override
    public TileState getTileCachetteSecondaire() {
        return new PetitRocherTile();
    }

    @Override
    protected int getInitialTours() {
        return 3;
    }


}

