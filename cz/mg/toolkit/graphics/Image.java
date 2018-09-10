package cz.mg.toolkit.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;


public class Image {
    private java.awt.image.BufferedImage implImage;
    
    public Image(InputStream stream) throws IOException {
        this.implImage = ImageIO.read(stream);
    }

    public Image(int width, int height, ColorModel colorModel) {
        if(width < 1) width = 1;
        if(height < 1) height = 1;
        this.implImage = new BufferedImage(width, height, colorModel.toImplid());
    }
    
    public Image(BufferedImage implImage) {
        this.implImage = implImage;
    }

    public BufferedImage getImplImage() {
        return implImage;
    }
    
    public int getWidth(){
        return implImage.getWidth();
    }
    
    public int getHeight(){
        return implImage.getHeight();
    }
    
    public static enum ColorModel {
        RGB,
        RGBA;
        
        public int toImplid(){
            switch(this){
                case RGB: return BufferedImage.TYPE_INT_RGB;
                case RGBA: return BufferedImage.TYPE_INT_ARGB;
                default: throw new RuntimeException();
            }
        }
    }
}
