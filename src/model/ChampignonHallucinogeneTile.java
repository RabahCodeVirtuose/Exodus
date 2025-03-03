package model;

public class ChampignonHallucinogeneTile extends NourritureDangeureuse{
    private final TileState orginalTile;

    public ChampignonHallucinogeneTile(TileState originalTile) {
        this.orginalTile = originalTile;
    }


    @Override
    public boolean isMovable() {
        return true;
    }

    @Override
    public String getDescription() {
        return orginalTile.getDescription()+" hallucinogène";
    }

    @Override
    public boolean isDangerous() {
        return true;
    }
}
