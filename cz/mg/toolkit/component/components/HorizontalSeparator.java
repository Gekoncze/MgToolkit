package cz.mg.toolkit.component.components;

import cz.mg.toolkit.component.Panel;
import cz.mg.toolkit.component.contents.Canvas;
import cz.mg.toolkit.graphics.Graphics;
import cz.mg.toolkit.event.adapters.GraphicsDrawAdapter;
import cz.mg.toolkit.layout.layouts.OverlayLayout;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;


public class HorizontalSeparator extends Panel {
    private static final int DEFAULT_PADDING = 4;
    private final Canvas canvas = new Canvas();
    
    public HorizontalSeparator() {
        initComponent();
        initComponents();
        addEventListeners();
    }
    
    private void initComponent() {
        setLayout(new OverlayLayout());
        setPadding(this, DEFAULT_PADDING);
        setFillParentHeight(this);
    }
    
    private void initComponents() {
        canvas.setParent(this);
        canvas.setContentWidth(3);
        canvas.setContentHeight(0);
        setFillParentHeight(canvas);
    }

    private void addEventListeners() {
        canvas.getEventListeners().addLast(new GraphicsDrawAdapter() {
            @Override
            public void onDrawEventLeave(Graphics g) {
                g.setColor(getCurrentForegroundColor());
                g.drawLine(canvas.getWidth()/2, 0, canvas.getWidth()/2, canvas.getHeight());
            }
        });
    }
}
