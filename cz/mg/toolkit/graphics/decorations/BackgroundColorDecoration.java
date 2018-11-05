package cz.mg.toolkit.graphics.decorations;

import cz.mg.toolkit.component.Component;
import cz.mg.toolkit.graphics.Color;
import cz.mg.toolkit.graphics.Decoration;
import cz.mg.toolkit.graphics.Graphics;
import static cz.mg.toolkit.utilities.properties.PropertiesInterface.*;


public class BackgroundColorDecoration extends Decoration {
    public BackgroundColorDecoration(Decoration decoration) {
        super(decoration);
    }
    
    @Override
    protected void onDraw(Graphics g, Component component) {
        g.setColor(getCurrentBackgroundColor(component));
    }
    
    public static final Color getCurrentBackgroundColor(Component component){
        if(component.isEffectivelyDisabled()) return getDisabledBackgroundColor(component);
        if(isHighlighted(component)) return getHighlightedBackgroundColor(component);
        return getBackgroundColor(component);
    }
}
