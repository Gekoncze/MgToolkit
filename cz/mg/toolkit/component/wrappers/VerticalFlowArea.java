package cz.mg.toolkit.component.wrappers;

import cz.mg.toolkit.component.Component;
import cz.mg.toolkit.event.adapters.AfterLayoutAdapter;
import cz.mg.toolkit.event.events.AfterLayoutEvent;
import cz.mg.toolkit.layout.layouts.StackLayout;
import cz.mg.toolkit.layout.layouts.VerticalStackLayout;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;


public class VerticalFlowArea extends ScrollArea {
    private final VerticalStackLayout contentLayout = new VerticalStackLayout();
    
    public VerticalFlowArea() {
        initComponent();
        addEventListeners();
    }

    private void initComponent() {
        contentLayout.setStackCount(1);
        getContentPanel().setLayout(contentLayout);
        setFillParent(this);
    }

    private void addEventListeners() {
        getEventListeners().addLast(new AfterLayoutAdapter() {
            @Override
            public void onEventEnter(AfterLayoutEvent e) {
                fixFlow();
            }
        });
    }
    
    private void fixFlow(){
        if(!canBeFixed()) return;
        double availableWidth = getContentPanel().getAvailableWidth();
        double horizontalSpacing = getHorizontalSpacing(getContentPanel());
        int sy = 0;
        double currentWidth = 0;
        boolean first = true;
        
        for(Component component : getContentPanel().getChildren()){
            if(component.isHidden()) continue;
            if(!first) currentWidth += horizontalSpacing;
            currentWidth += getMinWidth(component);
            if(currentWidth > availableWidth && !first){
                sy++;
                currentWidth = getMinWidth(component);
            }
            setRow(component, sy);
            first = false;
        }
        contentLayout.setStackCount(sy+1);
        relayout();
    }
    
    private boolean canBeFixed(){
        double availableWidth = getContentPanel().getAvailableWidth();
        double horizontalSpacing = getHorizontalSpacing(getContentPanel());
        StackLayout.Stack previousStack = null;
        for(StackLayout.Stack stack : contentLayout.getStacks()){
            if(stack.getComponents().count() < 1) return true;
            if(stack.getComponents().count() > 1 && getMinWidth(stack) > availableWidth) return true;
            if(previousStack != null){
                double possibleSize = getMinWidth(previousStack) + getMinWidth(stack.getComponents().getFirst()) + horizontalSpacing;
                if(possibleSize <= availableWidth) return true;
            }
            previousStack = stack;
        }
        return false;
    }
}
