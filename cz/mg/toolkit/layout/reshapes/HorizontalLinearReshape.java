package cz.mg.toolkit.layout.reshapes;

import cz.mg.collections.Collection;
import cz.mg.toolkit.component.Component;
import static cz.mg.toolkit.layout.reshapes.Reshape.getEffectiveMaxWidth;
import static cz.mg.toolkit.layout.reshapes.Reshape.getEffectiveMinWidth;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;


public class HorizontalLinearReshape {
    public static void reshape(Component parent, Collection<? extends Component> components){
        resize(parent, components);
        reposition(parent, components);
    }
    
    public static void resize(Component parent, Collection<? extends Component> components) {
        for(Component component : components) component.setWidth(getEffectiveMinWidth(component));
        double availableWidth = parent.getWidth() - computeWrapWidth(parent, components);
        if(availableWidth <= 0) return;
        
        do {
            double totalRatio = 0;
            for(Component component : components) if(!isFull(component)) totalRatio += getHorizontalWeight(component);
            if(totalRatio <= 0.0001) return;

            double remainingWidth = 0;
            for(Component component : components){
                if(!isFull(component)){
                    double relativeWidthAddition = getHorizontalWeight(component) / totalRatio;
                    double absoluteWidthAddition = relativeWidthAddition * availableWidth;
                    remainingWidth += addWidth(component, absoluteWidthAddition);
                }
            }
            availableWidth = remainingWidth;
        } while(availableWidth > 0);
    }
    
    public static void reposition(Component parent, Collection<? extends Component> components) {
        double x = getLeftPadding(parent);
        double spacing = getHorizontalSpacing(parent);
        for(Component component : components){
            component.setX(x);
            if(!component.isHidden()) x += component.getWidth() + spacing;
        }
    }
    
    public static double computeWrapWidth(Component parent, Collection<? extends Component> components){
        double totalPadding = getLeftPadding(parent) + getRightPadding(parent);
        if(components.count() <= 0) return totalPadding;
        
        double totalMinWidth = computeTotalMinWidth(parent, components);
        double totalSpacing = computeTotalSpacing(parent, components);
        return totalPadding + totalMinWidth + totalSpacing;
    }
    
    private static double computeTotalMinWidth(Component parent, Collection<? extends Component> components){
        double totalMinWidth = 0;
        for(Component component : components) totalMinWidth += getEffectiveMinWidth(component);
        return totalMinWidth;
    }
    
    private static double computeTotalSpacing(Component parent, Collection<? extends Component> components){
        int visibleComponentCount = 0;
        for(Component component : components){
            if(!component.isHidden()) visibleComponentCount++;
        }
        return getHorizontalSpacing(parent) * (visibleComponentCount - 1);
    }
    
    private static boolean isFull(Component component){
        double maxWidth = getEffectiveMaxWidth(component);
        return component.getWidth() >= maxWidth;
    }
    
    private static double addWidth(Component component, double sizeAddition){
        double maxWidth = getEffectiveMaxWidth(component);
        component.setWidth(component.getWidth() + sizeAddition);
        double remainingPorridge = component.getWidth() - maxWidth;
        if(remainingPorridge <= 0){
            return 0;
        } else {
            component.setWidth(maxWidth);
            return remainingPorridge;
        }
    }
}
