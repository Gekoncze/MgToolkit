package cz.mg.toolkit.component.wrappers.decorations;

import cz.mg.toolkit.component.ToplevelComponent;
import cz.mg.toolkit.component.wrappers.Decoration;
import cz.mg.toolkit.component.window.Window;
import cz.mg.toolkit.event.adapters.BeforeLayoutAdapter;
import cz.mg.toolkit.event.events.BeforeLayoutEvent;
import cz.mg.toolkit.layout.layouts.OverlayLayout;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;
import cz.mg.toolkit.impl.ImplWindow;


public class SystemDecoration extends Decoration {
    public static final String DEFAULT_DESIGN_NAME = "system decoration";
    
    public SystemDecoration() {
        setLayout(new OverlayLayout());
        addEventListeners();
    }
    
    private void addEventListeners(){
        getEventListeners().addLast(new BeforeLayoutAdapter() {
            @Override
            public void onEventEnter(BeforeLayoutEvent e) {
                Window window = getWindow();
                if(window == null) return;
                ImplWindow nativeWindow = window.getImplWindow();
                setLeftPadding(SystemDecoration.this, nativeWindow.getLeftInsets());
                setRightPadding(SystemDecoration.this, nativeWindow.getRightInsets());
                setTopPadding(SystemDecoration.this, nativeWindow.getTopInsets());
                setBottomPadding(SystemDecoration.this, nativeWindow.getBottomInsets());
            }
        });
    }
    
    private Window getWindow(){
        ToplevelComponent toplevelComponent = getToplevelComponent();
        if(toplevelComponent == null) return null;
        if(toplevelComponent instanceof Window) return (Window) toplevelComponent;
        return null;
    }
}
