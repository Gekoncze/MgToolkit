package cz.mg.toolkit.component.contents;

import cz.mg.toolkit.component.DrawableContent;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;
import cz.mg.toolkit.utilities.sizepolices.FillParentSizePolicy;


public class VerticalSeparator extends DrawableContent {
    private static final int DEFAULT_WIDTH = 4;
    private static final int DEFAULT_HEIGHT = 7;
    
    public VerticalSeparator() {
        initComponent();
    }
    
    private void initComponent() {
        setContentWidth(DEFAULT_WIDTH);
        setContentHeight(DEFAULT_HEIGHT);
        setHorizontalSizePolicy(this, new FillParentSizePolicy());
    }
}
