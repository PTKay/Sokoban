package isel.poo.sokoban.view;

import isel.leic.pg.Console;
import isel.poo.console.FieldView;
import isel.poo.console.ParentView;

import static isel.leic.pg.Console.*;

public class StatusPanel extends ParentView {
    // StatusPanel parameters
    public static final int WIDTH = 7;
    private static final int HEIGHT = 17;
    private static final int BACKGROUND_COLOR = DARK_GRAY;

    // FieldView spacing parameters (X and Y axis)
    private static final int FIELD_Y_START = 1;
    private static final int FIELD_Y_SEPARATION = 1;
    private static final int FIELD_X_SEPARATION = 1;

    // Only for position calculation purposes. Needs to be changed in the FieldView class to change the actual height.
    private static final int FIELD_HEIGHT = 2;

    // Text starting position (Y axis)
    private static final int TEXT_Y_START = FIELD_Y_START + (FIELD_HEIGHT + FIELD_Y_SEPARATION) * 3;

    // Various FieldView's from the StatusPanel
    private final FieldView LEVEL;
    private final FieldView MOVES;
    private final FieldView BOXES;

    /**
     * Constructor of the StatusPanel.
     * Adds a view for each type of parameter display (level number, num. of moves, and
     * num. of remaining boxes). For each view, it checks the "Y" position for it to be placed
     * using the fieldYPositionAtIdx() method.
     * @param winWidth The width of the play area, which is where the status panel will start to be drawn (X axis).
     */
    public StatusPanel(int winWidth) {
        super(0, winWidth, HEIGHT, WIDTH, BACKGROUND_COLOR);
        addView(LEVEL = new FieldView("Level", fieldYPositionAtIdx(0), FIELD_X_SEPARATION, "0"));
        addView(MOVES = new FieldView("Moves", fieldYPositionAtIdx(1), FIELD_X_SEPARATION, "0"));
        addView(BOXES = new FieldView("Boxes", fieldYPositionAtIdx(2), FIELD_X_SEPARATION, "0"));
    }

    public void setLevel(int number) {
        LEVEL.setValue(number);
    }

    public void setBoxes(int remainingBoxes) {
        BOXES.setValue(remainingBoxes);
    }

    public void setMoves(int moves) {
        MOVES.setValue(moves);
    }

    /**
     * Paints the StatusPanel in the game's window. Also calls the method
     * paintControls() to paint the "tutorial" text.
     */
    @Override
    public void paint() {
        super.paint();
        paintControls();
    }

    /**
     * Paints the controls tutorial text, using separation values
     * (saved in the fields TEXT_Y_START and FIELD_Y_START)
     * to space out the text.
     *
     * There's a need to sum alternating values (0,1,2...) to TEXT_Y_START for each type of text.
     * It's required to sum even numbers in the key text, and odd numbers for the
     * key's description/purpose.
     */
    private void paintControls() {
        Console.color(YELLOW, BACKGROUND_COLOR);
        print(TEXT_Y_START, FIELD_Y_START - 1, "Cursor" );
        print(TEXT_Y_START + 2, FIELD_Y_START - 1, "Esc" );
        print(TEXT_Y_START + 4, FIELD_Y_START - 1, "S" );

        Console.color(WHITE, BACKGROUND_COLOR);
        print(TEXT_Y_START + 1, FIELD_Y_START, "move" );
        print(TEXT_Y_START + 3, FIELD_Y_START, "finish" );
        print(TEXT_Y_START + 5, FIELD_Y_START, "start" );
    }

    /**
     * Calculates the Y position of a field, depending on the index specified.
     * It calculates the position, using the separation values present in the fields
     * relating to height, Y separation, and Y starting position.
     * @param idx The index of the field to be drawn/painted
     * @return The "Y" position of the index specified
     */
    private static int fieldYPositionAtIdx(int idx) {
        return idx == 0 ? FIELD_Y_START : FIELD_Y_START + ((FIELD_HEIGHT + FIELD_Y_SEPARATION) * idx);
    }
}
