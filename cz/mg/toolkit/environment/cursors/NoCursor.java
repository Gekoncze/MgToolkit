package cz.mg.toolkit.environment.cursors;

import cz.mg.toolkit.environment.Cursor;
import cz.mg.toolkit.graphics.Image;


public class NoCursor extends Cursor {
    public NoCursor() {
        super(new Image(16, 16, Image.ColorModel.RGBA), 0, 0);
    }
}
