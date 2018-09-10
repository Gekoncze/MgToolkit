package cz.mg.toolkit.event;

import cz.mg.toolkit.component.Component;


public abstract class EventContext {
    public abstract void pushState(Component component);
    public abstract void popState(Component component);
}
