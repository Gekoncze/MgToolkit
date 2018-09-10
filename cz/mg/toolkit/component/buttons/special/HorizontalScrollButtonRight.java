package cz.mg.toolkit.component.buttons.special;

import cz.mg.toolkit.component.Container;
import cz.mg.toolkit.graphics.Graphics;
import cz.mg.toolkit.event.adapters.GraphicsDrawAdapter;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;


public class HorizontalScrollButtonRight extends ScrollButton {
    public HorizontalScrollButtonRight() {
        initComponents();
    }
    
    private void initComponents() {
        getCanvas().getEventListeners().addLast(new GraphicsDrawAdapter() {
            @Override
            public void onDrawEventEnter(Graphics g) {
                double w = getCanvas().getWidth();
                double h = getCanvas().getHeight();
                g.setColor(getCurrentForegroundColor());
                g.drawLine(0, 0, w-1, h*0.5);
                g.drawLine(w-1, h*0.5, 0, h-1);
            }
        });
    }

    @Override
    protected void doScroll(Container scrollPanel) {
        scrollHorizontally(scrollPanel, getScrollSpeed());
    }
}
