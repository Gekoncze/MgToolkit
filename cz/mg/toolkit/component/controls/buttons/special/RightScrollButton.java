package cz.mg.toolkit.component.controls.buttons.special;

import cz.mg.toolkit.component.Container;
import cz.mg.toolkit.component.DrawableContent;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;


public class RightScrollButton extends ScrollButton {
    @Override
    protected void doScroll(Container scrollPanel) {
        scrollHorizontally(scrollPanel, getScrollSpeed());
    }
    
    @Override
    protected Content createContent() {
        return new Content();
    }
    
    public static class Content extends DrawableContent {
    }
}
