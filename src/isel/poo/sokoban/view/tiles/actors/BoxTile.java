package isel.poo.sokoban.view.tiles.actors;
import isel.poo.sokoban.model.Cell;
import isel.poo.sokoban.view.CellTile;

import static isel.leic.pg.Console.GREEN;
import static isel.leic.pg.Console.RED;
import static isel.leic.pg.Console.BLACK;

public class BoxTile extends CellTile {

    public BoxTile() {
        super(innerCell.getType() == Cell.TARGET ? GREEN : RED,
                BLACK, 'O');
    }
}
