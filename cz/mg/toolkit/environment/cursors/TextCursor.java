package cz.mg.toolkit.environment.cursors;

import cz.mg.toolkit.environment.Cursor;
import cz.mg.toolkit.impl.ImplCursor;


public class TextCursor extends Cursor {
    public static final TextCursor INSTANCE = new TextCursor();
    
    public TextCursor() {
        super(ImplCursor.NativeCursor.TEXT);
    }
}
