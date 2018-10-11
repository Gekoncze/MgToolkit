package cz.mg.toolkit.component.controls.buttons.special;

import cz.mg.toolkit.component.Container;
import cz.mg.toolkit.component.DrawableContent;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.scrollVertically;


public class DownScrollButton extends ScrollButton {
    @Override
    protected void doScroll(Container scrollPanel) {
        scrollVertically(scrollPanel, getScrollSpeed());
    }
    
    @Override
    protected Content createContent() {
        return new Content();
    }
    
    public static class Content extends DrawableContent {
    }
}
