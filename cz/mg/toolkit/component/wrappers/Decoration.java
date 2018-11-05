package cz.mg.toolkit.component.wrappers;

import cz.mg.toolkit.component.containers.Wrapper;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;
import cz.mg.toolkit.utilities.sizepolices.FillParentSizePolicy;


public abstract class Decoration extends Wrapper {
    public static final String DEFAULT_DESIGN_NAME = "decoration";
    
    public Decoration() {
        setSizePolicy(this, new FillParentSizePolicy());
    }
}
