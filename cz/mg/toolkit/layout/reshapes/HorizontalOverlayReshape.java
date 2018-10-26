package cz.mg.toolkit.layout.reshapes;

import cz.mg.collections.Collection;
import cz.mg.toolkit.component.Component;
import static cz.mg.toolkit.layout.reshapes.Reshape.align;
import static cz.mg.toolkit.layout.reshapes.Reshape.getEffectiveMaxWidth;
import static cz.mg.toolkit.layout.reshapes.Reshape.getEffectiveMinWidth;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;
import static cz.mg.toolkit.layout.reshapes.Reshape.align;


public class HorizontalOverlayReshape {
    public static void reshape(Component parent, Collection<? extends Component> components){
        resize(parent, components);
        reposition(parent, components);
    }
    
    public static void resize(Component parent, Collection<? extends Component> components) {
        double availableWidth = parent.getAvailableWidth();
        for(Component component : components) resize(availableWidth, component);
    }
    
    public static void resize(double availableWidth, Component component){
        double size = availableWidth;
        double minWidth = getEffectiveMinWidth(component);
        double maxWidth = getEffectiveMaxWidth(component);
        if(size > maxWidth) size = maxWidth;
        if(size < minWidth) size = minWidth;
        component.setWidth(size);
    }
    
    public static void reposition(Component parent, Collection<? extends Component> components) {
        double leadingPadding = getLeftPadding(parent);
        double trailingPadding = getRightPadding(parent);
        double parentWidth = parent.getWidth();
        for(Component component : components){
            double componentWidth = component.getWidth();
            double alignment = getHorizontalAlignment(component);
            double x = align(parentWidth, componentWidth, alignment, leadingPadding, trailingPadding);
            component.setX(x);
        }
    }

    public static double computeMinWidth(Component parent, Collection<? extends Component> components){
        double minWidth = 0;
        for(Component component : components) minWidth = Math.max(minWidth, getEffectiveMinWidth(component));
        return minWidth + getLeftPadding(parent) + getRightPadding(parent);
    }
}
