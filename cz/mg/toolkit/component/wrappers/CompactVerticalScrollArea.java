package cz.mg.toolkit.component.wrappers;

import cz.mg.toolkit.component.containers.Wrapper;
import cz.mg.toolkit.component.controls.buttons.special.DownScrollButton;
import cz.mg.toolkit.component.controls.buttons.special.UpScrollButton;
import cz.mg.toolkit.event.adapters.AfterLayoutAdapter;
import cz.mg.toolkit.layout.layouts.VerticalLayout;
import cz.mg.toolkit.utilities.ScrollControlsVisibility;
import cz.mg.toolkit.event.adapters.LocalMouseWheelAdapter;
import cz.mg.toolkit.event.events.AfterLayoutEvent;
import cz.mg.toolkit.event.events.MouseWheelEvent;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;
import cz.mg.toolkit.utilities.sizepolices.FillParentSizePolicy;
import cz.mg.toolkit.utilities.sizepolices.WrapAndFillSizePolicy;
import cz.mg.toolkit.utilities.sizepolices.WrapContentSizePolicy;


public class CompactVerticalScrollArea extends Wrapper {
    private final UpScrollButton upButton = new UpScrollButton();
    private final DownScrollButton downButton = new DownScrollButton();
    private ScrollControlsVisibility scrollControlsVisibility = ScrollControlsVisibility.WHEN_NEEDED;

    public CompactVerticalScrollArea() {
        initComponent();
        initComponents();
        addEventListeners();
    }
    
    private void initComponent(){
        setLayout(new VerticalLayout());
        setHorizontalSizePolicy(this, new WrapContentSizePolicy());
        setVerticalSizePolicy(this, new FillParentSizePolicy());
    }

    private void initComponents() {
        setHorizontalSizePolicy(getContentPanel(), new WrapAndFillSizePolicy());
        
        setHorizontalSizePolicy(upButton, new WrapAndFillSizePolicy());
        setHorizontalSizePolicy(downButton, new WrapAndFillSizePolicy());
                
        upButton.setScrollPanel(getContentPanel());
        downButton.setScrollPanel(getContentPanel());
        
        getChildren().addFirst(upButton);
        getChildren().addLast(downButton);
    }
    
    private void addEventListeners(){
        getEventListeners().addLast(new LocalMouseWheelAdapter() {
            @Override
            public void onMouseWheelEventLeave(MouseWheelEvent e) {
                e.consume();
                if(isDirectionUp(e)) upButton.trigger();
                if(isDirectionDown(e)) downButton.trigger();
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
        if(isHidden(upButton) && isHidden(downButton) && !shouldBeVisible) return;
        if(!isHidden(upButton) && !isHidden(downButton) && shouldBeVisible) return;
        setHidden(upButton, !shouldBeVisible);
        setHidden(downButton, !shouldBeVisible);
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
    }
}
