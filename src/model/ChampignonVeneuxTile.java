package model;
// ici il y a le pattern décorateur qui est utilisé pour décorer le champignon
public class ChampignonVeneuxTile extends NourritureDangeureuse {

    private final TileState orginalTile;

    public ChampignonVeneuxTile(TileState originalTile) {
        this.orginalTile = originalTile;
    }


    @Override
    public boolean isMovable() {
        return true;
    }

    @Override
    public String getDescription() {
        return orginalTile.getDescription()+" vénéneux";
    }

    @Override
    public boolean isDangerous() {
        return true;
    }
}
