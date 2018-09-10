package cz.mg.toolkit.event.contexts;

import cz.mg.toolkit.component.Component;
import cz.mg.toolkit.event.EventContext;


public class DummyEventContext extends EventContext {
    @Override
    public void pushState(Component component) {
    }

    @Override
    public void popState(Component component) {
    }
}
