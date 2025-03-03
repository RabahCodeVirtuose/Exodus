package model;

public class Champignon extends NourritureSecondaire {


    @Override
    public boolean isMovable() {
        return true; // Accessible pour le joueur
    }

    @Override
    public String getDescription() {
        return " Champignon ";
    }

    @Override
    public boolean isDangerous() {
        return false;
    }


}
