package cz.mg.toolkit.graphics.decorations;

import cz.mg.toolkit.component.Component;
import cz.mg.toolkit.graphics.Decoration;
import cz.mg.toolkit.graphics.Graphics;


public class OvalBorderDecoration extends Decoration {
    public OvalBorderDecoration() {
    }
    
    @Override
    protected void onDraw(Graphics g, Component component) {
        g.drawOvalBorder(0, 0, component.getWidth(), component.getHeight());
    }
}
