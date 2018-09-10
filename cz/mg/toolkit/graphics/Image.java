package cz.mg.toolkit.graphics;

import cz.mg.toolkit.impl.ImplImage;
import cz.mg.toolkit.impl.swing.SwingImplImage;
import java.io.IOException;
import java.io.InputStream;


public class Image {
    private final ImplImage implImage;
    
    public Image(InputStream stream) throws IOException {
        this.implImage = new SwingImplImage(stream);
    }

    public Image(int width, int height) {
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
