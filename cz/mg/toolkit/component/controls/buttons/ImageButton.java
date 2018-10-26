package cz.mg.toolkit.component.controls.buttons;

import cz.mg.toolkit.component.contents.ImageContent;
import cz.mg.toolkit.graphics.Image;


public class ImageButton extends ContentButton {
    public ImageButton() {
        super(new ImageContent());
    }
    
    public ImageButton(Image image) {
        super(new ImageContent(image));
    }
    
    public ImageButton(Image image, double contentWidth, double contentHeight) {
        super(new ImageContent(image, contentWidth, contentHeight));
    }
    
    public final ImageContent getImageContent(){
        return (ImageContent) getContent();
    }
}
