package cz.mg.toolkit.component.contents;

import cz.mg.toolkit.component.Content;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;
import cz.mg.toolkit.utilities.sizepolices.FillParentSizePolicy;


public class HorizontalSeparator extends Content {
    public static final String DEFAULT_DESIGN_NAME = "horizontal separator";
    
    public HorizontalSeparator() {
        initComponent();
    }
    
    private void initComponent() {
        setHorizontalSizePolicy(this, new FixedSizePolicy());
        setVerticalSizePolicy(this, new FillParentSizePolicy());
    }
}
