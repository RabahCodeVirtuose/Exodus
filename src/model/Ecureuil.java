package model;

public class Ecureuil extends Animal {

    public Ecureuil(Position position,Personnage monMaitre,int id) {
        super(position, monMaitre,5,id); // Les écureuils ont 5 tours par défaut
    }

    @Override
    void setNbToursPourPoserAnimal() {
        this.nbToursPourPoserAnimal=3;
    }

    @Override
    int getNbToursPourPoserAnimal() {
        return this.nbToursPourPoserAnimal;
    }

    @Override
    void decrementeNbToursPourPoserAnimal() {
    this.nbToursPourPoserAnimal--;
    }

    @Override
    public int getCompteur_perte_de_danger() {
        return 0;
    }

    @Override
    public void decerement_compteur_perte_de_danger() {

    }

    @Override
    public void reset_compteur_perte_de_danger() {

    }

    @Override
    public void setEstDansPoche() {
        this.estDansPoche=true;
    }

    @Override
    public void resetEstDansPoche() {
        this.estDansPoche=false;
    }

    @Override
    public boolean estDansPoche() {
        return this.estDansPoche;
    }

    @Override
    public void ressetNbToursPourEtatBizzare() {
        this.nbToursPourEtatBizzare=5;
    }

    @Override
    public int getNbToursPourEtatBizzare() {
        return this.nbToursPourEtatBizzare;
    }

    @Override
    public void decrementeNbToursPourEtatBizzare() {
        this.nbToursPourEtatBizzare--;
    }

    @Override
    public String getSymbole() {
        return " E ";
    }


    public String getCouleur() {
        if (etatCourant instanceof EtatAnimalAffame) {
            return TerminalColors.ANSI_BLACK + TerminalColors.ANSI_YELLOWISH_BACKGROUND + TerminalColors.ANSI_BOLD; // Affamé : Noir
        } else if (etatCourant instanceof EtatAnimalRassasie) {
            return TerminalColors.ANSI_LIGHT_CYAN + TerminalColors.ANSI_YELLOWISH_BACKGROUND + TerminalColors.ANSI_BOLD; // Rassasié : Bleu
        } else if (etatCourant instanceof EtatAnimalAmiDuPersonnage) {
            return TerminalColors.ANSI_DARK_PURPLE + TerminalColors.ANSI_YELLOWISH_BACKGROUND + TerminalColors.ANSI_BOLD; // Ami : Violet
        } else if (etatCourant instanceof EtatAnimalJunkie) {
            return TerminalColors.ANSI_VIVD_RED + TerminalColors.ANSI_YELLOWISH_BACKGROUND + TerminalColors.ANSI_BOLD; // Junkie : Rouge
        } else if (etatCourant instanceof EtatAnimalPerche) {
            return TerminalColors.ANSI_GREEN + TerminalColors.ANSI_YELLOWISH_BACKGROUND + TerminalColors.ANSI_BOLD; // Perché dans un arbre : Vert
        } else if (etatCourant instanceof EtatAnimalCache) {
            return TerminalColors.ANSI_YELLOWISH_BACKGROUND + TerminalColors.ANSI_YELLOWISH_BACKGROUND + TerminalColors.ANSI_BOLD; // Caché dans un buisson : Jaune
        }
        return TerminalColors.ANSI_WHITE; // Par défaut
    }

    @Override
    public String seCacheOu1() {
        return " Arbre ";
    }

    @Override
    public String seCacheOu2() {
        return " Buisson ";
    }

    @Override
    public TileState getTileCachettePrincipale() {
        return new ArbreTile();
    }

    @Override
    public TileState getTileCachetteSecondaire() {
        return new CocotierTile();
    }

    @Override
    protected int getInitialTours() {
        return 5;
    }


}
