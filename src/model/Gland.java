package model;

public class Gland extends NourriturePrincipale {


    @Override
    public boolean isMovable() {
        return true; // Peut marcher dessus après avoir ramassé l'objet
    }

    @Override
    public String getDescription() {
        return "Gland";
    }

    @Override
    public boolean isDangerous() {
        return false;
    }


}
