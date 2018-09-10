package cz.mg.toolkit.component.wrappers.decorations;

import cz.mg.toolkit.component.wrappers.Decoration;
import cz.mg.toolkit.environment.NativeWindow;
import cz.mg.toolkit.component.window.Window;
import cz.mg.toolkit.graphics.Image;
import cz.mg.toolkit.event.adapters.BeforeLayoutAdapter;
import cz.mg.toolkit.event.events.BeforeLayoutEvent;
import cz.mg.toolkit.layout.layouts.OverlayLayout;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;


public class SystemDecoration extends Decoration {
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
                NativeWindow nativeWindow = window.getNativeWindow();
                setLeftPadding(SystemDecoration.this, nativeWindow.getLeftInsets());
                setRightPadding(SystemDecoration.this, nativeWindow.getRightInsets());
                setTopPadding(SystemDecoration.this, nativeWindow.getTopInsets());
                setBottomPadding(SystemDecoration.this, nativeWindow.getBottomInsets());
            }
        });
    }

    @Override
    public void setTitle(String title) {
        Window window = getWindow();
        if(window == null) return;
        NativeWindow nativeWindow = window.getNativeWindow();
        nativeWindow.setTitle(title);
    }

    @Override
    public void setIcon(Image icon) {
        Window window = getWindow();
        if(window == null) return;
        NativeWindow nativeWindow = window.getNativeWindow();
        nativeWindow.setIcon(icon);
    }
}
