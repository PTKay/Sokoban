package isel.poo.sokoban.view.tiles;

import isel.poo.sokoban.view.CellTile;
import static isel.leic.pg.Console.WHITE;
import static isel.leic.pg.Console.GREEN;

public class TargetTile extends CellTile {
    public TargetTile() {
        super(WHITE, GREEN, '+');
    }
}
