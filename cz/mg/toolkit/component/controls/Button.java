package cz.mg.toolkit.component.controls;

import cz.mg.toolkit.component.DrawableContainer;
import cz.mg.toolkit.event.adapters.LocalMouseButtonAdapter;
import cz.mg.toolkit.event.events.ActionEvent;
import cz.mg.toolkit.event.events.MouseButtonEvent;
import cz.mg.toolkit.layout.layouts.OverlayLayout;
import cz.mg.toolkit.utilities.Triggerable;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;


public abstract class Button extends DrawableContainer implements Triggerable {
    public static final int DEFAULT_PADDING = 8;
    private ActionInvocation actionInvocation = ActionInvocation.EVENT_ENTER;
    
    public Button() {
        setLayout(new OverlayLayout());
        setPadding(this, DEFAULT_PADDING);
        addEventListeners();
    }
    
    private void addEventListeners(){
        getEventListeners().addLast(new LocalMouseButtonAdapter() {
            @Override
            public void onMouseButtonEventEnter(MouseButtonEvent e) {
                if(actionInvocation == ActionInvocation.EVENT_ENTER && wasPressed(e) && wasLeftButton(e)){
                    e.consume();
                    trigger();
                }
            }

            @Override
            public void onMouseButtonEventLeave(MouseButtonEvent e) {
                if(actionInvocation == ActionInvocation.EVENT_LEAVE && wasPressed(e) && wasLeftButton(e)){
                    e.consume();
                    trigger();
                }
            }
        });
    }

    public final ActionInvocation getActionInvocation() {
        return actionInvocation;
    }

    public final void setActionInvocation(ActionInvocation actionInvocation) {
        this.actionInvocation = actionInvocation;
    }
    
    @Override
    public void trigger(){
        raiseEvent(new ActionEvent(this));
    }
    
    public static enum ActionInvocation {
        EVENT_ENTER,
        EVENT_LEAVE
    }
}
