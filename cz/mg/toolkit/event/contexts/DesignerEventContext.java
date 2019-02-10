package cz.mg.toolkit.event.contexts;

import cz.mg.collections.list.List;
import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.toolkit.component.Component;
import cz.mg.toolkit.event.EventContext;
import cz.mg.toolkit.graphics.designer.Designer;
import cz.mg.toolkit.utilities.properties.PropertiesInterface;


public class DesignerEventContext extends EventContext {
    private final List<Designer> designers = new ChainList<>();

    public DesignerEventContext() {
    }
    
    public DesignerEventContext(Designer initialDesigner) {
        designers.addLast(initialDesigner);
    }
    
    @Override
    public final void pushState(Component component) {
        Designer designer = PropertiesInterface.getDesigner(component);
        if(designer != null) designers.addLast(designer);
    }

    @Override
    public final void popState(Component component) {
        Designer designer = PropertiesInterface.getDesigner(component);
        if(designer != null) designers.removeLast();
    }
    
    public final Designer getDesigner(){
        return designers.getLast();
    }
}
