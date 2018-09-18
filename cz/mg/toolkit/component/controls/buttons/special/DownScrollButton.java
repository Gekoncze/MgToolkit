package cz.mg.toolkit.component.controls.buttons.special;

import cz.mg.toolkit.component.Container;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.scrollVertically;


public class DownScrollButton extends ScrollButton {
    @Override
    protected void doScroll(Container scrollPanel) {
        scrollVertically(scrollPanel, getScrollSpeed());
    }
}
