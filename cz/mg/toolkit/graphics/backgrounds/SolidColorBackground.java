package cz.mg.toolkit.graphics.backgrounds;

import cz.mg.toolkit.component.Component;
import cz.mg.toolkit.graphics.Background;
import cz.mg.toolkit.graphics.Graphics;
import cz.mg.toolkit.utilities.Drawable;


public class SolidColorBackground implements Background {
    @Override
    public void onDraw(Graphics g, Component component) {
        g.setColor(((Drawable)component).getCurrentBackgroundColor());
        g.fillRectangle(0, 0, component.getWidth()-1, component.getHeight()-1);
    }
}
