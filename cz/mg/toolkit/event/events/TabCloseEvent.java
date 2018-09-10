package cz.mg.toolkit.event.events;

import cz.mg.toolkit.component.wrappers.HorizontalTabArea.Tab;
import cz.mg.toolkit.event.Event;


public class TabCloseEvent extends Event {
    private final Tab tab;

    public TabCloseEvent(Tab tab) {
        this.tab = tab;
    }

    public Tab getTab() {
        return tab;
    }
}
