package cz.mg.toolkit.event.events;

import cz.mg.toolkit.component.Component;
import cz.mg.toolkit.event.Event;


public abstract class VisitEvent extends Event {
    public abstract void onComponentEnter(Component component);
    public abstract void onComponentLeave(Component component);
}
