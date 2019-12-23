package isel.poo.sokoban.view.tiles;


import isel.poo.sokoban.view.CellTile;
import static isel.leic.pg.Console.CYAN;
import static isel.leic.pg.Console.GRAY;

public class HoleTile extends CellTile {
    public HoleTile() {
        super(CYAN, GRAY, '~');
    }
}
