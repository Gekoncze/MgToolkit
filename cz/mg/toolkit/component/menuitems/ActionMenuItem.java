package cz.mg.toolkit.component.menuitems;

import cz.mg.toolkit.component.MenuItem;
import cz.mg.toolkit.event.adapters.LocalMouseButtonAdapter;
import cz.mg.toolkit.event.events.ActionEvent;
import cz.mg.toolkit.event.events.MouseButtonEvent;
import cz.mg.toolkit.utilities.Triggerable;


public abstract class ActionMenuItem extends MenuItem implements Triggerable {
    public ActionMenuItem() {
        getEventListeners().addLast(new LocalMouseButtonAdapter() {
            @Override
            public void onMouseButtonEventEnter(MouseButtonEvent e) {
                if(wasPressed(e) && wasLeftButton(e)){
                    e.consume();
                    trigger();
                }
            }
        });
    }
    
    @Override
    public void trigger(){
        raiseEvent(new ActionEvent(this));
    }
}
