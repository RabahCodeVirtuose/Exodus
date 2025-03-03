package model;

public class ForestFactory implements AbstractFactory{
    @Override
    public Animal createAnimal(Position position, Personnage joueur, int id) {
        return new Ecureuil(position, joueur, id);
    }
    @Override
    public TileState createCachette1() {
        return new ArbreTile();
    }

    @Override
    public TileState dechiffrerEtAppeller(char type) {
     return switch (type) {
            case 'G' -> createNourriturePrincipale();
            case 'B' -> createCachette2();
            case 'A' -> createCachette1();
            case 'W' -> new WallTile();
            case 'V' -> createNourriturePourrie();
            case ' ', '@', 'E','R','H' -> new EmptyTile();
            case 'C' -> createNourritureSecondaire();
            default -> throw new IllegalArgumentException("Type inconnu : " + type);
        };
    }

    @Override
    public TileState createCachette2() {
        return new BuissonTile();
    }
    @Override
    public TileState createNourriturePrincipale() {
        return new Gland();
    }
    @Override
    public TileState createNourritureSecondaire() {
        return new Champignon();
    }

    @Override
    public TileState createNourriturePourrie() {
        return new ChampignonVeneuxTile(createNourritureSecondaire());
    }

    @Override
    public AnimalPredateur createAnimalPredateur1(Position position, int id , Personnage ennemi) {
        return new Renard(position, id,ennemi);
    }

    @Override
    public AnimalPredateur createAnimalPredateur2(Position position, int id, Personnage ennemi) {
        return new Hibou(position, id,ennemi);
    }


    @Override
    public char getAnimalChar() {
        return 'E';
    }

    @Override
    public char getPredateurChar1() {

        return 'R';
    }

    @Override
    public char getPredateurChar2() {
        return 'H';
    }

    @Override
    public String getTileDecoration(TileState tile) {
        return switch (tile) {
            case WallTile ignored -> TerminalColors.ANSI_BLACK_BACKGROUND + " # " + TerminalColors.ANSI_RESET;
            case ArbreTile ignored -> TerminalColors.ANSI_BLACK_BACKGROUND + TerminalColors.ANSI_GREEN + TerminalColors.ANSI_BOLD + " A " + TerminalColors.ANSI_RESET;
            case BuissonTile ignored -> TerminalColors.ANSI_BLACK_BACKGROUND + TerminalColors.ANSI_GREEN + TerminalColors.ANSI_BOLD + " B " + TerminalColors.ANSI_RESET;
            case EmptyTile ignored -> TerminalColors.ANSI_GREEN_BACKGROUND + " . " + TerminalColors.ANSI_RESET;
            case Gland ignored -> TerminalColors.ANSI_BROWN_ORANGE_BACKGROUND + TerminalColors.ANSI_YELLOWISH_TEXT + TerminalColors.ANSI_BOLD + " G " +TerminalColors.ANSI_RESET;
            case Champignon ignored -> TerminalColors.ANSI_WHITEY_BACKGROUND + TerminalColors.ANSI_BROWN + TerminalColors.ANSI_BOLD + " C " + TerminalColors.ANSI_RESET;
            case ChampignonVeneuxTile ignored -> TerminalColors.ANSI_WHITEY_BACKGROUND + TerminalColors.ANSI_BROWN + TerminalColors.ANSI_BOLD + " V " + TerminalColors.ANSI_RESET;

            default -> " ? ";
        };
    }

    @Override
    public String getCarteChemin() {
        return "maptest.txt";
    }
}
