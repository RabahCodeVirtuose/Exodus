package model;

public class ArbreTile extends CachettePrincipale{


    @Override
    public boolean isMovable() {
        return false;
    }

    @Override
    public String getDescription() {
        return "Arbre";
    }

    @Override
    public boolean isDangerous() {
        return false;
    }


}
