package cz.mg.toolkit.component.controls.buttons.special;

import cz.mg.toolkit.component.controls.buttons.ContentButton;


public class CloseButton extends ContentButton {
    public static final String DEFAULT_DESIGN_NAME = "close button";
    
    public CloseButton() {
        super(new Content());
    }
    
    public static class Content extends cz.mg.toolkit.component.Content {
        public static final String DEFAULT_DESIGN_NAME = "close button content";
    }
}
