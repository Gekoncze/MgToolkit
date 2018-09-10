package cz.mg.toolkit.environment;

import cz.mg.toolkit.graphics.Image;
import java.awt.Point;
import java.awt.Toolkit;


public class Cursor {
    private final java.awt.Cursor implCursor;

    public Cursor(java.awt.Cursor implCursor) {
        this.implCursor = implCursor;
    }
    
    public Cursor(Image image, int dx, int dy){
        this.implCursor = Toolkit.getDefaultToolkit().createCustomCursor(image.getImplImage(), new Point(dx, dy), "custom");
    }

    public java.awt.Cursor getImplCursor() {
        return implCursor;
    }
}
