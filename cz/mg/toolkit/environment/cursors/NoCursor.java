package cz.mg.toolkit.environment.cursors;

import cz.mg.toolkit.environment.Cursor;
import cz.mg.toolkit.graphics.images.BitmapImage;


public class NoCursor extends Cursor {
    public static final NoCursor INSTANCE = new NoCursor();
    
    public NoCursor() {
        super(new BitmapImage(16, 16), 0, 0);
    }
}
