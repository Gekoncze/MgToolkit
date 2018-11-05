package cz.mg.toolkit.component.controls;

import cz.mg.toolkit.component.containers.Panel;
import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.toolkit.component.Component;
import cz.mg.toolkit.layout.layouts.GridLayout;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;


public class Menu extends Panel {
    public static final String DEFAULT_DESIGN_NAME = "menu";
    public static final int COLUMN_COUNT = 4;
    
    private final GridLayout grid = new GridLayout(COLUMN_COUNT, 1);
    private final ChainList<MenuItem> items = new ChainList<>();
    
    public Menu() {
        setLayout(grid);
    }

    public ChainList<MenuItem> getItems() {
        return items;
    }
    
    public void updateComponents(){
        grid.setRowCount(items.count());
        getChildren().clear();
        
        int y = 0;
        for(MenuItem item : items){
            setRow(item, y);
            item.setParent(this);
            Component[] components = item.getComponents();
            for (Component component : components) {
                setRow(component, y);
                component.setParent(this);
            }
            y++;
        }
    }
}
