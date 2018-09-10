package cz.mg.toolkit.graphics.borders;

import cz.mg.toolkit.component.Component;
import cz.mg.toolkit.graphics.Border;
import cz.mg.toolkit.graphics.Graphics;
import cz.mg.toolkit.utilities.Drawable;


public class SolidColorBorder extends Border {
    @Override
    public void onDraw(Graphics g, Component component) {
        g.setColor(((Drawable)component).getCurrentForegroundColor());
        g.drawRectangle(0, 0, component.getWidth()-1, component.getHeight()-1);
    }
}
