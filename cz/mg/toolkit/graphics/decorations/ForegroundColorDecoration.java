package cz.mg.toolkit.graphics.decorations;

import cz.mg.toolkit.component.Component;
import cz.mg.toolkit.graphics.Color;
import cz.mg.toolkit.graphics.Decoration;
import cz.mg.toolkit.graphics.Graphics;
import static cz.mg.toolkit.utilities.properties.PropertiesInterface.*;


public class ForegroundColorDecoration extends Decoration {
    public ForegroundColorDecoration(Decoration decoration) {
        super(decoration);
    }
    
    @Override
    protected void onDraw(Graphics g, Component component) {
        g.setColor(getCurrentForegroundColor(component));
    }
    
    public static final Color getCurrentForegroundColor(Component component){
        if(component.isEffectivelyDisabled()) return getDisabledForegroundColor(component);
        if(isHighlighted(component)) return getHighlightedForegroundColor(component);
        return getForegroundColor(component);
    }
}
