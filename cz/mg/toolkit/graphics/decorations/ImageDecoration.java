package cz.mg.toolkit.graphics.decorations;

import cz.mg.toolkit.component.Component;
import cz.mg.toolkit.graphics.Decoration;
import cz.mg.toolkit.graphics.Graphics;
import cz.mg.toolkit.graphics.Image;


public class ImageDecoration extends Decoration {
    private Image image;

    public ImageDecoration(Image image) {
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
    
    @Override
    protected void onDraw(Graphics g, Component component) {
        if(image != null) g.drawImage(image, 0, 0, component.getWidth(), component.getHeight());
    }
}
