package cz.mg.toolkit.component.controls.menuitems;

import cz.mg.toolkit.component.Component;
import cz.mg.toolkit.component.controls.MenuItem;
import cz.mg.toolkit.component.contents.VerticalSeparator;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;


public class SeparatorMenuItem extends MenuItem {
    public static final String DEFAULT_DESIGN_NAME = "separator menu item";
    
    private final VerticalSeparator separator = new VerticalSeparator();
    private final Component[] components = new Component[]{separator};
    
    public SeparatorMenuItem() {
        initComponents();
    }
    
    private void initComponents() {
        setColumn(separator, 0);
        setColumnSpan(separator, 4);
        setHorizontallyOptional(separator, true);
    }
    
    @Override
    public Component[] getComponents() {
        return components;
    }
}
