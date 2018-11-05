package cz.mg.toolkit.component.controls.buttons.special;

import cz.mg.toolkit.component.Container;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;


public class LeftScrollButton extends ScrollButton {
    public static final String DEFAULT_DESIGN_NAME = "left scroll button";
    
    public LeftScrollButton() {
        super(new Content());
    }
    
    @Override
    protected void doScroll(Container scrollPanel) {
        scrollHorizontally(scrollPanel, -getScrollSpeed());
    }
    
    public static class Content extends ScrollButton.Content {
        public static final String DEFAULT_DESIGN_NAME = "left scroll button content";
    }
}
