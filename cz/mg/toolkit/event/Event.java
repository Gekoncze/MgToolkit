package cz.mg.toolkit.event;

import cz.mg.toolkit.event.contexts.DummyEventContext;


public abstract class Event {
    private boolean consumed = false;
    private EventContext eventContext = new DummyEventContext();
    
    public final boolean isConsumed() {
        return consumed;
    }

    public void setConsumed(boolean consumed) {
        this.consumed = consumed;
    }
    
    public final void consume() {
        this.consumed = true;
    }

    public final EventContext getEventContext() {
        return eventContext;
    }

    public final void setEventContext(EventContext eventContext) {
        this.eventContext = eventContext;
    }
    
    public boolean isDisabledFriendly(){
        return false;
    }
}
