package cz.mg.toolkit.event.events;

import cz.mg.toolkit.event.Event;


public class BeforeLayoutEvent extends Event {
    @Override
    public boolean isDisabledFriendly() {
        return true;
    }
}
