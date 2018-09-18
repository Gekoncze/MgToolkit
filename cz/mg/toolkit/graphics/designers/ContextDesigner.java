package cz.mg.toolkit.graphics.designers;

import cz.mg.collections.list.List;
import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.toolkit.component.Component;
import cz.mg.toolkit.graphics.Designer;


public abstract class ContextDesigner implements Designer {
    private final List<Context> contexts = new ChainList<>();
    
    @Override
    public final void design(Component component) {
        getContext().design(component);
    }
    
    public final Context getContext(){
        if(contexts.isEmpty()) return getDefaultContext();
        else return contexts.getLast();
    }
    
    @Override
    public final void pushState(Component component){
        Context newContext = getComponentContext(component);
        if(newContext != null) contexts.addLast(newContext);
    }
    
    @Override
    public final void popState(Component component){
        Context oldContext = getComponentContext(component);
        if(oldContext != null) contexts.removeLast();
    }
    
    @Override
    public final void clearState() {
        contexts.clear();
    }
    
    protected abstract Context getDefaultContext();
    protected abstract Context getComponentContext(Component component);
    
    public static abstract class Context {
        public abstract void design(Component component);
    }
}
