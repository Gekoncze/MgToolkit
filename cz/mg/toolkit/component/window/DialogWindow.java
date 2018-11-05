package cz.mg.toolkit.component.window;

import cz.mg.toolkit.event.adapters.WindowStateAdapter;
import cz.mg.toolkit.event.events.WindowStateEvent;
import static cz.mg.toolkit.utilities.properties.PropertiesInterface.*;


public class DialogWindow extends Window {
    public static final String DEFAULT_DESIGN_NAME = "dialog window";
    
    public DialogWindow(Window parent) {
        getEventListeners().addLast(new WindowStateAdapter(){
            @Override
            public void onEventEnter(WindowStateEvent e) {
                setDisabled(parent, isOpened());
                parent.redraw();
            }
        });
    }
}
