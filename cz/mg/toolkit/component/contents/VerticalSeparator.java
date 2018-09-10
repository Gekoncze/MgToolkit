package cz.mg.toolkit.component.contents;

import cz.mg.toolkit.component.DrawableContent;
import cz.mg.toolkit.graphics.Graphics;
import cz.mg.toolkit.event.adapters.GraphicsDrawAdapter;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;


public class VerticalSeparator extends DrawableContent {
    private static final int DEFAULT_WIDTH = 4;
    private static final int DEFAULT_HEIGHT = 7;
    
    private int spaceSize = 2;
    
    public VerticalSeparator() {
        initComponent();
        addEventListeners();
    }
    
    private void initComponent() {
        setContentWidth(DEFAULT_WIDTH);
        setContentHeight(DEFAULT_HEIGHT);
        setFillParentWidth(this);
    }

    private void addEventListeners() {
        getEventListeners().addLast(new GraphicsDrawAdapter() {
            @Override
            public void onDrawEventLeave(Graphics g) {
                g.setColor(getCurrentForegroundColor());
                double y = (getHeight()-1)/2;
                double x0 = spaceSize;
                double x1 = getWidth()-1 - spaceSize;
                g.drawLine(x0, y, x1, y);
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
