package cz.mg.toolkit.graphics.decorations;

import cz.mg.toolkit.component.Component;
import cz.mg.toolkit.graphics.Decoration;
import cz.mg.toolkit.graphics.Graphics;
import cz.mg.toolkit.utilities.DrawableComponent;


public class BackgroundColorDecoration extends Decoration {
    public BackgroundColorDecoration(Decoration decoration) {
        super(decoration);
    }
    
    @Override
    protected void onDraw(Graphics g, Component component) {
        if(component instanceof DrawableComponent){
            g.setColor(((DrawableComponent) component).getCurrentBackgroundColor());
        }
    }
}
