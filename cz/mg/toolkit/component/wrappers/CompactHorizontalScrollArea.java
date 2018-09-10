package cz.mg.toolkit.component.wrappers;

import cz.mg.toolkit.component.containers.Wrapper;
import cz.mg.toolkit.component.controls.buttons.special.HorizontalScrollButtonLeft;
import cz.mg.toolkit.component.controls.buttons.special.HorizontalScrollButtonRight;
import cz.mg.toolkit.event.adapters.AfterLayoutAdapter;
import cz.mg.toolkit.layout.layouts.HorizontalLayout;
import cz.mg.toolkit.utilities.ScrollControlsVisibility;
import cz.mg.toolkit.event.adapters.LocalMouseWheelAdapter;
import cz.mg.toolkit.event.events.AfterLayoutEvent;
import cz.mg.toolkit.event.events.MouseWheelEvent;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;


public class CompactHorizontalScrollArea extends Wrapper {
    private final HorizontalScrollButtonLeft leftButton = new HorizontalScrollButtonLeft();
    private final HorizontalScrollButtonRight rightButton = new HorizontalScrollButtonRight();
    private ScrollControlsVisibility scrollControlsVisibility = ScrollControlsVisibility.WHEN_NEEDED;

    public CompactHorizontalScrollArea() {
        initComponent();
        initComponents();
        addEventListeners();
    }
    
    private void initComponent() {
        setLayout(new HorizontalLayout());
        setFillParentWidth(this);
        setWrapContentHeight(this);
    }

    private void initComponents() {
        setWrapAndFillHeight(getContentPanel());
        setBorder(getContentPanel(), null);
        
        setWrapAndFillHeight(leftButton);
        setWrapAndFillHeight(rightButton);
        
        leftButton.setScrollPanel(getContentPanel());
        rightButton.setScrollPanel(getContentPanel());
        
        getChildren().addFirst(leftButton);
        getChildren().addLast(rightButton);
    }
    
    private void addEventListeners(){
        getEventListeners().addLast(new LocalMouseWheelAdapter() {
            @Override
            public void onMouseWheelEventLeave(MouseWheelEvent e) {
                e.consume();
                if(isDirectionUp(e)) leftButton.trigger();
                if(isDirectionDown(e)) rightButton.trigger();
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
        if(isHidden(leftButton) && isHidden(rightButton) && !shouldBeVisible) return;
        if(!isHidden(leftButton) && !isHidden(rightButton) && shouldBeVisible) return;
        setHidden(leftButton, !shouldBeVisible);
        setHidden(rightButton, !shouldBeVisible);
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
    }
}