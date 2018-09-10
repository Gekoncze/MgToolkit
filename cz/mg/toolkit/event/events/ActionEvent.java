package cz.mg.toolkit.event.events;

import cz.mg.toolkit.event.Event;


public class ActionEvent extends Event {
    private final Object source;
    
    public ActionEvent(Object source) {
        this.source = source;
    }
    
    public Object getSource() {
        return source;
    }
}
