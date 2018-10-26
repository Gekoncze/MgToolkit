package cz.mg.toolkit.component.contents;

import cz.mg.toolkit.component.Content;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;
import cz.mg.toolkit.utilities.sizepolices.WrapAndFillSizePolicy;


public class HorizontalSpacer extends Content {
    public HorizontalSpacer() {
        setHorizontalSizePolicy(this, new WrapAndFillSizePolicy());
    }
}
