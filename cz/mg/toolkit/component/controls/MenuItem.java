package cz.mg.toolkit.component.controls;

import cz.mg.toolkit.component.Component;
import cz.mg.toolkit.component.Content;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;


public abstract class MenuItem extends Content {
    public MenuItem() {
        setHorizontallyOptional(this, true);
        setColumn(this, 0);
        setColumnSpan(this, Menu.COLUMN_COUNT);        
        setFillParentWidth(this);
        setFillParentHeight(this);
    }

    public abstract Component[] getComponents();
}