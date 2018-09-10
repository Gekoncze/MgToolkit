package cz.mg.toolkit.layout.reshapes;

import cz.mg.collections.Collection;
import cz.mg.toolkit.component.Component;
import static cz.mg.toolkit.layout.reshapes.Reshape.getEffectiveMaxHeight;
import static cz.mg.toolkit.layout.reshapes.Reshape.getEffectiveMinHeight;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;


public class VerticalLinearReshape {
    public static void reshape(Component parent, Collection<? extends Component> components){
        resize(parent, components);
        reposition(parent, components);
    }
    
    public static void resize(Component parent, Collection<? extends Component> components) {
        for(Component component : components) component.setHeight(getEffectiveMinHeight(component));
        double availableHeight = parent.getHeight() - computeWrapHeight(parent, components);
        if(availableHeight <= 0) return;
        
        do {
            double totalRatio = 0;
            for(Component component : components) if(!isFull(component)) totalRatio += getVerticalWeight(component);
            if(totalRatio <= 0.0001) return;

            double remainingHeight = 0;
            for(Component component : components){
                if(!isFull(component)){
                    double relativeHeightAddition = getVerticalWeight(component) / totalRatio;
                    double absoluteHeightAddition = relativeHeightAddition * availableHeight;
                    remainingHeight += addHeight(component, absoluteHeightAddition);
                }
            }
            availableHeight = remainingHeight;
        } while(availableHeight > 0);
    }
    
    public static void reposition(Component parent, Collection<? extends Component> components) {
        double y = getTopPadding(parent);
        double spacing = getVerticalSpacing(parent);
        for(Component component : components){
            component.setY(y);
            if(!isHidden(component)) y += component.getHeight() + spacing;
        }
    }
    
    public static double computeWrapHeight(Component parent, Collection<? extends Component> components){
        double totalPadding = getTopPadding(parent) + getBottomPadding(parent);
        if(components.count() <= 0) return totalPadding;
        
        double totalMinHeight = computeTotalMinHeight(parent, components);
        double totalSpacing = computeTotalSpacing(parent, components);
        return totalPadding + totalMinHeight + totalSpacing;
    }
    
    private static double computeTotalMinHeight(Component parent, Collection<? extends Component> components){
        double totalMinHeight = 0;
        for(Component component : components) totalMinHeight += getEffectiveMinHeight(component);
        return totalMinHeight;
    }
    
    private static double computeTotalSpacing(Component parent, Collection<? extends Component> components){
        int visibleComponentCount = 0;
        for(Component component : components){
            if(!isHidden(component)) visibleComponentCount++;
        }
        return getVerticalSpacing(parent) * (visibleComponentCount - 1);
    }
    
    private static boolean isFull(Component component){
        double maxHeight = getEffectiveMaxHeight(component);
        return component.getHeight() >= maxHeight;
    }
    
    private static double addHeight(Component component, double porridgeServing){
        double maxHeight = getEffectiveMaxHeight(component);
        component.setHeight(component.getHeight() + porridgeServing);
        double remainingPorridge = component.getHeight() - maxHeight;
        if(remainingPorridge <= 0){
            return 0;
        } else {
            component.setHeight(maxHeight);
            return remainingPorridge;
        }
    }
}
