package cz.mg.toolkit.component.controls.menuitems;

import cz.mg.toolkit.component.Component;
import cz.mg.toolkit.component.controls.MenuItem;
import cz.mg.toolkit.component.contents.VerticalSeparator;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;


public class SeparatorItem extends MenuItem {
    private final VerticalSeparator separator = new VerticalSeparator();
    private final Component[] components = new Component[]{separator};
    
    public SeparatorItem() {
        initComponents();
    }
    
    private void initComponents() {
        setColumn(separator, 0);
        setColumnSpan(separator, 4);
        setHorizontallyOptional(separator, true);
        setPadding(separator, 0);
    }
    
    @Override
    public Component[] getComponents() {
        return components;
    }
}
