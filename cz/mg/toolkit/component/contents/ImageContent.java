package cz.mg.toolkit.component.contents;

import cz.mg.toolkit.component.DrawableContent;
import cz.mg.toolkit.graphics.Graphics;
import cz.mg.toolkit.graphics.Image;
import cz.mg.toolkit.event.adapters.GraphicsDrawAdapter;


public class ImageContent extends DrawableContent {
    private Image image;

    public ImageContent() {
        addEventListeners();
    }
    
    public ImageContent(Image image) {
        this.image = image;
        addEventListeners();
    }
    
    public ImageContent(Image image, double contentWidth, double contentHeight) {
        super(contentWidth, contentHeight);
        this.image = image;
        addEventListeners();
    }

    private void addEventListeners(){
        getEventListeners().addLast(new GraphicsDrawAdapter() {
            @Override
            public void onDrawEventEnter(Graphics g) {
                g.setColor(getCurrentForegroundColor());
                if(image != null) g.drawImage(image, 0, 0, getWidth(), getHeight());
            }
        });
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
