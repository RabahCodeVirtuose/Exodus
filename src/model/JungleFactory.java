package model;

public class JungleFactory implements AbstractFactory{
    @Override
    public Animal createAnimal(Position position, Personnage joueur, int id) {
        return new Singe(position, joueur, id);
    }
    @Override
    public TileState createCachette1() {
        return new CocotierTile();
    }

    @Override
    public TileState dechiffrerEtAppeller(char type) {
        return switch (type) {
            case 'B' ->createNourriturePrincipale();
            case 'P' -> createCachette2();
            case 'O' -> createCachette1();
            case 'V' -> createNourriturePourrie();
            case 'W' -> new WallTile();
            case ' ','@','S','T','N' -> new EmptyTile();
            case 'C' -> createNourritureSecondaire();
            default -> throw new IllegalArgumentException("Type inconnu : " + type);};
    }

    @Override
    public TileState createCachette2() {
        return new PetitRocherTile();
    }
    @Override
    public TileState createNourriturePrincipale() {
        return new Banane();
    }
    @Override
    public TileState createNourritureSecondaire() {
        return new Champignon();
    }

    @Override
    public TileState createNourriturePourrie() {
        return new ChampignonHallucinogeneTile(createNourritureSecondaire());
    }

    @Override
    public AnimalPredateur createAnimalPredateur1(Position position, int id , Personnage ennemi) {
        return new Serpent(position, id, ennemi);
    }

    @Override
    public AnimalPredateur createAnimalPredateur2(Position position, int id, Personnage ennemi) {

        return new Scorpion(position,id,ennemi);
    }


    @Override
    public char getAnimalChar() {
        return 'S';
    }

    @Override
    public char getPredateurChar1() {
        return 'T';
    }

    @Override
    public char getPredateurChar2() {
        return 'N';
    }

    @Override
    public String getTileDecoration(TileState tile) {
        return switch (tile) {
            case WallTile ignored -> TerminalColors.ANSI_BLACK_BACKGROUND + " # " + TerminalColors.ANSI_RESET;
            case CocotierTile ignored -> TerminalColors.ANSI_BLACK_BACKGROUND + TerminalColors.ANSI_BRIGHT_WHITE_TEXT + TerminalColors.ANSI_BOLD+ " O " + TerminalColors.ANSI_RESET;
            case PetitRocherTile ignored -> TerminalColors.ANSI_BLACK_BACKGROUND + TerminalColors.ANSI_WHITE + TerminalColors.ANSI_BOLD +  " P " + TerminalColors.ANSI_RESET;
            case EmptyTile ignored -> TerminalColors.ANSI_MOSS_GREEN_BACKGROUND + TerminalColors.ANSI_BOLD + " . " + TerminalColors.ANSI_RESET;
            case Banane ignored -> TerminalColors.ANSI_GREEN_BACKGROUND + TerminalColors.ANSI_YELLOWISH_TEXT + TerminalColors.ANSI_BOLD + " B " + TerminalColors.ANSI_RESET;
            case Champignon ignored -> TerminalColors.ANSI_WHITEY_BACKGROUND + TerminalColors.ANSI_BROWN + TerminalColors.ANSI_BOLD + " C " + TerminalColors.ANSI_RESET;
            case ChampignonHallucinogeneTile ignored -> TerminalColors.ANSI_WHITEY_BACKGROUND + TerminalColors.ANSI_BROWN + TerminalColors.ANSI_BOLD + " V " + TerminalColors.ANSI_RESET;

            default -> " ? ";
        };    }

    @Override
    public String getCarteChemin() {
        return "junglecarte33.txt";
    }
}
