package cz.mg.toolkit.component.buttons;

import cz.mg.toolkit.event.adapters.GraphicsDrawAdapter;
import cz.mg.toolkit.graphics.Graphics;


public class CloseButton extends CanvasButton {
    public CloseButton() {
        getCanvas().getEventListeners().addLast(new GraphicsDrawAdapter() {
            @Override
            public void onDrawEventLeave(Graphics g) {
                g.setColor(getCurrentForegroundColor());
                g.drawLine(0, 0, getCanvas().getWidth()-1, getCanvas().getHeight()-1);
                g.drawLine(0, getCanvas().getHeight()-1, getCanvas().getWidth()-1, 0);
            }
        });
    }
}
