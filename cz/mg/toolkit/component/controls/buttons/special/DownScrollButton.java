package cz.mg.toolkit.component.controls.buttons.special;

import cz.mg.toolkit.component.Container;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.scrollVertically;


public class DownScrollButton extends ScrollButton {
    public static final String DEFAULT_DESIGN_NAME = "down scroll button";
    
    public DownScrollButton() {
        super(new Content());
    }
    
    @Override
    protected void doScroll(Container scrollPanel) {
        scrollVertically(scrollPanel, getScrollSpeed());
    }
    
    public static class Content extends ScrollButton.Content {
        public static final String DEFAULT_DESIGN_NAME = "down scroll button content";
    }
}
