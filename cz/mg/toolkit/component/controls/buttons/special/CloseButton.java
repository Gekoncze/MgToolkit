package cz.mg.toolkit.component.controls.buttons.special;

import cz.mg.toolkit.component.DrawableContent;
import cz.mg.toolkit.component.controls.buttons.ContentButton;


public class CloseButton extends ContentButton {
    public CloseButton() {
        super(new Content());
    }
    
    public static class Content extends DrawableContent {
    }
}
