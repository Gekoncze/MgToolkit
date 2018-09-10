package cz.mg.toolkit.environment.cursors;

import cz.mg.toolkit.environment.Cursor;
import cz.mg.toolkit.impl.ImplCursor;


public class MoveCursor extends Cursor {
    public MoveCursor() {
        super(ImplCursor.NativeCursor.MOVE);
    }
}
