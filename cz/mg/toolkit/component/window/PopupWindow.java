package cz.mg.toolkit.component.window;

import cz.mg.toolkit.event.adapters.WindowStateAdapter;
import cz.mg.toolkit.event.events.WindowStateEvent;


public class PopupWindow extends Window {
    public PopupWindow() {
        initComponent();
        addEventListeners();
    }

    private void initComponent(){
        setDecorated(false);
        setAutoWrap(true);
    }
    
    private void addEventListeners() {
        getEventListeners().addLast(new WindowStateAdapter() {
            @Override
            public void onEventEnter(WindowStateEvent e) {
                if(e.getStateChange() == WindowStateEvent.StateChange.DEACTIVATED) closeImmediately();
            }
        });
    }
}
