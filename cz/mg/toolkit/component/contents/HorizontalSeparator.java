package cz.mg.toolkit.component.contents;

import cz.mg.toolkit.component.DrawableContent;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;


public class HorizontalSeparator extends DrawableContent {
    private static final int DEFAULT_WIDTH = 7;
    private static final int DEFAULT_HEIGHT = 4;
    
    public HorizontalSeparator() {
        initComponent();
    }
    
    private void initComponent() {
        setContentWidth(DEFAULT_WIDTH);
        setContentHeight(DEFAULT_HEIGHT);
        setFillParentHeight(this);
    }
}
