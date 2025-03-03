package model;

public class WallTile implements TileState {


    @Override
    public boolean isMovable() {
        return false;
    }

    @Override
    public String getDescription() {
        return "Mur";
    }

    @Override
    public boolean isDangerous() {
        return false;
    }


}
