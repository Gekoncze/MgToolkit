package cz.mg.toolkit.component;

import cz.mg.toolkit.component.window.Window;
import cz.mg.toolkit.event.Event;
import cz.mg.toolkit.event.EventListener;
import cz.mg.toolkit.event.EventObserver;
import cz.mg.toolkit.event.events.*;
import cz.mg.toolkit.event.adapters.BeforeLayoutAdapter;
import cz.mg.toolkit.event.adapters.DesignAdapter;
import cz.mg.toolkit.event.adapters.VisitAdapter;
import cz.mg.toolkit.utilities.EventListeners;
import cz.mg.toolkit.utilities.properties.Properties;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;


public abstract class Component extends Shape implements EventObserver {
    private boolean hidden = false;
    private boolean disabled = false;
    private boolean highlighted = false;
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
                e.getDesigner().design(Component.this);
            }
        });
    }
    
    private void updateSizeIntervals(){
        if(isWrapMinWidth(this)) setMinWidth(this, computeWrapWidth());
        if(isWrapMinHeight(this)) setMinHeight(this, computeWrapHeight());
        if(isWrapMaxWidth(this)) setMaxWidth(this, computeWrapWidth());
        if(isWrapMaxHeight(this)) setMaxHeight(this, computeWrapHeight());
    }
    
    public final boolean isEffectivelyDisabled(){
        if(isDisabled()) return true;
        boolean value = isDisabled();
        if(getParent() != null) value |= getParent().isEffectivelyDisabled();
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
    
    @Override
    public final void sendEvent(Event e){
        if(shallSkipEvent(e)) return;
        routeEvent(e);
    }
    
    public final boolean shallSkipEvent(Event e){
        return e.isConsumed() || isHidden() || (isEffectivelyDisabled() && !e.isDisabledFriendly());
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
    
    public final boolean isHidden() {
        return hidden;
    }

    public final void setHidden(boolean hidden) {
        this.hidden = hidden;
    }
    
    public final boolean isDisabled() {
        return disabled;
    }

    public final void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public final boolean isHighlighted() {
        return highlighted;
    }

    public final void setHighlighted(boolean highlighted) {
        this.highlighted = highlighted;
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
}
