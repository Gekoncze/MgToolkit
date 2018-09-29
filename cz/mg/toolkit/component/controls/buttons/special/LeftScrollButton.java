package cz.mg.toolkit.component.controls.buttons.special;

import cz.mg.toolkit.component.Container;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;


public class LeftScrollButton extends ScrollButton {
    @Override
    protected void doScroll(Container scrollPanel) {
        scrollHorizontally(scrollPanel, -getScrollSpeed());
    }
}