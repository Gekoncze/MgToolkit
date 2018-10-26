package cz.mg.toolkit.component.controls;

import cz.mg.toolkit.event.adapters.ActionAdapter;
import cz.mg.toolkit.event.adapters.LocalMouseButtonAdapter;
import cz.mg.toolkit.event.events.ActionEvent;
import cz.mg.toolkit.event.events.MouseButtonEvent;
import cz.mg.toolkit.utilities.Selectable;
import cz.mg.toolkit.utilities.Triggerable;


public class CheckBox extends Button implements Selectable, Triggerable {
    private boolean selected = false;
    
    public CheckBox() {
        addEventListeners();
    }

    private void addEventListeners() {
        getEventListeners().addLast(new LocalMouseButtonAdapter() {
            @Override
            public void onMouseButtonEventLeave(MouseButtonEvent e) {
                if(wasPressed(e) && wasLeftButton(e)){
                    e.consume();
                    trigger();
                }
            }
        });
        getEventListeners().addFirst(new ActionAdapter() {
            @Override
            public void onEventEnter(ActionEvent e) {
                setSelected(!isSelected());
                redraw();
            }
        });
    }

    @Override
    public final boolean isSelected() {
        return selected;
    }

    @Override
    public final void setSelected(boolean value) {
        this.selected = value;
    }
}
