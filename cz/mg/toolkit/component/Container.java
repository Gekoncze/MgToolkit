package cz.mg.toolkit.component;

import cz.mg.toolkit.event.Event;
import cz.mg.toolkit.event.adapters.AfterLayoutAdapter;
import cz.mg.toolkit.layout.Layout;
import cz.mg.toolkit.event.adapters.BeforeLayoutAdapter;
import cz.mg.toolkit.event.events.LayoutEvent;
import cz.mg.toolkit.layout.layouts.FreeLayout;
import cz.mg.toolkit.event.adapters.LayoutAdapter;
import cz.mg.toolkit.event.events.AfterLayoutEvent;
import cz.mg.toolkit.event.events.BeforeLayoutEvent;
import cz.mg.toolkit.layout.reshapes.Reshape;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;


public abstract class Container extends Component {
    public static String DEFAULT_DESIGN_NAME = "container";
    
    private Layout layout = new FreeLayout();
    
    private double wrapWidth = 0;
    private double wrapHeight = 0;
    
    private double wrapMinX = Double.MAX_VALUE;
    private double wrapMaxX = Double.MIN_VALUE;
    private double wrapMinY = Double.MAX_VALUE;
    private double wrapMaxY = Double.MIN_VALUE;
    
    public Container() {
        addEventListeners();
    }

    private void addEventListeners(){
        getEventListeners().addLast(new LayoutAdapter() {
            @Override
            public void onEventEnter(LayoutEvent e) {
                layoutComponents();
                updateWrap();
                alignContent();
                updateWrap();
                scrollComponents();
            }
        });
        
        getEventListeners().addFirst(new BeforeLayoutAdapter() {
            @Override
            public void onEventEnter(BeforeLayoutEvent e) {
                layout.onBeforeLayoutEnter(Container.this);
            }

            @Override
            public void onEventLeave(BeforeLayoutEvent e) {
                layout.onBeforeLayoutLeave(Container.this);
            }
        });
        
        getEventListeners().addFirst(new AfterLayoutAdapter() {
            @Override
            public void onEventEnter(AfterLayoutEvent e) {
                layout.onAfterLayoutEnter(Container.this);
            }

            @Override
            public void onEventLeave(AfterLayoutEvent e) {
                layout.onAfterLayoutLeave(Container.this);
            }
        });
    }
    
    private void layoutComponents() {
        layout.reshapeComponents(Container.this);
    }
    
    private void alignContent() {
        double dx = Reshape.align(getWidth(), wrapWidth, getHorizontalContentAlignment(this));
        double dy = Reshape.align(getHeight(), wrapHeight, getVerticalContentAlignment(this));
        for(Component component : getChildren()) component.move(dx, dy);
    }
    
    private void scrollComponents() {
        if(!isUnlimitedHorizontalScroll(Container.this)) setHorizontalScroll(Container.this, Math.max(getHorizontalScroll(Container.this), getMinHorizontalScroll()));
        if(!isUnlimitedHorizontalScroll(Container.this)) setHorizontalScroll(Container.this, Math.min(getHorizontalScroll(Container.this), getMaxHorizontalScroll()));
        if(!isUnlimitedVerticalScroll(Container.this)) setVerticalScroll(Container.this, Math.max(getVerticalScroll(Container.this), getMinVerticalScroll()));
        if(!isUnlimitedVerticalScroll(Container.this)) setVerticalScroll(Container.this, Math.min(getVerticalScroll(Container.this), getMaxVerticalScroll()));
        
        for(Component component : getChildren()){
            component.setX(component.getX() - getHorizontalScroll(this));
            component.setY(component.getY() - getVerticalScroll(this));
        };
    }
    
    public final double getMinHorizontalScroll(){
        if(wrapWidth <= getWidth()) return 0;
        return wrapMinX;
    }
    
    public final double getMaxHorizontalScroll(){
        if(wrapWidth <= getWidth()) return 0;
        return wrapMaxX - getWidth();
    }
    
    public final double getMinVerticalScroll(){
        if(wrapHeight <= getHeight()) return 0;
        return wrapMinY;
    }
    
    public final double getMaxVerticalScroll(){
        if(wrapHeight <= getHeight()) return 0;
        return wrapMaxY - getHeight();
    }

    public final Layout getLayout() {
        return layout;
    }

    public final void setLayout(Layout layout) {
        if(layout == null) throw new NullPointerException();
        this.layout = layout;
    }
    
    @Override
    public final double getContentWidth() {
        return wrapWidth;
    }

    @Override
    public final double getContentHeight() {
        return wrapHeight;
    }
    
    private void updateWrap(){
        wrapMinX = Double.MAX_VALUE;
        wrapMaxX = Double.MIN_VALUE;
        wrapMinY = Double.MAX_VALUE;
        wrapMaxY = Double.MIN_VALUE;
        
        wrapWidth = getLeftPadding(this) + getRightPadding(this);
        wrapHeight = getTopPadding(this) + getBottomPadding(this);
        
        if(getChildren().count() <= 0) return;
        
        for(Component component : getChildren()){
            if(isHidden(component)) continue;
            double currentMinX = component.getX();
            double currentMaxX = component.getX() + component.getWidth();
            double currentMinY = component.getY();
            double currentMaxY = component.getY() + component.getHeight();          
            if(currentMinX < wrapMinX) wrapMinX = currentMinX;
            if(currentMaxX > wrapMaxX) wrapMaxX = currentMaxX;
            if(currentMinY < wrapMinY) wrapMinY = currentMinY;
            if(currentMaxY > wrapMaxY) wrapMaxY = currentMaxY;
        }
        
        wrapMinX -= getLeftPadding(this);
        wrapMaxX += getRightPadding(this);
        wrapMinY -= getTopPadding(this);
        wrapMaxY += getBottomPadding(this);
        
        wrapWidth += wrapMaxX - wrapMinX;
        wrapHeight += wrapMaxY - wrapMinY;
    }

    @Override
    public double computeWrapWidth() {
        return layout.computeMinWidth(this);
    }

    @Override
    public double computeWrapHeight() {
        return layout.computeMinHeight(this);
    }
    
    @Override
    protected void routeEvent(Event e) {
        boolean ignored = shallIgnoreEvent(e);
        e.getEventContext().pushState(this);
        if(e.isConsumed()) return;
        if(!ignored) onEventEnter(e);
        if(e.isConsumed()) return;
        
        for(Component component : getChildren()){
            if(e.isConsumed()) return;
            component.sendEvent(e);
            if(e.isConsumed()) return;
        }
        
        if(e.isConsumed()) return;
        if(!ignored) onEventLeave(e);
        if(e.isConsumed()) return;
        e.getEventContext().popState(this);
    }
}
