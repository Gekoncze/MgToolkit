package cz.mg.toolkit.utilities;

import cz.mg.collections.list.List;
import cz.mg.collections.list.chainlist.ChainList;


public class SelectionGroup {
    private final List<Selectable> selectables = new ChainList<>();
    
    public final void addSelectable(Selectable selectable){
        selectables.addLast(selectable);
    }
    
    public final void removeSelectable(Selectable selectable){
        selectables.remove(selectable);
    }
    
    public final void removeAll(){
        selectables.clear();
    }
    
    public final void clearSelection(){
        for(Selectable selectable : selectables) selectable.setSelected(false);
    }
}
