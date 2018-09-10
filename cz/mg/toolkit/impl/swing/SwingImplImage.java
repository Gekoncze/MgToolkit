package cz.mg.toolkit.impl.swing;

import cz.mg.toolkit.impl.ImplImage;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;


public class SwingImplImage implements ImplImage {
    final java.awt.image.BufferedImage swingImage;
    private final int horizontalResolution;
    private final int verticalResolution;
    
    public SwingImplImage(InputStream stream) throws IOException {
        this.swingImage = ImageIO.read(stream);
        this.horizontalResolution = this.swingImage.getWidth();
        this.verticalResolution = this.swingImage.getHeight();
    }

    public SwingImplImage(int horizontalResolution, int verticalResolution) {
        if(horizontalResolution < 0) horizontalResolution = 0;
        if(verticalResolution < 0) verticalResolution = 0;
        this.horizontalResolution = horizontalResolution;
        this.verticalResolution = verticalResolution;
        if(horizontalResolution < 1) horizontalResolution = 1;
        if(verticalResolution < 1) verticalResolution = 1;
        this.swingImage = new BufferedImage(horizontalResolution, verticalResolution, BufferedImage.TYPE_INT_ARGB);
    }

    @Override
    public final int getHorizontalResolution() {
        return horizontalResolution;
    }

    @Override
    public final int getVerticalResolution() {
        return verticalResolution;
    }
}
