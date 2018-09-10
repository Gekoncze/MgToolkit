package cz.mg.toolkit.environment;

import cz.mg.toolkit.graphics.Image;
import cz.mg.toolkit.impl.ImplCursor;
import cz.mg.toolkit.impl.swing.SwingImplCursor;


public class Cursor {
    private final ImplCursor implCursor;

    public Cursor(ImplCursor.NativeCursor nativeCursor) {
        this.implCursor = new SwingImplCursor(nativeCursor);
    }
    
    public Cursor(Image image, int dx, int dy){
        this.implCursor = new SwingImplCursor(image, dx, dy);
    }

    public ImplCursor getImplCursor() {
        return implCursor;
    }
}
