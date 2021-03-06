package cz.mg.toolkit.component.controls;

import cz.mg.toolkit.event.adapters.ActionAdapter;
import cz.mg.toolkit.event.adapters.LocalMouseButtonAdapter;
import cz.mg.toolkit.event.events.ActionEvent;
import cz.mg.toolkit.event.events.MouseButtonEvent;
import cz.mg.toolkit.utilities.SelectionGroup;
import cz.mg.toolkit.utilities.Selectable;
import cz.mg.toolkit.utilities.Triggerable;


public class RadioButton extends Button implements Selectable, Triggerable {
    public static final String DEFAULT_DESIGN_NAME = "radio button";
    
    private boolean selected = false;
    private SelectionGroup selectionGroup;

    public RadioButton() {
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
                if(selectionGroup != null) selectionGroup.clearSelection();
                setSelected(true);
                redraw();
            }
        });
    }

    public final SelectionGroup getSelectionGroup() {
        return selectionGroup;
    }

    public final void setSelectionGroup(SelectionGroup selectionGroup) {
        if(this.selectionGroup != null) this.selectionGroup.removeSelectable(this);
        this.selectionGroup = selectionGroup;
        selectionGroup.addSelectable(this);
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
