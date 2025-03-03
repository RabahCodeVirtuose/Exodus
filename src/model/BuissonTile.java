package model;

public class BuissonTile extends CachetteSecondaire{


    @Override
    public boolean isMovable() {
        return false;
    }

    @Override
    public String getDescription() {
        return "Buisson";
    }

    @Override
    public boolean isDangerous() {
        return false;
    }


}
