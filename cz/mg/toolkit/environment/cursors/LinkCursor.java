package cz.mg.toolkit.environment.cursors;

import cz.mg.toolkit.environment.Cursor;
import cz.mg.toolkit.impl.ImplCursor;


public class LinkCursor extends Cursor {
    public static final LinkCursor INSTANCE = new LinkCursor();
    
    public LinkCursor() {
        super(ImplCursor.NativeCursor.LINK);
    }
}
