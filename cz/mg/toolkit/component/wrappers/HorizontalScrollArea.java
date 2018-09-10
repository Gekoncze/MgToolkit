package cz.mg.toolkit.component.wrappers;

import cz.mg.toolkit.component.Wrapper;
import cz.mg.toolkit.component.components.HorizontalScrollBar;
import cz.mg.toolkit.event.adapters.AfterLayoutAdapter;
import cz.mg.toolkit.utilities.ScrollControlsVisibility;
import cz.mg.toolkit.event.adapters.LocalMouseWheelAdapter;
import cz.mg.toolkit.event.events.AfterLayoutEvent;
import cz.mg.toolkit.event.events.MouseWheelEvent;
import cz.mg.toolkit.layout.layouts.VerticalLayout;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;


public class HorizontalScrollArea extends Wrapper {
    private final HorizontalScrollBar horizontalScrollBar = new HorizontalScrollBar();
    private ScrollControlsVisibility scrollControlsVisibility = ScrollControlsVisibility.WHEN_NEEDED;
    
    public HorizontalScrollArea() {
        initComponent();
        initComponents();
        addEventListeners();
    }
    
    private void initComponent() {
        setLayout(new VerticalLayout());
        setFillParent(this);
    }
    
    private void initComponents() {
        setBorder(getContentPanel(), null);
        getChildren().addLast(horizontalScrollBar);        
        horizontalScrollBar.setScrollablePanel(getContentPanel());
    }
    
    private void addEventListeners() {
        getEventListeners().addLast(new LocalMouseWheelAdapter(){
            @Override
            public void onMouseWheelEventLeave(MouseWheelEvent e) {
                e.consume();
                if(!isCtrlPressed()) {
                    if(isDirectionUp(e)){
                        horizontalScrollBar.getLeftButton().trigger();
                    } else {
                        horizontalScrollBar.getRightButton().trigger();
                    }
                }
            }
        });
        getEventListeners().addLast(new AfterLayoutAdapter() {
            @Override
            public void onEventEnter(AfterLayoutEvent e) {
                switch(scrollControlsVisibility){
                    case ALWAYS:
                        autosetHorizontalScrollControlsVisibility(true);
                        break;
                    case WHEN_NEEDED:
                        autosetHorizontalScrollControlsVisibility(areHorizontalScrollControlsNeeded());
                        break;
                    case OPTIONAL:
                        return;
                    case NEVER:
                        autosetHorizontalScrollControlsVisibility(false);
                        break;
                    default:
                        throw new RuntimeException();
                }
            }
        });
    }
    
    private void autosetHorizontalScrollControlsVisibility(boolean shouldBeVisible){
        if(horizontalScrollBar.isHidden() && !shouldBeVisible) return;
        if(!horizontalScrollBar.isHidden() && shouldBeVisible) return;
        horizontalScrollBar.setHidden(!shouldBeVisible);
        relayout();
    }
    
    private boolean areHorizontalScrollControlsNeeded(){
        return getContentPanel().getContentWidth() > getAvailableWidth();
    }

    public final ScrollControlsVisibility getScrollControlsVisibility() {
        return scrollControlsVisibility;
    }

    public final void setScrollControlsVisibility(ScrollControlsVisibility scrollControlsVisibility) {
        this.scrollControlsVisibility = scrollControlsVisibility;
        relayout();
    }
}
