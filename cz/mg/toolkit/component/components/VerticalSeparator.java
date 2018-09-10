package cz.mg.toolkit.component.components;

import cz.mg.toolkit.component.Panel;
import cz.mg.toolkit.component.contents.Canvas;
import cz.mg.toolkit.graphics.Graphics;
import cz.mg.toolkit.event.adapters.GraphicsDrawAdapter;
import cz.mg.toolkit.layout.layouts.OverlayLayout;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;


public class VerticalSeparator extends Panel {
    private static final int DEFAULT_PADDING = 4;
    private final Canvas canvas = new Canvas();
    
    public VerticalSeparator() {
        initComponent();
        initComponents();
        addEventListeners();
    }
    
    private void initComponent() {
        setLayout(new OverlayLayout());
        setPadding(this, DEFAULT_PADDING);
        setFillParentWidth(this);
    }
    
    private void initComponents() {
        canvas.setParent(this);
        canvas.setContentWidth(0);
        canvas.setContentHeight(3);
        setFillParentWidth(canvas);
    }

    private void addEventListeners() {
        canvas.getEventListeners().addLast(new GraphicsDrawAdapter() {
            @Override
            public void onDrawEventLeave(Graphics g) {
                g.setColor(getCurrentForegroundColor());
                g.drawLine(0, canvas.getHeight()/2, canvas.getWidth(), canvas.getHeight()/2);
            }
        });
    }
}
