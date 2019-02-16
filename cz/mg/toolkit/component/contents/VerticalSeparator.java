package cz.mg.toolkit.component.contents;

import cz.mg.toolkit.component.Content;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;
import cz.mg.toolkit.utilities.sizepolices.FillParentSizePolicy;
import cz.mg.toolkit.utilities.sizepolices.NoSizePolicy;


public class VerticalSeparator extends Content {
    public static final String DEFAULT_DESIGN_NAME = "vertical separator";
    
    public VerticalSeparator() {
        initComponent();
    }
    
    private void initComponent() {
        setHorizontalSizePolicy(this, new FillParentSizePolicy());
        setVerticalSizePolicy(this, new NoSizePolicy());
    }
}
