package model;

public class CocotierTile extends CachettePrincipale{
    @Override
    public boolean isMovable() {
        return false;
    }

    @Override
    public String getDescription() {
        return "Cocotier";
    }

    @Override
    public boolean isDangerous() {
        return false;
    }

}
