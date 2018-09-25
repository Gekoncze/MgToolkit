package cz.mg.toolkit.component.contents;

import cz.mg.toolkit.component.DrawableContent;
import cz.mg.toolkit.graphics.Graphics;
import cz.mg.toolkit.graphics.Image;
import cz.mg.toolkit.event.adapters.GraphicsDrawAdapter;


public class ImageContent extends DrawableContent {
    private Image image;

    public ImageContent() {
        this(null, 0, 0);
    }
    
    public ImageContent(Image image, int width, int height) {
        super(width, height);
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
}
