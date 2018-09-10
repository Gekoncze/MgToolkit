package cz.mg.toolkit.component.controls.buttons;

import cz.mg.toolkit.component.Content;
import cz.mg.toolkit.component.contents.ImageContent;


public class ImageButton extends ContentButton {
    private final ImageContent imageContent = new ImageContent();

    public ImageButton() {
        initComponents();
    }

    private void initComponents() {
        imageContent.setParent(this);
        imageContent.setUsePrefferedSize(false);
    }

    public ImageContent getImageContent() {
        return imageContent;
    }

    @Override
    public final Content getContent() {
        return imageContent;
    }
}
