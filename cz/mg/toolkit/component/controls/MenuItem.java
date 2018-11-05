package cz.mg.toolkit.component.controls;

import cz.mg.toolkit.component.Component;
import cz.mg.toolkit.component.Content;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;
import cz.mg.toolkit.utilities.sizepolices.FillParentSizePolicy;


public abstract class MenuItem extends Content {
    public static final String DEFAULT_DESIGN_NAME = "menu item";
    
    public MenuItem() {
        setHorizontallyOptional(this, true);
        setColumn(this, 0);
        setColumnSpan(this, Menu.COLUMN_COUNT);    
        setSizePolicy(this, new FillParentSizePolicy());
    }

    public abstract Component[] getComponents();
}