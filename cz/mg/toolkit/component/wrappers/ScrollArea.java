package cz.mg.toolkit.component.wrappers;

import cz.mg.toolkit.component.containers.Wrapper;
import cz.mg.toolkit.component.controls.HorizontalScrollBar;
import cz.mg.toolkit.component.controls.TextInput;
import cz.mg.toolkit.component.controls.VerticalScrollBar;
import cz.mg.toolkit.event.adapters.AfterLayoutAdapter;
import cz.mg.toolkit.utilities.ScrollControlsVisibility;
import cz.mg.toolkit.event.adapters.LocalMouseWheelAdapter;
import cz.mg.toolkit.event.events.AfterLayoutEvent;
import cz.mg.toolkit.event.events.MouseWheelEvent;
import cz.mg.toolkit.layout.layouts.GridLayout;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;
import cz.mg.toolkit.utilities.sizepolices.FillParentSizePolicy;
import cz.mg.toolkit.utilities.sizepolices.WrapAndFillSizePolicy;


public class ScrollArea extends Wrapper {
    public static final String DEFAULT_DESIGN_NAME = "scroll area";
    
    private final GridLayout grid = new GridLayout(2, 2);
    private final VerticalScrollBar verticalScrollBar = new VerticalScrollBar();
    private final HorizontalScrollBar horizontalScrollBar = new HorizontalScrollBar();
    private ScrollControlsVisibility scrollControlsVisibility = ScrollControlsVisibility.WHEN_NEEDED;
    
    public ScrollArea() {
        initComponent();
        initComponents();
        addEventListeners();
    }
    
    private void initComponent() {
        setLayout(grid);
        setSizePolicy(this, new FillParentSizePolicy());
    }
    
    private void initComponents() {
        setColumn(verticalScrollBar, 1);
        setRow(horizontalScrollBar, 1);
        
        verticalScrollBar.setScrollablePanel(getContentPanel());
        horizontalScrollBar.setScrollablePanel(getContentPanel());
        
        getChildren().addLast(verticalScrollBar);
        getChildren().addLast(horizontalScrollBar);
        
        setHorizontalSizePolicy(grid.getColumns().get(0), new WrapAndFillSizePolicy());
        setVerticalSizePolicy(grid.getRows().get(0), new WrapAndFillSizePolicy());
    }
    
    private void addEventListeners() {
        getEventListeners().addLast(new LocalMouseWheelAdapter(){
            @Override
            public void onMouseWheelEventLeave(MouseWheelEvent e) {
                e.consume();
                if(isShiftPressed()){
                    if(isDirectionUp(e)){
                        horizontalScrollBar.getLeftButton().trigger();
                    } else {
                        horizontalScrollBar.getRightButton().trigger();
                    }
                } else if(!isCtrlPressed()) {
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
                        autosetHorizontalScrollControlsVisibility(true);
                        autosetVerticalScrollControlsVisibility(true);
                        break;
                    case WHEN_NEEDED:
                        autosetHorizontalScrollControlsVisibility(areHorizontalScrollControlsNeeded());
                        autosetVerticalScrollControlsVisibility(areVerticalScrollControlsNeeded());
                        break;
                    case OPTIONAL:
                        return;
                    case NEVER:
                        autosetHorizontalScrollControlsVisibility(false);
                        autosetVerticalScrollControlsVisibility(false);
                        break;
                    default:
                        throw new RuntimeException();
                }
            }
        });
    }
    
    private void autosetHorizontalScrollControlsVisibility(boolean shouldBeVisible){
        if(isHidden(horizontalScrollBar) && !shouldBeVisible) return;
        if(!isHidden(horizontalScrollBar) && shouldBeVisible) return;
        setHidden(horizontalScrollBar, !shouldBeVisible);
        relayout();
    }
    
    private void autosetVerticalScrollControlsVisibility(boolean shouldBeVisible){
        if(isHidden(verticalScrollBar) && !shouldBeVisible) return;
        if(!isHidden(verticalScrollBar) && shouldBeVisible) return;
        setHidden(verticalScrollBar, !shouldBeVisible);
        relayout();
    }
    
    private boolean areHorizontalScrollControlsNeeded(){
        return getContentPanel().getContentWidth() > getContentPanel().getAvailableWidth();
    }
    
    private boolean areVerticalScrollControlsNeeded(){
        return getContentPanel().getContentHeight() > getContentPanel().getAvailableHeight();
    }

    public final ScrollControlsVisibility getScrollControlsVisibility() {
        return scrollControlsVisibility;
    }

    public final void setScrollControlsVisibility(ScrollControlsVisibility scrollControlsVisibility) {
        this.scrollControlsVisibility = scrollControlsVisibility;
        relayout();
    }
    
    public void scroll(double deltaX, double deltaY){
        scrollHorizontally(getContentPanel(), deltaX);
        scrollVertically(getContentPanel(), deltaY);
    }
}
