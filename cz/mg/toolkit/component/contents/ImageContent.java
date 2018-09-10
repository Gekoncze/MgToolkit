package cz.mg.toolkit.component.contents;

import cz.mg.toolkit.component.Content;
import cz.mg.toolkit.graphics.Graphics;
import cz.mg.toolkit.graphics.Image;
import cz.mg.toolkit.event.adapters.GraphicsDrawAdapter;


public class ImageContent extends Content {
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
                if(image != null) g.drawImage(image, 0, 0, getWidth(), getHeight());
            }
        });
    }
    
    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
