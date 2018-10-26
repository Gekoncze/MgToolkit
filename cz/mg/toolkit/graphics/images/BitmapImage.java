package cz.mg.toolkit.graphics.images;

import cz.mg.toolkit.graphics.Image;
import cz.mg.toolkit.impl.ImplImage;
import cz.mg.toolkit.impl.swing.SwingImplImage;
import java.io.IOException;
import java.io.InputStream;


public class BitmapImage implements Image {
    private final ImplImage implImage;
    private double width, height;
    
    public BitmapImage(InputStream stream) throws IOException {
        this.implImage = new SwingImplImage(stream);
    }

    public BitmapImage(int horizontalResolution, int verticalResolution) {
        this.implImage = new SwingImplImage(horizontalResolution, verticalResolution);
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

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public double getHeight() {
        return height;
    }

    public final void setWidth(double width) {
        this.width = width;
    }

    public final void setHeight(double height) {
        this.height = height;
    }
}
