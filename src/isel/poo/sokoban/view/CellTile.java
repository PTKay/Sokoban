package isel.poo.sokoban.view;

import isel.leic.pg.Console;
import isel.poo.console.tile.Tile;
import isel.poo.sokoban.model.Cell;

import isel.poo.sokoban.view.tiles.*;
import isel.poo.sokoban.view.tiles.actors.*;

import static isel.poo.sokoban.model.Cell.*;

public abstract class CellTile extends Tile {
    public static final int SIDE = 2;

    protected static Cell innerCell;

    private final int BACKGROUND_COLOR;
    private final int FONT_COLOR;
    private final char TILE_CHAR;

    /**
     * Creates a CellTile object based on the colors given in the parameters of the constructor.
     * @param bgColor Background color of the tile
     * @param fontColor The color of the char specified
     * @param tileChar the char to be used in the tile
     */
    protected CellTile(int bgColor, int fontColor, char tileChar) {
        this.BACKGROUND_COLOR = bgColor;
        this.FONT_COLOR = fontColor;
        this.TILE_CHAR = tileChar;
    }

    /**
     * Returns the corresponding Tile object, based on the Cell given as parameter.
     * @param cell The Cell to be used to create its corresponding Tile
     * @return The created Tile, based on the specified Cell
     */
    public static Tile tileOf(Cell cell) {
        innerCell = cell;
        char cellType = cell.hasActor() ? cell.getActorType() : cell.getType();

        switch (cellType) {
                case VOID:
                    return new VoidTile();
                case FLOOR:
                    return new FloorTile();
                case OBSTACLE:
                    return new ObstacleTile();
                case TARGET:
                    return new TargetTile();
                case HOLE:
                    return new HoleTile();
                case MAN:
                    return new ManTile();
                case BOX:
                    return new BoxTile();
                default:
                    return null;
        }
    }

    /**
     * Paints a tile, based on the size of its side, and the colors referenced
     * in the object field.
     */
    @Override
    public void paint() {
        Console.color(FONT_COLOR, BACKGROUND_COLOR);

        for (int line = 0; line < SIDE; line++) {
            for (int col = 0; col < SIDE; col++) {
                print(line, col, TILE_CHAR);
                Console.color(FONT_COLOR, BACKGROUND_COLOR);
            }
        }
    }
}
