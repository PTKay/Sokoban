package isel.poo.sokoban.view.tiles.actors;

import isel.poo.sokoban.model.Cell;
import isel.poo.sokoban.view.CellTile;

import static isel.leic.pg.Console.GRAY;
import static isel.leic.pg.Console.BLACK;
import static isel.leic.pg.Console.YELLOW;


public class ManTile extends CellTile {
    public ManTile() {
        super(innerCell.getType() == Cell.HOLE ? GRAY : YELLOW,
                BLACK, '@');
    }
}
