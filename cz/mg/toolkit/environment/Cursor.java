package cz.mg.toolkit.environment;

import cz.mg.toolkit.graphics.Image;
import cz.mg.toolkit.impl.Impl;
import cz.mg.toolkit.impl.ImplCursor;


public class Cursor {
    private final ImplCursor implCursor;

    public Cursor(ImplCursor.NativeCursor nativeCursor) {
        this.implCursor = Impl.getImplApi().createCursor(nativeCursor);
    }
    
    public Cursor(Image image, int dx, int dy){
        this.implCursor = Impl.getImplApi().createCursor(image, dx, dy);
    }

    public ImplCursor getImplCursor() {
        return implCursor;
    }
}
