package cz.mg.toolkit.component.contents;

import cz.mg.toolkit.component.Content;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;
import cz.mg.toolkit.utilities.sizepolices.FillParentSizePolicy;
import cz.mg.toolkit.utilities.sizepolices.NoSizePolicy;


public class HorizontalSeparator extends Content {
    public static final String DEFAULT_DESIGN_NAME = "horizontal separator";
    
    public HorizontalSeparator() {
        initComponent();
    }
    
    private void initComponent() {
        setHorizontalSizePolicy(this, new NoSizePolicy());
        setVerticalSizePolicy(this, new FillParentSizePolicy());
    }
}
