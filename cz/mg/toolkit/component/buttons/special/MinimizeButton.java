package cz.mg.toolkit.component.buttons.special;

import cz.mg.toolkit.component.buttons.CanvasButton;
import cz.mg.toolkit.component.window.Window;
import cz.mg.toolkit.graphics.Graphics;
import cz.mg.toolkit.event.adapters.ActionAdapter;
import cz.mg.toolkit.event.adapters.GraphicsDrawAdapter;
import cz.mg.toolkit.event.events.ActionEvent;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;


public class MinimizeButton extends CanvasButton {
    public MinimizeButton() {
        setPadding(this, 4);
        getCanvas().getEventListeners().addLast(new GraphicsDrawAdapter() {
            @Override
            public void onDrawEventLeave(Graphics g) {
                g.setColor(getCurrentForegroundColor());
                g.drawLine(0, getCanvas().getHeight()-1, getCanvas().getWidth()-1, getCanvas().getHeight()-1);
            }
        });
        getEventListeners().addLast(new ActionAdapter() {
            @Override
            public void onEventEnter(ActionEvent e) {
                e.consume();
                Window window = getWindow();
                if(window != null) window.setMinimized(!window.isMinimized());
            }
        });
    }
}
