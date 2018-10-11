package cz.mg.toolkit.graphics.decorations;

import cz.mg.toolkit.component.Component;
import cz.mg.toolkit.graphics.Decoration;
import cz.mg.toolkit.graphics.Graphics;
import cz.mg.toolkit.utilities.DrawableComponent;


public class ForegroundColorDecoration extends Decoration {
    public ForegroundColorDecoration(Decoration decoration) {
        super(decoration);
    }
    
    @Override
    protected void onDraw(Graphics g, Component component) {
        if(component instanceof DrawableComponent){
            g.setColor(((DrawableComponent) component).getCurrentForegroundColor());
        }
    }
}
