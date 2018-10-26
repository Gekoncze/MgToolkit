package cz.mg.toolkit.component.controls.buttons.special;

import cz.mg.toolkit.component.Container;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;


public class RightScrollButton extends ScrollButton {
    public RightScrollButton() {
        super(new Content());
    }
    
    @Override
    protected void doScroll(Container scrollPanel) {
        scrollHorizontally(scrollPanel, getScrollSpeed());
    }
    
    public static class Content extends ScrollButton.Content {
    }
}
