package cz.mg.toolkit.graphics.images;

import cz.mg.toolkit.graphics.Image;
import cz.mg.toolkit.impl.ImplImage;
import cz.mg.toolkit.impl.swing.SwingImplImage;
import java.io.IOException;
import java.io.InputStream;


public class BitmapImage implements Image {
    private final ImplImage implImage;
    
    public BitmapImage(InputStream stream) throws IOException {
        this.implImage = new SwingImplImage(stream);
    }

    public BitmapImage(int width, int height) {
        this.implImage = new SwingImplImage(width, height);
    }

    public ImplImage getImplImage() {
        return implImage;
    }
    
    public final int getHorizontalResolution(){
        return implImage.getHorizontalResolution();
    }
    
    public final int getVerticalResolution(){
        return implImage.getVerticalResolution();
    }
}
