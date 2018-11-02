package cz.mg.toolkit.environment.cursors;

import cz.mg.toolkit.environment.Cursor;
import cz.mg.toolkit.impl.ImplCursor;


public class LoadingCursor extends Cursor {
    public static final LoadingCursor INSTANCE = new LoadingCursor();
    
    public LoadingCursor() {
        super(ImplCursor.NativeCursor.LOADING);
    }
}
