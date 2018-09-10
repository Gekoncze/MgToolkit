package cz.mg.toolkit.component;

import cz.mg.toolkit.component.components.Menu;
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