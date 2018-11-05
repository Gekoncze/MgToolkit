package cz.mg.toolkit.component.contents;

import cz.mg.toolkit.component.Content;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;
import cz.mg.toolkit.utilities.sizepolices.WrapAndFillSizePolicy;


public class VerticalSpacer extends Content {
    public static final String DEFAULT_DESIGN_NAME = "vertical spacer";
    
    public VerticalSpacer() {
        setVerticalSizePolicy(this, new WrapAndFillSizePolicy());
    }
}
