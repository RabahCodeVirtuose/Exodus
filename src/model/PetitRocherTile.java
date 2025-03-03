package model;

public class PetitRocherTile extends CachetteSecondaire{
    @Override
    public boolean isMovable() {
        return false;
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public boolean isDangerous() {
        return false;
    }

}
