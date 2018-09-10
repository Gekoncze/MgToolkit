package cz.mg.toolkit.utilities;

import cz.mg.collections.list.List;
import cz.mg.collections.list.chainlist.ChainList;


public class SelectionGroup {
    private final List<Selectable> selectables = new ChainList<>();
    
    public void addSelectable(Selectable selectable){
        selectables.addLast(selectable);
    }
    
    public void removeSelectable(Selectable selectable){
        selectables.remove(selectable);
    }
    
    public void clearSelection(){
        for(Selectable selectable : selectables) selectable.setSelected(false);
    }
}
