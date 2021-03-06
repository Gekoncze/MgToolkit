package cz.mg.toolkit.component.wrappers;

import cz.mg.toolkit.component.containers.Wrapper;
import cz.mg.toolkit.component.controls.VerticalScrollBar;
import cz.mg.toolkit.event.adapters.AfterLayoutAdapter;
import cz.mg.toolkit.utilities.ScrollControlsVisibility;
import cz.mg.toolkit.event.adapters.LocalMouseWheelAdapter;
import cz.mg.toolkit.event.events.AfterLayoutEvent;
import cz.mg.toolkit.event.events.MouseWheelEvent;
import cz.mg.toolkit.layout.layouts.HorizontalLayout;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;
import cz.mg.toolkit.utilities.sizepolices.FillParentSizePolicy;


public class VerticalScrollArea extends Wrapper {
    public static final String DEFAULT_DESIGN_NAME = "vertical scroll area";
    
    private final VerticalScrollBar verticalScrollBar = new VerticalScrollBar();
    private ScrollControlsVisibility scrollControlsVisibility = ScrollControlsVisibility.WHEN_NEEDED;
    
    public VerticalScrollArea() {
        initComponent();
        initComponents();
        addEventListeners();
    }
    
    private void initComponent() {
        setLayout(new HorizontalLayout());
        setSizePolicy(this, new FillParentSizePolicy());
    }
    
    private void initComponents() {
        getChildren().addLast(verticalScrollBar);        
        verticalScrollBar.setScrollablePanel(getContentPanel());
    }
    
    private void addEventListeners() {
        getEventListeners().addLast(new LocalMouseWheelAdapter(){
            @Override
            public void onMouseWheelEventLeave(MouseWheelEvent e) {
                e.consume();
                if(!isCtrlPressed()) {
                    if(isDirectionUp(e)){
                        verticalScrollBar.getUpButton().trigger();
                    } else {
                        verticalScrollBar.getDownButton().trigger();
                    }
                }
            }
        });
        getEventListeners().addLast(new AfterLayoutAdapter() {
            @Override
            public void onEventEnter(AfterLayoutEvent e) {
                switch(scrollControlsVisibility){
                    case ALWAYS:
                        autosetVerticalScrollControlsVisibility(true);
                        break;
                    case WHEN_NEEDED:
                        autosetVerticalScrollControlsVisibility(areVerticalScrollControlsNeeded());
                        break;
                    case OPTIONAL:
                        return;
                    case NEVER:
                        autosetVerticalScrollControlsVisibility(false);
                        break;
                    default:
                        throw new RuntimeException();
                }
            }
        });
    }
    
    private void autosetVerticalScrollControlsVisibility(boolean shouldBeVisible){
        if(isHidden(verticalScrollBar) && !shouldBeVisible) return;
        if(!isHidden(verticalScrollBar) && shouldBeVisible) return;
        setHidden(verticalScrollBar, !shouldBeVisible);
        relayout();
    }
    
    private boolean areVerticalScrollControlsNeeded(){
        return getContentPanel().getContentHeight() > getAvailableHeight();
    }

    public final ScrollControlsVisibility getScrollControlsVisibility() {
        return scrollControlsVisibility;
    }

    public final void setScrollControlsVisibility(ScrollControlsVisibility scrollControlsVisibility) {
        this.scrollControlsVisibility = scrollControlsVisibility;
        relayout();
    }
}
