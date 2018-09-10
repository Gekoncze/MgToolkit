package cz.mg.toolkit.event.contexts;

import cz.mg.collections.list.List;
import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.toolkit.component.Component;
import cz.mg.toolkit.event.EventContext;
import cz.mg.toolkit.graphics.Designer;


public class DesignerEventContext extends EventContext {
    private final List<Designer> designers = new ChainList<>();

    public DesignerEventContext() {
    }
    
    public DesignerEventContext(Designer initialDesigner) {
        designers.addLast(initialDesigner);
    }
    
    @Override
    public void pushState(Component component) {
        Designer designer = cz.mg.toolkit.utilities.properties.PropertiesInterface.getDesigner(component);
        if(designer != null) designers.addLast(designer);
    }

    @Override
    public void popState(Component component) {
        Designer designer = cz.mg.toolkit.utilities.properties.PropertiesInterface.getDesigner(component);
        if(designer != null) designers.removeLast();
    }
    
    public Designer getDesigner(){
        return designers.getLast();
    }
}
