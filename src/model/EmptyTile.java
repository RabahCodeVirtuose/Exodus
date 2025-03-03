package model;

public class EmptyTile implements TileState{
    @Override
    public boolean isMovable() {
        return true;
    }

    @Override
    public String getDescription() {
        return "espace libre ";
    }

    @Override
    public boolean isDangerous() {
        return false;
    }


}
