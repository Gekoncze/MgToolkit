package cz.mg.toolkit.component.wrappers;

import cz.mg.toolkit.component.Component;
import cz.mg.toolkit.event.adapters.AfterLayoutAdapter;
import cz.mg.toolkit.event.events.AfterLayoutEvent;
import cz.mg.toolkit.layout.layouts.HorizontalStackLayout;
import cz.mg.toolkit.layout.layouts.StackLayout;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;
import cz.mg.toolkit.utilities.sizepolices.FillParentSizePolicy;


public class HorizontalFlowArea extends ScrollArea {
    private final HorizontalStackLayout contentLayout = new HorizontalStackLayout();
    
    public HorizontalFlowArea() {
        initComponent();
        addEventListeners();
    }

    private void initComponent() {
        contentLayout.setStackCount(1);
        getContentPanel().setLayout(contentLayout);
        setSizePolicy(this, new FillParentSizePolicy());
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
        double availableHeight = getContentPanel().getAvailableHeight();
        double verticalSpacing = getVerticalSpacing(getContentPanel());
        int sx = 0;
        double currentHeight = 0;
        boolean first = true;
        
        for(Component component : getContentPanel().getChildren()){
            if(isHidden(component)) continue;
            if(!first) currentHeight += verticalSpacing;
            currentHeight += getMinHeight(component);
            if(currentHeight > availableHeight && !first){
                sx++;
                currentHeight = getMinHeight(component);
            }
            setColumn(component, sx);
            first = false;
        }
        contentLayout.setStackCount(sx+1);
        relayout();
    }
    
    private boolean canBeFixed(){
        double availableHeight = getContentPanel().getAvailableHeight();
        double verticalSpacing = getVerticalSpacing(getContentPanel());
        StackLayout.Stack previousStack = null;
        for(StackLayout.Stack stack : contentLayout.getStacks()){
            if(stack.getComponents().count() < 1) return true;
            if(stack.getComponents().count() > 1 && getMinHeight(stack) > availableHeight) return true;
            if(previousStack != null){
                double possibleSize = getMinHeight(previousStack) + getMinHeight(stack.getComponents().getFirst()) + verticalSpacing;
                if(possibleSize <= availableHeight) return true;
            }
            previousStack = stack;
        }
        return false;
    }
}
