package cz.mg.toolkit.component.controls.buttons.special;

import cz.mg.toolkit.component.DrawableContent;
import cz.mg.toolkit.component.controls.buttons.ExtendedContentButton;


public class CloseButton extends ExtendedContentButton {
    public CloseButton() {
    }
    
    @Override
    protected Content createContent() {
        return new Content();
    }
    
    public static class Content extends DrawableContent {
    }
}
