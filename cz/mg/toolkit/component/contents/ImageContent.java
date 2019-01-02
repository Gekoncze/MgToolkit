package cz.mg.toolkit.component.contents;

import cz.mg.toolkit.component.Content;
import cz.mg.toolkit.graphics.Image;


public class ImageContent extends Content {
    public static final String DEFAULT_DESIGN_NAME = "image content";
    
    private Image image;

    public ImageContent() {
    }
    
    public ImageContent(Image image) {
        this.image = image;
    }
    
    public ImageContent(Image image, double contentWidth, double contentHeight) {
        super(contentWidth, contentHeight);
        this.image = image;
    }
    
    public final Image getImage() {
        return image;
    }

    public final void setImage(Image image) {
        this.image = image;
    }

    @Override
    public final double getPrefferedWidth() {
        return image != null ? image.getWidth() : 0;
    }

    @Override
    public final double getPrefferedHeight() {
        return image != null ? image.getHeight() : 0;
    }
}
