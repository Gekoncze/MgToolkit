package cz.mg.toolkit.event.events;

import cz.mg.toolkit.event.Event;


public class AfterLayoutEvent extends Event {
    public AfterLayoutEvent() {
    }
    
    @Override
    public boolean isDisabledFriendly() {
        return true;
    }
}
