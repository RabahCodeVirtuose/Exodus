package model;

public class Banane extends NourriturePrincipale{
    @Override
    public boolean isMovable() {
        return true;
    }

    @Override
    public String getDescription() {
        return "Banane";
    }

    @Override
    public boolean isDangerous() {
        return false;
    }


}
