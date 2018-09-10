package cz.mg.toolkit.component;

import cz.mg.collections.node.TreeNode;
import cz.mg.toolkit.component.window.Window;
import cz.mg.toolkit.event.Event;
import cz.mg.toolkit.event.EventListener;
import cz.mg.toolkit.event.EventObserver;
import cz.mg.toolkit.event.events.*;
import cz.mg.toolkit.event.adapters.BeforeLayoutAdapter;
import cz.mg.toolkit.event.adapters.DesignAdapter;
import cz.mg.toolkit.event.adapters.VisitAdapter;
import cz.mg.toolkit.event.contexts.DesignerEventContext;
import cz.mg.toolkit.graphics.Designer;
import cz.mg.toolkit.utilities.EventListeners;
import cz.mg.toolkit.utilities.properties.Properties;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;


public abstract class Component extends TreeNode<Component, Component> implements EventObserver {
    private double x, y;
    private double width, height;
    private final EventListeners eventListeners = new EventListeners();
    private Properties properties = new Properties();

    public Component() {
        addEventListeners();
    }
    
    private void addEventListeners(){
        getEventListeners().addLast(new BeforeLayoutAdapter() {
            @Override
            public void onEventLeave(BeforeLayoutEvent e) {
                updateSizeIntervals();
            }
        });
        
        getEventListeners().addLast(new VisitAdapter() {
            @Override
            public void onEventEnter(VisitEvent e) {
                e.onComponentEnter(Component.this);
            }

            @Override
            public void onEventLeave(VisitEvent e) {
                e.onComponentLeave(Component.this);
            }
        });
        
        getEventListeners().addLast(new DesignAdapter() {
            @Override
            public void onEventEnter(DesignEvent e) {
                getDesigner(e).design(Component.this);
            }
        });
        
        getParentChangeListeners().addLast(new TreeNodeParentChangeListener() {
            @Override
            public void parentChanged() {
                if(getParent() != null) design();
            }
        });
    }
    
    public final double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public final double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
    
    public final void setPosition(double x, double y) {
        setX(x);
        setY(y);
    }
    
    public final void move(double dx, double dy){
        this.x += dx;
        this.y += dy;
    }
    
    public final void moveHorizontally(double dx){
        this.x += dx;
    }
    
    public final void moveVertically(double dy){
        this.y += dy;
    }

    public final double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public final double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }
    
    public final void setSize(double width, double height){
        setWidth(width);
        setHeight(height);
    }
    
    private void updateSizeIntervals(){
        if(isWrapMinWidth(this)) setMinWidth(this, computeWrapWidth());
        if(isWrapMinHeight(this)) setMinHeight(this, computeWrapHeight());
        if(isWrapMaxWidth(this)) setMaxWidth(this, computeWrapWidth());
        if(isWrapMaxHeight(this)) setMaxHeight(this, computeWrapHeight());
    }
    
    public final boolean isEffectivelyDisabled(){
        boolean value = isDisabled(this);
        if(!value) if(getParent() != null) value |= getParent().isEffectivelyDisabled();
        return value;
    }

    public final EventListeners getEventListeners() {
        return eventListeners;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }
    
    public final void redraw(){
        raiseEvent(new RedrawEvent());
    }
    
    public final void relayout(){
        raiseEvent(new RelayoutEvent());
    }
    
    public final void redesign(){
        raiseEvent(new RedesignEvent());
    }
    
    public final void requestKeyboardFocus(){
        Window window = getWindow();
        if(window != null) window.setKeyboardFocus(this);
    }
    
    public final void requestMouseFocus(){
        Window window = getWindow();
        if(window != null) window.setMouseFocus(this);
    }
    
    public final void releaseKeyboardFocus(){
        Window window = getWindow();
        if(window != null) if(window.getKeyboardFocus() == this) window.setKeyboardFocus(null);
    }
    
    public final void releaseMouseFocus(){
        Window window = getWindow();
        if(window != null) if(window.getMouseFocus() == this) window.setMouseFocus(null);
    }
    
    public final boolean hasKeyboardFocus(){
        Window window = getWindow();
        if(window == null) return false;
        return window.getKeyboardFocus() == this;
    }
    
    public final boolean hasMouseFocus(){
        Window window = getWindow();
        if(window == null) return false;
        return window.getMouseFocus() == this;
    }
    
    public final Component getRoot(){
        Component current = this;
        while(current.getParent() != null) current = current.getParent();
        return current;
    }
    
    public final Window getWindow(){
        Component root = getRoot();
        if(root instanceof Window) return (Window) root;
        else return null;
    }
    
    public final void layout(){
        sendEvent(new BeforeLayoutEvent());
        sendEvent(new LayoutEvent());
        sendEvent(new AfterLayoutEvent());
    }
    
    public final void wrap(){
        sendEvent(new BeforeLayoutEvent());
        setWidth(getMinWidth(this));
        setHeight(getMinHeight(this));
        sendEvent(new LayoutEvent());
        sendEvent(new AfterLayoutEvent());
    }
    
    public final void design(){
        Component current = this;
        Designer designer = null;
        while(current != null){
            designer = getDesigner(current);
            if(designer != null) break;
            current = current.getParent();
        }
        if(designer == null) return;
        
        DesignEvent event = new DesignEvent();
        event.setEventContext(new DesignerEventContext(designer));
        sendEvent(event);
    }
    
    @Override
    public final void sendEvent(Event e){
        if(shallSkipEvent(e)) return;
        routeEvent(e);
    }
    
    public final boolean shallSkipEvent(Event e){
        return e.isConsumed() || isHidden(this) || (isDisabled(this) && !e.isDisabledFriendly());
    }
    
    public final boolean shallIgnoreEvent(Event e){
        if(e instanceof MouseEvent){
            Component focus = ((MouseEvent) e).getFocus();
            if(focus != null){
                return focus != this;
            }
        }
        if(e instanceof KeyboardEvent){
            Component focus = ((KeyboardEvent) e).getFocus();
            if(focus != null){
                return focus != this;
            }
        }
        return false;
    }
    
    protected void routeEvent(Event e){
        boolean ignored = shallIgnoreEvent(e);
        e.getEventContext().pushState(this);
        if(e.isConsumed()) return;
        if(!ignored) onEventEnter(e);
        if(e.isConsumed()) return;
        if(!ignored) onEventLeave(e);
        if(e.isConsumed()) return;
        e.getEventContext().popState(this);
    }
    
    public final void raiseEvent(Event e){
        e.getEventContext().pushState(this);
        if(shallSkipEvent(e)) return;
        onEventEnter(e);
        if(e.isConsumed()) return;
        if(getParent() != null) getParent().raiseEvent(e);
        if(e.isConsumed()) return;
        onEventLeave(e);
        e.getEventContext().popState(this);
    }
    
    public final void onEventEnter(Event e){
        for(EventListener listener : eventListeners) if(listener.acceptsEvent(e)) listener.onEventEnter(e);
    }
    
    public final void onEventLeave(Event e){
        for(EventListener listener : eventListeners) if(listener.acceptsEvent(e)) listener.onEventLeave(e);
    }
    
    public abstract double getContentWidth();
    public abstract double getContentHeight();
    
    public abstract double computeWrapWidth();
    public abstract double computeWrapHeight();
    
    public final double getAvailableWidth(){
        return getWidth() - getLeftPadding(this) - getRightPadding(this);
    }
    
    public final double getAvailableHeight(){
        return getHeight() - getTopPadding(this) - getBottomPadding(this);
    }
    
    public boolean isInside(double px, double py){
        return px >= x && py >= y && px < (x + width) && py < (y + height);
    }
}