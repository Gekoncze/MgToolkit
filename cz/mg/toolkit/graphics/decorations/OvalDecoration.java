package cz.mg.toolkit.graphics.decorations;

import cz.mg.toolkit.component.Component;
import cz.mg.toolkit.graphics.Decoration;
import cz.mg.toolkit.graphics.Graphics;


public class OvalDecoration extends Decoration {
    public OvalDecoration() {
    }
    
    @Override
    protected void onDraw(Graphics g, Component component) {
        g.drawOval(0, 0, component.getWidth(), component.getHeight());
    }
}
