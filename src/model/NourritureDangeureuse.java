package model;

public abstract class NourritureDangeureuse implements TileState{
    @Override
    public boolean isMovable() {
        return true;
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public boolean isDangerous() {
        return true;
    }
}
