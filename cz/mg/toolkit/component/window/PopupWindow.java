package cz.mg.toolkit.component.window;

import cz.mg.toolkit.event.adapters.WindowStateAdapter;
import cz.mg.toolkit.event.events.WindowStateEvent;


public class PopupWindow extends Window {
    public PopupWindow() {
        setDecorated(false);
        addEventListeners();
    }

    private void addEventListeners() {
        getEventListeners().addLast(new WindowStateAdapter() {
            @Override
            public void onEventEnter(WindowStateEvent e) {
                if(!isActivated()) closeImmediately();
            }
        });
    }
}
