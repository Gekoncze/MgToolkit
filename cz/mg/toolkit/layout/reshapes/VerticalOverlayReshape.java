package cz.mg.toolkit.layout.reshapes;

import cz.mg.collections.Collection;
import cz.mg.toolkit.component.Component;
import static cz.mg.toolkit.layout.reshapes.Reshape.align;
import static cz.mg.toolkit.layout.reshapes.Reshape.getEffectiveMaxHeight;
import static cz.mg.toolkit.layout.reshapes.Reshape.getEffectiveMinHeight;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;


public class VerticalOverlayReshape {
    public static void reshape(Component parent, Collection<? extends Component> components){
        resize(parent, components);
        reposition(parent, components);
    }
    
    public static void resize(Component parent, Collection<? extends Component> components) {
        double availableHeight = parent.getAvailableHeight();
        for(Component component : components) resize(availableHeight, component);
    }
    
    public static void resize(double availableHeight, Component component){
        double size = availableHeight;
        double minHeight = getEffectiveMinHeight(component);
        double maxHeight = getEffectiveMaxHeight(component);
        if(size > maxHeight) size = maxHeight;
        if(size < minHeight) size = minHeight;
        component.setHeight(size);
    }
    
    public static void reposition(Component parent, Collection<? extends Component> components) {
        double leadingPadding = getTopPadding(parent);
        double trailingPadding = getBottomPadding(parent);
        double parentHeight = parent.getHeight();
        for(Component component : components){
            double componentHeight = component.getHeight();
            double alignment = getVerticalAlignment(component);
            double y = align(parentHeight, componentHeight, alignment, leadingPadding, trailingPadding);
            component.setY(y);
        }
    }

    public static double computeMinHeight(Component parent, Collection<? extends Component> components){
        double minHeight = 0;
        for(Component component : components) minHeight = Math.max(minHeight, getEffectiveMinHeight(component));
        return minHeight + getTopPadding(parent) + getBottomPadding(parent);
    }
}
