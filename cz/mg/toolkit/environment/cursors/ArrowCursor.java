package cz.mg.toolkit.environment.cursors;

import cz.mg.toolkit.environment.Cursor;
import cz.mg.toolkit.impl.ImplCursor;


public class ArrowCursor extends Cursor {
    public static final ArrowCursor INSTANCE = new ArrowCursor();
    
    public ArrowCursor() {
        super(ImplCursor.NativeCursor.ARROW);
    }
}
