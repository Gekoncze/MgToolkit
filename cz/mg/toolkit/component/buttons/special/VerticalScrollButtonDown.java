package cz.mg.toolkit.component.buttons.special;

import cz.mg.toolkit.component.Container;
import cz.mg.toolkit.graphics.Graphics;
import cz.mg.toolkit.event.adapters.GraphicsDrawAdapter;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.scrollVertically;


public class VerticalScrollButtonDown extends ScrollButton {
    public VerticalScrollButtonDown() {
        initComponents();
    }
    
    private void initComponents() {
        getCanvas().getEventListeners().addLast(new GraphicsDrawAdapter() {
            @Override
            public void onDrawEventEnter(Graphics g) {
                double w = getCanvas().getWidth();
                double h = getCanvas().getHeight();
                g.setColor(getCurrentForegroundColor());
                g.drawLine(0, 0, w*0.5, h-1);
                g.drawLine(w*0.5, h-1, w-1, 0);
            }
        });
    }

    @Override
    protected void doScroll(Container scrollPanel) {
        scrollVertically(scrollPanel, getScrollSpeed());
    }
}
