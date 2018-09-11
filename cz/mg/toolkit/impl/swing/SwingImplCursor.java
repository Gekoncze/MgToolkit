package cz.mg.toolkit.impl.swing;

import cz.mg.toolkit.graphics.Image;
import cz.mg.toolkit.impl.ImplCursor;
import java.awt.Point;
import java.awt.Toolkit;


public class SwingImplCursor implements ImplCursor {
    final java.awt.Cursor swingCursor;
    
    public SwingImplCursor(NativeCursor nativeCursor) {
        switch(nativeCursor){
            case ARROW: this.swingCursor = new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR); break;
            case LINK: this.swingCursor = new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR); break;
            case LOADING: this.swingCursor = new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR); break;
            case MOVE: this.swingCursor = new java.awt.Cursor(java.awt.Cursor.MOVE_CURSOR); break;
            case TEXT: this.swingCursor = new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR); break;
            default: throw new RuntimeException();
        }
    }
    
    public SwingImplCursor(Image image, int dx, int dy){
        this.swingCursor = Toolkit.getDefaultToolkit().createCustomCursor(((SwingImplImage)image.getImplImage()).swingImage, new Point(dx, dy), "custom");
    }
}
