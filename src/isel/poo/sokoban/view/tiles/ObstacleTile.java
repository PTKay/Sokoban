package isel.poo.sokoban.view.tiles;

import isel.poo.sokoban.view.CellTile;
import static isel.leic.pg.Console.BLACK;
import static isel.leic.pg.Console.BROWN;

public class ObstacleTile extends CellTile {
    public ObstacleTile() {
        super(BROWN, BLACK, '-');
    }
}
