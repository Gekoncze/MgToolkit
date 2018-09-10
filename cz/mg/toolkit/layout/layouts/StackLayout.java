package cz.mg.toolkit.layout.layouts;

import cz.mg.collections.list.List;
import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.collections.list.quicklist.QuickList;
import cz.mg.toolkit.component.Component;
import cz.mg.toolkit.component.Container;
import cz.mg.toolkit.component.Content;
import cz.mg.toolkit.event.events.BeforeLayoutEvent;
import cz.mg.toolkit.layout.Layout;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;


public abstract class StackLayout extends Layout {
    protected static final BeforeLayoutEvent BEFORE_LAYOUT_EVENT = new BeforeLayoutEvent();
    protected final List<Stack> stacks = new QuickList<>();

    public StackLayout() {
    }

    public StackLayout(int stackCount) {
        setStackCount(stackCount);
    }
    
    public final List<Stack> getStacks() {
        return stacks;
    }
    
    public final void setStackCount(int stackCount){
        while(stackCount < stacks.count()) stacks.removeLast();
        while(stackCount > stacks.count()) stacks.addLast(createStack());
    }
    
    @Override
    public void onBeforeLayoutLeave(Container parent) {
        for(Stack stack : stacks){
            stack.setHidden(true);
            stack.setContentSize(0, 0);
            stack.components.clear();
            setHorizontalSpacing(stack, getHorizontalSpacing(parent));
            setVerticalSpacing(stack, getVerticalSpacing(parent));
        }
        
        for(Component component : parent.getChildren()) {
            if(component.isHidden()) continue;
            int s = getStack(component);
            Stack stack = stacks.get(s);
            stack.setHidden(false);
            stack.components.addLast(component);
        }
        
        for(Stack stack : stacks){
            stack.computeContentSize();
            stack.sendEvent(BEFORE_LAYOUT_EVENT);
        }
    }
    
    public abstract int getStack(Component component);
    public abstract Stack createStack();
    
    public static abstract class Stack extends Content {
        final List<Component> components = new ChainList<>();

        public List<Component> getComponents() {
            return components;
        }
        
        public abstract void computeContentSize();
    }
}
