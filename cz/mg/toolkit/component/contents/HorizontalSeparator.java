package cz.mg.toolkit.component.contents;

import cz.mg.toolkit.component.DrawableContent;
import cz.mg.toolkit.graphics.Graphics;
import cz.mg.toolkit.event.adapters.GraphicsDrawAdapter;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;


public class HorizontalSeparator extends DrawableContent {
    private static final int DEFAULT_WIDTH = 7;
    private static final int DEFAULT_HEIGHT = 4;
    
    private int spaceSize = 2;
    
    public HorizontalSeparator() {
        initComponent();
        addEventListeners();
    }
    
    private void initComponent() {
        setContentWidth(DEFAULT_WIDTH);
        setContentHeight(DEFAULT_HEIGHT);
        setFillParentHeight(this);
    }

    private void addEventListeners() {
        getEventListeners().addLast(new GraphicsDrawAdapter() {
            @Override
            public void onDrawEventLeave(Graphics g) {
                g.setColor(getCurrentForegroundColor());
                double x = (getWidth()-1)/2;
                double y0 = spaceSize;
                double y1 = getHeight()-1 - spaceSize;
                g.drawLine(x, y0, x, y1);
            }
        });
    }

    public final int getSpaceSize() {
        return spaceSize;
    }

    public final void setSpaceSize(int spaceSize) {
        this.spaceSize = spaceSize;
    }
}
